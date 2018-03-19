/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.Supplier;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("supplierFactory")
public class SupplierFactory {
    
    /*-----------------------------
    Convert customer list JSON array
    ------------------------------*/
    public String convertToJSON(List<Supplier> list){
        //declare varibles
        JSONArray array=new JSONArray();

     
        Iterator<Supplier> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Supplier supplier = iterator.next();
            JSONObject jSONObject=new JSONObject();//create json object
            //put object to json object
  
            jSONObject.put("supId", supplier.getSupId());
            jSONObject.put("name", supplier.getName());
            jSONObject.put("addressLine1", supplier.getAddress1());
            jSONObject.put("addressLine2", supplier.getAddress2());
            jSONObject.put("city", supplier.getCity());
            jSONObject.put("postalCode", supplier.getPostalCode());
            jSONObject.put("tel1", supplier.getTel1());
            jSONObject.put("tel2", supplier.getTel2());
            jSONObject.put("fax", supplier.getFax());
            jSONObject.put("cperson", supplier.getCperson());
            jSONObject.put("mobile", supplier.getMobile());
            jSONObject.put("email", supplier.getEmail());
            jSONObject.put("remarks", supplier.getRemarks());
            
            //add json object to json array
            array.add(jSONObject);
            
        }
        return array.toJSONString();
    }
    
    /*-----------------------------
    Convert customer object to JSON object
    ------------------------------*/
    
    public String convertToJSON(Supplier supplier){
        //declare varibles
       JSONObject jSONObject=new JSONObject();//create json object
       
            //put object to json object
  
             jSONObject.put("supId", supplier.getSupId());
            jSONObject.put("name", supplier.getName());
            jSONObject.put("address1", supplier.getAddress1());
            jSONObject.put("address2", supplier.getAddress2());
            jSONObject.put("city", supplier.getCity());
            jSONObject.put("postalCode", supplier.getPostalCode());
            jSONObject.put("tel1", supplier.getTel1());
            jSONObject.put("tel2", supplier.getTel2());
            jSONObject.put("fax", supplier.getFax());
            jSONObject.put("cperson", supplier.getCperson());
            jSONObject.put("mobile", supplier.getMobile());
            jSONObject.put("email", supplier.getEmail());
            jSONObject.put("remarks", supplier.getRemarks());
            
          
        return jSONObject.toJSONString();
    }
    
    /*-----------------------------
    Convert customer list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<Supplier> list){
        //declare varibles
        JSONArray array=new JSONArray();
        
        
        Iterator<Supplier> iterator = list.iterator();
        
        while (iterator.hasNext()) {//loop list
            Supplier supplier = iterator.next();
            JSONObject jSONObject=new JSONObject();//create json object
            //put object to json object
  
            jSONObject.put("id", supplier.getSupId());
            jSONObject.put("text", supplier.getName());
           
            
            //add json object to json array
            array.add(jSONObject);
            
        }
        return array;
    }
    
    
    
}
