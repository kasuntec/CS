/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//list table define
var tbl_customer = $('#tbl_customer').DataTable({
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
    listCustomer();
});

//New Button click event : show form
$("#newbtn").click(function () {
    $("#window_customer").modal();
    resetFrom();
    $("#custId").val("New");
});

//create button
$("#btn_create").click(function () {
    var form = $("#frm_customer");

    //check save from customer page or other page
    var pageTitle = $(document).find("title").text().trim();

    if (pageTitle == "Customers") {
        //customer page
        if (validateForm(form)) {
            saveCustomer();
        }
    } else {
        //from other page
        if (validateForm(form)) {
            saveCustomerFromPages();
        }

    }





});

//rest form
$("#btn_reset").click(function () {
    resetFrom($("#window_customer"));
    $("#custId").val("New");
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------Show All Customers---------------*/
function listCustomer() {

    //clear table
    tbl_customer.clear().draw();
    //set customer list to table
    $.ajax({
        type: 'GET',
        url: "../customer/list.htm",
        success: function (data, textStatus, jqXHR) {
            //set customer list to table
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var item = json[index];
                var editBtn = "<a><span style='cursor: pointer' class='glyphicon glyphicon-pencil' onclick='editCustomer(" + item.custId + ")' ></span></a>"
                var row = [item.custId, item.name, item.addressLine1 + "," + item.addressLine2 + "," + item.city + "," + item.postalCode, item.tel1 + "," + item.tel2, item.email, editBtn];
                tbl_customer.row.add(row).draw();
            });


        }

    })

}
/*-----------------------Save Customer---------------*/
function saveCustomer() {
    //set 0 to cust id if new customer
    if ($("#custId").val().trim() == "New") {
        $("#custId").val(0);
    }
    var formData = $("#frm_customer").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../customer/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_customer').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
            showMsg(resp.msg, resp.status);
            listCustomer();
            loadCustNameList();

        }

    })

}

/*-----------------------Save Customer Only for Other Pages---------------*/
function saveCustomerFromPages() {
    //set 0 to cust id if new customer
    //show loading icon
    $("#btn_create").addClass('disabled');    
    if ($("#custId").val().trim() == "New") {
        $("#custId").val(0);
    }
    var formData = $("#frm_customer").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../customer/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_customer').modal('hide');
            var resp = JSON.parse(data);

            //show done
            $("#btn_create").removeClass('disabled');          
            loadCustNameList(resp.id);


        }

    })

}

/*-----------------------customer edit window--------------*/

function editCustomer(custId) {

    var data = "custId=" + custId;

    $.ajax({
        type: 'GET',
        url: "view.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);//parse data to JSON

            if (response.status == "ok") {//check response
                //set customer detals to form using  json
                var customer = JSON.parse(response.customer);

                $("#custId").val(customer.custId);
                $("#name").val(customer.name);
                $("#addressLine1").val(customer.addressLine1);
                $("#addressLine2").val(customer.addressLine2);
                $("#city").val(customer.city);
                $("#postalCode").val(customer.postalCode);
                $("#tel1").val(customer.tel1);
                $("#tel2").val(customer.tel2);
                $("#fax").val(customer.fax);
                $("#cperson").val(customer.cperson);
                $("#mobile").val(customer.mobile);
                $("#email").val(customer.email);
                $("#remarks").val(customer.remarks);

                //show window customer
                $("#window_customer").modal();
            } else {
                //show error msg
                alert(response.msg);
            }


        }
    })
}


/*---------------------------Load Name List---------------------------------------*/
function loadCustNameList(custId) {
    $('#customer').empty();
    $('#img_load').html("<img src='../../images/icons/loading.gif'/>");
    $.ajax({
        type: 'GET',
        url: "../customer/name_list.htm",
        success: function (data, textStatus, jqXHR) {
            
            var custNameList=JSON.parse(data);
            $('#customer').select2({
                placeholder: 'Select Customer',
                data: custNameList
            });
            $('#img_load').html("");
            $('#customer').select2("val",custId);
        }
    });

}






