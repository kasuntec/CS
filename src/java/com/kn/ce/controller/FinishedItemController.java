/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.FinishedItemCatFactory;
import com.kn.ce.factory.FinishedItemFactory;
import com.kn.ce.factory.UOMFactory;
import com.kn.ce.model.entity.FinishedItem;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.FinishedItemCatService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.UOMService;
import com.kn.ce.service.UserService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
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
@RequestMapping("finished_item")
@Controller("finishedItemController")
public class FinishedItemController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private FinishedItemFactory finishedItemFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private FinishedItemCatService finishedItemCatService;
    @Autowired
    private FinishedItemCatFactory finishedItemCatFactory;
    @Autowired
    private UOMService uOMService;
    @Autowired
    private UOMFactory uOMFactory;

    public void setFinishedItemService(FinishedItemService finishedItemService) {
        this.finishedItemService = finishedItemService;
    }

    public void setFinishedItemFactory(FinishedItemFactory finishedItemFactory) {
        this.finishedItemFactory = finishedItemFactory;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*-----------------------------
    End of theDependancy Injection
 -----------------------------*/
 /*-------------------------------------------------------------------------------------------
* Customers List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            try {
                List catList = finishedItemCatService.list();
                List uomList = uOMService.list();

                model.addAttribute("catList", finishedItemCatFactory.convertToSelectList(catList));
                model.addAttribute("uomList", uOMFactory.convertToSelectList(uomList));
            } catch (Exception ex) {
                Logger.getLogger(RawItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "finished_Item_list";
        }
        return "login";

    }

    /*-------------------------------------------------------------------------------------------
* List Customers
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String list() {
        String jsonList = "";
        String status = SystemMessage.FAIL;
        try {
            //get customer list
            List<FinishedItem> list = finishedItemService.list();
            jsonList = finishedItemFactory.parseToJson(list);

        } catch (Exception ex) {
            //error throw   
            Logger.getLogger(FinishedItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(FinishedItem finishedItem) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;
        if (finishedItem != null) {  //check customer is not empty
            try {
                if (finishedItem.getItemId() == 0) {//check is new customer
                    //new customer : create
                    res = finishedItemService.save(finishedItem);

                    //set msg                  
                    msg = "Finished Item " + SystemMessage.SAVE;

                } else {
                    //exstring customer : update
                    res = finishedItemService.update(finishedItem);

                    //set msg                   
                    msg = "Finished Item  " + SystemMessage.UPDATE;

                }

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(FinishedItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //set response
        response.put("id", res);
        response.put("msg", msg);
        response.put("status", status);
        return response.toJSONString();

    }

    /*-------------------------------------------------------------------------------------------
* View Item
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/view.htm", method = RequestMethod.GET)
    @ResponseBody
    public String view(@RequestParam("itemId") long itemId) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        String jsonfinishedItem = "";

        try {
            FinishedItem finishedItem = finishedItemService.get(itemId);
            if (finishedItem != null) {
                jsonfinishedItem = finishedItemFactory.parseToJson(finishedItem);
                status = SystemMessage.OK;
                msg = SystemMessage.OK;

            } else {
                msg = "Finished Item  " + SystemMessage.NOT_FOUND;

            }
        } catch (Exception ex) {
            //error throw           
            response.put("status", status);
            Logger.getLogger(FinishedItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set error msgs response
        response.put("msg", msg);
        response.put("status", status);
        response.put("item", jsonfinishedItem);
        return response.toJSONString();

    }

}
