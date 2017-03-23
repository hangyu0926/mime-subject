$(function() {
/**
*isHistory:            是否为历史返回记录
*getMyBiddingDataJson：我的竞拍》》发送的数据
*backoutAuditDatajson：待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = (window.location.href.indexOf("?") == -1) ? false : true;
    var winHeight = document.documentElement.clientHeight;
    var getMyBiddingDataJson = {
            pageSize: 4,
            pageNo: 1
        };
/**
*发布审核数据获取方法
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getMyBidding = function(toEmpty){
        global_ajax("myBidding", getMyBiddingDataJson, function(data) {
            console.log(data);
            var len = data.detailInfo.myBiddingVOList.length;
            var orderList = null;
            var list = "";
            var str = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.myBiddingVOList[i];
                if(orderList.orderState == 4) {
                    orderList.orderState = "交易中";
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
                } else if(orderList.orderState == 5) {
                    orderList.orderState = "已撤销";
                    orderList.biddingState = "失败";
                    str = "<div class='order-continue'>订单已撤销，无法进行交易</div>";
                } else if(orderList.orderState == 6) {
                    orderList.orderState = "竞拍结束，等待确认";
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-continue'>竞拍失败，该订单已经结束</div>";
                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "竞拍获胜";
                        if(orderList.buyerConfirm == 1) {
                            str = "<div class='order-true'>"
                                + "<span class='completeBidding-btn'>完成订单</span>"
                                + "<span>确认已经线下交易完成，完成该订单</span>"
                                + "</div>";
                        } else {
                            str = "<div class='order-continue'>您已确认，等待卖家确认中，如卖家长时间没有确认请联系管理员</div>";
                        }
                    }
                }else if(orderList.orderState == 7) {
                    orderList.orderState = "交易结束";
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-continue'>交易已经结束</div>";
                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "成功";
                        str = "<div class='order-continue'>交易已经结束</div>";
                    }
                } else if(orderList.orderState == 9) {
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-continue'>竞拍失败，系统结算中</div>";
                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "成功";
                        str = "<div class='order-continue'>竞拍获胜，系统结算中</div>";
                    }
                }
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stocksAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initPrice+"</span>"
                    + "<span class='nowPrice'>当前竞价:"+orderList.nowPrice+"</span>"
                    + "<span class='myPrice'>我的出价:"+orderList.myPrice+"</span>"
                    + "<span class='biddingState'>竞拍状态:"+orderList.biddingState+"</span>"
                    + "<span class='orderState'>订单状态:"+orderList.orderState+"</span>"
                    + "<span class='saleTime'>上架时间:"+(new Date(orderList.saleTime)).getFullYear()+"-"+((new Date(orderList.saleTime)).getMonth()+1)+"-"+(new Date(orderList.saleTime)).getDate()+"</span>"
                    + "<span class='expireTime'>结束时间:"+(new Date(orderList.expireTime)).getFullYear()+"-"+((new Date(orderList.expireTime)).getMonth()+1)+"-"+(new Date(orderList.expireTime)).getDate()+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + str
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $(".myBidding").empty();
            }
            $(".myBidding").append(list);
            $(".myBidding").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        })
    };
/**
*订单完成   
*/
    $(".contentRight").on("click", ".resultInfo>.order-true>.completeBidding-btn", function(e) {
        console.log(111);
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo,
                "confirmUser": 1
            };
        global_ajax("confirmOrder", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("订单确认成功");
            $("#errorTips").modal("show");
            getMyBiddingDataJson.pageNo = 1;
            getMyBidding(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*继续竞价
*/
    $(".contentRight").on("click", ".resultInfo>.order-false>.continueBidding-btn", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var stocksAmt = $(this).parents(".resultInfo").siblings(".orderInfo").find(".stocksAmt").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo
            };
        global_ajax("preBidding", sendData, function(data) {
            console.log(data)
            var orderList = data.detailInfo;
            $("#setPreBidding").find("#preBidding-unitPrice").val(orderList.nowPrice + orderList.minMakeUp);
            $("#setPreBidding").find(".info-tips").html("此次最小加价"+orderList.minMakeUp+"，最多加价"+orderList.maxMakeUp+"");
            $("#setPreBidding").find(".mes").attr("maxPrice", orderList.maxPrice);
            $("#setPreBidding").find(".mes").attr("orderNo", orderNo);
            $("#setPreBidding").find(".mes").attr("minMakeUp", orderList.minMakeUp);
            $("#setPreBidding").find(".mes").attr("maxMakeUp", orderList.maxMakeUp);
            $("#setPreBidding").find(".mes").attr("nowPrice", orderList.nowPrice);
            $("#setPreBidding").find(".mes").attr("stocksAmt", stocksAmt);
            $("#setPreBidding").find(".preBidding-totalPrice").html(stocksAmt*(orderList.nowPrice + orderList.minMakeUp));
            $("#setPreBidding").modal("show");
            setUnitPrice(orderList.minMakeUp, orderList.maxMakeUp, orderList.nowPrice, orderList.maxPrice, stocksAmt);
        }, "GET", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*竞拍单价设置
*/
    var setUnitPrice = function(minMakeUp, maxMakeUp, originPrice, maxPrice, stocksAmt) {
        maxPrice = (maxPrice>maxMakeUp+originPrice)?maxMakeUp+originPrice:maxPrice;
        $("#setPreBidding .modal-body #preBidding-unitPrice").on({
            focus: function(e) {
                $(this).parent().siblings(".info-tips").addClass("active");
            },
            blur: function(e) {
                $(this).parent().siblings(".info-tips").removeClass("active");
            },
            keyup: function(e) {
                $("#setPreBidding").find(".preBidding-totalPrice").html(stocksAmt*parseInt($(this).val()));
                if(parseInt($(this).val()) <= maxPrice && parseInt($(this).val()) >= originPrice + minMakeUp) {
                    $(this).parent().siblings(".info-tips").removeClass("active");
                    $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
                } else {
                    $(this).parent().siblings(".info-tips").addClass("active");
                    $("#setPreBidding .modal-footer>.btn").removeClass("btn-primary").addClass("btn-default");
                }
            }
        })

        $("#setPreBidding .modal-body>.mes>i").on("click", function(e) {
            var nowPrice = parseInt($("#setPreBidding").find("#preBidding-unitPrice").val());
            if($(this).hasClass("preBidding-addPrice")) {
                nowPrice +=  minMakeUp;
                nowPrice = (nowPrice >= maxPrice) ? maxPrice : nowPrice;
            } else if($(this).hasClass("preBidding-reduceprice")) {
                nowPrice -=  minMakeUp;
                nowPrice = (nowPrice <= originPrice + minMakeUp) ? originPrice + minMakeUp : nowPrice;
            }
            $("#setPreBidding").find("#preBidding-unitPrice").val(nowPrice);
            $("#setPreBidding").find(".preBidding-totalPrice").html(stocksAmt*nowPrice);
        })
    };
/**
*竞拍该标的
*/
    $("#setPreBidding .modal-footer>.btn").on("click", function(e) {
        var orderNo = $("#setPreBidding").find(".mes").attr("orderNo");
        var biddingPrice = parseInt($("#setPreBidding").find("#preBidding-unitPrice").val());
        var sendData = {
                "orderNo": orderNo,
                "biddingPrice": biddingPrice
            };
        var maxPrice = parseInt($("#setPreBidding").find(".mes").attr("maxPrice"));
        var originPrice = parseInt($("#setPreBidding").find(".mes").attr("nowPrice"));
        var maxMakeUp = parseInt($("#setPreBidding").find(".mes").attr("maxMakeUp"));
        var minMakeUp = parseInt($("#setPreBidding").find(".mes").attr("minMakeUp"));
        var minprice = originPrice + minMakeUp;
        maxPrice = (maxPrice>maxMakeUp+originPrice)?maxMakeUp+originPrice:maxPrice;
        if($(this).hasClass("btn-primary")) {
            global_ajax("bidding", sendData, function(data) {
                console.log(data)
                $("#errorTips").find(".myModal-body").html("竞拍成功");
                $("#errorTips").modal("show");
                getMyBiddingDataJson.pageNo = 1;
                getMyBidding(true);
            }, "POST", function(data){
                $("#errorTips").find(".myModal-body").html(data.desc);
                $("#errorTips").modal("show");
            });
                $("#setPreBidding").modal("hide");
        }
    })
/**
*页面滚动到当前页面最底部时加载下一页
*issueBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){ 
        var myBiddingBottom = "";
        window.onscroll = function(e) {
            myBiddingBottom = document.querySelector("#myBidding").getBoundingClientRect().bottom;
            if(myBiddingBottom == winHeight - 40) {
                getMyBiddingDataJson.pageNo += 1;
                if(getMyBiddingDataJson.pageNo <= parseInt($("#myBidding").attr("maxPage"))) {
                    getBiddingList();
                } else {
                    getMyBiddingDataJson.pageNo = parseInt($("#myBidding").attr("maxPage"));
                }
            }
        }
    }
    addNextPageData();
/**
*判断是否为浏览器返回历史纪录加载数据
*/
    if(!isHistory) {
        getMyBidding(true);
    } else {
        var historyData = getCookie("myBiddingData");
        $("#myBidding").html(window.sessionStorage.getItem("myBidding"));
        getMyBiddingDataJson.pageNo = parseInt(historyData.split("&")[0]);
        $("#myBidding").attr("maxPage", parseInt(historyData.split("&")[1]));
        history.replaceState({}, "", "myBidding.html");
    }
/**
*当点击菜单时对应的不是当前页面增加一个历史记录
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
        var myBidding = $("#myBidding").html();
        var myBiddingData = getMyBiddingDataJson.pageNo + "&" + $("#myBidding").attr("maxPage");
        if($(this).attr("urlSrc") == "myBidding.html" || $(this).attr("urlSrc") == "myBidding.html?true"){
            return;
        } else {
            setCookie("myBiddingData", myBiddingData);
            window.sessionStorage.setItem("myBidding", myBidding);
            history.replaceState({}, "", "myBidding.html?true");
        }
    })
});
