package com.coco.mapper;

import com.coco.entity.SysRole;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysRoleMapper {

    /**
     * 添加角色信息
     * @param role 角色数据
     */
    void insert(SysRole role);

    /**
     * 修改角色信息
     * @param role 角色数据
     */
    void update(SysRole role);

    /**
     * 删除角色数据
     * @param id
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<SysRole> findPage(String queryString);

    /**
     * 根据角色ID查询出角色信息
     * @param id
     * @return
     */
    SysRole findById(Long id);

    /**
     * 根据角色ID删除对应的菜单数据
     * @param roleId
     */
    void deleteMenuById(@Param("roleId") Long roleId);

    /**
     * 根据角色ID删除对应的权限信息
     * @param roleId
     */
    void deletePermissionById(@Param("roleId") Long roleId);

    /**
     * 添加角色的权限信息
     * @param roleId 角色ID
     * @param permissionId 权限ID
     */
    void insertPermissions(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * 添加角色的菜单信息
     * @param roleId 角色ID
     * @param menuId 菜单ID
     */
    void insertMenus(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 根据角色名称查询是否存在角色信息
     * @param lable
     * @return
     */
    SysRole findByLabel(String lable);

    /**
     * 查询所有的角色信息
     * @return
     */
    List<SysRole> findAll();
}
