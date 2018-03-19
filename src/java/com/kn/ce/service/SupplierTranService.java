/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.Supplier;
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
@Service("supplierTranService")
public class SupplierTranService {

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
    public long save(SupplierTransaction transaction) throws Exception {
        return genericDao.save(transaction);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(SupplierTransaction transaction) throws Exception {
        return genericDao.update(transaction);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public SupplierTransaction get(long id) throws Exception {
        return (SupplierTransaction) genericDao.get(id, SupplierTransaction.class);
    }
    
    /*-----------------------------
    Get method by ref no and type
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public SupplierTransaction get(long refNo,String type) throws Exception {
         //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("refNo", refNo);
        Where where2 = new Where("type", type);
        wheres.add(where1);
        wheres.add(where2);
        
        return (SupplierTransaction) genericDao.get(SupplierTransaction.class, wheres);
    }
    
    /*-----------------------------
    Get method by supplier
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public SupplierTransaction get(Supplier suplier) throws Exception {
         //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("suplier", suplier);      
        wheres.add(where1);        
        
        return (SupplierTransaction) genericDao.get(SupplierTransaction.class, wheres);
    }
    
    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(SupplierTransaction.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(SupplierTransaction transaction) throws Exception {
        return genericDao.delete(transaction);
    }
    
    
    @Transactional(rollbackFor = Exception.class)
    public long  deleteTransaction(Grn grn,long refNo,String type) throws Exception {
         //set where
        long res=0l;
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("grn", grn);
        Where where2 = new Where("type", type);
        Where where3 = new Where("refNo", refNo);
        wheres.add(where1);
        wheres.add(where2);
        wheres.add(where3);
        
        //get transaction
        SupplierTransaction transaction=(SupplierTransaction) genericDao.get(SupplierTransaction.class, wheres);
        
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
