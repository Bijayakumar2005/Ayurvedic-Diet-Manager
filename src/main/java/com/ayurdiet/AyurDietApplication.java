package com.ayurdiet;

import com.ayurdiet.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AyurDietApplication implements CommandLineRunner {
    
    @Autowired
    private FoodService foodService;
    
    public static void main(String[] args) {
        SpringApplication.run(AyurDietApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize food data on application start
        foodService.initializeFoodData();
    }
}