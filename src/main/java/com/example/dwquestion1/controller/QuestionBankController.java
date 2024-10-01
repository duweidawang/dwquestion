package com.example.dwquestion1.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.common.ErrorCode;
import com.example.dwquestion1.common.ResultUtils;
import com.example.dwquestion1.manager.CountManager;
import com.example.dwquestion1.model.dto.questionBank.QuestionBankQueryRequest;
import com.example.dwquestion1.model.entity.QuestionBank;
import com.example.dwquestion1.model.entity.User;
import com.example.dwquestion1.model.vo.QuestionBankVO;
import com.example.dwquestion1.sentinel.SentinelConstant;
import com.example.dwquestion1.service.QuestionBankService;
import com.example.dwquestion1.service.UserService;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
public class QuestionBankController {

    @Autowired
    QuestionBankService questionBankService;
    @Autowired
    private CountManager countManager;
    @Resource
    UserService userService;

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
        //爬虫检测 //todo
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

    /**
     * 检测爬虫的方法
     * @param userId
     */
    private void crawlerDetect(long userId){
        //告警次数

        final int WARN_COUNT = 10;
        //封禁次数
        final int BAN_COUNT = 20;
        String key = String.format("user:access:%s",userId);
        long count = countManager.incrAndGetCounter(key, 1, TimeUnit.MINUTES, 180);
        if(count > BAN_COUNT){
            //进行封号  通过修改对应的权限进行封号
            User user = new User();
            user.setId(userId);
            user.setUserRole("ban");
            userService.updateById(user);
        }

        if(count > WARN_COUNT){
            //进行告警
            //可以通过像管理员发送邮件通知
        }




    }



}
