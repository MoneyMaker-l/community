package com.lv.community.controller;

import com.lv.community.dto.Comment;
import com.lv.community.dto.CommentCreateDTO;
import com.lv.community.dto.CommentQueryDTO;
import com.lv.community.dto.ResultDTO;
import com.lv.community.pojo.User;
import com.lv.community.service.CommentService;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-15-20:59
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //回复问题
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request
                      ){

        User user = (User) request.getSession().getAttribute("USER");
        Integer userId = user.getId();
//        if (user == null){
//            return ResultDTO.errorOf(2002,"未登录，请先登录");
//        }
        if (commentCreateDTO == null || commentCreateDTO.getContent() == null || commentCreateDTO.getContent() == ""){
            return ResultDTO.errorOf(2003,"輸入的內容不能为空");
        }


        Comment  comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(userId);
        int questionId = commentCreateDTO.getQuestion_id();
        comment.setQuestion_id(questionId);
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());

        commentService.insert(comment,questionId);

        return ResultDTO.okOf();

    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public Object comments(@RequestBody CommentCreateDTO commentCreateDTO){

        Comment  comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(9);
        int questionId = commentCreateDTO.getQuestion_id();
        comment.setQuestion_id(questionId);
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());

        commentService.insert(comment,questionId);

        return ResultDTO.okOf();
    }
}
