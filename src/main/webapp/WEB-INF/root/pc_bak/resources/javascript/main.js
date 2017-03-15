// 全局菜单事件
function Menu() {
    return {
        init: function () {// 菜单初始化
            var menuIndex = 1;// 默认执行点击首页
            if (!!sessionStorage.getItem("xypm-menuIndex")) {
                menuIndex = parseInt(sessionStorage.getItem("xypm-menuIndex"));
            }
            $('[menu-index=' + menuIndex + ']').click();
            return this;
        },
        changeMenu: function (menuIndex) {// javascript切换菜单
            $('[menu-index=' + menuIndex + ']').click();
            return this;
        },
        bindEvent: function () {// 绑定菜单事件
            var menu = this;
            $('#menu').find('dl').click(function (event) {
                var isCheck = $(this).hasClass('menu-father-check');
                $('#menu').find('dl.menu-father-check').removeClass('menu-father-check');
                $(this).addClass('menu-father-check');
                // 没有子节点，直接执行父节点相应事件
                if ($(this).find('dd').length == 0) {
                    if (isCheck) {
                        return;
                    }
                    // 记录当前点击菜单
                    $('#projectQuery_filter_dialog').hide();
                    $('#projectQueryDetail').hide();
                    sessionStorage.setItem("xypm-menuIndex", $(this).find('dt').attr('menu-index'));
                    // 执行右侧刷新事件
                    $('#menu').find('dl').find('li.menu-son-check').removeClass('menu-son-check');
                    menu.refreshContent($(this).find('dt').attr('menu-url'));
                }
            });
            $('#menu').find('dl').find('li').click(function (event) {
                if ($(this).hasClass('menu-son-check')) {
                    return;
                }
                $('#projectQuery_filter_dialog').hide();
                $('#projectQueryDetail').hide();
                $('#menu').find('dl').find('li.menu-son-check').removeClass('menu-son-check');
                $(this).addClass('menu-son-check').parent().parent().parent().click();
                // 记录当前点击菜单
                sessionStorage.setItem("xypm-menuIndex", $(this).attr('menu-index'));
                // 执行右侧刷新事件
                menu.refreshContent($(this).attr('menu-url'));
                // 阻止事件冒泡
                event.stopPropagation();
            })
            return menu;
        },
        rightEventsRoutes: {// 右侧事件映射关系
            'index': function () {// 首页
                Index.getInstance().indexInit();
            },
            'projectInput': function () {// 项目录入
                Project.getInstance().projectInputInit();
            },
            'projectQuery': function () {// 项目查看
                if (!window.projectQueryObject) {
                    window.projectQueryObject = {};
                }
                window.projectQueryObject['datagrid'] = Project.getInstance().projectQueryInit();
            },
            'accountInput': function () {// 账户录入
                Account.getInstance().accountInputInit();
            },
            'accountQuery': function () {// 账户查看
                if (!window.accountQueryObject) {
                    window.accountQueryObject = {};
                }
                window.accountQueryObject['datagrid'] = Account.getInstance().accountQueryInit();
            }
        },
        refreshContent: function (menuUrl) {// 点击菜单项执行右侧刷新
            var menu = this;
            menu.rightEventsRoutes[menuUrl]();
            return menu;
        }
    };
}

// 全局验证事件
function Validate() {
    return {
        objectIsEmpty: function (obj) {
            if (obj == undefined || obj == null) {
                return true;
            }
            return false;
        },
        stringIsEmpty: function (str) {
            if (this.objectIsEmpty(str) || typeof str != 'string' || str == '') {
                return true;
            }
            return false;
        },
        arrayIsEmpty: function (arr) {
            if (this.objectIsEmpty(arr) || arr.length == 0) {
                return true;
            }
            return false;
        },
        objectIsNumber: function (obj) {
            if ((obj | 0) === obj) {
                return true;
            }
            return false;
        },
        stringLessThanLength: function (str, len, isEqual) {
            var stringCanEqual = true;
            if (this.objectIsEmpty(isEqual) && typeof isEqual == 'boolean') {
                stringCanEqual = isEqual;
            }
            if (stringCanEqual) {
                return !this.stringIsEmpty(str) && str.length <= len;
            } else {
                return !this.stringIsEmpty(str) && str.length < len;
            }
        },
        stringMoreThanLength: function (str, len, isEqual) {
            var stringCanEqual = true;
            if (this.objectIsEmpty(isEqual) && typeof isEqual == 'boolean') {
                stringCanEqual = isEqual;
            }
            if (stringCanEqual) {
                return !this.stringIsEmpty(str) && str.length >= len;
            } else {
                return !this.stringIsEmpty(str) && str.length > len;
            }
        },
        stringIsMobile: function (str) {
            var mobileReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            return mobileReg.test(str);
        },
        stringIsEmail: function (str) {
            var emailReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.com|\.cn)$/;
            return emailReg.test(str) && str.length <= 100;
        },
        passwordIsOk: function (str) {
            var passwordReg = /^[A-Za-z0-9]{6,18}$/;
            return passwordReg.test(str);
        },
        stringIsChinese: function (str) {
            var chineseReg = /^[\u4e00-\u9fa5]{2,20}$/;
            return chineseReg.test(str);
        },
        stringIsAppraisal: function (str) {
            var appraisalReg = /^[\u4e00-\u9fa5]{1,100}$/;
            return appraisalReg.test(str);
        },
        stringIsProjectName: function (str) {
            var appraisalReg = /^[\u4e00-\u9fa5]{1,10}$/;
            return appraisalReg.test(str);
        }
    };
}

// 全局弹框事件
function Dialog() {
    var alertDialog = ''
        + '<div class="alert-dialog" id="alertDialog">'
        + '<div class="alert-dialog-message">'
        + '<p id="alert-dialog-message"></p>'
        + '<button type="button" onclick="closeAlertDialog()" class="btn btn-default btn-sm">确定</button>'
        + '</div>'
        + '</div>';
    var confirmDialog = ''
        + '<div class="confirm-dialog" id="confirmDialog">'
        + '<div class="confirm-dialog-message">'
        + '<p id="confirm-dialog-message"></p>'
        + '<button type="button" onclick="closeConfirmDialog()" class="btn btn-default btn-sm">取消</button>'
        + '<button type="button" class="btn btn-default btn-sm confirm-dialog-button-ok">确定</button>'
        + '</div>'
        + '</div>';
    $('body').append(alertDialog + confirmDialog);
    window.closeAlertDialog = function () {
        $('#alertDialog').removeClass('is-visible');
    }
    window.closeConfirmDialog = function () {
        $('#confirmDialog').removeClass('is-visible');
    }
    window.dialogTime;// 弹框定时器
    return {
        alert: function (msg, isAutoClose) {
            isAutoClose = !!isAutoClose && typeof isAutoClose == 'boolean' ? isAutoClose : true;
            $('#alert-dialog-message').html(msg);
            $('#alertDialog').addClass('is-visible');
            if (window.dialogTime && typeof window.dialogTime == 'number') {
                clearTimeout(window.dialogTime);
            }
            if (isAutoClose) {
                window.dialogTime = setTimeout("closeAlertDialog()", 3000);
            }
        },
        error: function (msg, isAutoClose) {
            isAutoClose = !!isAutoClose && typeof isAutoClose == 'boolean' ? isAutoClose : true;
            this.alert('<div class="alert-dialog-message-error">' + msg + '</div>', isAutoClose);
        },
        confirm: function (msg, callback, params) {// callback：点击确认执行函数；params：函数执行需要的参数
            $('#confirm-dialog-message').html(msg);
            $('#confirmDialog').addClass('is-visible').find('.confirm-dialog-button-ok').unbind('click').click(function () {
                closeConfirmDialog();
                callback(params);
            });
        }
    };
}

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
    'fileUpload': '/project/attachUploadToCos'					// 附件上传
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
    pagination.find('.pagination-custom-pageCount').text(pageCount);
    if (pageIndex == 1) {
        pagination.find('.pagination-custom-prev').addClass('pagination-custom-disabled');
        pagination.find('.pagination-custom-next').removeClass('pagination-custom-disabled');
    }
    if (pageIndex == pageCount) {
        pagination.find('.pagination-custom-next').addClass('pagination-custom-disabled');
        pagination.find('.pagination-custom-prev').removeClass('pagination-custom-disabled');
    }
    if (pageCount == 1 || pageCount == 0) {
        pagination.find('.pagination-custom-next').addClass('pagination-custom-disabled');
        pagination.find('.pagination-custom-prev').addClass('pagination-custom-disabled');
    }
    pagination.find('.pagination-custom-pageIndex').val(pageCount == 0 ? 0 : pageIndex);
    return pageCount;
}

// 全局对象创建
$(function () {
    window.global_menu = new Menu();
    window.global_validate = new Validate();
    window.global_dialog = new Dialog();
})

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
    $.each($('tr[data-inputindex]'), function (i, m) {
        if ($(m).attr('data-inputindex') != index) {
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
    $.each($('tr[data-inputindex]'), function (i, m) {
        if ($(m).attr('data-inputindex') != index) {
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
        return '../resources/image/icon_ppt.png';
    } else if (fileType.toLowerCase() == 'pdf') {
        return '../resources/image/icon_pdf.png';
    } else if (fileType.toLowerCase() == 'doc' || fileType.toLowerCase() == 'docx') {
        return '../resources/image/icon_doc.png';
    } else if (fileType.toLowerCase() == 'wmv') {
        return '../resources/image/icon_wmv.png';
    } else {
        return '../resources/image/icon_file.png';
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
                            trStr += '<tr>';
                            trStr += '<input type="hidden" value="' + trObj.userId + '" />';
                            trStr += '<td><span class="icon-delete" onclick="deleteThisAccount(\'' + trObj.userId + '\')"></span></td>';
                            trStr += '<td>' + (!trObj.createTime ? '--' : new Date(trObj.createTime).Format("yyyy-MM-dd hh:mm")) + '</td>';
                            trStr += '<td>' + trObj.permissionName + '</td>';
                            trStr += '<td>' + parseAccountUserName(trObj.userName) + '</td>';
                            trStr += '<td>' + trObj.userMailAdd + '</td>';
                            trStr += '<td>' + trObj.wechat + '</td>';
                            trStr += '<td>' + trObj.userMobile + '</td>';
                            trStr += '<td>' + trObj.initPassword + '</td>';
                            trStr += '<td><button type="button" class="btn btn-info btn-sm" onclick="resetThisAccountPassword(\'' + trObj.userId + '\')">重置</button></td>';
                            trStr += '</tr>';
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
                $('#homePage').hide();
                $('#accountInput').hide();
                $('#projectInput').hide();
                $('#projectQuery').hide();
                $('#accountQuery').show();
                accountQuery(defaultParams);

                // 页面事件绑定
                $('#accountQuery_datagrid_pagination').find('.pagination-custom-prev').unbind('click').click(function () {// 上一页
                    if ($(this).hasClass('pagination-custom-disabled')) {
                        return;
                    }
                    accountQueryObject.datagrid.params['pageNo'] = accountQueryObject.datagrid.params['pageNo'] - 1;
                    if (accountQueryObject.datagrid.params['pageNo'] == 1) {
                        $(this).addClass('pagination-custom-disabled');
                    }
                    $('#accountQuery_datagrid_pagination').find('.pagination-custom-next').removeClass('pagination-custom-disabled');
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQuery_datagrid_pagination').find('.pagination-custom-next').unbind('click').click(function () {// 下一页
                    if ($(this).hasClass('pagination-custom-disabled')) {
                        return;
                    }
                    accountQueryObject.datagrid.params['pageNo'] = accountQueryObject.datagrid.params['pageNo'] + 1;
                    if (accountQueryObject.datagrid.params['pageNo'] == accountQueryObject.pageCount) {
                        $(this).addClass('pagination-custom-disabled');
                    }
                    $('#accountQuery_datagrid_pagination').find('.pagination-custom-prev').removeClass('pagination-custom-disabled');
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQuery_datagrid_pagination').find('.pagination-custom-pageIndex').unbind('keypress').keypress(function (event) {// 当前页输入框
                    if (event.keyCode == 13) {
                        var pageNo = parseInt($.trim($(this).val()));
                        if (global_validate.objectIsEmpty(pageNo) || !global_validate.objectIsNumber(pageNo)) {
                            global_dialog.error('请输入要查询的页数！');
                            return;
                        }
                        if (pageNo <= 0) {
                            global_dialog.error('要查询的页数不能小于1！');
                            return;
                        }
                        if (pageNo > window.accountQueryObject.pageCount) {
                            global_dialog.error('该页数不存在！');
                            return;
                        }
                        accountQueryObject.datagrid.params['pageNo'] = pageNo;
                        accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                    }
                }).unbind('blur').blur(function () {
                    var pageNo = $(this).val();
                    if (global_validate.stringIsEmpty(pageNo) || !global_validate.objectIsNumber(pageNo) || pageNo <= 0 || pageNo > window.accountQueryObject.pageCount) {
                        $(this).val(window.accountQueryObject.datagrid.params['pageNo']);
                    }
                })
                $('#accountQueryAuthorityLevel').unbind('click').click(function (event) {// 点击权限级别下拉列表
                    if ($('#accountQueryAuthorityLevelSelect').is(':hidden')) {
                        $('#accountQueryAuthorityLevelSelect').show();
                    } else {
                        $('#accountQueryAuthorityLevelSelect').hide();
                    }
                    event.stopPropagation();
                })
                $('#accountQueryAuthorityLevelSelect').find('li[class!=divider]').unbind('click').click(function (event) {// 点击权限级别下拉列表
                    var str = $(this).text();
                    if ($(this).text() == '全部') {
                        str = '权限级别';
                    }
                    $('#accountQueryAuthorityLevel').html(str + '<span class="caret"></span>');
                    $('#accountQueryAuthorityLevelSelect').hide();
                    $('#accountQueryPermissionId').val($(this).attr('data-permissionId'));
                    var permissionId = parseInt($('#accountQueryPermissionId').val());
                    accountQueryObject.datagrid.params['permissionId'] = permissionId;
                    accountQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                    event.stopPropagation();
                })
                $(document).unbind('click').click(function (event) {// 点击权限级别下拉列表
                    if (!$('#accountQuery').is(':hidden') && !$('#accountQueryAuthorityLevelSelect').is(':hidden')) {
                        $('#accountQueryAuthorityLevelSelect').hide();
                    }
                })
                $('#accountQuerySearch').unbind('click').click(function () {// 点击搜索
                    // 参数获取，验证
                    var keyword = $('#accountQueryKeyword').val();
                    accountQueryObject.datagrid.params['keyWords'] = keyword;
                    accountQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    accountQueryObject.datagrid.query(accountQueryObject.datagrid.params);
                })
                $('#accountQueryDatagridOrderByDate').removeClass('datagrid-caret-asc').unbind('click').click(function () {// 点击创建时间排序
                    var timeOrderType = 0;
                    if ($(this).hasClass('datagrid-caret-asc')) {
                        $(this).removeClass('datagrid-caret-asc');
                    } else {
                        $(this).addClass('datagrid-caret-asc');
                        timeOrderType = 1;
                    }
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
                $('#homePage').hide();
                $('#projectInput').hide();
                $('#projectQuery').hide();
                $('#accountQuery').hide();
                $('#accountInput').show();

                // 页面事件绑定
                $('button[data-toggle=dropdown]').unbind('click').click(function (event) {// 点击每一个权限级别下拉列表
                    var role = $(this).find('.sr-only').text();
                    if ($('ul.dropdown-menu[role=' + role + ']').is(':hidden')) {
                        $('ul.dropdown-menu:visible').hide();
                        $('ul.dropdown-menu[role=' + role + ']').show();
                    } else {
                        $('ul.dropdown-menu[role=' + role + ']').hide();
                    }
                    event.stopPropagation();
                })
                $('ul.dropdown-menu li[class!=divider]').unbind('click').click(function () {// 点击每一个权限级别下拉列表
                    var str = $(this).text();
                    var role = $(this).parent().attr('role');
                    $(this).parent().hide().parent().find('button.dropdown-text').html(str);
                    $('#accountInputPermissionId_' + role).val($(this).attr('data-permissionId'));
                    event.stopPropagation();
                });
                $(document).unbind('click').click(function () {// 点击每一个权限级别下拉列表
                    if (!$('#accountInput').is(':hidden')) {
                        $('ul.dropdown-menu:visible').hide();
                    }
                })
                $('button[data-name=reset]').unbind('click').click(function () {// 点击清除
                    var parent_tr = $(this).parent().parent();
                    var parent_tr_index = parent_tr.attr('data-inputIndex');
                    parent_tr.find('input[data-name=userName], input[data-name=mobile], input[data-name=email]').val('');
                    parent_tr.find('button.dropdown-text').html('请选择');
                    $('#accountInputPermissionId_menu' + parent_tr_index).val(0);
                    parent_tr.find('td.text-left span').text('');
                })
                $('#accountInput_success').unbind('click').click(function () {// 批量录入成功后点击确认
                    $('#batchImportAccountSuccessDialog').hide();
                })
                $('#accountInputBatchInput').unbind('click').click(function () {
                    // 数据获取，验证
                    var dataList = [];
                    var isSubmit = true;// 是否能够提交
                    $('tr[data-inputIndex] td.text-left span').text('');
                    var isEmpty = true;// 判断是否有输入内容
                    $.each($('tr[data-inputIndex]'), function (i, m) {
                        var tr = $(m);
                        var tr_index = tr.attr('data-inputIndex');
                        var userMobile = $.trim(tr.find('input[data-name=mobile]').val());
                        var userMailAdd = $.trim(tr.find('input[data-name=email]').val());
                        var userName = $.trim(tr.find('input[data-name=userName]').val());
                        var permissionId = parseInt(tr.find('input#accountInputPermissionId_menu' + tr_index).val());
                        if (!(userMobile == '' && userMailAdd == '' && userName == '' && permissionId == 0)) {
                            isEmpty = false;
                            return false;
                        }
                    })
                    if (isEmpty) {
                        global_dialog.error('最少录入一行才可录入！');
                        return;
                    }

                    $.each($('tr[data-inputIndex]'), function (i, m) {
                        var tr = $(m);
                        var tr_index = tr.attr('data-inputIndex');
                        var userMobile = $.trim(tr.find('input[data-name=mobile]').val());
                        var userMailAdd = $.trim(tr.find('input[data-name=email]').val());
                        var userName = $.trim(tr.find('input[data-name=userName]').val());
                        var permissionId = parseInt(tr.find('input#accountInputPermissionId_menu' + tr_index).val());
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
                                tr.find('[data-name=userName]').parent().find('span').text('姓名只能输入2-20个汉字');
                            }
                        }

                        if (!global_validate.stringIsMobile(userMobile)) {
                            isPush = false;
                            isSubmit = false;
                            if (userMobile != '') {
                                tr.find('[data-name=mobile]').parent().find('span').text('手机号格式不正确');
                            }
                        }

                        if (!global_validate.stringIsEmail(userMailAdd)) {
                            isPush = false;
                            isSubmit = false;
                            if (userMailAdd != '') {
                                tr.find('[data-name=email]').parent().find('span').text('邮箱号格式不正确');
                            }
                        }

                        if (isPush && !checkMobileHasEqual(userMobile, tr_index)) {
                            isSubmit = false;
                            var span = tr.find('[data-name=mobile]').parent().find('span').text('手机号已存在');
                        }

                        if (isPush && !checkEmailHasEqual(userMailAdd, tr_index)) {
                            isSubmit = false;
                            var span = tr.find('[data-name=email]').parent().find('span').text('邮箱号已存在');
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
                            $('button[data-name=reset]').click();// 清空
                            if (data.detailInfo.addUserList.length != 0) {
                                var trsStr = '';
                                for (var i = 0; i < data.detailInfo.addUserList.length; i++) {
                                    var trObj = data.detailInfo.addUserList[i];
                                    trsStr += '<tr>';
                                    trsStr += '<td>' + (!trObj.createTime ? '--' : new Date(trObj.createTime).Format("yyyy-MM-dd hh:mm")) + '</td>';
                                    trsStr += '<td>' + trObj.permissionName + '</td>';
                                    trsStr += '<td>' + trObj.userName + '</td>';
                                    trsStr += '<td>' + trObj.userMailAdd + '</td>';
                                    trsStr += '<td>' + trObj.wechat + '</td>';
                                    trsStr += '<td>' + trObj.userMobile + '</td>';
                                    trsStr += '<td>' + trObj.initPassword + '</td>';
                                    trsStr += '</tr>';
                                }
                                $('#batchImportAccountSuccessDialog_table').empty().append(trsStr);
                                $('#batchImportAccountSuccessDialog').show();
                            }
                            for (var i = 0; i < data.detailInfo.addFailList.length; i++) {
                                var failObj = data.detailInfo.addFailList[i];
                                var tr = $('tr[data-inputIndex=' + (i + 1) + ']');
                                tr.find('button.dropdown-text').html(tr.find('[data-permissionId=' + failObj.permissionId + ']').text());
                                $('#accountInputPermissionId_menu' + (i + 1)).val(failObj.permissionId);
                                tr.find('[data-name=userName]').val(failObj.userName);
                                tr.find('[data-name=mobile]').val(failObj.userMobile);
                                tr.find('[data-name=email]').val(failObj.userMailAdd);
                                if (failObj.failReason == '手机号已存在') {
                                    tr.find('[data-name=mobile]').parent().find('span').text(failObj.failReason);
                                } else if (failObj.failReason == '邮箱已存在') {
                                    tr.find('[data-name=email]').parent().find('span').text(failObj.failReason);
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
                            lisStr += '<li' + (projectObj.recommendedMark == 1 ? ' class="recommend-project"' : '') + ' onclick="showProjectInfoDialog(\'' + projectObj.projectId + '\')">'
                                + '<div class="projectQuery-datagrid-coverImage">'
                                + '<img src="' + (projectObj.pictures[0] ? projectObj.pictures[0]["url"] : '../image/project_default.png') + '" alt="' + (projectObj.pictures[0] ? projectObj.pictures[0]["picName"] : '') + '" />'
                                + '</div>'
                                + '<div class="projectQuery-datagrid-baseInfo clearfix">'
                                + '<div class="projectQuery-datagrid-baseInfo-projectName pull-left">'
                                + '<span>' + projectObj.projectName + '</span>'
                                + '</div>'
                                + '<div class="projectQuery-datagrid-baseInfo-createTime pull-left">'
                                + '<span class="projectQuery-datagrid-baseInfo-font">建立时间：</span><span>' + projectObj.buildTime + '</span>'
                                + '</div>'
                                + '<div class="projectQuery-datagrid-baseInfo-financingStage pull-left">'
                                + '<span class="projectQuery-datagrid-baseInfo-font">融资阶段：</span><span>' + projectObj.financingStage + '</span>'
                                + '</div>'
                                + '<div class="projectQuery-datagrid-baseInfo-projectType pull-left">'
                                + '<span class="projectQuery-datagrid-baseInfo-font">项目分类：</span><span>' + (global_validate.stringIsEmpty(projectObj.topCategory) ? '' : projectObj.topCategory + '；') + projectObj.secondCategory + '</span>'
                                + '</div>'
                                + '</div>'
                                + '<div class="projectQuery-datagrid-desc pull-left">'
                                + '<span class="projectQuery-datagrid-baseInfo-font">项目概述：</span><span>' + projectObj.summary + '</span>'
                                + '</div>'
                                + '<input id="projectQuery_datagrid_projectId" type="hidden" value="' + projectObj.projectId + '" />'
                                + '</li>';
                        }
                        $('#projectQuery_datagrid').empty().append(lisStr);// 项目列表生成
                        window.projectQueryObject['pageCount'] = global_pagination('#projectQuery_datagrid_pagination', data.detailInfo.totalCount, 10, params.pageNo);// 分页栏生成
                    })
                }
                var defaultParams = {
                    keyword: '',
                    pageSize: 10,
                    pageNo: 1,
                    categoryQuery: [],
                    stageQuery: [],
                    progressQuery: []
                };

                $('#page_title').text('企业用户-项目列表');
                $('#homePage').hide();
                $('#projectInput').hide();
                $('#accountQuery').hide();
                $('#accountInput').hide();
                $('#projectQuery').show();
                // 清空筛选内容
                resetProjectQuerySearchParams();
                projectQuery(defaultParams);

                // 页面事件绑定
                $('#projectQuery_datagrid_pagination').find('.pagination-custom-prev').unbind('click').click(function () {// 上一页
                    if ($(this).hasClass('pagination-custom-disabled')) {
                        return;
                    }
                    projectQueryObject.datagrid.params['pageNo'] = projectQueryObject.datagrid.params['pageNo'] - 1;
                    if (projectQueryObject.datagrid.params['pageNo'] == 1) {
                        $(this).addClass('pagination-custom-disabled');
                    }
                    $('#projectQuery_datagrid_pagination').find('.pagination-custom-next').removeClass('pagination-custom-disabled');
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('#projectQuery_datagrid_pagination').find('.pagination-custom-next').unbind('click').click(function () {// 下一页
                    if ($(this).hasClass('pagination-custom-disabled')) {
                        return;
                    }
                    projectQueryObject.datagrid.params['pageNo'] = projectQueryObject.datagrid.params['pageNo'] + 1;
                    if (projectQueryObject.datagrid.params['pageNo'] == projectQueryObject.pageCount) {
                        $(this).addClass('pagination-custom-disabled');
                    }
                    $('#projectQuery_datagrid_pagination').find('.pagination-custom-prev').removeClass('pagination-custom-disabled');
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('#projectQuery_datagrid_pagination').find('.pagination-custom-pageIndex').unbind('keypress').keypress(function (event) {// 当前页输入框
                    if (event.keyCode == 13) {
                        var pageNo = parseInt($.trim($(this).val()));
                        if (global_validate.objectIsEmpty(pageNo) || !global_validate.objectIsNumber(pageNo)) {
                            global_dialog.error('请输入要查询的页数！');
                            return;
                        }
                        if (pageNo <= 0) {
                            global_dialog.error('要查询的页数不能小于1！');
                            return;
                        }
                        if (pageNo > window.projectQueryObject.pageCount) {
                            global_dialog.error('该页数不存在！');
                            return;
                        }
                        projectQueryObject.datagrid.params['pageNo'] = pageNo;
                        projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                    }
                }).unbind('blur').blur(function () {
                    var pageNo = $(this).val();
                    if (global_validate.stringIsEmpty(pageNo) || !global_validate.objectIsNumber(pageNo) || pageNo <= 0 || pageNo > window.projectQueryObject.pageCount) {
                        $(this).val(window.projectQueryObject.datagrid.params['pageNo']);
                    }
                })
                $('#projectQuerySearch').unbind('click').click(function () {// 点击搜索
                    // 参数获取，验证
                    var keyword = $.trim($('#projectQueryKeyWord').val());
                    projectQueryObject.datagrid.params['keyword'] = keyword;
                    projectQueryObject.datagrid.params['pageNo'] = 1;

                    // 数据提交，列表刷新
                    projectQueryObject.datagrid.query(projectQueryObject.datagrid.params);
                })
                $('#projectQuery_filter').unbind('click').click(function () {// 点击筛选
                    $('#projectQuery_filter_dialog').show();
                })
                $('#projectQuery_filter_hide').unbind('click').click(function () {// 点击取消筛选
                    $('#projectQuery_filter_dialog').hide();
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
                    $.each($('#projectQueryFilter_xmfl input[type=checkbox][name=s_node]:checked'), function (i, m) {
                        categoryQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $.each($('#projectQueryFilter_rzjd input[type=checkbox][name=s_node]:checked'), function (i, m) {
                        stageQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $.each($('#projectQueryFilter_xmjz input[type=checkbox][name=s_node]:checked'), function (i, m) {
                        progressQuery.push(parseInt($(m).attr('data-value')));
                    })
                    $('#projectQuery_filter_dialog').hide();

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
                $('#page_title').text('企业用户-项目录入');
                projectInputReset();
                $('#homePage').hide();
                $('#accountQuery').hide();
                $('#accountInput').hide();
                $('#projectQuery').hide();
                $('#projectInput_back').hide();
                $('#projectInput').show();

                // 页面事件绑定
                $('#projectInput .dropdown-toggle').unbind('click').click(function (event) {// 下拉列表事件
                    if ($(this).parent().find('.dropdown-menu').is(':hidden')) {
                        $('#projectInput .dropdown-menu').hide();
                        $(this).parent().find('.dropdown-menu').show();
                    } else {
                        $('#projectInput .dropdown-menu').hide();
                    }
                    event.stopPropagation();
                })
                $('#projectInput .dropdown-menu').unbind('click').click(function (event) {// 下拉列表事件
                    event.stopPropagation();
                })
                $('#projectInput .dropdown-menu li[class!=divider]').unbind('click').click(function (event) {// 下拉列表事件
                    var value = $(this).attr('data-value');
                    var text = $(this).text();
                    var oldValue = $('#project_topCategory').val();
                    var btnGroup = $(this).parent().hide().parent();
                    btnGroup.find('.dropdown-toggle').html(text + '<span class="caret"></span>');
                    btnGroup.find('input[type=hidden]').val(value);
                    if ($(this).parent().parent().attr('id') == 'projuceInput_topCategory_select') {
                        if (value == oldValue) {
                            return;
                        }
                        var ulStr = '';
                        $('#projuceInput_secondCategory_select').find('input[type=hidden]').val('');
                        $('#projuceInput_secondCategory_select').find('.dropdown-toggle').html('请选择<span class="caret"></span>');
                        if (value == 1) {
                            ulStr += '<li data-value="7"><a href="javascript:void(0);">消费金融</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="8"><a href="javascript:void(0);">现金贷</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="9"><a href="javascript:void(0);">供应链</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="10"><a href="javascript:void(0);">ABS</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="11"><a href="javascript:void(0);">催收</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="12"><a href="javascript:void(0);">保理</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="13"><a href="javascript:void(0);">租赁</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="14"><a href="javascript:void(0);">典当</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 2) {
                            ulStr += '<li data-value="15"><a href="javascript:void(0);">交易所</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="16"><a href="javascript:void(0);">外汇</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="17"><a href="javascript:void(0);">债券</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="18"><a href="javascript:void(0);">贵金属</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 3) {
                            ulStr += '<li data-value="19"><a href="javascript:void(0);">财富管理</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="20"><a href="javascript:void(0);">P2P</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="21"><a href="javascript:void(0);">众筹</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 4) {
                            ulStr += '<li data-value="22"><a href="javascript:void(0);">经济</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="23"><a href="javascript:void(0);">投行</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="24"><a href="javascript:void(0);">资管</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 5) {
                            ulStr += '<li data-value="25"><a href="javascript:void(0);">渠道</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="26"><a href="javascript:void(0);">产品</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 6) {
                            ulStr += '<li data-value="27"><a href="javascript:void(0);">银行</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="28"><a href="javascript:void(0);">证券</a></li>';
                            ulStr += '<li role="separator" class="divider"></li>';
                            ulStr += '<li data-value="29"><a href="javascript:void(0);">保险</a></li>';
                            $('#projuceInput_secondCategory_select').show().find('.dropdown-menu').empty().append(ulStr);
                        } else if (value == 30) {
                            $('#projuceInput_secondCategory_select').hide().find('.dropdown-menu').empty();
                        }
                        $('#projuceInput_secondCategory_select').find('.dropdown-menu li[class!=divider]').unbind('click').click(function () {
                            var value = $(this).attr('data-value');
                            var text = $(this).text();
                            var btnGroup = $(this).parent().hide().parent();
                            btnGroup.find('.dropdown-toggle').html(text + '<span class="caret"></span>');
                            btnGroup.find('input[type=hidden]').val(value);
                            event.stopPropagation();
                        })
                    }
                    event.stopPropagation();
                })
                $(document).unbind('click').click(function () {// 下拉列表事件
                    $('#projectInput .dropdown-menu').hide();
                })
                $('#projectInput_submit').unbind('click').click(function () {// 点击提交
                    var projectName = $.trim($('#project_projectName').val()), area = $('#project_area').val(), leader = $.trim($('#project_leader').val()),
                        contactNumber = $.trim($('#project_contactNumber').val()), email = $.trim($('#project_email').val()), financingSize = $('#project_financingSize').val(),
                        financingStage = $('#project_financingStage').val(), topCategory = $('#project_topCategory').val(), secondCategory = $('#project_secondCategory').val(),
                        progress = $('#project_progress').val(), recommendedMark = $('#project_recommendedMark').val(), summary = $.trim($('#project_summary').val()),
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
                    if ($('#projectPictureUpload').parent().find('.projectInput-projectInfo-pictures-uploadArea-pic').length != 1) {
                        global_dialog.error('图片 必须且仅支持上传1张 ！');
                        return;
                    }
                    $.each($('#projectPictureUpload').parent().find('.projectInput-projectInfo-pictures-uploadArea-pic'), function (i, m) {
                        var picObj = {};
                        picObj['picName'] = $(m).attr('data-picName');
                        picObj['url'] = $(m).find('img').attr('src');
                        picObj['coverMark'] = 0;
                        if (i == 0) {
                            picObj['coverMark'] = 1;
                        }
                        pictureInfo.push(picObj);
                    })

                    var boo = true;
                    $.each($('#projectAttachmentNoShareUpload').parent().find('.projectInput-projectInfo-attachment'), function (i, m) {
                        if ($(m).attr('data-ok') == 0) {
                            boo = false;
                            return false;
                        }
                        var attObj = {};
                        attObj['attName'] = $(m).find('img.attachmentType').attr('data-attName');
                        attObj['url'] = $(m).find('img.attachmentType').attr('data-attUrl');
                        attObj['shareMark'] = 0;
                        attachmentInfo.push(attObj);
                    })

                    $.each($('#projectAttachmentShareUpload').parent().find('.projectInput-projectInfo-attachment'), function (i, m) {
                        if ($(m).attr('data-ok') == 0) {
                            boo = false;
                            return false;
                        }
                        var attObj = {};
                        attObj['attName'] = $(m).find('img.attachmentType').attr('data-attName');
                        attObj['url'] = $(m).find('img.attachmentType').attr('data-attUrl');
                        attObj['shareMark'] = 1;
                        attachmentInfo.push(attObj);
                    })
                    if (!boo) {
                        global_dialog.error('请等待所有附件上传完成在提交！');
                        return;
                    }

                    // 数据提交
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
                })
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

var Index = (function () {
    var instantiated;

    function init() {
        return {
            indexInit: function () {
                $('#projectInput').hide();
                $('#accountQuery').hide();
                $('#accountInput').hide();
                $('#projectQuery').hide();
                $('#homePage').show();
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
