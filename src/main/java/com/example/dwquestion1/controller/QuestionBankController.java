package com.example.dwquestion1.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.common.ErrorCode;
import com.example.dwquestion1.common.ResultUtils;
import com.example.dwquestion1.model.dto.questionBank.QuestionBankQueryRequest;
import com.example.dwquestion1.model.entity.QuestionBank;
import com.example.dwquestion1.model.vo.QuestionBankVO;
import com.example.dwquestion1.sentinel.SentinelConstant;
import com.example.dwquestion1.service.QuestionBankService;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class QuestionBankController {

    @Autowired
    QuestionBankService questionBankService;

    /**
     * 分页获取题库列表（封装类）
     */
    @PostMapping("/list/page/vo")
    @SentinelResource(value = SentinelConstant.listQuestionBankVOByPage,
//            blockHandler = "handleBlockException",
            fallback = "handleFallback"
    )
    public BaseResponse<Page<QuestionBank>> listQuestionBankVOByPage(@RequestBody QuestionBankQueryRequest questionBankQueryRequest,
                                                                       HttpServletRequest request) {
        long current = questionBankQueryRequest.getCurrent();
        long size = questionBankQueryRequest.getPageSize();
        // 查询数据库

        Page<QuestionBank> questionBankPage =questionBankService.getQuestionBankList(current,size);
        return ResultUtils.success(questionBankPage);
    }

    /**
     * 降级操作，直接返回本地数据
     * @param questionBankQueryRequest
     * @param request
     * @param
     * @return
     */                                                                                                                                                 //要把异常类型给包括了
     public BaseResponse<Page<QuestionBank>> handleFallback(@RequestBody QuestionBankQueryRequest questionBankQueryRequest,
                                                            HttpServletRequest request,Throwable ex){

        return ResultUtils.success(null);
    }


    /**
     * 限流 提示信息
     */
    public BaseResponse<Page<QuestionBank>> handleBlockException(@RequestBody QuestionBankQueryRequest questionBankQueryRequest, HttpServletRequest request,Throwable ex){

        //这个DegradeException为 业务异常后抛出的异常            BlockException为触发限流后抛出的异常类型
        if(ex instanceof DegradeException){
            return handleFallback(questionBankQueryRequest,request,ex);
        }

        // 限流操作
        System.out.println(1);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"系统压力过大，请耐心等待");
    }

}
