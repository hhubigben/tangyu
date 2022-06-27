package com.example.spring_02.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;



//页面国际化
public class MyLocaleResolver implements LocaleResolver {
    @Override
    //解析请求
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的参数链接
        String language = request.getParameter("lang");

        Locale locale =Locale.getDefault();//如果没有就默认的

        //如果不为空，就是我们传过来的
        if(!StringUtils.isEmpty(language))
        {
            String [] split = language.split("_");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }



}
