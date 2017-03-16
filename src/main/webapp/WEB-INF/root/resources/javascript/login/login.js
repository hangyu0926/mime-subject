String.prototype.trim = function() {
  return  this.replace(/^\s+|\s+$/g, '');
};
;function doCheckregister(){
	$("#loginIn").hide();
	$("#findPass").hide();
	$("#register").show();
};
;function doChecklogin(){
	$("#register").hide();
	$("#findPass").hide();
	$("#loginIn").show();
}
//登录
;function doLogin(){
	var userName=$("#login_username").val();
	var userPassword=$("#login_password").val();
	if(!global_validate.stringIsEmail($("#account-email").val().trim())){
		return
	};
	userPassword = hex_md5(userPassword);
	global_ajax("login",{
		"userMobile":userName,
		"password":userPassword
	},function(data){
		window.location.href="";
	},"post",function(data){
		global_dialog.error(data.desc,function(){
			closeAlertDialog();
		});
	})
};

;function findPass(){
	$("#loginIn").hide();
	$("#register").hide();
	$("#findPass").show();
}
//修改密码
;function doModifypass(){
	var user=$("#find_username").val();
	var oldPass=$("#find_passwordOld").val();
	var newPass=$("#find_passwordNew").val();
	var newAgain=$("#find_passwordNewagain").val();
	if(newPass==newAgain){
		
	}else{
		global_dialog.error("两次不一样");
		return;
	};
	global_ajax("passModify",{
		"userMobile":user,
		"oldPassword":oldPass,
		"newPassword":newPass
	},function(data){
		global_dialog.error("修改成功");
	},"post",function(data){
		global_dialog.error(data.desc);
	})
};
//注册
;function doRegister(){
	var username=$("#register_username").val();
	var password=$("#register_password").val();
	global_ajax("",{},function(data){
		global_dialog.error("注册成功",function(){
			closeAlertDialog();
			doChecklogin();
		});
		
	},"post",function(data){
		global_dialog.error(data.desc,function(){
			closeAlertDialog();
		});
	})
};