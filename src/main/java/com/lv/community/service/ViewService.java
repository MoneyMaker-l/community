package com.lv.community.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author lvjiangtao
 * @create 2021-09-21-15:24
 */
@Service
public class ViewService {

    @Scheduled(fixedRate = 60*1000)
    public void incViewFromRedis2DB(){
        //TODO:将 redis 中的 view 加到数据库中

    }

}
