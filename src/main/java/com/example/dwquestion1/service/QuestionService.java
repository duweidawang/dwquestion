package com.example.dwquestion1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.model.entity.Question;
import io.netty.handler.codec.http.HttpRequestEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public interface QuestionService extends IService<Question> {

    Question getQuestionById(long id);







}
