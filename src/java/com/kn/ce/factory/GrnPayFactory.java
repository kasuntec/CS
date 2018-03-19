/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.GrnPayment;
import com.kn.ce.model.entity.InvoicePayment;
import com.kn.ce.model.entity.User;
import com.kn.ce.service.GrnService;
import com.kn.ce.service.InvoiceService;
import com.kn.ce.service.UtilityService;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("grnPayFactory")
public class GrnPayFactory {

    @Autowired
    private GrnService grnService;
    @Autowired
    private UtilityService utilityService;

    /*-----------------------------
    Convert  list JSON array
    ------------------------------*/
    public String convertToJSON(List<GrnPayment> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<GrnPayment> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            GrnPayment payment = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", payment.getId());
            jSONObject.put("date", utilityService.formatDate(payment.getDate()));
            jSONObject.put("grn", payment.getGrn().getGrnNo());
            jSONObject.put("user", payment.getUser().getUsername());
            jSONObject.put("payType", payment.getPayType());
            jSONObject.put("amount", payment.getAmount());

            //add json object to json array
            array.add(jSONObject);

        }
        return array.toJSONString();
    }

    public GrnPayment createObject(String json, User user) {
        GrnPayment payment = new GrnPayment();
        try {

            JSONParser jSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jSONParser.parse(json);

            payment.setId(Long.parseLong((String) jsonObject.get("id")));
            payment.setGrn(grnService.get(Long.parseLong((String) jsonObject.get("grn"))));
            payment.setUser(user);
            payment.setDate(utilityService.parseDate((String) jsonObject.get("date")));
            payment.setPayType(((String) jsonObject.get("payType")));
            payment.setAmount(Double.parseDouble((String) jsonObject.get("amount")));
            payment.setRefNo((String) jsonObject.get("refNo"));

        } catch (ParseException ex) {
            Logger.getLogger(GrnPayFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GrnPayFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }

    /*-----------------------------
    Convert  object to JSON object
    ------------------------------*/
    public String convertToJSON(GrnPayment payment) {
        //declare varibles
        JSONObject jSONObject = new JSONObject();//create json object

        if (payment.getGrn() != null) {
            //put object to json object
            jSONObject.put("id", payment.getId());
            jSONObject.put("date", utilityService.formatDate(payment.getDate()));
            jSONObject.put("grn", payment.getGrn().getGrnNo());
            jSONObject.put("user", payment.getUser().getUsername());
            jSONObject.put("payType", payment.getPayType());           
            jSONObject.put("refNo", payment.getRefNo());
            jSONObject.put("amount", payment.getAmount());
        }
        else{
             jSONObject.put("payId", 0);
        }

        return jSONObject.toJSONString();
    }

    /*-----------------------------
    Convert  list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<GrnPayment> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<GrnPayment> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            GrnPayment payemnt = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", payemnt.getId());
            jSONObject.put("text", payemnt.getAmount());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

}
