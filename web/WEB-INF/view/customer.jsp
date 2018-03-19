<div class="modal fade" id="window_customer" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-blue fg-white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="frm_title">Create New Customer</h4>
            </div>

            <div class="modal-body">
                <form id="frm_customer" name="frm_customer">                     
                    <br>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">ID</label>
                            <div class="col-md-3">
                                <input class="col-md-3 form-control input-sm" type="text" name="custId" id="custId" value="0" readonly="" />
                                
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right"> <span class="fg-red ">*</span> Name</label>
                            <div class="col-md-6">
                                <input class=" form-control input-sm" type="text" name="name" id="name" placeholder="Customer Name"  data-validation="required"/>                               
                            </div>
                          
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right"><span class="fg-red ">*</span> Address</label>
                            <div class="col-md-4">
                                <input class="form-control input-sm " type="text" name="addressLine1" id="addressLine1" placeholder="Line 1" data-validation="required"/>
                                <span class="fg-red ">*</span>
                            </div>                                 
                            <div class="col-md-4">
                                <input class=" form-control input-sm" type="text" name="addressLine2" id="addressLine2" placeholder="Line 2" data-validation="required"/>
                                 <span class="fg-red ">*</span>
                            </div>
                           
                            
                        </div>    
                        
                    </div><!--Row-->

                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right"></label>
                            <div class="col-md-4">
                                <input class=" form-control input-sm" type="text" name="city" id="city" placeholder="City" data-validation="required"/>
                               
                            </div>                           
                            <div class="col-md-4">
                                <input class="form-control input-sm" type="text" name="postalCode" id="postalCode" placeholder="Postal Code"/>
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>

                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right"> <span class="fg-red ">*</span> Contact No</label>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="text" name="tel1" id="tel1" placeholder="Land Phone No 1" data-validation="required-tel"/>
                                 <span class="fg-red ">*</span>
                            </div>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="text" name="tel2" id="tel2" placeholder="Land Phone No 2" data-validation="tel"/>
                            </div>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="text" name="fax" id="fax" placeholder="Fax No"/>
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">E-mail(Optional)</label>
                            <div class="col-md-6">
                                <input class="form-control input-sm" type="email" data-validation="email" name="email" id="email" placeholder="Abc@abc.com"/>
                            </div>

                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Contact Person</label>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="text" name="cperson" id="cperson" placeholder="Name"/>
                            </div>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="tel" name="mobile" id="mobile" placeholder="Contact No"/>
                            </div>                                               
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Remarks</label>
                            <div class="col-md-6">
                                <input class="form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Remarks"/>
                            </div>


                        </div>                                           
                    </div><!--Row-->
                    <br>
                     <div class="row">
                        <div class="form-group">
                             <label class="col-md-3 text-right"></label>
                             <div class="col-md-8">
                                 <span class="col-md-6 text-right fg-red">*Indicate required fields.</span>        
                            </div>
                                             
                        </div>                                           
                    </div><!--Row-->
                  


                </form>

            </div>
            <div class="modal-footer bg-grayLighter">
                <button class="btn btn-success" id="btn_create" name="btn_create">Create</button>
                <button type="reset" class="btn btn-default" id="btn_reset" name="btn_reset">Reset</button>
            </div>
        </div>
    </div>


</div>

    