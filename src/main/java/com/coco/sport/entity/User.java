package com.coco.sport.entity;

import lombok.Data;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/22 16:48
 * @ClassName: User
 * @Description: 用户实体
 * @Version: v1.0
 */
@Data
public class User {

    private int id;

    private String username;

    private String password;

    private String email;

    private String role;

    private boolean state;

}
