package com.study.cn.spring_security.service.impl;

import com.study.cn.spring_security.dao.IUserDao;
import com.study.cn.spring_security.entity.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 10:42
 * @Author: lzx
 * @Description: 用户登录认证
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.info("登录用户id为：{}", s);
        Users user = userDao.loadUserByUsername(s);
        System.out.println(user);
        if (user == null) {
            //  throw new LockedException("用户名 " + s + "不存在");
            //抛出错误，用户不存在
            throw new UsernameNotFoundException("用户名 " + s + "不存在");
        }
//else{
//            throw new BadCredentialsException("我错了");
//        }

        return user;
    }
}

