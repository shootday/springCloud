package com.study.cn.spring_security.service;

import com.study.cn.spring_security.SpringSecurityApplication;
import com.study.cn.spring_security.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 16:21
 * @Author: lzx
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringSecurityApplication.class}, //指定启动类
        webEnvironment = SpringBootTest.WebEnvironment.NONE) //无需web环境
public class UserService {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public  void a(){
        System.out.println(userService.loadUserByUsername("admin"));
    }
}
