package com.study.cn.eureka_consumer.controller;

import com.study.cn.eureka_consumer.HealthStatusService;
import com.study.cn.eureka_consumer.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/9 14:25
 * @Author: lzx
 * @Description: 服务生产者
 */
@RestController
public class IndexController {

    @Autowired
    private HealthStatusService healthStatusService;

    @GetMapping("test")
    public String test() {
        return "HIHI";
    }


    @GetMapping("map")
    public Map<String, Integer> map() {
        return Collections.singletonMap("id", 100);
    }


    @GetMapping("obj")
    public Person obj() {
        Person person = new Person(1, "6666");
        return person;
    }

    @GetMapping("obj2")
    public Person obj2(String name) {
        Person person = new Person(1, name);
        return person;
    }

    @PostMapping("postobj")
    public Person postobj(@RequestBody String name) {
        System.out.println("name---" + name);
        Person person = new Person(1, name);
        return person;
    }

    @PostMapping("postlocl")
    public URI postlocl(@RequestBody String name, HttpServletResponse response) throws Exception {
        URI uri = new URI("https://www.baidu.com/s?wd=" + name);

        //这个不加空指针
        response.addHeader("Location", uri.toString());
        return uri;
    }

    @GetMapping("/health")
    public String health(@RequestParam("status") Boolean status) {

        healthStatusService.setStatus(status);
        return healthStatusService.getStatus();
    }


}
