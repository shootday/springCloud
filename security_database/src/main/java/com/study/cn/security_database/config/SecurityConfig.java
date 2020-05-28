package com.study.cn.security_database.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.cn.security_database.config.filter.MyFilterSecurityInterceptor;
import com.study.cn.security_database.entity.Msg;
import com.study.cn.security_database.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: springCloudAll
 * @Date: 2020/5/12 20:43
 * @Author: lzx
 * @Description: Security配置
 */
@Configuration
@EnableWebSecurity
//开启Security注解支持
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;


    @Autowired
    private CustomUserService customUserService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源无需认证就可以访问
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/img/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = httpServletResponse.getWriter();
                Msg msg = new Msg("测试标题", "测试内容", "权限不足，请联系管理员!");
                out.write(new ObjectMapper().writeValueAsString(msg));
                out.flush();
                out.close();
            }
        })
        ; //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable();

    }


    /**
     * 密码加密方式
     *
     * @return
     */
    @Bean
    static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //   return new BCryptPasswordEncoder();
    }

    /**
     * Security的基于内存的认证.
     * <p>
     * 首先是Security重写configureGlobal(AuthenticationManagerBuilder auth)方法,(基于内存配置用户的角色的时候是不需要ROLE_前缀的)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //   自己实现 获取用户
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());

    }


}
