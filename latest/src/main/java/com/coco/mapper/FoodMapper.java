package com.coco.mapper;

import com.coco.entity.Food;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FoodMapper {

    /**
     * 分页查询菜品信息
     * @param queryString
     * @return
     */
    Page<Food> findPage(String queryString);

    /**
     * 根据菜品名称查询
     * @param title
     * @return
     */
    Food findByTitle(String title);

    /**
     * 添加菜品
     * @param food
     */
    void insert(Food food);

    /**
     * 删除菜品
     * @param id
     */
    void delete(Long id);

    /**
     * 更新菜品
     * @param food
     */
    void update(Food food);

    /**
     * 根据分类ID查询食物列表
     * @param id
     * @return
     */
    Page<Food> findByTypeId(String id);

    /**
     * 添加数组类型sql
     * @param foods
     */
    void insertList(@Param("foods") List<Food> foods);

    Page<Food> findMiniPage(@Param("typeId") Long typeId, @Param("keywords") String keywords);

    Food findById(Long id);
}
