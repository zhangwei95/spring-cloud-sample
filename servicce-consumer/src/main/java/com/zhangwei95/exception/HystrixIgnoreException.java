package com.zhangwei95.exception;

/**
 * @Description: 描述
 * @Author: zhangwei
 * @Date: 2020/8/4 10:36
 */
public class HystrixIgnoreException extends RuntimeException {

    private String message;

    public HystrixIgnoreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
