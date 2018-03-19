/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_job = $('#tbl_job').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {title: "Job No", width:"1%"}, 
        {title: "Date",width:"1%"}, 
        {title: "Delivery Date"}, 
        {title: "Customer",width:"100%"},       
        {title: "Staff"},
        {title: "Amount","className": "dt-right"},
        {title: "Balance","className": "dt-right"},
        {title: "Status"},
        {title: ""}
       
    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/
$("#jobStatus").change(function (){
    listJob();
});


//document ready event
$(document).ready(function () {

    listJob();
});




/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listJob() {

    //clear table
    tbl_job.clear().draw();

    //get stataus
    var status = $("#jobStatus").val();
    var data = "jobStatus=" + status;
    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var job = json[index];
                var viewBtn = "<a href='view.htm?jobNo=" + job.jobNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                var statusBtn=displayStatus(job.status);//show status                
                var row = [job.jobNo, job.date, job.delivryDate, job.customer,job.user, job.amount,job.balance, statusBtn,viewBtn];
                tbl_job.row.add(row).draw();
            });


        }

    });

}

function displayStatus(status){
    
    var statusBtn="";
    
    
    switch (status){
        case 'Open':
             statusBtn="<span class='label  bg-blue fg-white'>"+status+"</span>";
             break;
        case 'Canceled':
             statusBtn="<span class='label bg-red fg-white'>"+status+"</span>";
             break;
        case 'Started':
             statusBtn="<span class='label bg-amber fg-white'>"+status+"</span>";
             break;
        case 'Finished':
             statusBtn="<span class='label bg-lime fg-white'>"+status+"</span>";
             break;
        case 'Invoiced':
             statusBtn="<span class='label bg-emerald fg-white'>"+status+"</span>";
             break;
        case 'Closed':
             statusBtn="<span class='label bg-grayLight fg-white'>"+status+"</span>";
             break;
    }
    
    return statusBtn;
}




