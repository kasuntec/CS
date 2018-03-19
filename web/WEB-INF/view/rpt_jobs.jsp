<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jobs Report</title>
        <!--CSS-->
        <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet"	type="text/css" />
        <link href='<c:url value="/css/metro-colors.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/metro-icons.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery-ui.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery.dataTables.min.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/font-awesome.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/side-bar.css"/>' rel="stylesheet" type="text/css" />


    </head>

    <body>
        <jsp:include page="header.jsp"/>
        <div id="wrapper">

            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <jsp:include page="sidebar.jsp"/>
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> Jobs Report
                            <div class="pull-right" >
                               <button id="btnPrint"  type="button" class="btn btn-warning"><span><i class="fa fa-print" aria-hidden="true"></i></span> Print</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-primary" id="list">
                            <div class="panel panel-heading">

                            </div>
                            <div class="panel panel-body" id="print">
                                <form action="jobs.htm" method="post">
                                    <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-2 text-right ">From Date</label>
                                        <div class="col-md-2">
                                            <div class="col-md-10 pull-left no-padding-left">
                                                <input type="text" id="fromDate" name="fromDate" value="${fromDate}"/>                                           
                                                
                                            </div>
                                        </div>
                                        <label class="col-md-2 text-right ">To Date</label>
                                        <div class="col-md-2">
                                            <div class="col-md-10 pull-left no-padding-left">
                                                <input type="text" id="toDate" name="toDate" value="${toDate}"/>                                           
                                                
                                            </div>
                                        </div>
                                        <button type="submit" class="btn btn-success">View Report</button>
                                    </div>
                                    <br>
                                </div><!--Row-->
                                    <div class="row">
                                    <div class="form-group">                                        
                                        <label class="col-md-2 text-right ">Customer</label>
                                        <div class="col-md-3">
                                            <div class="col-md-10 pull-left no-padding-left">
                                                <select id="custId" name="custId" class="form-control">
                                                    <c:forEach var="customer" items="${customers}">
                                                        <option value="${customer.custId}">${customer.name}</option>
                                                    </c:forEach>
                                                    <option value="0">All</option>
                                                </select>     
                                                
                                                
                                            </div>
                                        </div>
                                        <label class="col-md-2 text-right ">Status</label>
                                        <div class="col-md-2">
                                            <div class="col-md-10 pull-left no-padding-left">
                                                <select id="status" name="status" class="form-control">
                                                    <option>All</option>
                                                    <option>Open</option>
                                                    <option>Canceled</option>
                                                    <option>Started</option>
                                                    <option>Finished</option>
                                                    <option>Invoiced</option>
                                                    <option>Closed</option>
                                                </select>                                      
                                                
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                </div><!--Row-->
                                    
                                </form>
                                
                                <hr>
                                <table id="tbl_rpt" class="table table-responsive">
                                  
                                     
                                    <thead>
                                        <tr>
                                            <th>Job No</th>
                                            <th>Date</th>
                                            <th>Delivery Date</th>
                                            <th>Customer</th>
                                            <th>Status</th>
                                                                                                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="report" items="${jobsReport}">
                                             <tr>
                                            <td>${report.jobNo}</td>
                                            <td>${report.date}</td>
                                            <td>${report.delivaryDate}</td>
                                            <td>${report.customer}</td>
                                            <td>${report.status}</td>                                           
                                           
                                           
                                            
                                        </tr>
                                            
                                        </c:forEach>
                                        
                                    </tbody>
                                   
                                </table>
                            </div>
                        </div><!--main panel div-->

                    </div><!--main row-->
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->

    </body>

    <!--JS-->
    <script src='<c:url value="/js/lib/jquery-1.11.2.min.js"/>'></script>
    <script src='<c:url value="/js/lib/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/js/lib/jquery-ui.js"/>'></script>
    <script src='<c:url value="/js/lib/jquery.dataTables.min.js"/>'></script>
    <script src='<c:url value="/js/actions/formValidator.js"/>'></script>
    <script src='<c:url value="/js/actions/systemNotification.js"/>'></script>
    <script src='<c:url value="/js/actions/reports.js"/>'></script>
    
   
    <script>
         $('#fromDate').datepicker({dateFormat: "yy-mm-dd"});
         $('#toDate').datepicker({dateFormat: "yy-mm-dd"});
         $('#custId').val("${customer}");
         $('#status').val("${status}");
    </script>


</html>
