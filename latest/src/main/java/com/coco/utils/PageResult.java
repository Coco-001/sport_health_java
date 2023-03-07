package com.coco.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult extends Result implements Serializable {

    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private long total;

    /**
     * 分页的数据
     */
    @ApiModelProperty(value = "分页的数据列表")
    private List<?> rows;

    public PageResult(long total, List<?> list) {
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.total = total;
        this.rows = list;
    }

    /**
     * 直接返回分页数据
     * @param total 分页总条数
     * @param list 分页数据列表
     * @return
     */
    public static Result pageRsult(long total, List<?> list) {
        return new PageResult(total, list);
    }
}
