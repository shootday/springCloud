package com.study.cn.security_database.controller;

import com.study.cn.security_database.entity.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: springCloudAll
 * @Date: 2020/5/20 16:31
 * @Author: lzx
 * @Description:
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/")
    public String index(Model model) {
        Msg msg = new Msg("测试标题", "测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", msg);
        return "home2";
    }


    @RequestMapping("/admin")
    @ResponseBody
    public String hello() {
        return "hello admin";
    }


    @RequestMapping("/admin/s")
    @ResponseBody
    public String hellossss() {
        return "hello adminsssssss";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getList() {
        return "hello getList";
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public String save() {
        return "hello save";
    }


    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ResponseBody
    public String update() {
        return "hello update";
    }
}