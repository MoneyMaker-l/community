package com.lv.community.dto;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-27-16:15
 */
@Data
public class CommentQueryDTO {
    private int id;
    private int type ;
    private long gmt_create;
    private String content;
    private String userName;
    private String avatar_url;
}
