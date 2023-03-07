package com.coco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class FoodType {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("分类标题")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("分类下的食物")
    private List<Food> foods;
}
