package com.coco.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.coco.entity.Food;
import com.coco.entity.FoodType;
import com.coco.service.FoodService;
import com.coco.utils.QiniuUtils;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import com.coco.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private QiniuUtils qiniuUtils;

    @ApiOperation(value = "通过excel批量导入")
    @PostMapping("/batchImport")
    public Result batchImport(@RequestParam("file") MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            List<Food> list = ExcelImportUtil.importExcel(file.getInputStream(), Food.class, params);
            list.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getImageUrls())) {
                    try {
                        FileInputStream inputStream = new FileInputStream(item.getImageUrls());
                        String uuid = UUID.randomUUID().toString().substring(0, 7);
                        int index = item.getImageUrls().lastIndexOf(".");
                        String suffix = item.getImageUrls().substring(index);
                        String fileName = uuid + suffix;
                        String upload = qiniuUtils.upload(inputStream, fileName);
                        item.setImageUrls(upload);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return foodService.batchImport(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("食物列表导入失败！");
        }
    }

    @ApiOperation(value = "添加菜品分类")
    @PostMapping("/type/insert")
    public Result insertType(@RequestBody FoodType foodType) {
        return foodService.insertType(foodType);
    }

    @ApiOperation(value = "删除菜品分类")
    @DeleteMapping("/type/delete/{id}")
    public Result deleteType(@PathVariable Long id) {
        return foodService.deleteType(id);
    }

    @ApiOperation(value = "修改菜品分类")
    @PostMapping("/type/update")
    public Result updateType(@RequestBody FoodType foodType) {
        return foodService.updateType(foodType);
    }

    @ApiOperation(value = "分页查询菜品分类及其菜品信息")
    @PostMapping("/type/findPage")
    public Result findPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findPage(queryInfo);
    }

    @GetMapping("/typeAll")
    public Result typeAll() {
        return foodService.typeAll();
    }

    @PostMapping("/findPage")
    public Result findFoodPage(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodPage(queryInfo);
    }

    @ApiOperation(value = "小程序分页查询食物列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNumber", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(value = "页数大小", name = "pageSize", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(value = "食物类别", name = "typeId", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(value = "关键字", name = "keywords", dataTypeClass = String.class)
    })
    @PostMapping("/mini/findPage")
    public Result findMiniPage(@RequestBody JSONObject object) {
        return foodService.findMiniPage(object);
    }

    @ApiOperation(value = "添加菜品")
    @PostMapping("/insert")
    public Result insert(@RequestBody Food food) {
        return foodService.insert(food);
    }

    @ApiOperation(value = "修改菜品")
    @PostMapping("/update")
    public Result update(@RequestBody Food food) {
        return foodService.update(food);
    }

    @ApiOperation(value = "删除菜品")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return foodService.delete(id);
    }

    @ApiOperation(value = "根据食物类别查询食物")
    @PostMapping("/typeId")
    public Result findFoodByTypeId(@RequestBody QueryInfo queryInfo) {
        return foodService.findFoodByTypeId(queryInfo);
    }

    @GetMapping("/{id}")
    public Result foodInfo(@PathVariable Long id) {
        return foodService.findById(id);
    }

}
