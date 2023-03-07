package com.coco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SysPermission {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 数据权限标签名称
     */
    @ApiModelProperty(value = "数据权限标签名称")
    private String label;

    /**
     * 数据权限标签值
     */
    @ApiModelProperty(value = "数据权限标签值")
    private String code;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private boolean status;
}
