package com.llopez.awallet.model;

public class BillCategory {
    private String name;
    private String color;
    private String createdAt;

    public BillCategory(){

    }

    public BillCategory(String name, String color, String createdAt){
        this.name = name;
        this.color = color;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
