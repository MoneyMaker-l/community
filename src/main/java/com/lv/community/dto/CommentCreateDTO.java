package com.lv.community.dto;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-15-21:03
 */
@Data
public class CommentCreateDTO {
    private int question_id;
    private String content;
    private int type;
}
