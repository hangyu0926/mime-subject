$(function() {
/**
*发布审核和撤销审核页面的隐藏和实现
*/
    
















});
//刚进来修改密码
;function doFirstModify(){
	var newPass=$("#firstnowPwd").val().trim();
	var mewPassagain=$("#firstnowPwdagain").val().trim();
	doModifypass(newPass,mewPassagain);
};

;function doClearfirst(){
  $("#firstnowPwd").val("");
  $("#firstnowPwdagain").val("");
};