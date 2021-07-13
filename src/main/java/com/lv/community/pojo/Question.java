package com.lv.community.pojo;

import lombok.Data;

import javax.swing.*;

/**
 * @author lvjiangtao
 * @create 2021-06-10-15:20
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private long gmt_create;
    private long gmt_modified;

}
