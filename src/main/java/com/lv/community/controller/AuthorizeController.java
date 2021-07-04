package com.lv.community.controller;

import com.lv.community.dto.AccessTokenDTO;
import com.lv.community.dto.GithubUser;
import com.lv.community.pojo.User;
import com.lv.community.provider.GithubProvider;
import com.lv.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lvjiangtao
 * @create 2021-06-07-19:06
 */

@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    @Value("${github.redirect.uri}")
    private String redirectUrl;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;



    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code" )String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser);


        if (githubUser != null && githubUser.getId() != null){
            //登录成功，存到session中，重定向到index 页面
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatar_url(githubUser.getAvatar_url());
            userService.createOrUpdate(user);

            request.getSession().setAttribute("USER",user);

            //解决服务器重启需要重新登录的问题-通过在数据库中创建一个token 标识来每次判断用户信息
            response.addCookie(new Cookie("token",token));


            return "redirect:/";

        }else {
            log.error("callback get github error {}",githubUser);
            //返回index 页面，重新登录
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //退出  将session 中的属性清除
        request.getSession().removeAttribute("USER");
        //清除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}
