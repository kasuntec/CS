<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Job</title>
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
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> Customer Job
                            <div class="pull-right" >                                   
                                <a href="new.htm"><button id="btnNew"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i> New</button></a>
                                <button id="btnSave"  type="button" class="btn btn-success"><span><i class="fa fa-floppy-o " aria-hidden="true"></i></span> Save</button>
                                <a href="list_page.htm"><button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i></span> Close</button></a>                                
                                <button id="btnPrint"  type="button" class="btn btn-warning"><span><i class="fa fa-print" aria-hidden="true"></i></span> Print</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                Job Information
                            </div>
                            <div class="panel panel-body">
                                <div class="col-md-8">
                                    <form id="frm_job" name="frm_job">
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Job No</label>
                                                <div class="col-md-2">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="jobNo" id="jobNo" value="New" readonly="true" />
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->
                                        <div class="row">
                                            <div class="form-group">

                                                <label class="col-md-3 text-right">Status</label>
                                                <div class="col-md-4">
                                                    <select name="status" id="status" class="form-control input-sm">
                                                        <option value="Open">Open</option>
                                                        <option value="Canceled">Canceled</option>
                                                        <option value="Started">Started</option>
                                                        <option value="Finished">Finished</option>
                                                        <option value="Invoiced">Invoiced</option>
                                                        <option value="Closed">Closed</option>
                                                    </select>
                                                </div>



                                            </div>  
                                            <br>
                                        </div><!--Row-->
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Customer</label>
                                                <div class="col-md-5">
                                                    <select class="form-control input-sm" id="customer" name="customer"></select>                                             
                                                </div>    

                                                <div class="col-md-4">
                                                    <span id="img_load"></span>
                                                    <button id="btnAddCust"  type="button" class="btn btn-info"><i class="fa fa-plus " ></i></span> Add New</button>
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
                                                <label class="col-md-3 text-right">Delivery Date</label>
                                                <div class="col-md-3">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="delivryDate" id="delivryDate"  data-validation="required" placeholder="YYYY-MM-dd" />                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->                                    
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Remarks</label>
                                                <div class="col-md-6">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Enter Job Remaks" />                                            
                                                </div>                                            
                                            </div>  
                                            <br>
                                        </div><!--Row-->

                                    </form>
                                </div>
                                <div class="col-md-4">
                                    <div class="panel panel-info">
                                        <div class="panel panel-heading">
                                            Totals
                                        </div>
                                        <div class="panel panel-body no-padding-bottom no-padding-top no-padding-left no-padding-right">
                                            <div class="col-md-6">
                                                <h4 class="fg-gray">
                                                    <p>Job Total</p>
                                                    <p>Advance</p>
                                                    <p>Balance</p>
                                                </h4>
                                            </div>
                                            <div class="col-md-6">
                                                <h4 class="fg-darkBlue pull-right">
                                                    <p class="text-right" id="total">0.00</p>
                                                    <p class="text-right" id="advance">0.00</p>
                                                    <p class="text-right" id="balance">0.00</p>                                                   
                                                </h4>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <button id="btnAdvance" class="btn btn-info pull-right"><i class="fa fa-money" aria-hidden="true"></i> Add Advance</button>

                                                </div>
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
                                    <div class="col-md-3">Item</div>
                                    <div class="col-md-2">Unit Price</div>                                   
                                    <div class="col-md-1">Width</div>
                                    <div class="col-md-1">Height</div>
                                    <div class="col-md-1">Qty</div>
                                    <div class="col-md-3">Item Remarks</div>
                                </div>
                                <form id="frm_line" name="frm_line">
                                    <div class="row">
                                        <div class="col-md-3  no-padding-right">                                       
                                            <select class="form-control input-sm" id="itemId" name="itemId" data-validation="required" ></select>                                             
                                        </div>
                                        <div class="col-md-2  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="unitPrice" id="unitPrice" onkeypress="return isNumberKey(event)" data-validation="required"  value="0.00"  />                                            
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
                                        <div class="col-md-3  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="line_remarks" id="line_remarks"  />                                           
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
                                <table id="tbl_line"></table>
                            </div>


                        </div>


                    </div><!--main row-->
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->
        <!--Advance Page-->
        <div id="window_advance" class="modal fade " role="dialog ">
            <div class="modal-dialog ">
                <div class="modal-content">
                    <div class="modal-header  bg-blue fg-white no-padding-bottom">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <div class="modal-title">
                            <h4><i class="fa fa-money fa-1x"></i> Advance Payment</h4>
                        </div>

                    </div>
                    <div class="modal-body">
                        <form id="frm_advance" name="frm_advance">
                            <div class="row">
                                <div class="col-md-2">
                                    <img src="<c:url value='/images/icons/adv_icon.png'/>"/>
                                </div>
                                <div class="col-md-10">
                                    <!--Row-->
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-4 text-right">Advance No</label>
                                            <div class="col-md-3">
                                                <input type="text" class="form-control col-md-3 input-sm" id="advId" name="AdvId" readonly="" value="New"/>                                        
                                            </div>
                                        </div>
                                        <br>
                                    </div>

                                    <div class="row" >   
                                        <div class="form-group">
                                            <label class="col-md-4 text-right">Pay Media</label>
                                            <div class="col-md-4">
                                                <select id="payType" name="payType" class="form-control input-sm">                                           
                                                    <option>Card</option>
                                                    <option>Cheque</option>
                                                    <option>Cash</option>
                                                </select>                                        
                                            </div>
                                        </div> 
                                        <br>
                                    </div>

                                    <!--Row-->
                                    <div class="row" id="rowRefNo">
                                        <div class="form-group">
                                            <label class="col-md-4 text-right" id="lbl_RefNo">Card /Cheque No</label>
                                            <div class="col-md-7">
                                                <input type="text"  class="col-md-5 form-control input-sm" id="refNo" name="refNo" />                                        
                                            </div>
                                        </div>
                                        <br>
                                    </div>

                                    <!--Row-->
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-4 text-right">Job Value</label>
                                            <div class="col-md-4">
                                                <input type="text"  class="col-md-3 form-control input-sm" id="jobValue" name="jobValue" data-validation="required" readonly=""/>                                        
                                            </div>
                                        </div>
                                        <br>
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-4 text-right">Amount</label>
                                            <div class="col-md-4">
                                                <input type="text" onkeypress='return isNumberKey(event)' class="col-md-3 form-control input-sm" id="amount" name="amount" placeholder="Amount" data-validation="required"/>                                        
                                            </div>
                                        </div>
                                        <br>
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-4 text-right">Balance</label>
                                            <div class="col-md-4">
                                                <input type="text" onkeypress='return isNumberKey(event)' class="col-md-3 form-control input-sm" id="bal" name="bal"  value="0.0" readonly=""/>                                        
                                            </div>
                                        </div>
                                        <br>
                                    </div>
                                </div>
                            </div>





                        </form>

                    </div>

                    <div class="modal-footer bg-grayLighter">
                        <button class="btn btn-success" id="btn_adv_save" name="btn_adv_save">Save</button>
                        <button type="reset" class="btn btn-default" id="btn_adv_reset" name="btn_adv_reset">Reset</button>
                    </div>


                </div>
            </div>

        </div>
        <!--Add Customer add page-->
        <jsp:include page="customer.jsp"/>
    </body>

    <script>
        var itemNameList =${itemNameList};
        var custNameList =${custNameList};
        var action = "${action}";
        var job =${job};
        var advancePayment =${advancePayment};


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
    <script src='<c:url value="/js/actions/job.js"/>'></script>   
    <script src='<c:url value="/js/actions/customer.js"/>'></script>   



</html>
