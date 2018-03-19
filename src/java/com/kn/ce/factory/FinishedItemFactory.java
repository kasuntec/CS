/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.FinishedItem;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("finishedItemFactory")
public class FinishedItemFactory {

    public String parseToJson(FinishedItem finishedItem) throws Exception {
        //define json object
        JSONObject jsonJob = new JSONObject();
        //set values to json object

        jsonJob.put("itemId", finishedItem.getItemId());
        jsonJob.put("category", finishedItem.getFinishedItemCategory().getCategory());
        jsonJob.put("uom", finishedItem.getUom().getUom());
        jsonJob.put("description", finishedItem.getDescription());
        jsonJob.put("unitPrice", finishedItem.getUnitPrice());
        jsonJob.put("remarks", finishedItem.getRemarks());

        return jsonJob.toJSONString();
    }

    public String parseToJson(List<FinishedItem> finishedItems) throws Exception {
        //define json objects
        JSONArray jSONArray=new JSONArray();
        Iterator<FinishedItem> iterator = finishedItems.iterator();
        while (iterator.hasNext()) {
            FinishedItem finishedItem = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("itemId", finishedItem.getItemId());
            jsonJob.put("category", finishedItem.getFinishedItemCategory().getCategory());
            jsonJob.put("uom", finishedItem.getUom().getUom());
            jsonJob.put("description", finishedItem.getDescription());
            jsonJob.put("unitPrice", finishedItem.getUnitPrice());
            jsonJob.put("remarks", finishedItem.getRemarks());
            
            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }
    
    public JSONArray convertToSelectList(List<FinishedItem> finishedItems) throws Exception {
        //define json objects
        JSONArray jSONArray=new JSONArray();
        Iterator<FinishedItem> iterator = finishedItems.iterator();
        while (iterator.hasNext()) {
            FinishedItem finishedItem = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", finishedItem.getItemId());
            jsonJob.put("text", finishedItem.getDescription());
          
            
            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray;
    }

}
