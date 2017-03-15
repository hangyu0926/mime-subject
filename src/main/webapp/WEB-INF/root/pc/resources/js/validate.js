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
        arrayIsEmpty: function(arr){
            if(this.objectIsEmpty(arr) || arr.length == 0){
                return true;
            }
            return false;
        },
        objectIsNumber: function(obj){
            if((obj | 0) === obj){
                return true;
            }
            return false;
        },
        stringLessThanLength: function(str, len, isEqual){
            var stringCanEqual = true;
            if(this.objectIsEmpty(isEqual) && typeof isEqual == 'boolean'){
                stringCanEqual = isEqual;
            }
            if(stringCanEqual){
                return !this.stringIsEmpty(str) && str.length <= len;
            }else{
                return !this.stringIsEmpty(str) && str.length < len;
            }
        },
        stringMoreThanLength: function(str, len, isEqual){
            var stringCanEqual = true;
            if(this.objectIsEmpty(isEqual) && typeof isEqual == 'boolean'){
                stringCanEqual = isEqual;
            }
            if(stringCanEqual){
                return !this.stringIsEmpty(str) && str.length >= len;
            }else{
                return !this.stringIsEmpty(str) && str.length > len;
            }
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
            var passwordReg = /^[A-Za-z0-9]{6,18}$/;
            return passwordReg.test(str);
        },
        stringIsChinese: function(str){
            var chineseReg = /^[\u4e00-\u9fa5]{2,20}$/;
            return chineseReg.test(str);
        },
        stringIsAppraisal: function(str){
            var appraisalReg = /^[\u4e00-\u9fa5]{1,100}$/;
            return appraisalReg.test(str);
        },
        stringIsProjectName: function(str){
            var appraisalReg = /^[\u4e00-\u9fa5]{1,10}$/;
            return appraisalReg.test(str);
        }
    };
}

$(function(){
    window.global_validate = new Validate();
})