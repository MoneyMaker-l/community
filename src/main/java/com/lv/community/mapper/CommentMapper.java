package com.lv.community.mapper;

import com.lv.community.dto.Comment;
import com.lv.community.dto.CommentQueryDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-06-15-21:02
 */
@Mapper
public interface CommentMapper {


    @Insert("insert into comment(question_id,type,commentator,gmt_create,gmt_modified,like_count,content) " +
            "values (#{question_id},#{type}, #{commentator},#{gmt_create}, #{gmt_modified},#{like_count},#{content})")
    void insert(Comment comment);

    //需要回复人的头像， 名称， 回复的内容，回复时间
    @Select("SELECT q.id,c.id,TYPE,gmt_create,content,u.name,avatar_url\n" +
            "FROM (comment c INNER JOIN user u ON c.commentator = u.id)\n" +
            "INNER JOIN question q ON q.id=c.`question_id`\n" +
            "WHERE q.id = #{id}\n and type = 1 ORDER BY c.`gmt_create` DESC;")
    List<CommentQueryDTO> findAllComment(@Param("id") Integer id);

    @Select("SELECT c.`id` AS comment_id,q.id AS question_id,TYPE,gmt_create,content,u.name,avatar_url\n" +
            "FROM (comment c INNER JOIN user u ON c.commentator = u.id)\n" +
            "INNER JOIN question q ON q.id=c.`question_id`\n" +
            "WHERE c.`question_id`= #{id} AND TYPE = 2\n" +
            "ORDER BY c.`gmt_create` DESC;\n")
    List<CommentQueryDTO> findSecondaryComments(@Param("id") Integer id);

}
