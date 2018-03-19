/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.Jobfactory;
import com.kn.ce.factory.RawIssueRtnfactory;
import com.kn.ce.factory.RawIssuefactory;
import com.kn.ce.factory.RawItemFactory;
import com.kn.ce.factory.WorkerFactory;
import com.kn.ce.model.entity.RawItemIssue;
import com.kn.ce.model.entity.RawItemIssueRtn;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.JobService;
import com.kn.ce.service.RawItemIssueRtnService;
import com.kn.ce.service.RawItemIssueService;
import com.kn.ce.service.RawItemService;
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
@RequestMapping("raw_item_issue_return")
@Controller("rawIssueRtnController")
public class RawIssueRtnController {

//Dependancy Injection
    @Autowired
    private JobService jobService;
    @Autowired
    private RawIssuefactory rawIssuefactory;
    @Autowired
    private UserService userService;
    @Autowired
    private RawItemFactory rawItemFactory;
    @Autowired
    private RawItemIssueService rawItemIssueService;
    @Autowired
    private Jobfactory jobfactory;
    @Autowired
    private RawItemService rawItemService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private WorkerFactory workerFactory;
    @Autowired
    private RawItemIssueRtnService rawItemIssueRtnService;
    @Autowired
    private RawIssueRtnfactory rawIssueRtnfactory;

    String ObjectDisplayName = "Raw item issue return ";

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "raw_issue_rtn_list";
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
                List list = rawItemIssueRtnService.list();
                jsonList = rawIssueRtnfactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(RawIssueRtnController.class.getName()).log(Level.SEVERE, null, ex);
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
                List issueNoList = rawItemIssueService.list(); //create  select list

                RawItemIssueRtn itemIssueRtn = null;//set empty null object   
                model.addAttribute("itemIssueRtn", rawIssueRtnfactory.parseToJson(itemIssueRtn));
                model.addAttribute("issueNoList", rawIssuefactory.convertToSelectList(issueNoList));
                model.addAttribute("action", PageMood.NEW);

            } catch (Exception ex) {
                Logger.getLogger(RawIssueRtnController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return "raw_issue_rtn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session, RawItemIssueRtn rawIssueRtn, @RequestParam("lines") String lines, @RequestParam("rtn_date") String date) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (rawIssueRtn != null) {  //check is not empty
                try {
                    //create  object from  factory class
                    RawItemIssueRtn itemIssueRtn = rawIssueRtnfactory.createObject(rawIssueRtn, lines, user, date);
                    if (itemIssueRtn.getRtnNo() == 0) {//check is new job
                        //new  : create
                        res = rawItemIssueRtnService.save(itemIssueRtn);

                        //set msg                  
                        msg = "Raw item issue return " + SystemMessage.SAVE;

                    } else {
                        //exstring  : update
                        res = rawItemIssueRtnService.update(itemIssueRtn);
                        if (res > 0) {
                            res = itemIssueRtn.getRtnNo();
                        }
                        //set msg                   
                        msg = ObjectDisplayName + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(RawIssueRtnController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String viewPage(Model model, HttpSession sesion, @RequestParam("rtnNo") long rtnNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get object from DB
                RawItemIssueRtn itemIssueRtn = rawItemIssueRtnService.get(rtnNo);

                if (itemIssueRtn != null) {//check object is exsits
                    //set page Attributes
                    List issueNoList = rawItemIssueService.list(); //create  select list                 
                    model.addAttribute("itemIssueRtn", rawIssueRtnfactory.parseToJson(itemIssueRtn));
                    model.addAttribute("issueNoList", rawIssuefactory.convertToSelectList(issueNoList));
                    model.addAttribute("action", PageMood.VIEW);

                } else {//not exsits
                    model.addAttribute("msg", ObjectDisplayName + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "raw_issue_rtn_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(RawIssueRtnController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "raw_issue_rtn";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }
}
