package com.complexgene.eatbud.model;

import java.io.Serializable;

public class Cart implements Serializable {

    private String itemName;
    private String itemType;
    private String itemImageName;
    private String itemImageUrl;
    private String itemDescriptionSmall;
    private String itemDescriptionLarge;
    private Double price;
    private int qty;


    public Cart() {
    }

    public Cart(String itemName, String itemType, String itemImageName, Double price, int qty) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemImageName = itemImageName;
        this.price = price;
        this.qty = qty;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}






