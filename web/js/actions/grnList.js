/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_grn = $('#tbl_grn').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {
            title: "Grn No" 
        }, {
            title: "Date"
        },
        {
            title: "Supplier"
        }, {
            title: "Store Keeper"
        },
        {
            title: "Net Total","className": "dt-right"
        },       
        {
            title: "Due Amount","className": "dt-right"
        },
        {
            title: "Status",
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

   listGRn();
});

//status change event
$('#status').change(function (){
    listGRn();
});




/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listGRn() {

    //clear table
    tbl_grn.clear().draw();

    //get stataus
    var status = $("#status").val();
    var data = "status=" + status;
    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var grn = json[index];
                var viewBtn = "<a href='view.htm?grnNo=" + grn.grnNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                 var statusBtn=displayStatus(grn.status);//show status
                var row = [grn.grnNo, grn.date, grn.supplier, grn.StoreKeeper, grn.netTotal,grn.dueBalance,statusBtn,viewBtn];
                tbl_grn.row.add(row).draw();
            });


        }

    });

}

//show status
function displayStatus(status){
    
    var statusBtn="";  
    
    switch (status){
        case 'Draft':
             statusBtn="<span class='label  bg-blue fg-white'>"+status+"</span>";
             break;
        case 'Canceled':
             statusBtn="<span class='label bg-red fg-white'>"+status+"</span>";
             break;
        case 'Checked':
             statusBtn="<span class='label bg-violet fg-white'>"+status+"</span>";
             break;
        case 'Unpaid':
             statusBtn="<span class='label bg-amber fg-white'>"+status+"</span>";
             break;
        case 'Partial':
             statusBtn="<span class='label bg-lime fg-white'>"+status+"</span>";
             break;
        case 'Paid':
             statusBtn="<span class='label bg-emerald fg-white'>"+status+"</span>";
             break;
    }
    console.info(status);
    return statusBtn;
}




