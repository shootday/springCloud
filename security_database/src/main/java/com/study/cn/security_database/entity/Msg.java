package com.study.cn.security_database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: springCloudAll
 * @Date: 2020/5/20 16:16
 * @Author: lzx
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {


    private String title;

    private String content;

    private String etraInfo;

}
