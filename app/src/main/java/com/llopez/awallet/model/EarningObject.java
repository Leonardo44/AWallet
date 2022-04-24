package com.llopez.awallet.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EarningObject {
    private String name;
    private Double amount;
    private String description;
    private String createdAt;
    private BillCategory category;

    public EarningObject(){

    }

    public EarningObject(BillCategory category, String name, Double amount, String description, String createdAt){
        this.category = category;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public BillCategory getCategory() {
        return category;
    }
    public void setCategory(BillCategory category) {
        this.category = category;
    }

    public Date getCreatedAtDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = dateFormat.parse(createdAt);
        return  date;
    }
}
