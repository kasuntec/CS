package com.kn.ce.model.entity;
// Generated Oct 1, 2016 10:00:22 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="creative_edge"
)
public class User  implements java.io.Serializable {


     private String username;
     private Location location;
     private UserRole userRole;
     private String fullName;
     private String email;
     private String password;
     private boolean active;
     private boolean login;
     private Set<GrnReturn> grnReturns = new HashSet<GrnReturn>(0);
     private Set<GrnPayment> grnPayments = new HashSet<GrnPayment>(0);
     private Set<Grn> grns = new HashSet<Grn>(0);
     private Set<Job> jobs = new HashSet<Job>(0);
     private Set<InvoicePayment> invoicePayments = new HashSet<InvoicePayment>(0);
     private Set<Invoice> invoices = new HashSet<Invoice>(0);
     private Set<CreditNote> creditNotes = new HashSet<CreditNote>(0);
     private Set<RawItemIssue> rawItemIssues = new HashSet<RawItemIssue>(0);

    public User() {
    }

	
    public User(String username, Location location, UserRole userRole, String fullName, String email, String password, boolean active, boolean login) {
        this.username = username;
        this.location = location;
        this.userRole = userRole;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.active = active;
        this.login = login;
    }
    public User(String username, Location location, UserRole userRole, String fullName, String email, String password, boolean active, boolean login, Set<GrnReturn> grnReturns, Set<GrnPayment> grnPayments, Set<Grn> grns, Set<Job> jobs, Set<InvoicePayment> invoicePayments, Set<Invoice> invoices, Set<CreditNote> creditNotes, Set<RawItemIssue> rawItemIssues) {
       this.username = username;
       this.location = location;
       this.userRole = userRole;
       this.fullName = fullName;
       this.email = email;
       this.password = password;
       this.active = active;
       this.login = login;
       this.grnReturns = grnReturns;
       this.grnPayments = grnPayments;
       this.grns = grns;
       this.jobs = jobs;
       this.invoicePayments = invoicePayments;
       this.invoices = invoices;
       this.creditNotes = creditNotes;
       this.rawItemIssues = rawItemIssues;
    }
   
     @Id 

    
    @Column(name="username", unique=true, nullable=false, length=20)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="location_id", nullable=false)
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_role", nullable=false)
    public UserRole getUserRole() {
        return this.userRole;
    }
    
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    
    @Column(name="full_name", nullable=false)
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    
    @Column(name="email", nullable=false, length=100)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="password", nullable=false, length=100)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="active", nullable=false)
    public boolean isActive() {
        return this.active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }

    
    @Column(name="login", nullable=false)
    public boolean isLogin() {
        return this.login;
    }
    
    public void setLogin(boolean login) {
        this.login = login;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<GrnReturn> getGrnReturns() {
        return this.grnReturns;
    }
    
    public void setGrnReturns(Set<GrnReturn> grnReturns) {
        this.grnReturns = grnReturns;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<GrnPayment> getGrnPayments() {
        return this.grnPayments;
    }
    
    public void setGrnPayments(Set<GrnPayment> grnPayments) {
        this.grnPayments = grnPayments;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Grn> getGrns() {
        return this.grns;
    }
    
    public void setGrns(Set<Grn> grns) {
        this.grns = grns;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Job> getJobs() {
        return this.jobs;
    }
    
    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<InvoicePayment> getInvoicePayments() {
        return this.invoicePayments;
    }
    
    public void setInvoicePayments(Set<InvoicePayment> invoicePayments) {
        this.invoicePayments = invoicePayments;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<Invoice> getInvoices() {
        return this.invoices;
    }
    
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<CreditNote> getCreditNotes() {
        return this.creditNotes;
    }
    
    public void setCreditNotes(Set<CreditNote> creditNotes) {
        this.creditNotes = creditNotes;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set<RawItemIssue> getRawItemIssues() {
        return this.rawItemIssues;
    }
    
    public void setRawItemIssues(Set<RawItemIssue> rawItemIssues) {
        this.rawItemIssues = rawItemIssues;
    }




}


