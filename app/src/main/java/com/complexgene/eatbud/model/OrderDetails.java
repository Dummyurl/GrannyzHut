package com.complexgene.eatbud.model;

import java.util.List;

/**
 * Created by fptechs48 on 02/07/18.
 */

public class OrderDetails {
    private String date;
    private String orderId;
    private String orderStatus;
    private String phoneNumber;
    private List<Item> itemsOrdered;
    private AddressDetails address;
    private String promoCodeApplied;
    private double orderAmount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Item> getItemsOrdered() {
        return itemsOrdered;
    }

    public void setItemsOrdered(List<Item> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public AddressDetails getAddress() {
        return address;
    }

    public void setAddress(AddressDetails address) {
        this.address = address;
    }

    public String getPromoCodeApplied() {
        return promoCodeApplied;
    }

    public void setPromoCodeApplied(String promoCodeApplied) {
        this.promoCodeApplied = promoCodeApplied;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
