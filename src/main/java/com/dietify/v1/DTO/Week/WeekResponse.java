package com.dietify.v1.DTO.Week;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeekResponse {

    @JsonProperty("week")
    private Week week;

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}
	
}
