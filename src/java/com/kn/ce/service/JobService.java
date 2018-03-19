/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kn.ce.service;

import com.kn.ce.dao.GenericDao;
import com.kn.ce.model.entity.AdvancePayment;
import com.kn.ce.model.entity.Customer;
import com.kn.ce.model.entity.Job;
import com.kn.ce.model.entity.JobLine;
import com.kn.ce.model.system.JobStatus;
import com.kn.ce.model.system.Where;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.print.attribute.standard.JobState;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kasun
 */
@Service("jobService")
public class JobService {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private GenericDao genericDao;
    @Autowired
    private AdvancePaymentService advancePaymentService;

    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }

    public void setAdvancePaymentService(AdvancePaymentService advancePaymentService) {
        this.advancePaymentService = advancePaymentService;
    }

    /*-----------------------------
    End of the Dependancy Injection 
    -----------------------------*/

 /*-----------------------------
    Save method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long save(Job job, AdvancePayment advancePayment) throws Exception {
        //save head objects
        Long id = genericDao.save(job);
        //save advance
        if (advancePayment != null) {//check is not null
            advancePayment.setJob(job);
            genericDao.save(advancePayment);
        }

        //save lines objects
        Iterator<JobLine> iterator = job.getJobLines().iterator();
        while (iterator.hasNext()) {
            JobLine jobLine = iterator.next();
            jobLine.setJob(job);//set saved job
            //save
            genericDao.save(jobLine);

        }
        return id;
    }

    /*-----------------------------
    Update method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long update(Job job) throws Exception {
        //update head objects
        Long id = genericDao.update(job);
        //save old lines
        genericDao.deleteLines(JobLine.class, job.getJobNo(), "job.jobNo");

        //save lines objects
        Iterator<JobLine> iterator = job.getJobLines().iterator();
        while (iterator.hasNext()) {
            JobLine jobLine = iterator.next();
            jobLine.setJob(job);//set saved job
            //save
            genericDao.save(jobLine);

        }
        return id;
    }

    /*-----------------------------
    Get method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Job get(long id) throws Exception {
        Job job = (Job) genericDao.get(id, Job.class);
        //calculations
        job.setAmount(getAmount(job));//amount
        job.setAdvance(advancePaymentService.getByJob(job.getJobNo()).getAmount());//advance
        //init depandancy
        Hibernate.initialize(job.getJobLines());
        Hibernate.initialize(job.getCustomer());
        return job;
    }

    /*-----------------------------
    List method 
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List<Job> list(String status) throws Exception {
        //define return varible
        List<Job> list = new ArrayList<Job>();
        Iterator<Job> it = null;
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("status", status);
        wheres.add(where);

        if (status.equals(JobStatus.ALL)) {//check status
            //load all jobs
            it = genericDao.list(Job.class).iterator();
        } else {
            //list job by status
            it = genericDao.list(Job.class, wheres).iterator();

        }

        //init depandancy on List
        while (it.hasNext()) {//loop
            Job job = it.next();
            Hibernate.initialize(job.getCustomer());
            
            //calculations
            job.setAmount(getAmount(job));//amount
            AdvancePayment advancePayment = advancePaymentService.getByJob(job.getJobNo());
            if(advancePayment!=null){
                job.setAdvance(advancePayment.getAmount());////check advance is not a null
            }
            else{
                //when advance null
                 job.setAdvance(0.00);
            }
            
            list.add(job);

        }

        return list;

    }
    
    /*-----------------------------
    List method for many status
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List<Job> list(String status1,String status2) throws Exception {
       
        //set where      
          Where where1 = new Where("status", status1);
          Where where2 = new Where("status", status2);
        
         return genericDao.listByOR(Job.class, where1,where2);
    }

    /*-----------------------------
    List method by customer
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public List list(Customer customer) throws Exception {
        //set where
        List<Where> wheres = new ArrayList<Where>();
        Where where = new Where("customer", customer);
        wheres.add(where);

        return genericDao.list(Job.class, wheres);
    }

    /*-----------------------------
    Delete method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public long delete(Job job) throws Exception {
        return genericDao.delete(job);
    }

    /*-----------------------------
    Get Total method
    -----------------------------*/
    @Transactional(rollbackFor = Exception.class)
    public Double getAmount(Job job) throws Exception {
        Double amount = 0.00;
        Iterator<JobLine> iterator = job.getJobLines().iterator();
        while (iterator.hasNext()) {//loop job lines
            JobLine jobLine = iterator.next();
            amount += (jobLine.getUnitPrice() * jobLine.getWidth() * jobLine.getHeight() * jobLine.getQty());
        }

        return amount;
    }

}
