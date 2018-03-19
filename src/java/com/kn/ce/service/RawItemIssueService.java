/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.RawItemIssue;
import com.kn.ce.model.entity.RawItemIssueLine;
import com.kn.ce.model.entity.RawItemStock;
import com.kn.ce.model.system.RawItemStockMoveType;
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
@Service("rawItemIssueService")
public class RawItemIssueService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private RawItemTranService rawItemTranService;

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
    public long save(RawItemIssue header) throws Exception {
        //save head objects
        Long id = genericDao.save(header);

        //save lines objects
        Iterator<RawItemIssueLine> iterator = header.getRawItemIssueLines().iterator();
        while (iterator.hasNext()) {
            RawItemIssueLine line = iterator.next();
            line.setRawItemIssue(header);//set saved header
            //save
            genericDao.save(line);

            //update stock
            RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getIssueNo(), RawItemStockMoveType.ISSUE, line.getQty()*-1);
            genericDao.save(rawItemStock);

        }

        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(RawItemIssue header) throws Exception {
        //update head objects
        Long id = genericDao.update(header);

        //save old lines
        genericDao.deleteLines(RawItemIssueLine.class, header.getIssueNo(), "rawItemIssue.issueNo");

        //save lines objects
        Iterator<RawItemIssueLine> iterator = header.getRawItemIssueLines().iterator();
        while (iterator.hasNext()) {
            RawItemIssueLine line = iterator.next();
            line.setRawItemIssue(header);//set saved header
            //save
            genericDao.save(line);

            //update stock
            rawItemTranService.deleteTransaction(header.getIssueNo(), RawItemStockMoveType.ISSUE);//dele old record
            RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getIssueNo(), RawItemStockMoveType.ISSUE, line.getQty()*-1);
            genericDao.save(rawItemStock);

        }
        return id;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemIssue get(long id) throws Exception {
        RawItemIssue rawItemIssue = (RawItemIssue) genericDao.get(id, RawItemIssue.class);
        Hibernate.initialize(rawItemIssue.getRawItemIssueLines());
        Hibernate.initialize(rawItemIssue.getWorker());
        return rawItemIssue;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {

        //list 
        return genericDao.list(RawItemIssue.class);

    }

    /*-----------------------------
    List method by Job
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Job job) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("job", job);
        wheres.add(where);

        return genericDao.list(RawItemIssue.class, wheres);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(RawItemIssue itemIssue) throws Exception {
        return genericDao.delete(itemIssue);
    }

}
