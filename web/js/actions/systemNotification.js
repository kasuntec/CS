/*------------------------------------------------------------------
 * System nofification Funactions
 *------------------------------------------------------------------*/


function showConfirmMsg(title, message, success_fun) {

    $.ConfirmMessageBox({
        title: title,
        message: message,
        success_fun: success_fun,
        showActionButton: true,
        actionButtionText: 'YES'
    }).show();

}
function showMsg(message, messageType) {


    $.MessageBox({
        message: message,
        messageType: messageType

    }).show();

}

/*---------------------------------------------------
 * Messgae box Object Content
 ---------------------------------------------------*/

//ConfirmMessageBox------------------------------------
$.ConfirmMessageBox = function (options) {
    return Object.create(ConfirmMessageBox).init(options);
};

var ConfirmMessageBox = {
    container: null,
    options: {
        title: '',
        message: '',
        success_fun: '',
        showActionButton: true,
        actionButtionText: 'YES'
    },
    init: function (options) {
        this.options = $.extend({}, this.options, options);
        this.container = "<div id='confmessage' class='modal fade bs-example-modal-sm' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel'>" +
                "<div class='modal-dialog modal-sm'>" +
                "<div class='modal-content'>" +
                "<div class='modal-header '>" +
                "<h4 class='modal-title' id='myModalLabel'>" + this.options.title + "" +
                "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button> </h4> </div>" +
                "<div class='modal-body'> " + this.options.message + " </div> ";
        if (this.options.showActionButton) {
            this.container = this.container + "<div class='modal-footer'> <button type='button' class='btn btn-default' data-dismiss='modal'>No</button> <button type='button' class='btn btn-info' id='confirmok' data-dismiss='modal' >" + this.options.actionButtionText + "</button>";
        }
        this.container = this.container + "</div> </div></div></div></div>";
        return this;
    },
    show: function () {
        $('#confmessage').remove();
        $(this.container).appendTo('body');
        $('#confirmok').click(this.options.success_fun);
        $('#confmessage').modal();
        $('#confmessage').modal({keyboard: false});
    }

};

//Message Box------------------------------------
$.MessageBox = function (options) {
    return Object.create(MessageBox).init(options);
};

var MessageBox = {
    container: null,
    options: {
        message: '',
        messageType: ''
    },
    init: function (options) {
        this.options = $.extend({}, this.options, options);

        //set messge colour and title using message type
        var messageColour = "";
        var title = "";
        if (this.options.messageType == 'ok') {
            title = "Success"
            messageColour = 'bg-green';
        } else {
            title = "Error"
            messageColour = 'bg-red';
        }
        this.container = "<div id='confmessage' class='modal fade bs-example-modal-sm ' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel'>" +
                "<div class='modal-dialog modal-sm '>" +
                "<div class='modal-content '>" +
                "<div class='modal-header no-padding-bottom " + messageColour + "'>" +
                "<h4 class='modal-title fg-white' id='myModalLabel'>" + title + "" +
                "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button> </h4> </div>" +
                "<div class='modal-body'> " + this.options.message +
                "<div class='modal-footer no-padding-bottom'><button type='button' class='btn btn-info' data-dismiss='modal' >Ok</button>";

        this.container = this.container + "</div></div></div></div>";
        return this;
    },
    show: function () {
        $('#confmessage').remove();
        $(this.container).appendTo('body');
        $('#confmessage').modal();
        $('#confmessage').modal({keyboard: false});
    }

};

//display notifcation
function showNotification(messageType, message) {
    var type = 'error'; //alert | success | error | warning | info
    var header;
    var image;
    switch (messageType) {
        //success message
        case 'success':
            type = 'success';
            header = 'Success';           
            image = "<span class='glyphicon glyphicon-ok' style='color:#3c763d;' ></span>";
            break;
            
        //danger message
        case 'error':
            type = 'error';
            header = 'Error';           
            image = "<span class='glyphicon glyphicon-exclamation-sign' style='color:#a94442;' ></span>";
            break;
        //warning message   
        case 'warning':
            type = 'warning';
            header = 'Warning';           
            image = "<span class='glyphicon glyphicon glyphicon-flash' style='color:#fcf8e3;' ></span>";
            break;
        
        //info message
        case 'info':
            type = 'info';
            header = 'Information';           
            image = "<span class='glyphicon glyphicon glyphicon-info-sign' style='color:#31b0d5;' ></span>";
            break;
    }
    notify({
        type: type,
        title: header,
        message: message,
        position: {
            x: "right", //right | left | center
            y: "top" //top | bottom | center
        },
        icon: image, //<i>
        size: "normal", //normal | full | small
        overlay: false, //true | false
        closeBtn: true, //true | false
        overflowHide: false, //true | false
        spacing: 20, //number px
        theme: "default", //default | dark-theme
        autoHide: true, //true | false
        delay: 4000, //number ms
        onShow: null, //function
        onClick: null, //function
        onHide: null, //function
        template: '<div class="notify"><div class="notify-text"></div></div>'
    });
}






