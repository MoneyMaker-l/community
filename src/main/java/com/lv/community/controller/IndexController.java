package com.lv.community.controller;

import com.lv.community.dto.PaginationDTO;
import com.lv.community.mapper.UserMapper;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvjiangtao
 * @create 2021-06-07-17:16
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size" , defaultValue = "5") int size,
                        @RequestParam(value = "search" ,required = false) String search) {

        //显示发现  下面的问题  和  发布问题的 人
        PaginationDTO list = questionService.list(search,page,size);
        model.addAttribute("pagination",list);
        model.addAttribute("search",search);

        return "index";
    }

}


