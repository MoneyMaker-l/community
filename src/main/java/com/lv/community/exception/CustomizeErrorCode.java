package com.lv.community.exception;

/**
 * @author lvjiangtao
 * @create 2021-06-28-11:13
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不存在了，要不换个试试");

    private String message;
    private Integer code;

    CustomizeErrorCode(int code, String message) {
        this.code=code;
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
