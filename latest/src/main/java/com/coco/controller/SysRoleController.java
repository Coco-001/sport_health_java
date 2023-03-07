package com.coco.controller;

import com.coco.entity.SysRole;
import com.coco.service.SysRoleService;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @GetMapping("/findAll")
    @ApiOperation(value = "添加用户时角色信息列表")
    public Result findAll() {
        return roleService.findAll();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return roleService.findPage(queryInfo);
    }

    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return roleService.delete(id);
    }

    @ApiOperation(value = "添加角色信息")
    @PostMapping("/insert")
    public Result insert(@RequestBody SysRole role) {
        return roleService.insert(role);
    }

    @ApiOperation(value = "修改角色信息")
    @PutMapping("/update")
    public Result update(@RequestBody SysRole role) {
        return roleService.update(role);
    }
}
