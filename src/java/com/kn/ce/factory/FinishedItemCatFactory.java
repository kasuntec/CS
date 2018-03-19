/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.entity.FinishedItemCategory;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemCategory;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("finishedItemCatFactory")
public class FinishedItemCatFactory {

    public String parseToJson(FinishedItemCategory itemCategory) throws Exception {
        //define json object
        JSONObject jsonJob = new JSONObject();
        //set values to json object       

        jsonJob.put("category", itemCategory.getCategory());
     
        return jsonJob.toJSONString();
    }

    public String parseToJson(List<FinishedItemCategory> itemCategorys) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<FinishedItemCategory> iterator = itemCategorys.iterator();
        while (iterator.hasNext()) {
            FinishedItemCategory itemCategory = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("category", itemCategory.getCategory());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

    public String convertToSelectList(List<FinishedItemCategory> itemCategorys) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<FinishedItemCategory> iterator = itemCategorys.iterator();
        while (iterator.hasNext()) {
            FinishedItemCategory category = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", category.getCategory());
            jsonJob.put("text", category.getCategory());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

}
