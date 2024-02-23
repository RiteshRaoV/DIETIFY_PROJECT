package com.dietify.v1.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Nutrients {
	
	//Retrieve the object properties from Nutrients in Meal Planner.
	//This will be recognized as a non-list object.
	@JsonProperty("calories")
	private int calories;
	@JsonProperty("carbohydrates")
	private int carbohydrates;
	@JsonProperty("fat")
	private int fat;
	@JsonProperty("protein")
	private int protein;
	
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	public int getCarbohydrates() {
		return carbohydrates;
	}
	public void setCarbohydrates(int carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	public int getFat() {
		return fat;
	}
	public void setFat(int fat) {
		this.fat = fat;
	}
	public int getProtein() {
		return protein;
	}
	public void setProtein(int protein) {
		this.protein = protein;
	}
}