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
            title: "Grn Rerturn No" 
        }, {
            title: "Date"
        },
        {
            title: "GRN#"
        }, {
            title: "Supplier"
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

   listGRNRtn();
});





/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listGRNRtn() {

    //clear table
    tbl_grn.clear().draw();

  
    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",    
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var grnRtn = json[index];
                var viewBtn = "<a href='view.htm?grnRtnNo=" + grnRtn.rtnNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                var row = [grnRtn.rtnNo, grnRtn.date, grnRtn.grn, grnRtn.supplierName, grnRtn.total,viewBtn];
                tbl_grn.row.add(row).draw();
            });


        }

    });

}






