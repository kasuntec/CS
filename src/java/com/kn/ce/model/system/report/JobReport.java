/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.model.system.report;

import java.util.Date;

/**
 *
 * @author Kasun
 */
public class JobReport {

    private String jobNo;
    private String custID;
    private Date date;
    private Date delivaryDate;
    private String customer;
    private String status;

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDelivaryDate() {
        return delivaryDate;
    }

    public void setDelivaryDate(Date delivaryDate) {
        this.delivaryDate = delivaryDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    

}
