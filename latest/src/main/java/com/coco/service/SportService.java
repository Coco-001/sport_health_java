package com.coco.service;

import com.coco.entity.Sport;
import com.coco.entity.WxRun;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;

import java.util.List;


public interface SportService {
    /**
     * 删除菜品
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    Result findInfo(Long id);

    /**
     * 修改菜品
     * @param sport
     * @return
     */
    Result update(Sport sport);

    /**
     * 添加菜品
     * @param sport
     * @return
     */
    Result insert(Sport sport);

    /**
     * 分页查询菜品信息
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    Result insertStep(List<WxRun> runs);

    Result stepReport();

}
