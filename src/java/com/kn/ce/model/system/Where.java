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
public class Where {
    
    private String property;
    private Object value;

    public Where(String property, Object value) {
        this.property = property;
        this.value = value;
    }
    
    

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    
    
}
