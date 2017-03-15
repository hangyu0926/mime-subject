// 全局service请求路径
var global_projectName = '/xypms';// 同意配置项目名
var global_serviceUrl = {
	'login': '/user/wechatLogin',// 用户登录
	'updatePassword': '/user/modifyPassword',//修改密码
	"getProjectDetail":"/project/getProjectDetail", //详情内容
	"getRecommendList":"/project/getRecommendList", //推荐列表
	"addAppraisal":"/project/addAppraisal",//发布评价
	"sendEmail":"/project/sendEmail",
	"queryRecommendProjectList": "/project/commentedProjects",
	"getProjectList": "/project/getProjectList"
};

// 全局AJAX请求封装
function global_ajax(urlKey, params, callback, method,error){
	var ajaxUrl = global_projectName + global_serviceUrl[urlKey];
	if(!!method && (method == 'get' || method == 'GET')){
		ajaxUrl += '?';
		for(var key in params){
			ajaxUrl += key + '=' + params[key] + '&';
		}
		ajaxUrl = ajaxUrl.substring(0, ajaxUrl.length - 1);
	}
	$.ajax({
		type: !method ? "post" : method,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		url: ajaxUrl,
		async: true,
		crossDomain: true,
		data: !!method && (method == 'get' || method == 'GET') ? '' : JSON.stringify(params),
		success: function(data){
			if(data.code == '0000'){
				callback(data);
			}else{
				error(data);
			}
		}
	});
}

// 全局分页栏封装
function global_pagination(id, totalCount, pageSize, pageIndex){
	var pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : Math.ceil(totalCount / pageSize);// 总页数
	var pagination = $(id);
	pagination.find('.pagination-custom-pageCount').text(pageCount < 10 ? '0' + pageCount : pageCount);
	if(pageIndex == 1 && pageCount != 1 && pageCount != 0){
		pagination.removeClass('xy-model-page-last').addClass('xy-model-page-first').removeClass('xy-model-page-only');
	}else if(pageIndex == pageCount && pageCount != 1 && pageCount != 0){
		pagination.removeClass('xy-model-page-first').addClass('xy-model-page-last').removeClass('xy-model-page-only');
	}else{
		pagination.removeClass('xy-model-page-first').removeClass('xy-model-page-last').removeClass('xy-model-page-only');
	}
	if(pageCount == 1 || pageCount == 0){
		pagination.addClass('xy-model-page-only');
	}
	pagination.find('.pagination-custom-pageIndex').text(pageIndex < 10 ? '0' + pageIndex : pageIndex);
	return pageCount;
}

// 时间格式化
Date.prototype.Format = function (fmt) { //author: meizz
	var o = {
		"M+": this.getMonth() + 1, //月份
		"d+": this.getDate(), //日
		"h+": this.getHours(), //小时
		"m+": this.getMinutes(), //分
		"s+": this.getSeconds(), //秒
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度
		"S": this.getMilliseconds() //毫秒
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

// 检验手机号是否有重复
function checkMobileHasEqual(mobile, index){
	var result = true;
	$.each($('#accountInput li'), function(i, m){
		if(i != index){
			var mv = $(m).find('input[data-name=mobile]').val();
			if(!!mv && mv == mobile){
				result = false;
				return false;
			}
		}
	})
	return result;
}

// 检验邮箱号是否有重复
function checkEmailHasEqual(email, index){
	var result = true;
	$.each($('#accountInput li'), function(i, m){
		if(i != index){
			var ev = $(m).find('input[data-name=email]').val();
			if(!!ev && ev == email){
				result = false;
				return false;
			}
		}
	})
	return result;
}
var global_pageSize = 0;

function resetProjectQuerySearchParams(){
	$('#projectQuery_filter_dialog input[type=checkbox]').removeAttr('checked');
	$('#projectQuery_filter_dialog .chooseColor').removeClass('chooseColor');
}


