/**
*global_dir   ：根目录地址
*global_route ：项目路径
*/
var global_dir = "/msts";
var global_route = {
    "releaseAuditList":"/stocks/releaseAuditList",//待审核订单-发布审核
    "backoutAuditList":"/stocks/backoutAuditList",//待审核订单-撤销审核
    "releaseAudit": "/stocks/releaseAudit",//发布审核通过/不通过
    "beConfirmedList":"/stocks/beConfirmedList",//待确认订单
    "confirmOrder":"/stocks/confirmOrder",//待确认订单
    "myBidding":"/stocks/myBidding",//我的竞拍
    "biddingList":"/stocks/biddingList",//竞拍列表
    "bidding":"/stocks/bidding",//竞拍标的
    "preBidding":"/stocks/preBidding",//准备竞拍
    "getMyOrder":"/stocks/getMyOrder",//我的发布
    "releaseOrder": "/stocks/releaseOrder",//发布新标的
    "backoutOrder": "/stocks/backoutOrder", //撤销订单
    "jumpReleaseOrder": "/stocks/jumpReleaseOrder",//跳转我的发布页
    'login': '/user/login',// 用户登录
    'passModify':'/user/modifyPassword',//修改密码
    'getUserList':'/user/getUserList',//获取用户信息
    'resetUserPassword':'/user/resetUserPassword',//修改用户密码
    'deleteUser':'/user/deleteUser',//删除用户
    'getUserList':'/user/getUserList',//获取用户列表
    'addUser':'/user/addUser',//新增账户
    'logout':'/user/logout',//登出
    'updateConfig':'/stocks/updateConfig'//竞拍配置
};
/**
*global_ajax
*urlKey： 发送请求地址下标
*params： 发送的数据
*callback： 成功后执行的回掉函数
*method： post or get
*error: 失败后执行的回掉函数
*/
var global_ajax = function(urlKey, params, callback, method, error) {
    var ajaxUrl =  global_dir + global_route[urlKey];
    if(method == "get" || method == "GET") {
    	ajaxUrl += "?";
    	for(var key in params) {
    		ajaxUrl += key + "=" + params[key] + "&";
    	}
    	ajaxUrl = ajaxUrl.subString(0, ajaxUrl.length-1);
    }
    $.ajax({
    	type: !method ? "post" : method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        url: ajaxUrl,
        data: (method == "get" || method == "GET")? "" : JSON.stringify(params),
        success: function(data) {
        	if(data.code == "0000" || data.code == "9016") {
        		callback(data);
        	} else {
        		error(data);
        	}
        }
    });
};