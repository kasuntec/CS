/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.factory.UserFactory;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.Location;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.entity.UserRole;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.EncryptService;
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
@RequestMapping("user")
@Controller("userController")
public class UserController {

    /*-----------------------------
    Dependancy Injection
-----------------------------*/
    @Autowired
    private UserService userService;
    @Autowired
    private UserFactory userFactory;

    /*-----------------------------
    End of theDependancy Injection
 -----------------------------*/
 /*-------------------------------------------------------------------------------------------
* Customers List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            //set model
            List<UserRole> listUserRole = userService.listUserRole();
            List<Location> listLocation = userService.listLocation();
            model.addAttribute("userRoles", listUserRole);
            model.addAttribute("locations", listLocation);
            return "user_list";
        }
        return userService.redirectToLogginPage();

    }

    /*-------------------------------------------------------------------------------------------
* List Customers
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String list(HttpSession session) {
        String jsonList = "";
        String status = SystemMessage.FAIL;
        if (userService.isUserLoggin(session)) {
            try {
                //get customer list
                List<User> list = userService.list();
                jsonList = userFactory.convertToJSON(list);

            } catch (Exception ex) {
                //error throw   
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
           

        } else {
           return userService.redirectToLogginPage();
        }
         return jsonList;
    }

    /*-------------------------------------------------------------------------------------------
* Save Customer
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    @ResponseBody
    public String create(User user) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;
        String userName = null;
        if (user != null) {  //check customer is not empty

            try {
                //check is exsits
                User getUser = userService.get(user.getUsername());
                //get encript password
                String encryptPass = EncryptService.getEncryptPass(user.getPassword());
                user.setPassword(encryptPass);//set encript passwd

                if (getUser == null) {//check is new user
                    //new user : create

                    userName = userService.save(user);

                    if (userName != null) {
                        res = 1l;
                    }

                    //set msg    
                    msg = SystemMessage.OK;
                    msg = "User " + SystemMessage.SAVE;

                } else {
                    //exstring user : update

                    res = userService.update(user);

                    //set msg  
                    msg = SystemMessage.OK;
                    msg = "User " + SystemMessage.UPDATE;

                }

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String view(@RequestParam("userName") String userName) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        String jsonuser = "";

        try {
            User user = userService.get(userName);
            if (user != null) {
                jsonuser = userFactory.convertToJSON(user);
                status = SystemMessage.OK;
                msg = SystemMessage.OK;

            } else {
                msg = "User " + SystemMessage.NOT_FOUND;

            }
        } catch (Exception ex) {
            //error throw           
            response.put("status", status);
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //set  msgs response
        response.put("msg", msg);
        response.put("status", status);
        response.put("user", jsonuser);
        return response.toJSONString();

    }

}
