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
 * Worker generated by hbm2java
 */
@Entity
@Table(name="worker"
    ,catalog="creative_edge"
)
public class Worker  implements java.io.Serializable {


     private long id;
     private String etfNo;
     private String fullName;
     private String designation;
     private String mobile;
     private Set<RawItemIssue> rawItemIssues = new HashSet<RawItemIssue>(0);

    public Worker() {
    }

	
    public Worker(long id, String etfNo, String fullName, String designation, String mobile) {
        this.id = id;
        this.etfNo = etfNo;
        this.fullName = fullName;
        this.designation = designation;
        this.mobile = mobile;
    }
    public Worker(long id, String etfNo, String fullName, String designation, String mobile, Set<RawItemIssue> rawItemIssues) {
       this.id = id;
       this.etfNo = etfNo;
       this.fullName = fullName;
       this.designation = designation;
       this.mobile = mobile;
       this.rawItemIssues = rawItemIssues;
    }
   
    @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    
    @Column(name="etf_no", nullable=false, length=20)
    public String getEtfNo() {
        return this.etfNo;
    }
    
    public void setEtfNo(String etfNo) {
        this.etfNo = etfNo;
    }

    
    @Column(name="full_name", nullable=false)
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
    @Column(name="designation", nullable=false, length=50)
    public String getDesignation() {
        return this.designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    
    @Column(name="mobile", nullable=false, length=20)
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="worker")
    public Set<RawItemIssue> getRawItemIssues() {
        return this.rawItemIssues;
    }
    
    public void setRawItemIssues(Set<RawItemIssue> rawItemIssues) {
        this.rawItemIssues = rawItemIssues;
    }




}


