package com.coco.utils;

import com.coco.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {

    /**
     * 从Security主体信息中获取用户信息
     * @return
     */
    public static SysUser getUser() {
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        user.setName(user.getUsername());
        return user;
    }

    /**
     * 在security中获取用户名
     * @return
     */
    public static String getUsername() {
        return getUser().getUsername();
    }

    /**
     * 在security中获取用户ID
     * @return
     */
    public static Long getUserId() {
        return getUser().getId();
    }

    /**
     * 在security中获取用户小程序ID
     * @return
     */
    public static String getOpenId() {
        return getUser().getOpenId();
    }
}
