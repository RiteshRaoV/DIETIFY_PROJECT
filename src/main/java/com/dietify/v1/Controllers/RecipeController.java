package com.dietify.v1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.DTO.RecipeDetails.AnalyzedInstruction;
import com.dietify.v1.DTO.RecipeDetails.ExtendedIngredient;
import com.dietify.v1.DTO.RecipeDetails.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class RecipeController {

    @Value("${apikey}") 
    private String apiKey;

    private final RestTemplate restTemplate;

    public RecipeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/recipe")
    public String getRecipeDetails(Model model) {
        // String apiUrl = "https://api.spoonacular.com/recipes/{id}/information?apiKey={apiKey}";
        String jsonstring="{\r\n" + //
                        "    \r\n" + //
                        "    \"image\": \"https://spoonacular.com/recipeImages/716429-556x370.jpg\",\r\n" + //
                        "    \r\n" + //
                        "    \"servings\": 2,\r\n" + //
                        "\"readyInMinutes\": 45,\r\n" + //
                        "    \r\n" + //
                        "   \"analyzedInstructions\": [\r\n" + //
                        "    {\r\n" + //
                        "      \"name\": \"\",\r\n" + //
                        "      \"steps\": [\r\n" + //
                        "        {\r\n" + //
                        "          \"number\": 1,\r\n" + //
                        "          \"step\": \"Spread the pesto over each piece of naan bread evenly.Top with the figs and then crumble goat cheese on top.\",\r\n" + //
                        "          \"ingredients\": [\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"goat cheese\",\r\n" + //
                        "              \"image\": \"goat-cheese.jpg\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"naan\",\r\n" + //
                        "              \"image\": \"naan.png\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"spread\",\r\n" + //
                        "              \"image\": \"\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"pesto\",\r\n" + //
                        "              \"image\": \"basil-pesto.png\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"figs\",\r\n" + //
                        "              \"image\": \"figs-fresh.jpg\"\r\n" + //
                        "            }\r\n" + //
                        "          ]\r\n" + //
                        "        },\r\n" + //
                        "\t{\r\n" + //
                        "          \"number\": 1,\r\n" + //
                        "          \"step\": \"Spread the pesto over each piece of naan bread evenly.Top with the figs and then crumble goat cheese on top.\",\r\n" + //
                        "          \"ingredients\": [\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"goat cheese\",\r\n" + //
                        "              \"image\": \"goat-cheese.jpg\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"naan\",\r\n" + //
                        "              \"image\": \"naan.png\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"spread\",\r\n" + //
                        "              \"image\": \"\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"pesto\",\r\n" + //
                        "              \"image\": \"basil-pesto.png\"\r\n" + //
                        "            },\r\n" + //
                        "            {\r\n" + //
                        "              \"name\": \"figs\",\r\n" + //
                        "              \"image\": \"figs-fresh.jpg\"\r\n" + //
                        "            }\r\n" + //
                        "          ]\r\n" + //
                        "        }\r\n" + //
                        "      ]\r\n" + //
                        "    }\r\n" + //
                        "  ],\r\n" + //
                        "\r\n" + //
                        "\r\n" + //
                        "    \"extendedIngredients\": [\r\n" + //
                        "        {\r\n" + //
                        "           \r\n" + //
                        "            \"amount\": 1.0,\r\n" + //
                        "            \"name\": \"butter\",\r\n" + //
                        "            \"unit\": \"tbsp\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "            \r\n" + //
                        "            \"amount\": 2.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"cauliflower florets\",\r\n" + //
                        "          \r\n" + //
                        "            \"unit\": \"cups\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "            \"amount\": 2.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"cheese\",\r\n" + //
                        "          \r\n" + //
                        "            \"unit\": \"tbsp\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "           \"amount\" : 4.0,\r\n" + //
                        "            \"name\": \"extra virgin olive oil\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"tbsp\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "          \r\n" + //
                        "            \"amount\": 5.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"garlic\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"cloves\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "            \r\n" + //
                        "            \"amount\": 6.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"pasta\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"ounces\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "           \r\n" + //
                        "            \"amount\": 2.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"red pepper flakes\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"pinches\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "           \r\n" + //
                        "            \"amount\": 2.0,\r\n" + //
                        "            \r\n" + //
                        "            \"name\": \"salt and pepper\",\r\n" + //
                        "            \r\n" + //
                        "            \"unit\": \"servings\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "           \r\n" + //
                        "            \"amount\": 3.0,\r\n" + //
                        "            \r\n" + //
                        "            \"name\": \"scallions\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"tbsp\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "            \r\n" + //
                        "            \"amount\": 2.0,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"white wine\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"tbsp\"\r\n" + //
                        "        },\r\n" + //
                        "        {\r\n" + //
                        "            \r\n" + //
                        "            \"amount\": 0.25,\r\n" + //
                        "           \r\n" + //
                        "            \"name\": \"whole wheat bread crumbs\",\r\n" + //
                        "           \r\n" + //
                        "            \"unit\": \"cup\"\r\n" + //
                        "        }\r\n" + //
                        "    ]\r\n" + //
                        "}";

            ObjectMapper mapper = new ObjectMapper();
		try {
			Recipe Response = mapper.readValue(jsonstring, Recipe.class);

			model.addAttribute("recipe", Response);
            System.out.println(Response);
            System.out.println("---------------"+Response.getReadyInMinutes());
            return "recipe";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";


















        // Recipe response = restTemplate.getForObject(apiUrl, Recipe.class, id, apiKey);

        // if (response != null) {
        //     Recipe recipe = new Recipe();
        //     recipe.setServings(response.getServings());
        //     recipe.setReadyInMinutes(response.getReadyInMinutes());
        //     recipe.setImage(response.getImage());

        //     // Extract only necessary information from extendedIngredients
        //     List<ExtendedIngredient> extendedIngredients = response.getExtendedIngredients();
        //     recipe.setExtendedIngredients(extendedIngredients);

        //     // Extract only necessary information from analyzedInstructions
        //     List<AnalyzedInstruction> analyzedInstructions = response.getAnalyzedInstructions();
        //     recipe.setAnalyzedInstructions(analyzedInstructions);

        //     return ResponseEntity.ok(recipe);
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
    }
}
