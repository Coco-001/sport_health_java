package com.coco.mapper;

import com.coco.entity.SysMenu;
import com.coco.entity.SysPermission;
import com.coco.entity.SysRole;
import com.coco.entity.SysUser;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface SysUserMapper {

    /**
     * 根据用户名获取用户对象
     * @param value
     * @return
     */
    SysUser findByUsername(@Param("value") String value);

    /**
     * 根据用户ID查询权限信息
     * @param userId
     * @return
     */
    List<SysRole> findRoles(@Param("userId") Long userId);

    /**
     * 根据用户ID查询菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> findMenus(@Param("userId") Long userId);

    /**
     * 根据父级ID和用户ID查询子级菜单
     * @param id 父级ID
     * @param userId 用户ID
     * @return
     */
    List<SysMenu> findChildrenMenu(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 根据用户ID查询权限数据
     * @param userId
     * @return
     */
    List<SysPermission> findPermissions(@Param("userId") Long userId);

    /**
     * 分页查询用户信息
     * @param queryString 分页查询字符串
     * @return 返回分页数据
     */
    Page<SysUser> findPage(String queryString);

    /**
     * 添加用户信息
     * @param user 用户信息
     */
    void insert(SysUser user);

    /**
     * 微信小程序进入添加信息
     * @param openid 微信小程序唯一标识
     */
    @Insert("insert into sys_user(open_id, status) values (#{openid}, 0)")
    void insertOpenid(@Param("openid") String openid);

    /**
     * 修改用户信息
     * @param user 用户信息
     */
    void update(SysUser user);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 根据用户信息中的角色列表，去添加用户的角色
     * @param userId
     * @param roleId
     */
    void insertUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据用户ID去删除角色列表
     * @param userId
     */
    void deleteRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询用户的基本信息
     * @param id
     * @return
     */
    SysUser findById(Long id);

    /**
     * 根据邮件修改密码
     * @param email 邮件
     * @param password 新密码
     */
    @Update("update sys_user set `password` = #{password} where email = #{email}")
    void updatePwdByMail(@Param("email") String email, @Param("password") String password);

    /**
     * 根据openid更新用户信息
     * @param user
     */
    void updateByopenId(SysUser user);
}
