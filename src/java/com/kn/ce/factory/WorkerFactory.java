/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.Worker;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("workerFactory")
public class WorkerFactory {

    public String parseToJson(Worker worker) throws Exception {
        //define json object
        JSONObject jsonObject = new JSONObject();
        //set values to json object       


        jsonObject.put("id", worker.getId());
        jsonObject.put("etfNo", worker.getEtfNo());
        jsonObject.put("fullName", worker.getFullName());
        jsonObject.put("designation", worker.getDesignation());
        jsonObject.put("mobile", worker.getMobile());
       

        return jsonObject.toJSONString();
    }

    public String parseToJson(List<Worker> workers) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<Worker> iterator = workers.iterator();
        while (iterator.hasNext()) {
            Worker worker = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", worker.getId());
            jsonJob.put("etfNo", worker.getId());
            jsonJob.put("fullName", worker.getFullName());
            jsonJob.put("designation", worker.getDesignation());
            jsonJob.put("mobile", worker.getMobile());
          
            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

    public String convertToSelectList(List<Worker> workers) throws Exception {
        //define json objects
        JSONArray jSONArray = new JSONArray();
        Iterator<Worker> iterator = workers.iterator();
        while (iterator.hasNext()) {
            Worker worker = iterator.next();

            JSONObject jsonJob = new JSONObject();

            //set values to json object
            jsonJob.put("id", worker.getId());
            jsonJob.put("text", worker.getFullName());
            
            //add josn object to jSON Array
            jSONArray.add(jsonJob);
        }

        return jSONArray.toJSONString();
    }

}
