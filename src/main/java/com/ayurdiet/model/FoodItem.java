package com.ayurdiet.model;

public class FoodItem {
    private String name;
    private Double quantity;
    private String unit;
    private String preparation;
    
    // Constructors
    public FoodItem() {}
    
    public FoodItem(String name, Double quantity, String unit, String preparation) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.preparation = preparation;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public String getPreparation() { return preparation; }
    public void setPreparation(String preparation) { this.preparation = preparation; }
}