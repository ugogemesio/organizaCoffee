package com.sw.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // @GetMapping("/login")
    // public String login() {
    //     return "login";
    // }

    @GetMapping("/login")
    public String home() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String registra() {
        return "registraForm";
    }
}
