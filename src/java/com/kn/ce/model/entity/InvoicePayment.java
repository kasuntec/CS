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
 * InvoicePayment generated by hbm2java
 */
@Entity
@Table(name="invoice_payment"
    ,catalog="creative_edge"
)
public class InvoicePayment  implements java.io.Serializable {


     private Long payId;
     private Invoice invoice;
     private User user;
     private Date date;
     private String payType;
     private double amount;
     private String refNo;

    public InvoicePayment() {
    }

	
    public InvoicePayment(Invoice invoice, User user, Date date, String payType, double amount) {
        this.invoice = invoice;
        this.user = user;
        this.date = date;
        this.payType = payType;
        this.amount = amount;
    }
    public InvoicePayment(Invoice invoice, User user, Date date, String payType, double amount, String refNo) {
       this.invoice = invoice;
       this.user = user;
       this.date = date;
       this.payType = payType;
       this.amount = amount;
       this.refNo = refNo;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="pay_id", unique=true, nullable=false)
    public Long getPayId() {
        return this.payId;
    }
    
    public void setPayId(Long payId) {
        this.payId = payId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="inv_no", nullable=false)
    public Invoice getInvoice() {
        return this.invoice;
    }
    
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="username", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable=false, length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="pay_type", nullable=false, length=10)
    public String getPayType() {
        return this.payType;
    }
    
    public void setPayType(String payType) {
        this.payType = payType;
    }

    
    @Column(name="amount", nullable=false, precision=20)
    public double getAmount() {
        return this.amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }

    
    @Column(name="ref_no", length=10)
    public String getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }




}


