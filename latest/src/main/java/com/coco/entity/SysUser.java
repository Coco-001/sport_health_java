package com.coco.entity;

import com.coco.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
public class SysUser implements UserDetails {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "前端展示的用户名")
    private String name;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "登录密码")
    private String password;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "微信唯一ID")
    private String openId;
    @ApiModelProperty(value = "当前状态")
    private boolean status;
    @ApiModelProperty(value = "是否是管理员")
    private boolean admin;
    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "角色信息")
    private List<SysRole> roles;

    @ApiModelProperty(value = "用户对应的菜单列表")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "用户对应的权限数据")
    private List<SysPermission> permissions;

    /**
     * 权限数据
     * @JsonIgnore 在返回的数据中，将该方法对应的属性数据给排除
     * @return
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            roles.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getCode())) {
                    list.add(new SimpleGrantedAuthority("ROLE_" + item.getCode()));
                }
            });
        }
        if (permissions != null && permissions.size() > 0) {
            permissions.forEach(item -> {
                if (StringUtils.isNotEmpty(item.getCode())) {
                    list.add(new SimpleGrantedAuthority(item.getCode()));
                }
            });
        }
        return list;
    }

    /**
     * 用户名
     * @return
     */
    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    /**
     * 账号是否过期
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 账号是否被锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 是否被禁用
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status;
    }
}
