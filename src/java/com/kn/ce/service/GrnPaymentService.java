/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnPayment;
import com.kn.ce.model.entity.SupplierTransaction;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.model.system.SupplierTransType;
import com.kn.ce.model.system.Where;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("grnPaymentService")
public class GrnPaymentService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private SupplierTranService supplierTranService;
    @Autowired
    private GrnService grnService;

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(GrnPayment payment) throws Exception {
        long res = genericDao.save(payment);

        //save tranaction
        if (res > 0) {
            SupplierTransaction transaction = new SupplierTransaction(payment.getGrn().getSupplier(), payment.getGrn(), payment.getDate(), payment.getId(), SupplierTransType.PAYMENT, payment.getAmount() * -1);
            supplierTranService.save(transaction);

            //change grn status
            Grn grn = grnService.get(payment.getGrn().getGrnNo());
            double balance = (grn.getGrossTotal() - grn.getDiscount() + grn.getNbtValue() + grn.getVatValue()) - grn.getPayAmount();

            if (balance <= 0) {
                grn.setStatus(GrnStatus.PAID);
            } else {
                grn.setStatus(GrnStatus.PARTIAL);
            }
            grnService.update(grn);
        }

        return res;
    }


    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(GrnPayment payment) throws Exception {
        long res = genericDao.update(payment);

        //save tranaction
        if (res > 0) {
            SupplierTransaction transaction = new SupplierTransaction(payment.getGrn().getSupplier(), payment.getGrn(), payment.getDate(), payment.getId(), SupplierTransType.PAYMENT, payment.getAmount() * -1);
            supplierTranService.deleteTransaction(payment.getGrn(), payment.getId(), SupplierTransType.PAYMENT);//delete old tranaction
            supplierTranService.save(transaction);

            //change grn status
            Grn grn = grnService.get(payment.getGrn().getGrnNo());
            double balance = (grn.getGrossTotal() - grn.getDiscount() + grn.getNbtValue() + grn.getVatValue()) - grn.getPayAmount();

            if (balance <= 0) {
                grn.setStatus(GrnStatus.PAID);
            } else {
                grn.setStatus(GrnStatus.PARTIAL);
            }
            grnService.update(grn);
        }
        return res;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public GrnPayment get(long id) throws Exception {
        return (GrnPayment) genericDao.get(id, GrnPayment.class);
    }

    //get payment total  by grn
    @Transactional(rollbackFor = Exception.class)
    public Double getGrnPayAmount(Grn grn) throws Exception {
        Double payAmount = 0.0;
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("grn", grn);
        wheres.add(where);
        Iterator<GrnPayment> list = genericDao.list(GrnPayment.class, wheres).iterator();

        while (list.hasNext()) {//loop 
            GrnPayment payment = list.next();
            payAmount += payment.getAmount();

        }

        return payAmount;
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        return genericDao.list(GrnPayment.class);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(GrnPayment payment) throws Exception {
        return genericDao.delete(payment);
    }

}
