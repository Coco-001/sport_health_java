package com.coco.utils;

import lombok.Data;


@Data
public class QueryInfo {
    /**
     * 第几页
     */
    private Integer pageNumber;

    /**
     * 一页多少条数据
     */
    private Integer pageSize;

    /**
     * 查询的内容
     */
    private String queryString;
}
