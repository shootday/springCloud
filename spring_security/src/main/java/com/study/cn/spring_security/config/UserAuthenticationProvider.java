package com.study.cn.spring_security.config;

import com.study.cn.spring_security.entity.Users;
import com.study.cn.spring_security.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 14:37
 * @Author: lzx
 * @Description: 自定义用户身份认证
 */
@Slf4j
//@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("开始校验账号密码：{}", authentication.toString());

        //表单提交的
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        System.out.println(username + "----a");
        System.out.println(password + "----b");
        //查询出来的用户密码

        Users userDetails = (Users) userService.loadUserByUsername(username);
        System.out.println(userDetails + "----数据库用户");
        boolean matches = passwordEncoder.matches(password, userDetails.getPassword());
        System.out.println(matches);
        if (matches) {
            if (userDetails.isEnabled()) {
                //登录成功
                return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
            } else {
                throw new DisabledException("账户不可用");
            }

        } else {
            throw new BadCredentialsException("用户名或者密码错误！");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
