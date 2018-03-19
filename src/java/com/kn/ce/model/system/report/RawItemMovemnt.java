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
public class RawItemMovemnt {
    private String date;
    private String itemId;
    private String description;
    private String type;
    private Double issue;
    private Double IssueRtn;
    private Double grn;
    private Double grnRtn;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getIssue() {
        return issue;
    }

    public void setIssue(Double issue) {
        this.issue = issue;
    }

    public Double getIssueRtn() {
        return IssueRtn;
    }

    public void setIssueRtn(Double IssueRtn) {
        this.IssueRtn = IssueRtn;
    }

    public Double getGrn() {
        return grn;
    }

    public void setGrn(Double grn) {
        this.grn = grn;
    }

    public Double getGrnRtn() {
        return grnRtn;
    }

    public void setGrnRtn(Double grnRtn) {
        this.grnRtn = grnRtn;
    }

    
  
    
    
    
}
