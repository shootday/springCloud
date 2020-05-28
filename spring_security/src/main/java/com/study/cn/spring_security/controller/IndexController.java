package com.study.cn.spring_security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Random;

/**
 * @program: springCloudAll
 * @Date: 2020/5/12 20:37
 * @Author: lzx
 * @Description:
 */

@RestController
public class IndexController {


    @GetMapping("hi")
    public Object hi() {
        //获取登录信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String string = authentication.getPrincipal().toString();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        Object details = authentication.getDetails();
        return string + "----" + authorities + "----" + credentials + "-----" + details;

    }

    @Secured({"ROLE_admin", "ROLE_guest"})  //admin或者 guest 才能访问
    @GetMapping("hi1")
    public String hi1() {
        return "hi1";
    }


    @GetMapping("admin/hi")
    @Secured("ROLE_admin")  //admin才能访问
    public String admin() {
        return "admin/hi";
    }

    @Secured("ROLE_user")  //useer才能访问
    @GetMapping("user/hi")
    public String user() {
        return "user/hi";
    }

    @Secured("ROLE_au")  //au才能访问
    @GetMapping("au/hi")
    public String au() {
        return "au/hi";
    }


    @PreAuthorize("hasRole('ROLE_admin')")  //ROLE_admin才能访问
    @GetMapping("admin/hi1")
    public String admin1() {
        return "admin/hi1";
    }

    @PreAuthorize("hasAnyRole('ROLE_admin','ROLE_guest')")  //ROLE_admin或者 ROLE_guest才能访问
    @GetMapping("admin/hi2")
    public String admin2() {
        return "admin/hi2";
    }

    @PreAuthorize("hasRole('ROLE_admin') AND hasRole('ROLE_user')")  //ROLE_admin并且 ROLE_guest才能访问
    @GetMapping("admin/hi3")
    public String admin3() {
        return "admin/hi3";
    }

    //spring el
    //表达式是true有权限  false 直接返回403
    @PostAuthorize("returnObject==1")
    @GetMapping("admin/hi4")
    public int admin4() {
        System.out.println("来了老弟");
        return new Random().nextInt(2);
    }

}
