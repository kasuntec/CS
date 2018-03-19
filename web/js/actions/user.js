/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




//list table define
var tbl_user = $('#tbl_user').DataTable({
    "paging": false,
    "ordering": false,
    "info": false,
    "searching": false,
    columns: [{
            title: "User Name"
        }, {
            title: "Location"
        }, {
            title: "User Role"
        }, {
            title: "Full Name"
        }, {
            title: "E-mail"
        },
        {
            title: "Active Status"
        },
        {
            title: "Login Status"
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

    listUsers();


});

//New Button click event : show form
$("#newbtn").click(function () {
    $("#window_user").modal();    
   resetFrom($("#frm_user"));

});

//create button
$("#btn_create").click(function () {
    var form = $("#window_user");


    //customer page
    if (validateForm(form)) {
        if (matchConfirmPassword()) {
            saveUser();
        } else {
            $("#errorMsg").addClass('alert');
            $("#errorMsg").html("Password did not match");
            $("#password2").addClass('bg-red');
        }

    }

});

//rest form
$("#btn_reset").click(function () {
    resetFrom($("#frm_user"));
    var form=$("#frm_user");
    form.reset();
   
});


//match confirm passowrd when focus out
$("#password2").focusout(function () {
    if (matchConfirmPassword()) {
        $("#errorMsg").removeClass('alert');
        $("#errorMsg").html("");
        $("#password2").removeClass('bg-red');

    } else {
        $("#errorMsg").addClass('alert');
        $("#errorMsg").html("Password did not match");
        $("#password2").addClass('bg-red');


    }
});

//match confirm passowrd  when type
$("#password2").keyup(function () {
    if (matchConfirmPassword()) {
        $("#password2").removeClass('bg-red');
    } else {
        $("#password2").addClass('bg-red');

    }
});



/*---------------------------------------------------
 * Functions
 ---------------------------------------------------*/

/*-----------------------Show All Customers---------------*/
function listUsers() {

    //clear table
    tbl_user.clear().draw();
    //set customer list to table
    $.ajax({
        type: 'GET',
        url: "../user/list.htm",
        success: function (data, textStatus, jqXHR) {
            //set customer list to table
            var json = JSON.parse(data);

            $(json).each(function (index) {
                var user = json[index];
                var onclick = "editUser('" + user.username + "')";
                var editBtn = "<a><span style='cursor: pointer' class='glyphicon glyphicon-pencil' onclick=" + onclick + " ></span></a>"
                var activeStatus = "";
                var loginStatus = "";
                if (user.active) {
                    activeStatus = "<span class='label bg-lime fg-white'>Yes</span>"
                } else {
                    activeStatus = "<span class='label bg-red fg-white'>No</span>"
                }

                if (user.login) {
                    loginStatus = "<span class='label bg-lime fg-white'>logged</span>"
                } else {
                    loginStatus = "<span class='label bg-amber fg-white'>Log out</span>"
                }

                var row = [user.username, user.location, user.userRole, user.fullName, user.email, activeStatus, loginStatus, editBtn];
                tbl_user.row.add(row).draw();
            });


        }

    })

}
/*-----------------------Save Customer---------------*/
function saveUser() {
    //set 0 to cust id if new customer

    var formData = $("#frm_user").serializeArray();

    $.ajax({
        type: 'POST',
        url: "../user/save.htm",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            $('#window_user').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
            showMsg(resp.msg, resp.status);
            listUsers();


        }

    })

}



/*-----------------------customer edit window--------------*/

function editUser(userName) {

    var data = "userName=" + userName;

    $.ajax({
        type: 'GET',
        url: "view.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            var response = JSON.parse(data);//parse data to JSON

            if (response.status == "ok") {//check response
                //set customer detals to form using  json
                var user = JSON.parse(response.user);

                $("#username").val(user.username);
                $("#location").val(user.location);
                $("#userRole").val(user.userRole);
                $("#fullName").val(user.fullName);
                $("#email").val(user.email);
                if (user.active) {
                    $('#active').bootstrapToggle('on')
                } else {
                    $('#active').bootstrapToggle('off')
                }
                $("#password").val("");

                //show window customer
                $("#window_user").modal();

                $("#location").trigger();
                $("#userRole").trigger();
            } else {


            }


        }
    })
}

function matchConfirmPassword() {
    var password = $("#password").val().trim();
    var confrmPassword = $("#password2").val().trim();

    if (password == confrmPassword) {
        return true;
    }
    return false;
}







