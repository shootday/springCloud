package com.study.cn.spring_security.config;

import com.google.code.kaptcha.Constants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: springCloudAll
 * @Date: 2020/5/20 10:23
 * @Author: lzx
 * @Description: 图形验证码验证拦截器
 */
public class CodeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getServletPath();


        if (uri.equals("/login") && req.getMethod().equalsIgnoreCase("post")) {


            String sessionCode = req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
            String formCode = req.getParameter("code").trim();

            if (StringUtils.isEmpty(formCode)) {
                throw new RuntimeException("验证码不能为空");
            }

            if (sessionCode.equalsIgnoreCase(formCode)) {

                System.out.println("验证通过");

            } else {
                System.out.println(req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
                throw new AuthenticationServiceException("验证码错误");
            }

        }

        chain.doFilter(request, response);
    }
}