package com.coco.service;

import com.coco.entity.Goods;
import com.coco.utils.QueryInfo;
import com.coco.utils.Result;

import java.util.List;


public interface GoodsService {
    /**
     * 分页查询
     * @param queryInfo 查询条件
     * @return
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 查询所有商品信息
     * @return
     */
    List<Goods> findAll();

    /**
     * 添加商品信息
     * @param goods
     * @return
     */
    Result add(Goods goods);

    /**
     * 修改商品信息
     * @param goods
     * @return
     */
    Result edit(Goods goods);

    /**
     * 根据ID删除商品信息
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 批量导入数据
     * @param list
     * @return
     */
    Result batchImport(List<Goods> list);
}
