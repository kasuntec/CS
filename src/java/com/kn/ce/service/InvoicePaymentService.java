/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.CustomerTransaction;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.InvoicePayment;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.system.CustomerTransType;
import com.kn.ce.model.system.InvoiceStatus;
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
@Service("invoicePaymentService")
public class InvoicePaymentService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private CustomerTranService customerTranService;
    
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private JobService jobService;

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(InvoicePayment payment) throws Exception {
        //save payment
        long res = genericDao.save(payment);
        Hibernate.initialize(payment.getInvoice().getJob().getCustomer());

       
        if (res > 0) {
             //save customer transaction
            Customer customer = payment.getInvoice().getJob().getCustomer();
            Invoice invoice = payment.getInvoice();
            CustomerTransaction transaction = new CustomerTransaction(customer, invoice, payment.getDate(), CustomerTransType.PAYMENT, payment.getAmount()*-1, payment.getPayId());
            customerTranService.save(transaction);
            
            //change status
            Invoice updtdInvoice  = invoiceService.get(invoice.getInvNo());
            Double advance = jobService.get(updtdInvoice.getJob().getJobNo()).getAdvance();
            double balance = (updtdInvoice.getGrossTotal()-updtdInvoice.getDiscount())-(updtdInvoice.getPayAmount()+advance);
            
            if(balance<=0){
                updtdInvoice.setStatus(InvoiceStatus.PAID);
            }
            else{
                updtdInvoice.setStatus(InvoiceStatus.PARTIAL);
                
            }
            genericDao.update(updtdInvoice);
            
            
        }

        return res;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(InvoicePayment payment) throws Exception {
        long res= genericDao.update(payment);
        
        Hibernate.initialize(payment.getInvoice().getJob().getCustomer());

        //save customer transaction
        if (res > 0) {
            Customer customer = payment.getInvoice().getJob().getCustomer();
            Invoice invoice = payment.getInvoice();
            CustomerTransaction transaction = new CustomerTransaction(customer, invoice, payment.getDate(), CustomerTransType.PAYMENT, payment.getAmount()*-1, payment.getPayId());
            customerTranService.deleteTransaction(invoice, payment.getPayId(), CustomerTransType.PAYMENT);
            customerTranService.save(transaction);
            
            
             //change status
             //change status
            Invoice updtdInvoice  = invoiceService.get(invoice.getInvNo());
            Double advance = jobService.get(updtdInvoice.getJob().getJobNo()).getAdvance();
            double balance = (updtdInvoice.getGrossTotal()-updtdInvoice.getDiscount())-(updtdInvoice.getPayAmount()+advance);
            
            if(balance<=0){
                updtdInvoice.setStatus(InvoiceStatus.PAID);
            }
            else{
                updtdInvoice.setStatus(InvoiceStatus.PARTIAL);
                
            }
            genericDao.update(updtdInvoice);
           
        }
        
         return res;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public InvoicePayment get(long id) throws Exception {
        return (InvoicePayment) genericDao.get(id, InvoicePayment.class);
    }

    /*-----------------------------
    Get method by invoice
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Double getPaymentTotal(Invoice invoice) throws Exception {
        Double payTotal = 0.0;
        //set where       
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("invoice", invoice);
        wheres.add(where);

        //get all payments for invoice
        Iterator<InvoicePayment> list = genericDao.list(InvoicePayment.class, wheres).iterator();

        while (list.hasNext()) {//loop list
            InvoicePayment payment = list.next();
            payTotal = payTotal + payment.getAmount();

        }

        return payTotal;
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(InvoicePayment.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(InvoicePayment payment) throws Exception {
        return genericDao.delete(payment);
    }

}
