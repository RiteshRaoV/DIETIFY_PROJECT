package com.dietify.v1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dietify.v1.DTO.RecipeDetails.AnalyzedInstruction;
import com.dietify.v1.DTO.RecipeDetails.ExtendedIngredient;
import com.dietify.v1.DTO.RecipeDetails.Recipe;


@RestController
// @RequestMapping("/recipes")
public class RecipeController {

    @Value("${apikey}")  // Define your API key in application.properties
    private String apiKey;

    private final RestTemplate restTemplate;

    public RecipeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/recipe")
    public ResponseEntity<Recipe> getRecipeDetails(@RequestParam String id) {
        String apiUrl = "https://api.spoonacular.com/recipes/{id}/information?apiKey={apiKey}";
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

            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
