package com.coco.sport.dao;

import com.coco.sport.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/27 22:35
 * @ClassName: UserDao
 * @Description: 登录处理
 * @Version: v1.0
 */
@Repository
@Mapper
public interface UserDao {
    public User getUserByMassage(@Param("username") String username, @Param("password") String password);

    public List<User> getAllUser(@Param("username") String username, @Param("pageStart") int pageStart, @Param("pageSize") int pageSize);

    public int getUserCounts(@Param("username") String username);

    public int updateState(Integer id, Boolean state);

    public int addUser(User user);

    public int deleteUser(Integer id);

    public User getUser(Integer id);

    public int updateUser(User user);
}
