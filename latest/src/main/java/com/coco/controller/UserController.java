package com.coco.controller;

import com.coco.entity.SysUser;
import com.coco.service.SysUserService;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return userService.findPage(queryInfo);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysUser user) {
        return userService.insert(user);
    }

    @ApiOperation(value = "修改用户信息")
    @PutMapping("/update")
    public Result update(@RequestBody SysUser user) {
        return userService.update(user);
    }

    @ApiOperation(value = "删除用户信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

}
