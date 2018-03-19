<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Raw item issue</title>
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
                        <h3 class="fg-darkBlue" > <span class="glyphicon glyphicon-th-list" ></span> Raw Item Issue
                            <div class="pull-right" >  
                                <span id="img_load"></span>
                                <a href="new.htm"><button id="btnNew"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i> New</button></a>
                                <button id="btnSave"  type="button" class="btn btn-success"><i class="fa fa-floppy-o " aria-hidden="true"></i></span> Save</button>
                               <a href="list_page.htm"><button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i></span> Closed</button></a>                                
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-info" id="list">
                            <div class="panel panel-heading">
                                Raw Item Issue Information
                            </div>
                            <div class="panel panel-body">
                                <div class="col-md-8">
                                    <form id="frm_job" name="frm_issue">
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Issue No</label>
                                                <div class="col-md-2">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="issueNo" id="issueNo" value="New" readonly="true" />
                                                </div>                                               
                                            </div>  
                                            <br>
                                        </div><!--Row-->
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Job No</label>
                                                <div class="col-md-5">
                                                    <select class="form-control input-sm" id="job" name="job"></select>                                             
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
                                                <label class="col-md-3 text-right">Issue To</label>
                                                <div class="col-md-5">
                                                    <select class="form-control input-sm" id="worker" name="worker"></select>                                             
                                                </div> 
                                            </div>  
                                            <br>
                                        </div><!--Row-->
                                        
                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Remarks</label>
                                                <div class="col-md-6">
                                                    <input class="col-md-3 form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Enter Remaks" />                                            
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
                                            <div class="col-md-8">
                                                <h4 class="fg-gray">
                                                    <p>No of Items</p>                                                   
                                                    <p>Total Qty</p>                                                   
                                                </h4>
                                            </div>
                                            <div class="col-md-4">
                                                <h4 class="fg-darkBlue pull-right">
                                                    <p class="text-right" id="itemCount">0</p>                                                                                   
                                                    <p class="text-right" id="totalQty">0</p>                                                                                   
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
                                    <div class="col-md-2">Qty</div>
                                    <div class="col-md-1"></div>
                                    <div class="col-md-2">Available Stock</div>
                                </div>
                                <form id="frm_line" name="frm_line">
                                    <div class="row">
                                        <div class="col-md-4  no-padding-right">                                       
                                            <select class="form-control input-sm" id="itemId" name="itemId" data-validation="required" ></select>                                             
                                        </div>
                                        <div class="col-md-2  no-padding-right">
                                            <input class="col-md-3 form-control input-sm" type="text" name="qty" id="qty" onkeypress="return isNumberKey(event)" data-validation="required"  value="0.00"  />                                            
                                        </div>
                                        <div class="col-md-1  no-padding-right">
                                             <span id="stock_load"></span>
                                        </div>
                                        <div class="col-md-2  no-padding-right">
                                            <label name="stock" id="stock"  class="col-md-3 text-right">...</label>
                                        </div>
                                        
                                        <div class="col-md-3  no-padding-right">
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
        var itemNameList =${itemNameList};
        var jobNoList =${jobNoList};
        var workerList =${workerList};
        var action = "${action}";
        var rawItemIssue =${rawItemIssue};
    


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
    <script src='<c:url value="/js/actions/rawIssue.js"/>'></script>   
 



</html>
