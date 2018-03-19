<%-- 
    Document   : login
    Created on : Sep 1, 2016, 9:34:07 PM
    Author     : Kasun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>System Login</title>

        <!--CSS-->
        <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet"	type="text/css" />
        <link href='<c:url value="/css/metro-colors.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/metro-icons.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/jquery-ui.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/select2.min.css"/>' rel="stylesheet" type="text/css" />     
        <link href='<c:url value="/css/jquery.dataTables.min.css"/>' rel="stylesheet" type="text/css" />
        <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css" />

        <!--JS-->
        <script src='<c:url value="/js/lib/jquery-1.11.2.min.js"/>'></script>
        <script src='<c:url value="/js/lib/bootstrap.min.js"/>'></script>
    </head>
    <body>
        <div class="container-fluid bg-grayLighter " >
            <div class="bg-grayLighter col-md-12 no-padding"></div>
            <div class="bg-grayLighter col-md-2"><img src='<c:url value="/images/logo.png"/>' /></div>
            <div class="col-md-6"><h4>Job Processing, Invoicing and Stock Control System</h4></div>
    
        </div>
        <div class="container-fluid col-md-12 no-padding " >
            <div class="bg-grayLight col-md-12 no-padding"></div> 
        </div>
        <div class="container-fluid">
            <br>
            <br>
            <br>
            <br>

            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">User Login</div>
                    <div class="panel-body">
                        <div>${errorMsg}</div>
                        <br>
                        <div class="col-md-1">
                             <img src="<c:url value='/images/icons/user_access_icon.png'/>"/>
                        </div>
                        <div class="col-md-11">
                              <form action="login.htm" method="post" id="frm_login">
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-6 text-right">User Name</label>
                                    <div class="col-md-6">
                                        <input class="col-md-5 form-control input-sm" type="text" name="username" id="username" placeholder="Enter User name" />
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-6 text-right">Password</label>
                                    <div class="col-md-6">
                                        <input class=" form-control input-sm" type="password" name="password" id="password" placeholder="Enter Password"  data-validation="required"/>
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/>
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-6 text-right"></label>
                                    <div class="col-md-6">
                                        <input class="btn btn-success" type="submit" value="login">   
                                        <input class="btn btn-danger" type="reset" value="Cancel">   
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/>

                        </form>
                        </div>
                      
                    </div>
                    <div class="panel-footer text-center fg-darkBlue bg-grayLighter">Design & Developed by Kasun Nadeeshana</div>

                </div>
            </div>
        </div>
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
    
    <script>
     
    </script>
      
    </body>
   
</html>

