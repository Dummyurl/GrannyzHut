package com.complexgene.eatbud.model;

import com.google.gson.internal.LinkedTreeMap;

public class FoodItem {

    private int itemNumber;
    private String itemName;
    private String itemType;
    private String itemImageName;
    private String itemImageUrl;
    private String itemDescriptionSmall;
    private String itemDescriptionLarge;
    private LinkedTreeMap<String, Double> quantityPriceDetails;


    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemImageName() {
        return itemImageName;
    }

    public void setItemImageName(String itemImageName) {
        this.itemImageName = itemImageName;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public String getItemDescriptionSmall() {
        return itemDescriptionSmall;
    }

    public void setItemDescriptionSmall(String itemDescriptionSmall) {
        this.itemDescriptionSmall = itemDescriptionSmall;
    }

    public String getItemDescriptionLarge() {
        return itemDescriptionLarge;
    }

    public void setItemDescriptionLarge(String itemDescriptionLarge) {
        this.itemDescriptionLarge = itemDescriptionLarge;
    }

    public LinkedTreeMap<String, Double> getQuantityPriceDetails() {
        return quantityPriceDetails;
    }

    public void setQuantityPriceDetails(LinkedTreeMap<String, Double> quantityPriceDetails) {
        this.quantityPriceDetails = quantityPriceDetails;
    }
}
