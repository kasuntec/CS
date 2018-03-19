/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnLine;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemIssue;
import com.kn.ce.model.entity.RawItemIssueLine;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.RawItemIssueService;
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
@Component("grnfactory")
public class Grnfactory {

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
    public Grn createObject(String header, String strLines, User user,double discount,double vatValue,double nbtValue) throws ParseException, Exception {
        //define varibles
        JSONParser jSONParser = new JSONParser();
        Set<GrnLine> lines = new HashSet<GrnLine>(0);

        //create  Object
        JSONObject jsonHeader = (JSONObject) jSONParser.parse(header);
        Grn grn = new Grn();
        //issue no
        String id = (String) jsonHeader.get("grnNo");
        if (id.equals("New")) {//check new issue 
            //set 0 to New Job
            grn.setGrnNo(0l);
        } else {
            grn.setGrnNo(Long.parseLong(id));
        }
        
        //tax or not
        String tax=(String) jsonHeader.get("tax");
        if(tax!=null &&tax.trim().equals("on")){
            grn.setTax(true);
        }
        else{
            grn.setTax(false);
        }
        grn.setSupplier(supplierService.get(Long.parseLong((String) jsonHeader.get("supplier"))));
        grn.setUser(user);
        grn.setRefNo((String) jsonHeader.get("refNo"));
        grn.setDate(utilityService.parseDateTime((String) jsonHeader.get("date")));
        //WHEN CHECKED STATUS
        String status=(String) jsonHeader.get("status");
        if(status.equals(GrnStatus.CHECKED)){
            status=GrnStatus.UNPAID;
        }
        grn.setStatus(status);
        grn.setRemarks((String) jsonHeader.get("remarks"));
        grn.setDiscount(discount);
        
       

        //create lines    
        JSONArray jSONArray = (JSONArray) jSONParser.parse(strLines);//parse string to json array
        Iterator iterator = jSONArray.iterator();
        double grossTotal=0.0;
        while (iterator.hasNext()) {//loop json array
            JSONArray jsonLine = (JSONArray) iterator.next();//get json object
            GrnLine line = new GrnLine();//define issue line

            //set json object values to line object
            line.setGrn(grn);//get header object
            RawItem rawItem = rawItemService.get(Long.parseLong(jsonLine.get(0).toString()));
            line.setRawItem(rawItem);
            line.setQty(Integer.parseInt(jsonLine.get(2).toString()));//qty
            line.setPrice(Double.parseDouble(jsonLine.get(3).toString()));//qty
            
            //calculate total
            grossTotal=grossTotal+(line.getQty()*line.getPrice());
            //add object to object list
            lines.add(line);

        }
        
         //calculate and set values
        double nbtRate=nbtValue==0.00?0.00:nbtValue/(grossTotal-discount)*100;
        double vatRate=vatValue==0.00?0.00:vatValue/(grossTotal+nbtValue-discount)*100;
        grn.setVatRate(vatRate);
        grn.setNbtRate(nbtRate);
        grn.setGrossTotal(grossTotal);
        grn.setVatValue(vatValue);
        grn.setNbtValue(nbtValue);
        
        

        //set line list to object
        grn.setGrnLines(lines);
        return grn;
    }

    //convert Object to JSON String
    public String parseToJson(Grn header) throws Exception {
        //define json object
        JSONObject jsonHeader = new JSONObject();
        JSONArray jsonLines = new JSONArray();
        Date nowDate = new Date();

        //head
        if (header != null) { //check object is not null         
            jsonHeader.put("grnNo", header.getGrnNo());
            jsonHeader.put("supplier", header.getSupplier().getSupId());
            jsonHeader.put("supplierName", supplierService.get(header.getSupplier().getSupId()).getName());
            jsonHeader.put("user", header.getUser().getUsername());
            jsonHeader.put("tax", header.isTax());
            jsonHeader.put("refNo", header.getRefNo());
            jsonHeader.put("date", utilityService.formatDateTime(header.getDate()));
            jsonHeader.put("status", header.getStatus());
            jsonHeader.put("remarks", header.getRemarks());
            jsonHeader.put("grossTotal", header.getGrossTotal());
            jsonHeader.put("discount", header.getDiscount());
            jsonHeader.put("vatRate", header.getVatRate());
            jsonHeader.put("nbtRate", header.getNbtRate());

            //calculations
            Double subTotal = (header.getGrossTotal() - header.getDiscount());
            Double taxValue = (subTotal * (header.getVatRate() / 100)) + (subTotal * (header.getNbtRate() / 100));
            Double netTotal = taxValue + subTotal;

            //set calculations
            jsonHeader.put("vatValue", header.getVatValue());
            jsonHeader.put("nbtValue", header.getNbtValue());
            jsonHeader.put("netTotal", utilityService.round(netTotal,2));
            jsonHeader.put("payAmount", header.getPayAmount());
            jsonHeader.put("duebalance",utilityService.round(netTotal - header.getPayAmount(),2) );
            //lines
            Iterator<GrnLine> iterator = header.getGrnLines().iterator();//get lines list

            while (iterator.hasNext()) {//loop lines 
                GrnLine line = iterator.next();
                //set values to json
                JSONObject jsonLine = new JSONObject();
                RawItem rawItem = rawItemService.get(line.getRawItem().getItemId());
                jsonLine.put("itemId", rawItem.getItemId());
                jsonLine.put("itemName", rawItem.getDescription());
                jsonLine.put("qty", line.getQty());
                jsonLine.put("price", line.getPrice());

                //add object to array
                jsonLines.add(jsonLine);
            }

            //add json array to json object
            jsonHeader.put("lines", jsonLines);
        } else {
            //new job
            jsonHeader.put("grnNo", "New");
            jsonHeader.put("date", utilityService.formatDateTime(nowDate));
        }

        return jsonHeader.toJSONString();

    }

    //create  List
    public JSONArray createList(List list) {
        Iterator<Grn> iterator = list.iterator();//get iterator
        JSONArray jSONArray = new JSONArray();
        while (iterator.hasNext()) {//loop iterator
            Grn object = iterator.next();

            JSONObject jsonHeader = new JSONObject();

            jsonHeader.put("grnNo", object.getGrnNo());
            jsonHeader.put("date", utilityService.formatDateTime(object.getDate()));
            jsonHeader.put("supplier", object.getSupplier().getName());
            jsonHeader.put("StoreKeeper", object.getUser().getUsername());

            //calculations
            Double subTotal = (object.getGrossTotal() - object.getDiscount());
            Double taxValue = (subTotal * (object.getVatRate() / 100)) + (subTotal * (object.getNbtRate() / 100));
            Double netTotal = taxValue + subTotal;

            jsonHeader.put("netTotal", utilityService.formatAsCurrency(netTotal));
            jsonHeader.put("payAmount",  utilityService.formatAsCurrency(object.getPayAmount()) );
            jsonHeader.put("dueBalance",  utilityService.formatAsCurrency(netTotal - object.getPayAmount()) );
            jsonHeader.put("status", object.getStatus());

            //add json object to list
            jSONArray.add(jsonHeader);

        }
        return jSONArray;
    }
    
    /*-----------------------------
    Convert list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<Grn> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<Grn> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            Grn grn = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", grn.getGrnNo());
            jSONObject.put("text", grn.getGrnNo() + " - " + utilityService.formatDate(grn.getDate()));

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }
    public JSONArray convertToSelectList(Grn grn) throws Exception {
        //declare varibles
        JSONArray array = new JSONArray();
        Set<GrnLine> grnLines = grn.getGrnLines();

        Iterator<GrnLine> iterator = grnLines.iterator();

        while (iterator.hasNext()) {//loop list
            GrnLine grnLine = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", grnLine.getRawItem().getItemId());
            jSONObject.put("text", rawItemService.get(grnLine.getRawItem().getItemId()).getDescription());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

}
