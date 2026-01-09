package com.ayurdiet.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
    
    @ModelAttribute
    public void globalAttributes(Model model, HttpSession session) {
        // Add user session attribute to all models
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        
        // Add dark mode preference
        Boolean darkMode = (Boolean) session.getAttribute("darkMode");
        if (darkMode == null) {
            darkMode = false;
            session.setAttribute("darkMode", darkMode);
        }
        model.addAttribute("darkMode", darkMode);
    }
}