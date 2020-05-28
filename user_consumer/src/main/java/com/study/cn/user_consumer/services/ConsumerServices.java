package com.study.cn.user_consumer.services;

import com.study.cn.user_api.services.UserServiceApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 16:25
 * @Author: lzx
 * @Description: Feign调用  通过公共的UserServiceApi来统一管理所有的需要调用的服务接口
 */
//不通过erueka服务名调用
//@FeignClient(name = "xxoo", url = "http://localhost:2001/users/")
//通过erueka服务名调用
@FeignClient(name = "user-provider")
//@FeignClient(name = "user-provider",fallback = ConsumerHystrix.class, path = "/user/")
//@FeignClient(name = "user-provider",fallbackFactory = ConsumerHystrixFactory.class, path = "/user/")
//path为前缀
public interface ConsumerServices extends UserServiceApi  {

    // @RequestMapping("/alive")
    // String alive();


    //get请求参数都需要加RequestParam
    // 不然Feign解析不到 会换成post提交 依赖中引入feign-httpclient可解决post问题，但是不加RequestParam取不到参数
    //post请求可以不加 RequestParam  但是在生产者里面的controller方法需要加上 @RequestBody

    @GetMapping("/getMap")
    Map<Integer, String> getMap(@RequestParam("id") Integer id);


    @GetMapping("/getMap2")
    Map<Integer, String> getMap2(@RequestParam("id") Integer id,@RequestParam("name") String name);


    @GetMapping("/getMap3")
    Map<Integer, String> getMap3(@RequestParam Map<String, Object> map);



    @PostMapping("/getMap5")
    Map<Integer, String> getMap5(Integer id);

    @PostMapping("/postMap")
    Map<Integer, String> postMap(Map<String, Object> map);


}
