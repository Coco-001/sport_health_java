package com.coco.mapper;

import com.coco.entity.SysPermission;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPermissionMapper {

    /**
     * 添加权限信息
     * @param permission 权限数据
     */
    void insert(SysPermission permission);

    /**
     * 修改权限信息
     * @param permission 权限数据
     */
    void update(SysPermission permission);

    /**
     * 删除权限数据
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysPermission> findPage(String queryString);

    /**
     * 根据角色ID查询该角色下的权限信息
     * @param roleId
     * @return
     */
    List<SysPermission> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询所有的权限数据
     * @return
     */
    List<SysPermission> findAll();
}
