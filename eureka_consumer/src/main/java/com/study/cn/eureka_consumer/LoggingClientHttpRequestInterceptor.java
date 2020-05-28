package com.study.cn.eureka_consumer;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 15:39
 * @Author: lzx
 * @Description: restTemplate拦截器
 * 如果每次请求都需要在http Header中添加相同的信息，可以通过ClientHttpRequestInterceptor来进行实现
 */
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        System.out.println("拦截啦！！！");
        System.out.println(request.getURI());

        ClientHttpResponse response = execution.execute(request, bytes);

        System.out.println(response.getHeaders());
        return response;
    }
}
