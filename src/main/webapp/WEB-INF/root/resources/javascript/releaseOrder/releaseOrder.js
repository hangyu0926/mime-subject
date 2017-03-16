$(function() {
/**
*发布审核和撤销审核页面的隐藏和实现
*/
    $(".content-title>span").on("click", function(e) {
        var index = $(this).index();
    	$(this).addClass("active").siblings().removeClass("active");
    	if(index == 0) {
    		$(".issuedAudit").removeClass("hide");
    		$(".cancelAudit").addClass("hide");
    	} else if(index == 1) {
    		$(".issuedAudit").addClass("hide");
    		$(".cancelAudit").removeClass("hide");
    	}
    })


















})