/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kasun
 */
@Repository("printDAO")
public class PrintDAO {
     public static Connection getConnection() throws SQLException {

         Connection connection=null;
        if (connection == null) {
            try {
            //connection infomation          
                
                String server_Name = "localhost";
                String db_Name = "creative_edge";
                String db_User = "root";
                String db_Pass = "pcsadmin";
                /*-----------------------------
                 -----------------------------*/
                Class.forName("com.mysql.jdbc.Driver");

            //connect
            connection = DriverManager.getConnection("jdbc:mysql://" + server_Name + ":3306/" + db_Name, db_User, db_Pass);
                boolean autoCommit = connection.getAutoCommit();
                if(autoCommit){
                    connection.setAutoCommit(true);
                }
            } catch (ClassNotFoundException e) {
            } catch (SQLException e) {              
                throw e;

            }
        }
        
        
        return connection;
    }
    
}
