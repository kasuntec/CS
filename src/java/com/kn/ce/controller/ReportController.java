/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.controller;

import com.kn.ce.model.system.report.CustomerOutsReport;
import com.kn.ce.model.system.report.JobReport;
import com.kn.ce.model.system.report.PurchaseReport;
import com.kn.ce.model.system.report.RawItemMovemnt;
import com.kn.ce.model.system.report.RawItemStockReport;
import com.kn.ce.model.system.report.SalesReport;
import com.kn.ce.model.system.report.SupplierOutsReport;
import com.kn.ce.service.CustomerService;
import com.kn.ce.service.RawItemCatService;
import com.kn.ce.service.ReportService;
import com.kn.ce.service.SupplierService;
import com.kn.ce.service.UserService;
import com.kn.ce.service.UtilityService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Kasun
 */
@Controller("reportController")
@RequestMapping("reports")
public class ReportController {

    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private UtilityService utilityService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private RawItemCatService rawItemCatService;

    //PAGE
    @RequestMapping(value = "main.htm", method = RequestMethod.GET)
    public String showMainPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
           
            return "reports";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE
    @RequestMapping(value = "sales.htm", method = RequestMethod.GET)
    public String showSalesReportPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
            try {
                model.addAttribute("customers", customerService.list());
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "rpt_sales";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "sales.htm", method = RequestMethod.POST)
    public String showSalesReport(Model model, HttpSession session,
            @RequestParam("fromDate") String fromStDate,
            @RequestParam("toDate") String toStDate,
            @RequestParam("term") String term,
            @RequestParam("custId") long custId) {
        if (userService.isUserLoggin(session)) {
            List<SalesReport> salesReport = new ArrayList<SalesReport>();
            try {
                //convert string dta to date
                Date fromDate = utilityService.parseDate(fromStDate);
                Date toDate = utilityService.parseDate(toStDate);
                //set report data by data request type

                if (term.equals("All") && custId != 0) {
                    //filter by customer
                    salesReport = reportService.getSalesByDateCustomer(fromDate, toDate, custId+"");
                } else if (!term.equals("All") && custId == 0) {
                    //filter by term
                    salesReport = reportService.getSalesByDateTerm(fromDate, toDate, term);
                } 
                else if (!term.equals("All") && custId != 0) {
                    //filter by term
                    salesReport = reportService.getSalesByDateCustomerTerm(fromDate, toDate, term,custId+"");
                }
                else {
                    //no filter
                    salesReport = reportService.getSalesByDate(fromDate, toDate);
                }

                //set report model to model
                model.addAttribute("salesReport", salesReport);
                model.addAttribute("fromDate", fromStDate);
                model.addAttribute("toDate", toStDate);
                model.addAttribute("customer", custId);
                model.addAttribute("term", term);
                 model.addAttribute("customers", customerService.list());
            } catch (ParseException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_sales";

    }
    //PAGE
    @RequestMapping(value = "purchase.htm", method = RequestMethod.GET)
    public String showPurchaseReportPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
            try {
                model.addAttribute("suppliers", supplierService.list());
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "rpt_purchase";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "purchase.htm", method = RequestMethod.POST)
    public String showPurchaseReport(Model model, HttpSession session,
            @RequestParam("fromDate") String fromStDate,
            @RequestParam("toDate") String toStDate,          
            @RequestParam("suppId") long suppId) {
        if (userService.isUserLoggin(session)) {
            List<PurchaseReport> purchaseReport = new ArrayList<PurchaseReport>();
            try {
                //convert string dta to date
                Date fromDate = utilityService.parseDate(fromStDate);
                Date toDate = utilityService.parseDate(toStDate);
                //set report data by data request type

                if (suppId != 0) {
                    //filter by customer
                    purchaseReport = reportService.getPurchaseByDateSupplier(fromDate, toDate, suppId+"");
                }
                else {
                    //no filter
                    purchaseReport = reportService.getPurchaseByDate(fromDate, toDate);
                }

                //set report model to model
                model.addAttribute("purchaseReport", purchaseReport);
                model.addAttribute("fromDate", fromStDate);
                model.addAttribute("toDate", toStDate);
                model.addAttribute("supplier", suppId);
                model.addAttribute("suppliers", supplierService.list());
            } catch (ParseException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_purchase";

    }
    
    //-------------------------------------JOB---------------------------
    //PAGE
    @RequestMapping(value = "jobs.htm", method = RequestMethod.GET)
    public String showJobReportPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
            try {
                model.addAttribute("customers", customerService.list());
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "rpt_jobs";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "jobs.htm", method = RequestMethod.POST)
    public String showJobReport(Model model, HttpSession session,
            @RequestParam("fromDate") String fromStDate,
            @RequestParam("toDate") String toStDate,
            @RequestParam("status") String status,
            @RequestParam("custId") long custId) {
        if (userService.isUserLoggin(session)) {
            List<JobReport> jobReport = new ArrayList<JobReport>();
            try {
                //convert string dta to date
                Date fromDate = utilityService.parseDate(fromStDate);
                Date toDate = utilityService.parseDate(toStDate);
                //set report data by data request type

                if (status.equals("All") && custId != 0) {
                    //filter by customer
                    jobReport = reportService.getJobByDateCustomer(fromDate, toDate, custId+"");
                } else if (!status.equals("All") && custId == 0) {
                    //filter by term
                    jobReport = reportService.getJobByStatus(fromDate, toDate, status);
                } 
                else if (!status.equals("All") && custId != 0) {
                    //filter by term
                    jobReport = reportService.getJobByStatusCustomer(fromDate, toDate,custId+"",status);
                }
                else {
                    //no filter
                    jobReport = reportService.getJobByDate(fromDate, toDate);
                }

                //set report model to model
                model.addAttribute("jobsReport", jobReport);
                model.addAttribute("fromDate", fromStDate);
                model.addAttribute("toDate", toStDate);
                model.addAttribute("customer", custId);
                model.addAttribute("status", status);
                 model.addAttribute("customers", customerService.list());
            } catch (ParseException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_jobs";

    }
    //-------------------------------------Stock---------------------------
    //PAGE
    @RequestMapping(value = "stock.htm", method = RequestMethod.GET)
    public String showStockReportPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
            try {
                model.addAttribute("categoryList", rawItemCatService.list());
                
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "rpt_stock";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "stock.htm", method = RequestMethod.POST)
    public String showStockReport(Model model, HttpSession session,           
            @RequestParam("category") String category) {
        if (userService.isUserLoggin(session)) {
            List<RawItemStockReport> stockReport = new ArrayList<RawItemStockReport>();
            try {
              
                //set report data by data request type                
                if(!category.trim().equals("All") ){
                    stockReport=reportService.getAllByCatStockReport(category);
                }
                else{
                     stockReport= reportService.getAllStockReport();
                }
                
               

                //set report model to model
                model.addAttribute("stockReport", stockReport);               
                model.addAttribute("category", category);
                model.addAttribute("categoryList", rawItemCatService.list());
            } catch (ParseException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_stock";

    }
    //PAGE
    @RequestMapping(value = "customer_outst.htm", method = RequestMethod.GET)
    public String showCustomerOutstPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
           
            return "rpt_customer_outst";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "customer_outst.htm", method = RequestMethod.POST)
    public String showCustomerOutstReport(Model model, HttpSession session,@RequestParam("date") String stDate) {
        if (userService.isUserLoggin(session)) {
            List<CustomerOutsReport> outsReports = new ArrayList<CustomerOutsReport>();
            try {
              Date date=  utilityService.parseDate(stDate);
             
                outsReports=reportService.getCustomerOutSt(date);
                //set report model to model
                model.addAttribute("customerOutsReport", outsReports);               
                model.addAttribute("stDate", stDate);               
               
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_customer_outst";

    }
    //PAGE
    @RequestMapping(value = "supp_outst.htm", method = RequestMethod.GET)
    public String showSupplierOutstPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
           
            return "rpt_supplier_outst";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "supp_outst.htm", method = RequestMethod.POST)
    public String showSupplierOutstReport(Model model, HttpSession session,@RequestParam("date") String stDate) {
        if (userService.isUserLoggin(session)) {
            List<SupplierOutsReport> outsReports = new ArrayList<SupplierOutsReport>();
            try {
              Date date=  utilityService.parseDate(stDate);
             
                outsReports=reportService.getSupplierOutsReport(date);
                //set report model to model
                model.addAttribute("outsReports", outsReports);               
                model.addAttribute("stDate", stDate);               
               
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_supplier_outst";

    }
    //PAGE
    @RequestMapping(value = "stock_movent.htm", method = RequestMethod.GET)
    public String getStockMovementPage(Model model, HttpSession session) {
        if (userService.isUserLoggin(session)) {
            try {
                model.addAttribute("categoryList", rawItemCatService.list());
                
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "rpt_stock_movent";
        } else {
            return userService.redirectToLogginPage();
        }

    }
    //PAGE with Data   

    @RequestMapping(value = "stock_movent.htm", method = RequestMethod.POST)
    public String getStockMovementReport(Model model, HttpSession session,@RequestParam("fromDate") String stFromDate,@RequestParam("toDate") String stToDate) {
        if (userService.isUserLoggin(session)) {
            List<RawItemMovemnt> stockReports = new ArrayList<RawItemMovemnt>();
            try {
              Date fromDate=  utilityService.parseDate(stFromDate);
              Date toDate=  utilityService.parseDate(stToDate);
             
                stockReports=reportService.getStockMovement(fromDate, toDate);
                //set report model to model
                model.addAttribute("stockReports", stockReports);               
               
            } catch (Exception ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            return userService.redirectToLogginPage();
        }

        return "rpt_stock_movent";

    }

}
