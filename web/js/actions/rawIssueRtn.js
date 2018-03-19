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
        {title: "Qty", "width": "6%", "class": "dt-right"},
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


    //customer
    $('#issueNo').select2({
        placeholder: 'Select Issue No',
        data: issueNoList
    });
    $('#issueNo').select2("val", "");


    //--------End of the fill select boxes-------

    //set texboxes to select all text when focus 
    $("input:text").focus(function () {
        $(this).select();
    });


    //View Saved Jo
    if (action == "View") {//check page mood
        //load job
        loadIssueRtn(itemIssueRtn);
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

    var rtnNo = $('#rtnNo').val().trim();
    if (rtnNo == "New") {
        $('#rtnNo').val(0);
    }

    var form = $("#frm_issue");

    if (validateForm(form)) {
        $("#btnSave").addClass('disabled');//disbale save button       
        $("#img_load").html("<img src='/CreativeEdge/images/icons/loading.gif'/>"); //show loading img           
        saveIssueRtn();


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
    var itemCode = $("#itemId").val();
    loadItem(itemCode);
});

//load isssue items
$('#issueNo').change(function () {
    var issueNo = $('#issueNo').val();
    if (issueNo != null) {
        loadIssueItems(issueNo);
    }


});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/


/*-----------------------Add item to table---------------*/

function addItem() {

    var itemId = $('#itemId').val();
    var itemDsc = $('#itemId').select2('data')[0];
    var qty = $('#qty').val();
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
        var row = [itemId, itemDsc.text, qty, deleteIcon];

        tbl_line.row.add(row).draw();
        //reset text boxes
        $("#frm_line").find("input[type='text']").val("");
        $("#frm_line").find("input[type='select']").val("Select");

    } else {
        showNotification('info', "Item is already added")

    }




}

/*-----------------------Remove Table Row---------------*/
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

/*-----------------------Show Job Total---------------*/
function showTotal() {
    var rows = tbl_line.rows().data();

    var totalQty = 0;
    var itemCount = 0;
    $(rows).each(function () {
        //get values from table rows       
        var qty = this[2] != "" ? parseInt(this[2]) : 0.00;
        totalQty += qty;
        itemCount++;
    });

//show total panel
    $('#itemCount').html(itemCount);
    $('#totalQty').html(totalQty);

}




/*---------------------------------------Save Jobs----------------------------------------*/
function saveIssueRtn() {
    var formData = $('#frm_issue').serialize(); //get header data  


    var rows = tbl_line.rows().data();//get table data

    //convert tabale data to array and convert to JSON
    var lines = new Array();
    $(rows).each(function (index) {
        lines[index] = this.slice(0, 3);
    });

    var data = formData + "&lines=" + JSON.stringify(lines);
//    console.info(data);

    //set custId list to table
    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);

            $('#rtnNo').val(resp.id);
            //show user msg
            showMsg(resp.msg, resp.status);


        }
    });

    //after saving or not gui changes
    $("#btnSave").removeClass('disabled');
    $("#img_load").html("");  //empty loading img 


}



/*---------------------------load Job by job id---------------------------------------*/
function loadIssueRtn(issueRtn) {
    // head
    $('#rtnNo').val(issueRtn.rtnNo);
    $('#issueNo').val(issueRtn.rawItemIssue);
    $('#date').val(issueRtn.date);
    $('#remarks').val(issueRtn.remarks);

    //trigger select2
    $('#issueNo').trigger('change');



    //lines
    $(issueRtn.lines).each(function (index) {//loop lines
        var deleteIcon = "<a class='dicon' onclick='removeItem(" + index + ")'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";
        var line = issueRtn.lines[index];//get line by line        
        var row = [line.itemId, line.itemName, line.qty, deleteIcon];
        //add row to table
        tbl_line.row.add(row).draw();
    });

    //show total
    showTotal();

}
/*---------------------------load Finished Item Details---------------------------------------*/
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


function loadIssueItems(issueNo) {
    var data = "issueNo=" + issueNo;

    $.ajax({
        type: 'GET',
        url: "../raw_issue/load_issue_items.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set custId list to table
            var resp = JSON.parse(data);

            //fill select boxes
            $('#itemId').select2({
                data: resp.itemNameList,
                placeholder: 'Select Item',
            });
            $('#itemId').select2("val", "");


        }

    });
}


