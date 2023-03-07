package com.coco.mapper;

import com.coco.entity.Sport;
import com.coco.entity.WxRun;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SportMapper {

    /**
     * 添加
     * @param sport
     */
    @Insert("insert into sport(title, content, create_time, create_name) values (#{title}, #{content}, #{createTime}, #{createName})")
    void insert(Sport sport);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 删除
     * @param id
     */
    Sport findInfo(Long id);

    /**
     * 修改
     * @param sport
     */
    void update(Sport sport);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Sport> findPage(String queryString);

    /**
     * 添加微信运动步数
     * @param runs
     */
    void insertStep(@Param("runs") List<WxRun> runs);

    /**
     * 添加微信运动步数
     * @param openid 用户唯一标志
     * @param time 时间
     */
    @Select("select * from wx_run where openid=#{openid} and time=#{time}")
    WxRun findStepByTime(@Param("openid") String openid, @Param("time") String time);

    @Update("update wx_run set step=#{step} where openid=#{openid} and time=#{time}")
    void updateStep(@Param("openid") String openid, @Param("time") String time, @Param("step") Integer step);

    @Select("select * from wx_run where openid=#{openid} and (time<=#{beginTime} and time>=#{endTime})")
    List<WxRun> findByTime(@Param("beginTime") String dateTime, @Param("endTime") String date1, @Param("openid") String openId);
}
