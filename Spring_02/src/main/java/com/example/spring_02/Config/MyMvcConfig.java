package com.example.spring_02.Config;
//拓展springMvc

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;
//扩展springmvc  官方建议这么做。
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html",
                "/","/user/login","/css/**","/js/**","/img/**","/cjj.html");
    }







    //扩展视图解析器
    //ViewResolver 实现了视图解析器接口的类   可以给他看做是视图解析器
//@Bean
//public ViewResolver myViewResolver()
//{
//    return new Myviewresolver();
//}


//   // 自己定义一个视图解析器
//public static class Myviewresolver implements ViewResolver {
//    @Override
//    public View resolveViewName(String viewName, Locale locale) throws Exception {
//        return null;
//    }
//}
}
