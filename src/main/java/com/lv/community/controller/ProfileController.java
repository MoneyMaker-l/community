package com.lv.community.controller;

import com.lv.community.dto.NotificationDTO;
import com.lv.community.dto.PaginationDTO;
import com.lv.community.pojo.User;
import com.lv.community.service.NotificationService;
import com.lv.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lvjiangtao
 * @create 2021-06-12-12:36
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/profile/{action}")
    public String profile(
            @PathVariable(name="action") String action,
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size" , defaultValue = "5") int size){
        User user = (User) request.getSession().getAttribute("USER");
        Integer notifier = user.getId();

        if ("questions".equals(action)){
            PaginationDTO paginationDTO = questionService.list(notifier,page, size);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("pagination",paginationDTO);
        } else if ("replies".equals(action)) {
            NotificationDTO notificationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
            model.addAttribute("pagination",notificationDTO);
        }
        return "profile";
    }
}
