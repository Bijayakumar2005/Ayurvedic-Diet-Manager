package com.ayurdiet.controller;

import com.ayurdiet.model.User;
import com.ayurdiet.service.UserService;
//import com.ayurdiet.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;
    
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        // Get fresh user data from database
        Optional<User> currentUser = userService.getUserById(sessionUser.getId());
        if (currentUser.isPresent()) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", currentUser.get());
            }
            return "profile";
        } else {
            return "redirect:/login";
        }
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute User updatedUser,
                               BindingResult result,
                               @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        // Check for validation errors
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", updatedUser);
            return "redirect:/user/profile";
        }
        
        Optional<User> optionalUser = userService.getUserById(sessionUser.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            // Update user fields
            user.setName(updatedUser.getName());
            user.setAge(updatedUser.getAge());
            user.setGender(updatedUser.getGender());
            user.setWeight(updatedUser.getWeight());
            user.setHeight(updatedUser.getHeight());
            user.setNotes(updatedUser.getNotes());
            
            // Handle image upload
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Delete old image if exists
                    if (user.getPhotoUrl() != null) {
                        userService.deleteImage(user.getPhotoUrl());
                    }
                    
                    // Save new image
                    String fileName = userService.saveImage(imageFile);
                    user.setPhotoUrl(fileName);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
                    return "redirect:/user/profile";
                }
            }
            
            userService.updateUser(user);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        }
        
        return "redirect:/user/profile";
    }
    
    @PostMapping("/profile/delete")
    public String deleteProfile(HttpSession session, RedirectAttributes redirectAttributes) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        
        try {
            // Delete user image if exists
            if (sessionUser.getPhotoUrl() != null) {
                userService.deleteImage(sessionUser.getPhotoUrl());
            }
            
            // Delete user from database
            userService.deleteUser(sessionUser.getId());
            session.invalidate();
            redirectAttributes.addFlashAttribute("success", "Your account has been deleted successfully");
            return "redirect:/";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete account: " + e.getMessage());
            return "redirect:/user/profile";
        }
    }
}