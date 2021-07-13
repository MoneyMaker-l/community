package com.lv.community.controller;

import com.lv.community.dto.CommentQueryDTO;
import com.lv.community.dto.QuestionDTO;
import com.lv.community.mapper.NotificationMapper;
import com.lv.community.pojo.Question;
import com.lv.community.service.CommentService;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-14-9:52
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationMapper notificationMapper;

    @GetMapping(value = {"/question/{id}","/question/{id}/{notificationId}"})
    public String question(@PathVariable(name = "id") Integer id,
                           @PathVariable(name = "notificationId" ,required = false) Integer notificationId,
                           Model model){

        questionService.incView(id);
        //设为已读
        notificationMapper.updateStatus(notificationId);

        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("question",questionDTO);

        List<Question> relativeQuestions = questionService.selectRelativeQuestion(id);
        model.addAttribute("relativeQuestions",relativeQuestions);

        //根据 question_id查
        List<CommentQueryDTO> commentDTO = commentService.findFirstComment(id);
        model.addAttribute("commentDTO",commentDTO);

        return "question";
    }
}
