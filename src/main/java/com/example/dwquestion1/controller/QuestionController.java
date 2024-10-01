package com.example.dwquestion1.controller;


import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.common.ErrorCode;
import com.example.dwquestion1.common.ResultUtils;
import com.example.dwquestion1.exception.ThrowUtils;
import com.example.dwquestion1.model.entity.Question;
import com.example.dwquestion1.service.QuestionService;
import com.example.dwquestion1.service.UserService;
import com.jd.platform.hotkey.client.callback.JdHotKeyStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/get/vo")
    public BaseResponse<Question> getQuestionById(@RequestParam("id") long id, HttpServletRequest request){
        //先校验id
        ThrowUtils.throwIf(id<0, ErrorCode.PARAMS_ERROR);

        //热key探测
        String key = "question_detail_"+id;
        //判断是否是热key
        //isHotKey方法用于判断是否是热key，没调用一次就会发到worker进行计算，如果是热key返回true
        if(JdHotKeyStore.isHotKey(key)){
            //从本地缓存中获取缓存值
            Object cacheQuestion = JdHotKeyStore.get(key);
            if(cacheQuestion !=null){
                //如果本地缓存有则直接返回
                log.info("从本地缓存获取到了值");
                return ResultUtils.success((Question) cacheQuestion);
            }else{
                //从数据库查找,并放到
                synchronized (QuestionController.class){
                    Object cacheQuestionSyn = JdHotKeyStore.get(key);
                    if(cacheQuestionSyn==null){
                        log.info("发现了热key，并设置了本地缓存");
                        Question question = questionService.getQuestionById(id);
                        JdHotKeyStore.smartSet(key,question);
                        return ResultUtils.success(question);
                    }else{
                        return ResultUtils.success((Question) cacheQuestionSyn);
                    }
                }

            }

        }
        //不是热key直接查找数据库
        log.info("不是热key直接查询数据库");
        Question question = questionService.getQuestionById(id);
        return ResultUtils.success(question);
    }


}
