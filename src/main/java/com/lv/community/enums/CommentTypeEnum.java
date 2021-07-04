package com.lv.community.enums;

/**
 * @author lvjiangtao
 * @create 2021-06-28-9:51
 */

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType(Integer type){
        return type;
    }
    CommentTypeEnum(Integer type){
        this.type = type;
    }
}
