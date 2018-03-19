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
import com.kn.ce.model.entity.SystemPage;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.entity.UserPageAccess;
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
@RequestMapping("user_access")
@Controller("userAccessController")
public class UserAccessController {

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
* User Access List Page
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String showPage(Model model, HttpSession session) {

        if (userService.isUserLoggin(session)) {
            //set model
            List<UserRole> listUserRole = userService.listUserRole();
            List<SystemPage> pageList = userService.pagesList();
            model.addAttribute("userRoles", listUserRole);
            model.addAttribute("selectPageList", userFactory.convertToSelectPagesList(pageList));
            
            return "user_access";
        }
        return userService.redirectToLogginPage();

    }

    /*-------------------------------------------------------------------------------------------
* List Customers
-------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String list(HttpSession session, @RequestParam("userRole") String userRole) {
        String jsonList = "";
        String status = SystemMessage.FAIL;
        if (userService.isUserLoggin(session)) {
            try {
                //get customer list
                UserRole userRoleObject = userService.getUserRole(userRole);
                List<SystemPage> list = userService.listPagesByUserRole(userRoleObject);
                jsonList = userFactory.convertPageListToJSON(list);

            } catch (Exception ex) {
                //error throw   
                Logger.getLogger(UserAccessController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String create(@RequestParam("userRole") String userRole, @RequestParam("pageList") String pageList, HttpSession session) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;

        if (userService.isUserLoggin(session)) {
            try {
                UserRole userRoleObject = userService.getUserRole(userRole);
                List<UserPageAccess> accessList = userFactory.createList(userRoleObject, pageList);
                res = userService.saveAccessLevel(accessList);

                //set msg
                if (res > 0) {
                    status = SystemMessage.OK;
                    msg=SystemMessage.SAVE;
                }

            } catch (Exception ex) {
                //error throw          
                response.put("status", status);
                Logger.getLogger(UserAccessController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //set response
        response.put("id", res);
        response.put("msg", msg);
        response.put("status", status);
        return response.toJSONString();

    }

  

}
