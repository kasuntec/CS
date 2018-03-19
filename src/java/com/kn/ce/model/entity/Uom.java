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
 * Uom generated by hbm2java
 */
@Entity
@Table(name="uom"
    ,catalog="creative_edge"
)
public class Uom  implements java.io.Serializable {


     private String uom;
     private Set<FinishedItem> finishedItems = new HashSet<FinishedItem>(0);
     private Set<RawItem> rawItems = new HashSet<RawItem>(0);

    public Uom() {
    }

	
    public Uom(String uom) {
        this.uom = uom;
    }
    public Uom(String uom, Set<FinishedItem> finishedItems, Set<RawItem> rawItems) {
       this.uom = uom;
       this.finishedItems = finishedItems;
       this.rawItems = rawItems;
    }
   
     @Id 

    
    @Column(name="uom", unique=true, nullable=false, length=15)
    public String getUom() {
        return this.uom;
    }
    
    public void setUom(String uom) {
        this.uom = uom;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="uom")
    public Set<FinishedItem> getFinishedItems() {
        return this.finishedItems;
    }
    
    public void setFinishedItems(Set<FinishedItem> finishedItems) {
        this.finishedItems = finishedItems;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="uom")
    public Set<RawItem> getRawItems() {
        return this.rawItems;
    }
    
    public void setRawItems(Set<RawItem> rawItems) {
        this.rawItems = rawItems;
    }




}


