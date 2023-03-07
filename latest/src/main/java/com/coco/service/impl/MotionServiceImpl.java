package com.coco.service.impl;

import com.coco.entity.Motion;
import com.coco.mapper.MotionMapper;
import com.coco.service.MotionService;
import com.coco.utils.PageResult;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;
import com.coco.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MotionServiceImpl implements MotionService {

    @Autowired
    private MotionMapper motionMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Motion> page = motionMapper.findPage(queryInfo.getQueryString());
        if (page == null) {
            return Result.fail("查询失败");
        }
        long total = page.getTotal();
        List<Motion> result = page.getResult();
        return PageResult.pageRsult(total, result);
    }

    @Override
    public Result add(Motion sport) {
        Motion name = motionMapper.findByName(sport.getName());
        if (StringUtils.isNotEmpty(name)) {
            return Result.fail("运动项目已存在，请勿重复添加");
        }
        if (motionMapper.insert(sport) <= 0) {
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public Result edit(Motion sport) {
        int update = motionMapper.update(sport);
        if (update <= 0) {
            return Result.fail("修改失败");
        }
        return Result.success("修改成功");
    }

    @Override
    public Result delete(Long id) {
        if (id == null) {
            return Result.fail("ID不能为空");
        }
        if (motionMapper.delete(id) <= 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result findInfo(Long id) {
        if (StringUtils.isEmpty(id)) {
            return Result.fail("ID不能为空");
        }
        return Result.success("查询成功", motionMapper.findById(id));
    }
}
