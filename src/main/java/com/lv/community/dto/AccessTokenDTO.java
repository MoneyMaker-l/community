package com.lv.community.dto;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-07-19:22
 */
@Data

//
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
