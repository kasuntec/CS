/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_user_access = $('#tbl_user_access').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {
            title: "Page ID"
        }, {
            title: "Page Name"
        },
        {
            title: "Action"
        }
    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {
    //fill combo boxes
    //pages
    $('#page').select2({
        data: selectPageList,
        placeholder: 'Select Page',
    });

    var userRole = $('#userRole').val();

    if (userRole != 0) {
        listPages(userRole);
    }
    $('#userRole').val(0).trigger();
    $('#page').select2("val", "");
    $('#page').trigger();


});



//btnAddItem Click Event
$("#btnAddItem").click(function () {

    var userRole = $('#userRole').val();
    var page = $('#page').val();

    if (userRole != null && page != null) {//validate
        addpage();
    } else {
        showNotification('error', "Please select required item")
    }



});

//save butoon clcick event
$("#btnSave").click(function () {
    var userRole = $('#userRole').val();
    var page = $('#page').val();

    if (userRole != null && page != null) {

    }

    var rows = tbl_user_access.rows();

    if (rows.length > 0) {//check is pages table is empty
        saveAccesss();
    } else {//try save to user role has no pages
        showConfirmMsg('Confirm', 'Are you sure whant to Save with no pages?', saveAccesss());
    }



});

//user role change event

$('#userRole').change(function () {
    var userRole = $('#userRole').val();

    if (userRole != 0) {
        listPages(userRole);
    }

});






/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listPages(userRole) {

    //clear table
    tbl_user_access.clear().draw();
    var data = "userRole=" + userRole;


    //set job list to table
    $.ajax({
        type: 'GET',
        url: "list.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var userPage = json[index];
                var deleteIcon = "<a class='dicon' onclick='removePage(this)'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";
                var row = [userPage.id, userPage.pageName, deleteIcon];
                tbl_user_access.row.add(row).draw();
            });


        }

    });

}


function saveAccesss() {

    var userRole = $('#userRole').val();

    var rows = tbl_user_access.rows().data();//get table data

    //convert tabale data to array and convert to JSON
    var pageList = new Array();
    $(rows).each(function (index) {
        pageList[index] = this.slice(0, 1);
    });

    var data = "userRole=" + userRole + "&pageList=" + JSON.stringify(pageList);
//    console.info(data);

    //set customer list to table
    $.ajax({
        type: 'POST',
        url: "save.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var resp = JSON.parse(data);
            //show user msg
            showMsg(resp.msg, resp.status);


        }

    });

}

//item remove function
function removePage(row) {

    //remove table row inner funaction
    function removeRow() {
        var tr = $($(row).parent()).parent(); // get parent tr    
        tbl_user_access.row(tr).remove().draw(false);
        showNotification('info', "Page removed")
    }



    showConfirmMsg('Confirm', 'Are you sure whant to remove?', removeRow);

}

function addpage() {
    var id = $('#page').val();
    var page = $('#page').select2('data')[0].text;
    var isNewRow = true;
    //check alredy added
    var rows = tbl_user_access.rows().data();//loop table
    $(rows).each(function (index) {
        var row = rows[index][0];
        if (id == row) {//check 
            //already added
            isNewRow = false;
           
        } else {
            //new page
            isNewRow = true;

        }
    });
   

    if (isNewRow) {//check status
        //new 
        var deleteIcon = "<a class='dicon' onclick='removePage(this)'  style='cursor: pointer' data-id='0' ><span class='glyphicon glyphicon-trash' > </spane></a>";
        var newRow = [id, page, deleteIcon];
        tbl_user_access.row.add(newRow).draw();

    } else {
        //alredy exsits       
        showNotification("info", "Page is alredy added for selected user role");

    }



    //reset text boxes    
    $("#frm_line").find("input[type='select']").val("Select");

}






