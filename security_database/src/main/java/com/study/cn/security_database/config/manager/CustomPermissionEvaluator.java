package com.study.cn.security_database.config.manager;

import com.study.cn.security_database.dao.SysPermissionDao;
import com.study.cn.security_database.dao.SysRoleDao;
import com.study.cn.security_database.entity.SysPermission;
import com.study.cn.security_database.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @program: springCloudAll
 * @Date: 2020/5/22 10:34
 * @Author: lzx
 * @Description: 我们需要自定义对hasPermission()方法的处理，
 *  就需要自定义PermissionEvaluator，创建类CustomPermissionEvaluator，实现PermissionEvaluator接口。
 *
 *  @PreAuthorize("hasPermission('/admin','r')")是关键，参数1指明了访问该接口需要的url，参数2指明了访问该接口需要的权限。
**/

//@Component
public  class CustomPermissionEvaluator implements PermissionEvaluator {


    @Autowired
    private SysPermissionDao permissionDao;


    @Autowired
    private SysRoleDao roleDao;


    /**
     * 自定义验证方法
     * @param authentication        登录的时候存储的用户信息
     * @param targetUrl    @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第一个参数
     * @param permission   @PreAuthorize("hasPermission('/hello/**','r')") 中hasPermission的第二个参数
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        System.out.println(targetUrl+"-----ta");
        System.out.println(permission+"-----pp");
        // 获得loadUserByUsername()方法的结果
        SysUser user = (SysUser) authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 遍历用户所有角色
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleDao.selectByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionDao.findByAdminRoleId(roleId);
            // 遍历permissionList
            for (SysPermission sysPermission : permissionList) {
                // 获取权限集
                String url = sysPermission.getUrl();
                // 如果访问的Url和权限用户符合的话，返回true
                if (targetUrl.equals(sysPermission.getUrl())) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
