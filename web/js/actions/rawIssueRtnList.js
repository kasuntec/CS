/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//list table define
var tbl_raw_issue = $('#tbl_raw_issue').DataTable({
    "paging": true,
    "ordering": false,
    "info": false,
    "searching": true,
    columns: [
        {title: "Return No"},
         {title: "Date"},
        {title: "Issue No"},
        {title: "Staff"},
        {title: ""}

    ]
});

/*---------------------------------------------------
 * Events
 ---------------------------------------------------*/

//document ready event
$(document).ready(function () {

    listList();
});




/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------List Jobs---------------*/
function listList() {

    //clear table
    tbl_raw_issue.clear().draw();

    //set job list to table
    $.ajax({
        type: 'GET',
        url: "load_list.htm",
        success: function (data, textStatus, jqXHR) {
            //set customer list to table          
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var issueRtn = json[index];
                var viewBtn = "<a href='view.htm?rtnNo=" + issueRtn.rtnNo + "'><span style='cursor: pointer' class='glyphicon glyphicon-folder-open'  ></span></a>"
                var row = [issueRtn.rtnNo, issueRtn.date,issueRtn.rawItemIssue,issueRtn.user,viewBtn];
                tbl_raw_issue.row.add(row).draw();
            });


        }

    });

}

function displayStatus(status) {

    var statusBtn = "";
    if (status == "Open") {
        statusBtn = "<button class='bg-blue fg-white'>" + status + "</button>"

    }

    switch (status) {
        case 'Open':
            statusBtn = "<span class='label  bg-blue fg-white'>" + status + "</span>";
            break;
        case 'Canceled':
            statusBtn = "<span class='btn bg-red fg-white'>" + status + "</span>";
            break;
        case 'Started':
            statusBtn = "<span class='btn bg-amber fg-white'>" + status + "</span>";
            break;
        case 'Finished':
            statusBtn = "<span class='btn bg-lime fg-white'>" + status + "</span>";
            break;
        case 'Invoiced':
            statusBtn = "<span class='btn bg-emerald fg-white'>" + status + "</span>";
            break;
        case 'Closed':
            statusBtn = "<span class='btn bg-grayLight fg-white'>" + status + "</span>";
            break;
    }

    return statusBtn;
}




