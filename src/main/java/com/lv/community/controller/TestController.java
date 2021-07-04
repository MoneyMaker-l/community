package com.lv.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lvjiangtao
 * @create 2021-06-28-8:40
 */
@Controller
public class TestController {

    @RequestMapping("/toTest")
    public String test(Model model){
        long l = System.currentTimeMillis();
        model.addAttribute("today",l);
        return "/Test";
    }
}
