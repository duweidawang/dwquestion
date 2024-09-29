package com.example.dwquestion1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.example.dwquestion1.mapper")
@ServletComponentScan  //用于扫描过滤器
public class Dwquestion1Application {

    public static void main(String[] args) {
        SpringApplication.run(Dwquestion1Application.class, args);
    }

}
