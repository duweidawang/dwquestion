package com.example.dwquestion1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dwquestion1.config.RedissonConfig;
import com.example.dwquestion1.constant.RedisConstant;
import com.example.dwquestion1.mapper.UserMapper;
import com.example.dwquestion1.model.entity.Question;
import com.example.dwquestion1.model.entity.User;
import com.example.dwquestion1.service.UserService;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    RedissonClient redissonClient;
    /**
     * 添加用户签到记录
     * @param id
     * @return
     */
    @Override
    public Boolean addUserSignService(long id) {
        LocalDate localDate = LocalDate.now();
        //获取记录的Redis key
        String key = RedisConstant.getUserSignInRedisKey(localDate.getYear(),id);
        RBitSet bitSet = redissonClient.getBitSet(key);
        //获取偏移量
        int dayOfYear = localDate.getDayOfYear();
        //查询当天是否签到 如果没有则签到
        if(!bitSet.get(dayOfYear)){
            bitSet.set(dayOfYear,true);
        }
        return true;
    }

    @Override
    public List<Integer> getUserSignByYear(Integer year, long id) {
        if(year == null){
            LocalDate now = LocalDate.now();
            year = now.getYear();
        }
        String key = RedisConstant.getUserSignInRedisKey(year,id);
        RBitSet bitSet = redissonClient.getBitSet(key);
        //将bitmap加载在内存中，减少后续对redis的访问
        BitSet bitSet1 = bitSet.asBitSet();
        //通过list来统计签到
        List<Integer> dayList = new ArrayList<>();
        //遍历，是否签到，将签到记录放到list
        Year year1 = Year.of(year);
        int length = year1.length();
        for(int i=1;i<length;i++){
            if(bitSet1.get(i)){
                dayList.add(i);
            }
        }
        return dayList;

    }
}
