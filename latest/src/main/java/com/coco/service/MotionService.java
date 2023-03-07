package com.coco.service;

import com.coco.entity.Motion;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;


public interface MotionService {

    /**
     * 分页查询
     * @param queryInfo 查询条件
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加运动信息
     * @param sport
     * @return
     */
    Result add(Motion sport);

    /**
     * 修改运动信息
     * @param sport
     * @return
     */
    Result edit(Motion sport);

    /**
     * 根据ID删除运动信息
     * @param id
     * @return
     */
    Result delete(Long id);

    Result findInfo(Long id);
}
