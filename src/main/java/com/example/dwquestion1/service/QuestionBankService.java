package com.example.dwquestion1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dwquestion1.model.entity.QuestionBank;

public interface QuestionBankService extends IService<QuestionBank> {

    Page<QuestionBank> getQuestionBankList(long current,long size);



}
