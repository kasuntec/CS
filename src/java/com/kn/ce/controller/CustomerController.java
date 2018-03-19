/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.CustomerService;
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
@RequestMapping("customer")
@Controller("customerController")
public class CustomerController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private UserService userService;

    /*-----------------------------
    End of theDependancy Injection
 -----------------------------*/
 /*-------------------------------------------------------------------------------------------
* Customers List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            return "customer_list";
        }
        return userService.redirectToLogginPage();

    }

    /*-------------------------------------------------------------------------------------------
* List Customers
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String list(HttpSession httpSession) {
        String jsonList = "";
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        try {
            if (userService.isUserLoggin(httpSession)) {
                //get customer list
                List<Customer> list = customerService.list();
                jsonList = customerFactory.convertToJSON(list);
            } else {
                //login expire
                msg = SystemMessage.EXPIRE;
                status = SystemMessage.FAIL;
                response.put("msg", msg);
                response.put("status", status);
                return response.toJSONString();
            }

        } catch (Exception ex) {
            //error throw   
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(Customer customer) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;
        if (customer != null) {  //check customer is not empty
            try {
                if (customer.getCustId() == 0) {//check is new customer
                    //new customer : create
                    res = customerService.save(customer);

                    //set msg    
                    msg = SystemMessage.OK;
                    msg = "Customer " + SystemMessage.SAVE;

                } else {
                    //exstring customer : update

                    res = customerService.update(customer);

                    //set msg  
                    msg = SystemMessage.OK;
                    msg = "Customer " + SystemMessage.UPDATE;

                }

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //set response
        response.put("id", res);
        response.put("msg", msg);
        response.put("status", status);
        return response.toJSONString();

    }

    /*-------------------------------------------------------------------------------------------
* View Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "view.htm", method = RequestMethod.GET)
    @ResponseBody
    public String view(@RequestParam("custId") long custId) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        String jsonCustomer = "";

        try {
            Customer customer = customerService.get(custId);
            if (customer != null) {
                jsonCustomer = customerFactory.convertToJSON(customer);
                status = SystemMessage.OK;

            } else {
                msg = "Customer " + SystemMessage.NOT_FOUND;

            }
        } catch (Exception ex) {
            //error throw           
            response.put("status", status);
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set error msgs response
        response.put("msg", msg);
        response.put("status", status);
        response.put("customer", jsonCustomer);
        return response.toJSONString();

    }

    /*-------------------------------------------------------------------------------------------
* load Customer Name List
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "name_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadCustNameList() {
        JSONArray custNameList = null;
        try {
            custNameList = customerFactory.convertToSelectList(customerService.list());

        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return custNameList.toJSONString();
    }

}
