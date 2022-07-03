package com.coco.sport.controller;

import com.alibaba.fastjson.JSON;
import com.coco.sport.dao.MenuDao;
import com.coco.sport.entity.MainMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/27 21:27
 * @ClassName: MenuController
 * @Description: 菜单控制
 * @Version: v1.0
 */
@RestController
public class MenuController {

    @Autowired
    MenuDao menuDao;

    @RequestMapping("/menus")
    public String getAllmenus() {
        HashMap<String, Object> data = new HashMap<>();
        List<MainMenu> menus = menuDao.getMenus();
        System.out.println(menus);
        if (menus != null) {
            data.put("menus", menus);
            data.put("status", 200);
        } else {
            data.put("status", 404);
        }
        return JSON.toJSONString(data);
    }
}
