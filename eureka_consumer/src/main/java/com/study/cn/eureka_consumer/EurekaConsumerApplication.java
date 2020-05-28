package com.study.cn.eureka_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableEurekaClient  //新版本可以不加
@EnableCircuitBreaker //开启Hystrix
@SpringBootApplication
public class EurekaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class, args);
    }


    //    必须使用应用名作为代替ip:端口，
//    http://127.0.0.1:8080/user/get
//    改成
//    http://应用名/user/get
//    不然会报错
//    使用RestTemplate时报错java.lang.IllegalStateException: No instances available for 127.0.0.1
//
//            1：不要使用ip+port的方式访问，取而代之的是应用名
//            2：这种方式发送的请求都会被ribbon拦截，ribbon从eureka注册中心获取服务列表，然后采用均衡策略进行访问
    //loadRestTemplate
    @Bean(name = "loadRestTemplate")
    @LoadBalanced
    RestTemplate loadRestTemplate() {
        RestTemplate restTemplate = restTemplate();
        //添加拦截器
        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());

        return restTemplate();
    }


    //RestTemplate
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    //ribbon 规则 默认轮询 也可通过配置文件
    //如果同时配置 测试一下 这里会覆盖配置文件
//    @Bean
//    public IRule myIRule() {
//        return new RetryRule();
//    }


}
