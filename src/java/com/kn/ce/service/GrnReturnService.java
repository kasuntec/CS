/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnLine;
import com.kn.ce.model.entity.GrnReturn;
import com.kn.ce.model.entity.GrnReturnLine;
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
@Service("grnReturnService")
public class GrnReturnService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private SupplierTranService supplierTranService;
    @Autowired
    GrnService grnService;
    
     @Autowired
    private RawItemTranService rawItemTranService;
   

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(GrnReturn header) throws Exception {
        //save head objects
        Long res = genericDao.save(header);

        //save lines objects
        Iterator<GrnReturnLine> iterator = header.getGrnReturnLines().iterator();
        while (iterator.hasNext()) {
            GrnReturnLine line = iterator.next();
            line.setGrnReturn(header);//set saved job
            //save
            genericDao.save(line);
            
             //save stock transaction
            rawItemTranService.deleteTransaction(header.getRtnNo(), RawItemStockMoveType.GRN_RTN);//dele old record
             Iterator<GrnReturnLine> iterator1 = header.getGrnReturnLines().iterator();
            while (iterator1.hasNext()) {
                GrnReturnLine line1 = iterator.next();
                //save stock transaction            
                RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getRtnNo(), RawItemStockMoveType.GRN_RTN, line.getQty()*-1);
                genericDao.save(rawItemStock);

            }

        }
          //get grn return
        Grn grn=grnService.get(header.getGrn().getGrnNo());
        //save supplier tranaction
        if(res>0){
            SupplierTransaction transaction=new SupplierTransaction(grn.getSupplier(), grn, header.getDate(), SupplierTransType.GRNRTN, header.getTotal()*-1);
            supplierTranService.save(transaction);
            
            //save stock transaction
            rawItemTranService.deleteTransaction(header.getRtnNo(), RawItemStockMoveType.GRN_RTN);//dele old record
            while (iterator.hasNext()) {
                GrnReturnLine line = iterator.next();
                //save stock transaction            
                RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getRtnNo(), RawItemStockMoveType.GRN_RTN, line.getQty()*-1);
                genericDao.save(rawItemStock);

            }
        }
        return res;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(GrnReturn header) throws Exception {
        //update head objects
        Long res = genericDao.update(header);
        
        //init poxy objects
       

        //save old lines
        genericDao.deleteLines(GrnReturnLine.class, header.getRtnNo(), "grnReturn.rtnNo");

        //save lines objects
        Iterator<GrnReturnLine> iterator = header.getGrnReturnLines().iterator();
        while (iterator.hasNext()) {
            GrnReturnLine line = iterator.next();
            line.setGrnReturn(header);//set saved job
            //save
            genericDao.save(line);

        }
        //get grn return
        Grn grn=grnService.get(header.getGrn().getGrnNo());
        //save supplier tranaction
         if(res>0){
            SupplierTransaction transaction=new SupplierTransaction(grn.getSupplier(), grn, header.getDate(), SupplierTransType.GRNRTN, header.getTotal()*-1);
             supplierTranService.deleteTransaction(header.getGrn(),0, SupplierTransType.GRNRTN);//delete old tranaction
            supplierTranService.save(transaction);
            
            //save stock transaction
            rawItemTranService.deleteTransaction(header.getRtnNo(), RawItemStockMoveType.GRN_RTN);//dele old record
            while (iterator.hasNext()) {
                GrnReturnLine line = iterator.next();
                //save stock transaction            
                RawItemStock rawItemStock = new RawItemStock(line.getRawItem(), header.getDate(), header.getRtnNo(), RawItemStockMoveType.GRN_RTN, line.getQty()*-1);
                genericDao.save(rawItemStock);

            }
        }
        return res;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public GrnReturn get(long id) throws Exception {
        GrnReturn grnReturn=(GrnReturn) genericDao.get(id, GrnReturn.class);
        Hibernate.initialize(grnReturn.getGrnReturnLines());
         Hibernate.initialize(grnReturn.getGrn().getSupplier());
        return grnReturn;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        List<GrnReturn> list =new ArrayList<GrnReturn>();
        Iterator<GrnReturn> iterator = genericDao.list(GrnReturn.class).iterator();
        while (iterator.hasNext()) {
            GrnReturn grnReturn = iterator.next();
            Hibernate.initialize(grnReturn.getGrn().getSupplier());
            
            //addd to list
            list.add(grnReturn);
            
        }
        return genericDao.list(GrnReturn.class);

    }

    /*-----------------------------
    List method by supplier
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Supplier supplier) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("grn.supplier", supplier);
        wheres.add(where);

        return genericDao.list(GrnReturn.class, wheres);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(Grn grn) throws Exception {
        return genericDao.delete(grn);
    }

}
