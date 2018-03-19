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
                        <h3 class="fg-darkBlue " > <span class="glyphicon glyphicon-th-list" ></span> System Access for User Role
                            <div class="pull-right" >                                   
                                <button id="btnSave"  type="button" class="btn btn-success"><i class="fa fa-floppy-o " aria-hidden="true"></i></span> Save</button>
                            </div>
                        </h3>
                        <hr>

                        <br>
                        <div class="panel panel-primary" id="list">
                            <div class="panel panel-heading">

                            </div>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right">User Role</label>
                                    <div class="col-md-3">
                                        <select id="userRole" name="userRole" class="form-control">
                                            <c:forEach var="userRole" items="${userRoles}">
                                                <option value="${userRole.userRole}">${userRole.userRole}</option>
                                            </c:forEach>
                                                 <option value="0">Select User role</option>
                                        </select>
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right">Page</label>
                                    <div class="col-md-5">
                                        <select id="page" name="page" class="form-control">
                                        </select>
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right"></label>
                                    <div class="col-md-3">
                                       <button id="btnAddItem"  type="button" class="btn btn-info" ><i class="fa fa-plus" aria-hidden="true"></i>Add Item</button>
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            
                          
                            <hr>
                            <div class="panel panel-body">
                                <table id="tbl_user_access"></table>
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
        var selectPageList=${selectPageList};
    </script>

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
    <script src='<c:url value="/js/actions/user_access.js"/>'></script>   




</html>
