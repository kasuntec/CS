/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemIssue;
import com.kn.ce.model.entity.RawItemIssueLine;
import com.kn.ce.model.entity.RawItemIssueRtn;
import com.kn.ce.model.entity.User;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.RawItemIssueService;
import com.kn.ce.service.RawItemService;
import com.kn.ce.service.UtilityService;
import com.kn.ce.service.WorkerService;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun Job Factory Class Create sub objects in job object
 */
@Component("rawIssuefactory")
public class RawIssuefactory {

    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private JobService jobService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private RawItemService rawItemService;

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

    //Create Object from JSON
    public RawItemIssue createObject(String issueString, String lines, User user) throws ParseException, Exception {
        JSONParser jSONParser = new JSONParser();
        Set<RawItemIssueLine> issueLines = new HashSet<>();

        //create  Object
        JSONObject jsonRawIssue = (JSONObject) jSONParser.parse(issueString);
        RawItemIssue itemIssue = new RawItemIssue();
        //issue no
        String issueNo = (String) jsonRawIssue.get("issueNo");
        if (issueNo.equals("New")) {//check new issue 
            //set 0 to New Job
            itemIssue.setIssueNo(0l);
        } else {
            itemIssue.setIssueNo(Long.parseLong(issueNo));
        }
        itemIssue.setJob(jobService.get(Long.parseLong((String) jsonRawIssue.get("job"))));
        itemIssue.setUser(user);
        itemIssue.setWorker(workerService.get(Long.parseLong((String) jsonRawIssue.get("worker"))));
        itemIssue.setDate(utilityService.parseDateTime((String) jsonRawIssue.get("date")));
        itemIssue.setRemarks((String) jsonRawIssue.get("remarks"));
        //create  issue lines    
        JSONArray jSONArray = (JSONArray) jSONParser.parse(lines);//parse string to json array
        Iterator iterator = jSONArray.iterator();

        while (iterator.hasNext()) {//loop json array
            JSONArray jsonLine = (JSONArray) iterator.next();//get json object
            RawItemIssueLine issueLine = new RawItemIssueLine();//define issue line

            //set json object values to line object
            RawItem rawItem = rawItemService.get(Long.parseLong(jsonLine.get(0).toString()));
            issueLine.setRawItem(rawItem);            
            issueLine.setQty(Integer.parseInt(jsonLine.get(2).toString()));//qty
            issueLine.setRawItemIssue(itemIssue);//get header object

            //add object to object list
            issueLines.add(issueLine);

        }

        //set line list to object
        itemIssue.setRawItemIssueLines(issueLines);
        return itemIssue;
    }

    //convert Object to JSON String
    public String parseToJson(RawItemIssue rawIssue) throws Exception {
        //define json object
        JSONObject jsonRawIssue = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();

        //head
        if (rawIssue!=null) { //check object is not null         
            jsonRawIssue.put("issueNo", rawIssue.getIssueNo());
            jsonRawIssue.put("job", rawIssue.getJob().getJobNo());
            jsonRawIssue.put("date", utilityService.formatDateTime(rawIssue.getDate()));
            jsonRawIssue.put("user", rawIssue.getUser().getUsername());
            jsonRawIssue.put("worker", rawIssue.getWorker().getId());
            jsonRawIssue.put("remarks", rawIssue.getRemarks());
            //lines
            Iterator<RawItemIssueLine> iterator = rawIssue.getRawItemIssueLines().iterator();//get lines list

            while (iterator.hasNext()) {//loop lines
                RawItemIssueLine line = iterator.next();
                //set values to json
                JSONObject jsonJobLine = new JSONObject();
                RawItem rawItem = rawItemService.get(line.getRawItem().getItemId());
                jsonJobLine.put("itemId", rawItem.getItemId());
                jsonJobLine.put("itemName", rawItem.getDescription());
                jsonJobLine.put("qty", line.getQty());

                //add object to array
                jsonLines.add(jsonJobLine);
            }

            //add json array to json object
            jsonRawIssue.put("lines", jsonLines);
        } else {
            //new job
            jsonRawIssue.put("issueNo", "New");
            jsonRawIssue.put("date", utilityService.formatDateTime(nowDate));
        }

        return jsonRawIssue.toJSONString();

    }

    //create  List
    public JSONArray createList(List list) {
        Iterator<RawItemIssue> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            RawItemIssue object = iterator.next();

            JSONObject jSONObject = new JSONObject();

            jSONObject.put("issueNo", object.getIssueNo());
            jSONObject.put("job", object.getJob().getJobNo());
            jSONObject.put("date", utilityService.formatDateTime(object.getDate()));
            jSONObject.put("user", object.getUser().getUsername());
            jSONObject.put("worker", object.getWorker().getId());
            jSONObject.put("remarks", object.getRemarks());

            //add json object to list
            jSONArray.add(jSONObject);

        }
        return jSONArray;
    }
    
    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(RawItemIssue rawItemIssue) throws Exception {
        //declare varibles
        JSONArray array = new JSONArray();
        Set<RawItemIssueLine> rawItemIssueLines = rawItemIssue.getRawItemIssueLines();

        Iterator<RawItemIssueLine> iterator = rawItemIssueLines.iterator();

        while (iterator.hasNext()) {//loop list
            RawItemIssueLine issueLine = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", issueLine.getRawItem().getItemId());
            jSONObject.put("text", rawItemService.get(issueLine.getRawItem().getItemId()).getDescription());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }
    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<RawItemIssue> list) throws Exception {
        //declare varibles
        JSONArray array = new JSONArray();
      

        Iterator<RawItemIssue> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            RawItemIssue itemIssue = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", itemIssue.getIssueNo());
            jSONObject.put("text",itemIssue.getIssueNo()+"-"+utilityService.formatDate(itemIssue.getDate()));

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }
    
    
    

}
