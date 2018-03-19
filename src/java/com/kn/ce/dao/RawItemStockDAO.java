/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;

import com.kn.ce.model.entity.RawItem;
import com.kn.ce.model.entity.RawItemStock;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kasun
 */
@Repository("rawItemStockDAO")
public class RawItemStockDAO {

    @Autowired
    private SessionFactory sessionFactory;

    //get raw Item Stock
    public Double getStock(RawItem rawItem) {
        Double stock = 0.00;

        //get current session
        Session s = sessionFactory.getCurrentSession();

        //create query
        Query query = s.createQuery("SELECT SUM(qty) FROM RawItemStock WHERE rawItem.itemId=:itemId GROUP BY rawItem.itemId");
        query.setLong("itemId", rawItem.getItemId());
         List list = query.list();
        
        if(list.size()>0){//check list is not empty
            stock=(Double) list.get(0);//get value and cust to double
        }

        return stock;
    }

}
