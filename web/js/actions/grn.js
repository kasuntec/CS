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
        {title: "#", "width": "1%"},
        {title: "Item Description", "width": "12%"},
        {title: "Qty", "width": "12%", "class": "dt-right"},
        {title: "Price", "width": "6%", "class": "dt-right"},
        {title: "Amount", "width": "20%", "class": "dt-right"},
        {title: "", "width": "1%", "class": "dt-center"}
    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {


    //set date picker
    $('#date').datepicker({dateFormat: "yy-mm-dd"});


    //fill select boxes
    $('#itemId').select2({
        data: itemNameList,
        placeholder: 'Select Item',
    });
    $('#itemId').select2("val", "");
    //fill select boxes
    $('#supplier').select2({
        placeholder: 'Select Supplier',
        data: supNameList,
        allowClear: true
    });
    $('#supplier').select2("val", "");



    if (action == "View") {//check page mood
        //load job
        loadGRN(grn);

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
    var form = $("#frm_grn");
    if (validateForm(form)) {
        $("#btnSave").addClass('disabled');//disbale save button       
        $("#img_load").html("<img src='/CreativeEdge/images/icons/loading.gif'/>"); //show loading img           
        saveGrn();
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
$("#price").focusout(function () {
    calculateLineAmount();
});
$("#price").keyup(function () {
    calculateLineAmount();
});

$("#qty").focusout(function () {
    calculateLineAmount();
});
$("#qty").keyup(function () {
    calculateLineAmount();
});

$("#discount").focusout(function () {
    showTotal();
});
$("#discount").keyup(function () {
    showTotal();
});
$("#nbtValue").focusout(function () {
    showTotal();
});
$("#nbtValue").keyup(function () {
    showTotal();
});
$("#vatValue").focusout(function () {
    showTotal();
});
$("#vatValue").keyup(function () {
    showTotal();
});


$("#btnPrint").click(function () {
    print();
});

/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/


/*-----------------------Add item to table---------------*/

function addItem() {
    var itemId = $('#itemId').val();
    var itemDsc = $('#itemId').select2('data')[0];
    var qty = $('#qty').val();
    var price = $('#price').val();
    var deleteIcon = "<a class='dicon' onclick='removeItem(this)'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";

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
        var row = [itemId, itemDsc.text, qty, price, (qty * price), deleteIcon];

        tbl_line.row.add(row).draw();

    } else {
        showNotification('info', "Item is already added")

    }




    //reset text boxes
    $("#frm_line").find("input[type='text']").val("");
    $("#frm_line").find("input[type='select']").val("Select");

}
//item remove function
function removeItem(row) {

    //remove table row inner funaction
    function removeRow() {
        var tr = $($(row).parent()).parent(); // get parent tr    
        tbl_line.row(tr).remove().draw(false);
        showNotification('info', "Item removed")
    }


    //show total
    showTotal();

    showConfirmMsg('Confirm', 'Are you sure whant to remove?', removeRow);



}



/*---------------------------------------Save Grn----------------------------------------*/
function saveGrn() {

    var formData = $('#frm_grn').serializeArray(); //get heder data    
    var grn = JSON.stringify(createJONObject(formData));
    //set totals to json object

    var discount = $('#discount').val().trim() == "" ? 0.00 : parseFloat($('#discount').val());
    var nbtValue = $('#nbtValue').val().trim() == "" ? 0.00 : parseFloat($('#nbtValue').val());
    var vatValue = $('#vatValue').val().trim() == "" ? 0.00 : parseFloat($('#vatValue').val());


    var rows = tbl_line.rows().data();//get table data

    //convert tabale data to array and convert to JSON
    var lines = new Array();
    $(rows).each(function (index) {
        lines[index] = this.slice(0, 7);
    });

    var data = "grn=" + grn + "&discount=" + discount + "&nbtValue=" + nbtValue + "&vatValue=" + vatValue + "&lines=" + JSON.stringify(lines);
//    console.info(data);

    //set customer list to table
    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set customer list to table
            var resp = JSON.parse(data);

            $('#grnNo').val(resp.id);
            //show user msg
            showMsg(resp.msg, resp.status);


        }

    });


}



/*---------------------------load Job by job id---------------------------------------*/
function loadGRN(grn) {


    //grn head
    $('#grnNo').val(grn.grnNo);
    $('#tax').prop('checked', grn.tax);
    $('#supplier').val(grn.supplier).trigger('change');
    $('#grnNo').val(grn.grnNo);
    $('#refNo').val(grn.refNo);
    $('#date').val(grn.date);
    $('#status').val(grn.status);
    $('#remarks').val(grn.remarks);
    $('#grossTotal').html(grn.grossTotal);
    $('#discount').val(grn.discount);
    $('#vatValue').val(grn.vatValue);
    $('#nbtValue').val(grn.nbtValue);

    //disable/enable action buttons
    if (grn.status == "Draft" || grn.status == "Checked") {
        $('#btnSave').prop("disabled", false);
    } else {
        $('#btnSave').prop("disabled", true);
    }


    //grn lines
    $(grn.lines).each(function (index) {//loop lines
        var deleteIcon = "<a class='dicon' onclick='removeItem(" + index + ")'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";
        var grnLine = grn.lines[index];//get line by line 
        var row = [grnLine.itemId, grnLine.itemName, grnLine.qty, grnLine.price, (grnLine.qty * grnLine.price), deleteIcon];
        //add row to table
        tbl_line.row.add(row).draw();
    });

    //show totals  
    showTotal();

}
/*---------------------------load Item Details---------------------------------------*/
function loadItem(itemId) {
    $("#stock_load").html("<img src='/CreativeEdge/images/icons/loading.gif'/>"); //show loading img           
    var data = "itemId=" + itemId;
    $.ajax({
        type: 'GET',
        data: data,
        url: "../raw_item/view.htm",
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);
            var item = JSON.parse(response.item);
            //set fg colour red when stock less than reorder level
            $("#stock").removeClass('fg-red');
            $("#stock").removeClass('fg-green');
            if (item.stock < item.reorderLevel) {
                $("#stock").addClass('fg-red');
            } else {
                $("#stock").addClass('fg-green');

            }
            //set values           
            $("#stock").html(item.stock);
            $('#qty').focus();
        }

    });
    $('#stock_load').html("");
}


function calculateLineAmount() {
    var unitPrice = $('#price').val().trim() != "" ? parseInt($('#price').val().trim()) : 0.00;
    var qty = $('#qty').val().trim() != "" ? parseInt($('#qty').val().trim()) : 0.00;


    var lineAmt = unitPrice * qty;
    $('#line_amount').html(lineAmt);
}


/*-----------------------Show Job Total---------------*/
function showTotal() {

    var rows = tbl_line.rows().data();

    var grossTotal = 0;
    $(rows).each(function () {
        //get values from table rows
        var qty = this[2] != "" ? parseInt(this[2]) : 0.00;
        var price = this[3] != "" ? parseInt(this[3]) : 0.00;

        grossTotal += (price * qty);
    });

    var discount = $('#discount').val().trim() != "" ? parseInt($('#discount').val()) : 0.00;
    var nbtValue = $('#nbtValue').val().trim() != "" ? parseInt($('#nbtValue').val()) : 0.00;
    var vatValue = $('#vatValue').val().trim() != "" ? parseInt($('#vatValue').val()) : 0.00;



    //calculate
    var netTotal = (grossTotal - discount) + (nbtValue + vatValue);

    //tax rate calculate
    var nbtRate = nbtValue == 0.00 ? 0.00 : (nbtValue / (grossTotal - discount)) * 100;
    var vatRate = vatValue == 0.00 ? 0.00 : (vatValue / (grossTotal + nbtValue - discount)) * 100;

    //round up
    vatRate = vatRate.toFixed(4);
    nbtRate = nbtRate.toFixed(4);

//show total panel
    $('#nbtRate').html(nbtRate);
    $('#vatRate').html(vatRate);
    $('#grossTotal').html(grossTotal);
    $('#netTotal').html(netTotal);

}


function print() {
    var id = $('#grnNo').val();
    var printFile = "grn_print";

    window.open('../print.htm?print_file=' + printFile + '&id=' + id);
}









