<%-- 
    Document   : header
    Created on : Aug 22, 2016, 9:34:35 PM
    Author     : Kasun
--%>

<%@page import="com.kn.ce.model.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Navigation -->
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {//check user session is set
        response.sendRedirect("login.htm");
    }
%>

<htmL>
    <body>
        <div class="container-fluid bg-grayLighter " >
            <div class="bg-grayLighter col-md-12 no-padding"></div>
            <div class="bg-grayLighter col-md-2"><img src='<c:url value="/images/logo.png"/>' /></div>
            <div class="col-md-6"><h4>Job Processing, Invoicing and Stock Control System</h4></div>
            <div class="col-md-4 "><h4 class="pull-right"><i class="fa fa-user fa-1x fg-blue"></i> <%=user.getFullName() + " (" + user.getUserRole().getUserRole() + ")" + "-" + user.getLocation().getName()%> <i title="Chanage Password" style='cursor: pointer' onclick="changePasswd()" class="fa fa-cog fa-1x fg-blue"></i> <a href="/CreativeEdge/logout.htm"><i title="Logout" class="fa fa-sign-out fa-2x fg-orange"></i></a></h4></div>     

        </div>
        <div class="container-fluid col-md-12 no-padding " >
            <div class="bg-grayLight col-md-12 no-padding"></div> 
        </div>
        <!--Change user password window-->
        <div class="modal fade" id="window_user_update" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-blue fg-white">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" id="frm_title">Change Password</h4>
                    </div>

                    <div class="modal-body">
                        <div class="alert-danger" id="errorMsg"></div>
                        <form id="frm_user_update" name="frm_user">


                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right">Old Password</label>
                                    <div class="col-md-6">
                                        <input  class="form-control input-sm" data-validation="required" minlength="8" type="Password" name="oldPassword" id="oldPassword" placeholder="Enter Old Password " />
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/> 
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right">New Password</label>
                                    <div class="col-md-6">
                                        <input  class="form-control input-sm" data-validation="required" minlength="8" type="password" name="password" id="chnge_password" placeholder="Enter Password (at least 8 characters)" />
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/> 
                            <div class="row">
                                <div class="form-group">
                                    <label class="col-md-4 text-right">Confirm New Password</label>
                                    <div class="col-md-6">
                                        <input  class="form-control input-sm" data-validation="required" minlength="8" type="password" name="password2" id="chnge_password2" placeholder="Re-Enter Password (at least 8 characters)" />
                                    </div>
                                </div>                                           
                            </div><!--Row-->
                            <br/>                                           


                        </form>

                    </div>
                    <div class="modal-footer bg-grayLighter">
                        <button class="btn btn-success" id="chnge_btn_save" name="btn_save">Save</button>
                        <button type="reset" class="btn btn-default" id="chnge_btn_reset" name="btn_reset">Reset</button>
                    </div>
                </div>
            </div>


        </div>
        <!--End of the Change user password window-->

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
        <script src='<c:url value="/js/actions/header.js"/>'></script>   

    </body>

</htmL>





