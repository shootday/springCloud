package com.study.cn.security_database.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_permission_role
 * @author 
 */
@Data
public class SysPermissionRole implements Serializable {
    /**
     * id主键
     */
    private Integer id;

    /**
     * 角色id
     */
    private Integer sysRoleId;

    private Integer sysPermissionId;

    private static final long serialVersionUID = 1L;
}