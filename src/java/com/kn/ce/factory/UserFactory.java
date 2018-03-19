/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.factory;

import com.kn.ce.model.entity.Location;
import com.kn.ce.model.entity.SystemPage;
import com.kn.ce.model.entity.User;
import com.kn.ce.model.entity.UserPageAccess;
import com.kn.ce.model.entity.UserPageAccessId;
import com.kn.ce.model.entity.UserRole;
import com.kn.ce.service.UserService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kasun
 */
@Component("userFactory")
public class UserFactory {

    /*-----------------------------
    Convert customer list JSON array
    ------------------------------*/
    @Autowired
    private UserService userService;

    public String convertToJSON(List<User> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<User> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            User user = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("username", user.getUsername());
            jSONObject.put("location", user.getLocation().getName());
            jSONObject.put("userRole", user.getUserRole().getUserRole());
            jSONObject.put("fullName", user.getFullName());
            jSONObject.put("email", user.getEmail());
            jSONObject.put("active", user.isActive());
            jSONObject.put("login", user.isLogin());

            //add json object to json array
            array.add(jSONObject);

        }
        return array.toJSONString();
    }

    /*-----------------------------
    Convert customer object to JSON object
    ------------------------------*/
    public String convertToJSON(User user) {
        //declare varibles
        JSONObject jSONObject = new JSONObject();//create json object

        //put object to json object
        jSONObject.put("username", user.getUsername());
        jSONObject.put("location", user.getLocation().getLocationId());
        jSONObject.put("userRole", user.getUserRole().getUserRole());
        jSONObject.put("fullName", user.getFullName());
        jSONObject.put("email", user.getEmail());
        jSONObject.put("active", user.isActive());
        jSONObject.put("login", user.isLogin());

        return jSONObject.toJSONString();
    }

    /*-----------------------------
    Convert  list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectList(List<UserRole> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<UserRole> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            UserRole userRole = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", userRole.getUserRole());
            jSONObject.put("text", userRole.getUserRole());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

    /*-----------------------------
    Convert  list to Select2 list
    ------------------------------*/
    public JSONArray convertToSelectLocationList(List<Location> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<Location> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            Location location = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", location.getLocationId());
            jSONObject.put("text", location.getName());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }

    //user page list to JSON
    public String convertPageListToJSON(List<SystemPage> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<SystemPage> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            SystemPage systemPage = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", systemPage.getId());
            jSONObject.put("pageName", systemPage.getPageName());

            //add json object to json array
            array.add(jSONObject);

        }
        return array.toJSONString();
    }

    //create user access list
    public List<UserPageAccess> createList(UserRole userRole, String pageList) throws ParseException {
        //difine
        List<UserPageAccess> pageAccessList = new ArrayList<UserPageAccess>();
        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(pageList);

        Iterator<JSONArray> iterator = array.iterator();

        while (iterator.hasNext()) {
            JSONArray jsonPage = iterator.next();
            
            //define and create access object using json
            UserPageAccess pageAccess = new UserPageAccess();
            pageAccess.setUserRole(userRole);
            String pageId = (String) jsonPage.get(0);
            int pageIdInt=Integer.parseInt(pageId);           
            SystemPage systemPage = userService.getPageById(pageIdInt);
            UserPageAccessId userPageAccessId = new UserPageAccessId(pageIdInt, userRole.getUserRole());
            pageAccess.setId(userPageAccessId);
            pageAccess.setSystemPage(systemPage);
            pageAccess.setAccess(true);            
            
            //addd object to list
            pageAccessList.add(pageAccess);

        }
        return pageAccessList;
    }
    
    //convert list to select2 json array
     public JSONArray convertToSelectPagesList(List<SystemPage> list) {
        //declare varibles
        JSONArray array = new JSONArray();

        Iterator<SystemPage> iterator = list.iterator();

        while (iterator.hasNext()) {//loop list
            SystemPage systemPage = iterator.next();
            JSONObject jSONObject = new JSONObject();//create json object
            //put object to json object

            jSONObject.put("id", systemPage.getId());
            jSONObject.put("text", systemPage.getPageName());

            //add json object to json array
            array.add(jSONObject);

        }
        return array;
    }
    

}
