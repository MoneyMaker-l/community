package com.lv.community.pojo;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-09-18:23
 */
@Data
public class User {

    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private String avatar_url;

}
