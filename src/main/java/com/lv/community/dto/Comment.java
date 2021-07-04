package com.lv.community.dto;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-15-21:04
 */
@Data
public class Comment {
    private int id;
    private  int question_id;
    private int type;
    private  int commentator;
    private  long gmt_create;
    private  long gmt_modified;
    private  int like_count;
    private  String content;
}
