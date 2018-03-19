<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Credit Note</title>
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
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> Credit Note
                            <div class="pull-right" >  
                                <span id="img_load"></span>
                                <a href="new.htm"><button id="btnNew"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i> New</button></a>
                                <button id="btnSave"  type="button" class="btn btn-success"><i class="fa fa-floppy-o " aria-hidden="true"></i></span> Save</button></
                               <a href="list_page.htm"><button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i></span> Close</button></a>                                
                                <button id="btnPrint"  type="button" class="btn btn-warning"><i class="fa fa-print" aria-hidden="true"></i></span> Print</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                Credit Note Information
                            </div>
                            <div class="panel panel-body">
                                <div class="col-md-7">
                                    <form id="frm_crn" name="frm_crn">
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">CRN No</label>
                                                <div class="col-md-3">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="crnNo" id="crnNo" value="New" readonly="true" />
                                                </div> 
                                            </div>  
                                            <br>
                                        </div><!--Row-->
                                       
                                        
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">invoice No</label>
                                                <div class="col-md-7">
                                                    <select class="form-control input-sm" id="invoice" name="invoice.invNo"></select>                                             
                                                </div> 
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Date</label>
                                                <div class="col-md-4">
                                                    <input class="col-md-4 form-control input-sm" type="text" name="crn_date" id="date"  data-validation="required" placeholder="YYYY-MM-dd"/>                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->    
                                        
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Remarks</label>
                                                <div class="col-md-7">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Enter Remaks" />                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                    </form>
                                </div>
                                <div class="col-md-5">
                                    <div class="panel panel-info">
                                        <div class="panel panel-heading">
                                            Totals
                                        </div>
                                        <div class="panel panel-body no-padding-bottom no-padding-top no-padding-left no-padding-right">
                                            <div class="col-md-8">
                                                <h4 class="fg-gray">
                                                    <p>Total</p>                                
                                                </h4>
                                            </div>
                                            <div class="col-md-4">
                                                <h4 class="fg-gray">
                                                    <p class="text-right" id="total">0.00</p>  
                                                </h4>
                                            </div>                                            

                                        </div>
                                    </div>

                                </div>



                            </div>
                        </div><!--main panel div-->

                        <!--Items-->
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                Items
                            </div>
                            <div class="panel panel-body no-padding-top no-padding-bottom">
                                <div class="row">
                                    <div class="col-md-4">Item</div>                                    
                                    <div class="col-md-2 text-right">Qty</div>
                                    <div class="col-md-1 text-right">Rate</div>
                                    <div class="col-md-3 text-right">Amount</div>
                                    <div class="col-md-1"></div>
                                   
                                </div>
                                <form id="frm_line" name="frm_line">
                                    <div class="row">
                                        <div class="col-md-4  no-padding-right">                                       
                                            <select class="form-control input-sm" id="itemId" name="itemId" ></select>                                             
                                        </div>
                                        <div class="col-md-2  no-padding-right">
                                            <input class="col-md-3 form-control input-sm text-right" type="text" name="qty" id="qty" onkeypress="return isNumberKey(event)" data-validation="number"  value="0.00"  />                                            
                                        </div>
                                        <div class="col-md-1  no-padding-right">
                                            <input class="col-md-3 form-control input-sm text-right" type="text" name="price" id="price" onkeypress="return isNumberKey(event)" data-validation="number"  value="0.00"  />                                            
                                        </div>
                                        <div class="col-md-3  no-padding-right">
                                            <label class="col-md-12 text-right"  name="line_amount" id="line_amount" >0.00</label>                                            
                                           
                                        </div>


                                        <div class="col-md-2  no-padding">
                                            <button id="btnAddItem"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i>Add Item</button>
                                        </div>


                                    </div>
                                   
                                </form>


                            </div>

                        </div>
                        <!--Items Table-->
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">

                            </div>
                            <div class="panel panel-body">
                                <table id="tbl_line"></table>
                            </div>


                        </div>


                    </div><!--main row-->
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->        
    </body>

    <script>      
        var invNoList =${invNoList};
        var action = "${action}";
        var crn =${crn};



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
    <script src='<c:url value="/js/actions/crn.js"/>'></script>   




</html>
