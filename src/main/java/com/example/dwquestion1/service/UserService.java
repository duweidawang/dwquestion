package com.example.dwquestion1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dwquestion1.model.entity.Question;
import com.example.dwquestion1.model.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    Boolean addUserSignService(long id);

    List<Integer> getUserSignByYear(Integer year,long id);






}
