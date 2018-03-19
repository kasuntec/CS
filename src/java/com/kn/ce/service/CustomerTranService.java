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
import com.kn.ce.model.entity.RawItemStock;
import com.kn.ce.model.entity.SupplierTransaction;
import com.kn.ce.model.system.Where;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("customerTranService")
public class CustomerTranService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

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
    public long save(CustomerTransaction transaction) throws Exception {
        return genericDao.save(transaction);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(CustomerTransaction transaction) throws Exception {
        return genericDao.update(transaction);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public CustomerTransaction get(long id) throws Exception {
        return (CustomerTransaction) genericDao.get(id, CustomerTransaction.class);
    }
    
    /*-----------------------------
    Get method by ref no and type
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public CustomerTransaction get(long refNo,String type) throws Exception {
         //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("refNo", refNo);
        Where where2 = new Where("type", type);
        wheres.add(where1);
        wheres.add(where2);
        
        return (CustomerTransaction) genericDao.get(CustomerTransaction.class, wheres);
    }
    
     /*-----------------------------
    Get method by Customer
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public CustomerTransaction get(Customer customer) throws Exception {
         //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("customer", customer);      
        wheres.add(where1);        
        
        return (CustomerTransaction) genericDao.get(CustomerTransaction.class, wheres);
    }
    
    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(CustomerTransaction.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(CustomerTransaction transaction) throws Exception {
        return genericDao.delete(transaction);
    }
    
    
    @Transactional(rollbackFor = Exception.class)
    public long  deleteTransaction(Invoice invoice,long refNo,String type) throws Exception {
         //set where
        long res=0l;
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("invoice", invoice);
        Where where2 = new Where("type", type);
        Where where3 = new Where("refNo", refNo);
        wheres.add(where1);
        wheres.add(where2);
        wheres.add(where3);
        
        //get transaction
        CustomerTransaction transaction=(CustomerTransaction) genericDao.get(CustomerTransaction.class, wheres);
        
        //delete tranaction
        if(transaction!=null){
             res = delete(transaction);
        }
        else{
            res=1l;
        }
       
        
        
        return res;
    }

}
