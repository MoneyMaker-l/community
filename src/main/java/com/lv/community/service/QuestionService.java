package com.lv.community.service;

import com.lv.community.dto.PaginationDTO;
import com.lv.community.dto.QuestionDTO;
import com.lv.community.exception.CustomizeErrorCode;
import com.lv.community.exception.CustomizeException;
import com.lv.community.mapper.QuestionMapper;
import com.lv.community.mapper.UserMapper;
import com.lv.community.pojo.Question;
import com.lv.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lvjiangtao
 * @create 2021-06-10-21:06
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(String search,int page, int size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        int totalPage;
        //获取totalPage
        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        //page < 1 或者 > totalPage
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        //offset 是 limit 中的第一个元素
        Integer offset = size*(page - 1);

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        //搜索內容 查询
        if (search != null && search != ""){
            String[] searchs = search.split(" ");
            String regexp = Arrays.stream(searchs).collect(Collectors.joining("|"));

            List<Question> questionList = questionMapper.selectSearchQuestion(regexp,offset,size);
            totalCount = questionMapper.selectTotalSearchQuestion(regexp);

            paginationDTO.setPagenation(totalCount,size,page);
            for (Question question : questionList) {
                User user = userMapper.findById(question.getCreator());

                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question,questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);

            }
            paginationDTO.setQuestionDTOS(questionDTOS);

            return  paginationDTO;
        }
        //主页显示 分页查询
        List<Question> questionList = questionMapper.select(offset,size);

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setQuestionDTOS(questionDTOS);
        paginationDTO.setPagenation(totalCount,size,page);

        return  paginationDTO;
    }
    //重载有用户时  的查询
    public PaginationDTO list(Integer creator,int page, int size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByCreator(creator);
        //调用 setPagenation 方法
        paginationDTO.setPagenation(totalCount,size,page);

        //传入  -1  或者  比totalPage 大的数时，在查询数据库之前 将page设为相应的值
        if (page < 1){
            page = 1;
        }
        if(paginationDTO.getTotalPage()> 0) {
            //totalCount 可能为 0  就会有错 外面加一层 判断
            if (page > paginationDTO.getTotalPage()) {
                page = paginationDTO.getTotalPage();
            }
        }

        //offset 是 limit 中的第一个元素
        Integer offset = size*(page - 1);

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        //questionMapper 去 分页查询
        List<Question> questionList = questionMapper.list(creator,offset,size);
        User user = userMapper.findById(creator);
        for (Question question : questionList) {

            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        paginationDTO.setQuestionDTOS(questionDTOS);


        return  paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        //异常处理
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
    //
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmt_create(System.currentTimeMillis());
            questionMapper.createQuestion(question);
        }else {

            questionMapper.updateQuestion(question);
        }
    }

    //增加 阅读数
    public void incView(Integer id) {
        Question question = questionMapper.getById(id);
        questionMapper.updateViewCount(question);
        System.out.println(question.getView_count());

    }

    public List<Question> selectRelativeQuestion(Integer id) {
        String tag = questionMapper.selectTag(id);
        String[] tags = tag.split(",");
        String regexp = Arrays.stream(tags).collect(Collectors.joining("|"));

        List<Question> relativeQuestions = questionMapper.selectRelativeQuestion(regexp,id);

        return relativeQuestions;
    }
}
