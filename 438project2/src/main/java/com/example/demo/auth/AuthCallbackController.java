package com.example.demo.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthCallbackController {

    @GetMapping("/auth/callback")
    public String callback() {
        return "auth-callback";
    }
}