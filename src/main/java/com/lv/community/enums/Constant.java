package com.lv.community.enums;

/**
 * @author lvjiangtao
 * @create 2021-09-21-15:03
 */
public class Constant {

    public static final String valueRedis = "Redis:String:questionId:";

    public static String getValueRedis(Integer id){
        return valueRedis+id;
    }
}
