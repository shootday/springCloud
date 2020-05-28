package com.study.cn.springcloud_zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @program: homepage-springCloud
 * @Date: 2020/4/2 15:17
 * @Author: lzx
 * @Description: 在过滤器中存储客户端发起请求的时间戳
 */
@Component
public class PreRequstFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;  //过滤器类型
    }

    @Override
    public int filterOrder() {
        return 0; //越小优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;  //是否启用当前的过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("------PreRequstFilter");
        //用于在过滤器之间传递消息
        RequestContext requestContent = RequestContext.getCurrentContext();
        requestContent.set("starttime", System.currentTimeMillis());
        return null;
    }
}
