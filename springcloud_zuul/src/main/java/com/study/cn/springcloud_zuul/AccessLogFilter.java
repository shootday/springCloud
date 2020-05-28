package com.study.cn.springcloud_zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: homepage-springCloud
 * @Date: 2020/4/2 15:24
 * @Author: lzx
 * @Description: 打印请求的时间戳
 */

@Slf4j
@Component
public class AccessLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;  //zull默认post之后
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("------AccessLogFilter");
        //用于在过滤器之间传递消息
        RequestContext requestContent = RequestContext.getCurrentContext();
        Long starttime = (Long) requestContent.get("starttime");

        HttpServletRequest request = requestContent.getRequest();
        String uri = request.getRequestURI();

        long duration = System.currentTimeMillis() - starttime;
        log.info("URi:{}, duration: {}ms", uri, duration/100);
        return null;
    }
}
