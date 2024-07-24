package com.sw.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/")
    public String index() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String registra() {
        return "registraForm";
    }
}
