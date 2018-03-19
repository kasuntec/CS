package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


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
 * Invoice generated by hbm2java
 */
@Entity
@Table(name="invoice"
    ,catalog="creative_edge"
)
public class Invoice  implements java.io.Serializable {


     private Long invNo;
     private Job job;
     private User user;
     private Date date;   
     private String type;
     private String status;
     private String remarks;     
     private double discount;    
     private double grossTotal;    
     private Set<CreditNote> creditNotes = new HashSet<CreditNote>(0);
     private Set<InvoiceLine> invoiceLines = new HashSet<InvoiceLine>(0);
     private Set<InvoicePayment> invoicePayments = new HashSet<InvoicePayment>(0);
     
     //calculation fields  
     private double payAmount;
     

    public Invoice() {
    }

	
    public Invoice(Job job, User user, Date date, String type, String status, double grossTotal, double discount) {
        this.job = job;
        this.user = user;
        this.date = date;
        this.type = type;
        this.status = status;
        this.grossTotal = grossTotal;
        this.discount = discount;
      
    }
    public Invoice(Job job, User user, Date date,String type, String status, String remarks, double grossTotal, double discount, Set<CreditNote> creditNotes, Set<InvoiceLine> invoiceLines, Set<InvoicePayment> invoicePayments) {
       this.job = job;
       this.user = user;
       this.date = date;     
       this.type = type;
       this.status = status;
       this.remarks = remarks;
       this.grossTotal = grossTotal;
       this.discount = discount;     
       this.creditNotes = creditNotes;
       this.invoiceLines = invoiceLines;
       this.invoicePayments = invoicePayments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="inv_no", unique=true, nullable=false)
    public Long getInvNo() {
        return this.invNo;
    }
    
    public void setInvNo(Long invNo) {
        this.invNo = invNo;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="job_no", nullable=false)
    public Job getJob() {
        return this.job;
    }
    
    public void setJob(Job job) {
        this.job = job;
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

   
    
    @Column(name="type", nullable=false, length=20)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="status", nullable=false, length=10)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name="remarks")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    @Column(name="gross_total", nullable=false, precision=10)
    public double getGrossTotal() {
        return this.grossTotal;
    }
    
    public void setGrossTotal(double grossTotal) {
        this.grossTotal = grossTotal;
    }

    
    @Column(name="discount", nullable=false, precision=10)
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }


@OneToMany(fetch=FetchType.LAZY, mappedBy="invoice")
    public Set<CreditNote> getCreditNotes() {
        return this.creditNotes;
    }
    
    public void setCreditNotes(Set<CreditNote> creditNotes) {
        this.creditNotes = creditNotes;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="invoice")
    public Set<InvoiceLine> getInvoiceLines() {
        return this.invoiceLines;
    }
    
    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="invoice")
    public Set<InvoicePayment> getInvoicePayments() {
        return this.invoicePayments;
    }
    
    public void setInvoicePayments(Set<InvoicePayment> invoicePayments) {
        this.invoicePayments = invoicePayments;
    }

    @Transient
    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
    
    




}


