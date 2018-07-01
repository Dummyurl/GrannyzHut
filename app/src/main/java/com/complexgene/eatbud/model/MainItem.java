package com.complexgene.eatbud.model;




import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;


public class MainItem implements Serializable{

    private int qtyStandard=0;
    private int qtySmall=0;
    private String mainItemName;
    private String mainItemTag;
    private String mainItemType;
    private String mainItemImageName;
    private String mainItemImageUrl;
    private String mainItemDescriptionSmall;
    private String mainItemDescriptionLarge;
    private LinkedHashMap<String, Double> quantityPriceDetails;
    private List<Item> items;


    public int getQtyStandard() {
        return qtyStandard;
    }

    public void setQtyStandard(int qtyStandard) {
        this.qtyStandard = qtyStandard;
    }

    public int getQtySmall() {
        return qtySmall;
    }

    public void setQtySmall(int qtySmall) {
        this.qtySmall = qtySmall;
    }

    public String getMainItemName() {
        return mainItemName;
    }

    public void setMainItemName(String mainItemName) {
        this.mainItemName = mainItemName;
    }

    public String getMainItemTag() {
        return mainItemTag;
    }

    public void setMainItemTag(String mainItemTag) {
        this.mainItemTag = mainItemTag;
    }

    public String getMainItemType() {
        return mainItemType;
    }

    public void setMainItemType(String mainItemType) {
        this.mainItemType = mainItemType;
    }

    public String getMainItemImageName() {
        return mainItemImageName;
    }

    public void setMainItemImageName(String mainItemImageName) {
        this.mainItemImageName = mainItemImageName;
    }

    public String getMainItemImageUrl() {
        return mainItemImageUrl;
    }

    public void setMainItemImageUrl(String mainItemImageUrl) {
        this.mainItemImageUrl = mainItemImageUrl;
    }

    public String getMainItemDescriptionSmall() {
        return mainItemDescriptionSmall;
    }

    public void setMainItemDescriptionSmall(String mainItemDescriptionSmall) {
        this.mainItemDescriptionSmall = mainItemDescriptionSmall;
    }

    public String getMainItemDescriptionLarge() {
        return mainItemDescriptionLarge;
    }

    public void setMainItemDescriptionLarge(String mainItemDescriptionLarge) {
        this.mainItemDescriptionLarge = mainItemDescriptionLarge;
    }

    public LinkedHashMap<String, Double> getQuantityPriceDetails() {
        return quantityPriceDetails;
    }

    public void setQuantityPriceDetails(LinkedHashMap<String, Double> quantityPriceDetails) {
        this.quantityPriceDetails = quantityPriceDetails;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

