package com.study.cn.security_database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @program: springCloudAll
 * @Date: 2020/5/22 16:18
 * @Author: lzx
 * @Description: 权限url与请求方式对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyGrantedAuthority implements GrantedAuthority {

    /**
     * 权限url
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;

    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}