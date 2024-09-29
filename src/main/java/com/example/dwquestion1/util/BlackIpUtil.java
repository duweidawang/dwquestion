package com.example.dwquestion1.util;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

public class BlackIpUtil {
    private static BitMapBloomFilter bloomFilter;

    //判断ip是否在黑名单内
    public static boolean isBlackIp(String ip){
        return bloomFilter.contains(ip);
    }

    //重建ip黑名单
    public static void rebuildBlackIp(String configInfo){
        if(StrUtil.isBlank(configInfo)){
            configInfo = "{}";
        }
        //解析yaml文件
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(configInfo, Map.class);
        //获取ip黑名单
        List<String> blackIpList = (List<String>)map.get("blackIpList");
        //
        synchronized (BlackIpUtil.class){
            if(CollectionUtil.isNotEmpty(blackIpList)){
                BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(958506);
                for(String ip:blackIpList){
                    bitMapBloomFilter.add(ip);
                }
                bloomFilter = bitMapBloomFilter;
            }else{
                bloomFilter = new BitMapBloomFilter(100);
            }
        }


    }

}
