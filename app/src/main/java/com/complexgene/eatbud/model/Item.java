package com.complexgene.eatbud.model;


import java.io.Serializable;

public class Item implements Serializable{
    private String itemName;
    private String itemType;
    private String itemImageName;
    private String itemImageUrl;
    private String itemDescriptionSmall;
    private String itemDescriptionLarge;

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
}
