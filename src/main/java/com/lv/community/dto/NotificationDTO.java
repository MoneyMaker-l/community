package com.lv.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lvjiangtao
 * @create 2021-07-01-11:29
 */
@Data
public class NotificationDTO {
    private List<QuestionDTO> questionDTOS = null;
    private List<NotificationNameQuestionDTO> notificationNameQuestionDTOS;
    private Integer newReply;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;//当前页
    private List<Integer> pages = new ArrayList<Integer>(); //当前页面页数
    private Integer totalPage;

    public void setPagenation(Integer totalCount, int size, int page) {

        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }

        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        this.page = page;

        //这个地方  有点不容易想到
        pages.add(page);
        for (int i = 1; i <= 3 ; i++) {
            if (page - i > 0 ){
                pages.add(0,page-i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }
        //是否展示上一页
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }

    }
}
