package com.example.dwquestion1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dwquestion1.mapper.QuestionMapper;
import com.example.dwquestion1.model.entity.Question;
import com.example.dwquestion1.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {


    /**
     * 根据id获取题目
     * @param id
     * @return
     */
    @Override
    public Question getQuestionById(long id) {
        Question byId = this.getById(id);
        return byId;
    }





}
