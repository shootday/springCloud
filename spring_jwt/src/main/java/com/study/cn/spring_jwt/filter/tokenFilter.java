package com.study.cn.spring_jwt.filter;

import com.study.cn.spring_jwt.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * @program: springCloudAll
 * @Date: 2020/5/26 23:14
 * @Author: lzx
 * @Description:
 */
@WebFilter(urlPatterns = "/**")
@Component
public class tokenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("启动。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("dddddd ");
        HttpServletRequest request=( HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        System.out.println(token);
        if(!StringUtils.isEmpty(token)){
            try {
                Boolean parseJWT2 = TokenUtil. parseJWT(token);
                if(parseJWT2){
                    System.out.println("正确");
                    filterChain.doFilter(servletRequest,servletResponse);
                }
            } catch (ParseException e) {
                System.out.println("错误");
                e.printStackTrace();
            }


        }else{
            System.out.println("token无");
        }
    }
}
