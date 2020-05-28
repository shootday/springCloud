package com.study.cn.user_consumer.hystrix;

import com.study.cn.user_api.entity.Person;
import com.study.cn.user_consumer.services.ConsumerServices;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/20 15:16
 * @Author: lzx
 * @Description: Hystrix熔断降级
 */
@Component
public class ConsumerHystrix implements ConsumerServices {

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
        return "{msg:降级了}";
    }

    @Override
    public String live() {
        return null;
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
}
