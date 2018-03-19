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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FinishedItem generated by hbm2java
 */
@Entity
@Table(name="finished_item"
    ,catalog="creative_edge"
)
public class FinishedItem  implements java.io.Serializable {


     private Long itemId;
     private FinishedItemCategory finishedItemCategory;
     private Uom uom;
     private String description;
     private double unitPrice;
     private String remarks;
     private Set<JobLine> jobLines = new HashSet<JobLine>(0);
     private Set<CreditNoteLine> creditNoteLines = new HashSet<CreditNoteLine>(0);
     private Set<InvoiceLine> invoiceLines = new HashSet<InvoiceLine>(0);

    public FinishedItem() {
    }

	
    public FinishedItem(FinishedItemCategory finishedItemCategory, Uom uom, String description, double unitPrice) {
        this.finishedItemCategory = finishedItemCategory;
        this.uom = uom;
        this.description = description;
        this.unitPrice = unitPrice;
    }
    public FinishedItem(FinishedItemCategory finishedItemCategory, Uom uom, String description, double unitPrice, String remarks, Set<JobLine> jobLines, Set<CreditNoteLine> creditNoteLines, Set<InvoiceLine> invoiceLines) {
       this.finishedItemCategory = finishedItemCategory;
       this.uom = uom;
       this.description = description;
       this.unitPrice = unitPrice;
       this.remarks = remarks;
       this.jobLines = jobLines;
       this.creditNoteLines = creditNoteLines;
       this.invoiceLines = invoiceLines;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="item_id", unique=true, nullable=false)
    public Long getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category", nullable=false)
    public FinishedItemCategory getFinishedItemCategory() {
        return this.finishedItemCategory;
    }
    
    public void setFinishedItemCategory(FinishedItemCategory finishedItemCategory) {
        this.finishedItemCategory = finishedItemCategory;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="uom", nullable=false)
    public Uom getUom() {
        return this.uom;
    }
    
    public void setUom(Uom uom) {
        this.uom = uom;
    }

    
    @Column(name="description", nullable=false)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="unit_price", nullable=false, precision=10)
    public double getUnitPrice() {
        return this.unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    
    @Column(name="remarks")
    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="finishedItem")
    public Set<JobLine> getJobLines() {
        return this.jobLines;
    }
    
    public void setJobLines(Set<JobLine> jobLines) {
        this.jobLines = jobLines;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="finishedItem")
    public Set<CreditNoteLine> getCreditNoteLines() {
        return this.creditNoteLines;
    }
    
    public void setCreditNoteLines(Set<CreditNoteLine> creditNoteLines) {
        this.creditNoteLines = creditNoteLines;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="finishedItem")
    public Set<InvoiceLine> getInvoiceLines() {
        return this.invoiceLines;
    }
    
    public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }




}


