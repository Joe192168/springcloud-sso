package com.joe.commons.vo;

import com.joe.commons.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @description: 公共返回json实体消息
 * @author: Joe
 **/
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(){
        return new Result(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> success(T data){
        return new Result(ResponseCodeEnum.SUCCESS.getCode(),ResponseCodeEnum.SUCCESS.getMessage(),data);
    }

    public static Result error(int code, String msg){
        return new Result(code,msg);
    }

    public static <T> Result<T> error(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

}