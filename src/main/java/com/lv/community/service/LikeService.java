package com.lv.community.service;


import com.google.common.collect.Sets;
import com.lv.community.api.Constant;
import com.lv.community.dto.HotArticlDTO;
import com.lv.community.mapper.QuestionMapper;
import com.lv.community.pojo.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author lvjiangtao
 * @create 2021-08-16-17:35
 */
@Service
@Transactional
@Slf4j
public class LikeService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //点赞操作
    public boolean doLike(Integer userId,Integer questionId) {
        //当前用户是否可以点赞该文章
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations<String,String,Set<Integer>> hashOperations = redisTemplate.opsForHash();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        String key = Constant.RedisLike+":"+userId+":"+questionId;
        Boolean canLike = valueOperations.setIfAbsent(key, 1);

        if (canLike){
            //可以点赞
            //更新到数据库
            if (userId == 0){
                return true;
            }
            int res = questionMapper.updateLikeCountByPrimaryKey(questionId);

            if (res > 0){
                //缓存点赞的相关信息 (hash -- key --field<questionId> -- value(set<userId...>)))
                String hashUserId = Constant.RedisLikeUserId;
                //得到点过该问题赞的 set<Integer> userId
                Set<Integer> uIds = hashOperations.get(hashUserId, questionId.toString());
                if (uIds==null || uIds.isEmpty()){
                    uIds = Sets.newHashSet();
                }
                uIds.add(userId);
                hashOperations.put(hashUserId,questionId.toString(),uIds);

                //更新排行榜
                Question question = questionMapper.findQuestionById(questionId);
                String title = question.getTitle();
                String zsetRank = Constant.RedisRank;

                int LikeCount = uIds.size();//一个redis:rank:v1 53::如何做分页
                zSetOperations.add(zsetRank,questionId+"::"+title,LikeCount);
            }
        }
        return true;
    }

    //获取排行榜热点文章
    public List<HotArticlDTO> getHotArticle(){
        List<HotArticlDTO> list = new LinkedList<>();
        String zsetRank = Constant.RedisRank;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set<String> zset = zSetOperations.reverseRange(zsetRank, 0, 9); //value 分为 questionId  title
        for (String s : zset) {
            int pos = StringUtils.indexOf(s,"::");
            String saId = StringUtils.substring(s,0,pos);
            String aTitle = StringUtils.substring(s,pos+2);
            int aId = Integer.parseInt(saId);
            list.add(new HotArticlDTO(aTitle,aId));
        }
        return list;
    }

    //取消点赞
    public void unLike(Integer userId,Integer questionId){

        //todo:

    }
}
