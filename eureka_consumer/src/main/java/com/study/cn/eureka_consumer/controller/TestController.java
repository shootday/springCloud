package com.study.cn.eureka_consumer.controller;


import com.alibaba.fastjson.JSON;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: springCloudAll
 * @Date: 2020/4/9 14:25
 * @Author: lzx
 * @Description: 服务消费者--服务调用   远程服务调用
 */
@org.springframework.web.bind.annotation.RestController
public class TestController {

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


    @GetMapping("temp")
    public String test() {
        return "HIHI consumer";
    }


    //获取具体服务  eurekaClient
    @GetMapping("cli6")
    public Object cli6() {

        //loadBalancerClient  使用Ribbon客户端负载均衡调用 多个eureka-provider中挑一个调用  自动过滤掉down的客户端
        ServiceInstance choose = loadBalancerClient.choose("eureka-provider");
        System.out.println(choose.getUri() + "---");

        //简单的远程服务调用   RestTemplate 或者httpclient

        String forObject = restTemplate.getForObject(choose.getUri() + "/test", String.class);

        return forObject;
    }

    AtomicInteger atomicInteger = new AtomicInteger();


    //手动负载均衡
    @GetMapping("cli7")
    public Object cli7() {

        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        System.out.println(JSON.toJSONString(instances));


        //自定义随机
        int i = new Random().nextInt(instances.size());
        ServiceInstance serviceInstance = instances.get(i);

        //自定义轮询
        int b = atomicInteger.getAndIncrement();
        ServiceInstance serviceInstance1 = instances.get(b % instances.size());

        //权重 每个节点设置metadata 权重1-9
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            Integer weight = Integer.valueOf(metadata.get("weight")); //权重值
            System.out.println("server---" + instance.getInstanceId() + "weight---" + weight);

        }
        return serviceInstance1;
    }

    //自动处理url     restTemplate加上@LoadBalanced  使用服务名进行调用
    @GetMapping("cli8")
    public Object cli8() {

        String url = "http://eureka-provider/test";
        //简单的远程服务调用   RestTemplate 或者httpclient
        System.out.println("自动处理url" + url);
        String forObject = loadRestTemplate.getForObject(url, String.class);

        return forObject;
    }


}
