/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_crn = $('#tbl_crn').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {
            title: "CRN No" 
        }, {
            title: "Date"
        },
        {
            title: "Invoice#"
        }, {
            title: "Customer"
        },
        {
            title: "Total","className": "dt-right"
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

   listCRN();
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listCRN() {

    //clear table
    tbl_crn.clear().draw();

  
    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",    
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var crn = json[index];
                var viewBtn = "<a href='view.htm?crnNo=" + crn.crnNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                var row = [crn.crnNo, crn.date, crn.invoice, crn.customerName, crn.total,viewBtn];
                tbl_crn.row.add(row).draw();
            });


        }

    });

}






