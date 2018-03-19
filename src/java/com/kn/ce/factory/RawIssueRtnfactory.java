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
import com.kn.ce.model.entity.RawItemIssueRtnLine;
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
@Component("rawIssueRtnfactory")
public class RawIssueRtnfactory {

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

    //Create Object from JSON
    public RawItemIssueRtn createObject(RawItemIssueRtn issueRtn, String lines, User user, String date) throws ParseException, Exception {
        JSONParser jSONParser = new JSONParser();
        Set<RawItemIssueRtnLine> issueRtnLines = new HashSet<>();

        issueRtn.setUser(user);
        issueRtn.setDate(utilityService.parseDateTime(date));

        //create  issue lines    
        JSONArray jSONArray = (JSONArray) jSONParser.parse(lines);//parse string to json array
        Iterator iterator = jSONArray.iterator();

        while (iterator.hasNext()) {//loop json array
            JSONArray jsonLine = (JSONArray) iterator.next();//get json object
            RawItemIssueRtnLine issueLine = new RawItemIssueRtnLine();//define issue line

            //set json object values to line object
            RawItem rawItem = rawItemService.get(Long.parseLong(jsonLine.get(0).toString()));
            issueLine.setRawItem(rawItem);
            issueLine.setQty(Integer.parseInt(jsonLine.get(2).toString()));//qty
            issueLine.setRawItemIssueRtn(issueRtn);//get header object

            //add object to object list
            issueRtnLines.add(issueLine);

        }

        //set line list to object
        issueRtn.setRawItemIssueRtnLines(issueRtnLines);
        return issueRtn;
    }

    //convert Object to JSON String
    public String parseToJson(RawItemIssueRtn itemIssueRtn) throws Exception {
        //define json object
        JSONObject jsonRawIssue = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();

        //head
        if (itemIssueRtn != null) { //check object is not null         
            jsonRawIssue.put("rtnNo", itemIssueRtn.getRtnNo());
            jsonRawIssue.put("rawItemIssue", itemIssueRtn.getRawItemIssue().getIssueNo());
            jsonRawIssue.put("date", utilityService.formatDateTime(itemIssueRtn.getDate()));
            jsonRawIssue.put("user", itemIssueRtn.getUser().getUsername());
            jsonRawIssue.put("remarks", itemIssueRtn.getRemarks());
            //lines
            Iterator<RawItemIssueRtnLine> iterator = itemIssueRtn.getRawItemIssueRtnLines().iterator();//get lines list

            while (iterator.hasNext()) {//loop lines
                RawItemIssueRtnLine line = iterator.next();
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
            jsonRawIssue.put("rtnNo", "New");
            jsonRawIssue.put("date", utilityService.formatDateTime(nowDate));
        }

        return jsonRawIssue.toJSONString();

    }

    //create  List
    public JSONArray createList(List list) {
        Iterator<RawItemIssueRtn> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            RawItemIssueRtn itemIssueRtn = iterator.next();

            JSONObject jsonRawIssue = new JSONObject();

            jsonRawIssue.put("rtnNo", itemIssueRtn.getRtnNo());
            jsonRawIssue.put("rawItemIssue", itemIssueRtn.getRawItemIssue().getIssueNo());
            jsonRawIssue.put("date", utilityService.formatDateTime(itemIssueRtn.getDate()));
            jsonRawIssue.put("user", itemIssueRtn.getUser().getUsername());
            jsonRawIssue.put("remarks", itemIssueRtn.getRemarks());

            //add json object to list
            jSONArray.add(jsonRawIssue);

        }
        return jSONArray;
    }

}
