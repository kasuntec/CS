/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnLine;
import com.kn.ce.model.entity.GrnPayment;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.RawItemStock;
import com.kn.ce.model.entity.Supplier;
import com.kn.ce.model.entity.SupplierTransaction;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.model.system.RawItemStockMoveType;
import com.kn.ce.model.system.SupplierTransType;
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
@Service("grnService")
public class GrnService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;

    @Autowired
    private GrnPaymentService grnPaymentService;

    @Autowired
    private SupplierTranService supplierTranService;

    @Autowired
    private RawItemTranService rawItemTranService;

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(Grn header) throws Exception {
        //save head objects
        Long id = genericDao.save(header);

        //save lines objects
        Iterator<GrnLine> iterator = header.getGrnLines().iterator();
        while (iterator.hasNext()) {
            GrnLine line = iterator.next();
            line.setGrn(header);//set saved job
            //save
            genericDao.save(line);

        }
        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(Grn header) throws Exception {
        //update head objects
        Long id = genericDao.update(header);

        //save old lines
        genericDao.deleteLines(GrnLine.class, header.getGrnNo(), "grn.grnNo");

        //save lines objects
        Iterator<GrnLine> iterator = header.getGrnLines().iterator();
        while (iterator.hasNext()) {
            GrnLine line = iterator.next();
            line.setGrn(header);//set saved job
            //save
            genericDao.save(line);

        }

        if (id > 0 && header.getStatus().equals(GrnStatus.UNPAID)) {
            supplierTranService.deleteTransaction(header,header.getGrnNo(), RawItemStockMoveType.GRN);//delete old transaction
            double netTotal = (header.getGrossTotal() - header.getDiscount()) + (header.getVatValue() + header.getNbtValue());
            SupplierTransaction transaction = new SupplierTransaction(header.getSupplier(), header, header.getDate(),header.getGrnNo(), SupplierTransType.GRN, netTotal);
            supplierTranService.save(transaction);

            //save stock transaction
            rawItemTranService.deleteTransaction(header.getGrnNo(), RawItemStockMoveType.GRN);//dele old record
            Iterator<GrnLine> iterator1 = header.getGrnLines().iterator();
            while (iterator1.hasNext()) {
                GrnLine line = iterator1.next();
                //save stock transaction            
                RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getGrnNo(), RawItemStockMoveType.GRN, line.getQty());
                genericDao.save(rawItemStock);

            }
        }
        return id;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Grn get(long id) throws Exception {
        Grn grn = (Grn) genericDao.get(id, Grn.class);
        //initialize proxy
        Hibernate.initialize(grn.getGrnLines());
        Hibernate.initialize(grn.getSupplier());

        //set payment amount          
        grn.setPayAmount(grnPaymentService.getGrnPayAmount(grn));

        return grn;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(String status) throws Exception {
        //define varibles
        List<Grn> grns = new ArrayList<Grn>();
        Iterator<Grn> it = null;

        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("status", status);
        wheres.add(where);

        if (status.equals(GrnStatus.ALL)) {//check status
            //load all 
            it = genericDao.list(Grn.class).iterator();
        } else {
            //list  by status
            it = genericDao.list(Grn.class, wheres).iterator();
        }

        //initialize proxy objects and set calculations fields
        while (it.hasNext()) {
            Grn grn = it.next();
            //initialize proxy
            Hibernate.initialize(grn.getSupplier());
            Hibernate.initialize(grn.getGrnLines());

            //set payment amount          
            grn.setPayAmount(grnPaymentService.getGrnPayAmount(grn));

            //add grn to list
            grns.add(grn);
        }

        return grns;

    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(String status1, String status2) throws Exception {
        //set where      
        Where where1 = new Where("status", status1);
        Where where2 = new Where("status", status2);
        return genericDao.listByOR(Grn.class, where1, where2);

    }

    /*-----------------------------
    List method by suppliers
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Supplier supplier) throws Exception {
        //define varibles
        List<Grn> grns = new ArrayList<Grn>();
        Iterator<Grn> it = null;

        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("supplier", supplier);
        wheres.add(where);

        //list  by supplier
        it = genericDao.list(Grn.class, wheres).iterator();

        //initialize proxy objects and set calculations fields
        while (it.hasNext()) {
            Grn grn = it.next();
            //initialize proxy
            Hibernate.initialize(grn.getSupplier());
            Hibernate.initialize(grn.getGrnLines());

            //add grn to list
            grns.add(grn);
        }

        return grns;

    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(Grn grn) throws Exception {
        return genericDao.delete(grn);
    }

}
