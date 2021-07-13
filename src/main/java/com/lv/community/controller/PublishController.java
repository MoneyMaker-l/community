package com.lv.community.controller;

import com.lv.community.dto.QuestionDTO;
import com.lv.community.pojo.Question;
import com.lv.community.pojo.User;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvjiangtao
 * @create 2021-06-10-11:24
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO questionDTO = questionService.findQuestionById(id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        model.addAttribute("id",questionDTO.getId());

        return "/publish";
    }

    @PostMapping("/publish/edit")
    public String doPublish(
           @RequestParam( value = "title", required = false ) String title,
           @RequestParam(value = "description", required = false) String description,
           @RequestParam(value = "tag" , required = false) String tag,
           @RequestParam(value = "id",required = false) Integer id,
           HttpServletRequest request,
           Model model
    ){
        System.out.println("doPublish------->");

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title ==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("USER");
        if (user == null){
            return "redirect:/";
        }
        Integer creatorId = user.getId();

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(creatorId);
        question.setId(id);

        questionService.createOrUpdate(question);


        return "redirect:/";
    }
}
