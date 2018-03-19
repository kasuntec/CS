/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.Customer;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("CustomerFactory")
public class CustomerFactory {
    
    /*-----------------------------
    Convert customer list JSON array
    ------------------------------*/
    public String convertToJSON(List<Customer> list){
        //declare varibles
        JSONArray array=new JSONArray();
        
        
        Iterator<Customer> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Customer customer = iterator.next();
            JSONObject jSONObject=new JSONObject();//create json object
            //put object to json object
  
            jSONObject.put("custId", customer.getCustId());
            jSONObject.put("name", customer.getName());
            jSONObject.put("addressLine1", customer.getAddressLine1());
            jSONObject.put("addressLine2", customer.getAddressLine2());
            jSONObject.put("city", customer.getCity());
            jSONObject.put("postalCode", customer.getPostalCode());
            jSONObject.put("tel1", customer.getTel1());
            jSONObject.put("tel2", customer.getTel2());
            jSONObject.put("fax", customer.getFax());
            jSONObject.put("cperson", customer.getCperson());
            jSONObject.put("mobile", customer.getMobile());
            jSONObject.put("email", customer.getEmail());
            jSONObject.put("remarks", customer.getRemarks());
            
            //add json object to json array
            array.add(jSONObject);
            
        }
        return array.toJSONString();
    }
    
    /*-----------------------------
    Convert customer object to JSON object
    ------------------------------*/
    
    public String convertToJSON(Customer customer){
        //declare varibles
       JSONObject jSONObject=new JSONObject();//create json object
       
            //put object to json object
  
             jSONObject.put("custId", customer.getCustId());
            jSONObject.put("name", customer.getName());
            jSONObject.put("addressLine1", customer.getAddressLine1());
            jSONObject.put("addressLine2", customer.getAddressLine2());
            jSONObject.put("city", customer.getCity());
            jSONObject.put("postalCode", customer.getPostalCode());
            jSONObject.put("tel1", customer.getTel1());
            jSONObject.put("tel2", customer.getTel2());
            jSONObject.put("fax", customer.getFax());
            jSONObject.put("cperson", customer.getCperson());
            jSONObject.put("mobile", customer.getMobile());
            jSONObject.put("email", customer.getEmail());
            jSONObject.put("remarks", customer.getRemarks());
            
          
        return jSONObject.toJSONString();
    }
    
    /*-----------------------------
    Convert customer list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<Customer> list){
        //declare varibles
        JSONArray array=new JSONArray();
        
        
        Iterator<Customer> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Customer customer = iterator.next();
            JSONObject jSONObject=new JSONObject();//create json object
            //put object to json object
  
            jSONObject.put("id", customer.getCustId());
            jSONObject.put("text", customer.getName());
           
            
            //add json object to json array
            array.add(jSONObject);
            
        }
        return array;
    }
    
    
    
}
