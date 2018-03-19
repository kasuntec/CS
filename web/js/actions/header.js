/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//events-----------------------------

//match confirm passowrd when focus out
$("#chnge_password2").focusout(function () {
    if (updatMatchConfirmPassword()) {
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
$("#chnge_password2").keyup(function () {
    
    if (updatMatchConfirmPassword()) {
        $("#chnge_password2").removeClass('bg-red');
    } else {
        $("#chnge_password2").addClass('bg-red');

    }
});

//rest form
$("#chnge_btn_reset").click(function () {
    resetFrom($("#frm_user_update"));   
});

//save button
$("#chnge_btn_save").click(function () {
    var form = $("#frm_user_update");


    //customer page
    if (validateForm(form)) {
        if (updatMatchConfirmPassword()) {
            changePassword();
        } else {
            $("#errorMsg").addClass('alert');
            $("#errorMsg").html("Password did not match");
            $("#chnge_password2").addClass('bg-red');
        }

    }

});

//functions---------------------------
function changePasswd() {
    $("#window_user_update").modal();
}

function updatMatchConfirmPassword() {
    var password = $("#chnge_password").val().trim();
    var confrmPassword = $("#chnge_password2").val().trim();
    

    if (password == confrmPassword) {
        return true;
    }
    return false;
}

function changePassword() {
    //set 0 to cust id if new customer

    var oldPassword = $("#oldPassword").val();
    var password = $("#chnge_password").val();
    
    var data="oldPassword="+oldPassword+"&password="+password;

    $.ajax({
        type: 'POST',
        url: "change_password.htm",
        data: data,
        success: function (data, textStatus, jqXHR) {
            $('#window_user_update').modal('hide');
            var resp = JSON.parse(data);

            //show user msg
            showMsg(resp.msg, resp.status);
            
          
          


        }

    })

}


