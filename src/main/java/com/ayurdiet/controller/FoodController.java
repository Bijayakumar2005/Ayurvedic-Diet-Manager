package com.ayurdiet.controller;

import com.ayurdiet.model.Food;
import com.ayurdiet.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/food")
public class FoodController {
    
    @Autowired
    private FoodService foodService;
    
    // Initialize food data on application start
    @PostConstruct
    public void init() {
        foodService.initializeFoodData();
    }
    
    @GetMapping("/search")
    public String foodSearch(@RequestParam(value = "query", required = false) String query, 
                            @RequestParam(value = "category", required = false) String category,
                            Model model, HttpSession session) {
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        List<Food> foods;
        
        if (query != null && !query.trim().isEmpty()) {
            foods = foodService.searchFoods(query.trim());
            model.addAttribute("searchQuery", query);
        } else if (category != null && !category.trim().isEmpty()) {
            foods = foodService.getFoodsByCategory(category.trim());
            model.addAttribute("selectedCategory", category);
        } else {
            foods = foodService.getAllFoods();
        }
        
        model.addAttribute("foods", foods);
        model.addAttribute("categories", foodService.getAllCategories());
        return "food-search";
    }
    
    @GetMapping("/detail/{id}")
    public String foodDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Food food = foodService.getFoodById(id);
        if (food == null) {
            return "redirect:/food/search";
        }
        
        model.addAttribute("food", food);
        return "food-detail";
    }
}