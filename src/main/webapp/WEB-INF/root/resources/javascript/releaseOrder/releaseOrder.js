$(function() {
/**
*isHistory:               是否为历史返回记录
*getReleaseOrderDataJson: 我的发布>>发送的数据
*/    
    var isHistory = (window.location.href.indexOf("?") == -1) ? false : true;
    var winHeight = document.documentElement.clientHeight;
    var getReleaseOrderDataJson = {
            pageSize: 4,
            pageNo: 1
        };
/**
*我的发布
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getReleaseOrderData = function(toEmpty){
        global_ajax("getMyOrder", getReleaseOrderDataJson, function(data) {
        	console.log(data)
            var len = data.detailInfo.orderDTOList.length;
            var orderList = null;
            var list = "";
            var str = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderDTOList[i];
                if(orderList.sellerConfirm == 0) {
                	str = "<div class='audit-btn'>"
                        + "<span class='audit-true'>确认完成</span>"
                        + "</div>"
                        + "<div class='auditIdea'>"
                        + "<span>确认该订单线下交易完成</span>"
                        + "</div>";
                } else if(orderList.hasbackout == 0) {
                	str = "<div class='audit-btn'>"
                        + "<span class='audit-false'>撤销该订单</span>"
                        + "</div>"
                        + "<div class='auditIdea'>"
                        + "<span></span>"
                        + "</div>";
                } else {
                	str = "";
                }
                if(orderList.orderState == 1) {
                	orderList.orderState = "待审核";
                } else if(orderList.orderState == 2) {
                	orderList.orderState = "已取消";
                } else if(orderList.orderState ==3) {
                	orderList.orderState = "撤销审核中";
                } else if(orderList.orderState ==4) {
                	orderList.orderState = "交易中";
                } else if(orderList.orderState ==5) {
                	orderList.orderState = "已撤销";
                } else if(orderList.orderState ==6) {
                	orderList.orderState = "竞拍结束，等待确认";
                } else if(orderList.orderState ==7) {
                	orderList.orderState = "交易结束";
                } else if(orderList.orderState ==8) {
                	orderList.orderState = "流拍";
                } else if(orderList.orderState ==9) {
                    orderList.orderState = "竞拍完成，等待系统结算";
                }
                if(orderList.saleTime == null) {
                    orderList.saleTime = "--";
                } else {
                    orderList.saleTime = (new Date(orderList.saleTime)).getFullYear()+"-"+((new Date(orderList.saleTime)).getMonth()+1)+"-"+(new Date(orderList.saleTime)).getDate()
                }
                if(orderList.expireTime == null) {
                    orderList.expireTime = "--";
                } else {
                    orderList.expireTime = (new Date(orderList.expireTime)).getFullYear()+"-"+((new Date(orderList.expireTime)).getMonth()+1)+"-"+(new Date(orderList.expireTime)).getDate()
                }
                if(orderList.maxBiddingPrice == 0) {
                    orderList.maxBiddingPrice = "--";
                }
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='stockAmt'>股权数:"+orderList.stockAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initialPrice+"</span>"
                    + "<span class='saleTime'>上架时间:"+orderList.saleTime+"</span>"
                    + "<span class='orderState'>竞拍状态:"+orderList.orderState+"</span>"
                    + "<span class='maxBiddingPrice'>当前竞价:"+orderList.maxBiddingPrice+"</span>"
                    + "<span class='expireTime'>结束时间:"+orderList.expireTime+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + str
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".releaseOrder").empty();
            }
            $(".releaseOrder").append(list);
            $(".releaseOrder").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        })
    };
/**
*确认订单
*/
    $(".contentRight").on("click", ".resultInfo>.audit-btn>.audit-true", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo,
                "confirmUser": 2
            };
        global_ajax("confirmOrder", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("订单确认成功");
            $("#errorTips").modal("show");
            getReleaseOrderDataJson.pageNo = 1;
            getReleaseOrderData(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*撤销订单
*/
    $(".contentRight").on("click", ".resultInfo>.audit-btn>.audit-false", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo
            };
        global_ajax("backoutOrder", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("撤销订单成功");
            $("#errorTips").modal("show");
            getReleaseOrderDataJson.pageNo = 1;
            getReleaseOrderData(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*发布新标的
*/
    $("#releaseNewInfo .modal-footer .btn-primary").on("click", function(e)  {
        var stocksAmt = parseInt($("#releaseNewInfo").find("#stocksAmt").val());
        var initialPrice = parseInt($("#releaseNewInfo").find("#initialPrice").val());
        var userId = window.localStorage.getItem('userId');
        var sendData = {
                "stockAmt": stocksAmt,
                "initialPrice": initialPrice
            };
        if(stocksAmt > 0 && stocksAmt<= parseInt($("#releaseNewInfo .modal-body").attr("stocksSaleAmt")) && initialPrice >= parseInt($("#releaseNewInfo .modal-body").attr("minStockPrice")) && initialPrice <= parseInt($("#releaseNewInfo .modal-body").attr("maxStockPrice"))) {
        	$("#releaseNewInfo").modal("hide");
        	global_ajax("releaseOrder", sendData, function(data) {
	            $("#errorTips").find(".myModal-body").html("发布成功");
	            $("#errorTips").modal("show");
	            $("#errorTips .myModal-footer>.btn").on("click", function(e) {
	            	if($("#errorTips").find(".myModal-body").html() == "发布成功") {
	            		window.location.reload();
	            	}
	            })
        	},"POST",function(data) {
	            $("#errorTips").find(".myModal-body").html(data.desc);
	            $("#errorTips").modal("show");
        	});
        } else {
        	$("#releaseNewInfo .modal-body>p").addClass("active");
        }
    })

    $("#releaseNewInfo .modal-body input").on("input propertychange", function(e) {
    	var stocksAmt = parseInt($("#releaseNewInfo").find("#stocksAmt").val());
    	var initialPrice = parseInt($("#releaseNewInfo").find("#initialPrice").val());
        var stocksSaleAmt = $("#releaseNewInfo .modal-body").attr("stocksSaleAmt");
        var minStockPrice = $("#releaseNewInfo .modal-body").attr("minStockPrice");
        var maxStockPrice = $("#releaseNewInfo .modal-body").attr("maxStockPrice");
        if(stocksAmt > 0 && stocksAmt<= parseInt($("#releaseNewInfo .modal-body").attr("stocksSaleAmt")) && initialPrice >= parseInt($("#releaseNewInfo .modal-body").attr("minStockPrice")) && initialPrice <= parseInt($("#releaseNewInfo .modal-body").attr("maxStockPrice"))) {
        	$("#releaseNewInfo .modal-body>p").removeClass("active");
        }
    })
/**
*点击发布新标的按钮
*/
    $(".contentRight>.content-title>span").on("click", function(e) {
        global_ajax("jumpReleaseOrder", {}, function(data) {
    		$("#releaseNewInfo").modal("show");
    		$("#releaseNewInfo .modal-body>p").removeClass("active");
    		$("#releaseNewInfo .modal-body span").eq(0).html("（最多"+ data.detailInfo.stocksSaleAmt+"）");
    		$("#releaseNewInfo .modal-body span").eq(1).html("（最低"+ data.detailInfo.minStockPrice+"，最高"+data.detailInfo.maxStockPrice+"）");
            $("#releaseNewInfo .modal-body").attr("stocksSaleAmt", data.detailInfo.stocksSaleAmt);
            $("#releaseNewInfo .modal-body").attr("minStockPrice", data.detailInfo.minStockPrice);
            $("#releaseNewInfo .modal-body").attr("maxStockPrice", data.detailInfo.maxStockPrice);
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*页面滚动到当前页面最底部时加载下一页
*releaseOrderBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){
        var releaseOrderBottom = "";
        window.onscroll = function(e) {
            releaseOrderBottom = document.querySelector("#releaseOrder").getBoundingClientRect().bottom;
            if(releaseOrderBottom == winHeight - 40) {
                getReleaseOrderDataJson.pageNo += 1;
                if(getReleaseOrderDataJson.pageNo <= parseInt($("#releaseOrder").attr("maxPage"))) {
                    getReleaseOrderData();
                } else {
                    getReleaseOrderDataJson.pageNo = parseInt($("#releaseOrder").attr("maxPage"));
                }
            }
        }
    };
    addNextPageData();
/**
*判断是否为浏览器返回历史纪录加载数据
*historyData:上一次存贮的信息（包含哪个页面， 当前第几页）
*/
    if(!isHistory) {
        getReleaseOrderData(true);
    } else {
        var historyData = getCookie("releaseOrderData");
        $("#releaseOrder").html(window.sessionStorage.getItem("releaseOrder"));
        getReleaseOrderDataJson.pageNo = parseInt(historyData.split("&")[0]);
        $("#releaseOrder").attr("maxPage", parseInt(historyData.split("&")[1]));
        history.replaceState({}, "", "releaseOrder.html");
    }
/**
*当点击菜单时对应的不是当前页面增加一个历史记录
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
        var releaseOrder = $("#releaseOrder").html();
        var releaseOrderData = getReleaseOrderDataJson.pageNo + "&" + $("#releaseOrder").attr("maxPage");
        if($(this).attr("urlSrc") == "releaseOrder.html" || $(this).attr("urlSrc") == "releaseOrder.html?true"){
            return;
        } else {
            setCookie("releaseOrderData", releaseOrderData);
            window.sessionStorage.setItem("releaseOrder", releaseOrder);
            history.replaceState({}, "", "releaseOrder.html?true");
        }
    })
});
//刚进来修改密码
;function doFirstModify(){
	var newPass=$("#firstnowPwd").val().trim();
	var mewPassagain=$("#firstnowPwdagain").val().trim();
	doModifypass(newPass,mewPassagain);
};

;function doClearfirst(){
  $("#firstnowPwd").val("");
  $("#firstnowPwdagain").val("");
};