package com.lv.community;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

@SpringBootTest
@Slf4j
class CommunityApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    public void zsetTest(){
//        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add("score","jack",30    );
//        zSetOperations.add("score","tom",20    );
//        zSetOperations.add("score","jim",100    );
//        zSetOperations.add("score","james",60    );
//        Set set = zSetOperations.reverseRangeByScore("score", 0, 2);
//        for (Object o : set) {
//            log.info("reverseRangeByScore--->{}",o);
//        }

        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("123","java","mysql");

    }
}
