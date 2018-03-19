/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.factory.FinishedItemFactory;
import com.kn.ce.factory.Jobfactory;
import com.kn.ce.factory.RawIssuefactory;
import com.kn.ce.factory.RawItemFactory;
import com.kn.ce.factory.WorkerFactory;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.RawItemIssue;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.AdvancePaymentService;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
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
@RequestMapping("raw_issue")
@Controller("rawIssueController")
public class RawIssueController {

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

    String ObjectDisplayName = "Raw item issue ";

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "raw_issue_list";
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
                List list = rawItemIssueService.list();
                jsonList = rawIssuefactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(RawIssueController.class.getName()).log(Level.SEVERE, null, ex);
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
                JSONArray jobNoList = jobfactory.convertToSelectList(jobService.list(JobStatus.OPEN, JobStatus.STARED)); //create job select list
                String workerList = workerFactory.convertToSelectList(workerService.list());

                RawItemIssue rawItemIssue = null;//set empty null object   
                model.addAttribute("rawItemIssue", rawIssuefactory.parseToJson(rawItemIssue));
                model.addAttribute("itemNameList", itemNameList);
                model.addAttribute("jobNoList", jobNoList);
                model.addAttribute("workerList", workerList);
                model.addAttribute("action", "New");

            } catch (Exception ex) {
                Logger.getLogger(RawIssueController.class.getName()).log(Level.SEVERE, null, ex);

            }
            return "raw_issue";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session, @RequestParam("rawIssue") String jsonHead, @RequestParam("lines") String lines) {
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
                    RawItemIssue itemIssue = rawIssuefactory.createObject(jsonHead, lines, user);
                    if (itemIssue.getIssueNo() == 0) {//check is new job
                        //new customer : create
                        res = rawItemIssueService.save(itemIssue);

                        //set msg                  
                        msg = "Raw item issue " + SystemMessage.SAVE;

                    } else {
                        //exstring customer : update
                        res = rawItemIssueService.update(itemIssue);
                        if (res > 0) {
                            res = itemIssue.getIssueNo();
                        }
                        //set msg                   
                        msg = ObjectDisplayName + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(RawIssueController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String viewPage(Model model, HttpSession sesion, @RequestParam("issueNo") long issueNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get object from DB
                RawItemIssue issue = rawItemIssueService.get(issueNo);

                if (issue != null) {//check object is exsits
                    //set page Attributes
                    String itemNameList = rawItemFactory.convertToSelectList(rawItemService.list()); //create item select list
                    JSONArray jobNoList = jobfactory.convertToSelectList(jobService.list(JobStatus.OPEN, JobStatus.STARED)); //create job select list
                    String workerList = workerFactory.convertToSelectList(workerService.list());
                    model.addAttribute("rawItemIssue", rawIssuefactory.parseToJson(issue));//set empty null object               
                    model.addAttribute("itemNameList", itemNameList);
                    model.addAttribute("jobNoList", jobNoList);
                    model.addAttribute("workerList", workerList);
                    model.addAttribute("action", PageMood.VIEW);//set page action

                } else {//not exsits
                    model.addAttribute("msg", ObjectDisplayName + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "raw_issue_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(RawIssueController.class.getName()).log(Level.SEVERE, null, ex);
            }

            return "raw_issue";
        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }
    
    //load in AJAX Method
    @RequestMapping(value = "load_issue_items.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadGrnItems(HttpSession sesion, @RequestParam("issueNo") long issueNo) {
         JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login
           
            try {
                //get job
                RawItemIssue issue = rawItemIssueService.get(issueNo);

                if (issue != null) {//check object is exsits
                    //set page Attributes                   
                    JSONArray itemNameList = rawIssuefactory.convertToSelectList(issue);
                    response.put("itemNameList", itemNameList);
                      

                } else {//not exsits
                    response.put("msg", "Issue " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return response.toJSONString();
    }
}
