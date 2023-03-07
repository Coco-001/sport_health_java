package com.coco.entity;

import lombok.Data;

@Data
public class Motion {

    /**
     * 主键
     */
    private Long id;
    /**
     * 运动名称
     */
    private String name;
    /**
     * 适用年龄
     */
    private String applicableAge;
    /**
     * 身体收益部位
     */
    private String beneficialPosition;
    /**
     * 运动科普
     */
    private String introduction;

}
