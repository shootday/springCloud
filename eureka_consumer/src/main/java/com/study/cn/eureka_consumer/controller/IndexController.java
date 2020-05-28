package com.study.cn.eureka_consumer.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @program: springCloudAll
 * @Date: 2020/4/9 14:25
 * @Author: lzx
 * @Description: 服务消费者--服务调用
 */
@RestController
public class IndexController {

    //cloud实现
    @Autowired
    DiscoveryClient discoveryClient;


    //Eureka 实现
    @Autowired
    EurekaClient eurekaClient;

    //Ribbon
    @Autowired
    LoadBalancerClient loadBalancerClient;


    @GetMapping("test")
    public String test() {
        return "HIHI consumer";
    }

    //获取服务列表
    @GetMapping("cli")
    public String cli() {
        List<String> services = discoveryClient.getServices();
        System.out.println(JSON.toJSONString(services));
        return "HIHI consumer";
    }

    //获取具体服务信息
    @GetMapping("cli2")
    public Object cli2() {
        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        System.out.println(JSON.toJSONString(instances));
        return instances;
    }


    //获取具体服务  discoveryClient
    @GetMapping("cli3")
    public Object cli3() {

        List<ServiceInstance> instances = discoveryClient.getInstances("eureka-provider");
        System.out.println(JSON.toJSONString(instances));
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getUri());
            System.out.println(instance.getInstanceId());


        }
        return instances;
    }


    //获取具体服务  eurekaClient
    @GetMapping("cli4")
    public Object cli4() {
        String homePageUrl = "";
        String forObject = "";
//        List<InstanceInfo> instancesById = eurekaClient.getInstancesById("localhost:eureka-provider:7001");
//        System.out.println(JSON.toJSONString(instancesById));
//        if (instancesById.size() > 0) {
//            for (InstanceInfo instanceInfo : instancesById) {
//                if(instanceInfo.getStatus().equals(InstanceInfo.InstanceStatus.UP)) {
//                    homePageUrl = instanceInfo.getHomePageUrl() + "test";
//                }
//            }
//        }

//        Application application = eurekaClient.getApplication("eureka-provider");
//        System.out.println(JSON.toJSONString(application));

        List<InstanceInfo> instancesByVipAddress = eurekaClient.getInstancesByVipAddress("eureka-provider", false);
        System.out.println(JSON.toJSONString(instancesByVipAddress));
        for (InstanceInfo byVipAddress : instancesByVipAddress) {
            if (byVipAddress.getStatus().equals(InstanceInfo.InstanceStatus.UP)) {
                homePageUrl = byVipAddress.getHomePageUrl() + "test";
            }
        }


        //简单的远程服务调用   RestTemplate 或者httpclient
        if (null != homePageUrl) {
            RestTemplate restTemplate = new RestTemplate();
            forObject = restTemplate.getForObject(homePageUrl, String.class);
        }
        return forObject;
    }


    //获取具体服务  eurekaClient
    @GetMapping("cli5")
    public Object cli5() {

        //loadBalancerClient  使用Ribbon客户端负载均衡调用 多个eureka-provider中挑一个调用  自动过滤掉down的客户端
        ServiceInstance choose = loadBalancerClient.choose("eureka-provider");
        System.out.println(choose.getUri() + "---");

        //简单的远程服务调用   RestTemplate 或者httpclient

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(choose.getUri()+"/test", String.class);

        return forObject;
    }
}
