package com.ayurdiet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    
    @GetMapping("/")
    public String home(HttpSession session) {
        return "index";
    }
    
    @GetMapping("/food-search")
    public String foodSearchRedirect(HttpSession session) {
        // Redirect to the actual food search page with authentication check
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "redirect:/food/search";
    }
}