<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>System Users</title>
        <!--CSS-->
        <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet"	type="text/css" />
        <link href='<c:url value="/css/metro-colors.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/metro-icons.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery-ui.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/bootstrap-toggle.min.css"/>' rel="stylesheet" type="text/css" />
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
                        <h3 class="fg-darkBlue " > <span class="glyphicon glyphicon-th-list" ></span> System Users
                            <div class="pull-right" >                                   
                                <button id="newbtn"  type="button" class="btn btn-success"  ><span class="glyphicon glyphicon-file" aria-hidden="true" ></span> New</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-primary" id="list">
                            <div class="panel panel-heading">

                            </div>
                            <div class="panel panel-body">
                                <table id="tbl_user"></table>
                            </div>
                        </div><!--main panel div-->

                        <!--Add New Form-->
                        <div class="modal fade" id="window_user" role="dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header bg-blue fg-white">
                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                        <h4 class="modal-title" id="frm_title">Create New System User</h4>
                                    </div>

                                    <div class="modal-body">
                                        <div class="alert-danger" id="errorMsg"></div>
                                        <form id="frm_user" name="frm_user">
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">User Name</label>
                                                    <div class="col-md-3">
                                                        <input class="col-md-4 form-control input-sm" type="text" name="username" id="username" data-validation="required"  />

                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                            <br/>
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Location</label>
                                                    <div class="col-md-6">
                                                        <select id="location" name="location.locationId" class="form-control">
                                                            <c:forEach var="location" items="${locations}">
                                                                <option value="${location.locationId}">${location.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">User Role</label>
                                                    <div class="col-md-6">
                                                        <select id="userRole" name="userRole.userRole" class="form-control">
                                                            <c:forEach var="userRole" items="${userRoles}">
                                                                <option value="${userRole.userRole}">${userRole.userRole}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                            <br/>

                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Full Name</label>
                                                    <div class="col-md-6">
                                                        <input class="form-control input-sm" type="text" name="fullName" id="fullName" placeholder="Full Name" data-validation="required"/>
                                                    </div>


                                                </div>                                           
                                            </div><!--Row-->

                                            <br/>
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">E-mail</label>
                                                    <div class="col-md-6">
                                                        <input class="form-control input-sm" type="email" data-validation="email" name="email" id="email" placeholder="Abc@abc.com"  />
                                                    </div>

                                                </div>                                           
                                            </div><!--Row-->
                                            <br/>

                                           
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Password</label>
                                                    <div class="col-md-6">
                                                        <input  class="form-control input-sm" data-validation="required" minlength="8" type="password" name="password" id="password" placeholder="Enter Password (at least 8 characters)" />
                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                            
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Confirm Password</label>
                                                    <div class="col-md-6">
                                                        <input  class="form-control input-sm" data-validation="required" minlength="8" type="password" name="password2" id="password2" placeholder="Enter Password (at least 8 characters)" />
                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                              <br/>
                                            <div class="row">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Active</label>
                                                    <div class="col-md-6">
                                                        <input id="active" name="active" data-toggle="toggle" type="checkbox" data-on="Yes" data-off="No" data-width="100">                                                        
                                                    </div>
                                                </div>                                           
                                            </div><!--Row-->
                                            
                                            
                                            <br/>


                                        </form>

                                    </div>
                                    <div class="modal-footer bg-grayLighter">
                                        <button class="btn btn-success" id="btn_create" name="btn_create">Create</button>
                                        <button type="reset" class="btn btn-default" id="btn_reset" name="btn_reset">Reset</button>
                                    </div>
                                </div>
                            </div>


                        </div>
                        <!--Add New Form-->
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
    <script src='<c:url value="/js/lib/jquery.dataTables.min.js"/>'></script>
    <script src='<c:url value="/js/lib/select2.min.js"/>'></script>   
    <script src='<c:url value="/js/lib/jquery-ui.js"/>'></script> 
    <script src='<c:url value="/js/lib/jquery.notify.min.js"/>'></script> 
    <script src='<c:url value="/js/lib/bootstrap2-toggle.min.js"/>'></script> 
    <script src='<c:url value="/js/actions/systemNotification.js"/>'></script>  
    <script src='<c:url value="/js/actions/formValidator.js"/>'></script> 
    <script src='<c:url value="/js/actions/formJSONFactory.js"/>'></script>     
    <script src='<c:url value="/js/actions/user.js"/>'></script>   




</html>
