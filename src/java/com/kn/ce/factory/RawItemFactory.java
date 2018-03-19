/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.RawItem;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("rawItemFactory")
public class RawItemFactory {

    public String parseToJson(RawItem rawItem) throws Exception {
        //define json object
        JSONObject jsonJob = new JSONObject();
        //set values to json object       

        jsonJob.put("itemId", rawItem.getItemId()); 
        jsonJob.put("rawItemCategory", rawItem.getRawItemCategory().getCategory());
        jsonJob.put("uom", rawItem.getUom().getUom());
        jsonJob.put("description", rawItem.getDescription());
        jsonJob.put("cost", rawItem.getCost());
        jsonJob.put("remarks", rawItem.getRemarks());
        jsonJob.put("reorderLevel", rawItem.getReorderLevel());
        jsonJob.put("stock", rawItem.getStockQty());

        return jsonJob.toJSONString();
    }

    public String parseToJson(List<RawItem> rawItems) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<RawItem> iterator = rawItems.iterator();
        while (iterator.hasNext()) {
            RawItem rawItem = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("itemId", rawItem.getItemId());
            jsonJob.put("RawItemCategory", rawItem.getRawItemCategory().getCategory());
            jsonJob.put("uom", rawItem.getUom().getUom());
            jsonJob.put("description", rawItem.getDescription());
            jsonJob.put("cost", rawItem.getCost());
            jsonJob.put("remarks", rawItem.getRemarks());
            jsonJob.put("reorderLevel", rawItem.getReorderLevel());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

    public String convertToSelectList(List<RawItem> rawItems) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<RawItem> iterator = rawItems.iterator();
        while (iterator.hasNext()) {
            RawItem rawItem = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", rawItem.getItemId());
            jsonJob.put("text", rawItem.getDescription());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

}
