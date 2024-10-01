package com.example.dwquestion1.controller;


import com.example.dwquestion1.common.BaseResponse;
import com.example.dwquestion1.common.ResultUtils;
import com.example.dwquestion1.service.UserService;
import com.google.api.Http;
import io.lettuce.core.api.reactive.BaseRedisReactiveCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add/sign")
    public BaseResponse<Boolean> addUserSign(HttpServletRequest request){
        //检查是否登录，登录才可以签到
        Boolean b = userService.addUserSignService(5);
        return ResultUtils.success(b);
    }


    @GetMapping("/get/sign")
    public BaseResponse<List<Integer>> getUserSignByYear(Integer year,HttpServletRequest request){
        //判断登录获取登录用户id
        List<Integer> userSignByYear = userService.getUserSignByYear(year, 5);
        return ResultUtils.success(userSignByYear);

    }
}
