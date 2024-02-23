package com.dietify.v1.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dietify.v1.Entity.UserMealPlan;
import com.dietify.v1.Repository.UserMealPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserMealPlanService {
    @Autowired
    private UserMealPlanRepository userMealPlanRepository;

    public void saveUserMealPlan(String userId, String type, Object data) {
        UserMealPlan userMealPlan = new UserMealPlan();
        userMealPlan.setUserId(userId);
        userMealPlan.setType(type);
        userMealPlan.setData(data);
        userMealPlanRepository.save(userMealPlan);
    }
    public List<UserMealPlan> getUserMealPlansByUserIdAndType(String userId, String type) {
        return userMealPlanRepository.findByUserIdAndType(userId, type);
    }
}


