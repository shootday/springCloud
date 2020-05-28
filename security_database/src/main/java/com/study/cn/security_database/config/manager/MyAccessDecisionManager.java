package com.study.cn.security_database.config.manager;

import com.study.cn.security_database.entity.MyGrantedAuthority;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @program: springCloudAll
 * @Date: 2020/5/21 16:13
 * @Author: lzx
 * @Description: 自定义的权限控制管理器
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    // decide 方法是判定是否拥有权限的决策方法，
    //authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
    //object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    //configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {


        /**
         * 验证请求的方式以及有没有权限
         */
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        String url, method;
        AntPathRequestMatcher matcher;
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga instanceof MyGrantedAuthority) {
                MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
                url = urlGrantedAuthority.getUrl();
                System.out.println(url+"----url");
                method = urlGrantedAuthority.getMethod() ;
                matcher = new AntPathRequestMatcher(url);
                System.out.println(matcher.matches(request)+"---matcherRequest");
                if (matcher.matches(request)) {
                    System.out.println("11111111111111");
                    //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                    if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                        return;
                    }
                }
            } else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {//未登录只允许访问 login 页面
                matcher = new AntPathRequestMatcher("/login");
                if (matcher.matches(request)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(" No authority to access！");


        /**下面这个不验证请求的方式**/
//
//        /**
//         * 如果请求的资源不需要权限，则直接放行
//         */
//        if (null == collection || collection.size() <= 0) {
//            return;
//        }
//
//        ConfigAttribute c;
//        String needRole;
//
//        /**
//         * 判断用户所拥有的权限是否是资源所需要的权限之一，如果是则放行，否则拦截
//         */
//        for (Iterator<ConfigAttribute> iter = collection.iterator(); iter.hasNext(); ) {
//            c = iter.next();
//            needRole = c.getAttribute();
//            for (GrantedAuthority ga : authentication.getAuthorities()) {//authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
//                if (needRole.trim().equals(ga.getAuthority())) {
//                    return;
//                }
//            }
//        }
//        // 没有权限让我们去捕捉
//        throw new AccessDeniedException(" No authority to access！");

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
