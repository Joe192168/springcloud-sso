package com.joe.commons.exception;

/**
 * Token自定义异常类
 * @author Joe
 */
public class TokenAuthenticationException extends RuntimeException {

    private int code;

    private String msg;

    public TokenAuthenticationException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public TokenAuthenticationException(String message, int code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public TokenAuthenticationException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public TokenAuthenticationException(Throwable cause, int code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public TokenAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}