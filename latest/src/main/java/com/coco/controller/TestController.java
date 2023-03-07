package com.coco.controller;

import com.coco.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "测试接口")
public class TestController {

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @ApiOperation(value = "测试test")
    @GetMapping("/test")
    public Result test() {
        return Result.success("信息返回成功", "你好！");
    }
}
