package com.study.cn.security_database.service;

import com.study.cn.security_database.entity.SysPermission;
import com.study.cn.security_database.dao.SysPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: springCloudAll
 * @Date: 2020/5/21 15:51
 * @Author: lzx
 * @Description:
 */
@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionDao permissionDao;

    public List<SysPermission> findAll() {
        return permissionDao.findAll();
    }


    public List<SysPermission> findByAdminUserId(int userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new RuntimeException("用户id不能为空");
        }
        return permissionDao.findByAdminUserId(userId);
    }

}
