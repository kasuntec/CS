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
import com.kn.ce.model.entity.RawItemIssueRtn;
import com.kn.ce.model.entity.RawItemIssueRtnLine;
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
@Service("rawItemIssueRtnService")
public class RawItemIssueRtnService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private RawItemTranService rawItemTranService;

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(RawItemIssueRtn header) throws Exception {
        //save head objects
        Long id = genericDao.save(header);

        //save lines objects
        Iterator<RawItemIssueRtnLine> iterator = header.getRawItemIssueRtnLines().iterator();
        while (iterator.hasNext()) {
            RawItemIssueRtnLine line = iterator.next();
            line.setRawItemIssueRtn(header);//set saved header
            //save
            genericDao.save(line);
            
              //update stock
            RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getRtnNo(), RawItemStockMoveType.ISSUE_RTN, line.getQty());
            genericDao.save(rawItemStock);

        }
        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(RawItemIssueRtn header) throws Exception {
        //update head objects
        Long id = genericDao.update(header);

        //save old lines
        genericDao.deleteLines(RawItemIssueRtnLine.class, header.getRtnNo(), "rawItemIssueRtn.rtnNo");

        //save lines objects
        Iterator<RawItemIssueRtnLine> iterator = header.getRawItemIssueRtnLines().iterator();
        while (iterator.hasNext()) {
            RawItemIssueRtnLine line = iterator.next();
            line.setRawItemIssueRtn(header);//set saved header
            //save
            genericDao.save(line);
            
             //update stock
            rawItemTranService.deleteTransaction(header.getRtnNo(), RawItemStockMoveType.ISSUE);//dele old record
            RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getRtnNo(), RawItemStockMoveType.ISSUE_RTN, line.getQty());
            genericDao.save(rawItemStock);

        }
        return id;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public RawItemIssueRtn get(long id) throws Exception {
        RawItemIssueRtn rawItemIssueRtn=(RawItemIssueRtn) genericDao.get(id, RawItemIssueRtn.class);
        Hibernate.initialize(rawItemIssueRtn.getRawItemIssueRtnLines());
        return rawItemIssueRtn;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {

        //list 
        return genericDao.list(RawItemIssueRtn.class);

    }

    /*-----------------------------
    List method by Job
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(RawItemIssue rawItemIssue) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("rawItemIssue", rawItemIssue);
        wheres.add(where);

        return genericDao.list(RawItemIssueRtn.class, wheres);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(RawItemIssueRtn rawItemIssueRtn) throws Exception {
        return genericDao.delete(rawItemIssueRtn);
    }

}
