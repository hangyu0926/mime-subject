// 全局验证事件
function Validate(){
    return {
        objectIsEmpty: function(obj){
            if(obj == undefined || obj == null){
                return true;
            }
            return false;
        },
        stringIsEmpty: function(str){
            if(this.objectIsEmpty(str) || typeof str != 'string' || str == ''){
                return true;
            }
            return false;
        },
        stringIsMobile: function(str){
            var mobileReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            return mobileReg.test(str);
        },
        stringIsEmail: function(str){
            var emailReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.com|\.cn)$/;
            return emailReg.test(str) && str.length <= 100;
        },
        passwordIsOk: function(str){
            var passwordReg = /^\d{6}$/;
            return passwordReg.test(str);
        }
    };
}

$(function(){
    window.global_validate = new Validate();
})