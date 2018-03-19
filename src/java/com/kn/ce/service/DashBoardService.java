/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.DashBoardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("dashBoardService")
public class DashBoardService {

    //dependancy injection
    @Autowired
    private DashBoardDAO dashBoardDAO;

    @Transactional
    public Double getJobTotalByStatus(String status) {

        return dashBoardDAO.getJobTotalByStatus(status);
    }

    @Transactional
    public Double getinvoiceTotalByStatus(String status) {

        return dashBoardDAO.getinvoiceTotalByStatus(status);
    }

    @Transactional
    public Double getGrnTotalByStatus(String status) {

        return dashBoardDAO.getGrnTotalByStatus(status);
    }

    @Transactional
    public Double getSalesIncome() {

        return dashBoardDAO.getSalesIncome();
    }

    @Transactional
    public Double getPurchaseTotal() {

        return dashBoardDAO.getPurchaseTotal();
    }

    @Transactional
    public Double getTotalRecivbleIncome() {

        return dashBoardDAO.getTotalRecivbleIncome();
    }

    @Transactional
    public Double getPaybleTotal() {

        return dashBoardDAO.getPaybleTotal();
    }

    @Transactional
    public int getReOrderItemsCount() {

        return dashBoardDAO.getReOrderItemsCount();
    }

    @Transactional
    public Double getStockValueTotal() {

        return dashBoardDAO.getStockValueTotal();
    }

}
