package com.coco.config.security.service;

import com.coco.entity.SysMenu;
import com.coco.entity.SysUser;
import com.coco.mapper.SysUserMapper;
import com.coco.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现UserDetailsService接口，实现自定义登陆逻辑
 * 重写loadUserByUsername方法
 *
 * @author ajie
 * @createTime 2021年07月30日 22:23:00
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisUtils redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //判断缓存中是否存在用户信息 存在则直接从缓存中取，不存在则查询数据库并把数据存入缓存
        SysUser user;
        if (redisUtil.haskey("userInfo_" + username)) {
            //缓存中存在用户信息，直接从redis中取
            user = (SysUser)redisUtil.getValue("userInfo_" + username);
            redisUtil.expire("userInfo_" + username, 5);
        } else {
            user = userMapper.findByUsername(username);
            if (null == user) {
                throw new UsernameNotFoundException("用户名或密码错误！");
            }
            if (user.isAdmin()) {
                //非管理员需要查询角色信息
                user.setRoles(userMapper.findRoles(null));
                user.setPermissions(userMapper.findPermissions(null));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(null);
                //获取子级菜单
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenu(item.getId(), null)));
                user.setMenus(menus);
            } else {
                //非管理员需要查询角色信息
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
                //获取父级菜单
                List<SysMenu> menus = userMapper.findMenus(user.getId());
                //获取子级菜单
                menus.forEach(item -> item.setChildren(userMapper.findChildrenMenu(item.getId(), user.getId())));
                user.setMenus(menus);
            }
            redisUtil.setValueTime("userInfo_" + username, user, 5);
        }
        return user;
    }
}
