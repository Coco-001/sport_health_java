package com.coco.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "响应参数")
public class Result implements Serializable {
    /**
     * 响应给前端是否成功的标识
     */
    @ApiModelProperty(value = "是否成功标识", dataType = "boolean")
    private boolean flag;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息", dataType = "String")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据", dataType = "Object")
    private Object data;

    public Result() {
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    /**
     * 响应成功的结果
     * @param message
     * @param data
     * @return
     */
    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    /**
     * 响应成功的结果
     * @param message
     * @return
     */
    public static Result success(String message) {
        return new Result(true, message);
    }

    /**
     * 响应失败的结果
     * @param message
     * @return
     */
    public static Result fail(String message) {
        return new Result(false, message);
    }
}
