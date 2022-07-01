package com.example.spring_02.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session){
        Connection con ;
        PreparedStatement pStmt;
        ResultSet res;
        String password1="";
        String name="";
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/highway", "root", "Ty20021104.");
            System.out.println("good");
            String sql = "SELECT * FROM user where 用户名=?";
            pStmt=con.prepareStatement(sql);
            pStmt.setString(1,username);
            res=pStmt.executeQuery();
            if(res.next())
            {
                 password1 = res.getString(2);
                 name = res.getString(3);
            }
            if(password.equals(password1))
            {
                //登陆成功
                session.setAttribute("loginuser",name);
                return "redirect:/main.html";
            }
            else {
                model.addAttribute("msg","用户名或者密码错误");
                return "index";
            }


        } catch (
                Exception e) {
            System.out.println(e);
        }
        return "index";

    }





    //注销
    @RequestMapping("/user/logout")
    public String userLogout(HttpSession session)

    {
        session.invalidate();
        return "redirect:/index.html";
    }



}
