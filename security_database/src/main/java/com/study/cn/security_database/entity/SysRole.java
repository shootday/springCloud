package com.study.cn.security_database.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_role
 * @author 
 */
@Data
public class SysRole implements Serializable {
    /**
     * id主键
     */
    private Integer id;

    /**
     * 角色名
     */
    private String name;

    private static final long serialVersionUID = 1L;
}