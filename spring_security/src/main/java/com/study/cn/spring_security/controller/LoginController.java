package com.study.cn.spring_security.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @program: springCloudAll
 * @Date: 2020/5/12 20:37
 * @Author: lzx
 * @Description:
 */
@Controller
public class LoginController {

    @GetMapping("login.html")
    public String login() {
        return "login";
    }


    @GetMapping("error.html")
    public String errorhtml() {
        return "error";
    }

    @ResponseBody
    @RequestMapping("error2")
    public void erro2() {
        System.out.println("111111111");

    }

    @RequestMapping("success")
    public String success() {
        System.out.println("success");
        return "/index";
    }

    @GetMapping("403")
    public String err403() {
        return "403";
    }


    @Autowired
    private Producer captchaProducer;


    @GetMapping("/kaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
