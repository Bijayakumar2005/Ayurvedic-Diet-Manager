package com.ayurdiet.model;

import javax.persistence.*;

@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    private String category;
    private String description;
    
    // Nutritional values
    private Double calories;
    private Double protein;
    private Double carbohydrates;
    private Double fat;
    private Double fiber;
    
    // Ayurvedic properties
    private String rasa; // Taste
    private String virya; // Energy
    private String vipaka; // Post-digestive effect
    private String dosha; // Effect on doshas
    
    @Column(length = 1000)
    private String benefits;
    
    @Column(length = 1000)
    private String precautions;
    
    // Constructors
    public Food() {}
    
    public Food(String name, String category, String description, Double calories, 
                Double protein, Double carbohydrates, Double fat, Double fiber, 
                String rasa, String virya, String vipaka, String dosha, 
                String benefits, String precautions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.fiber = fiber;
        this.rasa = rasa;
        this.virya = virya;
        this.vipaka = vipaka;
        this.dosha = dosha;
        this.benefits = benefits;
        this.precautions = precautions;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }
    
    public Double getProtein() { return protein; }
    public void setProtein(Double protein) { this.protein = protein; }
    
    public Double getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(Double carbohydrates) { this.carbohydrates = carbohydrates; }
    
    public Double getFat() { return fat; }
    public void setFat(Double fat) { this.fat = fat; }
    
    public Double getFiber() { return fiber; }
    public void setFiber(Double fiber) { this.fiber = fiber; }
    
    public String getRasa() { return rasa; }
    public void setRasa(String rasa) { this.rasa = rasa; }
    
    public String getVirya() { return virya; }
    public void setVirya(String virya) { this.virya = virya; }
    
    public String getVipaka() { return vipaka; }
    public void setVipaka(String vipaka) { this.vipaka = vipaka; }
    
    public String getDosha() { return dosha; }
    public void setDosha(String dosha) { this.dosha = dosha; }
    
    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }
    
    public String getPrecautions() { return precautions; }
    public void setPrecautions(String precautions) { this.precautions = precautions; }
}