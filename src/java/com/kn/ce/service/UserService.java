/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.dao.UserRoleDAO;
import com.kn.ce.model.entity.Location;
import com.kn.ce.model.entity.SystemPage;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.entity.UserPageAccess;
import com.kn.ce.model.entity.UserRole;
import com.kn.ce.model.system.Where;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("userService")
public class UserService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private UserRoleDAO userRoleDAO;

    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }

    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public String save(User user) throws Exception {
        return genericDao.saveReturnString(user);
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(User user) throws Exception {
        return genericDao.update(user);
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public User get(String username) throws Exception {
        User user = (User) genericDao.get(username, User.class, "username");

        if (user != null) {
            Hibernate.initialize(user.getUserRole());
            Hibernate.initialize(user.getLocation());
        }

        return user;
    }

    //get user role
    @Transactional(rollbackFor = Exception.class)
    public UserRole getUserRole(String userRole) throws Exception {
        UserRole userRoleObject = (UserRole) genericDao.get(userRole, UserRole.class, "userRole");
        return userRoleObject;
    }

    /*-----------------------------
    List method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list() throws Exception {
        List<User> list = new ArrayList<User>();
        Iterator<User> iterator = genericDao.list(User.class).iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            Hibernate.initialize(user.getUserRole());
            Hibernate.initialize(user.getLocation());
            list.add(user);

        }
        return list;
    }

    /*-----------------------------
    check user is loggin
    -----------------------------*/
    public static boolean isUserLoggin(HttpSession session) {
        User user = (User) session.getAttribute("user");//get user from session
        if (user != null) {//check user is not null
            return true;
        }
        return false;

    }

    /*-----------------------------
    check  is isAuthorized for user
    -----------------------------*/
    public static boolean isAuthorized(HttpSession session, String url) {
//         SystemUser user = (SystemUser) session.getAttribute("user");//get user from session
//         if(user!=null){//check user is not null
//             return true;
//         }
//         return false;

        return true;

    }

    /*-----------------------------
    List user access pages by user role
    -----------------------------*/
    @Transactional
    public List<SystemPage> listPagesByUserRole(UserRole userRole) {
        return userRoleDAO.listPagesByUserRole(userRole);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(User user) throws Exception {
        return genericDao.delete(user);
    }

    /*-----------------------------
    redirect To Loggin Page
    -----------------------------*/
    public String redirectToLogginPage() {

        return "redirect:../login.htm";
    }

    //list user role
    @Transactional(rollbackFor = Exception.class)
    public List<UserRole> listUserRole() {
        return userRoleDAO.listAccessUserRoles();
    }
    //list user role

    @Transactional(rollbackFor = Exception.class)
    public List<Location> listLocation() {
        return genericDao.list(Location.class);
    }

    //------------------------------USER ACCESS-------------------------
    @Transactional
    public long saveAccessLevel(List<UserPageAccess> pageAccesList) throws Exception {
        long res = 0;

        //delete old access list
        UserRole userRole = pageAccesList.get(0).getUserRole(); //
        res = userRoleDAO.deleteOldUserAccess(userRole);

        //save new access list      
        if (res > 0) {
            Iterator<UserPageAccess> iterator = pageAccesList.iterator();//get iterator
            while (iterator.hasNext()) {//loop acces list

                UserPageAccess pageAccess = iterator.next();
                res = userRoleDAO.saveUserAccess(pageAccess); //save

            }
        }

        return res;

    }

    /*-----------------------------
    List pages catergory by user role
    -----------------------------*/
    @Transactional
    public List<String> listPagesCatByUserRole(UserRole userRole) {
        return userRoleDAO.listPagesCatByUserRole(userRole);
    }

    //get page by id
    @Transactional
    public SystemPage getPageById(int id) {
        return (SystemPage) userRoleDAO.getPage(id);
    }

    //list system pages
    @Transactional
    public List pagesList() {
        return genericDao.list(SystemPage.class);
    }

}
