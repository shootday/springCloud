package com.study.cn.security_database.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_role_user
 * @author 
 */
@Data
public class SysRoleUser implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer sysUserId;

    /**
     * 角色id
     */
    private Integer sysRoleId;

    private static final long serialVersionUID = 1L;
}