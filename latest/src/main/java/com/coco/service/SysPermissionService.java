package com.coco.service;

import com.coco.entity.SysPermission;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;


public interface SysPermissionService {

    /**
     * 分页查询
     * @param queryInfo 页码、页数大小、查询内容
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 添加权限数据
     * @param permission
     * @return
     */
    Result insert(SysPermission permission);

    /**
     * 删除权限数据
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 修改权限数据
     * @param permission
     * @return
     */
    Result update(SysPermission permission);

    /**
     * 查询所有的权限数据
     * @return
     */
    Result findAll();
}
