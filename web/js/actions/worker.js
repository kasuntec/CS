/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//list table define
var tbl_worker = $('#tbl_worker').DataTable({
    "paging": false,
    "ordering": false,
    "info": false,
    "searching": false,
    columns: [{
            title: "#"
        }, {
            title: "ETF No"
        }, {
            title: "Name"
        }, {
            title: "Designation"
        }, {
            title: "Mobile"
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
    listWorker();
});

//New Button click event : show form
$("#newbtn").click(function () {
    $("#window_worker").modal();
    resetFrom();
    $("#id").val("New");
});

//create button
$("#btn_create").click(function () {
    var form = $("#frm_worker");

    //check save from worker page or other page
    var pageTitle = $(document).find("title").text().trim();

    if (pageTitle == "Workers") {
        //worker page
        if (validateForm(form)) {
            saveWorker();
        }
    } else {
        //from other page
        if (validateForm(form)) {
            saveWorkerFromPages();
        }

    }





});

//rest form
$("#btn_reset").click(function () {
    resetFrom($("#window_worker"));
    $("#id").val("New");
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------Show All Customers---------------*/
function listWorker() {

    //clear table
    tbl_worker.clear().draw();
    //set worker list to table
    $.ajax({
        type: 'GET',
        url: "../worker/list.htm",
        success: function (data, textStatus, jqXHR) {
            //set worker list to table
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var worker = json[index];
                var editBtn = "<a><span style='cursor: pointer' class='glyphicon glyphicon-pencil' onclick='editWorker(" + worker.id + ")' ></span></a>"
                var row = [worker.id, worker.etfNo, worker.fullName,worker.designation,worker.mobile, editBtn];
                tbl_worker.row.add(row).draw();
            });


        }

    })

}
/*-----------------------Save Customer---------------*/
function saveWorker() {
    //set 0 to cust id if new worker
    if ($("#id").val().trim() == "New") {
        $("#id").val(0);
    }
    var formData = $("#frm_worker").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../worker/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_worker').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
            showMsg(resp.msg, resp.status);
            listWorker();
            loadWorkerList();

        }

    })

}

/*-----------------------Save Customer Only for Other Pages---------------*/
function saveWorkerFromPages() {
    //set 0 to cust id if new worker
    //show loading icon
    $("#btn_create").addClass('disabled');    
    if ($("#id").val().trim() == "New") {
        $("#id").val(0);
    }
    var formData = $("#frm_worker").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../worker/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_worker').modal('hide');
            var resp = JSON.parse(data);

            //show done
            $("#btn_create").removeClass('disabled');          
            loadWorkerList(resp.id);


        }

    })

}

/*-----------------------worker edit window--------------*/

function editWorker(id) {

    var data = "id=" + id;

    $.ajax({
        type: 'GET',
        url: "view.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);//parse data to JSON

            if (response.status == "ok") {//check response
                //set worker detals to form using  json
                var worker = JSON.parse(response.worker);

                $("#id").val(worker.id);
                $("#etfNo").val(worker.etfNo);
                $("#fullName").val(worker.fullName);
                $("#designation").val(worker.designation);
                $("#mobile").val(worker.mobile);
               

                //show window worker
                $("#window_worker").modal();
            } else {
                //show error msg
                alert(response.msg);
            }


        }
    })
}


/*---------------------------Load Name List---------------------------------------*/
function loadWorkerList(id) {
    $('#worker').empty();
    $('#img_load').html("<img src='../../images/icons/loading.gif'/>");
    $.ajax({
        type: 'GET',
        url: "../worker/name_list.htm",
        success: function (data, textStatus, jqXHR) {
            
            var supNameList=JSON.parse(data);
            $('#worker').select2({
                placeholder: 'Select Worker',
                data: supNameList
            });
            $('#img_load').html("");
            $('#worker').select2("val",id);
        }
    });

}






