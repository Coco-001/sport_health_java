package com.coco.sport.entity;

import lombok.Data;

/**
 * @Author: Mr.Shan
 * @Date: 2022/6/30 20:14
 * @ClassName: QueryInfo
 * @Description: 封装查询参数
 * @Version: v1.0
 */
@Data
public class QueryInfo {

    //查询username
    private String query;

    //当前页码
    private int pageNum = 1;

    //每页最大值
    private int pageSize = 1;

}
