package com.ayurdiet.service;

import com.ayurdiet.model.DietPlan;
import com.ayurdiet.model.Meal;
import com.ayurdiet.model.FoodItem;
import com.ayurdiet.model.User;
import com.ayurdiet.repository.DietPlanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DietPlanService {
    
    @Autowired
    private DietPlanRepository dietPlanRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public List<DietPlan> getUserDietPlans(Long userId) {
        return dietPlanRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public DietPlan getDietPlanById(Long id) {
        return dietPlanRepository.findById(id).orElse(null);
    }
    
    public DietPlan saveDietPlan(DietPlan dietPlan) {
        return dietPlanRepository.save(dietPlan);
    }
    
    public void deleteDietPlan(Long id) {
        dietPlanRepository.deleteById(id);
    }
    
    public DietPlan generateDietPlan(User user, String healthGoals, String dietType, 
                                   String foodPreferences, String allergies) {
        DietPlan dietPlan = new DietPlan();
        dietPlan.setUser(user);
        dietPlan.setPlanName("Ayurvedic Diet Plan for " + user.getName());
        dietPlan.setDurationDays(7);
        dietPlan.setStartDate(LocalDate.now());
        dietPlan.setEndDate(LocalDate.now().plusDays(6));
        dietPlan.setHealthGoals(healthGoals);
        dietPlan.setDietType(dietType);
        dietPlan.setFoodPreferences(foodPreferences);
        dietPlan.setAllergies(allergies);
        
        // Determine dosha based on user characteristics (simplified logic)
        String dosha = determineDosha(user);
        dietPlan.setPrimaryDosha(dosha);
        
        try {
            // Generate meals and convert to JSON
            List<Meal> meals = generateMeals(dosha, dietType, allergies);
            String mealsJson = objectMapper.writeValueAsString(meals);
            dietPlan.setMealsJson(mealsJson);
        } catch (JsonProcessingException e) {
            // Handle JSON conversion error
            System.err.println("Error converting meals to JSON: " + e.getMessage());
        }
        
        // Generate recommendations
        dietPlan.setRecommendations(generateRecommendations(dosha, healthGoals));
        
        return dietPlanRepository.save(dietPlan);
    }
    
    // Helper method to get meals from JSON
    public List<Meal> getMealsFromDietPlan(DietPlan dietPlan) {
        if (dietPlan.getMealsJson() == null || dietPlan.getMealsJson().isEmpty()) {
            return new ArrayList<>();
        }
        
        try {
            return objectMapper.readValue(dietPlan.getMealsJson(), 
                objectMapper.getTypeFactory().constructCollectionType(List.class, Meal.class));
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing meals JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private String determineDosha(User user) {
        // Simplified dosha determination logic
        if (user.getAge() != null && user.getWeight() != null && user.getHeight() != null) {
            double bmi = user.getWeight() / Math.pow(user.getHeight() / 100, 2);
            
            if (bmi < 18.5) {
                return "Vata";
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                return "Pitta";
            } else {
                return "Kapha";
            }
        }
        
        // Fallback: random dosha for demo purposes
        String[] doshas = {"Vata", "Pitta", "Kapha"};
        return doshas[new Random().nextInt(doshas.length)];
    }
    
    private List<Meal> generateMeals(String dosha, String dietType, String allergies) {
        List<Meal> meals = new ArrayList<>();
        
        // Breakfast
        List<FoodItem> breakfastItems = new ArrayList<>();
        breakfastItems.add(new FoodItem(getBreakfastFood(dosha), 1.0, "bowl", "Cooked with ghee"));
        Meal breakfast = new Meal("Breakfast", "8:00 AM", breakfastItems, "Eat warm and chew slowly. Avoid cold beverages.");
        meals.add(breakfast);
        
        // Lunch
        List<FoodItem> lunchItems = new ArrayList<>();
        lunchItems.add(new FoodItem(getLunchFood(dosha), 1.0, "plate", "Freshly prepared"));
        lunchItems.add(new FoodItem("Vegetables", 1.0, "bowl", "Steamed or sautéed"));
        Meal lunch = new Meal("Lunch", "1:00 PM", lunchItems, "Main meal of the day. Include all six tastes.");
        meals.add(lunch);
        
        // Dinner
        List<FoodItem> dinnerItems = new ArrayList<>();
        dinnerItems.add(new FoodItem(getDinnerFood(dosha), 1.0, "bowl", "Lightly cooked"));
        Meal dinner = new Meal("Dinner", "7:00 PM", dinnerItems, "Light meal. Finish at least 2 hours before bedtime.");
        meals.add(dinner);
        
        return meals;
    }
    
    private String getBreakfastFood(String dosha) {
        switch (dosha) {
            case "Vata": return "Warm oatmeal with ghee and dates";
            case "Pitta": return "Cooling porridge with coconut and cardamom";
            case "Kapha": return "Light quinoa with spices and steamed apples";
            default: return "Kitchari (balanced meal)";
        }
    }
    
    private String getLunchFood(String dosha) {
        switch (dosha) {
            case "Vata": return "Wheat roti with mung dal and vegetables";
            case "Pitta": return "Basmati rice with coconut vegetable curry";
            case "Kapha": return "Barley roti with chickpea curry and bitter greens";
            default: return "Kitchari with vegetables";
        }
    }
    
    private String getDinnerFood(String dosha) {
        switch (dosha) {
            case "Vata": return "Warm soup with root vegetables";
            case "Pitta": return "Steamed vegetables with quinoa";
            case "Kapha": return "Light vegetable broth with spices";
            default: return "Vegetable soup";
        }
    }
    
    private String generateRecommendations(String dosha, String healthGoals) {
        StringBuilder recommendations = new StringBuilder();
        
        recommendations.append("Based on your primary dosha (").append(dosha).append("), here are recommendations:\n\n");
        
        switch (dosha) {
            case "Vata":
                recommendations.append("- Favor warm, moist, and grounding foods\n");
                recommendations.append("- Avoid cold, dry, and raw foods\n");
                recommendations.append("- Eat at regular times\n");
                recommendations.append("- Include sweet, sour, and salty tastes\n");
                recommendations.append("- Practice gentle yoga and meditation\n");
                break;
            case "Pitta":
                recommendations.append("- Favor cool, refreshing foods\n");
                recommendations.append("- Avoid spicy, oily, and fried foods\n");
                recommendations.append("- Eat in a calm environment\n");
                recommendations.append("- Include sweet, bitter, and astringent tastes\n");
                recommendations.append("- Practice cooling breathing exercises\n");
                break;
            case "Kapha":
                recommendations.append("- Favor light, warm, and dry foods\n");
                recommendations.append("- Avoid heavy, oily, and sweet foods\n");
                recommendations.append("- Engage in regular exercise\n");
                recommendations.append("- Include pungent, bitter, and astringent tastes\n");
                recommendations.append("- Practice vigorous yoga and breathing exercises\n");
                break;
        }
        
        if (healthGoals != null && !healthGoals.isEmpty()) {
            recommendations.append("\nAdditional recommendations for your health goals: ").append(healthGoals);
        }
        
        return recommendations.toString();
    }
}