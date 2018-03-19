/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.CreditNote;
import com.kn.ce.model.entity.CreditNoteLine;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.CustomerTransaction;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.system.CustomerTransType;
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
@Service("creditNoteService")
public class CreditNoteService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

     @Autowired
    private CustomerTranService customerTranService;
     @Autowired
     private InvoiceService invoiceService;

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(CreditNote header) throws Exception {
        //save head objects
        Long id = genericDao.save(header);

        //save lines objects
        Iterator<CreditNoteLine> iterator = header.getCreditNoteLines().iterator();
        while (iterator.hasNext()) {
            CreditNoteLine line = iterator.next();
            line.setCreditNote(header);//set saved job
            //save
            genericDao.save(line);

        }
        
        //save customer transaction 
        if(id>0){
            Invoice invoice=invoiceService.get(header.getInvoice().getInvNo());
            Customer customer = invoice.getJob().getCustomer();    
             Double netTotal = header.getTotal()*-1;
            CustomerTransaction transaction = new CustomerTransaction(customer, header.getInvoice(), header.getDate(), CustomerTransType.PAYMENT,netTotal);
            customerTranService.deleteTransaction(header.getInvoice(), 0, CustomerTransType.INVOICE);
            customerTranService.save(transaction);
        }
        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(CreditNote header) throws Exception {
        //update head objects
        Long id = genericDao.update(header);

        //save old lines
        genericDao.deleteLines(CreditNoteLine.class, header.getCrnNo(), "creditNote.crnNo");

        //save lines objects
        Iterator<CreditNoteLine> iterator = header.getCreditNoteLines().iterator();
        while (iterator.hasNext()) {
            CreditNoteLine line = iterator.next();
            line.setCreditNote(header);//set saved job
            //save
            genericDao.save(line);

        }
        
         //save customer transaction 
        if(id>0){
            Customer customer = header.getInvoice().getJob().getCustomer();    
             Double netTotal = header.getTotal()*-1;
            CustomerTransaction transaction = new CustomerTransaction(customer, header.getInvoice(), header.getDate(), CustomerTransType.PAYMENT,netTotal);
            customerTranService.deleteTransaction(header.getInvoice(), 0, CustomerTransType.INVOICE);
            customerTranService.save(transaction);
        }
        return id;
        
        
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public CreditNote get(long id) throws Exception {
        CreditNote creditNote=(CreditNote) genericDao.get(id, CreditNote.class);
        Hibernate.initialize(creditNote.getCreditNoteLines());
        Hibernate.initialize(creditNote.getInvoice().getJob().getCustomer());
        return creditNote;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        //list  
        List<CreditNote> list=new ArrayList<CreditNote>();
        Iterator<CreditNote> iterator=genericDao.list(CreditNote.class).iterator();
        while (iterator.hasNext()) {
            CreditNote creditNote = iterator.next();
            Hibernate.initialize(creditNote.getInvoice().getJob().getCustomer());
            list.add(creditNote);
        }
        
        return list;
    }

    /*-----------------------------
    List method by customer
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Invoice invoice) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("invoice", invoice);
        wheres.add(where);

        return genericDao.list(CreditNote.class, wheres);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(CreditNote creditNote) throws Exception {
        return genericDao.delete(creditNote);
    }

}
