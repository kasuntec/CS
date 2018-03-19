/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.DashBoardFactory;
import com.kn.ce.model.entity.SystemPage;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.EncryptService;
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
@Controller
public class LoginController {

    //services
    @Autowired
    private UserService userService;
    @Autowired
    private DashBoardFactory dashBoardFactory;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*-------------------------------------------------------------------------------------------
     * Show Home Page
     -------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String showStartingPage(HttpSession session,Model model) {

        if (userService.isUserLoggin(session)) {//check user loggin
            User user = (User) session.getAttribute("user");//get user from session

            //Set User Access
            List<SystemPage> accessPageList = userService.listPagesByUserRole(user.getUserRole());
            session.setAttribute("accessPageList", accessPageList);
            try {
                model=dashBoardFactory.createDashBoard(model);
            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            

            //return to index page
            return "index";
        } else {
            //return to loggin page
            return "login";
        }

    }


    /*-------------------------------------------------------------------------------------------
     * Show Login Page
     -------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "login.htm", method = RequestMethod.GET)
    public String showPage(Model model) {

        return "login";
    }


    /*-------------------------------------------------------------------------------------------
     * Show Login Out
     -------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "logout.htm", method = RequestMethod.GET)
    public String logOut(Model model, HttpSession httpSession) {
        //invalidate session       
        httpSession.invalidate();
        //return to login page
        return "login";
    }

    /*-------------------------------------------------------------------------------------------
     * User Login 
     -------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "login.htm", method = RequestMethod.POST)
    public String login(Model model,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session) {

        try {
            //get user
            User user = userService.get(username);

            //encript
            String encryptPass = EncryptService.getEncryptPass(password);

            if (user.getUsername().equals(username) && encryptPass.equals(user.getPassword())) {//check user name and password

                //check is user active
                if (user.isActive()) {
                    //put user to session
                    //login successs
                    //update islogin status
                    user.setLogin(true);
                    user.setActive(true);
                    userService.update(user);
                    session.setAttribute("user", user);
                    return "redirect:index.htm";//redirect to index page

                } else {
                    //user is not active
                    model.addAttribute("errorMsg", "<div  class=\"alert alert-danger\">User login was disabled. Please contact system admin</div>");
                return "login";

                }

            } else {
                //login fail-invaild password
                model.addAttribute("errorMsg", "<div  class=\"alert alert-danger\">Invalid User Name or Password!</div>");
                return "login";
            }

        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //login fail-invaild user
        model.addAttribute("errorMsg", "<div  class=\"alert alert-danger\">Invalid User Name or Password!</div>");
        return "login";
    }

    @RequestMapping(value = "change_password.htm", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("password") String newPassword, HttpSession session) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;

        long res = 0l;

        //get current user
        User user = (User) session.getAttribute("user");

        if (user != null) {  //check customer is not empty

            try {
                String oldEncryptPass = EncryptService.getEncryptPass(oldPassword);//get encript text of old password

                if (user.getPassword().equals(oldEncryptPass)) { //check user password and enter password
                    //match
                    String encryptPass = EncryptService.getEncryptPass(newPassword); //Encrypt new password
                    user.setPassword(encryptPass);//set new password

                    //update user
                    res = userService.update(user);

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                        msg = "User password change successfully. Please login again using new password ";

                        //invalidate sesssion
                        session.invalidate();
                    }

                } else {
                    //not match
                    status = SystemMessage.FAIL;
                    msg = "Old password did not match.Please try again !";
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
     * Show Login Page
     -------------------------------------------------------------------------------------------*/
    @RequestMapping(value = "/master_data.htm", method = RequestMethod.GET)
    public String showMasterPage(Model model) {

        return "master_file";
    }

}
