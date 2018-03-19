package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Customer generated by hbm2java
 */
@Entity
@Table(name="customer"
    ,catalog="creative_edge"
)
public class Customer  implements java.io.Serializable {


     private Long custId;
     private String name;
     private String addressLine1;
     private String addressLine2;
     private String city;
     private String postalCode;
     private String tel1;
     private String tel2;
     private String fax;
     private String cperson;
     private String mobile;
     private String email;
     private String remarks;
     private Set<Job> jobs = new HashSet<Job>(0);
     private Set<CustomerTransaction> customerTransactions = new HashSet<CustomerTransaction>(0);

    public Customer() {
    }

	
    public Customer(String name, String addressLine1, String addressLine2, String city, String tel1) {
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.tel1 = tel1;
    }
    public Customer(String name, String addressLine1, String addressLine2, String city, String postalCode, String tel1, String tel2, String fax, String cperson, String mobile, String email, String remarks, Set<Job> jobs, Set<CustomerTransaction> customerTransactions) {
       this.name = name;
       this.addressLine1 = addressLine1;
       this.addressLine2 = addressLine2;
       this.city = city;
       this.postalCode = postalCode;
       this.tel1 = tel1;
       this.tel2 = tel2;
       this.fax = fax;
       this.cperson = cperson;
       this.mobile = mobile;
       this.email = email;
       this.remarks = remarks;
       this.jobs = jobs;
       this.customerTransactions = customerTransactions;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="cust_id", unique=true, nullable=false)
    public Long getCustId() {
        return this.custId;
    }
    
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    
    @Column(name="name", nullable=false)
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

    
    @Column(name="tel1", nullable=false, length=15)
    public String getTel1() {
        return this.tel1;
    }
    
    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    
    @Column(name="tel2", length=15)
    public String getTel2() {
        return this.tel2;
    }
    
    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    
    @Column(name="fax", length=50)
    public String getFax() {
        return this.fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }

    
    @Column(name="cperson", length=50)
    public String getCperson() {
        return this.cperson;
    }
    
    public void setCperson(String cperson) {
        this.cperson = cperson;
    }

    
    @Column(name="mobile", length=15)
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    @Column(name="email", length=50)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="remarks", length=100)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public Set<Job> getJobs() {
        return this.jobs;
    }
    
    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="customer")
    public Set<CustomerTransaction> getCustomerTransactions() {
        return this.customerTransactions;
    }
    
    public void setCustomerTransactions(Set<CustomerTransaction> customerTransactions) {
        this.customerTransactions = customerTransactions;
    }




}


