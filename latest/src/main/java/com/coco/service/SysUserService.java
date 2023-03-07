package com.coco.service;

import com.coco.entity.SysUser;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import com.coco.vo.LoginVo;
import org.springframework.context.annotation.Primary;

public interface SysUserService {

    /**
     * 登录接口
     * @param loginVo 登录参数： 账号和密码
     * @return 返回token，用token去获取资源
     */
    Result login(LoginVo loginVo);

    /**
     * 登录接口
     * @param openid 登录参数： 账号和密码
     * @return 返回token，用token去获取资源
     */
    Result miniLogin(String openid, String sessionKey);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 分页查询
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    Result insert(SysUser user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    Result update(SysUser user);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 根据邮箱修改密码
     * @param email
     * @param password
     */
    void updatePwdByMail(String email, String password);

    /**
     * 根据openid更新用户信息
     * @param user
     * @return
     */
    Result updateByopenId(SysUser user);
}
