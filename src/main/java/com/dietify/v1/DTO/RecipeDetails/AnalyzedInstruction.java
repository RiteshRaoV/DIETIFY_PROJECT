package com.dietify.v1.DTO.RecipeDetails;

import java.util.List;

public class AnalyzedInstruction {
    private String name;
    private List<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
