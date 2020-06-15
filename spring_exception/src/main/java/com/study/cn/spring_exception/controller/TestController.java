package com.study.cn.spring_exception.controller;

import com.study.cn.spring_exception.annotation.PrintLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springCloudAll
 * @Date: 2020/5/15 10:20
 * @Author: lzx
 * @Description:
 */
@RestController
public class TestController {

    @RequestMapping("/info")
    public String test() {
        throw new NullPointerException("TestController have exception");
    }


    @RequestMapping("/aa")
    public String aa() {
        return "aaaa";
    }


    @PrintLog
    @RequestMapping("/bb")
    public String bbb() {
        return "bbbb";
    }


}
