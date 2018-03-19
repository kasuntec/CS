/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.dao.DashBoardDAO;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.model.system.InvoiceStatus;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.service.DashBoardService;
import com.kn.ce.service.GrnService;
import com.kn.ce.service.InvoiceService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.RawItemService;
import com.kn.ce.service.UserService;
import com.mysql.fabric.xmlrpc.base.Data;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @author Kasun
 */
@Component("dashBoardFactory")
public class DashBoardFactory {

    //depandency injection
    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private GrnService grnService;
    @Autowired
    private DashBoardService dashBoardService;

    @Autowired
    private RawItemService rawItemService;
    
   

    public Model createDashBoard(Model model) throws Exception {




        

        //------------------------------set values to dashbord----------------
        //JOb Counts
        model.addAttribute("jobOpenCount", jobService.list(JobStatus.OPEN).size());
        model.addAttribute("jobCancelCount", jobService.list(JobStatus.CANCELED).size());
        model.addAttribute("jobStartedCount", jobService.list(JobStatus.STARED).size());
        model.addAttribute("jobFinishedCount", jobService.list(JobStatus.FINISHED).size());
        model.addAttribute("jobInvoicedCount", jobService.list(JobStatus.INVOICED).size());
        model.addAttribute("jobClosedCount", jobService.list(JobStatus.CLOSED).size());
        
        //invoice Counts
        model.addAttribute("invDraftCount", invoiceService.list(InvoiceStatus.DRAFT).size());
        model.addAttribute("invFinlzCount", invoiceService.list(InvoiceStatus.FINALIZED).size());
        model.addAttribute("invCncl", invoiceService.list(InvoiceStatus.CANCELED).size());
        model.addAttribute("invUnPCount", invoiceService.list(InvoiceStatus.UNPAID).size());
        model.addAttribute("invParCount", invoiceService.list(InvoiceStatus.PARTIAL).size());
        model.addAttribute("invPaidCount", invoiceService.list(InvoiceStatus.PAID).size());
        
        //GRN Counts
        model.addAttribute("grnDraftCount", grnService.list(GrnStatus.DRAFT).size());
        model.addAttribute("grnCheckdCount", grnService.list(GrnStatus.CHECKED).size());
        model.addAttribute("grnCncl", grnService.list(GrnStatus.CANCELED).size());
        model.addAttribute("grnUnPCount", grnService.list(GrnStatus.UNPAID).size());
        model.addAttribute("grnParCount", grnService.list(GrnStatus.PARTIAL).size());
        model.addAttribute("grnPaidCount", grnService.list(GrnStatus.PAID).size());
        
        //JOb totals
        model.addAttribute("jobOpenTotal", dashBoardService.getJobTotalByStatus(JobStatus.OPEN));
        model.addAttribute("jobCancelTotal", dashBoardService.getJobTotalByStatus(JobStatus.CANCELED));
        model.addAttribute("jobStartedTotal", dashBoardService.getJobTotalByStatus(JobStatus.STARED));
        model.addAttribute("jobFinishedTotal", dashBoardService.getJobTotalByStatus(JobStatus.FINISHED));
        model.addAttribute("jobInvoicedTotal", dashBoardService.getJobTotalByStatus(JobStatus.INVOICED));
        model.addAttribute("jobClosedTotal", dashBoardService.getJobTotalByStatus(JobStatus.CLOSED));
        
        //invoice totals
        model.addAttribute("invDraftTotal", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.DRAFT));
        model.addAttribute("invFinlzTotal", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.FINALIZED));
        model.addAttribute("invCncl", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.CANCELED));
        model.addAttribute("invUnPTotal", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.UNPAID));
        model.addAttribute("invParTotal", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.PARTIAL));
        model.addAttribute("invPaidTotal", dashBoardService.getinvoiceTotalByStatus(InvoiceStatus.PAID));
        
        //GRN totals---------------------------
        model.addAttribute("grnDraftTotal", dashBoardService.getGrnTotalByStatus(GrnStatus.DRAFT));
        model.addAttribute("grnCheckdTotal", dashBoardService.getGrnTotalByStatus(GrnStatus.CHECKED));
        model.addAttribute("grnCncl", dashBoardService.getGrnTotalByStatus(GrnStatus.CANCELED));
        model.addAttribute("grnUnPTotal", dashBoardService.getGrnTotalByStatus(GrnStatus.UNPAID));
        model.addAttribute("grnParTotal", dashBoardService.getGrnTotalByStatus(GrnStatus.PARTIAL));
        model.addAttribute("grnPaidTotal", dashBoardService.getGrnTotalByStatus(GrnStatus.PAID));
        
        //totals------------------------------
         model.addAttribute("salesIncomeTotal", dashBoardService.getSalesIncome());
         model.addAttribute("purchaseTotal", dashBoardService.getPurchaseTotal());
         model.addAttribute("reciveble", dashBoardService.getTotalRecivbleIncome());
         model.addAttribute("payble", dashBoardService.getPaybleTotal());
         
        //stock------------------------------
         model.addAttribute("reOrderCount", dashBoardService.getReOrderItemsCount());
         model.addAttribute("stockValue", dashBoardService.getStockValueTotal());
        
        return model;
    }

}
