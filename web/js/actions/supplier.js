/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//list table define
var tbl_supplier = $('#tbl_supplier').DataTable({
    "paging": false,
    "ordering": false,
    "info": false,
    "searching": false,
    columns: [{
            title: "#"
        }, {
            title: "Name"
        }, {
            title: "Address"
        }, {
            title: "Telphone"
        }, {
            title: "E-mail"
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
    listSupplier();
});

//New Button click event : show form
$("#newbtn").click(function () {
    $("#window_supplier").modal();
    resetFrom();
    $("#supId").val("New");
});

//create button
$("#btn_create").click(function () {
    var form = $("#frm_supplier");

    //check save from supplier page or other page
    var pageTitle = $(document).find("title").text().trim();

    if (pageTitle == "Suppliers") {
        //supplier page
        if (validateForm(form)) {
            saveSupplier();
        }
    } else {
        //from other page
        if (validateForm(form)) {
            saveSupplierFromPages();
        }

    }





});

//rest form
$("#btn_reset").click(function () {
    resetFrom($("#window_supplier"));
    $("#supId").val("New");
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------Show All Customers---------------*/
function listSupplier() {

    //clear table
    tbl_supplier.clear().draw();
    //set supplier list to table
    $.ajax({
        type: 'GET',
        url: "../supplier/list.htm",
        success: function (data, textStatus, jqXHR) {
            //set supplier list to table
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var item = json[index];
                var editBtn = "<a><span style='cursor: pointer' class='glyphicon glyphicon-pencil' onclick='editSupplier(" + item.supId + ")' ></span></a>"
                var row = [item.supId, item.name, item.addressLine1 + "," + item.addressLine2 + "," + item.city + "," + item.postalCode, item.tel1 + "," + item.tel2, item.email, editBtn];
                tbl_supplier.row.add(row).draw();
            });


        }

    })

}
/*-----------------------Save Customer---------------*/
function saveSupplier() {
    //set 0 to cust id if new supplier
    if ($("#supId").val().trim() == "New") {
        $("#supId").val(0);
    }
    var formData = $("#frm_supplier").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../supplier/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_supplier').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
            showMsg(resp.msg, resp.status);
            listSupplier();
            loadSuppNameList();

        }

    })

}

/*-----------------------Save Customer Only for Other Pages---------------*/
function saveSupplierFromPages() {
    //set 0 to cust id if new supplier
    //show loading icon
    $("#btn_create").addClass('disabled');    
    if ($("#supId").val().trim() == "New") {
        $("#supId").val(0);
    }
    var formData = $("#frm_supplier").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../supplier/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_supplier').modal('hide');
            var resp = JSON.parse(data);

            //show done
            $("#btn_create").removeClass('disabled');          
            loadSuppNameList(resp.id);


        }

    })

}

/*-----------------------supplier edit window--------------*/

function editSupplier(supId) {

    var data = "supId=" + supId;

    $.ajax({
        type: 'GET',
        url: "view.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);//parse data to JSON

            if (response.status == "ok") {//check response
                //set supplier detals to form using  json
                var supplier = JSON.parse(response.supplier);

                $("#supId").val(supplier.supId);
                $("#name").val(supplier.name);
                $("#address1").val(supplier.addressLine1);
                $("#address2").val(supplier.addressLine2);
                $("#city").val(supplier.city);
                $("#postalCode").val(supplier.postalCode);
                $("#tel1").val(supplier.tel1);
                $("#tel2").val(supplier.tel2);
                $("#fax").val(supplier.fax);
                $("#cperson").val(supplier.cperson);
                $("#mobile").val(supplier.mobile);
                $("#email").val(supplier.email);
                $("#remarks").val(supplier.remarks);

                //show window supplier
                $("#window_supplier").modal();
            } else {
                //show error msg
                alert(response.msg);
            }


        }
    })
}


/*---------------------------Load Name List---------------------------------------*/
function loadSuppNameList(supId) {
    $('#supplier').empty();
    $('#img_load').html("<img src='../../images/icons/loading.gif'/>");
    $.ajax({
        type: 'GET',
        url: "../supplier/name_list.htm",
        success: function (data, textStatus, jqXHR) {
            
            var supNameList=JSON.parse(data);
            $('#supplier').select2({
                placeholder: 'Select Supplier',
                data: supNameList
            });
            $('#img_load').html("");
            $('#supplier').select2("val",supId);
        }
    });

}






