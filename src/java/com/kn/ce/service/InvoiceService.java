/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.CustomerTransaction;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.InvoiceLine;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.system.CustomerTransType;
import com.kn.ce.model.system.InvoiceStatus;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.model.system.Where;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("invoiceService")
public class InvoiceService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    InvoicePaymentService invoicePaymentService;
    @Autowired
    private AdvancePaymentService advancePaymentService;
     @Autowired
    private CustomerTranService customerTranService;
    
    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(Invoice header) throws Exception {
        //save head objects
        Long id = genericDao.save(header);

        //save lines objects
        Iterator<InvoiceLine> iterator = header.getInvoiceLines().iterator();
        while (iterator.hasNext()) {
            InvoiceLine line = iterator.next();
            line.setInvoice(header);//set saved job
            //save
            genericDao.save(line);
            
        }
        //change job status
        Job job = header.getJob();
        job.setStatus(JobStatus.INVOICED);
        genericDao.update(job);
        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(Invoice invoice) throws Exception {
        //update head objects
        Long id = genericDao.update(invoice);

        //save old lines
        genericDao.deleteLines(InvoiceLine.class, invoice.getInvNo(), "invoice.invNo");

        //save lines objects
        Iterator<InvoiceLine> iterator = invoice.getInvoiceLines().iterator();
        while (iterator.hasNext()) {
            InvoiceLine line = iterator.next();
            line.setInvoice(invoice);//set saved job
            //save
            genericDao.save(line);
            
        }
        //save customer transaction when finalize status
        if(id>0&&invoice.getStatus().equals(InvoiceStatus.UNPAID)){
            Customer customer = invoice.getJob().getCustomer();    
             Double netTotal = invoice.getGrossTotal()-(advancePaymentService.getByJob(invoice.getJob().getJobNo()).getAmount());
            CustomerTransaction transaction = new CustomerTransaction(customer, invoice, invoice.getDate(), CustomerTransType.INVOICE,netTotal,0l);
            customerTranService.deleteTransaction(invoice, 0l, CustomerTransType.INVOICE);
            customerTranService.save(transaction);
        }
        
        return id;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Invoice get(long id) throws Exception {
        Invoice invoice = (Invoice) genericDao.get(id, Invoice.class);
        Hibernate.initialize(invoice.getInvoiceLines());
        Hibernate.initialize(invoice.getJob().getCustomer());

        
        //set adavance amount
        AdvancePayment advancePayment = advancePaymentService.getByJob(invoice.getJob().getJobNo());
        Job job = invoice.getJob();
        job.setAdvance(advancePayment.getAmount());
        invoice.setJob(job);
        
        //set gross total and payments       
        invoice.setPayAmount(invoicePaymentService.getPaymentTotal(invoice));
        return invoice;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(String status) throws Exception {
        //define varibles
        List<Invoice> list = new ArrayList<Invoice>();
        Iterator<Invoice> it = null;
        //set where       
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("status", status);
        wheres.add(where);
        
        if (status.equals(InvoiceStatus.ALL)) {//check status
            //load all 
            it = genericDao.list(Invoice.class).iterator();
        } else {
            //list  by status
            it = genericDao.list(Invoice.class, wheres).iterator();
        }

        //loop and init depandancy
        while (it.hasNext()) {
            Invoice invoice = it.next();
            Hibernate.initialize(invoice.getInvoiceLines());
            Hibernate.initialize(invoice.getJob().getCustomer());
            Hibernate.initialize(invoice.getJob());

            //get adavance payment
            AdvancePayment advancePayment = advancePaymentService.getByJob(invoice.getJob().getJobNo());
            if (advancePayment != null) {
                invoice.getJob().setAdvance(advancePayment.getAmount());
            } else {
                invoice.getJob().setAdvance(0.0);
            }

            //set amount           
            invoice.setPayAmount(invoicePaymentService.getPaymentTotal(invoice));
            
            list.add(invoice);
            
        }
        
        return list;
        
    }

    /*-----------------------------
    List method by customer
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Customer customer) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("customer", customer);
        wheres.add(where);
        
        return genericDao.list(Invoice.class, wheres);
    }
    
     /*-----------------------------
    List method for many status
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List<Invoice> list(String status1,String status2) throws Exception {
       
        //set where      
          Where where1 = new Where("status", status1);
          Where where2 = new Where("status", status2);
        
         return genericDao.listByOR(Invoice.class, where1,where2);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(Invoice invoice) throws Exception {
        return genericDao.delete(invoice);
    }

    
}
