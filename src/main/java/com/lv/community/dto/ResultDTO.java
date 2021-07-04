package com.lv.community.dto;

import lombok.Data;

/**
 * @author lvjiangtao
 * @create 2021-06-28-9:42
 */
@Data
public class ResultDTO {

    private String message;
    private Integer code;

    //设置错误码，错误信息
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("成功");
        return resultDTO;
    }
}
