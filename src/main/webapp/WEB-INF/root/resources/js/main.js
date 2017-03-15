// 全局service请求路径
var global_projectName = '/xypms';// 同意配置项目名
var global_serviceUrl = {
    'login': '/user/login',											// 用户登录
    'loginOut': '/user/logout',										// 用户登出
    'updatePassword': '/user/modifyPassword',						// 修改密码
    'queryAccountList': '/employee/getUserList',					// 条件查询账户列表
    'deleteAccount': '/employee/deleteUser',						// 删除一条账户
    'resetAccountPassword': '/employee/resetUserPassword',		// 重置账户密码
    'batchImportAccount': '/employee/addUserList',				// 批量导入新账户
    'queryProjectList': '/project/getProjectList',				// 条件查询项目列表
    'queryOneProjectInfo': '/project/getProjectDetail',			// 查询项目详情
    'queryRecommendProjectList': '/project/getRecommendList',	// 查询推荐项目列表
    'addProject': '/project/addProject',							// 新增一个项目
    'updateProject': '/project/updateProject',					// 修改一个项目
    'issueAppraisal': '/project/addAppraisal',					// 发布评价
    'shareProject': '/project/shareProject',						// 项目分享
    'picUpload': '/project/picUploadToCos',						// 图片上传
    'fileUpload': '/project/attachUploadToCos',					// 附件上传
    'sharePush': '/project/shareProject'							// 项目分享推送
};

// 全局AJAX请求封装
function global_ajax(urlKey, params, callback, method) {
    var ajaxUrl = global_projectName + global_serviceUrl[urlKey];
    if (!!method && (method == 'get' || method == 'GET')) {
        ajaxUrl += '?';
        for (var key in params) {
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
        success: function (data) {
            if (data.code == '0000') {
                callback(data);
            } else if (data.code == '901') {
                window.location.href = '/xypms/views/login.html';
            } else {
                global_dialog.error(data.desc);
            }
        }
    });
}

// 全局分页栏封装
function global_pagination(id, totalCount, pageSize, pageIndex) {
    var pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : Math.ceil(totalCount / pageSize);// 总页数
    var pagination = $(id);
    pagination.find('.pagination-custom-pageCount').text(pageCount < 10 ? '0' + pageCount : pageCount);
    if (pageIndex == 1 && pageCount != 1 && pageCount != 0) {
        pagination.removeClass('xy-model-page-last').addClass('xy-model-page-first').removeClass('xy-model-page-only');
    } else if (pageIndex == pageCount && pageCount != 1 && pageCount != 0) {
        pagination.removeClass('xy-model-page-first').addClass('xy-model-page-last').removeClass('xy-model-page-only');
    } else {
        pagination.removeClass('xy-model-page-first').removeClass('xy-model-page-last').removeClass('xy-model-page-only');
    }
    if (pageCount == 1 || pageCount == 0) {
        pagination.addClass('xy-model-page-only');
    }
    pagination.find('.pagination-custom-pageIndex').text(pageIndex < 10 ? '0' + pageIndex : pageIndex);
    return pageCount;
}

function accountResultListConvert(resultList) {// 对账号结果集进行转换，null显示为空
    for (var i = 0; i < resultList.length; i++) {
        for (var key in resultList[i]) {
            if (resultList[i][key] == null) {
                resultList[i][key] = '';
            }
        }
    }
    return resultList
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
function checkMobileHasEqual(mobile, index) {
    var result = true;
    $.each($('#accountInput li'), function (i, m) {
        if (i != index) {
            var mv = $(m).find('input[data-name=mobile]').val();
            if (!!mv && mv == mobile) {
                result = false;
                return false;
            }
        }
    })
    return result;
}

// 检验邮箱号是否有重复
function checkEmailHasEqual(email, index) {
    var result = true;
    $.each($('#accountInput li'), function (i, m) {
        if (i != index) {
            var ev = $(m).find('input[data-name=email]').val();
            if (!!ev && ev == email) {
                result = false;
                return false;
            }
        }
    })
    return result;
}

// 根据输入文件地址，输出文件图标，只支持ppt/pdf/doc/wmv
function fileAddressToFileIcon(fileAddress) {
    var fileStrArr = fileAddress.split('.');
    var fileType = fileStrArr[fileStrArr.length - 1];
    if (fileType.toLowerCase() == 'ppt') {
        return 'bg-icon-ppt';
    } else if (fileType.toLowerCase() == 'pdf') {
        return 'bg-icon-ptf';
    } else if (fileType.toLowerCase() == 'doc' || fileType.toLowerCase() == 'docx') {
        return 'bg-icon-doc';
    } else if (fileType.toLowerCase() == 'wmv') {
        return 'bg-icon-wmv';
    } else {
        return '';
    }
}

// 账号列表中用户名：超出5个字显示...
function parseAccountUserName(userName) {
    if (global_validate.stringLessThanLength(userName, 5)) {
        return userName;
    } else {
        return userName.substr(0, 5) + '...';
    }
}

// 鼠标滚轮事件:delta > 0 = 向上滚动;delta < 0 = 向下滚动
var mouseWheelEvent = function (e) {
    e = e || window.event;
    var delta = 0;
    if (e.wheelDelta) {//IE/Opera/Chrome
        delta = e.wheelDelta;
    } else if (e.detail) {//Firefox
        delta = e.detail;
    }
    var scrollTop = $(window).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(window).height();
    if (!$('#accountQuery').is(':hidden')) {
        if (delta > 0) {
            if (scrollTop == 0) {
                $('#accountQuery_datagrid_pagination').find('.xy-model-page-prev').click();
            }
        } else if (delta < 0) {
            if (scrollTop + windowHeight == scrollHeight) {
                $('#accountQuery_datagrid_pagination').find('.xy-model-page-next').click();
            }
        }
    } else if (!$('#projectQuery').is(':hidden')) {
        if (delta > 0) {
            if (scrollTop == 0) {
                $('#projectQuery_datagrid_pagination').find('.xy-model-page-prev').click();
            }
        } else if (delta < 0) {
            if (scrollTop + windowHeight == scrollHeight) {
                $('#projectQuery_datagrid_pagination').find('.xy-model-page-next').click();
            }
        }
    }
}

// 账号相关内容，单例模式创建
var Account = (function () {
    var instantiated;

    function init() {
        return {
            accountQueryInit: function () {// 账号查看初始化
                // 账号列表信息获取
                var accountQuery = function (params) {
                    global_ajax('queryAccountList', params, function (data) {
                        var trStr = '';
                        for (var i = 0; i < accountResultListConvert(data.detailInfo.userList).length; i++) {
                            var trObj = data.detailInfo.userList[i];
                            trStr += '<li>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-createTime">' + (!trObj.createTime ? '--' : new Date(trObj.createTime).Format("yyyy-MM-dd hh:mm")) + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-privilegeLevel">' + trObj.permissionName + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-name">' + parseAccountUserName(trObj.userName) + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-email">' + trObj.userMailAdd + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-weixin">' + trObj.wechat + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-userName">' + trObj.userMobile + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-oldPsw">' + trObj.initPassword + '</span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-resetPsw" onclick="resetThisAccountPassword(\'' + trObj.userId + '\')"></span>';
                            trStr += '<span class="xy-index-body-content-accountQuery-list-delete" onclick="deleteThisAccount(\'' + trObj.userId + '\')"></span>';
                            trStr += '</li>';
                        }
                        $('#accountQuery_datagrid').empty().append(trStr);// 账号列表生成
                        $('#accountQuery_totalCount').empty().text(data.detailInfo.count);// 已生成账号名单数
                        window.accountQueryObject['pageCount'] = global_pagination('#accountQuery_datagrid_pagination', data.detailInfo.totalCount, 10, params.pageNo);// 分页栏生成
                    })
                };

                var defaultParams = {
                    keyWords: '',
                    pageSize: 10,
                    pageNo: 1,
                    permissionId: 0,
                    timeOrderType: 0
                };
                $('#page_title').text('企业用户-账户列表');

                $('#accountInput').hide();
                $('#projectInput').hide();
                $('#projectQuery').hide();
                $('#accountQuery').show();
                accountQuery(defaultParams);

                // 页面事件绑定
                $('#accountQuery_datagrid_pagination').find('.xy-model-page-prev').unbind('click').click(function () {// 上一页
                    if ($('#accountQuery_datagrid_pagination').hasClass('xy-model-page-first') || $('#accountQuery_datagrid_pagination').hasClass('xy-model-page-only')) {
                        return;
                    }
                    accountQueryObject.datagrid.params['pageNo'] = accountQueryObject.datagrid.params['pageNo'] - 1;
                    if (accountQueryObject.datagrid.params['pageNo'] == 1) {
                        $('#accountQuery_datagrid_pagination').addClass('xy-model-page-first');
                    }
                    $('#accountQuery_datagrid_pagination').removeClass('xy-model-page-last');
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQuery_datagrid_pagination').find('.xy-model-page-next').unbind('click').click(function () {// 下一页
                    if ($('#accountQuery_datagrid_pagination').hasClass('xy-model-page-last') || $('#accountQuery_datagrid_pagination').hasClass('xy-model-page-only')) {
                        return;
                    }
                    accountQueryObject.datagrid.params['pageNo'] = accountQueryObject.datagrid.params['pageNo'] + 1;
                    if (accountQueryObject.datagrid.params['pageNo'] == accountQueryObject.pageCount) {
                        $('#accountQuery_datagrid_pagination').addClass('xy-model-page-last');
                    }
                    $('#accountQuery_datagrid_pagination').removeClass('xy-model-page-first');
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQueryAuthorityLevel').unbind('click').click(function (event) {// 点击权限级别下拉列表
                    if ($('#accountQueryAuthorityLevel').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility') == 'visible') {
                        $('#accountQueryAuthorityLevel').removeClass('xy-index-body-content-accountQuery-list-header-privilegeLevel-click').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility', 'hidden');
                    } else {
                        $('#accountQueryAuthorityLevel').addClass('xy-index-body-content-accountQuery-list-header-privilegeLevel-click').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility', 'visible');
                    }
                    event.stopPropagation();
                })
                $('#accountQueryAuthorityLevel').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').find('span').unbind('click').click(function (event) {// 点击权限级别下拉列表
                    $('#accountQueryAuthorityLevel').removeClass('xy-index-body-content-accountQuery-list-header-privilegeLevel-click').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility', 'hidden');
                    var permissionId = parseInt($(this).attr('data-permissionId'));
                    accountQueryObject.datagrid.params['permissionId'] = permissionId;
                    accountQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                    event.stopPropagation();
                })
                $(document).click(function (event) {// 点击权限级别下拉列表
                    if (!$('#accountQuery').is(':hidden')) {
                        if ($('#accountQueryAuthorityLevel').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility') == 'visible') {
                            $('#accountQueryAuthorityLevel').removeClass('xy-index-body-content-accountQuery-list-header-privilegeLevel-click').find('.xy-index-body-content-accountQuery-list-header-privilegeLevel-select').css('visibility', 'hidden');
                        }
                    }
                })
                $('#accountQuerySearch').find('i').unbind('click').click(function () {// 点击搜索
                    // 参数获取，验证
                    var keyword = $('#accountQuerySearch').find('input').val();
                    accountQueryObject.datagrid.params['keyWords'] = $.trim(keyword);
                    accountQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQueryDatagridOrderByDate').unbind('click').click(function () {// 点击创建时间排序
                    var timeOrderType = parseInt($(this).find('i').attr('data-order'));
                    if (timeOrderType == 0) {
                        timeOrderType = 1;
                    } else {
                        timeOrderType = 0;
                    }
                    $(this).find('i').attr('data-order', timeOrderType);
                    accountQueryObject.datagrid.params['timeOrderType'] = timeOrderType;
                    accountQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })

                return {
                    params: defaultParams,
                    query: accountQuery
                }
            },
            accountInputInit: function () {// 账户录入初始化
                $('#page_title').text('企业用户-账户录入');

                $('#projectInput').hide();
                $('#projectQuery').hide();
                $('#accountQuery').hide();
                $('#accountInput').show();

                // 页面事件绑定
                $('#accountInput li>span:nth-child(1)').unbind('click').click(function (event) {// 点击每一个权限级别下拉列表
                    if ($(this).hasClass('xy-index-body-content-accountInput-select-checked')) {
                        $(this).removeClass('xy-index-body-content-accountInput-select-checked');
                    } else {
                        $('#accountInput li>span:nth-child(1).xy-index-body-content-accountInput-select-checked').removeClass('xy-index-body-content-accountInput-select-checked');
                        $(this).addClass('xy-index-body-content-accountInput-select-checked');
                    }
                    event.stopPropagation();
                })
                $('#accountInput li>span:nth-child(1)>.xy-index-body-content-accountInput-select>span').unbind('click').click(function () {// 点击每一个权限级别下拉列表
                    $('#accountInput li>span:nth-child(1)').removeClass('xy-index-body-content-accountInput-select-checked');
                    var str = $(this).text();
                    $(this).parent().parent().find('.xy-index-body-content-accountInput-select-show').text(str).attr('data-pid', $(this).attr('data-pid')).css('color', '#4f5354');
                    event.stopPropagation();
                });
                $(document).click(function () {// 点击每一个权限级别下拉列表
                    if (!$('#accountInput').is(':hidden')) {
                        $('#accountInput li>span:nth-child(1)').removeClass('xy-index-body-content-accountInput-select-checked');
                    }
                })
                $('#accountInput li .xy-index-body-content-accountInput-clear').unbind('click').click(function () {// 点击清除
                    var parent_tr = $(this).parent();
                    parent_tr.find('input').val('');
                    parent_tr.find('.xy-index-body-content-accountInput-select-show').text('请选择权限级别').attr('data-pid', 0).css('color', '#c1c1c1');
                    parent_tr.find('.xy-index-body-content-accountInput-tip').text('');
                })
                $('#accountInput_success').unbind('click').click(function () {// 批量录入成功后点击确认
                    $('#batchImportAccountSuccessDialog').hide();
                })
                $('#accountInputBatchInput').unbind('click').click(function () {
                    // 数据获取，验证
                    var dataList = [];
                    var isSubmit = true;// 是否能够提交
                    $('#accountInput li .xy-index-body-content-accountInput-tip').text('');
                    var isEmpty = true;// 判断是否有输入内容
                    $.each($('#accountInput li'), function (i, m) {
                        var tr = $(m);
                        var userMobile = $.trim(tr.find('input[data-name=mobile]').val());
                        var userMailAdd = $.trim(tr.find('input[data-name=email]').val());
                        var userName = $.trim(tr.find('input[data-name=userName]').val());
                        var permissionId = parseInt(tr.find('.xy-index-body-content-accountInput-select-show').attr('data-pid'));
                        if (!(userMobile == '' && userMailAdd == '' && userName == '' && permissionId == 0)) {
                            isEmpty = false;
                            return false;
                        }
                    })
                    if (isEmpty) {
                        global_dialog.error('最少录入一行才可录入！');
                        return;
                    }

                    $.each($('#accountInput li'), function (i, m) {
                        var tr = $(m);
                        var userMobile = $.trim(tr.find('input[data-name=mobile]').val());
                        var userMailAdd = $.trim(tr.find('input[data-name=email]').val());
                        var userName = $.trim(tr.find('input[data-name=userName]').val());
                        var permissionId = parseInt(tr.find('.xy-index-body-content-accountInput-select-show').attr('data-pid'));
                        var isPush = true;

                        if (global_validate.stringIsEmpty(userMobile) && global_validate.stringIsEmpty(userMailAdd) && global_validate.stringIsEmpty(userName) && permissionId == 0) {
                            return true;
                        }

                        if (permissionId == 0) {
                            isPush = false;
                            isSubmit = false;
                        }

                        if (!global_validate.stringIsChinese(userName)) {
                            isPush = false;
                            isSubmit = false;
                            if (userName != '') {
                                tr.find('[data-name=userName]').parent().find('span').text('格式错误');
                            }
                        }

                        if (!global_validate.stringIsMobile(userMobile)) {
                            isPush = false;
                            isSubmit = false;
                            if (userMobile != '') {
                                tr.find('[data-name=mobile]').parent().find('span').text('格式错误');
                            }
                        }

                        if (!global_validate.stringIsEmail(userMailAdd)) {
                            isPush = false;
                            isSubmit = false;
                            if (userMailAdd != '') {
                                tr.find('[data-name=email]').parent().find('span').text('格式错误');
                            }
                        }

                        if (isPush && !checkMobileHasEqual(userMobile, i)) {
                            isSubmit = false;
                            var span = tr.find('[data-name=mobile]').parent().find('span').text('重复录入');
                        }

                        if (isPush && !checkEmailHasEqual(userMailAdd, i)) {
                            isSubmit = false;
                            var span = tr.find('[data-name=email]').parent().find('span').text('重复录入');
                        }

                        if (isPush) {
                            dataList.push({
                                userMobile: userMobile,
                                userMailAdd: userMailAdd,
                                userName: userName,
                                permissionId: permissionId
                            });
                        }
                    })

                    if (dataList.length == 0) {
                        // global_dialog.error('最少录入一行才可录入！');
                        return;
                    }

                    if (isSubmit) {
                        var params = {
                            userList: dataList
                        }

                        // 数据提交，成功则弹框显示成功导入数据
                        global_ajax('batchImportAccount', params, function (data) {
                            // 弹框显示成功导入数据
                            $('#accountInput li .xy-index-body-content-accountInput-clear').click();// 清空
                            if (data.detailInfo.addUserList.length != 0) {
                                var trsStr = '';
                                for (var i = 0; i < data.detailInfo.addUserList.length; i++) {
                                    var trObj = data.detailInfo.addUserList[i];
                                    trsStr += '<li class="clearfix">';
                                    trsStr += '<span>' + (!trObj.createTime ? '--' : new Date(trObj.createTime).Format("yyyy-MM-dd hh:mm")) + '</span>';
                                    trsStr += '<span>' + trObj.permissionName + '</span>';
                                    trsStr += '<span>' + trObj.userName + '</span>';
                                    trsStr += '<span>' + trObj.userMailAdd + '</span>';
                                    trsStr += '<span>' + trObj.wechat + '</span>';
                                    trsStr += '<span>' + trObj.userMobile + '</span>';
                                    trsStr += '<span>' + trObj.initPassword + '</span>';
                                    trsStr += '</li>';
                                }
                                $('#batchImportAccountSuccessDialog_table').empty().append(trsStr);
                                $('#batchImportAccountSuccessDialog').show();
                            }
                            for (var i = 0; i < data.detailInfo.addFailList.length; i++) {
                                var failObj = data.detailInfo.addFailList[i];
                                var tr = $('#accountInput li').eq(i);
                                var permissionObj = {
                                    '4': 'GP',
                                    '3': '基石LP',
                                    '2': '一般LP',
                                    '5': '专家、顾问',
                                };
                                tr.find('.xy-index-body-content-accountInput-select-show').text(permissionObj[failObj.permissionId]).attr('data-pid', failObj.permissionId);
                                tr.find('[data-name=userName]').val(failObj.userName);
                                tr.find('[data-name=mobile]').val(failObj.userMobile);
                                tr.find('[data-name=email]').val(failObj.userMailAdd);
                                if (failObj.failReason == '手机号已存在') {
                                    tr.find('[data-name=mobile]').parent().find('span').text('已存在');
                                } else if (failObj.failReason == '邮箱已存在') {
                                    tr.find('[data-name=email]').parent().find('span').text('已存在');
                                }
                            }
                        })
                    }
                })
            }
        }
    }

    return {
        getInstance: function () {
            if (!instantiated) {
                instantiated = init();
            }
            return instantiated;
        }
    }
})();

var global_pageSize = 0;

function resetProjectQuerySearchParams() {
    $('#projectQuery_filter_dialog input[type=checkbox]').removeAttr('checked');
    $('#projectQuery_filter_dialog .chooseColor').removeClass('chooseColor');
}

// 项目相关内容，单例模式创建
var Project = (function () {
    var instantiated;

    function init() {
        return {
            projectQueryInit: function () {// 项目查看初始化
                // 项目列表信息获取
                var projectQuery = function (params) {
                    global_ajax('queryProjectList', params, function (data) {
                        var lisStr = '';
                        for (var i = 0; i < data.detailInfo.projectSummaryVOList.length; i++) {
                            var projectObj = data.detailInfo.projectSummaryVOList[i];
                            lisStr += '<div class="xy-index-body-content-projectQuery-info' + (projectObj.recommendedMark == 1 ? ' xy-index-body-content-projectQuery-good' : '') + '" onclick="showProjectInfoDialog(\'' + projectObj.projectId + '\')">';
                            lisStr += '<p class="xy-index-body-content-projectQuery-info-title">' + projectObj.projectName + '</p>';
                            lisStr += '<ul class="xy-index-body-content-projectQuery-info-infos">';
                            lisStr += '<li>项目分类：' + (global_validate.stringIsEmpty(projectObj.topCategory) ? '' : projectObj.topCategory + '；') + projectObj.secondCategory + '</li>';
                            lisStr += '<li>融资阶段：' + projectObj.financingStage + '</li>';
                            lisStr += '<li>建立时间：' + projectObj.buildTime + '</li>';
                            lisStr += '</ul>';
                            lisStr += '<img src="' + (projectObj.pictures[0] ? projectObj.pictures[0]["url"] : '') + '">';
                            lisStr += '<p class="xy-index-body-content-projectQuery-info-desc">' + projectObj.summary + '</p>';
                            lisStr += '</div>';
                        }
                        $('#projectQuery_datagrid').empty().append(lisStr);// 项目列表生成
                        window.projectQueryObject['pageCount'] = global_pagination('#projectQuery_datagrid_pagination', data.detailInfo.totalCount, global_pageSize, params.pageNo);// 分页栏生成
                    })
                }
                var defaultParams = {
                    keyword: '',
                    pageSize: global_pageSize,
                    pageNo: 1,
                    categoryQuery: [],
                    stageQuery: [],
                    progressQuery: []
                };

                $('#page_title').text('企业用户-项目列表');

                $('#projectInput').hide();
                $('#accountQuery').hide();
                $('#accountInput').hide();
                $('#projectQuery').show();
                // 清空筛选内容
                resetProjectQuerySearchParams();
                projectQuery(defaultParams);

                // 页面事件绑定
                $('#projectQuery_datagrid_pagination').find('.xy-model-page-prev').unbind('click').click(function () {// 上一页
                    if ($('#projectQuery_datagrid_pagination').hasClass('xy-model-page-first') || $('#projectQuery_datagrid_pagination').hasClass('xy-model-page-only')) {
                        return;
                    }
                    projectQueryObject.datagrid.params['pageNo'] = projectQueryObject.datagrid.params['pageNo'] - 1;
                    if (projectQueryObject.datagrid.params['pageNo'] == 1) {
                        $('#projectQuery_datagrid_pagination').addClass('xy-model-page-first');
                    }
                    $('#projectQuery_datagrid_pagination').removeClass('xy-model-page-last');
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('#projectQuery_datagrid_pagination').find('.xy-model-page-next').unbind('click').click(function () {// 下一页
                    if ($('#projectQuery_datagrid_pagination').hasClass('xy-model-page-last') || $('#projectQuery_datagrid_pagination').hasClass('xy-model-page-only')) {
                        return;
                    }
                    projectQueryObject.datagrid.params['pageNo'] = projectQueryObject.datagrid.params['pageNo'] + 1;
                    if (projectQueryObject.datagrid.params['pageNo'] == projectQueryObject.pageCount) {
                        $('#projectQuery_datagrid_pagination').addClass('xy-model-page-last');
                    }
                    $('#projectQuery_datagrid_pagination').removeClass('xy-model-page-first');
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('.xy-index-body-content-projectQuery-keyword').find('i').unbind('click').click(function () {// 点击搜索
                    // 参数获取，验证
                    var keyword = $(this).parent().find('input').val();
                    projectQueryObject.datagrid.params['keyword'] = $.trim(keyword);
                    projectQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('.xy-index-body-content-projectQuery-filter').unbind('click').click(function () {// 点击筛选
                    if ($(this).hasClass('xy-index-body-content-projectQuery-filter-checked')) {
                        $('.xy-index-body-content-projectQuery-filter').removeClass('xy-index-body-content-projectQuery-filter-checked');
                        $('#projectInput').hide();
                        $('#accountQuery').hide();
                        $('#accountInput').hide();
                        $('#projectQuery_filter_dialog').hide();
                        $('#projectQuery').show();
                    } else {
                        $('.xy-index-body-content-projectQuery-filter').addClass('xy-index-body-content-projectQuery-filter-checked');
                        $('#projectInput').hide();
                        $('#accountQuery').hide();
                        $('#accountInput').hide();
                        $('#projectQuery').hide();
                        $('#projectQuery_filter_dialog').show();
                        $("#projectQueryFilter_rzjd .xy-index-body-content-choose-contant-small-check").unbind('click').click(function () {
                            var label = $(this).parent().children('label');
                            if (label.hasClass('smallLabelChecked')) {
                                $(this).removeClass('smallLabelChecked');
                                label.removeClass('smallLabelChecked');
                            } else {
                                $(this).addClass('smallLabelChecked');
                                label.addClass('smallLabelChecked');
                            }
                        });
                        $("#projectQueryFilter_xmjz .xy-index-body-content-choose-contant-small-check").unbind('click').click(function () {
                            var label = $(this).parent().children('label');
                            if (label.hasClass('smallLabelChecked')) {
                                $(this).removeClass('smallLabelChecked');
                                label.removeClass('smallLabelChecked');
                            } else {
                                $(this).addClass('smallLabelChecked');
                                label.addClass('smallLabelChecked');
                            }
                        });
                        $(".netZchan .xy-index-body-content-choose-contant-small-check").unbind('click').click(function () {
                            var label = $(this).parent().children('label');
                            if (label.hasClass('smallLabelChecked')) {
                                $(this).removeClass('smallLabelChecked');
                                label.removeClass('smallLabelChecked');
                            } else {
                                $(this).addClass('smallLabelChecked');
                                label.addClass('smallLabelChecked');
                            }
                        });
                        $(".xy-index-body-content-choose-contant-big-check").unbind('click').click(function () {
                            var label = $(this).parent().children('label');
                            if (label.hasClass('bigLabelChecked')) {
                                label.removeClass('bigLabelChecked');
                                $(this).parent().parent().find('.xy-index-body-content-choose-contant-small-check.smallLabelChecked').click();
                            } else {
                                label.addClass('bigLabelChecked');
                                $(this).parent().parent().find('.xy-index-body-content-choose-contant-small-check:not(".smallLabelChecked")').click();
                            }
                        });
                    }
                })
                $('#projectQuery_filter_dialog input[type=checkbox][name=f_node]').unbind('click').click(function () {// 点击筛选中父节点
                    if ($(this).is(':checked')) {
                        $(this).parent().parent().find('div[class*=xmfl_]').find('input[type=checkbox][name=s_node]').prop('checked', 'checked');
                    } else {
                        $(this).parent().parent().find('div[class*=xmfl_]').find('input[type=checkbox][name=s_node]').removeAttr('checked');
                    }
                })
                $('#projectQuery_filter_dialog div[class*=xmfl_] input[type=checkbox][name=s_node]').unbind('click').click(function () {// 点击筛选中项目分类子节点，若是取消勾选，将父节点也取消
                    if (!$(this).is(':checked')) {
                        $(this).parent().parent().find('input[type=checkbox][name=f_node]').removeAttr('checked');
                    }
                })
                $('#projectQuery_filter_ok').unbind('click').click(function () {// 点击确认
                    // 获取参数
                    var categoryQuery = [];
                    var stageQuery = [];
                    var progressQuery = [];
                    $.each($('.netZchan input[type=checkbox]:checked'), function (i, m) {
                        if (!$(m).attr('data-value')) {
                            return true;
                        }
                        categoryQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $.each($('#projectQueryFilter_rzjd input[type=checkbox]:checked'), function (i, m) {
                        stageQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $.each($('#projectQueryFilter_xmjz input[type=checkbox]:checked'), function (i, m) {
                        progressQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $('.xy-index-body-content-projectQuery-filter').removeClass('xy-index-body-content-projectQuery-filter-checked');
                    $('#projectInput').hide();
                    $('#accountQuery').hide();
                    $('#accountInput').hide();
                    $('#projectQuery_filter_dialog').hide();
                    $('#projectQuery').show();

                    // 数据提交
                    projectQueryObject.datagrid.params['categoryQuery'] = categoryQuery;
                    projectQueryObject.datagrid.params['stageQuery'] = stageQuery;
                    projectQueryObject.datagrid.params['progressQuery'] = progressQuery;
                    projectQueryObject.datagrid.params['pageNo'] = 1;

                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })

                return {
                    params: defaultParams,
                    query: projectQuery
                }
            },
            projectInputInit: function () {// 项目录入初始化
                projectInputReset();
                $('#projectInput_goback').parent().hide();

                $('#accountQuery').hide();
                $('#accountInput').hide();
                $('#projectQuery').hide();
                $('#projectInput').show();

                // 页面事件绑定
                projectInputEventInit('add');
            }
        }
    }

    return {
        getInstance: function () {
            if (!instantiated) {
                instantiated = init();
            }
            return instantiated
        }
    }
})();

function projectInputEventInit(_type, _projectId) {
    $('#projectInput input.xy-index-body-content-projectInput-input').unbind('focus').focus(function (event) {
        $(this).parent().find('span').show();
    })
    $('#projectInput input.xy-index-body-content-projectInput-input').unbind('blur').blur(function (event) {
        if ($(this).val() == '') {
            $(this).parent().find('span').hide();
        }
    })
    $('#projectInput .xy-index-body-content-projectInput-select').unbind('click').click(function (event) {// 下拉列表事件
        if ($(this).hasClass('xy-index-body-content-projectInput-select-checked')) {
            $(this).removeClass('xy-index-body-content-projectInput-select-checked');
            if ($(this).attr('data-select') == '') {
                $(this).parent().children('span').hide();
            }
        } else {
            $('#projectInput .xy-index-body-content-projectInput-select.xy-index-body-content-projectInput-select-checked').removeClass('xy-index-body-content-projectInput-select-checked');
            $('#projectInput .xy-index-body-content-projectInput-select.xy-index-body-content-projectInput-select-checked[data-select=""]').parent().children('span').hide();
            $(this).addClass('xy-index-body-content-projectInput-select-checked').parent().children('span').show();
        }
        $('#projectInput .xy-index-body-content-projectInput-select-snd').css('visibility', 'hidden');
        $('#projectInput .xy-index-body-content-projectInput-select ul li').attr('style', '');
        if ($('#project_category').attr('data-select') == '' || ($('#project_category').attr('data-select') != 30 && $('#project_category').attr('data-select-snd') == '')) {
            $('#project_category').attr('data-select', '').attr('data-select-snd', '').css('color', '#c1c1c1').children('span').text('请选择项目分类').parent().parent().children('span').hide();
        }
        event.stopPropagation();
    })
    $('#projectInput .xy-index-body-content-projectInput-select ul li').unbind('click').click(function (event) {// 下拉列表事件
        if ($(this).hasClass('xy-index-body-content-projectInput-select-xmfl')) {
            var str = $(this).children('span').text();
            var select = $(this).attr('data-select');
            $(this).parent().parent().attr('data-select-snd', '');
            if (select == 30) {
                $('#projectInput .xy-index-body-content-projectInput-select ul li').attr('style', '');
                $('.xy-index-body-content-projectInput-select-xmfl').find('.xy-index-body-content-projectInput-select-snd').css('visibility', 'hidden');
                $('#projectInput .xy-index-body-content-projectInput-select').removeClass('xy-index-body-content-projectInput-select-checked');
                $(this).parent().parent().attr('data-select', select).attr('data-select-snd', '').css('color', '#4f5354').children('span').text(str).parent().parent().children('span').show();
            } else {
                $('#projectInput .xy-index-body-content-projectInput-select ul li').css('color', '#4f5354');
                $(this).css('color', '#dab866').parent().parent().attr('data-select', select).css('color', '#4f5354').children('span').text(str + ' ;').parent().parent().children('span').show();
                $('.xy-index-body-content-projectInput-select-xmfl').find('.xy-index-body-content-projectInput-select-snd').css('visibility', 'hidden');
                $(this).find('.xy-index-body-content-projectInput-select-snd').css('visibility', 'visible').children('span').unbind('click').click(function (event) {
                    var str = $(this).text();
                    var selectSnd = $(this).attr('data-select-snd');
                    var textStr = $(this).parent().parent().parent().parent().attr('data-select-snd', selectSnd).children('span').text().split(';')[0];
                    $(this).parent().parent().parent().parent().children('span').text(textStr + '; ' + str);
                    $('#projectInput .xy-index-body-content-projectInput-select ul li').attr('style', '');
                    $('.xy-index-body-content-projectInput-select-xmfl').find('.xy-index-body-content-projectInput-select-snd').css('visibility', 'hidden');
                    $('#projectInput .xy-index-body-content-projectInput-select.xy-index-body-content-projectInput-select-checked').removeClass('xy-index-body-content-projectInput-select-checked');
                    event.stopPropagation();
                });
            }
        } else {
            $('#projectInput .xy-index-body-content-projectInput-select').removeClass('xy-index-body-content-projectInput-select-checked');
            var str = $(this).text();
            var select = $(this).attr('data-select');
            $(this).parent().parent().attr('data-select', select).css('color', '#4f5354').find('span').text(str).parent().parent().children('span').show();
        }
        event.stopPropagation();
    })
    $(document).click(function () {// 下拉列表事件
        if (!$('#projectInput').is(':hidden')) {
            $('#projectInput .xy-index-body-content-projectInput-select.xy-index-body-content-projectInput-select-checked').removeClass('xy-index-body-content-projectInput-select-checked');
            $('#projectInput .xy-index-body-content-projectInput-select.xy-index-body-content-projectInput-select-checked[data-select=""]').parent().children('span').hide();
            $('#projectInput .xy-index-body-content-projectInput-select-snd').css('visibility', 'hidden');
            $('#projectInput .xy-index-body-content-projectInput-select ul li').attr('style', '');
            if ($('#project_category').attr('data-select') == '' || ($('#project_category').attr('data-select') != 30 && $('#project_category').attr('data-select-snd') == '')) {
                $('#project_category').attr('data-select', '').attr('data-select-snd', '').css('color', '#c1c1c1').children('span').text('请选择项目分类').parent().parent().children('span').hide();
            }
        }
    })
    $('#projectInput_goback').unbind('click').click(function () {
        projectInputReset();
        showProjectInfoDialog(_projectId);
    })
    $('#projectInput_submit').unbind('click').click(function () {// 点击提交
        var projectName = $.trim($('#project_projectName').val()), area = $('#project_area').attr('data-select'), leader = $.trim($('#project_leader').val()),
            contactNumber = $.trim($('#project_contactNumber').val()), email = $.trim($('#project_email').val()), financingSize = $('#project_financingSize').attr('data-select'),
            financingStage = $('#project_financingStage').attr('data-select'), topCategory = $('#project_category').attr('data-select'), secondCategory = $('#project_category').attr('data-select-snd'),
            progress = $('#project_progress').attr('data-select'), recommendedMark = $('#project_recommendedMark').attr('data-select'), summary = $.trim($('#project_summary').val()),
            detail = $.trim($('#project_detail').val()), pictureInfo = [], attachmentInfo = [];
        // 参数验证
        if (projectName == '' || area == '' || leader == '' || contactNumber == ''
            || email == '' || financingSize == '' || financingStage == '' || topCategory == ''
            || progress == '' || recommendedMark == '' || summary == '' || detail == '') {
            global_dialog.error('请仔细核对填写信息，所有均为必填项！');
            return;
        }
        if (topCategory != 30 && secondCategory == '') {
            global_dialog.error('请仔细核对填写信息，所有均为必填项！');
            return;
        }
        if (!global_validate.stringIsProjectName(projectName)) {
            global_dialog.error('项目名称必须为 1-10个汉字 ！');
            return;
        }
        if (!global_validate.stringIsChinese(leader)) {
            global_dialog.error('项目负责人必须为 2-20个汉字 ！');
            return;
        }
        if (!global_validate.stringIsMobile(contactNumber)) {
            global_dialog.error('联系电话必须为 正确的手机格式 ！');
            return;
        }
        if (!global_validate.stringIsEmail(email)) {
            global_dialog.error('邮箱格式必须为 12ab@AB.com/cn ！');
            return;
        }
        if (!global_validate.stringLessThanLength(summary, 30)) {
            global_dialog.error('项目概述必须为 1-30个字 ！');
            return;
        }
        if (!global_validate.stringLessThanLength(detail, 300)) {
            global_dialog.error('项目详情介绍必须为 1-300个字 ！');
            return;
        }

        // 获取图片数据
        if ($('#projectPictureUpload').parent().find('img').attr('src') == '../resources/image/Group 2891.png' || $('#projectPictureUpload').parent().find('img').attr('src') == '') {
            global_dialog.error('图片 必须且仅支持上传1张 ！');
            return;
        }
        var picObj = {};
        picObj['picName'] = $('#projectPictureUpload').parent().find('img').attr('alt');
        picObj['url'] = $('#projectPictureUpload').parent().find('img').attr('src');
        picObj['coverMark'] = 1;
        pictureInfo.push(picObj);

        var boo = true;
        $.each($('#projectAttachmentNoShareUpload').parent().find('li'), function (i, m) {
            if ($(m).hasClass('xy-index-body-content-projectInput-addAttachment-default')) {
                return true;
            }
            if ($(m).attr('data-ok') == 0) {
                boo = false;
                return false;
            }
            var attObj = {};
            attObj['attName'] = $(m).attr('data-attName');
            attObj['url'] = $(m).attr('data-attUrl');
            attObj['shareMark'] = 0;
            attachmentInfo.push(attObj);
        })

        $.each($('#projectAttachmentShareUpload').parent().find('li'), function (i, m) {
            if ($(m).hasClass('xy-index-body-content-projectInput-addAttachment-default')) {
                return true;
            }
            if ($(m).attr('data-ok') == 0) {
                boo = false;
                return false;
            }
            var attObj = {};
            attObj['attName'] = $(m).attr('data-attName');
            attObj['url'] = $(m).attr('data-attUrl');
            attObj['shareMark'] = 1;
            attachmentInfo.push(attObj);
        })
        if (!boo) {
            global_dialog.error('请等待所有附件上传完成在提交！');
            return;
        }

        // 数据提交
        if (_type == 'add') {
            global_ajax('addProject', {
                projectName: projectName,
                area: area,
                leader: leader,
                contactNumber: contactNumber,
                email: email,
                financingSize: financingSize,
                financingStage: financingStage,
                topCategory: (topCategory == 30 ? -1 : topCategory),
                secondCategory: (topCategory == 30 ? 30 : secondCategory),
                progress: progress,
                recommendedMark: recommendedMark,
                summary: summary,
                detail: detail,
                pictureInfo: pictureInfo,
                attachmentInfo: attachmentInfo
            }, function (data) {
                global_dialog.alert('新增项目成功！');
                // 清空所有录入框
                projectInputReset();
            })
        } else if (_type == 'update') {
            global_ajax('updateProject', {
                projectId: _projectId,
                projectName: projectName,
                area: area,
                leader: leader,
                contactNumber: contactNumber,
                email: email,
                financingSize: financingSize,
                financingStage: financingStage,
                topCategory: (topCategory == 30 ? -1 : topCategory),
                secondCategory: (topCategory == 30 ? 30 : secondCategory),
                progress: progress,
                recommendedMark: recommendedMark,
                summary: summary,
                detail: detail,
                pictureInfo: pictureInfo,
                attachmentInfo: attachmentInfo
            }, function (data) {
                global_dialog.alert('修改项目成功！');
                // 返回
                projectUpdateGoBack(_projectId);
            })
        }
    })
}
