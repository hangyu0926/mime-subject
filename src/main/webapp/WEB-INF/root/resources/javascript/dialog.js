// 全局弹框事件
function Dialog(){
    var alertDialog = ''
        + '<div class="alert-dialog" id="alertDialog">'
        + '<div class="alert-dialog-message"><div class="alert-dialog-header">提示</div>'
        + '<p id="alert-dialog-message"></p>'
        + '<span  onclick="" class="btn btn-default btn-sm">确定</button>'
        + '</span>'
        + '</div>';
    // var confirmDialog = ''
    //     + '<div class="confirm-dialog" id="confirmDialog">'
    //     + '<div class="confirm-dialog-message">'
    //     + '<p id="confirm-dialog-message"></p>'
    //     + '<button type="button" onclick="closeConfirmDialog()" class="btn btn-default btn-sm">取消</button>'
    //     + '<button type="button" class="btn btn-default btn-sm confirm-dialog-button-ok">确定</button>'
    //     + '</div>'
    //     + '</div>';
    $('body').append(alertDialog);
    window.closeAlertDialog = function(){
        $('#alertDialog').hide();
    };
    window.closeConfirmDialog = function(){
        $('#confirmDialog').removeClass('is-visible');
    };
    window.dialogTime;// 弹框定时器
    return {
        alert: function(msg, callBack){
            // isAutoClose = !!isAutoClose && typeof isAutoClose == 'boolean' ? isAutoClose : true;
            $('#alert-dialog-message').html(msg);
            $('#alertDialog').show();
            // if(window.dialogTime && typeof window.dialogTime == 'number'){
            //     clearTimeout(window.dialogTime);
            // }
            // if(isAutoClose){
            //     window.dialogTime = setTimeout("closeAlertDialog()", 3000);
            // }
            $("#alertDialog span").click(callBack);
        },
        error: function(msg, callBack){
            // isAutoClose = !!isAutoClose && typeof isAutoClose == 'boolean' ? isAutoClose : true;
            this.alert('<div class="alert-dialog-message-error">' + msg + '</div>', callBack);
            // $('#alert-dialog-message').html(msg);
            // $("#alertDialog span").click(callBack());
        },
        confirm: function(msg, callback, params){// callback：点击确认执行函数；params：函数执行需要的参数
            $('#confirm-dialog-message').html(msg);
            $('#confirmDialog').addClass('is-visible').find('.confirm-dialog-button-ok').unbind('click').click(function(){
                closeConfirmDialog();
                callback(params);
            });
        }
    };
}

$(function(){
    window.global_dialog = new Dialog();
});