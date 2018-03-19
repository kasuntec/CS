/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.InvoiceLine;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.InvoiceStatus;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.UtilityService;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun Job Factory Class Create sub objects in job object
 */
@Component("invoiceFactory")
public class InvoiceFactory {
    
    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private JobService jobService;
    
    private JSONParser jSONParser = new JSONParser();

    //create job from form values
    public Invoice createObject(String header, User user, double discount) throws ParseException, Exception {
        JSONParser jSONParser = new JSONParser();
        Set<InvoiceLine> invLines = new HashSet<>();

        //create JOb Object
        JSONObject jsonHeader = (JSONObject) jSONParser.parse(header);
        Invoice invoice = new Invoice();
        //job no
        String invNo = (String) jsonHeader.get("invNo");
        if (invNo.equals("New")) {//check new job 
            //set 0 to New Job
            invoice.setInvNo(0l);
        } else {
            invoice.setInvNo(Long.parseLong(invNo));
        }

        //get job
        Job job = jobService.get(Long.parseLong((String) jsonHeader.get("job")));

        //set invoice
        invoice.setJob(job);
        invoice.setUser(user);
        invoice.setDate(utilityService.parseDateTime((String) jsonHeader.get("date")));
        invoice.setType((String) jsonHeader.get("type"));

       
        String status = (String) jsonHeader.get("status");
        invoice.setStatus((String) jsonHeader.get("status"));
         //set status for finalized status
        if (status.equals("Finalized")) {
            invoice.setStatus(InvoiceStatus.UNPAID);
        }        
        invoice.setRemarks((String) jsonHeader.get("remarks"));
        invoice.setDiscount(discount);

        //create lines    
        Iterator<JobLine> it = job.getJobLines().iterator();

        //genarate invoice lines using job lines
        double grossTotal=0.0;
        while (it.hasNext()) {
            JobLine jobLine = it.next();//get job line

            //create and set invoice line
            InvoiceLine invoiceLine = new InvoiceLine();//define job line
            //set json object values to job line object

            invoiceLine.setFinishedItem(jobLine.getFinishedItem());
            invoiceLine.setInvoice(invoice);
            invoiceLine.setWidth(jobLine.getWidth());
            invoiceLine.setHeight(jobLine.getHeight());
            invoiceLine.setQty(jobLine.getQty());
            invoiceLine.setUnitPrice(jobLine.getUnitPrice());
            
            //calculate grossTotal
            grossTotal=grossTotal+(invoiceLine.getUnitPrice()*invoiceLine.getWidth()*invoiceLine.getHeight()*invoiceLine.getQty());

            //add object to object list
            invLines.add(invoiceLine);
            
        }
        invoice.setGrossTotal(grossTotal);

        //set job line set to job object
        invoice.setInvoiceLines(invLines);
        return invoice;
    }

    //convert Job to JSON
    public String parseToJson(Invoice invoice) throws Exception {
        //define json object
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();
        //set values to json object 
        //head
        if (invoice.getInvNo() != null) { //check object is not null        

            jsonObject.put("invNo", invoice.getInvNo());
            jsonObject.put("job", invoice.getJob().getJobNo());
            jsonObject.put("customer", invoice.getJob().getCustomer().getName());
            jsonObject.put("user", invoice.getUser().getUsername());
            jsonObject.put("date", utilityService.formatDateTime(invoice.getDate()));
            jsonObject.put("type", invoice.getType());
            jsonObject.put("status", invoice.getStatus());
            jsonObject.put("remarks", invoice.getRemarks());
            jsonObject.put("grossTotal", invoice.getGrossTotal());
            jsonObject.put("discount", invoice.getDiscount());
            jsonObject.put("advance", invoice.getJob().getAdvance());
            jsonObject.put("payAmount", invoice.getPayAmount());
            jsonObject.put("netTotal", invoice.getGrossTotal() - invoice.getDiscount());
            jsonObject.put("balance", (invoice.getGrossTotal() - invoice.getDiscount()) - invoice.getPayAmount() - invoice.getJob().getAdvance());

            //lines
            Iterator<InvoiceLine> iterator = invoice.getInvoiceLines().iterator();//get lines list

            while (iterator.hasNext()) {//loop lines
                InvoiceLine invoiceLine = iterator.next();

                //set values to json
                JSONObject jsonLine = new JSONObject();
                FinishedItem finishedItem = finishedItemService.get(invoiceLine.getFinishedItem().getItemId());
                jsonLine.put("itemId", invoiceLine.getFinishedItem().getItemId());
                jsonLine.put("itemName", finishedItem.getDescription());
                jsonLine.put("unitPrice", finishedItem.getUnitPrice());
                jsonLine.put("qty", invoiceLine.getQty());
                jsonLine.put("width", invoiceLine.getWidth());
                jsonLine.put("height", invoiceLine.getHeight());

                //add object to array
                jsonLines.add(jsonLine);
            }

            //add json array to json object
            jsonObject.put("invoiceLines", jsonLines);
        } else {
            //new job
            jsonObject.put("invNo", "New");
            jsonObject.put("date", utilityService.formatDateTime(nowDate));
            
        }
        
        return jsonObject.toJSONString();
        
    }

    //create Job List
    public JSONArray createList(List list) {
        Iterator<Invoice> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            Invoice invoice = iterator.next();
            
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put("invNo", invoice.getInvNo());
            jsonObject.put("job", invoice.getJob().getJobNo());
            jsonObject.put("customer", invoice.getJob().getCustomer().getName());
            jsonObject.put("user", invoice.getUser().getUsername());
            jsonObject.put("date", utilityService.formatDateTime(invoice.getDate()));
            jsonObject.put("type", invoice.getType());
            jsonObject.put("status", invoice.getStatus());
            double netTotal = (invoice.getGrossTotal() - invoice.getDiscount());
            jsonObject.put("netTotal", netTotal);            
            double balance = (netTotal - invoice.getJob().getAdvance()) - invoice.getPayAmount();
            jsonObject.put("balance", balance);

            //add json object to list
            jSONArray.add(jsonObject);
            
        }
        return jSONArray;
    }

    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<Invoice> list) {
        //declare varibles
        JSONArray array = new JSONArray();
        
        Iterator<Invoice> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Invoice invoice = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", invoice.getInvNo());
            jSONObject.put("text", invoice.getInvNo() + " - " + utilityService.formatDate(invoice.getDate()));

            //add json object to json array
            array.add(jSONObject);
            
        }
        return array;
    }
    
     /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(Invoice invoice) throws Exception {
        //declare varibles
        JSONArray array = new JSONArray();
        Set<InvoiceLine> invoiceLines = invoice.getInvoiceLines();

        Iterator<InvoiceLine> iterator = invoiceLines.iterator();

        while (iterator.hasNext()) {//loop list
            InvoiceLine invoiceLine = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", invoiceLine.getFinishedItem().getItemId());
            jSONObject.put("text", finishedItemService.get(invoiceLine.getFinishedItem().getItemId()).getDescription());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }
    
}
