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
 * FinishedItemCategory generated by hbm2java
 */
@Entity
@Table(name="finished_item_category"
    ,catalog="creative_edge"
)
public class FinishedItemCategory  implements java.io.Serializable {


     private String category;
     private Set<FinishedItem> finishedItems = new HashSet<FinishedItem>(0);

    public FinishedItemCategory() {
    }

	
    public FinishedItemCategory(String category) {
        this.category = category;
    }
    public FinishedItemCategory(String category, Set<FinishedItem> finishedItems) {
       this.category = category;
       this.finishedItems = finishedItems;
    }
   
     @Id 

    
    @Column(name="category", unique=true, nullable=false, length=50)
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="finishedItemCategory")
    public Set<FinishedItem> getFinishedItems() {
        return this.finishedItems;
    }
    
    public void setFinishedItems(Set<FinishedItem> finishedItems) {
        this.finishedItems = finishedItems;
    }




}

