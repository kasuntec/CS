/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.model.system;

/**
 *
 * @author Kasun
 */
public  class SystemMessage {
    
    //msg type
    public final static String OK="ok";
    public final static String FAIL="fail";
  
    
    //messges
    //ok
    public final static String SAVE="is saved successfully";
    public final static String UPDATE="is updated successfully";
    public final static String DELETE="is deleted successfully";
    
    //fail
    public final static String EXISTS="is already exists";
    public final static String NOT_FOUND="was not found";
    public final static String NO_PERMISION="You do not have permission to perform this operation";
    public final static String EMPTY="is can't be empty";
    public final static String LENGTH_EXCED="Maximum length exceeded for ";
    public final static String LENGTH_MIN="does not meet the minimum length";
     public final static String DB_ERROR="Database connection error. Please try again later";
     public final static String UN_EX_ERROR="An unexpected error has occurred. please contact supporting team";
     public final static String EXPIRE="Your session has expired. please login again";
     
    
    
    //
    
}
