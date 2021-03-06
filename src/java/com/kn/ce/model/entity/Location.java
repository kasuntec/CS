package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Location generated by hbm2java
 */
@Entity
@Table(name="location"
    ,catalog="creative_edge"
)
public class Location  implements java.io.Serializable {


     private String locationId;
     private String name;
     private String addressLine1;
     private String addressLine2;
     private String city;
     private String postalCode;
     private Set<User> users = new HashSet<User>(0);

    public Location() {
    }

	
    public Location(String locationId, String name, String addressLine1, String addressLine2, String city) {
        this.locationId = locationId;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
    }
    public Location(String locationId, String name, String addressLine1, String addressLine2, String city, String postalCode, Set<User> users) {
       this.locationId = locationId;
       this.name = name;
       this.addressLine1 = addressLine1;
       this.addressLine2 = addressLine2;
       this.city = city;
       this.postalCode = postalCode;
       this.users = users;
    }
   
     @Id 

    
    @Column(name="location_id", unique=true, nullable=false, length=50)
    public String getLocationId() {
        return this.locationId;
    }
    
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    
    @Column(name="name", nullable=false, length=100)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="address_line1", nullable=false, length=100)
    public String getAddressLine1() {
        return this.addressLine1;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    
    @Column(name="address_line2", nullable=false)
    public String getAddressLine2() {
        return this.addressLine2;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    
    @Column(name="city", nullable=false)
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    
    @Column(name="postal_code", length=50)
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="location")
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }




}


