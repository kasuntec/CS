<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invoice</title>
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
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> Invoice
                            <div class="pull-right" >                                   
                                <a href="new.htm"><button id="btnNew"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i> New</button></a>
                                <button id="btnSave"  type="button" class="btn btn-success"><i class="fa fa-floppy-o " aria-hidden="true"></i></span> Save</button>
                               <a href="list_page.htm"><button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i></span> Close</button></a>                                
                                <button id="btnPrint"  type="button" class="btn btn-warning"><i class="fa fa-print" aria-hidden="true"></i></span> Print</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                Invoice Information
                            </div>
                            <div class="panel panel-body">
                                <div class="col-md-12">
                                    <form id="frm_invoice" name="frm_invoice">
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Invoice No</label>
                                                <div class="col-md-2">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="invNo" id="invNo" value="New" readonly="true" />

                                                </div>
                                                <label class="col-md-3 text-right">Status</label>
                                                <div class="col-md-4">                                               
                                                    <select name="status" id="status" class="form-control input-sm">
                                                        <option value="Draft">Draft</option>
                                                        <option value="Finalized">Finalized</option>                                                     
                                                        <option value="Canceled">Canceled</option>
                                                       
                                                    </select>

                                                </div>
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Terms</label>
                                                <div class="col-md-3">
                                                    <select class="form-control input-sm" id="type" name="type">
                                                        <option>Cash</option>
                                                        <option>Credit</option>
                                                    </select>  
                                                </div> 
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Job No</label>
                                                <div class="col-md-5">
                                                    <select class="form-control input-sm" id="job" name="job"></select>  <span id="img_load"></span>                                             
                                                </div>  
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Customer</label>
                                                <div class="col-md-5">
                                                    <label class="col-md-12" id="customer">...</label>    
                                                </div> 
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Date</label>
                                                <div class="col-md-3">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="date" id="date"  data-validation="required" placeholder="YYYY-MM-dd"/>                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Remarks</label>
                                                <div class="col-md-6">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Enter Invoice Remaks" />                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                    </form>
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
                                    <div class="col-md-2">Unit Price</div>                                   
                                    <div class="col-md-1">Width</div>
                                    <div class="col-md-1">Height</div>
                                    <div class="col-md-1">Qty</div>

                                </div>
                                <form id="frm_line" name="frm_line">
                                    <div class="row">
                                        <div class="col-md-4  no-padding-right">                                       
                                            <select class="form-control input-sm" id="itemId" name="itemId" data-validation="required" ></select>                                             
                                        </div>
                                        <div class="col-md-2  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="unitPrice" id="unitPrice" onkeypress="return isNumberKey(event)" data-validation="required" value="0.00"  />                                            
                                        </div>
                                        <div class="col-md-1  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="width" id="width" onkeypress="return isNumberKey(event)" data-validation="required" value="0.00"/>  
                                        </div>
                                        <div class="col-md-1  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="height" id="height" onkeypress="return isNumberKey(event)" data-validation="required" value="0.00"/>                                           
                                        </div>

                                        <div class="col-md-1  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="qty" id="qty" onkeypress="return isNumberKey(event)" data-validation="required" value="0"/>                                           
                                        </div>


                                    </div>
                                </form>

                                <div class="row">
                                    <br>
                                    <div class="col-md-3">  
                                        <button id="btnAddItem"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i>Add Item</button>
                                    </div>
                                    <div class="col-md-3 pull-right">  
                                        <h4>Amount <p id="line_amount" class="pull-right fg-darkCyan">0.00</p></h4>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--Items Table-->
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">

                            </div>
                            <div class="panel panel-body">
                                <div class="row">
                                    <table id="tbl_line"></table>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-md-5 pull-right">
                                        <div class="col-md-8">
                                            <h4 class="fg-gray">
                                                <p>Gross Total</p>    
                                                <p>Discount Value</p> 
                                                <p><b>Net Total</b></p> 
                                                <p>Advance Payment</p>   
                                                <p>Paid</p>                                                   
                                                <p><b>Balance</b></p>                                                   
                                            </h4>
                                        </div>
                                        <div class="col-md-4">
                                            <h4 class="fg-gray">
                                                <p class="text-right" id="grossTotal">0.00</p>                                                 

                                                <div class="row">
                                                    <div class="form-group">
                                                        <input class="col-md-1 form-control input-sm text-right" type="text" name="discount" id="discount"/>
                                                    </div>
                                                </div>
                                                <p class="text-right" id="netTotal">0.00</p>  
                                                <p class="text-right" id="advance">0.00</p>  
                                                <p class="text-right" id="payAmount">0.00</p>                                                 
                                                <b><p class="text-right" id="balance">0.00</p></b>  

                                            </h4>
                                        </div>              
                                    </div>
                                </div>

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
        var itemNameList =${itemNameList};
        var jobList =${jobList};
        var action = "${action}";
        var invoice =${invoice};

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
    <script src='<c:url value="/js/actions/invoice.js"/>'></script>   




</html>
