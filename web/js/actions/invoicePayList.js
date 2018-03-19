/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_payment = $('#tbl_payment').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {title: "Payment#", width: "1%"},
        {title: "Date", },
        {title: "Invoice#", width: "1%"},
        {title: "Staff"},
         {title: "Pay Type"},
        {title: "Amount"},
        {title: ""}

    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {

    listtPayment();
});




/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listtPayment() {

    //clear table
    tbl_payment.clear().draw();

   
   
    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",      
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var invPayment = json[index];
                var viewBtn = "<a href='view.htm?payId=" + invPayment.payId + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"

                var row = [invPayment.payId, invPayment.date, invPayment.invoice, invPayment.user, invPayment.payType, invPayment.amount, viewBtn];
                tbl_payment.row.add(row).draw();
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
            statusBtn = "<span class='btn bg-red fg-white'>" + status + "</span>";
            break;
        case 'Unpaid':
            statusBtn = "<span class='btn bg-amber fg-white'>" + status + "</span>";
            break;
        case 'Partial':
            statusBtn = "<span class='btn bg-lime fg-white'>" + status + "</span>";
            break;
        case 'Paid':
            statusBtn = "<span class='btn bg-emerald fg-white'>" + status + "</span>";
            break;

    }

    return statusBtn;
}




