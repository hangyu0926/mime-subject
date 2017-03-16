/**
*global_dir   ：根目录地址
*global_route ：项目路径
*/
var global_dir = "";
var global_route = {};
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
        dataType: json,
        url: ajaxUrl,
        data: (method == "get" || method == "GET")? "" : JSON.stringify(params),
        success: function(data) {
        	if(data.code == "0000") {
        		callback(data);
        	} else {
        		error(data);
        	}
        }
    })
};