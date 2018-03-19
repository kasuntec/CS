<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GRN Payment</title>
        <!--CSS-->
        <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet"	type="text/css" />
        <link href='<c:url value="/css/metro-colors.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/metro-icons.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery-ui.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet" type="text/css" />      
        <link href='<c:url value="/css/jquery.dataTables.min.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery.notify.css"/>' rel="stylesheet" type="text/css" />
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
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> GRN Payment
                            <div class="pull-right" >                                   
                                <a href="new.htm"><button id="btnNew"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i> New</button></a>
                                <a href="list_page.htm"><button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i></span> Close</button></a>                                
                                <button id="btnPrint "  onclick="print()" type="button" class="btn btn-warning"><i class="fa fa-print" aria-hidden="true"></i>Print</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                GRN Payment 
                            </div>
                            <div class="panel panel-body">
                                <div class="col-md-7">
                                    <jsp:include page="grn_pay_popup.jsp"></jsp:include>
                                    </div>

                                    <div class="col-md-5 bg-grayLighter">
                                        <div class="row bg-info">
                                            <div class="col-md-12">
                                                <h4>GRN Details</h4>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4 text-right">
                                                  <h4>Supplier</h4>
                                            </div>
                                            <div class="col-md-8 fg-green">
                                                <h4 id="supplier">...</h4>
                                            </div>
                                           
                                        </div>
                                        <div class="col-md-4 text-right">      
                                            <h4>Total</h4>
                                            <h4>Balance</h4>
                                        </div>
                                        <div class="col-md-8 fg-green">                                                                                                                              
                                            <h4 id="GRNTotal">0.00</h4>
                                            <h4 id="balance">0.00</h4>

                                        </div>
                                    </div>

                                </div>
                            </div><!--main panel div-->

                        </div><!--main row-->
                    </div>
                </div>
                <!-- /#page-content-wrapper -->

            </div>
            <!-- /#wrapper -->

        </body>

        <script>
            var grnNoList =${grnNoList};
            var action = "${action}";
            var payment =${payment};


    </script>   

    <!--JS-->
    <script src='<c:url value="/js/lib/jquery-1.11.2.min.js"/>'></script>
    <script src='<c:url value="/js/lib/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/js/lib/jquery.dataTables.min.js"/>'></script>
    <script src='<c:url value="/js/lib/select2.min.js"/>'></script>   
    <script src='<c:url value="/js/lib/jquery-ui.js"/>'></script> 
    <script src='<c:url value="/js/lib/jquery.notify.min.js"/>'></script> 
    <script src='<c:url value="/js/actions/formValidator.js"/>'></script> 
    <script src='<c:url value="/js/actions/formJSONFactory.js"/>'></script> 
    <script src='<c:url value="/js/actions/systemNotification.js"/>'></script>  
    <script src='<c:url value="/js/actions/grnPayment.js"/>'></script>   




</html>
