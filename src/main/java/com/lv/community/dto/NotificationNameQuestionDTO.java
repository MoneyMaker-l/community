package com.lv.community.dto;

import com.lv.community.pojo.Notification;
import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-07-01-11:51
 */
@Data
public class NotificationNameQuestionDTO {
    private String notifierName;
    private String questionName;
    private Notification notification;
    private Integer notificationId;
}
