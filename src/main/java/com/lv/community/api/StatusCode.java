package com.lv.community.api;

/**
 * 状态码的枚举类
 * @author lvjiangtao
 * @create 2021-08-16-11:56
 */
public enum StatusCode {
    Succesee(0,"成功"),
    Fail(-1,"失败"),
    InvalidParams(201,"非法参数")
    ;
    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
