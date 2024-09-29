package com.example.dwquestion1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dwquestion1.mapper.QuestionBankMapper;
import com.example.dwquestion1.model.entity.QuestionBank;
import com.example.dwquestion1.service.QuestionBankService;
import org.springframework.stereotype.Service;

@Service
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements QuestionBankService {

    /**
     * 分页获取题库
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<QuestionBank> getQuestionBankList(long current, long size) {

        // 参数校验
        QueryWrapper<QuestionBank> questionBankQueryWrapper = new QueryWrapper<>();
        Page<QuestionBank> objectPage = new Page<>(current, size);
        Page<QuestionBank> page = page(objectPage, questionBankQueryWrapper);
        return page;
    }
}
