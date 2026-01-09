package com.ayurdiet.model;

import java.util.List;

public class Meal {
    private String mealType; // Breakfast, Lunch, Dinner, Snack
    private String time;
    private List<FoodItem> foodItems;
    private String instructions;
    
    // Constructors
    public Meal() {}
    
    public Meal(String mealType, String time, List<FoodItem> foodItems, String instructions) {
        this.mealType = mealType;
        this.time = time;
        this.foodItems = foodItems;
        this.instructions = instructions;
    }
    
    // Getters and setters
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public List<FoodItem> getFoodItems() { return foodItems; }
    public void setFoodItems(List<FoodItem> foodItems) { this.foodItems = foodItems; }
    
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}