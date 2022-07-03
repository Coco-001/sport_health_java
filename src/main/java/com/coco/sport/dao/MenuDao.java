package com.coco.sport.dao;

import com.coco.sport.entity.MainMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/27 21:02
 * @ClassName: MenuDao
 * @Description: Todo
 * @Version: v1.0
 */
@Repository
@Mapper
public interface MenuDao {
    public List<MainMenu> getMenus();
}
