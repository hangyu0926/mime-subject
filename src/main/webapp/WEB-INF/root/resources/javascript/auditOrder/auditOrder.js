$(function() {
/**
*isHistory:               是否为历史返回记录
*getReleaseAuditDataJson：待审核订单-发布审核》》发送的数据
*backoutAuditDatajson：   待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = (window.location.href.indexOf("?") == -1) ? false : true;
    var winHeight = document.documentElement.clientHeight;
    var getReleaseAuditDataJson = {
            pageSize: 4,
            pageNo: 1
        };
    var backoutAuditDatajson = {
            pageSize: 4,
            pageNo: 1
        };
/**
*发布审核和撤销审核页面的隐藏和实现 
*/
    $(".content-title>span").on("click", function(e) {
        var index = $(this).index();
        $(this).addClass("active").siblings().removeClass("active");
    	if(index == 0) {
    		$(".issuedAudit").removeClass("hide");
    		$(".cancelAudit").addClass("hide");
            window.sessionStorage.setItem("issuedAudit", 0);
    	} else if(index == 1) {
    		$(".issuedAudit").addClass("hide");
    		$(".cancelAudit").removeClass("hide");
            window.sessionStorage.setItem("issuedAudit", 1);
    	}
    })
/**
*发布审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getReleaseAudit = function(toEmpty){
        global_ajax("releaseAuditList", getReleaseAuditDataJson, function(data) {
            console.log(data)
            var len = data.detailInfo.orderDTOList.length;
            var orderList = null;
            var list = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderDTOList[i];
                if(orderList.createTime == null) {
                    orderList.createTime = "--";
                } else {
                    orderList.createTime = (new Date(orderList.createTime)).getFullYear()+"-"+((new Date(orderList.createTime)).getMonth()+1)+"-"+(new Date(orderList.createTime)).getDate()
                }
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='sellerName'>发布人:"+orderList.sellerName+"</span>"
                    + "<span class='stockAmt'>股权数:"+orderList.stockAmt+"</span>"
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
            }
            $(".issuedAudit").append(list);
            $(".issuedAudit").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        })
    };

/**
*撤销审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/
    var getBackoutAudit = function(toEmpty){
        global_ajax("backoutAuditList", backoutAuditDatajson, function(data) {
            console.log(data)
            var len = data.detailInfo.orderDTOList.length;
            var orderList = null;
            var list = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderDTOList[i];
                if(orderList.maxBiddingPrice == 0) {
                    orderList.maxBiddingPrice = "--";
                }
                if(orderList.maxBidder == null) {
                    orderList.orderState = "未拍";
                    orderList.maxBidder = "--";
                } else {
                    orderList.orderState = "已拍";
                }
                orderList.maxBiddingPrice = (orderList.maxBiddingPrice == 0)? "--":orderList.maxBiddingPrice;
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='sellerName'>发布人:"+orderList.sellerName+"</span>"
                    + "<span class='stockAmt'>股权数:"+orderList.stockAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initialPrice+"</span>"
                    + "<span class='createTime'>提交时间:"+(new Date(orderList.createTime)).getFullYear()+"-"+((new Date(orderList.createTime)).getMonth()+1)+"-"+(new Date(orderList.createTime)).getDate()+"</span>"
                    + "<span class='saleTime'>上架时间:"+(new Date(orderList.saleTime)).getFullYear()+"-"+((new Date(orderList.saleTime)).getMonth()+1)+"-"+(new Date(orderList.saleTime)).getDate()+"</span>"
                    + "<span class='orderState'>订单状态:"+orderList.orderState+"</span>"
                    + "<span class='maxBiddingPrice'>当前竞价:"+orderList.maxBiddingPrice+"</span>"
                    + "<span class='maxBidder'>当前持有人:"+orderList.maxBidder+"</span>"
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
            }
            $(".cancelAudit").append(list);
            $(".cancelAudit").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    };
/**
*发布审核通过/不通过
*/
    $(".contentRight").on("click", "#issuedAudit .resultInfo>.audit-btn>span", function(e) {
        var checkingResult = $(this).index();
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var checkingView = $(this).parent().siblings(".auditIdea").find(".auditIdea-area").val();
        var sendData = {
                "checkingResult": checkingResult,
                "orderNo": orderNo,
                "checkingView": checkingView
            };
        global_ajax("releaseAudit", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("操作成功");
            $("#errorTips").modal("show");
            getReleaseAuditDataJson.pageNo = 1;
            getReleaseAudit(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*撤销审核通过/不通过
*/
    $(".contentRight").on("click", "#cancelAudit .resultInfo>.audit-btn>span", function(e) {
        var checkingResult = $(this).index();
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var checkingView = $(this).parent().siblings(".auditIdea").find(".auditIdea-area").val();
        var sendData = {
                "checkingResult": checkingResult,
                "orderNo": orderNo,
                "checkingView": checkingView
            };
        global_ajax("backoutAudit", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("操作成功");
            $("#errorTips").modal("show");
            backoutAuditDatajson.pageNo = 1;
            getBackoutAudit(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*页面滚动到当前页面最底部时加载下一页
*issueBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){
        var issueBottom = "";
        var cancelBottom = "";
        window.onscroll = function(e) {
            if(!$(".issuedAudit").hasClass("hide")) {
                issueBottom = document.querySelector(".issuedAudit").getBoundingClientRect().bottom;
                if(issueBottom == winHeight - 40) {
                    getReleaseAuditDataJson.pageNo += 1;
                    if(getReleaseAuditDataJson.pageNo <= parseInt($(".issuedAudit").attr("maxPage"))) {
                        getReleaseAudit();
                    } else {
                        getReleaseAuditDataJson.pageNo = parseInt($(".issuedAudit").attr("maxPage"));
                    }
                    console.log(getReleaseAuditDataJson.pageNo)
                }
            } else {
                cancelBottom = document.querySelector(".cancelAudit").getBoundingClientRect().bottom;
                if(issueBottom == winHeight - 40) {
                    backoutAuditDatajson.pageNo += 1;
                    if(backoutAuditDatajson.pageNo <= parseInt($(".cancelAudit").attr("maxPage"))) {
                        getBackoutAudit();
                    } else {
                        backoutAuditDatajson.pageNo = parseInt($(".cancelAudit").attr("maxPage"));
                    }
                }
            }
        };
    }
    addNextPageData();
/**
*判断是否为浏览器返回历史纪录加载数据
*historyData:上一次存贮的信息（包含哪个页面， 当前第几页）
*/
    if(!isHistory) {
        getReleaseAudit(true);
        getBackoutAudit(true);
    } else {
        var historyData = getCookie("auditOrder");
        $("#issuedAudit").html(window.sessionStorage.getItem("auditOrderOne"));
        $("#cancelAudit").html(window.sessionStorage.getItem("auditOrderTwo"));
        if(historyData.split("&")[0] == "1") {
            $(".issuedAudit").addClass("hide");
            $(".cancelAudit").removeClass("hide");
            $(".contentRight>.content-title>span").eq(1).addClass("active").siblings().removeClass("active");
        } else {
            $(".issuedAudit").removeClass("hide");
            $(".cancelAudit").addClass("hide");
            $(".contentRight>.content-title>span").eq(0).addClass("active").siblings().removeClass("active");
        }
        getReleaseAuditDataJson.pageNo = parseInt(historyData.split("&")[1]);
        backoutAuditDatajson.pageNo = parseInt(historyData.split("&")[2]);
        $(".cancelAudit").attr("maxPage", parseInt(historyData.split("&")[3]));
        history.replaceState({}, "", "auditOrder.html");
    }
/**
*点击子菜单展示对应的页面
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
        var auditOrderOne = $("#issuedAudit").html();
        var auditOrderTwo = $("#cancelAudit").html();
        var auditOrderData = window.sessionStorage.getItem("issuedAudit") + "&" + getReleaseAuditDataJson.pageNo + "&" + backoutAuditDatajson.pageNo + "&" + $(".cancelAudit").attr("maxPage");
        if($(this).attr("urlSrc") == "auditOrder.html" || $(this).attr("urlSrc") == "auditOrder.html?true"){
            return;
        }else {
            setCookie("auditOrder", auditOrderData);
            window.sessionStorage.setItem("auditOrderOne", auditOrderOne);
            window.sessionStorage.setItem("auditOrderTwo", auditOrderTwo);
            history.replaceState({}, "", "auditOrder.html?true");
        }
    })
})