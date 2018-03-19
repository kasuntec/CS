/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.model.system.report;

/**
 *
 * @author Kasun
 */
public class RawItemStockReport {
    private String itemId;
    private String itemName;
    private String category;
    private String uom;
    private Double stock;
    private Double cost;
    private Double reOrderLvl;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getReOrderLvl() {
        return reOrderLvl;
    }

    public void setReOrderLvl(Double reOrderLvl) {
        this.reOrderLvl = reOrderLvl;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    
    
    
}
