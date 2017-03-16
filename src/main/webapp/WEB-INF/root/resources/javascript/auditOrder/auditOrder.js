String.prototype.trim = function() {
  return  this.replace(/^\s+|\s+$/g, '');
};
;function accountAdd(){
	if($("#account-email").val().trim().length==0 || $("#account-name").val().trim().length==0 || $("#account-phone").val().trim().length==0 ||$("#account-gushu").val().trim().length==0 ||$("#account-ksgushu").val().trim().length==0){
		alert("aa");
		return;
	};
	if(!global_validate.stringIsEmail($("#account-email").val().trim())){
		alert("bb")
		return
	};
	if(!global_validate.stringIsMobile($("#account-phone").val().trim())){
		alert("cc");
		return
	}
};

;function accountReset(){

};