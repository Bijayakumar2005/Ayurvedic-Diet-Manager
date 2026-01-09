package com.ayurdiet.service;

import com.ayurdiet.model.Food;
import com.ayurdiet.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
    
    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }
    
    public List<Food> searchFoods(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllFoods();
        }
        return foodRepository.searchFoods(query.trim());
    }
    
    public List<Food> getFoodsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return getAllFoods();
        }
        return foodRepository.findByCategoryContainingIgnoreCase(category.trim());
    }
    
    public List<String> getAllCategories() {
        return foodRepository.findAllCategories();
    }
    
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }
    
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
    
    public void initializeFoodData() {
        // Only add foods if database is empty
        if (foodRepository.count() == 0) {
            // Grains
            foodRepository.save(new Food(
                "Brown Rice", "Grains", "Whole grain rice with bran layer intact", 
                111.0, 2.6, 23.0, 0.9, 1.8, 
                "Sweet", "Cooling", "Sweet", "Vata balancing, Pitta balancing, Kapha increasing in excess",
                "Good for digestion, provides energy, supports heart health", 
                "May increase Kapha if consumed in excess"
            ));
            
            foodRepository.save(new Food(
                "Quinoa", "Grains", "Gluten-free grain rich in protein", 
                120.0, 4.4, 21.3, 1.9, 2.8, 
                "Sweet", "Cooling", "Sweet", "Tridoshic (balanced for all doshas)",
                "High in protein, contains all essential amino acids, gluten-free", 
                "Should be rinsed well to remove saponins"
            ));
            
            // Fruits
            foodRepository.save(new Food(
                "Apple", "Fruits", "Sweet and crunchy fruit", 
                52.0, 0.3, 14.0, 0.2, 2.4, 
                "Sweet, Astringent", "Cooling", "Sweet", "Vata balancing, Pitta balancing, Kapha balancing in moderation",
                "Supports digestion, rich in antioxidants, good for heart health", 
                "May cause gas in some individuals when eaten in excess"
            ));
            
            foodRepository.save(new Food(
                "Pomegranate", "Fruits", "Juicy fruit with edible seeds", 
                83.0, 1.7, 18.7, 1.2, 4.0, 
                "Sweet, Astringent", "Cooling", "Sweet", "Pitta balancing, Kapha balancing",
                "Rich in antioxidants, supports heart health, anti-inflammatory", 
                "May increase Vata if consumed in excess"
            ));
            
            // Vegetables
            foodRepository.save(new Food(
                "Spinach", "Vegetables", "Leafy green vegetable", 
                23.0, 2.9, 3.6, 0.4, 2.2, 
                "Sweet, Astringent", "Cooling", "Sweet", "Pitta balancing, Vata increasing, Kapha balancing",
                "Rich in iron, supports eye health, anti-inflammatory", 
                "Contains oxalates, should be cooked for better absorption"
            ));
            
            foodRepository.save(new Food(
                "Ginger", "Vegetables", "Aromatic rhizome used as spice", 
                80.0, 1.8, 17.8, 0.8, 2.0, 
                "Pungent", "Heating", "Sweet", "Vata balancing, Kapha balancing, Pitta increasing in excess",
                "Aids digestion, reduces nausea, anti-inflammatory", 
                "May aggravate Pitta if consumed in large quantities"
            ));
            
            // Legumes
            foodRepository.save(new Food(
                "Mung Bean", "Legumes", "Small green beans", 
                106.0, 7.0, 19.2, 0.4, 7.6, 
                "Sweet", "Cooling", "Sweet", "Tridoshic (balanced for all doshas)",
                "Easy to digest, high in protein, detoxifying", 
                "Should be soaked before cooking for better digestion"
            ));
            
            foodRepository.save(new Food(
                "Chickpeas", "Legumes", "Round, beige legumes", 
                139.0, 7.3, 22.5, 2.2, 6.3, 
                "Sweet", "Heating", "Sweet", "Vata balancing, Pitta increasing, Kapha increasing",
                "Good source of protein and fiber, supports blood sugar control", 
                "May cause gas, should be well cooked with digestive spices"
            ));
            
            // Dairy
            foodRepository.save(new Food(
                "Ghee", "Dairy", "Clarified butter", 
                900.0, 0.3, 0.0, 99.5, 0.0, 
                "Sweet", "Cooling", "Sweet", "Tridoshic (balanced for all doshas)",
                "Enhances digestion, improves memory, lubricates tissues", 
                "Should be consumed in moderation due to high calorie content"
            ));
            
            foodRepository.save(new Food(
                "Yogurt", "Dairy", "Fermented milk product", 
                59.0, 3.5, 4.7, 3.3, 0.0, 
                "Sour, Sweet", "Heating", "Sour", "Vata balancing, Pitta increasing, Kapha increasing",
                "Source of probiotics, supports gut health, provides calcium", 
                "Should be avoided at night and by those with high Pitta or Kapha"
            ));
            
            // Spices
            foodRepository.save(new Food(
                "Turmeric", "Spices", "Golden-yellow spice", 
                354.0, 7.8, 64.9, 9.9, 21.1, 
                "Bitter, Pungent", "Heating", "Pungent", "Tridoshic (balanced for all doshas)",
                "Anti-inflammatory, antioxidant, supports joint health", 
                "Should be used with black pepper for better absorption"
            ));
            
            foodRepository.save(new Food(
                "Cumin", "Spices", "Aromatic seeds", 
                375.0, 17.8, 44.2, 22.3, 10.5, 
                "Pungent", "Cooling", "Pungent", "Vata balancing, Pitta balancing, Kapha balancing",
                "Aids digestion, reduces gas, enhances nutrient absorption", 
                "Should be lightly roasted for better flavor and digestibility"
            ));
        }
    }
}