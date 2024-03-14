package com.dietify.v1.DTO.Day;

import java.util.List;

import com.dietify.v1.DTO.Meals;
import com.dietify.v1.DTO.Nutrients;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class DayResponse {

    private List<Meals> meals;
    private Nutrients nutrients;
	public List<Meals> getMeals() {
		return meals;
	}
	public void setMeals(List<Meals> meals) {
		this.meals = meals;
	}
	public Nutrients getNutrients() {
		return nutrients;
	}
	public void setNutrients(Nutrients nutrients) {
		this.nutrients = nutrients;
	}
	// @Override
    // public String toString() {
    //     return "DayResponse{" +
    //             "meals=" + meals +
    //             ", nutrients=" + nutrients +
    //             '}';
    // }

}
