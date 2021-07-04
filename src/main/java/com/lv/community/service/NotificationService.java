package com.lv.community.service;

import com.lv.community.dto.NotificationDTO;
import com.lv.community.dto.NotificationNameQuestionDTO;
import com.lv.community.mapper.NotificationMapper;
import com.lv.community.mapper.QuestionMapper;
import com.lv.community.mapper.UserMapper;
import com.lv.community.pojo.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-07-01-10:55
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    //重载有用户时  的查询
    public NotificationDTO list(Integer creator, int page, int size){

        NotificationDTO notificationDTO = new NotificationDTO();
        Integer totalCount = notificationMapper.selectAllNotification(creator);

        notificationDTO.setNewReply(totalCount);
        //调用 setPagenation 方法
        notificationDTO.setPagenation(totalCount,size,page);

        //传入  -1  或者  比totalPage 大的数时，在查询数据库之前 将page设为相应的值
        if (page < 1){
            page = 1;
        }
        if(notificationDTO.getTotalPage()> 0) {
            //totalCount 可能为 0  就会有错 外面加一层 判断
            if (page > notificationDTO.getTotalPage()) {
                page = notificationDTO.getTotalPage();
            }
        }
        //offset 是 limit 中的第一个元素
        Integer offset = size*(page - 1);

        List<NotificationNameQuestionDTO> list = new ArrayList<>();
        List<Notification> notifications = notificationMapper.selectNotification(offset,size);
        for (Notification notification : notifications) {
            NotificationNameQuestionDTO nameQuestionDTO = new NotificationNameQuestionDTO();
            int notifier = notification.getNotifier();
            String notifierName = userMapper.selectNotificationName(notifier);
            int questionId = notification.getQuestionId();
            String questionName = questionMapper.selectNotifierName(questionId);
            nameQuestionDTO.setQuestionName(questionName);
            //回复的 id
            int notificationId = notification.getId();
            nameQuestionDTO.setNotificationId(notificationId);
            nameQuestionDTO.setNotifierName(notifierName);
            nameQuestionDTO.setNotification(notification);
            list.add(nameQuestionDTO);
        }

        notificationDTO.setNotificationNameQuestionDTOS(list);
        return  notificationDTO;
    }
}
