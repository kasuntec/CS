/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.GrnPayFactory;
import com.kn.ce.factory.Grnfactory;
import com.kn.ce.factory.InvoiceFactory;
import com.kn.ce.factory.InvoicePayFactory;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnPayment;
import com.kn.ce.model.entity.Invoice;
import com.kn.ce.model.entity.InvoicePayment;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.model.system.InvoiceStatus;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.GrnPaymentService;
import com.kn.ce.service.GrnService;
import com.kn.ce.service.InvoicePaymentService;
import com.kn.ce.service.InvoiceService;
import com.kn.ce.service.UserService;
import com.kn.ce.service.UtilityService;
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
@RequestMapping("grn_payment")
@Controller("grnPaymentController")
public class GrnPaymentController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private GrnPaymentService grnPaymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private GrnService grnService;
    @Autowired
    private Grnfactory grnfactory;
    @Autowired
    private GrnPayFactory grnPayFactory;

    /*-----------------------------
    End of theDependancy Injection
 -----------------------------*/
 /*-------------------------------------------------------------------------------------------
* Invoice Payment List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            return "grn_payment_list";
        }
        return "login";

    }

    /*-------------------------------------------------------------------------------------------
* Invoice Payment add Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "new.htm", method = RequestMethod.GET)
    public String newPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            try {
                List<Grn> list = grnService.list(GrnStatus.PARTIAL, InvoiceStatus.UNPAID);
                JSONArray invSelectList = grnfactory.convertToSelectList(list);
                model.addAttribute("grnNoList", invSelectList);
                model.addAttribute("action", PageMood.NEW);
                model.addAttribute("payment", grnPayFactory.convertToJSON(new GrnPayment()));

            } catch (Exception ex) {
                Logger.getLogger(GrnPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "grn_payment";
        }
        return "login";

    }

    /*-------------------------------------------------------------------------------------------
* List Invoice Payment
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "load_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String list() {
        String jsonList = "";
        String status = SystemMessage.FAIL;
        try {
            //get customer list
            List<GrnPayment> list = grnPaymentService.list();
            jsonList = grnPayFactory.convertToJSON(list);

        } catch (Exception ex) {
            //error throw   
            Logger.getLogger(GrnPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Invoice Payment
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestParam("payment") String jsonObject, HttpSession session) {
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (jsonObject != null) {  //check  is not empty
                try {
                    //create object from factory class
                    GrnPayment payment = grnPayFactory.createObject(jsonObject, user);
                    if (payment.getId() == 0) {//check is new job
                        //new customer : create
                        res = grnPaymentService.save(payment);

                        //set msg                  
                        msg = "Payment " + SystemMessage.SAVE;

                    } else {
                        //exstring customer : update
                        res = grnPaymentService.update(payment);
                        if (res > 0) {
                            res = payment.getId();
                        }
                        //set msg                   
                        msg = "Payment " + SystemMessage.UPDATE;

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

    /*-------------------------------------------------------------------------------------------
* View Invoice Payment
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "view.htm", method = RequestMethod.GET)
    public String view(@RequestParam("payId") long payId, Model model, HttpSession session) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;

        if (userService.isUserLoggin(session)) {
            try {
                GrnPayment payment = grnPaymentService.get(payId);//get 

                List<Grn> list = grnService.list(InvoiceStatus.PARTIAL, InvoiceStatus.UNPAID);
                JSONArray invSelectList = grnfactory.convertToSelectList(list);
                model.addAttribute("grnNoList", invSelectList);
                model.addAttribute("action", PageMood.VIEW);
                model.addAttribute("payment", grnPayFactory.convertToJSON(payment));

            } catch (Exception ex) {
                Logger.getLogger(GrnPaymentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "grn_payment";
        }
        return "login";

    }

}
