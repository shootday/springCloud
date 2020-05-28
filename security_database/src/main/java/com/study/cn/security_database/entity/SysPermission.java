package com.study.cn.security_database.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_permission
 * @author 
 */
@Data
public class SysPermission implements Serializable {
    /**
     * id主键
     */
    private Integer id;

    /**
     * 名称 权限的名字可以随意起名
     */
    private String name;

    /**
     * 描述
     */
    private String descritpion;

    /**
     * url 通配符为两颗星，比如说 /user下的所有url，应该写成 /user/**
     */
    private String url;

    /**
     * 上级节点
     */
    private Integer pid;

    /**
     * 请求的方式，适配restful风格
     */
    private String method;

    private static final long serialVersionUID = 1L;
}