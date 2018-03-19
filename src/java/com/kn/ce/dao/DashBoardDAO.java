/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;

import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Repository("dashBoardDAO")
public class DashBoardDAO {

    //depandancy injection
    @Autowired
    private SessionFactory sessionFactory;

    ;
     private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    private Date startDate;
    private Date endDate;

    public DashBoardDAO() {

//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(new Date());
//        calendar1.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, 0, 0, 0);
//        startDate = calendar1.getTime();
//
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTime(new Date());
//        calendar2.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, 0, 0, 0);
//        endDate = calendar2.getTime();

        Calendar stCalendar = Calendar.getInstance();
        stCalendar.setTime(new Date());
        stCalendar.set(Calendar.DAY_OF_MONTH, stCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        stCalendar.set(Calendar.HOUR, 0);
        stCalendar.set(Calendar.MINUTE, 0);
        stCalendar.set(Calendar.SECOND, 0);
        startDate = stCalendar.getTime();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        endCalendar.set(Calendar.HOUR, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);   
        endDate = endCalendar.getTime();

    }

    public Double getJobTotalByStatus(String status) {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();

        
        SQLQuery query = session.createSQLQuery("SELECT  Sum(job_line.qty*job_line.unit_price*job_line.width*job_line.height) AS Total "
                + "FROM job "
                + "Inner Join job_line ON job.job_no = job_line.job_no "
                + "WHERE job.`date` BETWEEN  :startDate AND :endDate  AND status=:status");
        
        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setString("status", status);

        List list = query.list();
        if(list.size()>0 && list.get(0)!=null){
            total=(Double)  list.get(0);
        }
       
        return total;
    }

    public Double getinvoiceTotalByStatus(String status) {
       double total = 0.0;
        Session session = sessionFactory.getCurrentSession();

        
        SQLQuery query = session.createSQLQuery("SELECT  Sum(invoice_line.qty*invoice_line.unit_price*invoice_line.width*invoice_line.height) AS Total "
                + "FROM invoice "
                + "Inner Join invoice_line ON invoice.inv_no = invoice_line.inv_id "
                + "WHERE invoice.`date` BETWEEN  :startDate AND :endDate  AND invoice.status=:status");
        
        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setString("status", status);

        List list = query.list();
        if(list.size()>0 && list.get(0)!=null){
            total=(Double)  list.get(0);
        }
       
        return total;
    }

    public Double getGrnTotalByStatus(String status) {
       double total = 0.0;
        Session session = sessionFactory.getCurrentSession();

        
        SQLQuery query = session.createSQLQuery("SELECT  Sum(grn_line.qty*grn_line.price) AS Total "
                + "FROM grn "
                + "Inner Join grn_line ON grn.grn_no = grn_line.grn_id "
                + "WHERE grn.`date` BETWEEN  :startDate AND :endDate  AND status=:status");
        
        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);
        query.setString("status", status);

        List list = query.list();
        if(list.size()>0 && list.get(0)!=null){
            total=(Double)  list.get(0);
        }
       
        return total;
    }

    public Double getSalesIncome() {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Sum(customer_transaction.amount)"
                + "FROM customer_transaction  "
                + "WHERE  customer_transaction.`type` =  'Payment' AND  "
                + "customer_transaction.`date` BETWEEN  :startDate AND :endDate");

        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            total = (Double) list.get(0);
        }

        return total;
    }

    public Double getPurchaseTotal() {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Sum(supplier_transaction.amount)"
                + "FROM supplier_transaction  "
                + "WHERE  supplier_transaction.`type` =  'Payment' AND  "
                + "supplier_transaction.`date`  BETWEEN  :startDate AND :endDate ");

        query.setDate("startDate", startDate);
        query.setDate("endDate", endDate);

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            total = (Double) list.get(0);
        }

        return total;
    }

    public Double getTotalRecivbleIncome() {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Sum(customer_transaction.amount)"
                + "FROM customer_transaction  ");

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            total = (Double) list.get(0);
        }

        return total;
    }

    public Double getPaybleTotal() {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Sum(supplier_transaction.amount)"
                + "FROM supplier_transaction   ");

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            total = (Double) list.get(0);
        }

        return total;
    }

    public int getReOrderItemsCount() {
        int count = 0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Count(view_stock_report.itemId)  FROM view_stock_report "
                + "WHERE view_stock_report.reOrderLvl >=  view_stock_report.stock ");

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            BigInteger bi = (BigInteger) list.get(0);
            count=bi.intValue();
        }

        return count;
    }

    public Double getStockValueTotal() {
        double total = 0.0;
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery("SELECT Sum(view_stock_report.stock*view_stock_report.cost) "
                + "FROM view_stock_report ");

        List list = query.list();

        if (list.size() > 0 && list.get(0) != null) {
            total = (Double) list.get(0);
        }

        return total;
    }

}
