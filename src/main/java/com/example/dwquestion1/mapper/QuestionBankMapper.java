package com.example.dwquestion1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dwquestion1.model.entity.QuestionBank;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {

}




