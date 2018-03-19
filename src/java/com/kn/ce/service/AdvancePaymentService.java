/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("advancePaymentService")
public class AdvancePaymentService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

    @Transactional(rollbackFor = Exception.class)
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
    public long save(AdvancePayment advancePayment) throws Exception {
        return genericDao.save(advancePayment);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(AdvancePayment advancePayment) throws Exception {
        return genericDao.update(advancePayment);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public AdvancePayment get(long id) throws Exception {
        return (AdvancePayment) genericDao.get(id, AdvancePayment.class);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public AdvancePayment getByJob(long id) throws Exception {
        return (AdvancePayment) genericDao.get(id, AdvancePayment.class,"job.jobNo");
    }
    
    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(AdvancePayment.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(AdvancePayment advancePayment) throws Exception {
        return genericDao.delete(advancePayment);
    }

}
