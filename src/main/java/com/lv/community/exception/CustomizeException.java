package com.lv.community.exception;

/**
 * @author lvjiangtao
 * @create 2021-06-28-10:43
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
    public CustomizeException(String message){
        this.message = message;
    }
}
