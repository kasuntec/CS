<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="frm_payment" name="frm_payment">
    <div class="row">       
        <div class="col-md-12">
            <!--Row-->
            <div class="row">
                <div class="form-group">
                    <label class="col-md-4 text-right">Payment No</label>
                    <div class="col-md-3">
                        <input type="text" class="form-control col-md-3 input-sm" id="id" name="id" readonly="" value="New"/>                                        
                    </div>
                </div>
                <br>
            </div>

            <div class="row" >   
                <div class="form-group">
                    <label class="col-md-4 text-right">Invoice No</label>
                    <div class="col-md-6">
                        <select id="grn" name="grn" class="form-control input-sm" data-validation="required">                                           

                        </select>                                        
                    </div>
                </div> 
                <br>
            </div>
            
             <div class="row">
                <div class="form-group">
                    <label class="col-md-4 text-right">Date </label>
                    <div class="col-md-5">
                        <input type="text" class="form-control col-md-3 input-sm" id="date" name="date" placeholder="yyyy-MM-dd" data-validation="required"/>                                        
                    </div>
                </div>
                <br>
            </div>

            <div class="row" >   
                <div class="form-group">
                    <label class="col-md-4 text-right">Pay Media</label>
                    <div class="col-md-5">
                        <select id="payType" name="payType" class="form-control input-sm">                                           
                            <option>Card</option>
                            <option>Cheque</option>
                            <option>Cash</option>
                        </select>                                        
                    </div>
                </div> 
                <br>
            </div>



            <!--Row-->
            <div class="row" id="rowRefNo">
                <div class="form-group">
                    <label class="col-md-4 text-right" id="lbl_RefNo">Ref No</label>
                    <div class="col-md-6">
                        <input type="text"  class="col-md-5 form-control input-sm" id="refNo" name="refNo" placeholder="Enter Card /Cheque No" />                                        
                    </div>
                </div>
                <br>
            </div>
         
             <!--Row-->
            <div class="row">
                <div class="form-group">
                    <label class="col-md-4 text-right">Pay Amount</label>
                    <div class="col-md-5">
                        <input type="text" onkeypress='return isNumberKey(event)' class="col-md-3 form-control input-sm" id="amount" name="amount" placeholder="Amount" data-validation="required"/>                                        
                    </div>
                </div>
                <br>
            </div>

            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-8">

                    <button id="btnSave"  type="button" class="btn btn-success"><i class="fa fa-floppy-o " aria-hidden="true"></i> Save</button>
                    <button id="btnCancel"  type="button" class="btn btn-danger"><i class="fa fa-close" aria-hidden="true"></i> Cancel</button>
                </div>


            </div>
        </div>
    </div>





</form>
        
        

