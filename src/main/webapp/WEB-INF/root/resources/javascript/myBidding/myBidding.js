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
                    orderList.orderState = "系统结算中";
                    if(orderList.biddingState == 1) {
                        orderList.biddingState = "失败";
                        str = "<div class='order-continue'>竞拍失败，系统结算中</div>";
                    } else if(orderList.biddingState == 2) {
                        orderList.biddingState = "成功";
                        str = "<div class='order-continue'>竞拍获胜，系统结算中</div>";
                    }
                }
                if(!orderList.saleTime) {
                    orderList.saleTime = "--";
                } else {
                    saleTime = new Date(orderList.saleTime);
                    sT = {
                        Y: saleTime.getFullYear(),
                        M: saleTime.getMonth()+1,
                        D: saleTime.getDate(),
                        H: saleTime.getHours(),
                        m: saleTime.getMinutes()
                    };
                    sT.D = (sT.D < 10) ? "0"+sT.D+"" : sT.D;
                    sT.H = (sT.H < 10) ? "0"+sT.H+"" : sT.H;
                    sT.m = (sT.m < 10) ? "0"+sT.m+"" : sT.m;
                    orderList.saleTime = sT.Y + "-" + sT.M + "-" + sT.D + " " + sT.H + ":" + sT.m;
                }
                if(!orderList.expireTime) {
                    orderList.expireTime = "--";
                } else {
                    expireTime = new Date(orderList.expireTime);
                    cT = {
                        Y: expireTime.getFullYear(),
                        M: expireTime.getMonth()+1,
                        D: expireTime.getDate(),
                        H: expireTime.getHours(),
                        m: expireTime.getMinutes()
                    };
                    cT.D = (cT.D < 10) ? "0"+cT.D+"" : cT.D;
                    cT.H = (cT.H < 10) ? "0"+cT.H+"" : cT.H;
                    cT.m = (cT.m < 10) ? "0"+cT.m+"" : cT.m;
                    orderList.expireTime = cT.Y + "-" + cT.M + "-" + cT.D + " " + cT.H + ":" + cT.m;
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
                    + "<span class='saleTime'>上架时间:"+orderList.saleTime+"</span>"
                    + "<span class='expireTime'>结束时间:"+orderList.expireTime+"</span>"
                    + "<span class='sellerName'>卖家姓名:"+orderList.sellerName+"</span>"
                    + "<span class='sellerMobile'>卖家电话:"+orderList.sellerMobile+"</span>"
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
    var myBiddingData = {
            orderNo: "",
            stocksAmt: "",
            minMakeUp: "",
            maxMakeUp: "",
            maxPrice: "",
            nowPrice: ""
        };
    $(".contentRight").on("click", ".resultInfo>.order-false>.continueBidding-btn", function(e) {
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
            $("#setPreBidding").find(".info-tips").html("此次最小加价"+orderList.minMakeUp+"，最多加价"+orderList.maxMakeUp+ "，最高单价"+maxPrice);
            $("#setPreBidding").find(".preBidding-totalPrice").html(stocksAmt*nowPrice);
            myBiddingData.orderNo = orderNo;
            myBiddingData.stocksAmt = stocksAmt;
            myBiddingData.maxPrice = orderList.maxPrice;
            myBiddingData.minMakeUp = orderList.minMakeUp;
            myBiddingData.maxMakeUp = orderList.maxMakeUp;
            myBiddingData.nowPrice = orderList.nowPrice;
            $("#setPreBidding").modal("show");
            $("#setPreBidding").find(".info-tips").removeClass("active");
            $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
        }, "GET", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
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
                $("#setPreBidding").find(".preBidding-totalPrice").html(myBiddingData.stocksAmt*parseInt($(this).val()));
                if(myBiddingData.maxPrice >= myBiddingData.maxMakeUp+myBiddingData.nowPrice) {
                    maxPrice = myBiddingData.maxMakeUp+myBiddingData.nowPrice;
                    if(parseInt($(this).val()) <= maxPrice && parseInt($(this).val()) >= myBiddingData.nowPrice + myBiddingData.minMakeUp) {
                        $(this).parent().siblings(".info-tips").removeClass("active");
                        $("#setPreBidding .modal-footer>.btn").removeClass("btn-default").addClass("btn-primary");
                    } else {
                        $(this).parent().siblings(".info-tips").addClass("active");
                        $("#setPreBidding .modal-footer>.btn").removeClass("btn-primary").addClass("btn-default");
                    }
                } else if(myBiddingData.maxPrice < myBiddingData.maxMakeUp+myBiddingData.nowPrice) {
                    maxPrice = myBiddingData.maxPrice;
                    if(myBiddingData.maxPrice >= myBiddingData.minMakeUp+myBiddingData.nowPrice) {
                        if(parseInt($(this).val()) <= maxPrice && parseInt($(this).val()) >= myBiddingData.nowPrice + myBiddingData.minMakeUp) {
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
                nowPrice +=  myBiddingData.minMakeUp;
            } else if($(this).hasClass("preBidding-reduceprice")) {
                nowPrice -=  myBiddingData.minMakeUp;
            }
            if(myBiddingData.maxPrice >= myBiddingData.maxMakeUp+myBiddingData.nowPrice) {
                maxPrice = myBiddingData.maxMakeUp+myBiddingData.nowPrice;
                nowPrice = (nowPrice >= maxPrice) ? maxPrice : nowPrice;
                nowPrice = (nowPrice <= myBiddingData.nowPrice + myBiddingData.minMakeUp) ? myBiddingData.nowPrice + myBiddingData.minMakeUp : nowPrice;
            } else if(myBiddingData.maxPrice <= myBiddingData.maxMakeUp+myBiddingData.nowPrice) {
                maxPrice = myBiddingData.maxPrice;
                nowPrice = maxPrice;
            } else if(myBiddingData.nowPrice == myBiddingData.maxPrice) {
                maxPrice = myBiddingData.maxPrice;
                nowPrice = maxPrice;
            }
            $("#setPreBidding").find("#preBidding-unitPrice").val(nowPrice);
            $("#setPreBidding").find(".preBidding-totalPrice").html(myBiddingData.stocksAmt*nowPrice);
        })
    };
    setUnitPrice();
/**
*竞拍该标的
*/
    $("#setPreBidding .modal-footer>.btn").on("click", function(e) {
        var biddingPrice = parseInt($("#setPreBidding").find("#preBidding-unitPrice").val());
        var sendData = {
                "orderNo": myBiddingData.orderNo,
                "biddingPrice": biddingPrice
            };
        if($(this).hasClass("btn-primary")) {
            global_ajax("bidding", sendData, function(data) {
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
                    getMyBidding();
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
