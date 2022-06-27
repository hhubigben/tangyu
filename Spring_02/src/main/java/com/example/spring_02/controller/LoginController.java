package com.example.spring_02.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session){

        if(!StringUtils.isEmpty(username)&&"123456".equals(password))
        {
            //登陆成功
            session.setAttribute("loginuser",username);
            return "redirect:/main.html";
        }
        else {
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }

    }





    //注销
    @RequestMapping("/user/logout")
    public String userLogout(HttpSession session)

    {
        session.invalidate();
        return "redirect:/index.html";
    }



}
