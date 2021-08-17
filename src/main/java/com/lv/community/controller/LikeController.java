package com.lv.community.controller;

import com.lv.community.api.BaseResponse;
import com.lv.community.api.StatusCode;
import com.lv.community.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实现 点赞  取消点赞 的功能
 * @author lvjiangtao
 * @create 2021-08-16-17:09
 */
@RestController
@Slf4j
public class LikeController {

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/doLike",method = RequestMethod.GET)
    public BaseResponse doLike(Integer userId, Integer questionId){ //likeDto需要有点赞人的id，文章的id，文章的内容

        BaseResponse response = new BaseResponse(StatusCode.Succesee);

            try {
                boolean b = likeService.doLike(userId, questionId);
                response.setData(b);
            }catch (Exception e){
                log.error("异常",e);
                response = new BaseResponse(StatusCode.Fail,e.getMessage());
            }finally {
                return response;
            }
    }
}
