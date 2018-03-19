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
    $('#invoice').select2({
        data: invoiceNoList,
        placeholder: 'Select invoice',
    });
    $('#invoice').select2("val", "");



    //set texboxes to select all text when focus 
    $("input:text").focus(function () {
        $(this).select();
    });


    //View Saved Jo
    if (action == "View") {//check page mood
        //load job
        loadPayment(payment);
        // loadInvoiceDetails(payment.payId);
    }

});

$("#invoice").change(function () {
    var invoice = $("#invoice").val();

    if (invoice != null) {
        loadInvoiceDetails(invoice);
    }


});

$("#btnPrint").click(function () {
    print();
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

function loadInvoiceDetails(invoice) {
    var data = "invNo=" + invoice;

    $.ajax({
        type: 'GET',
        url: "../invoice/loadDetails.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);


            var invoice = JSON.parse(resp.invoice)
            $('#customer').html(invoice.customer);
            $('#invTotal').html(invoice.netTotal);
            $('#balance').html(invoice.balance);
            $('#amount').html(invoice.balance);


        }

    });
}








/*---------------------------------------Save Jobs----------------------------------------*/
function savePayment() {

    if ($("#payId").val().trim() == "New") {
        $("#payId").val(0);
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

            $('#payId').val(resp.id);
            $("#btnSave").removeClass('disabled');
            //show user msg            
            showMsg(resp.msg, resp.status);
            //show updated invoice details
            var invoice = $("#invoice").val();
            loadInvoiceDetails(invoice);

        }

    });


}



/*---------------------------load payemnt by id---------------------------------------*/
function loadPayment(payment) {

    //head
    $('#payId').val(payment.payId);
    $('#invoice').select2("val", payment.invoice);
    $('#invoice').trigger('change');
    $('#date').val(payment.date);
    $('#payType').val(payment.payType);
    $('#refNo').val(payment.refNo);
    $('#amount').val(payment.amount);

}

//check is paamount exceed
function checkIsExceed() {
    var payAmount = parseFloat($('#amount').val());
    var balance = parseFloat($('#balance').html());
    

    if (payAmount <= balance) {
        return true;
    } else {
        showNotification('info', 'Payment amount cannot exceed invoice balance');
        return false;

    }
}


function print() {
    var id = $('#payId').val();
    var printFile = "inv_payment_print";

    window.open('../print.htm?print_file=' + printFile + '&id=' + id);
}
