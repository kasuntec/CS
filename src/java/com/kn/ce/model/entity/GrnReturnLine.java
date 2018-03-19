package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * GrnReturnLine generated by hbm2java
 */
@Entity
@Table(name="grn_return_line"
    ,catalog="creative_edge"
)
public class GrnReturnLine  implements java.io.Serializable {


     private Long id;
     private GrnReturn grnReturn;
     private RawItem rawItem;
     private double qty;
     private double price;

    public GrnReturnLine() {
    }

    public GrnReturnLine(GrnReturn grnReturn, RawItem rawItem, double qty, double price) {
       this.grnReturn = grnReturn;
       this.rawItem = rawItem;
       this.qty = qty;
       this.price = price;
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
    @JoinColumn(name="rtn_no", nullable=false)
    public GrnReturn getGrnReturn() {
        return this.grnReturn;
    }
    
    public void setGrnReturn(GrnReturn grnReturn) {
        this.grnReturn = grnReturn;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id", nullable=false)
    public RawItem getRawItem() {
        return this.rawItem;
    }
    
    public void setRawItem(RawItem rawItem) {
        this.rawItem = rawItem;
    }

    
    @Column(name="qty", nullable=false, precision=10)
    public double getQty() {
        return this.qty;
    }
    
    public void setQty(double qty) {
        this.qty = qty;
    }

    
    @Column(name="price", nullable=false, precision=10)
    public double getPrice() {
        return this.price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }




}

