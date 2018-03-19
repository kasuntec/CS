/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.dao;


import com.kn.ce.model.entity.SystemPage;
import com.kn.ce.model.entity.UserPageAccess;
import com.kn.ce.model.entity.UserRole;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("userRoleDAO")
public class UserRoleDAO {
    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/
    

    /*-----------------------------
    List user access pages by user role
    -----------------------------*/   
    public List<SystemPage> listPagesByUserRole(UserRole userRole) {
        List<SystemPage> systemPages = new ArrayList<SystemPage>();

        Session s = sessionFactory.getCurrentSession();

        SQLQuery query = s.createSQLQuery("SELECT  "
                + "system_page.id AS id,"
                + "system_page.page_name AS pageName,"
                + "system_page.url AS url,"
                + "system_page.icon icon,"
                + "system_page.catergory  AS catergory "
                + "FROM system_page  "
                + "Inner Join user_page_access ON system_page.id = user_page_access.page_id "
                + "WHERE user_page_access.user_role =:user_role AND user_page_access.access=1 "
                + "Order by id ASC ");

        query.setResultTransformer(Transformers.aliasToBean(SystemPage.class));
        //set parameters
        query.setString("user_role", userRole.getUserRole());
        systemPages = query.list();

        return systemPages;

    }
    
    /*-----------------------------
    List pages catergory by user role
    -----------------------------*/

    public List<String> listPagesCatByUserRole(UserRole userRole) {

        Session s = sessionFactory.getCurrentSession();

        SQLQuery query = s.createSQLQuery("SELECT  "
                + "system_page.catergory  AS catergory "
                + "FROM system_page  "
                + "Inner Join user_page_access ON system_page.id = user_page_access.page_id "
                + "WHERE user_page_access.user_role =:user_role AND user_page_access.access=1 "
                + "Group by catergory Order by id ASC");

       
        //set parameters
        query.setString("user_role", userRole.getUserRole());
        return query.list();

    }
    
    //save user access
    public long saveUserAccess(UserPageAccess pageAccess) throws Exception {
        long res = 0l;//define method response varible
        //get current session using session factory       
        Session session = sessionFactory.getCurrentSession();

        //save object using session
        session.save(pageAccess);
        res=1l;
       
        //return response
        return res;

    }
    
    
    //delete old lines
    public long deleteOldUserAccess(UserRole userRole){
          Session s = sessionFactory.getCurrentSession();
          String hql="DELETE FROM UserPageAccess WHERE userRole.userRole=:userRole";
          Query query = s.createQuery(hql);
          query.setString("userRole", userRole.getUserRole());
          return 1l;
    }
    
    public SystemPage getPage(int id) {
        //get current session using session factory
       Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        SystemPage systemPage = (SystemPage) session.get(SystemPage.class, id);
    
        //return object
        return systemPage;
    }
    
    //load all user roles expect system admin and Super User
    public List listAccessUserRoles() {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(UserRole.class);
        criteria.add(Restrictions.ne("userRole", "System Admin"));
        criteria.add(Restrictions.ne("userRole", "Super User"));

        //return list
        return criteria.list();

    }

}
