/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;

import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.system.report.CustomerOutsReport;
import com.kn.ce.model.system.report.JobReport;
import com.kn.ce.model.system.report.PurchaseReport;
import com.kn.ce.model.system.report.RawItemMovemnt;
import com.kn.ce.model.system.report.RawItemStockReport;
import com.kn.ce.model.system.report.SalesReport;
import com.kn.ce.model.system.report.SupplierOutsReport;
import java.util.Date;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kasun
 */
@Repository("reportDAO")
public class ReportDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //---------------------------------------------SALES REPORTS-----------------------------------
    //get sales report by date
    public List<SalesReport> getSalesByDate(Date fromDate, Date toDate) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_sales_report where `date` BETWEEN  :fromDate AND :toDate");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setResultTransformer(Transformers.aliasToBean(SalesReport.class));
        return query.list();

    }

    //get sales report by date
    public List<SalesReport> getSalesByDateCustomer(Date fromDate, Date toDate, String custId) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_sales_report where (custId=:custId) AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("custId", custId.trim());
        query.setResultTransformer(Transformers.aliasToBean(SalesReport.class));
        return query.list();

    }

    //get sales report by date
    public List<SalesReport> getSalesByDateTerm(Date fromDate, Date toDate, String term) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_sales_report where  (term=:type)   AND (`date` BETWEEN  :fromDate AND :toDate)");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("type", term);
        query.setResultTransformer(Transformers.aliasToBean(SalesReport.class));
        return query.list();

    }

    //get sales report by date
    public List<SalesReport> getSalesByDateCustomerTerm(Date fromDate, Date toDate, String term, String custId) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_sales_report where (custId=:custId) AND (term=:type) AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("custId", custId.trim());
        query.setString("type", term.trim());
        query.setResultTransformer(Transformers.aliasToBean(SalesReport.class));
        return query.list();

    }

    //---------------------------------------------PURCHASE REPORTS-----------------------------------
    //get sales report by date
    public List<PurchaseReport> getPurchaseByDate(Date fromDate, Date toDate) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_purchase_report where `date` BETWEEN  :fromDate AND :toDate");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setResultTransformer(Transformers.aliasToBean(PurchaseReport.class));
        return query.list();

    }

    //get sales report by date
    public List<PurchaseReport> getPurchaseByDateSupplier(Date fromDate, Date toDate, String supId) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_purchase_report where (supID=:supID) AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("supID", supId.trim());
        query.setResultTransformer(Transformers.aliasToBean(PurchaseReport.class));
        return query.list();

    }

    //Job
    public List<JobReport> getJobByDate(Date fromDate, Date toDate) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_job_report where (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setResultTransformer(Transformers.aliasToBean(JobReport.class));
        return query.list();
    }

    public List<JobReport> getJobByDateCustomer(Date fromDate, Date toDate, String custID) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_job_report where custID=:custID AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("custID", custID.trim());
        query.setResultTransformer(Transformers.aliasToBean(JobReport.class));
        return query.list();
    }

    public List<JobReport> getJobByStatus(Date fromDate, Date toDate, String status) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_job_report where status=:status AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("status", status.trim());
        query.setResultTransformer(Transformers.aliasToBean(JobReport.class));
        return query.list();
    }

    public List<JobReport> getJobByStatusCustomer(Date fromDate, Date toDate, String custID, String status) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_job_report where status=:status AND custID=:custID AND (`date` BETWEEN  :fromDate AND :toDate) ");
        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setString("status", status.trim());
        query.setString("custID", custID.trim());
        query.setResultTransformer(Transformers.aliasToBean(JobReport.class));
        return query.list();
    }

    //Stock Report   
    public List<RawItemStockReport> getAllStockReport() {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_stock_report ");;
        query.setResultTransformer(Transformers.aliasToBean(RawItemStockReport.class));
        return query.list();
    }

    //Stock Report   
    public List<RawItemStockReport> getAllByCatStockReport(String category) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_stock_report where category=:category ");
        query.setString("category", category);
        query.setResultTransformer(Transformers.aliasToBean(RawItemStockReport.class));
        return query.list();
    }

    public List<RawItemStockReport> getAvaibleStockReport() {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_stock_report where stock > 0 ");
        query.setResultTransformer(Transformers.aliasToBean(RawItemStockReport.class));
        return query.list();
    }

    public List<RawItemStockReport> getAvaibleByCatStockReport(String category) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("select * from view_stock_report where stock > 0 AND category=:category  ");
        query.setString("category", category);
        query.setResultTransformer(Transformers.aliasToBean(RawItemStockReport.class));
        return query.list();
    }

    public List<CustomerOutsReport> getCustomerOutSt(Date date) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("SELECT "
                + "cast(customer_transaction.cust_id as  char(50) ) AS  custId, "
                + "customer.name AS cusyomer, "
                + "Sum(customer_transaction.amount) AS amount "
                + "FROM customer_transaction "
                + "Inner Join customer ON customer_transaction.cust_id = customer.cust_id "                
                + "WHERE date_format(customer_transaction.`date`,'%Y-%m-%d')  <= date_format(:date,'%Y-%m-%d') AND "
                + "customer_transaction.amount >  '0' "
                + "GROUP BY customer_transaction.cust_id ");
        query.setDate("date", date);
        query.setResultTransformer(Transformers.aliasToBean(CustomerOutsReport.class));
        return query.list();
    }

    public List<SupplierOutsReport> getSupplierOutSt(Date date) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery("SELECT cast(supplier_transaction.sup_id as  char(50) ) AS  supId, "
                + "supplier.name AS supplier, "
                + "Sum(supplier_transaction.amount) AS amount "
                + "FROM supplier_transaction "
                + "Inner Join supplier ON supplier_transaction.sup_id = supplier.sup_id "
                + "WHERE supplier_transaction.`date` <=:date AND "
                + "supplier_transaction.amount >  '0' "
                + "GROUP BY supplier_transaction.sup_id ");
        query.setDate("date", date);
        query.setResultTransformer(Transformers.aliasToBean(SupplierOutsReport.class));
        return query.list();
    }

    public List<RawItemMovemnt> getStockMovement(Date fromDate, Date toDate) {
        Session s = sessionFactory.getCurrentSession();
        SQLQuery query = s.createSQLQuery(""
                + "SELECT "
                + "cast(view_stock_movement_by_type.`date` as  char(50) charset utf8) AS date , "
                + "cast(view_stock_movement_by_type.itemId as  char(50) charset utf8) AS itemId ,"
                + "view_stock_movement_by_type.description description,"
                + "view_stock_movement_by_type.type AS type,"
                + "sum(if(`type`='issue',qty,0))*-1 issue,"
                + "sum(if(`type`='Issue_Rtn',qty,0)) IssueRtn,"
                + "sum(if(`type`='Grn',qty,0)) grn,"
                + "sum(if(`type`='Grn_Rtn',qty,0))*-1 grnRtn "
                + "FROM view_stock_movement_by_type "
                + "WHERE "
                + "view_stock_movement_by_type.`date` BETWEEN  :fromDate AND :toDate "
                + "GROUP BY view_stock_movement_by_type.itemId ");

        query.setDate("fromDate", fromDate);
        query.setDate("toDate", toDate);
        query.setResultTransformer(Transformers.aliasToBean(RawItemMovemnt.class));
        return query.list();
    }

}
