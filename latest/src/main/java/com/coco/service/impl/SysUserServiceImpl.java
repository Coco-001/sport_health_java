package com.coco.service.impl;

import com.coco.config.security.service.UserDetailServiceImpl;
import com.coco.entity.SysRole;
import com.coco.entity.SysUser;
import com.coco.mapper.SysUserMapper;
import com.coco.service.SysUserService;
import com.coco.utils.*;
import com.coco.vo.LoginVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登录接口
     * @param loginVo 登录参数： 账号和密码
     * @return
     */
    @Override
    public Result login(LoginVo loginVo) {
        UserDetails userDetails;
        if ("2".equals(loginVo.getType())) {
            if (!StringUtils.isNotEmpty(loginVo.getPhoneNumber()) || !StringUtils.isNotEmpty(loginVo.getCode())) {
                return Result.fail("请填写完整的信息！");
            }
            // 验证码对比
            Object code = redisUtils.getValue(loginVo.getPhoneNumber() + "sms");
            if (code == null) {
                return Result.fail("验证码已过期！");
            }
            if (!code.toString().equals(loginVo.getCode())) {
                return Result.fail("验证码不正确！");
            }
            userDetails = userDetailsService.loadUserByUsername(loginVo.getPhoneNumber());
        } else {
            if (!StringUtils.isNotEmpty(loginVo.getUsername()) || !StringUtils.isNotEmpty(loginVo.getPassword())) {
                return Result.fail("请填写完整的信息！");
            }
            log.info("1. 开始登录");
            userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
            log.info("2. 判断用户是否存在，密码是否正确");
            if (null == userDetails || !passwordEncoder.matches(MD5Utils.md5(loginVo.getPassword()), userDetails.getPassword())) {
                return Result.fail("账号或密码错误，请重新输入！");
            }
        }
        if (!userDetails.isEnabled()) {
            return Result.fail("该账号未启用，请联系管理员！");
        }
        log.info("登录成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登录信息获取token");
        //需要借助jwt来生成token
        String token = tokenUtils.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return Result.success("登录成功！", map);
    }

    /**
     * 微信小程序登录
     * @param openid 登录参数： 账号和密码
     * @return
     */
    @Override
    public Result miniLogin(String openid, String sessionKey) {
        UserDetails userDetails;
        userDetails = userDetailsService.loadUserByUsername(openid);
        if (userDetails == null) {
            userMapper.insertOpenid(openid);
            userDetails = userDetailsService.loadUserByUsername(openid);
        }
        if (!userDetails.isEnabled()) {
            return Result.fail("该账号未启用，请联系管理员！");
        }
        log.info("微信小程序登录成功，在security对象中存入登陆者信息");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("根据登录信息获取token");
        //需要借助jwt来生成token
        String token = tokenUtils.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        map.put("userInfo", userDetails);
        map.put("openid", openid);
        map.put("sessionKey", sessionKey);
        return Result.success("登录成功！", map);
    }

    @Override
    public SysUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("分页查询--> 页码==>{}, 页数大小==>{}", queryInfo.getPageNumber(), queryInfo.getPageSize());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysUser> page = userMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysUser> result = page.getResult();
        result.forEach(item -> {
            item.setRoles(userMapper.findRoles(item.getId()));
            item.setName(item.getUsername());
            item.setPassword(null);
        });
        return PageResult.pageRsult(total, result);
    }

    @Transactional
    @Override
    public Result insert(SysUser user) {
        log.info("根据用户名查询用户信息");
        SysUser sysUser = userMapper.findByUsername(user.getUsername());
        if (null != sysUser) {
            return Result.fail("用户名已经存在！");
        }
        log.info("给密码加密");
        user.setPassword(passwordEncoder.encode(MD5Utils.md5(user.getPassword())));
        log.info("1. 添加用户信息");
        userMapper.insert(user);
        log.info("2. 添加角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(item -> userMapper.insertUserRoles(user.getId(), item.getId()));
        }
        log.info("3. 用户角色添加成功有{}个", roles.size());
        return Result.success("用户添加成功！");
    }

    @Transactional
    @Override
    public Result update(SysUser user) {
        log.info("1. 先将用户角色信息给删除");
        userMapper.deleteRolesByUserId(user.getId());
        log.info("2. 添加用户角色信息");
        List<SysRole> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(item -> userMapper.insertUserRoles(user.getId(), item.getId()));
        }
        log.info("3. 修改用户信息");
        userMapper.update(user);
        return Result.success("用户信息修改成功！");
    }

    @Override
    public Result delete(Long id) {
        SysUser user = userMapper.findById(id);
        if (null == user) {
            return Result.fail("用户ID不存在！");
        }
        userMapper.deleteRolesByUserId(id);
        userMapper.delete(id);
        return Result.success("用户信息删除成功！");
    }

    @Override
    public void updatePwdByMail(String email, String password) {
        log.info("邮箱修改密码");
        userMapper.updatePwdByMail(email, password);
    }

    @Override
    public Result updateByopenId(SysUser user) {
        if (StringUtils.isEmpty(user.getOpenId())) {
            return Result.fail("请传递小程序唯一标识");
        }
        // 清除用户缓存，从新获取
        redisUtils.delKey("username_" + user.getOpenId());
        userMapper.updateByopenId(user);
        return Result.success("用户信息更新成功");
    }
}
