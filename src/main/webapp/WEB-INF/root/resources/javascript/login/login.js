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
	if($("#login_username").val().trim().length==0 || $("#login_password").val().trim().length==0){
		return
	}
	if(!global_validate.stringIsEmail($("#login_username").val().trim())){
		return
	};
	userPassword = hex_md5(userPassword);
	global_ajax("login",{
		"userMail":userName,
		"password":userPassword
	},function(data){
		localStorage.clear();
		var name=data.detailInfo.userName;
		localStorage.setItem("name", name);
		var permissionId=data.detailInfo.permissionId;
		localStorage.setItem("permissionId", permissionId);
		window.location.href="/views/goToAuction.html?id="+data.code;
	},"post",function(data){
		global_dialog.error(data.desc,function(){
			closeAlertDialog();
		});
	});
};

;function findPass(){
	$("#loginIn").hide();
	$("#register").hide();
	$("#findPass").show();
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