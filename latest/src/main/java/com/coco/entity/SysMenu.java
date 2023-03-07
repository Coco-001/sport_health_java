package com.coco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysMenu {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 前端路由
     */
    @ApiModelProperty(value = "前端路由")
    private String path;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    /**
     * 菜单标题
     */
    @ApiModelProperty(value = "菜单标题")
    private String title;
    /**
     * 前端组件
     */
    @ApiModelProperty(value = "前端组件")
    private String component;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private boolean status;

    /**
     *  - @JsonIgnore 不被序列化 前端就取不到
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentId;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;
}
