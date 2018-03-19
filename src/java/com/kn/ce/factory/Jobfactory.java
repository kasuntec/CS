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
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import com.kn.ce.model.entity.User;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
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
@Component("jobfactory")
public class Jobfactory {

    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UtilityService utilityService;

    private JSONParser jSONParser = new JSONParser();

    public void setFinishedItemService(FinishedItemService finishedItemService) {
        this.finishedItemService = finishedItemService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setUtilityService(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    //create job from form values
    public Job createJob(String jobString, String lines, User user) throws ParseException, Exception {
        JSONParser jSONParser = new JSONParser();
        Set<JobLine> jobLines = new HashSet<>();

        //create JOb Object
        JSONObject jsonJob = (JSONObject) jSONParser.parse(jobString);
        Job job = new Job();
        //job no
        String jobNo = (String) jsonJob.get("jobNo");
        if (jobNo.equals("New")) {//check new job 
            //set 0 to New Job
            job.setJobNo(0l);
        } else {
            job.setJobNo(Long.parseLong(jobNo));
        }
        job.setCustomer(customerService.get(Long.parseLong((String) jsonJob.get("customer"))));
        job.setUser(user);
        job.setDate(utilityService.parseDateTime((String) jsonJob.get("date")));
        job.setDelivryDate(utilityService.parseDate((String) jsonJob.get("delivryDate")));
        job.setRemarks((String) jsonJob.get("remarks"));
        job.setStatus((String) jsonJob.get("status"));

        //create  job lines    
        JSONArray jSONArray = (JSONArray) jSONParser.parse(lines);//parse string to json array
        Iterator iterator = jSONArray.iterator();

        while (iterator.hasNext()) {//loop json array
            JSONArray jsonLine = (JSONArray) iterator.next();//get json object
            JobLine jobLine = new JobLine();//define job line
            //set json object values to job line object
            FinishedItem finishedItem = finishedItemService.get(Long.parseLong(jsonLine.get(0).toString()));
            jobLine.setFinishedItem(finishedItem);
            jobLine.setJob(job);
            jobLine.setWidth(Long.parseLong(jsonLine.get(3).toString()));
            jobLine.setHeight(Long.parseLong(jsonLine.get(4).toString()));
            jobLine.setQty(Integer.parseInt(jsonLine.get(5).toString()));
            jobLine.setUnitPrice(finishedItem.getUnitPrice());
            jobLine.setRemarks((String) jsonLine.get(6));

            //add object to object list
            jobLines.add(jobLine);

        }

        //set job line set to job object
        job.setJobLines(jobLines);
        return job;
    }

    //create Adavance
    public AdvancePayment createAdvancePay(String advance, Job job, User user) throws ParseException {
        //define varibles
        AdvancePayment advancePayment = new AdvancePayment();
        JSONParser jSONParser = new JSONParser();

        if (advance != "") {
            //parse to json
            JSONObject jsonAdvance = (JSONObject) jSONParser.parse(advance);

            //set object using JSON
            advancePayment.setId(0);
            advancePayment.setDate(job.getDate());
            advancePayment.setPayType((String) jsonAdvance.get("payType"));
            advancePayment.setAmount(Double.parseDouble((String) jsonAdvance.get("amount")));
            advancePayment.setRefNo((String) jsonAdvance.get("refNo"));
        }

        return advancePayment;
    }

    //convert Job to JSON
    public String parseToJson(Job job) throws Exception {
        //define json object
        JSONObject jsonJob = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();
        //set values to json object 
        //head
        if (job.getJobNo() != null) { //check object is not null         
            jsonJob.put("jobNo", job.getJobNo());
            jsonJob.put("date", utilityService.formatDateTime(job.getDate()));
            jsonJob.put("delivryDate", utilityService.formatDate(job.getDelivryDate()));
            jsonJob.put("customer", job.getCustomer().getCustId());
            jsonJob.put("customerName", job.getCustomer().getName());
            jsonJob.put("mobile", job.getCustomer().getMobile());
            jsonJob.put("user", job.getUser().getUsername());
            jsonJob.put("remarks", job.getRemarks());
            jsonJob.put("status", job.getStatus());

            //lines
            Iterator<JobLine> iterator = job.getJobLines().iterator();//get lines list

            while (iterator.hasNext()) {//loop lines
                JobLine jobLine = iterator.next();

                //set values to json
                JSONObject jsonJobLine = new JSONObject();
                FinishedItem finishedItem = finishedItemService.get(jobLine.getFinishedItem().getItemId());
                jsonJobLine.put("itemId", jobLine.getFinishedItem().getItemId());
                jsonJobLine.put("itemName", finishedItem.getDescription());
                jsonJobLine.put("unitPrice", finishedItem.getUnitPrice());
                jsonJobLine.put("qty", jobLine.getQty());
                jsonJobLine.put("width", jobLine.getWidth());
                jsonJobLine.put("height", jobLine.getHeight());
                jsonJobLine.put("remarks", jobLine.getRemarks());

                //add object to array
                jsonLines.add(jsonJobLine);
            }

            //add json array to json object
            jsonJob.put("jobLines", jsonLines);
        } else {
            //new job
            jsonJob.put("jobNo", "New");
            jsonJob.put("date", utilityService.formatDateTime(nowDate));
            jsonJob.put("delivryDate", utilityService.formatDate(nowDate));

        }

        return jsonJob.toJSONString();

    }

    //convert adavace to JSON
    public String parseToJson(AdvancePayment advPayment) {
        //define json object
        JSONObject jsonAdv = new JSONObject();
        //set values to json object       
        if (advPayment.getId()!= 0l) {
            jsonAdv.put("advId", advPayment.getId());
            jsonAdv.put("date", utilityService.formatDateTime(advPayment.getDate()));
            jsonAdv.put("payType", advPayment.getPayType());
            jsonAdv.put("amount", advPayment.getAmount());
            jsonAdv.put("refNo", advPayment.getRefNo());
        } else {
            //set empty onject
            jsonAdv.put("advId", "New");
            jsonAdv.put("date", utilityService.formatDateTime(new Date()));
        }

        return jsonAdv.toJSONString();
    }

    //create Job List
    public JSONArray createList(List list) {
        Iterator<Job> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            Job job = iterator.next();

            JSONObject jSONObject = new JSONObject();

            jSONObject.put("jobNo", job.getJobNo());
            jSONObject.put("customer", job.getCustomer().getName());
            jSONObject.put("mobile", job.getCustomer().getMobile());
            jSONObject.put("user", job.getUser().getUsername());
            jSONObject.put("date", utilityService.formatDate(job.getDate()));
            jSONObject.put("delivryDate", utilityService.formatDate(job.getDelivryDate()));
            jSONObject.put("amount", job.getAmount());
            jSONObject.put("balance", job.getAmount()-job.getAdvance());//claculate balance amount
            jSONObject.put("status", job.getStatus());

            //add json object to list
            jSONArray.add(jSONObject);

        }
        return jSONArray;
    }
    
    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<Job> list){
        //declare varibles
        JSONArray array=new JSONArray();
        
        
        Iterator<Job> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Job job = iterator.next();
            JSONObject jSONObject=new JSONObject();//create json object
            //put object to json object
  
            jSONObject.put("id", job.getJobNo());
            jSONObject.put("text", job.getJobNo()+" - "+utilityService.formatDate(job.getDate()));
           
            
            //add json object to json array
            array.add(jSONObject);
            
        }
        return array;
    }
    
    
}
