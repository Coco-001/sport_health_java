package com.coco.sport.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/22 20:58
 * @ClassName: MainMenu
 * @Description: 主菜单
 * @Version: v1.0
 */
@Data
public class MainMenu {

    private int id;

    private String title;

    private String path;

    private List<SubMenu> sList;
}
