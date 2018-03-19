<%-- 
    Document   : print
    Created on : Jan 28, 2016, 1:27:52 AM
    Author     : user
--%>

<%@page import="com.kn.ce.service.PrintService"%>
<%@page import="net.sf.jasperreports.web.servlets.ReportServlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Print</title>
    </head>
    <body>
        <%
            String appPath = application.getRealPath("/");
            String fullPath = appPath;
            HashMap hashMap = new HashMap();
            PrintService printService = new PrintService();
            hashMap.put("id", request.getParameter("id"));            
            String printFile=request.getParameter("print_file");
            printService.getReport(fullPath + "/reports/"+printFile+".jasper", hashMap, response);
        %>
      

    </body>
</html>
