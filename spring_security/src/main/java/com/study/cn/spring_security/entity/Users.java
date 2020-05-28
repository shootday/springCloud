package com.study.cn.spring_security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * @program: springCloudAll
 * @Date: 2020/5/18 10:44
 * @Author: lzx
 * @Description: 登录用户  该体类最后与系统中用户的实体类分开
 */
@Data //自动为所有字段添加@ToString, @EqualsAndHashCode, @Getter方法，为非final字段添加@Setter
@NoArgsConstructor //自动生成无参数构造函数。
@AllArgsConstructor  //自动生成全参数构造函数
public class Users implements Serializable, UserDetails {


    private String username;

    private String password;

    private boolean isEnabled;

    //List<GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("role"));
    }

    //账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //帐户密码是否未过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
