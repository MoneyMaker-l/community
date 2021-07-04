package com.lv.community.pojo;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-07-01-9:46
 */
@Data
public class Notification {
    private int id;
    private int notifier;
    private int receiver;
    private int questionId;
    private long gmt_create;
    private int status;
}
