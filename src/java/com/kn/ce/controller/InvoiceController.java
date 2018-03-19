/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.factory.FinishedItemFactory;
import com.kn.ce.factory.InvoiceFactory;
import com.kn.ce.factory.Jobfactory;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.AdvancePaymentService;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.InvoiceService;
import com.kn.ce.service.JobService;
import com.kn.ce.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("invoice")
@Controller("invoiceController")
public class InvoiceController {

//Dependancy Injection
    @Autowired
    private JobService jobService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private FinishedItemFactory finishedItemFactory;
    @Autowired
    private AdvancePaymentService advancePaymentService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private Jobfactory jobfactory;
    @Autowired
    private InvoiceFactory invoiceFactory;

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "invoice_list";
        } else {
            return userService.redirectToLogginPage();
        }

    }

//    Load Lists
    @RequestMapping(value = "load_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadList(HttpSession httpSession, @RequestParam("status") String status) {
        //define return varible
        JSONArray jsonList = null;
        if (userService.isUserLoggin(httpSession)) {
            try {
                //get list
                List list = invoiceService.list(status);
                jsonList = invoiceFactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return jsonList.toJSONString();

        } else {
            return userService.redirectToLogginPage();
        }

    }

//New Page
    @RequestMapping(value = "new.htm", method = RequestMethod.GET)
    public String newPage(Model model, HttpSession sesion) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //set page Attributes
                JSONArray itemNameList = finishedItemFactory.convertToSelectList(finishedItemService.list()); //create item select list
                JSONArray jobList = jobfactory.convertToSelectList(jobService.list(JobStatus.FINISHED)); //create customer select list
                model.addAttribute("invoice", invoiceFactory.parseToJson(new Invoice()));//set empty null object             
                model.addAttribute("itemNameList", itemNameList);
                model.addAttribute("jobList", jobList);
                model.addAttribute("action", "New");

                return "invoice";

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session, @RequestParam("invoice") String jsonObject, @RequestParam("lines") String lines, @RequestParam("discount") double discount) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (jsonObject != null) {  //check job is not empty
                try {
                    //create bject from factory class                   
                    Invoice invoice = invoiceFactory.createObject(jsonObject, user, discount);
                    if (invoice.getInvNo() == 0) {//check is new job
                        //new customer : create
                        res = invoiceService.save(invoice);

                        //set msg                  
                        msg = "Invoice " + SystemMessage.SAVE;

                    } else {
                        //exstring customer : update
                        res = invoiceService.update(invoice);
                        if (res > 0) {
                            res = invoice.getInvNo();
                        }
                        //set msg                   
                        msg = "Invoice " + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        //set response
        response.put("id", res);
        response.put("msg", msg);
        response.put("status", status);
        return response.toJSONString();
    }

//View Method
    @RequestMapping(value = "view.htm", method = RequestMethod.GET)
    public String viewPage(Model model, HttpSession sesion, @RequestParam("invNo") long invNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get job
                Invoice invoice = invoiceService.get(invNo);

                if (invoice != null) {//check object is exsits
                    //set page Attributes
                    JSONArray itemNameList = finishedItemFactory.convertToSelectList(finishedItemService.list()); //create item select list
                    JSONArray jobList = jobfactory.convertToSelectList(jobService.list(JobStatus.INVOICED)); //create customer select list
                    String invoiceJSOn = invoiceFactory.parseToJson(invoice);
                    model.addAttribute("invoice", invoiceJSOn);//set  object             
                    model.addAttribute("itemNameList", itemNameList);
                    model.addAttribute("jobList", jobList);
                    model.addAttribute("action", PageMood.VIEW);//set page action

                } else {//not exsits
                    model.addAttribute("msg", "Invoice " + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "invoice_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "invoice";

        } else {
            //return to login page if not logged in
            return userService.redirectToLogginPage();
        }

    }

    //load in AJAX Method
    @RequestMapping(value = "loadDetails.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadDetails(HttpSession sesion, @RequestParam("invNo") long invNo) {
         JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login
           
            try {
                //get job
                Invoice invoice = invoiceService.get(invNo);

                if (invoice != null) {//check object is exsits
                    //set page Attributes                   
                    String invoiceJSOn = invoiceFactory.parseToJson(invoice);
                    response.put("invoice", invoiceJSOn);//set  object             

                } else {//not exsits
                    response.put("msg", "Invoice " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response.toJSONString();
    }
    
    //load invoice items
    @RequestMapping(value = "load_invoice_items.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadGrnItems(HttpSession sesion, @RequestParam("invNo") long invNo) {
         JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login
           
            try {
                //get job
                Invoice invoice = invoiceService.get(invNo);

                if (invoice != null) {//check object is exsits
                    //set page Attributes                   
                    JSONArray itemNameList = invoiceFactory.convertToSelectList(invoice);
                    response.put("itemNameList", itemNameList);
                      

                } else {//not exsits
                    response.put("msg", "Invoice " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response.toJSONString();
    }
}
