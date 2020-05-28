package com.study.cn.user_provider.controller;

import com.alibaba.fastjson.JSON;
import com.study.cn.user_api.entity.Person;
import com.study.cn.user_api.services.UserServiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 16:08
 * @Author: lzx
 * @Description: 实现公共api 统一管理
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserServiceApi {

    @Value("${server.port}")
    String port;


    private AtomicInteger count = new AtomicInteger();

    @Override
    public String alive() {

        //测试ribbon超时机制
        //没掉通 后续六秒内不在调用这台，直接调用好的  过六秒再来调用
//        try {
//            System.out.println("准备睡--");
//
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        int i = count.getAndIncrement();
//        System.out.println(port + "好的====好的第：" + i + "次调用");
        return "port:" + port;
    }


    @Override
    public String live() {
        //测试hystirx降级 获取异常
        int i=1/0;
        return "live";
    }


    @Override
    public String getUserById(Integer id) {
        System.out.println(id + "------provider-");

        return "id.toString()";
    }

    @Override
    public String getUsers() {
        return "user-api";
    }


    @Override
    public Person postPerson(Person person) {
        System.out.println(JSON.toJSONString(person) + "------pro");
        return person;
    }


    @GetMapping("/getMap")
    public Map<Integer, String> getMap(@RequestParam("id") Integer id) {
        System.out.println(id);
        return Collections.singletonMap(id, "mmeme");
    }

    @GetMapping("/getMap2")
    public Map<Integer, String> getMap2(Integer id, String name) {
        System.out.println(id);
        return Collections.singletonMap(id, name);
    }

    @GetMapping("/getMap3")
    public Map<Integer, String> getMap3(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return Collections.singletonMap(Integer.parseInt(map.get("id").toString()), map.get("name").toString());
    }

    @PostMapping("/getMap5")
    Map<Integer, String> getMap5(@RequestBody Integer id) {
        return Collections.singletonMap(id, "xxxxx");
    }

    ;


    @PostMapping("/postMap")
    public Map<Integer, String> postMap(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        return Collections.singletonMap(Integer.parseInt(map.get("id").toString()), map.get("name").toString());
    }


}
