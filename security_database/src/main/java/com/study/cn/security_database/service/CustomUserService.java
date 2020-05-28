package com.study.cn.security_database.service;

import com.study.cn.security_database.dao.SysPermissionDao;
import com.study.cn.security_database.dao.SysUserDao;
import com.study.cn.security_database.entity.MyGrantedAuthority;
import com.study.cn.security_database.entity.SysPermission;
import com.study.cn.security_database.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springCloudAll
 * @Date: 2020/5/20 16:20
 * @Author: lzx
 * @Description: /自定义UserDetailsService 接口
 */
@Service
@Slf4j
public class CustomUserService implements UserDetailsService {


    @Autowired
    private SysUserDao userDao;

    @Autowired
    private SysPermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.info("登录用户id为：{}", s);
        SysUser user = userDao.findByUserName(s);
        System.out.println(user);

        if (user == null) {
            //  throw new LockedException("用户名 " + s + "不存在");
            //抛出错误，用户不存在
            throw new UsernameNotFoundException("用户名 " + s + "不存在");
        }

        List<SysPermission> permissions = permissionDao.findByAdminUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority;
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
//        for (SysRole role : user.getRoles()) {
//            grantedAuthority = new SimpleGrantedAuthority(role.getName());
//            authorities.add(grantedAuthority);
//        }
//
        for (SysPermission permission : permissions) {
            if (permission != null && permission.getName() != null) {
                //用户验证url与url请求的方式  == null ? "GET" : permission.getMethod()
                grantedAuthority = new MyGrantedAuthority(permission.getUrl(), permission.getMethod() );
                System.out.println(grantedAuthority);
                // grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                authorities.add(grantedAuthority);
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
        //  return user;
    }
}
