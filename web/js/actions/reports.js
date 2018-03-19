/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("#btnPrint").click(function () {
    var data = $('#print').html();
    print(data);
});

//print report
function print(data) {
    var http = new XMLHttpRequest();
    var url = "../print_report.htm?data";
    var params = "data=" + data;
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  
    http.send(params);

}