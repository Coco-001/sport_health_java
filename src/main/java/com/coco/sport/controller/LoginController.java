package com.coco.sport.controller;

import com.alibaba.fastjson.JSON;
import com.coco.sport.dao.UserDao;
import com.coco.sport.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/26 23:03
 * @ClassName: LoginController
 * @Description: 用户登录
 * @Version: v1.0
 */
@RestController
public class LoginController {
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/login")
    public String userLogin(@RequestBody User user) {
        Map<String, Object> res = new HashMap<>();
        User u = userDao.getUserByMassage(user.getUsername(), user.getPassword());
        String flag = "error";
        if (u != null) flag = "ok";
        res.put("flag", flag);
        res.put("user", u);
        return com.alibaba.fastjson.JSON.toJSONString(res);
    }
}
