package com.study.cn.spring_exception.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @program: springCloudAll
 * @Date: 2020/6/15 16:26
 * @Author: lzx
 * @Description:  类上或者方法上加上该注解则打印日志 基于aop
 */
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface PrintLog {
}
