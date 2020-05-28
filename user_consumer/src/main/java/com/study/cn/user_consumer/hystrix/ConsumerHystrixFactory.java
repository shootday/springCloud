package com.study.cn.user_consumer.hystrix;

import com.study.cn.user_api.entity.Person;
import com.study.cn.user_consumer.services.ConsumerServices;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/20 15:59
 * @Author: lzx
 * @Description: Hystrix熔断降级更细致 可接收服务端的异常
 */
@Component
public class ConsumerHystrixFactory implements FallbackFactory<ConsumerServices> {
    @Override
    public ConsumerServices create(Throwable throwable) {
        return new ConsumerServices() {
            @Override
            public Map<Integer, String> getMap(Integer id) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap2(Integer id, String name) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap3(Map<String, Object> map) {
                return null;
            }

            @Override
            public Map<Integer, String> getMap5(Integer id) {
                return null;
            }

            @Override
            public Map<Integer, String> postMap(Map<String, Object> map) {
                return null;
            }

            @Override
            public String alive() {
                return "sssssss降级了sssss";
            }

            @Override
            public String live() {
                System.out.println(throwable+"=====");
                throwable.printStackTrace();
                //可自定义异常处理
                if(throwable instanceof FeignException.InternalServerError){
                    return "sssssss降级了sssss";
                }
                return "降级了sssss";
            }

            @Override
            public String getUserById(Integer id) {
                return null;
            }

            @Override
            public String getUsers() {
                return null;
            }

            @Override
            public Person postPerson(Person person) {
                return null;
            }
        };
    }
}
