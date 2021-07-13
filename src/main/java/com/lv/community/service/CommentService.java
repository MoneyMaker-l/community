package com.lv.community.service;

import com.lv.community.dto.Comment;
import com.lv.community.dto.CommentQueryDTO;
import com.lv.community.mapper.CommentMapper;
import com.lv.community.mapper.NotificationMapper;
import com.lv.community.mapper.QuestionMapper;
import com.lv.community.pojo.Notification;
import com.lv.community.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-27-15:48
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private NotificationMapper notificationMapper;


    @Transactional
    public void insert(Comment comment,int questionId) {

        Question question = questionMapper.findQuestionById(questionId);
        questionMapper.updateQuestionByComment(question);
        commentMapper.insert(comment);
        //加入通知  数据库
        Notification notification = new Notification();
        notification.setQuestionId(questionId);
        notification.setGmt_create(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        //接受者
        notification.setReceiver(question.getCreator());
        notificationMapper.addNotification(notification);

    }

    public List<CommentQueryDTO> findFirstComment(Integer id) {
        List<CommentQueryDTO> commentQueryDTO = commentMapper.findAllComment(id);
        return commentQueryDTO;
    }

    public List<CommentQueryDTO> findSecondaryComments(Integer id) {
        List<CommentQueryDTO> commentQueryDTOS = commentMapper.findSecondaryComments(id);
        return commentQueryDTOS;
    }
}
