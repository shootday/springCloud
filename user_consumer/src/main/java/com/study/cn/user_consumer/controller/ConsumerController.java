package com.study.cn.user_consumer.controller;

import com.alibaba.fastjson.JSON;
import com.study.cn.user_api.entity.Person;
import com.study.cn.user_consumer.services.ConsumerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 16:28
 * @Author: lzx
 * @Description:
 */
@RefreshScope  //重新加载config 可以通过 http://laptop-meicvj3d:3000/actuator/refresh 刷新配置
@RestController
public class ConsumerController {

    @Value("${server.port}")
    String port;

    @Autowired
    private ConsumerServices consumerServices;

    //获取cloud config中的配置  当前配置文件改成bootstrap.yml 才能读取注入到
    @Value("${myconfig}")
    String myconfig;

    @RequestMapping("con")
    public String con() {
        System.out.println("alive----con");
        return "myconfig:" + myconfig;
    }

    @RequestMapping("alive")
    public String alive() {
        System.out.println("alive----con");
        return "consumer:" + port + "->>>provider: " + consumerServices.alive();
    }

    @RequestMapping("live")
    public String live() {
        System.out.println("live----con");
        return consumerServices.live();
    }


    @RequestMapping("getUsers")
    public String getUsers() {
        System.out.println("----getUsers");
        return consumerServices.getUsers();
    }

    @RequestMapping("getUserById")
    public String getUsers(Integer id) {
        System.out.println(id + "----id");
        return consumerServices.getUserById(id);
    }


    @GetMapping("/map")
    public Map<Integer, String> map(Integer id) {
        System.out.println(id);
        return consumerServices.getMap(id);
    }

    @GetMapping("/map2")
    public Map<Integer, String> map2(Integer id, String name) {
        System.out.println(id);
        System.out.println(name);
        return consumerServices.getMap2(id, name);
    }

    //map需要加@RequestParam
    @GetMapping("/map3")
    public Map<Integer, String> map3(@RequestParam Map<String, Object> map) {
//		System.out.println(id);
//		HashMap<String, Object> map = new HashMap<>(2);
//
//		map.put("id", id);
//		map.put("name", name);
//		syso
        System.out.println(map);
        return consumerServices.getMap3(map);
    }

    @GetMapping("/map5")
    public Map<Integer, String> map5(Integer id) {
        System.out.println(id);
        return consumerServices.getMap5(id);
    }


    @GetMapping("/map4")
    public Map<Integer, String> map4(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return consumerServices.postMap(map);
    }


    @GetMapping("/postPerson")
    public Person postPerson(Person person) {
        System.out.println(JSON.toJSONString(person));
        return consumerServices.postPerson(person);
    }

}
