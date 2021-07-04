package com.lv.community.mapper;

import com.lv.community.pojo.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-07-01-9:50
 */
@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(notifier,receiver,questionId,gmt_create) " +
            "values (#{notifier},#{receiver},#{questionId},#{gmt_create})")
    void addNotification(Notification notification);

    @Select("select * from notification where status = 0 limit #{offset},#{size}")
    List<Notification> selectNotification(@Param("offset") int offset, @Param("size") int size);

    @Select("select count(*) from notification where receiver = #{creator} and status = 0 ")
    Integer selectAllNotification(@Param("creator") Integer creator);

    @Update("update notification set status = 1 where id = #{notificationId} ")
    void updateStatus(@Param("notificationId") Integer notificationId);
}
