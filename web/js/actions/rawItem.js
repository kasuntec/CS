/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//list table define
var tbl_item = $('#tbl_item').DataTable({
    "paging": true,
    "ordering": true,
    "info": false,
    "searching": true,
    columns: [{
            title: "Item ID"
        }, {
            title: "Description"
        }, {
            title: "Category"
        }, {
            title: "UOM"
        }, {
            title: "Cost"
        },
        {
            title: "Remarks"
        },
        {
            title: "Re-Order Level"
        },
        {
            title: ""
        }
    ]
});
/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {
    listItem();
    
    //fill comob boxes
     $('#rawItemCategory').select2({
        data: catList     
    });
    
     $('#uom').select2({
        data: uomList       
    });
    
});

//New Button click event : show form
$("#newbtn").click(function () {
    $("#window_item").modal();
    resetFrom();
    $("#itemId").val("New");
});

//create button
$("#btn_create").click(function () {
    var form = $("#frm_item");

    //check save from customer page or other page
    var pageTitle = $(document).find("title").text().trim();

    if (pageTitle == "Raw Items") {
        //customer page
        if (validateForm(form)) {
            saveItem();
        }
    } else {
        //from other page
        if (validateForm(form)) {
            saveItemFromPages();
        }

    }





});

//rest form
$("#btn_reset").click(function () {
    resetFrom($("#window_item"));
    $("#itemId").val("New");
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------Show All Customers---------------*/
function listItem() {

    //clear table
    tbl_item.clear().draw();
    //set customer list to table
    $.ajax({
        type: 'GET',
        url: "../raw_item/list.htm",
        success: function (data, textStatus, jqXHR) {
            //set customer list to table
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var item = json[index];
                var editBtn = "<a><span style='cursor: pointer' class='glyphicon glyphicon-pencil' onclick='editItem(" + item.itemId + ")' ></span></a>"
                var row = [item.itemId,item.description,item.RawItemCategory, item.uom , item.cost, item.remarks,item.reorderLevel, editBtn];
                tbl_item.row.add(row).draw();
            });


        }

    })

}
/*-----------------------Save Customer---------------*/
function saveItem() {    
     $('#btn_create').prop("disabled", true);
    //set 0 to cust id if new customer
    if ($("#itemId").val().trim() == "New") {
        $("#itemId").val(0);
    }
    var formData = $("#frm_item").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../raw_item/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_item').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
             $('#btn_create').prop("disabled", false);
            showMsg(resp.msg, resp.status);
            listItem();
            loadItemList();

        }

    })

}

/*-----------------------Save Customer Only for Other Pages---------------*/
function saveItemFromPages() {
    //set 0 to cust id if new customer
    //show loading icon
   $('#btn_create').prop("disabled", true);
    if ($("#itemId").val().trim() == "New") {
        $("#itemId").val(0);
    }
    var formData = $("#frm_item").serializeArray();

    $.ajax({
        type: 'POST',
         url: "../raw_item/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_item').modal('hide');
            var resp = JSON.parse(data);

            //show done           
             $('#btn_create').prop("disabled", false);
            loadItemList(resp.id);


        }

    })

}

/*-----------------------customer edit window--------------*/

function editItem(itemId) {

    var data = "itemId=" + itemId;

    $.ajax({
        type: 'GET',
        url: "view.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);//parse data to JSON

            if (response.status == "ok") {//check response
                //set customer detals to form using  json
                var item = JSON.parse(response.item);

                $("#itemId").val(item.itemId);
                $("#description").val(item.description);
                $("#cost").val(item.cost);
                $("#remarks").val(item.remarks);
                $("#reorderLevel").val(item.reorderLevel);
                $("#stock").val(item.stock);
                $("#rawItemCategory").val(item.rawItemCategory);
                $("#uom").val(item.uom);
               

                //show window customer
                $("#window_item").modal();
            } else {
                //show error msg
                alert(response.msg);
            }


        }
    })
}


/*---------------------------Load Name List---------------------------------------*/
function loadItemList(itemId) {
    $('#itemId').empty();
    $('#img_load').html("<img src='../../images/icons/loading.gif'/>");
    $.ajax({
        type: 'GET',
        url: "../raw_item/name_list.htm",
        success: function (data, textStatus, jqXHR) {
            
            var itemNameList=JSON.parse(data);
            $('#itemId').select2({
                placeholder: 'Select Item',
                data: itemNameList
            });
            $('#img_load').html("");
            $('#itemId').select2("val",itemId);
        }
    });

}






