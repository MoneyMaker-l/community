package com.lv.community.controller;

import com.lv.community.dto.FileDTO;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lvjiangtao
 * @create 2021-06-30-9:45
 */
@Controller
public class FileController {

    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/css/image/bunting-flag.png");
        return fileDTO;
    }
}
