package com.dietify.v1.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dietify.v1.Entity.UserMealPlan;

@Repository
public interface UserMealPlanRepository extends MongoRepository<UserMealPlan, String> {
    // Add custom query methods if needed
    List<UserMealPlan> findByUserIdAndType(String userId, String type);

}
