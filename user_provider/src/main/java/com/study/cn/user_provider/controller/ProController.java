package com.study.cn.user_provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 16:08
 * @Author: lzx
 * @Description: user服务
 */
@RestController
@RequestMapping("/users")
public class ProController {


    @RequestMapping("/alive")
    public String alive() {
        return "ok";
    }


}
