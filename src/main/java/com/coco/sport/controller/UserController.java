package com.coco.sport.controller;

import com.alibaba.fastjson.JSON;
import com.coco.sport.dao.UserDao;
import com.coco.sport.entity.QueryInfo;
import com.coco.sport.entity.User;
import com.coco.sport.util.RedisUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
//
//
//    @Resource
//    private UserDao userDao;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @RequestMapping("/allUser")
//    public String getUserList(QueryInfo queryInfo) {
//
//        //用户总数
//        //int userCounts = userDao.getUserCounts("%" + queryInfo.getQuery() + "%");
//        //先从从redis中查询，如果查询结果为空，再从数据库中查询，把结果存入redis
//        Integer userCounts = (Integer) redisUtil.get("userCounts");
//        if (null == userCounts) {
//            userCounts = userDao.getUserCounts("%" + queryInfo.getQuery() + "%");
//            redisUtil.set("userCounts", userCounts);
//        }
//
//        //从第几页开始
//        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
//
//        //查询用户列表，@用户名模糊查询，@开始页，@页面大小
//        //List<User> userList = userDao.getAllUser("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
//        //先从从redis中查询，如果查询结果为空，再从数据库中查询，把结果存入redis
//        List<User> userList = (List<User>) redisUtil.get("allUser");
//        if (CollectionUtils.isEmpty(userList)) {
//            userList = userDao.getAllUser("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
//            redisUtil.set("allUser", userList);
//            //单个用户数据也存一份
//            for (User user : userList) redisUtil.set(String.valueOf(user.getId()), user);
//        }
//
//        Map<String, Object> res = new HashMap<>();
//
//        res.put("userCounts", userCounts);
//        res.put("userList", userList);
//
//        return JSON.toJSONString(res);
//    }
//
//    @RequestMapping("/userState")
//    public String updateState(@RequestParam("id") Integer id, @RequestParam("state") Boolean state) {
//        int res = userDao.updateState(id, state);
//        return res > 0 ? "success" : "error";
//    }
//
//    @RequestMapping("/addUser")
//    public String addUser(@RequestBody User user) {
//        user.setRole("普通用户");
//        user.setState(false);
//        int i = userDao.addUser(user);
//        List<User> userList = (List<User>) redisUtil.get("allUser");
//        userList.add(user);
//        redisUtil.set("allUser", userList);
//        redisUtil.set(String.valueOf(user.getId()), user);
//        return i > 0 ? "success" : "error";
//    }
//
//    @RequestMapping("/deleteUser")
//    public String deleteUser(@RequestParam("id") Integer id) {
//        //从数据库中删除
//        int i = userDao.deleteUser(id);
//        //从缓存中移除
//        User user = (User) redisUtil.get(String.valueOf(id));
//        List<User> userList = (List<User>) redisUtil.get("allUser");
//        userList.remove(userList.indexOf(user));
//        redisUtil.del(String.valueOf(id));
//        redisUtil.set("allUser", user);
//        return i > 0 ? "success" : "error";
//    }

//    @RequestMapping("/getUser")
//    public String getUser(@RequestParam("id") Integer id) {
//        //从缓存查
//        User user = (User) redisUtil.get(String.valueOf(id));
//        //缓存中没有
//        if (null == user) {
//            //从数据库查
//            user = userDao.getUser(id);
//            //写入缓存
//            redisUtil.set(String.valueOf(user.getId()), user);
//        }
//        return JSON.toJSONString(user);
//    }

//    @RequestMapping("/updateUser")
//    public String updateUser(@RequestBody User user) {
//        User u = (User) redisUtil.get(String.valueOf(user.getId()));
//        List<User> userList = (List<User>) redisUtil.get("allUser");
//        Integer idx = userList.indexOf(u);
//        //更新写入缓存
//        redisUtil.set(String.valueOf(user.getId()), user);
//        userList.set(idx, user);
//        redisUtil.set("allUser", userList);
//        //更新写入数据库
//        int i = userDao.updateUser(user);
//        return i > 0 ? "success" : "error";
//    }
//
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
