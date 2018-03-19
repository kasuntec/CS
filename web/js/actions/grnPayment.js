/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {

    //---------------------init form-------------------------------
    //set date picker
    $('#date').datepicker({dateFormat: "yy-mm-dd"});


    //--------fill select boxes-------
    //item
    $('#grn').select2({
        data: grnNoList,
        placeholder: 'Select GRN',
    });
    $('#grn').select2("val", "");



    //set texboxes to select all text when focus 
    $("input:text").focus(function () {
        $(this).select();
    });


    //View Saved Jo
    if (action == "View") {//check page mood
        //load job
        loadPayment(payment);       
     
    }

});

$("#grn").change(function () {
    var grn = $("#grn").val();

    if (grn != null) {
        loadGrnDetails(grn);
    }


});


//btnSave Click Event
$("#btnSave").click(function () {
    var form = $("#frm_payment");

    if (validateForm(form)) {//validate form
        
        if(checkIsExceed()){//check is exceed
            $("#btnSave").addClass('disabled');
             savePayment();
        }
       

    }

});






/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/


/*-----------------------Load Invoice---------------*/

function loadGrnDetails(grn) {
    var data = "grnNo=" + grn;

    $.ajax({
        type: 'GET',
        url: "../grn/loadDetails.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);


            var grn = JSON.parse(resp.grn)
            $('#supplier').html(grn.supplierName);
            $('#GRNTotal').html(grn.netTotal);
            $('#balance').html(grn.duebalance);
          

        }

    });
}








/*---------------------------------------Save Jobs----------------------------------------*/
function savePayment() {

    if ($("#id").val().trim() == "New") {
        $("#id").val(0);
    }
    //serialize form
    var formData = $('#frm_payment').serializeArray(); //get header data 
    var payment = JSON.stringify(createJONObject(formData));//convert to JOSn
    var data = "payment=" + payment;

    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {

            var resp = JSON.parse(data);

            $('#id').val(resp.id);
            $("#btnSave").removeClass('disabled');
            //show user msg            
            showMsg(resp.msg, resp.status);
            //show updated invoice details
            var grn = $("#grn").val();
            loadGrnDetails(grn);

        }

    });


}



/*---------------------------load payemnt by id---------------------------------------*/
function loadPayment(payment) {

    //head
    $('#id').val(payment.id);
    $('#grn').select2("val", payment.grn);
    $('#grn').trigger('change');
    $('#date').val(payment.date);
    $('#payType').val(payment.payType);
    $('#refNo').val(payment.refNo);
    $('#amount').val(payment.amount);

}

//check is paamount exceed
function checkIsExceed() {
    var payAmount = $('#amount').val();
    var balance = $('#balance').html();
    
    if(balance==0){
         showNotification('info', 'GRN was paid');
        return false;
    }

    if (payAmount <= balance) {
        return true;
    } else {
        showNotification('info', 'Payment amount cannot exceed GRN balance');
        return false;

    }
}

function print() {
    var id = $('#id').val();
    var printFile = "supp_payment_print";

    window.open('../print.htm?print_file=' + printFile + '&id=' + id);
}

