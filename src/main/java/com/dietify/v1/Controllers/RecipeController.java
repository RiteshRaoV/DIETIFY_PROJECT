package com.dietify.v1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.dietify.v1.DTO.RecipeDetails.AnalyzedInstruction;
import com.dietify.v1.DTO.RecipeDetails.ExtendedIngredient;
import com.dietify.v1.DTO.RecipeDetails.Recipe;


@Controller
public class RecipeController {

    @Value("${apikey}") 
    private String apiKey;

    private final RestTemplate restTemplate;

    public RecipeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/recipe")
    public String getRecipeDetails(@RequestParam("id") String id,Model model) {
        String apiUrl = "https://api.spoonacular.com/recipes/"+id+"/information?apiKey="+apiKey;
        System.out.println("-------------------------------");
        Recipe response = restTemplate.getForObject(apiUrl, Recipe.class, id, apiKey);

        if (response != null) {
            Recipe recipe = new Recipe();
            recipe.setServings(response.getServings());
            recipe.setReadyInMinutes(response.getReadyInMinutes());
            recipe.setImage(response.getImage());

            // Extract only necessary information from extendedIngredients
            List<ExtendedIngredient> extendedIngredients = response.getExtendedIngredients();
            recipe.setExtendedIngredients(extendedIngredients);

            // Extract only necessary information from analyzedInstructions
            List<AnalyzedInstruction> analyzedInstructions = response.getAnalyzedInstructions();
            recipe.setAnalyzedInstructions(analyzedInstructions);

            model.addAttribute("recipe", recipe);
            return "recipedetails";
        } else {
            return "errorpage";
        }
    }
}