package com.coco.service.impl;

import com.coco.entity.Food;
import com.coco.entity.FoodType;
import com.coco.mapper.FoodMapper;
import com.coco.mapper.FoodTypeMapper;
import com.coco.service.FoodService;
import com.coco.utils.PageResult;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodTypeMapper foodTypeMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public Result delete(Long id) {
        foodMapper.delete(id);
        return Result.success("食物删除成功");
    }

    @Override
    public Result update(Food food) {
        foodMapper.update(food);
        return Result.success("食物修改成功");
    }

    @Override
    public Result insert(Food food) {
        foodMapper.insert(food);
        return Result.success("食物添加成功");
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<FoodType> page = foodTypeMapper.findPage(queryInfo.getQueryString());
        return PageResult.pageRsult(page.getTotal(), page.getResult());
    }

    @Override
    public Result findFoodPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Food> page = foodMapper.findPage(queryInfo.getQueryString());
        return PageResult.pageRsult(page.getTotal(), page.getResult());
    }

    @Override
    public Result updateType(FoodType foodType) {
        foodTypeMapper.update(foodType);
        return Result.success("菜品分类更新成功");
    }

    @Override
    public Result deleteType(Long id) {
        foodTypeMapper.delete(id);
        return Result.success("菜品分类删除成功");
    }

    @Override
    public Result insertType(FoodType foodType) {
        foodTypeMapper.insert(foodType);
        return Result.success("菜品分类添加成功");
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result batchImport(List<Food> list) {
        List<Food> foods = new ArrayList<>();
        //1. 读取分类
        for (Food food : list) {
            FoodType foodType = foodTypeMapper.findByTitle(food.getTypeTitle());
            Food title = foodMapper.findByTitle(food.getTitle());
            if (null != foodType) {
                //修改
                if (title != null) {
                    foodMapper.update(food);
                } else {
                    //添加
                    food.setTypeId(foodType.getId());
                    foods.add(food);
                }
            }
        }
        foodMapper.insertList(foods);
        return Result.success("批量导入成功！");
    }

    @Override
    public Result typeAll() {
        return Result.success("分类查询成功！", foodTypeMapper.typeAll());
    }

    @Override
    public Result findFoodByTypeId(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        String queryString = queryInfo.getQueryString();
        Page<Food> foods = foodMapper.findByTypeId(queryString);
        return Result.success("食物查询成功！", new PageResult(foods.getTotal(), foods.getResult()));
    }

    @Override
    public Result findMiniPage(JSONObject object) {
        PageHelper.startPage(object.getInteger("pageNumber"), object.getInteger("pageSize"));
        Long typeId = object.getLong("typeId");
        String keywords = object.getString("keywords");
        Page<Food> foods = foodMapper.findMiniPage(typeId, keywords);
        return PageResult.pageRsult(foods.getTotal(), foods.getResult());
    }

    @Override
    public Result findById(Long id) {
        if (id == null) {
            return Result.fail("请传递食物编号");
        }
        return Result.success("食物信息查询成功", foodMapper.findById(id));
    }
}
