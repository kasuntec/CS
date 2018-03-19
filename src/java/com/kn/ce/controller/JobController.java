/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.factory.CustomerFactory;
import com.kn.ce.factory.FinishedItemFactory;
import com.kn.ce.factory.Jobfactory;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.system.PageMood;
import com.kn.ce.model.system.SystemMessage;
import com.kn.ce.service.AdvancePaymentService;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.FinishedItemService;
import com.kn.ce.service.JobService;
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
@RequestMapping("job")
@Controller("jobController")
public class JobController {

//Dependancy Injection
    @Autowired
    private JobService jobService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FinishedItemService finishedItemService;
    @Autowired
    private CustomerFactory customerFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private FinishedItemFactory finishedItemFactory;
    @Autowired
    private AdvancePaymentService advancePaymentService;

    @Autowired
    private Jobfactory jobfactory;

    public void setCustomerFactory(CustomerFactory customerFactory) {
        this.customerFactory = customerFactory;
    }

    public void setJobService(JobService jobService) {
        this.jobService = jobService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setFinishedItemFactory(FinishedItemFactory finishedItemFactory) {
        this.finishedItemFactory = finishedItemFactory;
    }

    public void setFinishedItemService(FinishedItemService finishedItemService) {
        this.finishedItemService = finishedItemService;
    }

    public void setJobfactory(Jobfactory jobfactory) {
        this.jobfactory = jobfactory;
    }

    public void setAdvancePaymentService(AdvancePaymentService advancePaymentService) {
        this.advancePaymentService = advancePaymentService;
    }

//End of theDependancy Injection
//     Show list Page
    @RequestMapping(value = "list_page.htm", method = RequestMethod.GET)
    public String listPage(HttpSession httpSession) {
        if (userService.isUserLoggin(httpSession)) {
            return "job_list";
        } else {
            return userService.redirectToLogginPage();
        }

    }

//    Load Lists
    @RequestMapping(value = "load_list.htm", method = RequestMethod.GET)
    @ResponseBody
    public String loadList(HttpSession httpSession, @RequestParam("jobStatus") String status) {
        //define return varible
        JSONArray jsonList = null;
        if (userService.isUserLoggin(httpSession)) {
            try {
                //get list
                List list = jobService.list(status);
                jsonList = jobfactory.createList(list);
            } catch (Exception ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
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
                JSONArray itemNameList = finishedItemFactory.convertToSelectList(finishedItemService.list()); //create item select list
                JSONArray custNameList = customerFactory.convertToSelectList(customerService.list()); //create customer select list
                model.addAttribute("job", jobfactory.parseToJson(new Job()));//set empty null object
                model.addAttribute("advancePayment", jobfactory.parseToJson(new AdvancePayment()));//set empty null object
                model.addAttribute("itemNameList", itemNameList);
                model.addAttribute("custNameList", custNameList);
                model.addAttribute("action", "New");

                return "job";

            } catch (Exception ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

//Save Method
    @ResponseBody
    @RequestMapping(value = "save.htm", method = RequestMethod.POST)
    public String save(HttpSession session, @RequestParam("job") String jsonJob, @RequestParam("lines") String lines, @RequestParam("advance") String advance) {
        //response msg define
        JSONObject response = new JSONObject();
        String msg = SystemMessage.UN_EX_ERROR;
        String status = SystemMessage.FAIL;
        long res = 0l;
        User user = (User) session.getAttribute("user");

        if (userService.isUserLoggin(session)) {//check user is login

            if (jsonJob != null) {  //check job is not empty
                try {
                    //create job object from job factory
                    Job job = jobfactory.createJob(jsonJob, lines, user);
                    AdvancePayment advancePayment = jobfactory.createAdvancePay(advance, job, user);
                    if (job.getJobNo() == 0) {//check is new job
                        //new customer : create
                        res = jobService.save(job, advancePayment);

                        //set msg                  
                        msg = "Job " + SystemMessage.SAVE;

                    } else {
                        //exstring customer : update
                        res = jobService.update(job);
                        if (res > 0) {
                            res = job.getJobNo();
                        }
                        //set msg                   
                        msg = "Job " + SystemMessage.UPDATE;

                    }

                    //set msg
                    if (res > 0) {
                        status = SystemMessage.OK;
                    }

                } catch (Exception ex) {
                    Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
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
    public String viewPage(Model model, HttpSession sesion, @RequestParam("jobNo") long jobNo) {
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get job
                Job job = jobService.get(jobNo);
                //get adavance
                AdvancePayment advancePayment = advancePaymentService.getByJob(jobNo);

                if (job != null || advancePayment != null) {//check object is exsits
                    //set page Attributes
                    JSONArray itemNameList = finishedItemFactory.convertToSelectList(finishedItemService.list()); //create item select list
                    JSONArray custNameList = customerFactory.convertToSelectList(customerService.list()); //create customer select list
                    model.addAttribute("itemNameList", itemNameList);
                    model.addAttribute("custNameList", custNameList);
                    model.addAttribute("job", jobfactory.parseToJson(job));//convert jon to json and add to page
                    model.addAttribute("advancePayment", jobfactory.parseToJson(advancePayment));//convert jon to json and add to page
                    model.addAttribute("action", PageMood.VIEW);//set page action

                    return "job";

                } else {//not exsits
                    model.addAttribute("msg", "Job " + SystemMessage.NOT_FOUND);
                    model.addAttribute("status", SystemMessage.FAIL);
                    return "job_list";
                }

            } catch (Exception ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //return to login page if not logged in
        return userService.redirectToLogginPage();
    }

    //load Job Details
    @ResponseBody
    @RequestMapping(value = "view_details.htm", method = RequestMethod.GET)
    public String viewJob(HttpSession sesion, @RequestParam("jobNo") long jobNo) {
        //define response json
        JSONObject response = new JSONObject();
        if (userService.isUserLoggin(sesion)) {//check user is login

            try {
                //get job
                Job job = jobService.get(jobNo);
                //get adavance
                AdvancePayment advancePayment = advancePaymentService.getByJob(jobNo);

                if (job != null || advancePayment != null) {//check object is exsits
                    //set page Attributes                   
                    response.put("job", jobfactory.parseToJson(job));//convert jon to json and add to response
                    response.put("advancePayment", jobfactory.parseToJson(advancePayment));//convert jon to json and add to page

                } else {//not exsits
                    response.put("msg", "Job " + SystemMessage.NOT_FOUND);
                    response.put("status", SystemMessage.FAIL);

                }

            } catch (Exception ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return response.toJSONString();
    }
}
