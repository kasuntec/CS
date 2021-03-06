package com.kn.ce.model.entity;
// Generated Oct 17, 2016 9:55:50 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Grn generated by hbm2java
 */
@Entity
@Table(name="grn"
    ,catalog="creative_edge"
)
public class Grn  implements java.io.Serializable {


     private Long grnNo;
     private Supplier supplier;
     private User user;
     private boolean tax;
     private String refNo;
     private Date date;
     private String status;
     private String remarks;
     private double discount;
     private double vatRate;
     private double nbtRate;     
      private double grossTotal;
       private double vatValue;
     private double nbtValue;
     private Set<GrnLine> grnLines = new HashSet<GrnLine>(0);
     private Set<GrnPayment> grnPayments = new HashSet<GrnPayment>(0);
     private Set<GrnReturn> grnReturns = new HashSet<GrnReturn>(0);
     
     //calculation fields
    
    
     private double payAmount;
     

    public Grn() {
    }

	
    public Grn(Supplier supplier, User user, boolean tax, String refNo, Date date, String status, String remarks, double discount, double vatRate, double nbtRate) {
        this.supplier = supplier;
        this.user = user;
        this.tax = tax;
        this.refNo = refNo;
        this.date = date;
        this.status = status;
        this.remarks = remarks;
        this.discount = discount;
        this.vatRate = vatRate;
        this.nbtRate = nbtRate;
    }
    public Grn(Supplier supplier, User user, boolean tax, String refNo, Date date, String status, String remarks, double discount, double vatRate, double nbtRate, Set<GrnLine> grnLines, Set<GrnPayment> grnPayments, Set<GrnReturn> grnReturns) {
       this.supplier = supplier;
       this.user = user;
       this.tax = tax;
       this.refNo = refNo;
       this.date = date;
       this.status = status;
       this.remarks = remarks;
       this.discount = discount;
       this.vatRate = vatRate;
       this.nbtRate = nbtRate;
       this.grnLines = grnLines;
       this.grnPayments = grnPayments;
       this.grnReturns = grnReturns;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="grn_no", unique=true, nullable=false)
    public Long getGrnNo() {
        return this.grnNo;
    }
    
    public void setGrnNo(Long grnNo) {
        this.grnNo = grnNo;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sup_id", nullable=false)
    public Supplier getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="username", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    @Column(name="tax", nullable=false)
    public boolean isTax() {
        return this.tax;
    }
    
    public void setTax(boolean tax) {
        this.tax = tax;
    }

    
    @Column(name="ref_no", nullable=false, length=20)
    public String getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable=false, length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="status", nullable=false, length=10)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name="remarks", nullable=false)
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    @Column(name="discount", nullable=false, precision=10, scale = 2)
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    
    @Column(name="vat_rate", nullable=false, precision=10, scale=7)
    public double getVatRate() {
        return this.vatRate;
    }
    
    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    
    @Column(name="nbt_rate", nullable=false, precision=10, scale=7)
    public double getNbtRate() {
        return this.nbtRate;
    }
    
    public void setNbtRate(double nbtRate) {
        this.nbtRate = nbtRate;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="grn")
    public Set<GrnLine> getGrnLines() {
        return this.grnLines;
    }
    
    public void setGrnLines(Set<GrnLine> grnLines) {
        this.grnLines = grnLines;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="grn")
    public Set<GrnPayment> getGrnPayments() {
        return this.grnPayments;
    }
    
    public void setGrnPayments(Set<GrnPayment> grnPayments) {
        this.grnPayments = grnPayments;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="grn")
    public Set<GrnReturn> getGrnReturns() {
        return this.grnReturns;
    }
    
    public void setGrnReturns(Set<GrnReturn> grnReturns) {
        this.grnReturns = grnReturns;
    }

    @Column(name="gross_total", nullable=false, precision=10, scale = 2)
    public double getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }

     @Column(name="vat_value", nullable=false, precision=10, scale = 7)
    public double getVatValue() {
        return vatValue;
    }

    public void setVatValue(double vatValue) {
        this.vatValue = vatValue;
    }

     @Column(name="nbt_value", nullable=false, precision=10, scale = 7)
    public double getNbtValue() {
        return nbtValue;
    }

    public void setNbtValue(double nbtValue) {
        this.nbtValue = nbtValue;
    }

    @Transient
    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
    
    




}


