package com.study.cn.spring_security.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 16:15
 * @Author: lzx
 * @Description:
 */
public interface IUserService {

    UserDetails loadUserByUsername(String username);
}
