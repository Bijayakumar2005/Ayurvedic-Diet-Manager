package com.ayurdiet.controller;

import com.ayurdiet.model.DietPlan;
import com.ayurdiet.model.Meal;
import com.ayurdiet.model.User;
import com.ayurdiet.service.DietPlanService;
import com.ayurdiet.service.PdfService;
// import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpSession;
//import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/diet-plan")
public class DietPlanController {
    
    @Autowired
    private DietPlanService dietPlanService;
    
    @Autowired
    private PdfService pdfService;
    
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        if (!model.containsAttribute("dietPlan")) {
            model.addAttribute("dietPlan", new DietPlan());
        }
        
        return "diet-plan-create";
    }
    
    @PostMapping("/generate")
    public String generateDietPlan(@RequestParam String healthGoals,
                                  @RequestParam String dietType,
                                  @RequestParam(required = false) String foodPreferences,
                                  @RequestParam(required = false) String allergies,
                                  HttpSession session,
                                  Model model) {
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        DietPlan dietPlan = dietPlanService.generateDietPlan(user, healthGoals, dietType, foodPreferences, allergies);
        model.addAttribute("dietPlan", dietPlan);
        
        return "redirect:/diet-plan/view/" + dietPlan.getId();
    }
    
    @GetMapping("/view/{id}")
    public String viewDietPlan(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        DietPlan dietPlan = dietPlanService.getDietPlanById(id);
        if (dietPlan == null || !dietPlan.getUser().getId().equals(user.getId())) {
            return "redirect:/diet-plan/list";
        }
        
        // Get meals from JSON
        List<Meal> meals = dietPlanService.getMealsFromDietPlan(dietPlan);
        
        model.addAttribute("dietPlan", dietPlan);
        model.addAttribute("meals", meals);
        return "diet-plan-view";
    }
    
    @GetMapping("/list")
    public String listDietPlans(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<DietPlan> dietPlans = dietPlanService.getUserDietPlans(user.getId());
        model.addAttribute("dietPlans", dietPlans);
        
        return "diet-plan-list";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteDietPlan(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        DietPlan dietPlan = dietPlanService.getDietPlanById(id);
        if (dietPlan != null && dietPlan.getUser().getId().equals(user.getId())) {
            dietPlanService.deleteDietPlan(id);
        }
        
        return "redirect:/diet-plan/list";
    }
    @GetMapping("/download/{id}")
public ResponseEntity<ByteArrayResource> downloadDietPlan(@PathVariable Long id, HttpSession session) {
    
    User user = (User) session.getAttribute("user");
    if (user == null) {
        return ResponseEntity.notFound().build();
    }
    
    DietPlan dietPlan = dietPlanService.getDietPlanById(id);
    if (dietPlan == null || !dietPlan.getUser().getId().equals(user.getId())) {
        return ResponseEntity.notFound().build();
    }
    
    try {
        byte[] pdfBytes = pdfService.generateDietPlanPdf(dietPlan);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);
        
        String fileName = "Ayurvedic-Diet-Plan-" + dietPlan.getUser().getName() + ".pdf";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(resource);
    } catch (Exception e) {
        // Log the error and return an error response
        System.err.println("Error generating PDF: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}