package com.lv.community.mapper;

import com.lv.community.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @author lvjiangtao
 * @create 2021-06-09-18:22
 */
@Mapper
public interface UserMapper {


    @Insert("insert into user (id,name,account_id,token,avatar_url) values (0,#{name}, #{account_id} , #{token},#{avatar_url})")
    void addUser(User user);


    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);


    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id") String account_id);

    @Update("update user set name=#{name},token=#{token},avatar_url=#{avatar_url} where id=${id}")
    void update(User user);

    @Select("select name from user where id = #{notifier}")
    String selectNotificationName(@Param("notifier") int notifier);

}
