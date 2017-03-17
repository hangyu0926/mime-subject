var pageSize = 10; //每页数量
var search = ''; //搜索条件
var totalCount = 0; //最大页
//填充数据
;function accountView(condition,str) {
  global_ajax("getUserList", condition, function(data) {
    //搜索置为1
    if(str=="search"){
      $(".account_toolbar_page").text(1);
    };
    $("#accountTbody").empty();
    //最大页
    totalCount = Math.ceil((data.detailInfo.totalCount)/pageSize);
    $(".account_toolbar_totalCount").text(totalCount);
    //没有数据就返回
    if (totalCount == 0) {
      global_dialog.error("没有符合条件的数据", function() {
        closeAlertDialog();
      });
      return
    };
    $(".account_toolbar_totalCount").text(totalCount);
    //遍历数据
    var account='';
    $(data.detailInfo.userList).each(function(i, m) {
       account += '<tr><td>' + m.userName + '</td><td>' 
      			  + m.userMailAdd + '</td><td>' 
      			  + m.userMobile + '</td><td>' 
      			  + m.totalStock + '</td><td>' 
      			  + m.availableStock + '</td><td onclick="doModify(' + m.userId + ')">' 
      			  + '重置密码</td><td onclick="doDelete(' + m.userId + ')">' 
      			  + '删除</td></tr>'
    });
    $("#accountTbody").append(account);

  }, "post", function(data) {
    //失败清空
    if(str=="search"){
      $("#accountSearch").val('');
      search = ''
    }
    global_dialog.error(data.desc, function() {
      closeAlertDialog()
    });
  });
};
//重置密码
;function doModify(id) {
  // $("#modifyPassword").click(function() {
    global_ajax("resetUserPassword", { "userId": id }, function(data) {
      global_dialog.error("重置成功", function() {
        closeAlertDialog()
      });
    }, "post", function(data) {
      global_dialog.error(data.desc, function() {
        closeAlertDialog()
      });
    });
  // });
};
//删除用户
;function doDelete(id) {
  global_ajax("deleteUser", { "userId": id }, function(data) {
  	//提示删除成功
  	global_dialog.error("删除成功", function() {
        closeAlertDialog();
      });
  	//重新刷数据 判断当前页是不是最后一条
  	var page=$(".account_toolbar_page").text();
  	if($("#accountTbody tr").length==1 && page!==1){
  		--page;
  	};
  	$(".account_toolbar_page").text(page);
  	accountView({"keyWords":search,"pageSize":pageSize,"pageNo":page});
  }, "post", function(data) {
  	global_dialog.error(data.desc, function() {
        closeAlertDialog()
      });
  });
};
//上一页
;function accountPre() {
  var page = $(".account_toolbar_page").text();
  if (page == 1) {
    return
  };
  --page;
  $(".account_toolbar_page").text(page);
  accountView({ "keyWords": search, "pageSize": pageSize, "pageNo": page });
};
// 下一页
;function accountNext() {
  var page = $(".account_toolbar_page").text();
  if (page == totalCount) {
    return
  };
  ++page;
  $(".account_toolbar_page").text(page);
  accountView({ "keyWords": search, "pageSize": pageSize, "pageNo": page });
};
//搜索
;function accountSearch() {
  search = $("#accountSearch").val();
  accountView({ "keyWords": search, "pageSize": pageSize, "pageNo": 1 },"search");
}
// 刚进页面的时候执行一下
accountView({"keyWords":'',"pageSize":pageSize,"pageNo":1});
