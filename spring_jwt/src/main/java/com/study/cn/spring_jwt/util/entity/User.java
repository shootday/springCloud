package com.study.cn.spring_jwt.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 22:01
 * @Author: lzx
 * @Description:
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Integer id;
    private String openid;
    private String name;
    private String headImg;
    private String phone;
    private String sign;
    private Integer sex;
    private String city;
    private Date createTime;
}
