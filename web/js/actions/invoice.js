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
        {title: "Amount", "width": "12%", "class": "dt-right"},
        {title: "", "width": "10%", "class": "dt-center"}
    ]
});




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
    $('#itemId').select2({
        data: itemNameList,
        placeholder: 'Select Item',
    });
    $('#itemId').select2("val", "");

    //customer
    $('#job').select2({
        placeholder: 'Select Job',
        data: jobList
    });
    $('#job').select2("val", "");
    //--------End of the fill select boxes-------

    //set texboxes to select all text when focus 
    $("input:text").focus(function () {
        $(this).select();
    });


    //View Saved Jo
    if (action == "View") {//check page mood
        //load job
        loadInvoice(invoice);
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
    var form = $("#frm_invoice");

    if (validateForm(form)) {
        $("#btnSave").addClass('disabled');
        saveInvoice();

    }

});

//item select change Event
$("#itemId").change(function () {
    var itemId = $("#itemId").val();
    if (itemId != null) {//check selected item is not null
        loadItem(itemId);

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

//load job details
$('#job').change(function () {
    console.info("Job");
    var jobNo = $('#job').val();
    if (jobNo != null) {//check is not null
        if ($('#invNo').val() == "New") {//check is new invoice(only Job item list load for new invoice )
            loadJobDetails(jobNo);
        }

    }

});

//discount key press
$('#discount').keyup(function () {
    showTotal();
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
    var amount = (width * height) * unitPrice * qty;
    var deleteIcon = "<a class='dicon' onclick='removeItem(this)'  style='cursor: pointer' data-id='0' ><i class='fa fa-times fa-2x fg-red' ></i></a>";

   
    var isNewRow = true;
    var rows = tbl_line.rows().data();//loop table
    $(rows).each(function (index) {
        var row = rows[index][0];
        console.info(rows);
        if (itemId == row) {//check 
            //already added
            isNewRow = false;

        } else {
            //new page
            isNewRow = true;

        }
    });
     

    if (isNewRow) {
        var row = [itemId, itemDsc.text, unitPrice, width, height, qty, amount, deleteIcon];
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

    var rows = tbl_line.rows().data();

    var grosTotal = 0;
    $(rows).each(function () {
        //get values from table rows
        var unitPrice = this[2] != "" ? parseInt(this[2]) : 0.00;
        var height = this[3] != "" ? parseInt(this[3]) : 0.00;
        var width = this[4] != "" ? parseInt(this[4]) : 0.00;
        var qty = this[5] != "" ? parseInt(this[5]) : 0.00;

        grosTotal += (unitPrice * height * width * qty);
    });

    //calculation
    var payAmount = $('#payAmount').val().trim() == "" ? 0.0 : parseFloat($('#payAmount').html().trim());//get payment amount
    var advance = $('#advance').html().trim() == "" ? 0.0 : parseFloat($('#advance').html().trim());//get payment amount
    var discount = $('#discount').val().trim() == "" ? 0.0 : parseFloat($('#discount').val().trim());//get payment amount
    var netTotal = grosTotal - discount;
    var balance = netTotal - (advance + payAmount);

    //show total panel
    $('#grossTotal').html(grosTotal);
    $('#netTotal').html(netTotal);
    $('#balance').html(balance);



}




/*---------------------------------------Save Jobs----------------------------------------*/
function saveInvoice() {


    //create head to json
    var formData = $('#frm_invoice').serializeArray(); //get header data 
    var invoice = JSON.stringify(createJONObject(formData));//convert to JOSn
    var discount = $('#discount').val().trim() == "" ? 0.00 : parseFloat($('#discount').val());

    //convert tabale data to array and convert to JSON
    var rows = tbl_line.rows().data();//get table data
    var lines = new Array();
    $(rows).each(function (index) {
        lines[index] = this.slice(0, 7);
    });


    var data = "invoice=" + invoice + "&discount=" + discount + "&lines=" + JSON.stringify(lines);

    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);

            $('#job').val(resp.id);
            $("#btnSave").removeClass('disabled');
            //show user msg
            showMsg(resp.msg, resp.status);


        }

    });


}



/*---------------------------load Job by job id---------------------------------------*/
function loadInvoice(invoice) {

    //head
    $('#invNo').val(invoice.invNo);
    $('#job').select2("val", invoice.job);
    $('#job').trigger('change');
    $('#customer').html(invoice.customer);
    $('#date').val(invoice.date);
    $('#type').val(invoice.type);
    $('#status').val(invoice.status);
    $('#remarks').val(invoice.remarks);
    $('#grossTotal').html(invoice.grossTotal);
    $('#discount').val(invoice.discount);
    $('#advance').html(invoice.advance);
    $('#payAmount').html(invoice.payAmount);



    //lines
    tbl_line.clear().draw();//clear table
    $(invoice.invoiceLines).each(function (index) {//loop lines
        var deleteIcon = "<a class='dicon' onclick='removeItem(" + index + ")'  style='cursor: pointer' data-id='0' ><i class='fa fa-times fa-2x fg-red' ></i></a>";
        var invoiceLine = invoice.invoiceLines[index];//get line by line 
        var amount = invoiceLine.width * invoiceLine.height * invoiceLine.unitPrice * invoiceLine.qty;
        var row = [invoiceLine.itemId, invoiceLine.itemName, invoiceLine.unitPrice, invoiceLine.width, invoiceLine.height, invoiceLine.qty, amount, deleteIcon];
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

function loadJobDetails(jobNo) {
    $("#img_load").html("<img src='/CreativeEdge/images/icons/loading.gif'/>"); //show loading img  
    var data = "jobNo=" + jobNo;

    $.ajax({
        type: 'GET',
        url: "../job/view_details.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);
            var job = JSON.parse(response.job);
            var advance = JSON.parse(response.advancePayment);

            //set values
            $('#customer').html(job.customerName);
            $('#advance').html(advance.amount);

            //set lines to table
            var jobLines = job.jobLines;

            tbl_line.clear().draw();//clear table
            $(jobLines).each(function (index) {//loop lines
                var deleteIcon = "<a class='dicon' onclick='removeItem(" + index + ")'  style='cursor: pointer' data-id='0' ><i class='fa fa-times fa-2x fg-red' ></i></a>";
                var jobLine = jobLines[index];//get line by line 
                var amount = jobLine.width * jobLine.height * jobLine.unitPrice * jobLine.qty;
                var row = [jobLine.itemId, jobLine.itemName, jobLine.unitPrice, jobLine.width, jobLine.height, jobLine.qty, amount, deleteIcon];
                //add row to table
                tbl_line.row.add(row).draw();

                showTotal();
            });
            $("#img_load").html(""); //show loading img  

        }
    });


}

function print() {
    var id = $('#invNo').val();
    var printFile = "invoice_print";

    window.open('../print.htm?print_file=' + printFile + '&id=' + id);
}

