package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * RawItemStock generated by hbm2java
 */
@Entity
@Table(name="raw_item_stock"
    ,catalog="creative_edge"
)
public class RawItemStock  implements java.io.Serializable {


     private Long id;
     private RawItem rawItem;
     private Date date;
     private long refNo;
     private String type;
     private double qty;

    public RawItemStock() {
    }

    public RawItemStock(RawItem rawItem, Date date, long refNo, String type, double qty) {
       this.rawItem = rawItem;
       this.date = date;
       this.refNo = refNo;
       this.type = type;
       this.qty = qty;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=false)
    public RawItem getRawItem() {
        return this.rawItem;
    }
    
    public void setRawItem(RawItem rawItem) {
        this.rawItem = rawItem;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable=false, length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="ref_no", nullable=false)
    public long getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(long refNo) {
        this.refNo = refNo;
    }

    
    @Column(name="type", nullable=false, length=10)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="qty", nullable=false, precision=10)
    public double getQty() {
        return this.qty;
    }
    
    public void setQty(double qty) {
        this.qty = qty;
    }




}

