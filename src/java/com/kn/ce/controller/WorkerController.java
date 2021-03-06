/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.factory.SupplierFactory;
import com.kn.ce.factory.WorkerFactory;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.Supplier;
import com.kn.ce.model.entity.Worker;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.SupplierService;
import com.kn.ce.service.UserService;
import com.kn.ce.service.WorkerService;
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
@RequestMapping("worker")
@Controller("workerController")
public class WorkerController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private WorkerService  workerService;
    @Autowired
    private WorkerFactory workerFactory;
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
            return "worker_list";
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
                //get worker list
                List<Worker> list = workerService.list();
                jsonList = workerFactory.parseToJson(list);
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
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(Worker worker) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;
        if (worker != null) {  //check worker is not empty
            try {
                if (worker.getId() == 0) {//check is new worker
                    //new worker : create
                    res = workerService.save(worker);

                    //set msg    
                    msg = SystemMessage.OK;
                    msg = "Worker " + SystemMessage.SAVE;

                } else {
                    //exstring worker : update

                    res = workerService.update(worker);

                    //set msg  
                    msg = SystemMessage.OK;
                    msg = "Worker " + SystemMessage.UPDATE;

                }

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                    
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String view(@RequestParam("id") long id) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        String jsonWorker = "";

        try {
            Worker worker = workerService.get(id);
            if (worker != null) {
                jsonWorker = workerFactory.parseToJson(worker);
                status = SystemMessage.OK;

            } else {
                msg = "Supplier " + SystemMessage.NOT_FOUND;

            }
        } catch (Exception ex) {
            //error throw           
            response.put("status", status);
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set error msgs response
        response.put("msg", msg);
        response.put("status", status);
        response.put("worker", jsonWorker);
        return response.toJSONString();

    }

    /*-------------------------------------------------------------------------------------------
* load Customer Name List
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "name_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadCustNameList() {
        String workerNameList = null;
        try {
            workerNameList = workerFactory.convertToSelectList(workerService.list());

        } catch (Exception ex) {
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return workerNameList;
    }

}
