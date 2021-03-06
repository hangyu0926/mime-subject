$(function() {
/**
*左侧菜单高度始终适应真个屏幕
*/
    var winHeight = document.documentElement.clientHeight;
    $(".leftMenu").css("height", winHeight);
    $(window).resize(function(e) {
	    var winHeight = document.documentElement.clientHeight;
	    $(".leftMenu").css("height", winHeight);
    })
/**
*点击菜单时使其子菜单展示出来
*/
    $(".leftMenu>ul>li").on("click", function(e) {
    	$(this).addClass("active").siblings().removeClass("active");
    })
/**
*点击子菜单展示对应的页面
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
    	var urlSrc = $(this).attr("urlSrc");
    	window.location.href = urlSrc;
    })
/**
*阻止模态框弹出时出现滚动条
*/
    $(".modal").on("show.bs.modal", function(){
        $(this).css("overflow-y", "hidden");
        $("body").css({"overflow": "auto", "margin-right": "0px"});
    })
    //顶栏用户名
    var name = localStorage.getItem("name");
    $(".nav-head-userName").text(name);
    //权限1超管
    var permissionId = localStorage.getItem("permissionId");
    if(permissionId=="1"){
        // $(".accountManagement").hide();
        // $(".admin").hide();
    }else{
        $(".accountManagement").hide();
        $(".admin").hide();
    }
});

// 修改密码
;function doModifypass(str1, str2, str3) {
//判空
if (str3 == undefined) {
    if(str1.length == 0 || str2.length == 0) {
        global_dialog.error("请填写完整", function() {
            closeAlertDialog();
        });
        return
    };
} else {
    if (str1.length == 0 || str2.length == 0 || str3 == 0) {
        global_dialog.error("请填写完整", function() {
            closeAlertDialog();
        });
        return
    };
};
  //密码格式正不正确
if (str3 == undefined) {
    if (!global_validate.passwordIsOk(str1) || !global_validate.passwordIsOk(str2) ) {
        global_dialog.error("密码为6位数字", function() {
            closeAlertDialog();
        });
        return
    };
}else{
    if (!global_validate.passwordIsOk(str1) || !global_validate.passwordIsOk(str2) || !global_validate.passwordIsOk(str3)) {
        global_dialog.error("密码为6位数字", function() {
            closeAlertDialog();
        });
        return
    };
};
//两次输入一致判断
if (str1 == str2) {

} else {
    global_dialog.error("两次输入不一致", function() {
        closeAlertDialog();
    });
    return
};
if(str1=="123456"){
      global_dialog.error("不能修改为初始密码", function() {
        closeAlertDialog();
    });
    return
}
if(str2==str3){
    global_dialog.error("旧密码和新密码一致无法修改", function() {
        closeAlertDialog();
    });
    return
};
if (str3 == undefined) {
    str3 = '';
    str2=hex_md5(str2);
}else{
    str3=hex_md5(str3);
    str2=hex_md5(str2);
}
  
global_ajax("passModify", {
    "oldPassword": str3,
    "newPassword": str2
}, function(data) {
    doClear();
    $("#firstPwd-modal").modal("hide");
    $("#changePwd-modal").modal("hide");
    global_dialog.error("修改成功", function() {
        closeAlertDialog();
    });
}, "post", function(data) {
    global_dialog.error(data.desc, function() {
        closeAlertDialog();
        });
    });
};

//不是第一次进来
;function doNotfirst(){
    var oldPass=$("#originPwd").val().trim();
    var newPass=$("#nowPwd").val().trim();
    var newAgain=$("#nowPwdagain").val().trim();
    doModifypass(newPass,newAgain,oldPass);
}
// 清空修改密码框
;function doClear() {
    $("#originPwd").val("");
    $("#nowPwd").val("");
    $("#nowPwdagain").val("");
};
//登出
;function doLoginout() {
    global_ajax("logout", {}, function(data) {
        window.location.href = "login.html";
    })
};

