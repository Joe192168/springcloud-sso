package com.joe.commons.vo;

import lombok.Data;

/**
 * @description: 公共返回json实体消息
 * @author: Joe
 **/
@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result from(String code, String msg, T data) {
        return new Result(code, msg, data);
    }
}