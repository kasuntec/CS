/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.ReportDAO;
import com.kn.ce.model.system.report.CustomerOutsReport;
import com.kn.ce.model.system.report.JobReport;
import com.kn.ce.model.system.report.PurchaseReport;
import com.kn.ce.model.system.report.RawItemMovemnt;
import com.kn.ce.model.system.report.RawItemStockReport;
import com.kn.ce.model.system.report.SalesReport;
import com.kn.ce.model.system.report.SupplierOutsReport;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kasun
 */
@Service("reportService")
public class ReportService {

    @Autowired
    private ReportDAO reportDAO;

    //sales ---------------------------------------------
    @Transactional
    public List<SalesReport> getSalesByDate(Date fromDate, Date toDate) {
        return reportDAO.getSalesByDate(fromDate, toDate);
    }

    @Transactional
    public List<SalesReport> getSalesByDateCustomer(Date fromDate, Date toDate, String custID) {
        return reportDAO.getSalesByDateCustomer(fromDate, toDate, custID);
    }

    @Transactional
    public List<SalesReport> getSalesByDateTerm(Date fromDate, Date toDate, String term) {
        return reportDAO.getSalesByDateTerm(fromDate, toDate, term);
    }

    @Transactional
    public List<SalesReport> getSalesByDateCustomerTerm(Date fromDate, Date toDate, String term, String custID) {
        return reportDAO.getSalesByDateCustomerTerm(fromDate, toDate, term, custID);
    }

    //purchase ---------------------------------------------
    @Transactional
    public List<PurchaseReport> getPurchaseByDate(Date fromDate, Date toDate) {
        return reportDAO.getPurchaseByDate(fromDate, toDate);
    }

    @Transactional
    public List<PurchaseReport> getPurchaseByDateSupplier(Date fromDate, Date toDate, String supId) {
        return reportDAO.getPurchaseByDateSupplier(fromDate, toDate, supId);
    }

    //Job ---------------------------------------------
    @Transactional
    public List<JobReport> getJobByDate(Date fromDate, Date toDate) {
        return reportDAO.getJobByDate(fromDate, toDate);
    }

    @Transactional
    public List<JobReport> getJobByDateCustomer(Date fromDate, Date toDate, String custId) {
        return reportDAO.getJobByDateCustomer(fromDate, toDate, custId);
    }

    @Transactional
    public List<JobReport> getJobByStatus(Date fromDate, Date toDate, String status) {
        return reportDAO.getJobByStatus(fromDate, toDate, status);
    }

    @Transactional
    public List<JobReport> getJobByStatusCustomer(Date fromDate, Date toDate, String custId, String status) {
        return reportDAO.getJobByStatusCustomer(fromDate, toDate, custId, status);
    }

    //Stock Report
    @Transactional
    public List<RawItemStockReport> getAllStockReport() {
        return reportDAO.getAllStockReport();
    }

    @Transactional
    public List<RawItemStockReport> getAllByCatStockReport(String category) {

        return reportDAO.getAllByCatStockReport(category);
    }

    @Transactional
    public List<RawItemStockReport> getAvaibleStockReport() {

        return reportDAO.getAvaibleStockReport();
    }

    @Transactional
    public List<RawItemStockReport> getAvaibleByCatStockReport(String category) {
        return reportDAO.getAvaibleByCatStockReport(category);
    }

    @Transactional
    public List<CustomerOutsReport> getCustomerOutSt(Date date) {
        return reportDAO.getCustomerOutSt(date);
    }
    @Transactional
    public List<SupplierOutsReport> getSupplierOutsReport(Date date) {
        return reportDAO.getSupplierOutSt(date);
    }

    @Transactional
    public List<RawItemMovemnt> getStockMovement(Date fromDate, Date toDate) {
        return reportDAO.getStockMovement(fromDate, toDate);
    }

}
