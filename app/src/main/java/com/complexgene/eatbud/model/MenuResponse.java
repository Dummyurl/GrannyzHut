package com.complexgene.eatbud.model;

import java.util.List;

public class MenuResponse {

    private String date;
    private List<Meal> meals;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
