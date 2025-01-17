package com.zepto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

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

    public RetailItems() {
    }

    public RetailItems(Integer itemId) {
        this.itemId = itemId;
    }

    public RetailItems(Integer itemId, String category, String itemName, Double itemRate) {
        this.itemId = itemId;
        this.category = category;
        this.itemName = itemName;
        this.itemRate = itemRate;
    }

    @Override
    public String toString() {
        return "RetailItems{" +
                "itemId=" + itemId +
                ", category='" + category + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemRate=" + itemRate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RetailItems that = (RetailItems) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(category, that.category) && Objects.equals(itemName, that.itemName) && Objects.equals(itemRate, that.itemRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, category, itemName, itemRate);
    }
}
