/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.dao.RawItemStockDAO;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.FinishedItemCategory;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemCategory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("finishedItemCatService")
public class FinishedItemCatService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
   


    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    public long save(FinishedItemCategory itemCategory) throws Exception {
        return genericDao.save(itemCategory);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(FinishedItemCategory itemCategory) throws Exception {
        return genericDao.update(itemCategory);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemCategory get(String id) throws Exception {      
        return (RawItemCategory) genericDao.get(id, FinishedItemCategory.class);
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(FinishedItemCategory.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(FinishedItemCategory itemCategory) throws Exception {
        return genericDao.delete(itemCategory);
    }

}
