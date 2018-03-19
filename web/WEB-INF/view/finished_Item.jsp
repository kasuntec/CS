<div class="modal fade" id="window_item" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-blue fg-white">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="frm_title">Create New Raw Item</h4>
            </div>

            <div class="modal-body">
                <form id="frm_item" name="frm_customer">
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Item Id</label>
                            <div class="col-md-3">
                                <input class="col-md-3 form-control input-sm" type="text" name="itemId" id="itemId" value="0" readonly="" />

                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Description</label>
                            <div class="col-md-6">
                                <input class=" form-control input-sm" type="text" name="description" id="description" placeholder="Item Description"  data-validation="required"/>
                            </div>
                        </div>                                           
                    </div><!--Row-->
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Category</label>
                            <div class="col-md-4">
                                <select class="form-control input-sm" id="category" name="finishedItemCategory.category"></select>
                            </div>
                        </div>
                    </div> <!--Row-->     
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Unit of Measure</label>
                            <div class="col-md-4">
                                <select class="form-control input-sm" id="uom" name="uom.uom"></select></div>
                        </div>
                    </div> <!--Row-->                                          
                  
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Cost</label>
                            <div class="col-md-6">
                                <input class="form-control input-sm" type="text" data-validation="required" name="unitPrice" id="unitPrice" placeholder="0.00"/>
                            </div>

                        </div>                                           
                    </div><!--Row-->
                    <br/>                   
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <label class="col-md-3 text-right">Remarks</label>
                            <div class="col-md-6">
                                <input class="form-control input-sm" type="text" name="remarks" id="remarks" placeholder="Remarks"/>
                            </div>


                        </div>                                           
                    </div><!--Row-->


                </form>

            </div>
            <div class="modal-footer bg-grayLighter">
                <button class="btn btn-success" id="btn_create" name="btn_create">Save</button>
                <button type="reset" class="btn btn-default" id="btn_reset" name="btn_reset">Reset</button>
            </div>
        </div>
    </div>


</div>

