/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.Grnfactory;
import com.kn.ce.factory.RawItemFactory;
import com.kn.ce.factory.SupplierFactory;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.GrnService;
import com.kn.ce.service.RawItemService;
import com.kn.ce.service.SupplierService;
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
@RequestMapping("grn")
@Controller("grnController")
public class GrnController {

//Dependancy Injection
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private Grnfactory grnfactory;
    @Autowired
    private UserService userService;
    @Autowired
    private RawItemFactory rawItemFactory;
    @Autowired
    private GrnService grnService;
    @Autowired
    private SupplierFactory supplierFactory;
    @Autowired
    private RawItemService rawItemService;
  

    String ObjectDisplayName = "GRN ";

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "grn_list";
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
                List list = grnService.list(status);
                jsonList = grnfactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(GrnController.class.getName()).log(Level.SEVERE, null, ex);
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
                String itemNameList = rawItemFactory.convertToSelectList(rawItemService.list()); //create item select list
               JSONArray supNameList = supplierFactory.convertToSelectList(supplierService.list());
                Grn grn = null;//set empty null object   
                model.addAttribute("grn", grnfactory.parseToJson(grn));
                model.addAttribute("itemNameList", itemNameList);
                model.addAttribute("supNameList", supNameList);
                model.addAttribute("action", "New");

            } catch (Exception ex) {
                Logger.getLogger(GrnController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return "grn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session, 
            @RequestParam("grn") String jsonHead,
            @RequestParam("lines") String lines,
            @RequestParam("vatValue") double vatValue,
            @RequestParam("nbtValue") double nbtValue,
            @RequestParam("discount") double discount) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (jsonHead != null) {  //check job is not empty
                try {
                    //create  object from  factory class
                    Grn grn = grnfactory.createObject(jsonHead, lines, user, discount, vatValue, nbtValue);
                    if (grn.getGrnNo() == 0) {//check is new job
                        //new customer : create
                        res = grnService.save(grn);

                        //set msg                  
                        msg = ObjectDisplayName + SystemMessage.SAVE;

                    } else {
                        //exstring customer : update
                        res = grnService.update(grn);
                        if (res > 0) {
                            res = grn.getGrnNo();
                        }
                        //set msg                   
                        msg = ObjectDisplayName + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(GrnController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String viewPage(Model model, HttpSession sesion, @RequestParam("grnNo") long grnNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get object from DB
                Grn grn = grnService.get(grnNo);

                if (grn != null) {//check object is exsits
                    //set page Attributes
                    String itemNameList = rawItemFactory.convertToSelectList(rawItemService.list()); //create item select list
                    JSONArray supNameList = supplierFactory.convertToSelectList(supplierService.list());
                    model.addAttribute("grn", grnfactory.parseToJson(grn));
                    model.addAttribute("itemNameList", itemNameList);
                    model.addAttribute("supNameList", supNameList);
                    model.addAttribute("action", PageMood.VIEW);//set page action

                } else {//not exsits
                    model.addAttribute("msg", ObjectDisplayName + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "raw_issue_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(GrnController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "grn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }
    
    //load in AJAX Method
    @RequestMapping(value = "loadDetails.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadDetails(HttpSession sesion, @RequestParam("grnNo") long invNo) {
         JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login
           
            try {
                //get job
                Grn grn = grnService.get(invNo);

                if (grn != null) {//check object is exsits
                    //set page Attributes                   
                    String json = grnfactory.parseToJson(grn);
                    response.put("grn", json);//set  object             

                } else {//not exsits
                    response.put("msg", "Grn " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response.toJSONString();
    }
    //load in AJAX Method
    @RequestMapping(value = "load_grn_items.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadGrnItems(HttpSession sesion, @RequestParam("grnNo") long grnNo) {
         JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login
           
            try {
                //get job
                Grn grn = grnService.get(grnNo);

                if (grn != null) {//check object is exsits
                    //set page Attributes                   
                    JSONArray itemNameList = grnfactory.convertToSelectList(grn);
                    response.put("itemNameList", itemNameList);
                      

                } else {//not exsits
                    response.put("msg", "Grn " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response.toJSONString();
    }
}
