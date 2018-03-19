/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_invoice = $('#tbl_invoice').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {title: "Invoice#", width: "1%"},
        {title: "Date", },
        {title: "Job#"},     
        {title: "Customer", width: "100%"},
        {title: "Staff"},
        {title: "Amount"},
        {title: "Balance", "className": "dt-right"},
        {title: "Status"},
        {title: ""}

    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {

    listInvoice();
});

$("#status").change(function (){
    listInvoice();
});


/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listInvoice() {

    //clear table
    tbl_invoice.clear().draw();

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
                var invoice = json[index];
                var viewBtn = "<a href='view.htm?invNo=" + invoice.invNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                var statusBtn = displayStatus(invoice.status);//show status                
                var row = [invoice.invNo, invoice.date, invoice.job, invoice.customer, invoice.user,invoice.netTotal, invoice.balance, statusBtn, viewBtn];
                tbl_invoice.row.add(row).draw();
            });


        }

    });

}

//display status with colours
function displayStatus(status) {

    var statusBtn = "";
    switch (status) {
        case 'Draft':
            statusBtn = "<span class='label  bg-blue fg-white'>" + status + "</span>";
            break;
        case 'Canceled':
            statusBtn = "<span class='label bg-red fg-white'>" + status + "</span>";
            break;
        case 'Unpaid':
            statusBtn = "<span class='label bg-amber fg-white'>" + status + "</span>";
            break;
        case 'Partial':
            statusBtn = "<span class='label bg-lime fg-white'>" + status + "</span>";
            break;
        case 'Paid':
            statusBtn = "<span class='label bg-emerald fg-white'>" + status + "</span>";
            break;
      
    }

    return statusBtn;
}




