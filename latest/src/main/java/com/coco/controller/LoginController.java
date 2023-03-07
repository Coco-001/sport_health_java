package com.coco.controller;

import com.coco.service.SysUserService;
import com.coco.utils.RedisUtils;
import com.coco.utils.Result;
import com.coco.utils.SecurityUtils;
import com.coco.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class LoginController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    @ApiOperation(value = "短信登录")
    @PostMapping("/sms/login")
    public Result smsLogin(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/getInfo")
    public Result getUserInfo() {
        return Result.success("获取用户信息成功", SecurityUtils.getUser());
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public Result logout() {
        // 清除缓存
        redisUtils.delKey("userInfo_" + SecurityUtils.getUsername());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("退出成功！");
    }

}
