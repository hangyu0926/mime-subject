$(function() {
/**
*isHistory:               是否为历史返回记录
*getConfirmOrderDataJson：确认订单》》发送的数据
*backoutAuditDatajson：   待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = false;
    var winHeight = document.documentElement.clientHeight;
    var getConfirmOrderDataJson = {orderNo: ""};
    var backoutAuditDatajson = {
            pageSize: 10,
            pageNo: 1
        };
/**
*发布审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getReleaseAudit = function(toEmpty){
        global_ajax("releaseAuditList", getReleaseAuditDataJson, function(data) {
            var len = data.detailInfo.orderList.length;
            var orderList = null;
            var list = null;
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderList[i];
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='userName'>发布人:"+orderList.userName+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stocksAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initialPrice+"</span>"
                    + "<span class='createTime'>提交时间:"+orderList.createTime+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + "<div class='audit-btn'>"
                    + "<span class='audit-true active'>审核通过</span>"
                    + "<span class='audit-false'>审核不通过</span>"
                    + "</div>"
                    + "<div class='auditIdea'>"
                    + "<input type='text' name='auditIdea-area' class='auditIdea-area'>"
                    + "<span>请输入您的意见:</span>"
                    + "</div>"
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".issuedAudit").empty();
            } else {
                return;
            }
            $(".issuedAudit").append(list);
            $(".issuedAudit").attr("maxPage", Math.ceil(data.detailInfo.orderList.totalCount/10));
        }, "POST", function(data) {
            window.global_dialog.error(data.desc,function(){
                closeAlertDialog()
            });
        })
    };

/**
*撤销审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/
    var getBackoutAudit = function(toEmpty){
        global_ajax("backoutAuditList", backoutAuditDatajson, function(data) {
            var len = data.detailInfo.orderList.length;
            var orderList = null;
            var list = null;
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderList[i];
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='userName'>发布人:"+orderList.userName+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stocksAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initialPrice+"</span>"
                    + "<span class='createTime'>提交时间:"+orderList.createTime+"</span>"
                    + "<span class='saleTime'>上架时间:"+orderList.saleTime+"</span>"
                    + "<span class='orderState'>订单状态:"+orderList.orderState+"</span>"
                    + "<span class='biddingPrice'>当前竞价:"+orderList.biddingPrice+"</span>"
                    + "<span class='userName'>当前持有人:"+orderList.userName+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + "<div class='audit-btn'>"
                    + "<span class='audit-true active'>审核通过</span>"
                    + "<span class='audit-false'>审核不通过</span>"
                    + "</div>"
                    + "<div class='auditIdea'>"
                    + "<input type='text' name='auditIdea-area' class='auditIdea-area'>"
                    + "<span>请输入您的意见:</span>"
                    + "</div>"
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".cancelAudit").empty();
            } else {
                return;
            }
            $(".cancelAudit").append(list);
            $(".cancelAudit").attr("maxPage", Math.ceil(data.detailInfo.orderList.totalCount/10));
        }, "POST", function(data) {
            window.global_dialog.error(data.desc,function(){
                closeAlertDialog()
            });
        });
    };
/**
*发布审核通过不通过
*/
    $(".contentRight").on("click", ".resultInfo>.audit-btn>.audit-true", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo
            };
        global_ajax("confirmOrder", sendData, function(data) {
            return;
        }, "POST", function(data){
            window.global_dialog.error(data.desc,function(){
                closeAlertDialog()
            });
        });
    })
/**
*判断是否为浏览器返回历史纪录加载数据
*/
    if(!isHistory) {
        //getReleaseAudit(true);
        //getBackoutAudit(true);
    } else {
        //window.sessionStorage.getItem();
        // if(window.sessionStorage.getItem("issuedAudit")) {
        //     $(".issuedAudit").removeClass("hide");
        //     $(".cancelAudit").addClass("hide");
        // } else {
        //     $(".issuedAudit").addClass("hide");
        //     $(".cancelAudit").removeClass("hide");
        // }
    }
/**
*页面滚动到当前页面最底部时加载下一页
*issueBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){
        var issueBottom = "";
        var cancelBottom = "";
        window.onscroll = function(e) {
            if($(".issuedAudit").hasClass("hide")) {
                var cancelBottom = document.querySelector(".cancelAudit").getBoundingClientRect().bottom;
                if(issueBottom == winHeight - 40) {
                    backoutAuditDatajson.pageNo += 1;
                    if(backoutAuditDatajson.pageNo >= parseInt($(".cancelAudit").attr("maxPage"))) {
                        backoutAuditDatajson.pageNo = parseInt($(".cancelAudit").attr("maxPage"));
                    }
                    getBackoutAudit();
                }
            } else {
                var issueBottom = document.querySelector(".issuedAudit").getBoundingClientRect().bottom;
                if(issueBottom == winHeight - 40) {
                    getReleaseAuditDataJson.pageNo += 1;
                    if(getReleaseAuditDataJson.pageNo >= parseInt($(".issuedAudit").attr("maxPage"))) {
                        getReleaseAuditDataJson.pageNo = parseInt($(".issuedAudit").attr("maxPage"));
                    }
                    getReleaseAudit();
                }
            }
        };
        
    }
})