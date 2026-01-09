package com.ayurdiet.model;

import javax.persistence.*;
import java.time.LocalDate;
// import java.util.List;

@Entity
@Table(name = "diet_plans")
public class DietPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "plan_name", nullable = false)
    private String planName;
    
    @Column(name = "duration_days")
    private Integer durationDays;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "primary_dosha")
    private String primaryDosha;
    
    @Column(name = "secondary_dosha")
    private String secondaryDosha;
    
    @Column(name = "health_goals", length = 1000)
    private String healthGoals;
    
    @Column(name = "diet_type")
    private String dietType; // Vegetarian, Non-vegetarian, Vegan, etc.
    
    @Column(name = "food_preferences", length = 1000)
    private String foodPreferences;
    
    @Column(name = "allergies", length = 1000)
    private String allergies;
    
    // Store meals as JSON string instead of nested collections
    @Column(name = "meals_json", length = 4000)
    private String mealsJson;
    
    @Column(name = "recommendations", length = 2000)
    private String recommendations;
    
    @Column(name = "created_at")
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    // Constructors
    public DietPlan() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public String getPrimaryDosha() { return primaryDosha; }
    public void setPrimaryDosha(String primaryDosha) { this.primaryDosha = primaryDosha; }
    
    public String getSecondaryDosha() { return secondaryDosha; }
    public void setSecondaryDosha(String secondaryDosha) { this.secondaryDosha = secondaryDosha; }
    
    public String getHealthGoals() { return healthGoals; }
    public void setHealthGoals(String healthGoals) { this.healthGoals = healthGoals; }
    
    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }
    
    public String getFoodPreferences() { return foodPreferences; }
    public void setFoodPreferences(String foodPreferences) { this.foodPreferences = foodPreferences; }
    
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    
    public String getMealsJson() { return mealsJson; }
    public void setMealsJson(String mealsJson) { this.mealsJson = mealsJson; }
    
    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }
    
    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
    
    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }
    
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDate.now();
    }

    public Object getMeals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMeals'");
    }

    public interface Meal {

        String getMealType();

        Object getFoodItems();
    }

    public interface FoodItem {
    }
}