package com.lv.community.dto;

import com.lv.community.pojo.User;
import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-10-21:08
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private User user;
}
