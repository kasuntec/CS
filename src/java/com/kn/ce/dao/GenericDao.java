/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;

import com.kn.ce.model.system.Where;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kasun
 */
@Repository("genericDao")
public class GenericDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //save method
    public long save(Object object) throws Exception {
        long res = 0l;//define method response varible
        //get current session using session factory       
        Session session = sessionFactory.getCurrentSession();

        //save object using session
        res = (Long) session.save(object);
       
        //return response
        return res;

    }
    //save method
    public String saveReturnString(Object object) throws Exception {
        String res = null;//define method response varible
        //get current session using session factory       
        Session session = sessionFactory.getCurrentSession();

        //save object using session
        res = (String) session.save(object);
       
       
        //return response
        return res;

    }

    //update method
    public long update(Object object) throws Exception {
        long res = 0l;//define method response varible
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //update object using session
        session.update(object);

        res = 1;      
        //return response
        return res;

    }

    //get method
    public Object get(long id, Class cls) {
        //get current session using session factory
       Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        Object object = session.get(cls, id);
    
        //return object
        return object;
    }
    //get method
    public Object get(String id, Class cls) {
        //get current session using session factory
       Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        Object object = session.get(cls, id);
    
        //return object
        return object;
    }

    //get method
    public Object get(Object id, Class cls, String property) {
        //int vars
        Object object = null;
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        Criteria c = session.createCriteria(cls);
        c.add(Restrictions.eq(property, id));
        List list = c.list();
        if (list.size() > 0) {
            object = list.get(0);
        }
        //return object
        return object;
    }
    
    //get method by where
    public Object get(Class cls, List<Where> wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause 
        for (int i = 0; i < wheres.size(); i++) {
            Where where = wheres.get(i);
            criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));

        }
              
        //return list
        List list= criteria.list();
        if(list.size()>0){
            return list.get(0);
        }
        
        return null;
    }

    //delete method
    public long delete(Object object) throws Exception {
        long res = 0l;//define method response varible
        //get current session using session factory
     Session session = sessionFactory.getCurrentSession();

        //delete object using session
        session.delete(object);
        res = 1;
      
        //return response
        return res;

    }

    public List list(Class cls) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //return list
        return criteria.list();

    }
    
    //delete Header detals item lines
    public long deleteLines(Class c,long id,String property){
          Session s = sessionFactory.getCurrentSession();
          String hql="DELETE FROM "+c.getSimpleName()+" WHERE "+property+"=:id";
          Query query = s.createQuery(hql);
          query.setLong("id", id);
          return query.executeUpdate();
    }
   

    //list by multiple wheres using AND
    public List list(Class cls, List<Where> wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause 
        for (int i = 0; i < wheres.size(); i++) {
            Where where = wheres.get(i);
            criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));

        }
      
        //return list
        return criteria.list();

    }
    
    //list by two wheres using OR
    public List listByOR(Class cls, Where where1,Where where2) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        String stQuery="FROM "+cls.getSimpleName()+" "
                + "WHERE "+where1.getProperty()+" = '"+where1.getValue() +"' OR "
                + " " +where2.getProperty()+" = '"+where2.getValue()+"'" ;       

        
         Query query = session.createQuery(stQuery);
      
        //return list
        return query.list();

    }
}
