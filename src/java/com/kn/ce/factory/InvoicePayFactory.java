/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.InvoicePayment;
import com.kn.ce.model.entity.User;
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
@Component("invoicePayFactory")
public class InvoicePayFactory {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UtilityService utilityService;

    /*-----------------------------
    Convert  list JSON array
    ------------------------------*/
    public String convertToJSON(List<InvoicePayment> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<InvoicePayment> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            InvoicePayment payment = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("payId", payment.getPayId());
            jSONObject.put("date", utilityService.formatDate(payment.getDate()));
            jSONObject.put("invoice", payment.getInvoice().getInvNo());
            jSONObject.put("user", payment.getUser().getUsername());
            jSONObject.put("payType", payment.getPayType());
            jSONObject.put("amount", payment.getAmount());

            //add json object to json array
            array.add(jSONObject);

        }
        return array.toJSONString();
    }

    public InvoicePayment createObject(String json, User user) {
        InvoicePayment payment = new InvoicePayment();
        try {

            JSONParser jSONParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jSONParser.parse(json);

            payment.setPayId(Long.parseLong((String) jsonObject.get("payId")));
            payment.setInvoice(invoiceService.get(Long.parseLong((String) jsonObject.get("invoice"))));
            payment.setUser(user);
            payment.setDate(utilityService.parseDate((String) jsonObject.get("date")));
            payment.setPayType(((String) jsonObject.get("payType")));
            payment.setAmount(Double.parseDouble((String) jsonObject.get("amount")));
            payment.setRefNo((String) jsonObject.get("refNo"));

        } catch (ParseException ex) {
            Logger.getLogger(InvoicePayFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InvoicePayFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return payment;
    }

    /*-----------------------------
    Convert  object to JSON object
    ------------------------------*/
    public String convertToJSON(InvoicePayment payment) {
        //declare varibles
        JSONObject jSONObject = new JSONObject();//create json object

        if (payment.getPayId() != null) {
            //put object to json object
            jSONObject.put("payId", payment.getPayId());
            jSONObject.put("invoice", payment.getInvoice().getInvNo());
            jSONObject.put("date", utilityService.formatDate(payment.getDate()));
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
    public JSONArray convertToSelectList(List<InvoicePayment> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<InvoicePayment> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            InvoicePayment payemnt = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", payemnt.getPayId());
            jSONObject.put("text", payemnt.getAmount());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

}
