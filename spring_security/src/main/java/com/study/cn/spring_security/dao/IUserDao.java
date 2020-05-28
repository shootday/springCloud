package com.study.cn.spring_security.dao;

import com.study.cn.spring_security.entity.Users;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 10:59
 * @Author: lzx
 * @Description:
 */
public interface IUserDao {

    /**
     * 根据用户名获取信息
     * @param username
     * @return
     */
    Users loadUserByUsername(String username);
}
