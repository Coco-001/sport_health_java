package com.coco.service.impl;

import com.coco.entity.SysMenu;
import com.coco.mapper.SysMenuMapper;
import com.coco.service.SysMenuService;
import com.coco.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private RedisUtils redisUtil;

    @Override
    public Result findParent() {
        return Result.success("查询父级菜单成功", menuMapper.findParent());
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始数据分页-->页码{}, --->{}页数--->查询内容{}", queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<SysMenu> page = menuMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysMenu> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return new PageResult(total, result);
    }

    @Override
    public Result insert(SysMenu menu) {
        menuMapper.insert(menu);
        redisUtil.delKey("userInfo_" + SecurityUtils.getUsername());
        return Result.success("添加菜单数据成功");
    }

    @Override
    public Result delete(Long id) {
        menuMapper.delete(id);
        redisUtil.delKey("userInfo_" + SecurityUtils.getUsername());
        return Result.success("删除菜单数据成功");
    }

    @Override
    public Result update(SysMenu menu) {
        menuMapper.update(menu);
        redisUtil.delKey("userInfo_" + SecurityUtils.getUsername());
        return Result.success("修改菜单数据成功");
    }
}
