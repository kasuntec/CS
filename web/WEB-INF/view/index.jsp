<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Job Processing, Invoicing and Stock Control System</title>
        <!--CSS-->
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="css/metro-colors.css" type="text/css"/>
        <link rel="stylesheet" href="css/metro-icons.css" type="text/css"/>

        <link rel="stylesheet" href="css/font-awesome.css" type="text/css"/>
        <link rel="stylesheet" href="css/side-bar.css" type="text/css"/>

        <!--JS-->
        <script src="js/lib/jquery-1.11.2.min.js"></script>
        <script src="js/lib/bootstrap.min.js"></script>
    </head>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {//check user session is set
            response.sendRedirect("login.htm");
        }
    %>

    <body>

        <jsp:include page="header.jsp"/>
        <div id="wrapper">

            <!-- Sidebar -->
            <div id="sidebar-wrapper">
                <jsp:include page="sidebar.jsp"/>
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Page Content -->
            <div id="page-content-wrapper" >
                <div class="container-fluid">
                    <div class="row" >
                        <div class="col-lg-12">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            <h4>Customer's Jobs - Current Month</h4>
                                        </div>
                                        <div class="panel-body">
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue">
                                                    <b><p >OPEN</p></b>
                                                    <b><p>CANCELED</p></b>
                                                    <b><p>ONGOING</p></b>
                                                    <b><p>FINISHED</p></b>
                                                    <b><p>INVOICE</p></b>
                                                    <b><p>CLOSED</p></b>
                                                </h5>
                                            </div>
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue">
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobOpenTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobCancelTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobStartedTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobFinishedTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobInvoicedTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${jobClosedTotal}"></fmt:formatNumber></p></b>
                                                </h5>
                                            </div>

                                        </div>
                                    </div>

                                </div>
                                <div class="col-md-4">
                                    <div class="panel panel-info">
                                        <div class="panel-heading">
                                            <h4>Invoices - Current Month</h4>
                                        </div>
                                        <div class="panel-body">
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue ">
                                                    <b><p>DRAFT</p></b>
                                                    <b><p>FINALIZED</p></b>
                                                    <b><p>CANCELED</p></b>
                                                    <b><p>UNPAID</p></b>
                                                    <b><p>PARTIAL PAID</p></b>
                                                    <b><p>FULL PAID</p></b>
                                                </h5>
                                            </div>
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue">
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invDraftTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invFinlzTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invCncl}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invUnPTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invParTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${invPaidTotal}"></fmt:formatNumber></p></b>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="col-md-4">
                                    <div class="panel panel-success">
                                        <div class="panel-heading">
                                            <h4>Good Receive Notes - Current Month</h4>
                                        </div>
                                        <div class="panel-body">
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue ">
                                                    <b><p>DRAFT</p></b>
                                                    <b><p>CHECKED</p></b>                                              
                                                    <b><p>UNPAID</p></b>
                                                    <b><p>PARTIAL PAID</p></b>
                                                    <b><p>FULL PAID</p></b>
                                                     <b><p></p></b>
                                                </h5>
                                            </div>
                                            <div class="col-md-6">
                                                <h5 class="fg-darkBlue">
                                                   <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${grnDraftTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${grnCheckdTotal}"></fmt:formatNumber></p></b>                                                    
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${grnUnPTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${grnParTotal}"></fmt:formatNumber></p></b>
                                                    <b><p><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${grnPaidTotal}"></fmt:formatNumber></p></b>
                                                    <b><p></p></b>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="panel bg-cyan fg-white">
                                        <div class="panel-heading">
                                            <h3 class="text-right">Sales Income - Current Month</h3>
                                            <h3 class="text-right"> <fmt:formatNumber type="currency" currencyCode="LKR" value=" ${salesIncomeTotal}"></fmt:formatNumber></h3>
                                        </div>

                                    </div>

                                </div>
                                <div class="col-md-6">
                                    <div class="panel bg-green fg-white ">
                                        <div class="panel-heading">
                                            <h3 class="text-right ">Purchases Expense - Current Month</h3>
                                            <h3 class="text-right "><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${purchaseTotal}"></fmt:formatNumber></h3>
                                        </div>

                                    </div>

                                </div>


                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="panel bg-emerald fg-white">
                                        <div class="panel-heading">
                                            <h3 class="text-right">Total Receivables</h3>
                                            <h3 class="text-right "><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${reciveble}"></fmt:formatNumber></h3>
                                        </div>

                                    </div>

                                </div>
                                <div class="col-md-6">
                                    <div class="panel bg-darkBlue fg-white ">
                                        <div class="panel-heading">
                                            <h3 class="text-right ">Total payable </h3>
                                           <h3 class="text-right "><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${payble}"></fmt:formatNumber></h3>
                                        </div>

                                    </div>

                                </div>


                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="panel bg-red fg-white">
                                        <div class="panel-heading">
                                            <h3 class="text-right">Re-Order Level Reach Items</h3>
                                             <h3 class="text-right"></h3>
                                        </div>
                                       

                                    </div>

                                </div>
                                <div class="col-md-6">
                                    <div class="panel bg-red fg-white">
                                        <div class="panel-heading">
                                            <h3 class="text-right">Total Raw Item Value</h3>
                                            <h3 class="text-right"><fmt:formatNumber type="currency" currencyCode="LKR" value=" ${stockValue}"></fmt:formatNumber></h3>
                                        </div>
                                        <div class="panel-body bg-white">
                                            <a href="reports/stock.htm"><button class="btn btn-success pull-right">View Stock Report</button></a>
                                        </div>

                                    </div>

                                </div>



                            </div>





                        </div>
                    </div>
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->




    </body>



</html>
