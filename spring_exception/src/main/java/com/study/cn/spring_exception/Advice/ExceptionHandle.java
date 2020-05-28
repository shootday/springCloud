package com.study.cn.spring_exception.Advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: springCloudAll
 * @Date: 2020/5/15 10:43
 * @Author: lzx
 * @Description:  异常处理器  有这个不会进入 HttpErrorController
 */
//@ControllerAdvice
public class ExceptionHandle {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    //@ResponseBody  返回json格式 用于前后端分离
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        ModelAndView mav = new ModelAndView();

        mav.addObject("exception", e);

        mav.addObject("url", req.getRequestURL());

        mav.setViewName(DEFAULT_ERROR_VIEW);

        return mav;

    }
}
