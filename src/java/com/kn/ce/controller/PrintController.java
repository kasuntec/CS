/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Kasun
 */
@Controller("printController")
public class PrintController {
    
    @RequestMapping(value = "print.htm")
    public String print(Model model,@RequestParam("print_file") String printFile,@RequestParam("id") long id){      
        return "print";
    }
    
    @RequestMapping(value = "print_report.htm",method = RequestMethod.POST)  
    public String print(Model model,@RequestParam("data") String data){
        model.addAttribute("rptData", data);
        return "rpt_print";
    }
    
}
