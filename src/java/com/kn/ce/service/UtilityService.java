/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;

/**
 * This this utility service class
 * @author user
 */
@Service("UtilityService")
public class UtilityService {
    
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd h:mm:ss");
    
    //format date to String
    public String formatDate(Date date){
        return dateFormat.format(date);
    }
    
     //format date and time  to String
    public String formatDateTime(Date date){
        return dateFormat.format(date);
    }
    
    //format Date String to Date
    public Date parseDate(String date) throws ParseException{
        return dateFormat.parse(date);
    }
    
      //format Date and Time  String to Date
    public Date parseDateTime(String date) throws ParseException{
        return dateFormat.parse(date);
    }
    
    //round values
    public Double round(Double value,int decimals){
        int power = (int) Math.pow(10, decimals);//get 10 to power decimals
        Integer i=new Integer(power);//create Intger object using int value
        return Math.round(value * i.doubleValue()) / i.doubleValue();
    }
    
    //format as currency
    public String formatAsCurrency(Double value){
    
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(value);
        
    }
    
    
    
}
