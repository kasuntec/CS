/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemStock;
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
@Service("rawItemTranService")
public class RawItemTranService {

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
    public long save(RawItemStock transaction) throws Exception {
        return genericDao.save(transaction);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(RawItemStock transaction) throws Exception {
        return genericDao.update(transaction);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemStock get(long id) throws Exception {
        return (RawItemStock) genericDao.get(id, RawItemStock.class);
    }

    /*-----------------------------
    Get method by ref no and type
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemStock get(long refNo, String type) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("refNo", refNo);
        Where where2 = new Where("type", type);
        wheres.add(where1);
        wheres.add(where2);

        return (RawItemStock) genericDao.get(RawItemStock.class, wheres);
    }

    /*-----------------------------
    Get method by rawItem
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemStock get(RawItem rawItem) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("rawItem", rawItem);
        wheres.add(where1);

        return (RawItemStock) genericDao.get(RawItemStock.class, wheres);
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(RawItemStock.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(RawItemStock transaction) throws Exception {
        return genericDao.delete(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    public long deleteTransaction(long refNo, String type) throws Exception {
        //set where
        long res = 0l;
        List<Where> wheres = new ArrayList<Where>();
        Where where1 = new Where("refNo", refNo);
        Where where2 = new Where("type", type);
        wheres.add(where1);
        wheres.add(where2);

        //get transaction
        List list = (List) genericDao.get(RawItemStock.class, wheres);
        if (list != null) {
            while (list.iterator().hasNext()) {
                RawItemStock rawItemStock = (RawItemStock) list.iterator().next();
                //delete tranaction
                res = delete(rawItemStock);

            }
        }
        else{
            res=1l;
        }

        return res;
    }

}
