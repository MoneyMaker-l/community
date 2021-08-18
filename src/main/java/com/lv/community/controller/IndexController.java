package com.lv.community.controller;

import com.lv.community.api.BaseResponse;
import com.lv.community.api.StatusCode;
import com.lv.community.dto.HotArticlDTO;
import com.lv.community.dto.PaginationDTO;
import com.lv.community.service.LikeService;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-07-17:16
 */

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;
    @Autowired
    private LikeService likeService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size" , defaultValue = "5") int size,
                        @RequestParam(value = "search" ,required = false) String search) {

        //显示发现  下面的问题  和  发布问题的 人
        PaginationDTO list = questionService.list(search,page,size);
        model.addAttribute("pagination",list);
        model.addAttribute("search",search);
        //查询热点文章
        List<HotArticlDTO> hotArticle = likeService.getHotArticle();
        model.addAttribute("hotArticle",hotArticle);
        return "index";
    }
    @ResponseBody
    @RequestMapping("/getHotArticle")
    public BaseResponse getHotArticle(){
        BaseResponse response = new BaseResponse(StatusCode.Succesee);
        List<HotArticlDTO> hotArticle = likeService.getHotArticle();

        response.setData(hotArticle);

        return response;
    }
}


