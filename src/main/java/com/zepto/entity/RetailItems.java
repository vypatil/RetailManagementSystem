package com.zepto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RetailItems {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer itemId;
    @Column
    private String category;
    @Column
    private String itemName;
    @Column
    private Double itemRate;

    // Getters and Setters
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemRate() {
        return itemRate;
    }

    public void setItemRate(double itemRate) {
        this.itemRate = itemRate;
    }
}
