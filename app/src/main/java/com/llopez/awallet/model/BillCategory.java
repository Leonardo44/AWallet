package com.llopez.awallet.model;

import java.util.Date;

public class BillCategory {
    private String documentName;
    private String name;
    private String color;
    private Date createdAt;

    public BillCategory(){

    }

    public BillCategory(String documentName, String name, String color, Date createdAt){
        this.documentName = documentName;
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
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
