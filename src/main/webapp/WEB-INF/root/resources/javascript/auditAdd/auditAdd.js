;function accountAdd(){
	var email=$("#account-email").val().trim();
	var name=$("#account-name").val().trim();
	var phone=$("#account-phone").val().trim();
	var gushu=$("#account-gushu").val().trim();
	var ksgushu=$("#account-ksgushu").val().trim();
	if(email.length==0 || name.length==0 || phone.length==0 ||gushu.length==0 ){
		return;
	};
	if(!global_validate.stringIsEmail(email) ){
		global_dialog.error("邮箱格式不正确",function(){
			closeAlertDialog();
		});
		return
	};
	if(!global_validate.stringIsMobile(phone) ){
		global_dialog.error("手机格式不正确",function(){
			closeAlertDialog();
		});
		return
	};
	if(ksgushu.length==0){
		ksgushu=gushu;
	};
	gushu=parseInt(gushu);
	ksgushu=parseInt(ksgushu);
	global_ajax("addUser",{
		"userMobile":phone,
		"userMailAdd":email,
		"userName":name,
		"totalStock":gushu,
		"availableStock":ksgushu
	},function(data){
		$("input").each(function(i,m){
				$(m).val("");
			});
		global_dialog.error("新增成功",function(){
			closeAlertDialog();
		});
	},"post",function(data){
		global_dialog.error(data.desc,function(){
			closeAlertDialog();
		});
	});
};

;function accountReset(){

};