package com.complexgene.eatbud.model;



import java.io.Serializable;
import java.util.List;


public class Meal implements Serializable{
    private String mealType;
    private List<MainItem> mainItemList;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public List<MainItem> getMainItemList() {
        return mainItemList;
    }

    public void setMainItemList(List<MainItem> mainItemList) {
        this.mainItemList = mainItemList;
    }
}

