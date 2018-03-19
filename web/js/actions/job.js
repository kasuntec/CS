/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Define Job line data table
var tbl_line = $('#tbl_line').DataTable({
    "paging": false,
    "ordering": false,
    "info": false,
    "searching": false,
    columns: [
        {title: "ID", "width": "1%"},
        {title: "Description", "width": "12%"},
        {title: "U-Price", "width": "6%", "class": "dt-right"},
        {title: "Width", "width": "6%", "class": "dt-right"},
        {title: "Height", "width": "6%", "class": "dt-right"},
        {title: "Qty", "width": "12%", "class": "dt-right"},
        {title: "Remarks", "width": "12%"},
        {title: "Amount", "width": "12%", "class": "dt-right"},
        {title: "", "width": "10%", "class": "dt-center"}
    ]
});

//advance save or not saved 
var isAdvaceSaved = false;



/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {

    //---------------------init form-------------------------------
    //set date picker
    $('#date').datepicker({dateFormat: "yy-mm-dd"});
    $('#delivryDate').datepicker({dateFormat: "yy-mm-dd"});

    //--------fill select boxes-------
    //item
    $('#itemId').select2({
        data: itemNameList,
        placeholder: 'Select Item',
    });
    $('#itemId').select2("val", "");

    //customer
    $('#customer').select2({
        placeholder: 'Select Customer',
        data: custNameList
    });
    $('#customer').select2("val", "");
    //--------End of the fill select boxes-------

    //set texboxes to select all text when focus 
    $("input:text").focus(function () {
        $(this).select();
    });


    //View Saved Jo
    if (action == "View") {//check page mood
        //load job
        loadJob(job, advancePayment);
    }

});


//btnAddItem Click Event
$("#btnAddItem").click(function () {
    var form = $("#frm_line");
    if (validateForm(form)) {
        addItem();
    }

    //show totals  
    showTotal();


});

//btnSave Click Event
$("#btnSave").click(function () {
    var form = $("#frm_job");

    if (validateForm(form)) {

        if (isAdvaceSaved) {//check is advance save for job
            $("#btnSave").addClass('disabled');
            saveJob();
        } else {
            //show error messge to add advance payment
            showNotification("info", "Please add a advance payment");
        }

    }

});

//item select change Event
$("#itemId").change(function () {
    var itemId = $("#itemId").val();
    if (itemId != null) {//check selected item is not null
        loadItem(itemId);

    }

});

$("#btnAdvance").click(function () {
    $("#window_advance").modal();
    $('#rowRefNo').hide();
    $("#payType").val("Cash");
    
    showBalance();
});

//Add New Customer button click event : show form
$("#btnAddCust").click(function () {
    $("#window_customer").modal();
    $("#customer").val("New");
});




//Advance rest form
$("#btn_adv_reset").click(function () {
    resetFrom($('#frm_advance'));
    $("#advId").val("New");
});

//advance save button
$("#btn_adv_save").click(function () {
    var frmAdv = $("#window_advance");
    if (validateForm(frmAdv)) {
        isAdvaceSaved = true;
    }

    $("#window_advance").modal('hide');
    showTotal();
    
    alert($("#total").html());
     //showBalance();
    
   
    
});

//advance pay media change event
$("#payType").change(function () {
    var payType = $("#payType").val().trim();
    if (payType == "Card") {
        $('#lbl_RefNo').html("Card No");
        $('#rowRefNo').show();
        $('#refNo').attr('placeholder', 'Enter Card No');

    } else if (payType == "Cheque") {
        $('#lbl_RefNo').html("Cheque No");
        $('#rowRefNo').show();
        $('#refNo').attr('placeholder', 'Enter Cheque No');
    } else {
        $('#rowRefNo').hide();
    }

});

//focus events
$("#itemId").focusout(function () {
    calculateLineAmount();
});
$("#unitPrice").focusin(function () {
    calculateLineAmount();
});
$("#unitPrice").keyup(function () {
    calculateLineAmount();
});
$("#width").focusin(function () {
    calculateLineAmount();
});
$("#width").keyup(function () {
    calculateLineAmount();
});
$("#height").focusin(function () {
    calculateLineAmount();
});
$("#height").keyup(function () {
    calculateLineAmount();
});
$("#qty").focusin(function () {
    calculateLineAmount();
});
$("#qty").keyup(function () {
    calculateLineAmount();
});

$("#btnPrint").click(function () {
    print();
});

$("#amount").keyup(function () {
    showBalance();
});




/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/


/*-----------------------Add item to table---------------*/

function addItem() {

    var itemId = $('#itemId').val();
    var itemDsc = $('#itemId').select2('data')[0];
    var unitPrice = $('#unitPrice').val();
    var width = $('#width').val();
    var height = $('#height').val();
    var qty = $('#qty').val();
    var remarks = $('#line_remarks').val();
    var amount = (width * height) * unitPrice * qty;
    var deleteIcon = "<a class='dicon' onclick='removeItem(this)'  style='cursor: pointer' data-id='0' ><i class='fa fa-times fa-2x fg-red' ></i></a>";

    var isNewRow = true;
    var rows = tbl_line.rows().data();//loop table
    $(rows).each(function (index) {
        var row = rows[index][0];
        if (itemId == row) {//check 
            //already added
            isNewRow = false;

        } else {
            //new page
            isNewRow = true;

        }
    });

    if (isNewRow) {
        var row = [itemId, itemDsc.text, unitPrice, width, height, qty, remarks, amount, deleteIcon];

        tbl_line.row.add(row).draw();

    } else {
        showNotification('info', "Item is already added")

    }



    //reset text boxes
    $("#frm_line").find("input[type='text']").val("");
    $("#frm_line").find("input[type='select']").val("Select");




}

/*-----------------------Remove Table Row---------------*/
function removeItem(row) {

    //remove table row inner funaction
    function removeRow() {
        var tr = $($(row).parent()).parent(); // get parent tr    
        tbl_line.row(tr).remove().draw(false);
    }


    //show total
    showTotal();

    showConfirmMsg('Confirm', 'Are you sure whant to remove?', removeRow);



}

/*-----------------------Show Job Total---------------*/
function showTotal() {
    var advPayment = $('#amount').val().trim() != "" ? parseInt($('#amount').val().trim()) : 0.00;//get adavance total   
    var rows = tbl_line.rows().data();

    var total = 0;
    $(rows).each(function () {
        //get values from table rows
        var unitPrice = this[2] != "" ? parseInt(this[2]) : 0.00;
        var height = this[3] != "" ? parseInt(this[3]) : 0.00;
        var width = this[4] != "" ? parseInt(this[4]) : 0.00;
        var qty = this[5] != "" ? parseInt(this[5]) : 0.00;

        total += (unitPrice * height * width * qty);
    });

//show total panel
    $('#total').html(total);
    $('#advance').html(advPayment);
    $('#balance').html(total - advPayment);
}




/*---------------------------------------Save Jobs----------------------------------------*/
function saveJob() {


    var jobFormData = $('#frm_job').serializeArray(); //get header data 


    var job = JSON.stringify(createJONObject(jobFormData));

    //advance
    var advance = "";
    if (isAdvaceSaved) {//check advance is saved
        var advFormData = $('#frm_advance').serializeArray(); //get header data 
        advance = JSON.stringify(createJONObject(advFormData));
    }



    var rows = tbl_line.rows().data();//get table data

    //convert tabale data to array and convert to JSON
    var lines = new Array();
    $(rows).each(function (index) {
        lines[index] = this.slice(0, 7);
    });

    var data = "job=" + job + "&lines=" + JSON.stringify(lines) + "&advance=" + advance;
//    console.info(data);

    //set custId list to table
    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);

            $('#jobNo').val(resp.id);
            $("#btnSave").removeClass('disabled');
            //show user msg
            showMsg(resp.msg, resp.status);


        }

    });


}



/*---------------------------load Job by job id---------------------------------------*/
function loadJob(job, advancePayment) {
    //job head
    $('#jobNo').val(job.jobNo);
    $('#status').val(job.status);
    $('#date').val(job.date);
    $('#customer').select2("val", job.customer);
    $('#customer').trigger('change');
    $('#delivryDate').val(job.delivryDate);
    $('#remarks').val(job.remarks);


    //advance
    $('#advId').val(advancePayment.advId);
    $('#payType').val(advancePayment.payType);
    $('#refNo').val(advancePayment.refNo);
    $('#amount').val(advancePayment.amount);

    //button disabaled
    if (job.status == "Invoiced" || job.status == "Closed") {
        $('#btnSave').prop("disabled", true);
        $('#btnAdvance').prop("disabled", true);

    } else {
        $('#btnSave').prop("disabled", false);
        $('#btnAdvance').prop("disabled", false);
    }

    if (advancePayment.advId != null || advancePayment.advId > 0) {
        isAdvaceSaved = true;
    }


    //job lines
    tbl_line.clear().draw();//clear table
    $(job.jobLines).each(function (index) {//loop lines
        var deleteIcon = "<a class='dicon' onclick='removeItem(" + index + ")'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";
        var jobLine = job.jobLines[index];//get line by line 
        var amount = jobLine.width * jobLine.height * jobLine.unitPrice * jobLine.qty;
        var row = [jobLine.itemId, jobLine.itemName, jobLine.unitPrice, jobLine.width, jobLine.height, jobLine.qty, jobLine.remarks, amount, deleteIcon];
        //add row to table
        tbl_line.row.add(row).draw();
    });

    //show total
    showTotal();

}
/*---------------------------load Finished Item Details---------------------------------------*/
function loadItem(itemId) {
    var data = "itemId=" + itemId;
    $.ajax({
        type: 'GET',
        data: data,
        url: "../finished_item/view.htm",
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);
            var item = JSON.parse(response.item);
            //set

            $("#unitPrice").val(item.unitPrice);
            $('#width').focus();
        }
    });
}

function calculateLineAmount() {
    var unitPrice = $('#unitPrice').val().trim() != "" ? parseInt($('#unitPrice').val().trim()) : 0.00;
    var width = $('#width').val().trim() != "" ? parseInt($('#width').val().trim()) : 0.00;
    var height = $('#height').val().trim() != "" ? parseInt($('#height').val().trim()) : 0.00;
    var qty = $('#qty').val().trim() != "" ? parseInt($('#qty').val().trim()) : 0.00;



    var lineAmt = unitPrice * width * height * qty;
    $('#line_amount').html(lineAmt);
}


function print() {
    var id = $('#jobNo').val();
    var printFile = "job_print";

    window.open('../print.htm?print_file=' + printFile + '&id=' + id);
}

function showBalance(){
    var jobTotal=$("#total").html();
    $('#jobValue').val(jobTotal);
     var advpay=0.0;
    if($('#amount').val()!=0){
         var advpay=parseFloat($('#amount').val())
    }
    else{
        
    }
   
    
   var bal=jobTotal-advpay;
   $('#bal').val(bal);
   
}
