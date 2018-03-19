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
        {title: "GRN#", width: "1%"},
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
                var payment = json[index];
                var viewBtn = "<a href='view.htm?payId=" + payment.id + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"

                var row = [payment.id, payment.date, payment.grn, payment.user, payment.payType, payment.amount, viewBtn];
                tbl_payment.row.add(row).draw();
            });


        }

    });

}







