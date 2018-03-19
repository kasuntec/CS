/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.RawItemCategory;
import com.kn.ce.model.entity.Uom;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("uOMFactory")
public class UOMFactory {

    public String parseToJson(Uom uom) throws Exception {
        //define json object
        JSONObject jsonJob = new JSONObject();
        //set values to json object       

        jsonJob.put("uom", uom.getUom());
     
        return jsonJob.toJSONString();
    }

    public String parseToJson(List<Uom> uoms) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<Uom> iterator = uoms.iterator();
        while (iterator.hasNext()) {
            Uom uom = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
             jsonJob.put("uom", uom.getUom());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

    public String convertToSelectList(List<Uom> uoms) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<Uom> iterator = uoms.iterator();
        while (iterator.hasNext()) {
            Uom uom = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", uom.getUom());
            jsonJob.put("text", uom.getUom());

            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

}
