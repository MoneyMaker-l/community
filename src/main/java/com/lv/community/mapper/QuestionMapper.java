package com.lv.community.mapper;

import com.lv.community.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-10-15:23
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,tag,creator,gmt_create) values (#{title},#{description},#{tag},#{creator},#{gmt_create})")
    void createQuestion(Question question);

    //展示所有用户的问题 按分页显示
    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> select(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(*) from question ")
    Integer count();

    @Select("select count(*) from question where creator = #{creator}")
    Integer countByCreator(@Param("creator") Integer creator);

    //展示 当前登录用户的 问题  按分页展示
    @Select("select * from question where creator=#{creator} limit #{offset},#{size}")
    List<Question> list(@Param("creator") Integer creator, @Param("offset") int offset, @Param("size") int size);

    @Select("select * from question where  id = #{id}")
    Question findQuestionById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},tag=#{tag} where id = #{id}")
    void updateQuestion(Question question);

    @Update("update question set view_count = #{view_count} + 1  where id = #{id}" )
    void updateViewCount(Question question);

    @Update("update question set comment_count = comment_count +1 where id=#{id}")
    void updateQuestionByComment(Question question);

    @Select("select * from question q where tag regexp #{regexp} and id != #{id}")
    List<Question> selectRelativeQuestion(@Param("regexp") String regexp,@Param("id") Integer id);

    @Select("select tag from question where id = #{id};")
    String selectTag(@Param("id") Integer id);

    @Select("select * from question where tag regexp #{regexp} limit #{offset},#{size}")
    List<Question> selectSearchQuestion(String regexp, Integer offset, int size);

    @Select("select count(*) from question where tag regexp #{regexp}")
    Integer selectTotalSearchQuestion(@Param(value = "regexp") String regexp);

    @Select("select title from question where id = #{questionId}")
    String selectNotifierName(@Param("questionId") int questionId);

    @Update("update question set like_count=like_count+1 where id =#{id}")
    int updateLikeCountByPrimaryKey(@Param("id") Integer questionId);

}
