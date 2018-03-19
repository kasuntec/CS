
/*-----------------------form validate--------------*/

function validateForm(form) {
    $('#errorMsg').removeClass("alert");//reset old alerts
    $('#errorMsg').html("");
    var inputs = form.find('input');//get inputs from Form    
    var selects = form.find('select');//get select from Form
    var errors = 0;

//------------------------form input boxes validations---------------------------------------//
    $(inputs).each(function (index) {//loop inputs

        var input = inputs[index];//get input object
        $(input).removeClass('bg-pink');//remove already error indicate
        var valType = $(input).attr('data-validation');//get input validation type


//--------------------required fields validations
        if (valType == 'required') {//check is required
            var val = $(input).val();
            if (isEmpty(val)) {
                errors++;//increment errors count
                //indicate error                
                $(input).addClass('bg-pink');
                $(input).attr('placeholder', 'Please complete');
            }
        }

//--------------------required telephone no fields validations
        if (valType == 'required-tel') {//check is required
            var val = $(input).val();
            if (isEmpty(val)) {
                errors++;//increment errors count
                //indicate error                
                $(input).addClass('bg-pink');
                $(input).attr('placeholder', 'Please complete');
            } else {
                if (!isPhonenumber(val)) {//check telephone
                    errors++;//increment errors count   
                    $(input).addClass('bg-pink');
                    $(input).attr('placeholder', 'Please enter valid no');
                    showNotification('error', "Please enter valid phone no");
                    $(input).addClass('bg-pink');

                }
            }
        }
//--------------------telephone no fields validations
        if (valType == 'tel') {//check is required
            var val = $(input).val();
            if (val.length > 0) {//check user is type email or not
                //user type email
                if (!isPhonenumber(val)) {
                    errors++;//increment errors count                   
                    $('#errorMsg').html("Please enter valid phone no ");
                    showNotification('error', "Please enter valid phone no");
                    $(input).addClass('bg-pink');

                }
            }
            
        }

//--------------------only number fields validations
        if (valType == 'number') {
            if (isNumber(val)) {
                errors++;//increment errors count
                //indicate error                
                $(input).addClass('bg-pink');
                $(input).attr('placeholder', 'Please enter number');
            }
        }


        //leangth validation 
        if (valType == 'length') {//
            var minlength = $(input).attr('minlength');
            var val = $(input).val();
            if (val.length < minlength) {
                errors++;//increment errors count
                //indicate error                
                $(input).addClass('bg-pink');
                $(input).attr('placeholder', 'value is too short');
            }
        }

        //--------------------------input type validation---------------------//
        var inputType = $(input).attr('type');

        //password validation
        if (inputType == "password") {//check is password input           
            var minlength = $(input).attr('minlength');
            var val = $(input).val();
            if (val.length < minlength) {
                errors++;//increment errors count
                //indicate error                
                $(input).addClass('bg-pink');
                $(input).attr('placeholder', 'Password is too short');
            }
        }

        //email address validation
        if (inputType == "email") {//check is e-mail
            var val = $(input).val();
            if (val.length > 0) {//check user is type email or not
                //user type email
                if (!isEmail(val)) {
                    errors++;//increment errors count                   
                    $('#errorMsg').html("Please enter valid e-mail address ");
                    //showNotification('error', "Please enter valid e-mail address ");
                    $(input).addClass('bg-pink');

                }
            }

        }



    });



//------------------------select boxes validations---------------------------------------//
    $(selects).each(function (index) {//loop selects
        var select = selects[index];//get input object
        var val = $(select).val();
        if (isEmpty(val)) {
            errors++;//increment errors count
            //indicate error                

            $(select).attr('placeholder', 'Please Select');
            $(select).attr('background-color', 'red');

        }
    });




//check errors and indicate-------------------------------------------
    if (errors > 0) {//check error count and return status
        $('#errorMsg').html("<br>Please enter valid data to indicated fields ");//for popup window
        $('#errorMsg').addClass("alert");
        showNotification('error', "Please enter valid data to indicated fields ");
        return false;
    } else {
        return true;
    }


}

//-------------------------Functions------------------------------------------------
//------- check empty funcation
function isEmpty(val) {
    if (val.length == 0 || val == "") {
        return true;
    } else {
        return false;
    }
}
//------- check empty funcation
function isEmpty(val) {
    if (val == null || val.length == 0 || val == "") {
        return true;
    } else {
        return false;
    }
}
//------- check is number funcation
function isNumber(val) {
    return $.isNumeric(val);
}

//------- check is email
function isEmail(email) {
    var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
    if (filter.test(email)) {

        return true;
    } else {
        return false;
    }
    return true;
}

//reset form
function resetFrom(form) {


    $(form).find("input").val("");
    $(form).find("input").removeClass('bg-pink');
    $("#errorMsg").removeClass('alert');
    $("#errorMsg").html("");
    $("#password2").removeClass('bg-red');
    console.info($(form).find("input"))

}

//block enter non Numeric  values to text box
function isNumberKey(e) {

    var key = e.charCode || e.keyCode || 0;//get key code           
    //check key code 
    /*allow backspace, tab, delete, enter, arrows, numbers and keypad numbers,
     home, end, period, and numpad decimal*/
    if (
            key == 8 ||
            key == 9 ||
            key == 13 ||
            key == 46 ||
            key == 110 ||
            key == 190 ||
            (key >= 35 && key <= 40) ||
            (key >= 48 && key <= 57) ||
            (key >= 96 && key <= 105)) {
        return true;
    }

    return false;
}


function isPhonenumber(tel)
{
    var filter = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    if (filter.test(tel)) {

        return true;
    } else {
        return false;
    }
    return true;
}





