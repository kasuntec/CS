<div class="modal fade" id="window_worker" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-blue fg-white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="frm_title">Create New Worker</h4>
            </div>

            <div class="modal-body">
                <form id="frm_worker" name="frm_worker">
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Worker ID</label>
                            <div class="col-md-3">
                                <input class="col-md-3 form-control input-sm" type="text" name="id" id="id" value="0" readonly="" />
                                
                            </div>
                        </div>                           
                    </div><!--Row-->
                    <br>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">ETF ID (If any)</label>
                            <div class="col-md-3">
                                <input class="col-md-3 form-control input-sm" type="text" name="etfNo" id="etfNo" value="0" placeholder="Enter ETF No"  />
                                
                            </div>
                        </div>                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Name</label>
                            <div class="col-md-6">
                                <input class=" form-control input-sm" type="text" name="fullName" id="fullName" placeholder="Worker Name"  data-validation="required"/>
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Designation</label>
                            <div class="col-md-6">
                                <input class=" form-control input-sm" type="text" name="designation" id="designation" placeholder="Designation"  data-validation="required"/>
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                  
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Contact No</label>
                            <div class="col-md-3">
                                <input class="form-control input-sm" type="text" name="mobile" id="mobile" placeholder="Mobile No (Eg-07X-XXXXXXX)"/>
                            </div>                                                                        
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                   


                </form>

            </div>
            <div class="modal-footer bg-grayLighter">
                <button class="btn btn-success" id="btn_create" name="btn_create">Save</button>
                <button type="reset" class="btn btn-default" id="btn_reset" name="btn_reset">Reset</button>
            </div>
        </div>
    </div>


</div>

    