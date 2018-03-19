/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.PrintDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kasun
 */
@Service("printService")
public class PrintService {
    
    //dependancy injection
    private PrintDAO printDAO;
    
    
    public void getReport(String reportFile, HashMap parameters, HttpServletResponse response) throws IOException, JRException {

        try {
            Connection connection = printDAO.getConnection();
            byte[] bytes = null;
            ServletOutputStream servletOutputStream = response.getOutputStream();
            bytes = JasperRunManager.runReportToPdf(reportFile, parameters, connection);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=report.pdf");

            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();           
            
        } 

    }
    
}
