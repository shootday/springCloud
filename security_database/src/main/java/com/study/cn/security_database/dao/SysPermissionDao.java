package com.study.cn.security_database.dao;

import com.study.cn.security_database.entity.SysPermission;

import java.util.List;

public interface SysPermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<SysPermission> findAll();

    List<SysPermission> findByAdminUserId(int userId);

    List<SysPermission> findByAdminRoleId(int roleId);
}