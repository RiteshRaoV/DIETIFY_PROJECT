package com.dietify.v1.Controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.dietify.v1.DTO.Formdata;
import com.dietify.v1.DTO.Day.Day;
import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.DTO.Week.Week;
import com.dietify.v1.DTO.Week.WeekResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mealplanner")
public class MealController {

	@Value("${spoonacular.urls.baseurl}")
	private String baseURL;

	@Value("${apikey}")
	private String apiKey;

	@GetMapping("/day")
	public String day(Model model,HttpSession session){
		model.addAttribute("dayResponse", session.getAttribute("dayResponse"));
		return "day-list";
	}
	@PostMapping("/day")
	public String getDayMeals(@ModelAttribute Formdata formdata, Model model, HttpSession session) {
		session.removeAttribute("dayResponse");

		// RestTemplate rt = new RestTemplate();

		// URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
		// .queryParam("timeFrame", "day")
		// .queryParamIfPresent("targetCalories",
		// Optional.ofNullable(formdata.getTargetCalories()))
		// .queryParamIfPresent("diet", Optional.ofNullable(formdata.getDiet()))
		// .queryParam("apiKey", apiKey)
		// .build()
		// .toUri();

		// ResponseEntity<DayResponse> response = rt.getForEntity(uri,
		// DayResponse.class);
		// if (response.getStatusCode().is2xxSuccessful()) {
		// DayResponse dayResponse = response.getBody();
		// if (dayResponse != null && dayResponse.getMeals() != null) {
		// dayResponse.getMeals().forEach(meal -> {
		// int id = meal.getId();
		// String imageURL = "https://spoonacular.com/recipeImages/" + id +
		// "-312x231.jpg";
		// meal.setSourceUrl(imageURL);
		// });
		// }
		// session.setAttribute("dayResponse", dayResponse);
		// }
		// int calories = formdata.getTargetCalories();
		// String diet = formdata.getDiet();
		// model.addAttribute("calories", calories);
		// model.addAttribute("Diet", diet);
		// model.addAttribute("dayResponse", response.getBody());
		String jsonString = "{\n" + //
				"    \"meals\": [\n" + //
				"        {\n" + //
				"            \"id\": 649824,\n" + //
				"            \"imageType\": \"jpg\",\n" + //
				"            \"title\": \"Lemon Zucchini Muffins\",\n" + //
				"            \"readyInMinutes\": 45,\n" + //
				"            \"servings\": 10,\n" + //
				"            \"sourceUrl\": \"https://spoonacular.com/recipeImages/649824-312x231.jpg\"\n" + //
				"        },\n" + //
				"        {\n" + //
				"            \"id\": 642777,\n" + //
				"            \"imageType\": \"jpg\",\n" + //
				"            \"title\": \"Fig and Goat Cheese Pizza With Pesto\",\n" + //
				"            \"readyInMinutes\": 15,\n" + //
				"            \"servings\": 6,\n" + //
				"            \"sourceUrl\": \"https://spoonacular.com/recipeImages/642777-312x231.jpg\"\n" + //
				"        },\n" + //
				"        {\n" + //
				"            \"id\": 648460,\n" + //
				"            \"imageType\": \"jpg\",\n" + //
				"            \"title\": \"Japanese Chicken Donburi\",\n" + //
				"            \"readyInMinutes\": 45,\n" + //
				"            \"servings\": 4,\n" + //
				"            \"sourceUrl\": \"https://spoonacular.com/recipeImages/648460-312x231.jpg\"\n" + //
				"        }\n" + //
				"    ],\n" + //
				"    \"nutrients\": {\n" + //
				"        \"calories\": 1966.51,\n" + //
				"        \"protein\": 55.7,\n" + //
				"        \"fat\": 59.33,\n" + //
				"        \"carbohydrates\": 296.93\n" + //
				"    }\n" + //
				"}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			DayResponse dayResponse = mapper.readValue(jsonString, DayResponse.class);
			session.setAttribute("dayResponse", dayResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mealplanner/day";
	}

	@GetMapping("/week")
	public String week(Model model,HttpSession session){
		model.addAttribute("weekresponse",session.getAttribute("weekResponse"));
		return "weekList";
	}
	@PostMapping("/week")
	public String getWeekMeals(@ModelAttribute Formdata formdata, Model model,HttpSession session) {
		session.removeAttribute("weekResponse");

		// RestTemplate restTemplate = new RestTemplate();

		// URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
		// 		.queryParam("timeFrame", "week")
		// 		.queryParamIfPresent("targetCalories", Optional.ofNullable(formdata.getTargetCalories()))
		// 		.queryParamIfPresent("diet", Optional.ofNullable(formdata.getDiet()))
		// 		.queryParam("apiKey", apiKey)
		// 		.build()
		// 		.toUri();

		// String jsonResponse = restTemplate.getForObject(uri, String.class);

		// ResponseEntity<WeekResponse> responseEntity = restTemplate.getForEntity(uri,
		// WeekResponse.class);

		String jsonString ="{\n" + //
						"   \"week\":{\n" + //
						"      \"monday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":638974,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Chocolate Cream Cheese Muffins\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":16,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/638974-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":1697625,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Light and Tasty Tomato Basil Mozzarella Pasta for a Hot Summer Evening\",\n" + //
						"               \"readyInMinutes\":25,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/1697625-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":642125,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Easy Thai Fried Rice\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/642125-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1820.86,\n" + //
						"            \"protein\":53.79,\n" + //
						"            \"fat\":59.25,\n" + //
						"            \"carbohydrates\":267.27\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"tuesday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":665329,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Wild Blueberry Lemon Muffins\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":14,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/665329-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":1697625,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Light and Tasty Tomato Basil Mozzarella Pasta for a Hot Summer Evening\",\n" + //
						"               \"readyInMinutes\":25,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/1697625-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":633093,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Autumn Fried Rice with Buffalo Nuts®\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":8,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/633093-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1853.65,\n" + //
						"            \"protein\":53.64,\n" + //
						"            \"fat\":63.89,\n" + //
						"            \"carbohydrates\":263.27\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"wednesday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":657274,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Pumpkin Coffee Cake\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":20,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/657274-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":653251,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Noodles and Veggies With Peanut Sauce\",\n" + //
						"               \"readyInMinutes\":30,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/653251-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":634404,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Basic Risotto\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/634404-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1925.41,\n" + //
						"            \"protein\":49.6,\n" + //
						"            \"fat\":57.85,\n" + //
						"            \"carbohydrates\":298.79\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"thursday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":639637,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Classic scones\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/639637-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":649988,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Light and Easy Alfredo\",\n" + //
						"               \"readyInMinutes\":15,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/649988-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":634404,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Basic Risotto\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/634404-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1912.57,\n" + //
						"            \"protein\":51.75,\n" + //
						"            \"fat\":65.03,\n" + //
						"            \"carbohydrates\":274.2\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"friday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":1100990,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Blueberry, Chocolate & Cocao Superfood Pancakes - Gluten-Free/Paleo/Vegan\",\n" + //
						"               \"readyInMinutes\":30,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/1100990-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":652417,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Moroccan chickpea and lentil stew\",\n" + //
						"               \"readyInMinutes\":30,\n" + //
						"               \"servings\":3,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/652417-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":1697697,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"One-Pan Butternut Squash Risotto with Mushrooms\",\n" + //
						"               \"readyInMinutes\":70,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/1697697-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1911.14,\n" + //
						"            \"protein\":51.48,\n" + //
						"            \"fat\":63.79,\n" + //
						"            \"carbohydrates\":294.44\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"saturday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":649300,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Latkes; Fried Vegetable Pancakes from Europe and the Middle East\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/649300-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":650127,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Linguine in Cream Sauce with Poached Eggs and Bacon\",\n" + //
						"               \"readyInMinutes\":25,\n" + //
						"               \"servings\":2,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/650127-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":636714,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Cajun Cuisine: Vegan Jambalaya\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/636714-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1951.02,\n" + //
						"            \"protein\":54.68,\n" + //
						"            \"fat\":60.28,\n" + //
						"            \"carbohydrates\":297.24\n" + //
						"         }\n" + //
						"      },\n" + //
						"      \"sunday\":{\n" + //
						"         \"meals\":[\n" + //
						"            {\n" + //
						"               \"id\":637055,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Caramelized cranberries coconut pancakes\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":5,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/637055-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":1098387,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Quinoa Salad with Barberries & Nuts\",\n" + //
						"               \"readyInMinutes\":30,\n" + //
						"               \"servings\":4,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/1098387-556x370.jpg\"\n" + //
						"            },\n" + //
						"            {\n" + //
						"               \"id\":641687,\n" + //
						"               \"imageType\":\"jpg\",\n" + //
						"               \"title\":\"Dry Mee Siam\",\n" + //
						"               \"readyInMinutes\":45,\n" + //
						"               \"servings\":3,\n" + //
						"               \"sourceUrl\":\"https://spoonacular.com/recipeImages/641687-556x370.jpg\"\n" + //
						"            }\n" + //
						"         ],\n" + //
						"         \"nutrients\":{\n" + //
						"            \"calories\":1857.72,\n" + //
						"            \"protein\":52.01,\n" + //
						"            \"fat\":69.27,\n" + //
						"            \"carbohydrates\":259.18\n" + //
						"         }\n" + //
						"      }\n" + //
						"   }\n" + //
						"}"; // JSON string containing the response data
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WeekResponse weekresponse = objectMapper.readValue(jsonString, WeekResponse.class);
			updateMealSourceUrls(weekresponse.getWeek());
			session.setAttribute("weekResponse",weekresponse);
			return "redirect:/mealplanner/week";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "errorpage";
	}

	private void updateMealSourceUrls(Week week) {
		Day[] days = { week.getMonday(), week.getTuesday(), week.getWednesday(),
				week.getThursday(), week.getFriday(), week.getSaturday(), week.getSunday() };
		for (Day day : days) {
			if (day != null) {

				day.getMeals().forEach(meal -> {
					System.out.println("---------------" + meal);
					int id = meal.getId();
					String imageURL = "https://spoonacular.com/recipeImages/" + id + "-312x231.jpg";
					meal.setSourceUrl(imageURL);
				});
			}
		}
	}
}