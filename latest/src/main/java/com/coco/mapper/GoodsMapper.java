package com.coco.mapper;

import com.coco.entity.Goods;
import com.github.pagehelper.Page;

import java.util.List;


public interface GoodsMapper {

    /**
     * 数据查询
     * @param queryString 根据字符串查询
     * @return
     */
    Page<Goods> findPage(String queryString);

    /**
     * 查询所有商品
     * @return
     */
    List<Goods> findAll();
    /**
     * 修改商品信息
     * @param goods 商品参数
     * @return
     */
    int update(Goods goods);

    /**
     * 根据商品编号查询商品信息
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 添加商品
     * @param goods
     * @return
     */
    int insert(Goods goods);

    /**
     * 根据商品名称查询商品信息
     * @param name
     * @return
     */
    Goods findByName(String name);

    /**
     * 根据编号删除商品信息
     * @param id
     * @return
     */
    int delete(Long id);
}
