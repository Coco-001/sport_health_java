package com.coco.service.impl;

import com.coco.entity.Goods;
import com.coco.mapper.GoodsMapper;
import com.coco.service.GoodsService;
import com.coco.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Result findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Goods> page = goodsMapper.findPage(queryInfo.getQueryString());
        if (page == null) {
            return Result.fail("查询失败");
        }
        long total = page.getTotal();
        List<Goods> result = page.getResult();
        return PageResult.pageRsult(total, result);
    }

    @Override
    public Result add(Goods goods) {
        Goods good = goodsMapper.findByName(goods.getName());
        if (good != null) {
            return Result.fail("商品已存在，请勿重复添加");
        }
        int i = goodsMapper.insert(goods);
        if (i <= 0) {
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @Override
    public Result delete(Long id) {
        if (id == null) {
            return Result.fail("商品ID不能为空");
        }
        int i = goodsMapper.delete(id);
        if (i <= 0) {
            return Result.fail("删除失败");
        }
        return Result.success("删除成功");
    }

    @Override
    public Result edit(Goods goods) {
        int update = goodsMapper.update(goods);
        if (update <= 0) {
            return Result.fail("修改失败");
        }
        return Result.success("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result batchImport(List<Goods> list) {
        int i = 0,j = 0;
        for (Goods goods : list) {
            goods.setCreateUserId(SecurityUtils.getUserId());
            Goods byName = goodsMapper.findByName(goods.getName());
            if (byName != null) {
                goods.setId(byName.getId());
                goods.setRemark("管理员：" + SecurityUtils.getUsername() + "于" + DateUtils.getDateTime() + " --> 更新了商品:" + goods.getName() + "；目前数量：" + goods.getNumber());
                i += goodsMapper.update(goods);
            } else {
                goods.setCreateTime(new Date());
                goods.setRemark("管理员：" + SecurityUtils.getUsername() + "于" + DateUtils.getDateTime() + "将商品：" + goods.getName() + "加入库存，数量：" + goods.getNumber());
                j += goodsMapper.insert(goods);
            }
        }
        if (i <= 0 && j <= 0) {
            return Result.fail("商品导入失败");
        }
        return Result.success("商品导入成功");
    }

    @Override
    public List<Goods> findAll() {
        return goodsMapper.findAll();
    }
}
