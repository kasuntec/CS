/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.Supplier;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("finishedItemService")
public class FinishedItemService {

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
    public long save(FinishedItem finishedItem) throws Exception {
        return genericDao.save(finishedItem);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(FinishedItem finishedItem) throws Exception {
        return genericDao.update(finishedItem);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public FinishedItem get(long id) throws Exception {
        return (FinishedItem) genericDao.get(id, FinishedItem.class);
    }
    
    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(FinishedItem.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(FinishedItem  finishedItem) throws Exception {
        return genericDao.delete(finishedItem);
    }

}
