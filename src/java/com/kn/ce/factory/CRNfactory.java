/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.CreditNote;
import com.kn.ce.model.entity.CreditNoteLine;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnLine;
import com.kn.ce.model.entity.GrnReturn;
import com.kn.ce.model.entity.GrnReturnLine;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.User;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.RawItemService;
import com.kn.ce.service.SupplierService;
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
@Component("cRNfactory")
public class CRNfactory {

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
    @Autowired
    private SupplierService supplierService;

    private JSONParser jSONParser = new JSONParser();

   

    //Create Object from JSON
    public CreditNote createObject(CreditNote creditNote, String strLines, User user,String date) throws ParseException, Exception {
        //define varibles
        JSONParser jSONParser = new JSONParser();
        Set<CreditNoteLine> lines = new HashSet<CreditNoteLine>(0);

        creditNote.setUser(user);
        creditNote.setDate(utilityService.parseDate(date));

        //create lines    
        JSONArray jSONArray = (JSONArray) jSONParser.parse(strLines);//parse string to json array
        Iterator iterator = jSONArray.iterator();
        double total=0.0;
        while (iterator.hasNext()) {//loop json array
            JSONArray jsonLine = (JSONArray) iterator.next();//get json object
            CreditNoteLine line = new CreditNoteLine();//define issue line

            //set json object values to line object
            line.setCreditNote(creditNote);//get header object
            FinishedItem finishedItem = finishedItemService.get(Long.parseLong(jsonLine.get(0).toString()));
            line.setFinishedItem(finishedItem);
            line.setQty(Integer.parseInt(jsonLine.get(2).toString()));//qty
            line.setPrice(Double.parseDouble(jsonLine.get(3).toString()));//qty
            
            //calculate total
            total=total+(line.getQty()*line.getPrice());
            //add object to object list
            lines.add(line);

        }
        
         //calculate and set values
       creditNote.setTotal(total);
        
        

        //set line list to object
        creditNote.setCreditNoteLines(lines);
        return creditNote;
    }

    //convert Object to JSON String
    public String parseToJson(CreditNote header) throws Exception {
        //define json object
        JSONObject jsonHeader = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();
        
 
     
        //head
        if (header != null) { //check object is not null         
            jsonHeader.put("crnNo", header.getCrnNo());
            jsonHeader.put("invoice", header.getInvoice().getInvNo());
            jsonHeader.put("customerName", customerService.get(header.getInvoice().getJob().getCustomer().getCustId()).getName());
            jsonHeader.put("user", header.getUser().getUsername());           
            jsonHeader.put("date", utilityService.formatDateTime(header.getDate()));           
            jsonHeader.put("remarks", header.getRemarks());
            jsonHeader.put("total", header.getTotal());
           
           
            //lines
            Iterator<CreditNoteLine> iterator = header.getCreditNoteLines().iterator();//get lines list
            


            while (iterator.hasNext()) {//loop lines 
                CreditNoteLine line = iterator.next();
                //set values to json
                JSONObject jsonLine = new JSONObject();
                FinishedItem finishedItem = finishedItemService.get(line.getFinishedItem().getItemId());
                jsonLine.put("itemId", finishedItem.getItemId());
                jsonLine.put("itemName", finishedItem.getDescription());
                jsonLine.put("qty", line.getQty());
                jsonLine.put("price", line.getPrice());

                //add object to array
                jsonLines.add(jsonLine);
            }

            //add json array to json object
            jsonHeader.put("lines", jsonLines);
        } else {
            //new job
            jsonHeader.put("crnNo", "New");
            jsonHeader.put("date", utilityService.formatDateTime(nowDate));
        }

        return jsonHeader.toJSONString();

    }

    //create  List
    public JSONArray createList(List list) throws Exception {
        Iterator<CreditNote> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            CreditNote header = iterator.next();

            JSONObject jsonHeader = new JSONObject();

            jsonHeader.put("crnNo", header.getCrnNo());
            jsonHeader.put("invoice", header.getInvoice().getInvNo());
            jsonHeader.put("customerName", customerService.get(header.getInvoice().getJob().getCustomer().getCustId()).getName());
            jsonHeader.put("user", header.getUser().getUsername());           
            jsonHeader.put("date", utilityService.formatDateTime(header.getDate()));           
            jsonHeader.put("remarks", header.getRemarks());
            jsonHeader.put("total", header.getTotal());

            //add json object to list
            jSONArray.add(jsonHeader);

        }
        return jSONArray;
    }
    
    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<CreditNote> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<CreditNote> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            CreditNote crn = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", crn.getCrnNo());
            jSONObject.put("text", crn.getCrnNo() + " - " + utilityService.formatDate(crn.getDate()));

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

}
