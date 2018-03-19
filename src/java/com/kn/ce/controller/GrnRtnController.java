/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.GrnRtnfactory;
import com.kn.ce.factory.Grnfactory;
import com.kn.ce.factory.RawItemFactory;
import com.kn.ce.factory.SupplierFactory;
import com.kn.ce.model.entity.Grn;
import com.kn.ce.model.entity.GrnReturn;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.GrnStatus;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.GrnReturnService;
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
@RequestMapping("grn_rtn")
@Controller("grnRtnController")
public class GrnRtnController {

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
    @Autowired
    private GrnReturnService grnReturnService;
    @Autowired
    private GrnRtnfactory grnRtnfactory;
         

    String ObjectDisplayName = "GRN Return";

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "grn_rtn_list";
        } else {
            return userService.redirectToLogginPage();
        }

    }

//    Load Lists
    @RequestMapping(value = "load_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadList(HttpSession httpSession) {
        //define return varible
        JSONArray jsonList = null;
        if (userService.isUserLoggin(httpSession)) {
            try {
                //get list
                List list = grnReturnService.list();
                jsonList = grnRtnfactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(GrnRtnController.class.getName()).log(Level.SEVERE, null, ex);
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
                JSONArray grnNoList = grnfactory.convertToSelectList(grnService.list(GrnStatus.ALL));
                GrnReturn grnReturn = null;//set empty null object   
                model.addAttribute("grnReturn", grnRtnfactory.parseToJson(grnReturn));
                model.addAttribute("itemNameList", itemNameList);
                model.addAttribute("grnNoList", grnNoList);
                model.addAttribute("action", "New");

            } catch (Exception ex) {
                Logger.getLogger(GrnRtnController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return "grn_rtn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session,GrnReturn grnReturn,@RequestParam("lines") String lines,@RequestParam("rtn_date") String date ) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (grnReturn != null) {  //check job is not empty
                try {
                    //create  object from  factory class
                    grnReturn = grnRtnfactory.createObject(grnReturn, lines, user,date);
                    if (grnReturn.getRtnNo()== 0) {//check is new job
                        //new  : create
                        res = grnReturnService.save(grnReturn);

                        //set msg                  
                        msg = ObjectDisplayName + SystemMessage.SAVE;

                    } else {
                        //exstring  : update
                        res = grnReturnService.update(grnReturn);
                        if (res > 0) {
                            res = grnReturn.getRtnNo();
                        }
                        //set msg                   
                        msg = ObjectDisplayName + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(GrnRtnController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String viewPage(Model model, HttpSession sesion, @RequestParam("grnRtnNo") long grnRtnNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get object from DB
                GrnReturn grnReturn = grnReturnService.get(grnRtnNo);

                if (grnReturn != null) {//check object is exsits
                    //set page Attributes
                    String itemNameList = rawItemFactory.convertToSelectList(rawItemService.list()); //create item select list
                    JSONArray grnNoList = grnfactory.convertToSelectList(grnService.list(GrnStatus.ALL));                  
                    model.addAttribute("grnReturn", grnRtnfactory.parseToJson(grnReturn));
                    model.addAttribute("itemNameList", itemNameList);
                    model.addAttribute("grnNoList", grnNoList);
                    model.addAttribute("action", PageMood.VIEW);

                } else {//not exsits
                    model.addAttribute("msg", ObjectDisplayName + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "grn_rtn_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(GrnRtnController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "grn_rtn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

}
