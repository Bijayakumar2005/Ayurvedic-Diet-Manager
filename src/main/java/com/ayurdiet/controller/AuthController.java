package com.ayurdiet.controller;

import com.ayurdiet.model.User;
import com.ayurdiet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")  // This is the correct mapping for login
    public String loginForm(Model model, HttpSession session) {
        // Redirect to dashboard if already logged in
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "login";
    }
    
    @GetMapping("/signup")  // This is the correct mapping for signup
    public String signupForm(Model model, HttpSession session) {
        // Redirect to dashboard if already logged in
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "signup";
    }
    
    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("user") User user, 
                              BindingResult result,
                              @RequestParam("confirmPassword") String confirmPassword,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        // Redirect to dashboard if already logged in
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        
        // Check for validation errors
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }
        
        // Check if passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }
        
        // Check if email already exists
        if (userService.emailExists(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/signup";
        }
        
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("success", "Registration successful. Please login.");
        return "redirect:/login";
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, 
                           @RequestParam String password,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        // Redirect to dashboard if already logged in
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        
        Optional<User> user = userService.loginUser(email, password);
        if (user.isPresent()) {
            session.setAttribute("user", user.get());
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}