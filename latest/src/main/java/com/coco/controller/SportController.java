package com.coco.controller;

import com.coco.entity.Sport;
import com.coco.service.SportService;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sport")
public class SportController {

    @Autowired
    private SportService sportService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return sportService.findPage(queryInfo);
    }

    @ApiOperation(value = "删除运动咨询信息")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return sportService.delete(id);
    }

    @ApiOperation(value = "添加运动咨询信息")
    @PostMapping("/insert")
    public Result insert(@RequestBody Sport sport) {
        return sportService.insert(sport);
    }

    @ApiOperation(value = "修改运动咨询信息")
    @PutMapping("/update")
    public Result update(@RequestBody Sport sport) {
        return sportService.update(sport);
    }

    @ApiOperation(value = "获取详细信息")
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Long id) {
        return sportService.findInfo(id);
    }

}
