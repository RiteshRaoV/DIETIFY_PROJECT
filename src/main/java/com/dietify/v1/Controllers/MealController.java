package com.dietify.v1.Controllers;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@PostMapping("/day")
	public String getDayMeals(@ModelAttribute Formdata formdata, Model model, HttpSession session) {
		// session.removeAttribute("dayResponse");

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
			model.addAttribute("dayResponse", dayResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "day-list";
	}

	@PostMapping("/week")
	public String getWeekMeals(@ModelAttribute Formdata formdata, Model model,HttpSession session) {

		RestTemplate restTemplate = new RestTemplate();

		URI uri = UriComponentsBuilder.fromHttpUrl(baseURL)
				.queryParam("timeFrame", "week")
				.queryParamIfPresent("targetCalories", Optional.ofNullable(formdata.getTargetCalories()))
				.queryParamIfPresent("diet", Optional.ofNullable(formdata.getDiet()))
				.queryParam("apiKey", apiKey)
				.build()
				.toUri();

		String jsonResponse = restTemplate.getForObject(uri, String.class);
		// ResponseEntity<WeekResponse> responseEntity = restTemplate.getForEntity(uri,
		// WeekResponse.class);

		String jsonString = jsonResponse; // JSON string containing the response data
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			WeekResponse weekresponse = objectMapper.readValue(jsonString, WeekResponse.class);
			updateMealSourceUrls(weekresponse.getWeek());
			session.setAttribute("weekResponse",weekresponse);
			model.addAttribute("weekresponse", weekresponse);
			return "weeklist";
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