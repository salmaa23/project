package com.example.taskmanager.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test-dashboard")
    public String dashboard() {
        return "testDashboard"; // this will return your testDashboard.html page
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
