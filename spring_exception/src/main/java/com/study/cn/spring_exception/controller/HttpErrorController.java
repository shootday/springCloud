package com.study.cn.spring_exception.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: springCloudAll
 * @Date: 2020/5/15 10:17
 * @Author: lzx
 * @Description: 系统默认的错误处理类为BasicErrorController，将会显示如上的错误页面。
 * 这里编写一个自己的错误处理类，上面默认的处理类将不会起作用。
 * getErrorPath()返回的路径服务器将会重定向到该路径对应的处理类，本例中为error方法。
 */
@Controller
public class HttpErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * Web页面错误处理
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String error(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("访问/error" + " 错误代码：" + response.getStatus());
        //获取异常返回
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == 401) {
            return "401";
        } else if (statusCode == 404) {
            return "404";
        } else if (statusCode == 403) {
            return "403";
        } else {
            return "500";
        }

    }


    /**
     * JSON格式错误信息
     */
    @ResponseBody
    @RequestMapping(value = ERROR_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String error(HttpServletRequest request) {
        //RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        // Map<String, Object> body = this.errorAttributes.getErrorAttributes(requestAttributes, true);
        Map<String, Object> body = getErrorAttributes(request, true);
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        System.out.println(body);
        System.out.println(exception.getCause()+"-=================");
        if (exception.getCause() instanceof NullPointerException) {
            System.out.println("111111111111111");
        }
        System.out.println(exception + "--------------");
        return "服务器端异常！" + body.get("status") + "---" + body.get("message") + "---" + body.get("trace");
    }


    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(servletWebRequest,
                includeStackTrace);
    }


    private int getStatus(HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            return status;
        }

        return 500;
    }
}
