package com.ayurdiet.repository;

import com.ayurdiet.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
    List<DietPlan> findByUserId(Long userId);
    List<DietPlan> findByUserIdOrderByCreatedAtDesc(Long userId);
    void deleteByUserId(Long userId);
    
    // Add this method if it doesn't exist
    long count();
}