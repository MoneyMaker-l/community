package com.lv.community.interceptor;

import com.lv.community.mapper.NotificationMapper;
import com.lv.community.mapper.UserMapper;
import com.lv.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lvjiangtao
 * @create 2021-06-12-22:00
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);

                    //查到的用户存在 放到 session 域中
                    if (user != null) {
                        request.getSession().setAttribute("USER", user);
                        Integer receiver = user.getId();
                        Integer newReply = notificationMapper.selectAllNotification(receiver);
                        request.getSession().setAttribute("newReplay",newReply);
                    } else{
                        break;
                    }
                }
            }

        }
        return true;
    }
}
