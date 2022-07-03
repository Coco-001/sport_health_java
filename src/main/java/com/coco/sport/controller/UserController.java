package com.coco.sport.controller;

import com.alibaba.fastjson.JSON;
import com.coco.sport.dao.UserDao;
import com.coco.sport.entity.QueryInfo;
import com.coco.sport.entity.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/30 20:12
 * @ClassName: UserController
 * @Description: 角色控制
 * @Version: v1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/allUser")
    public String getUserList(QueryInfo queryInfo) {
        //用户总数
        int userCounts = userDao.getUserCounts("%" + queryInfo.getQuery() + "%");

        //从第几页开始
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();

        //查询用户列表，@用户名模糊查询，@开始页，@页面大小
        List<User> userList = userDao.getAllUser("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());

        Map<String, Object> res = new HashMap<>();

        res.put("userCounts", userCounts);
        res.put("userList", userList);

        return JSON.toJSONString(res);
    }

    @RequestMapping("/userState")
    public String updateState(@RequestParam("id") Integer id, @RequestParam("state") Boolean state) {
        int res = userDao.updateState(id, state);
        return res > 0 ? "success" : "error";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        user.setRole("普通用户");
        user.setState(false);
        int i = userDao.addUser(user);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Integer id) {
        int i = userDao.deleteUser(id);
        return i > 0 ? "success" : "error";
    }

    //这里获取User只是为了给前端进行展示，单纯更新完全不需要这一步操作
    @RequestMapping("/getUser")
    public String getUser(@RequestParam("id") Integer id) {
        return JSON.toJSONString(userDao.getUser(id));
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        int i = userDao.updateUser(user);
        return i > 0 ? "success" : "error";
    }
}
