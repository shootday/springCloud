package com.study.cn.user_api.services;

import com.study.cn.user_api.entity.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: springCloudAll
 * @Date: 2020/4/14 16:38
 * @Author: lzx
 * @Description: userApi 相当于接口文档  方便统一管理
 * 但是相对于来说只用于java 如果其它语言的话 还是的写文档
 */

//@RequestMapping("/user")  //加了这个配置hystrix会找不到 ,so把url映射放在接口中
// 如下注释的alive接口，那么provider里面的接口也都带上了/user，因为是公共的api
//或者这里的接口不配置前缀，直接在Feign那里配置path即可，当然provider里面对应的controller加上@RequestMapping("/user")
public interface UserServiceApi {


    @RequestMapping("/alive")
     String alive() ;

    @RequestMapping("/live")
    String live() ;

//    @RequestMapping("/user/alive")
//    String alive() ;

    /**
     * 用户查询
     *
     * @return
     */
    //参数需要加@RequestParam不然取不到
    @RequestMapping("/getUserById")
    //String getUserById(Integer id);
    String getUserById(@RequestParam("id") Integer id);

    /**
     * 获取所有的用户
     *
     * @return
     */
    @RequestMapping("/getUsers")
    String getUsers();


    /**
     * 添加的用户
     *
     * @return
     */
    @PostMapping("/postPerson")
    Person postPerson(@RequestBody Person person);
}
