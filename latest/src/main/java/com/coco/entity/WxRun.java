package com.coco.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxRun {

    private String openid;

    private String time;

    private Integer step;

}
