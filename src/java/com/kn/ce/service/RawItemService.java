/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.dao.RawItemStockDAO;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.RawItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("rawItemService")
public class RawItemService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private RawItemStockDAO rawItemStockDAO;

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
    public long save(RawItem rawItem) throws Exception {
        return genericDao.save(rawItem);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(RawItem rawItem) throws Exception {
        return genericDao.update(rawItem);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItem get(long id) throws Exception {
        RawItem rawItem = (RawItem) genericDao.get(id, RawItem.class);
        //set stock qty
        Double stock = rawItemStockDAO.getStock(rawItem);
        rawItem.setStockQty(stock);

        return rawItem;
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(RawItem.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(RawItem rawItem) throws Exception {
        return genericDao.delete(rawItem);
    }

}
