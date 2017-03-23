$(function() {
/**
*isHistory:               是否为历史返回记录
*getConfirmOrderDataJson：确认订单》》发送的数据
*backoutAuditDatajson：   待审核订单-撤销审核》》发送的数据
*/    
    var isHistory = (window.location.href.indexOf("?") == -1) ? false : true;
    var winHeight = document.documentElement.clientHeight;
    var getConfirmOrderDataJson = {
            pageSize: 4,
            pageNo: 1
        };
/**
*待确认订单
*toEmpty:true/false 是否清空之前的数据
*/ 
    var getConfirmedList = function(toEmpty){
        global_ajax("beConfirmedList", getConfirmOrderDataJson, function(data) {
            var len = data.detailInfo.orderDTOList.length;
            var orderList = null;
            var list = "";
            for(var i = 0; i < len; i++) {
                orderList = data.detailInfo.orderDTOList[i];
                list += "<li>"
                    + "<div class='orderInfo clearfix'>"
                    + "<div>"
                    + "<span class='orderNo'>订单号:"+orderList.orderNo+"</span>"
                    + "<span class='userName'>发布人:"+orderList.sellerName+"</span>"
                    + "<span class='stocksAmt'>股权数:"+orderList.stockAmt+"</span>"
                    + "<span class='initialPrice'>起拍单价:"+orderList.initialPrice+"</span>"
                    + "<span class='saleTime'>上架时间:"+(new Date(orderList.saleTime)).getFullYear()+"-"+((new Date(orderList.saleTime)).getMonth()+1)+"-"+(new Date(orderList.saleTime)).getDate()+"</span>"
                    + "<span class='maxBiddingPrice'>最终竞价:"+orderList.maxBiddingPrice+"</span>"
                    + "<span class='buyName'>竞拍获胜者:"+orderList.buyName+"</span>"
                    + "<span class='buyerCounts'>竞价轮数:"+orderList.buyerCounts+"</span>"
                    + "</div>"
                    + "</div>"
                    + "<div class='resultInfo clearfix'>"
                    + "<div class='audit-btn'>"
                    + "<span class='audit-true'>完成该订单</span>"
                    + "</div>"
                    + "<div class='auditIdea'>"
                    + "<span>确认双方已线下完成订单，交易完成</span>"
                    + "</div>"
                    + "</div>"
                    + "</li>";
            }
            if(toEmpty) {
                $("#confirmOrder").empty();
            }
            $("#confirmOrder").append(list);
            $("#confirmOrder").attr("maxPage", Math.ceil(data.detailInfo.totalCount/4));
        }, "POST", function(data) {
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        })
    };
/**
*完成该订单
*/
    $(".contentRight").on("click", ".resultInfo>.audit-btn>.audit-true", function(e) {
        var orderNo = $(this).parents(".resultInfo").siblings(".orderInfo").find(".orderNo").html().split(":")[1];
        var sendData = {
                "orderNo": orderNo,
                "confirmUser": 3
            };
        global_ajax("confirmOrder", sendData, function(data) {
            $("#errorTips").find(".myModal-body").html("操作成功");
            $("#errorTips").modal("show");
            getConfirmOrderDataJson.pageNo = 1;
            getConfirmedList(true);
        }, "POST", function(data){
            $("#errorTips").find(".myModal-body").html(data.desc);
            $("#errorTips").modal("show");
        });
    })
/**
*页面滚动到当前页面最底部时加载下一页
*ConfirmedListBottom:发布审核页面滚动到底部
*cancelBottom:撤销审核页面滚动到底部
*/
    var addNextPageData = function(){
        var ConfirmedListBottom = "";
        window.onscroll = function(e) {
            var ConfirmedListBottom = document.querySelector("#confirmOrder").getBoundingClientRect().bottom;
            if(ConfirmedListBottom == winHeight - 40) {
                getConfirmOrderDataJson.pageNo += 1;
                if(getConfirmOrderDataJson.pageNo <= parseInt($(".confirmOrder").attr("maxPage"))) {
                    getConfirmedList();
                } else {
                    getConfirmOrderDataJson.pageNo = parseInt($(".confirmOrder").attr("maxPage"));
                }
            }
        };
    };
    addNextPageData();
/**
*判断是否为浏览器返回历史纪录加载数据
*historyData:上一次存贮的信息（包含哪个页面， 当前第几页）
*/
    if(!isHistory) {
        getConfirmedList(true);
    } else {
        var historyData = getCookie("confirmOrder");
        $("#confirmOrder").html(window.sessionStorage.getItem("confirmOrder"));
        getConfirmOrderDataJson.pageNo = parseInt(historyData.split("&")[0]);
        $(".confirmOrder").attr("maxPage", parseInt(historyData.split("&")[1]));
        history.replaceState({}, "", "confirmOrder.html");
    }
/**
*点击子菜单展示对应的页面
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
        var confirmOrder = $("#confirmOrder").html();
        var confirmOrderData = getConfirmOrderDataJson.pageNo + "&" + $(".confirmOrder").attr("maxPage");
        if($(this).attr("urlSrc") == "confirmOrder.html" || $(this).attr("urlSrc") == "confirmOrder.html?true"){
            return;
        } else {
            setCookie("confirmOrderData", confirmOrderData);
            window.sessionStorage.setItem("confirmOrder", confirmOrder);
            history.replaceState({}, "", window.location.href+"?true");
        }
    })
})