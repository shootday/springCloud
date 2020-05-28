package com.study.cn.security_database.dao;

import com.study.cn.security_database.entity.SysRole;

public interface SysRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);


    SysRole selectByName(String roleName);
}