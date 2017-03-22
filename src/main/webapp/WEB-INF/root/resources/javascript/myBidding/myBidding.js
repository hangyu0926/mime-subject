$(function() {
/**
*isHistory:            是否为历史返回记录
*getMyBiddingDataJson：我的竞拍》》发送的数据
*backoutAuditDatajson：待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = false;
    var winHeight = document.documentElement.clientHeight;
    var getMyBiddingDataJson = {
            pageSize: 10,
            pageNo: 1,
            userId: window.localStorage.getItem("userId")
        };
/**
*发布审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getMyBidding = function(toEmpty){
        global_ajax("getMyOrder", getMyBiddingDataJson, function(data) {
            var len = data.detailInfo.orderList.length;
            var orderList = null;
            var list = null;
            var str = null;
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderList[i];
                if(orderList.orderState == 1) {
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-false'>"
                            + "<span class='continueBidding-btn'>继续竞价</span>"
                            + "<span>您的出价已被超，点击按钮继续竞价</span>"
                            + "</div>";
                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "保持领先";
                        str = "<div class='order-continue'>请及时刷新页面或关注邮箱邮件，以保持竞价领先</div>";
                    }
                } else if(orderList.orderState == 2) {
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-continue'>交易已经结束</div>";

                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "成功";
                        str = "<div class='order-true'>"
                            + "<span class='confirmBidding-btn'>订单完成</span>"
                            + "<span>确认已经线下交易完成，完成该订单</span>"
                            + "</div>"
                    }
                }
                if(!orderList.saleTime) {
                    orderList.saleTime = "--"
                } 
                list = "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stocksAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initPrice+"</span>"
                    + "<span class='maxBiddingPrice'>当前竞价:"+orderList.maxBiddingPrice+"</span>"
                    + "<span class='myPrice'>我的出价:"+orderList.myPrice+"</span>"
                    + "<span class='biddingState'>竞拍状态:"+orderList.biddingState+"</span>"
                    + "<span class='orderState'>订单状态:"+orderList.orderState+"</span>"
                    + "<span class='saleTime'>上架时间:"+orderList.saleTime+"</span>"
                    + "<span class='expireTime'>结束时间:"+orderList.expireTime+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + str
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".myBidding").empty();
            } else {
                return;
            }
            $(".myBidding").append(list);
            $(".myBidding").attr("maxPage", Math.ceil(data.detailInfo.orderList.totalCount/10));
        }, "POST", function(data) {
            window.global_dialog.error(data.desc,function(){
                closeAlertDialog()
            });
        })
    };
/**
*订单完成   继续竞价
*/
    $(".contentRight").on("click", ".resultInfo>.order-true>.confirmBidding-btn", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "checkingResult": checkingResult,
                "orderNo": orderNo,
                "checkingView": checkingView
            };
        global_ajax("releaseAudit", sendData, function(data) {
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
        // if(window.sessionStorage.getItem("myBidding")) {
        //     $(".myBidding").removeClass("hide");
        //     $(".cancelAudit").addClass("hide");
        // } else {
        //     $(".myBidding").addClass("hide");
        //     $(".cancelAudit").removeClass("hide");
        // }
    }
/**
*页面滚动到当前页面最底部时加载下一页
*issueBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){ 
        var myBiddingBottom = "";
        window.onscroll = function(e) {
            myBiddingBottom = document.querySelector("#auctionList").getBoundingClientRect().bottom;
            if(myBiddingBottom == winHeight - 40) {
                getBiddingListDataJson.pageNo += 1;
                if(getBiddingListDataJson.pageNo <= parseInt($("#auctionList").attr("maxPage"))) {
                    getBiddingList();
                } else {
                    getBiddingListDataJson.pageNo = parseInt($("#auctionList").attr("maxPage"));
                }
            }
        }
    }
})