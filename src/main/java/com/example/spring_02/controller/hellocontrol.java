package com.example.spring_02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellocontrol {
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

}

