package com.study.cn.eureka_consumer.controller;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 14:29
 * @Author: lzx
 * @Description: restTemplate基本使用
 */
@RestController
public class resttController {


    //cloud实现
    @Autowired
    DiscoveryClient discoveryClient;


    //Eureka 实现
    @Autowired
    EurekaClient eurekaClient;

    //Ribbon
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier(value = "loadRestTemplate")
    private RestTemplate loadRestTemplate;


    @GetMapping("cli9")
    public Object cli9() {

        String url = "http://eureka-provider/test";

        //简单的远程服务调用   RestTemplate 或者httpclient

        System.out.println("自动处理" + url);
        //String forObject = loadRestTemplate.getForObject(url, String.class);

        ResponseEntity<String> forEntity = loadRestTemplate.getForEntity(url, String.class);

        System.out.println("forEntity---" + forEntity);
        return forEntity;
    }

    @GetMapping("cli10")
    public Object cli10() {

        String url = "http://eureka-provider/map";

        //简单的远程服务调用   RestTemplate 或者httpclient

        System.out.println("自动处理" + url);
        //String forObject = loadRestTemplate.getForObject(url, String.class);

        ResponseEntity<Map> forEntity = loadRestTemplate.getForEntity(url, Map.class);

        System.out.println("forEntity---map" + forEntity);
        return forEntity;
    }

    @GetMapping("cli11")
    public Object cli11() {

        String url = "http://eureka-provider/obj";

        //简单的远程服务调用   RestTemplate 或者httpclient

        System.out.println("自动处理" + url);
        //String forObject = loadRestTemplate.getForObject(url, String.class);

        //ResponseEntity<Map> forEntity = loadRestTemplate.getForEntity(url, Map.class);

        Map forObject = loadRestTemplate.getForObject(url, Map.class);
        System.out.println("forEntity---map" + forObject);

        return forObject;
    }

    @GetMapping("cli12")
    public Object cli12() {

        //简单的远程服务调用   RestTemplate 或者httpclient

        //  String url = "http://eureka-provider/obj2?name={1}";
        //Map forObject = loadRestTemplate.getForObject(url, Map.class,"c测试");

        Map map = new HashMap();
        map.put("name", "dadha");

        String url = "http://eureka-provider/obj2?name={name}";

        Map forObject = loadRestTemplate.getForObject(url, Map.class, map);

        System.out.println("forEntity---map" + forObject);


        return forObject;
    }

    @HystrixCommand(fallbackMethod = "back")
    //Hystrix  模拟就写在controller了，实际写services
    //启动类加@EnableCircuitBreaker
    @GetMapping("cli13")
    public Object cli13() {

        //简单的远程服务调用   RestTemplate 或者httpclient

        //  String url = "http://eureka-provider/obj2?name={1}";
        //Map forObject = loadRestTemplate.getForObject(url, Map.class,"c测试");

        Map map = new HashMap();
        map.put("name", "faafa");

        String url = "http://eureka-provider/postobj";
        //ResponseEntity<Map> mapResponseEntity = loadRestTemplate.postForEntity(url, map, Map.class);

        Map forObject = loadRestTemplate.postForObject(url, "faafa", Map.class);

        System.out.println("forEntity13----map" + forObject);

        return forObject;
    }


    public String back() {

        return "请求失败~bbb...";
    }


    @GetMapping("cli14")
    public Object cli14(HttpServletResponse response) throws Exception {
        String url = "http://eureka-provider/postlocl";
        URI uri = loadRestTemplate.postForLocation(url, "sssss");
        response.sendRedirect(uri.toString());
        return null;
    }


}
