package com.first.babylog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/index.html
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signUp"; // templates/singUp.html
    }
    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        return "forgetPassword"; // templates/singUp.html
    }


}
