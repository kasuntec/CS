/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.RawItemCatFactory;
import com.kn.ce.factory.RawItemFactory;
import com.kn.ce.factory.UOMFactory;
import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.RawItemCatService;
import com.kn.ce.service.RawItemService;
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
@RequestMapping("raw_item")
@Controller("rawItemController")
public class RawItemController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private RawItemService rawItemService;
    @Autowired
    private RawItemFactory rawItemFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private RawItemCatService rawItemCatService;
    @Autowired
    private RawItemCatFactory rawItemCatFactory;
      @Autowired
    private UOMService uOMService;
      @Autowired
    private UOMFactory uOMFactory;
   
            

    

    /*-----------------------------
    End of theDependancy Injection
 -----------------------------*/
    
     String ObjectDisplayName = "Raw item ";
 /*-------------------------------------------------------------------------------------------
* Customers List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            try {
                List catList = rawItemCatService.list();
                List uomList = uOMService.list();
              
                model.addAttribute("catList", rawItemCatFactory.convertToSelectList(catList));
                model.addAttribute("uomList", uOMFactory.convertToSelectList(uomList));
            } catch (Exception ex) {
                Logger.getLogger(RawItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "raw_item_list";
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
            List<RawItem> list = rawItemService.list();
            jsonList = rawItemFactory.parseToJson(list);

        } catch (Exception ex) {
            //error throw   
            Logger.getLogger(RawItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(RawItem rawItem) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;
        if (rawItem != null) {  //check customer is not empty
            try {
                if (rawItem.getItemId() == 0) {//check is new customer
                    //new customer : create
                    res = rawItemService.save(rawItem);

                    //set msg                  
                    msg = ObjectDisplayName+ SystemMessage.SAVE;

                } else {
                    //exstring customer : update
                    res = rawItemService.update(rawItem);

                    //set msg                   
                    msg = ObjectDisplayName+ SystemMessage.UPDATE;

                }

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(RawItemController.class.getName()).log(Level.SEVERE, null, ex);
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
        String jsonItem = "";

        try {
            RawItem rawItem = rawItemService.get(itemId);
            if (rawItem != null) {
                jsonItem = rawItemFactory.parseToJson(rawItem);
                status = SystemMessage.OK;
                msg = SystemMessage.OK;

            } else {
                msg = ObjectDisplayName+ SystemMessage.NOT_FOUND;

            }
        } catch (Exception ex) {
            //error throw           
            response.put("status", status);
            Logger.getLogger(RawItemController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set error msgs response
        response.put("msg", msg);
        response.put("status", status);
        response.put("item", jsonItem);
        return response.toJSONString();

    }
    
    

}
