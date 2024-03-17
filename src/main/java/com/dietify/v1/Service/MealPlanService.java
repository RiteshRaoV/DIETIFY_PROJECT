package com.dietify.v1.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MealPlanService {

    @Value("${spoonacular.urls.baseurl}")
    private String baseURL;

    @Value("${apikey}")
    private String apiKey;
}
