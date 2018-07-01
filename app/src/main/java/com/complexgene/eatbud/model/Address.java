package com.complexgene.eatbud.model;

/**
 * Created by satyabrata on 13/6/18.
 */

public class Address {

    private String id;
    private String type;
    private String address;
    private double lat;
    private double lon;
    private boolean isDefault;

    public Address() {
    }

    public Address(String id, String type, String address, boolean isDefault) {
        this.id = id;
        this.type = type;
        this.address = address;
        this.isDefault = isDefault;
    }

    public Address(String type, String address, boolean isDefault) {
        this.type = type;
        this.address = address;
        this.isDefault = isDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
