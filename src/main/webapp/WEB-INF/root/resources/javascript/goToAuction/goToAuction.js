$(function() {
/**
*isHistory:               是否为历史返回记录
*getBiddingListDataJson：待审核订单-发布审核》》发送的数据
*backoutAuditDatajson：   待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = (window.location.href.indexOf("?") == -1) ? false : true;
    var winHeight = document.documentElement.clientHeight;
    var getBiddingListDataJson = {
            pageSize: 4,
            pageNo: 1
        };
/**
*我要竞拍
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getBiddingList = function(toEmpty){
        global_ajax("biddingList", getBiddingListDataJson, function(data) {
            var len = data.detailInfo.biddingVOList.length;
            var orderList = null;
            var list = "";
            var str = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.biddingVOList[i];
            	if(orderList.biddingState == 1) {
 					str = "<span class='goToAuction'>竞拍</span>";
            	} else {
 					str = "<span class='infoTip'>您是最高竞价人，请持续关注邮件通知或刷新页面</span>";
            	}
            	if(orderList.biddingState == 1) {
            		orderList.biddingState = "可以竞价";
            	} else {
            		orderList.biddingState = "不可竞价";
            	}
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stocksAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initPrice+"</span>"
                    + "<span class='nowPrice'>当前竞价:"+orderList.nowPrice+"</span>"
                    + "<span class='sellerName'>出售人姓名:"+orderList.sellerName+"</span>"
                    + "<span class='saleTime'>上架时间:"+(new Date(orderList.saleTime)).getFullYear()+"-"+((new Date(orderList.saleTime)).getMonth()+1)+"-"+(new Date(orderList.saleTime)).getDate()+"</span>"
                    + "<span class='expireTime'>结束时间:"+(new Date(orderList.expireTime)).getFullYear()+"-"+((new Date(orderList.expireTime)).getMonth()+1)+"-"+(new Date(orderList.expireTime)).getDate()+"</span>"
                    + "<span class='biddingState'>竞拍状态:"+orderList.biddingState+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + str
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".auctionList").empty();
            }
            $(".auctionList").append(list);
            $(".auctionList").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        })
    };
/**
*准备竞价
*/  
    var biddingData = {
            orderNo: "",
            stocksAmt: "",
            minMakeUp: "",
            maxMakeUp: "",
            maxPrice: "",
            nowPrice: ""
        };
    $(".contentRight").on("click", ".resultInfo>.goToAuction", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var stocksAmt = $(this).parents(".resultInfo").siblings(".orderInfo").find(".stocksAmt").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo
            };
        global_ajax("preBidding", sendData, function(data) {
            var orderList = data.detailInfo;
            var nowPrice = "";
            var maxPrice = (orderList.maxPrice >= orderList.nowPrice + orderList.maxMakeUp) ? orderList.nowPrice + orderList.maxMakeUp : orderList.maxPrice;
            if(orderList.nowPrice + orderList.minMakeUp >= maxPrice) {
                nowPrice = maxPrice;
            } else {
                nowPrice = orderList.nowPrice + orderList.minMakeUp;
            }
            $("#setPreBidding").find("#preBidding-unitPrice").val(nowPrice);
            $("#setPreBidding").find(".info-tips").html("此次最小加价"+orderList.minMakeUp+"，最多加价"+orderList.maxMakeUp+"");
            $("#setPreBidding").find(".preBidding-totalPrice").html(stocksAmt*nowPrice);
            biddingData.orderNo = orderNo;
            biddingData.stocksAmt = stocksAmt;
            biddingData.maxPrice = orderList.maxPrice;
            biddingData.minMakeUp = orderList.minMakeUp;
            biddingData.maxMakeUp = orderList.maxMakeUp;
            biddingData.nowPrice = orderList.nowPrice;
            $("#setPreBidding").modal("show");
            $("#setPreBidding").find(".info-tips").removeClass("active");
            $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
        }, "GET", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*竞拍该标的
*/
    $("#setPreBidding .modal-footer>.btn").on("click", function(e) {
        var biddingPrice = parseInt($("#setPreBidding").find("#preBidding-unitPrice").val());
        var sendData = {
                "orderNo": biddingData.orderNo,
                "biddingPrice": biddingPrice
            };
        if($(this).hasClass("btn-primary")) {
	        global_ajax("bidding", sendData, function(data) {
	            $("#errorTips").find(".myModal-body").html("竞拍成功");
	            $("#errorTips").modal("show");
                getBiddingListDataJson.pageNo = 1;
                getBiddingList(true);
	        }, "POST", function(data){
	            $("#errorTips").find(".myModal-body").html(data.desc);
	            $("#errorTips").modal("show");
	        });
	        $("#setPreBidding").modal("hide");
        }
    })
/**
*竞拍单价设置
*/
    var setUnitPrice = function() {
        var maxPrice = "";
        $("#setPreBidding .modal-body #preBidding-unitPrice").on({
            focus: function(e) {
                $(this).parent().siblings(".info-tips").addClass("active");
            },
            blur: function(e) {
                $(this).parent().siblings(".info-tips").removeClass("active");
            },
            keyup: function(e) {
                $("#setPreBidding").find(".preBidding-totalPrice").html(biddingData.stocksAmt*parseInt($(this).val()));
                if(biddingData.maxPrice >= biddingData.maxMakeUp+biddingData.nowPrice) {
                    maxPrice = biddingData.maxMakeUp+biddingData.nowPrice;
                    if(parseInt($(this).val()) <= maxPrice && parseInt($(this).val()) >= biddingData.nowPrice + biddingData.minMakeUp) {
                        $(this).parent().siblings(".info-tips").removeClass("active");
                        $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
                    } else {
                        $(this).parent().siblings(".info-tips").addClass("active");
                        $("#setPreBidding .modal-footer>.btn").removeClass("btn-primary").addClass("btn-default");
                    }
                } else if(biddingData.maxPrice < biddingData.maxMakeUp+biddingData.nowPrice) {
                    maxPrice = biddingData.maxPrice;
                    if(biddingData.maxPrice >= biddingData.minMakeUp+biddingData.nowPrice) {
                        if(parseInt($(this).val()) <= maxPrice && parseInt($(this).val()) >= biddingData.nowPrice + biddingData.minMakeUp) {
                            $(this).parent().siblings(".info-tips").removeClass("active");
                            $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
                        } else {
                            $(this).parent().siblings(".info-tips").addClass("active");
                            $("#setPreBidding .modal-footer>.btn").removeClass("btn-primary").addClass("btn-default");
                        }
                    } else {
                        $(this).parent().siblings(".info-tips").addClass("active");
                        $("#setPreBidding .modal-footer>.btn").removeClass("btn-primary").addClass("btn-default");

                    }
                }
            }
        })

        $("#setPreBidding .modal-body>.mes>i").on("click", function(e) {
			var nowPrice = parseInt($("#setPreBidding").find("#preBidding-unitPrice").val());
            $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
            if($(this).hasClass("preBidding-addPrice")) {
	            nowPrice +=  biddingData.minMakeUp;
	    	} else if($(this).hasClass("preBidding-reduceprice")) {
	            nowPrice -=  biddingData.minMakeUp;
	    	}
            if(biddingData.maxPrice >= biddingData.maxMakeUp+biddingData.nowPrice) {
                maxPrice = biddingData.maxMakeUp+biddingData.nowPrice;
                nowPrice = (nowPrice >= maxPrice) ? maxPrice : nowPrice;
                nowPrice = (nowPrice <= biddingData.nowPrice + biddingData.minMakeUp) ? biddingData.nowPrice + biddingData.minMakeUp : nowPrice;
            } else if(biddingData.maxPrice <= biddingData.maxMakeUp+biddingData.nowPrice) {
                maxPrice = biddingData.maxPrice;
                nowPrice = maxPrice;
            } else if(biddingData.nowPrice == biddingData.maxPrice) {
                maxPrice = biddingData.maxPrice;
                nowPrice = maxPrice;
            }
	    	$("#setPreBidding").find("#preBidding-unitPrice").val(nowPrice);
	    	$("#setPreBidding").find(".preBidding-totalPrice").html(biddingData.stocksAmt*nowPrice);
	    })
    };
    setUnitPrice();
/**
*页面滚动到当前页面最底部时加载下一页
*auctionListBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){
        var auctionListBottom = "";
        window.onscroll = function(e) {
            auctionListBottom = document.querySelector("#auctionList").getBoundingClientRect().bottom;
            if(auctionListBottom == winHeight - 40) {
                getBiddingListDataJson.pageNo += 1;
                if(getBiddingListDataJson.pageNo <= parseInt($("#auctionList").attr("maxPage"))) {
                    getBiddingList();
                } else {
                    getBiddingListDataJson.pageNo = parseInt($("#auctionList").attr("maxPage"));
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
        getBiddingList(true);
    } else {
        var historyData = getCookie("auctionListData");
        $("#auctionList").html(window.sessionStorage.getItem("auctionList"));
        getBiddingListDataJson.pageNo = parseInt(historyData.split("&")[0]);
        $("#auctionList").attr("maxPage", parseInt(historyData.split("&")[1]));
        history.replaceState({}, "", "goToAuction.html");
    }
/**
*当点击菜单时对应的不是当前页面增加一个历史记录
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
        var auctionList = $("#auctionList").html();
        var auctionListData = getBiddingListDataJson.pageNo + "&" + $("#auctionList").attr("maxPage");
        if($(this).attr("urlSrc") == "goToAuction.html" || $(this).attr("urlSrc") == "goToAuction.html?true"){
            return;
        } else {
            setCookie("auctionListData", auctionListData);
            window.sessionStorage.setItem("auctionList", auctionList);
            history.replaceState({}, "", "goToAuction.html?true");
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