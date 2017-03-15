$(function() {
/**
*左侧菜单高度始终适应真个屏幕
*/
    var winHeight = document.documentElement.clientHeight;
    $(".leftMenu").css("height", winHeight);
    $(window).resize(function(e) {
	    var winHeight = document.documentElement.clientHeight;
	    $(".leftMenu").css("height", winHeight);
    })
/**
*菜单选项展示
*/
    // var menuFirst = window.sessionStorage.getItem("menuState").split("&")[0];
    // var menuSecond = window.sessionStorage.getItem("menuState").split("&")[1];
    // $(".leftMenu>ul>li").eq(menuFirst).addClass("active");
    // $(".leftMenu>ul>li:eq("+menuFirst+") li").eq(menuSecond).addClass("active");
/**
*点击菜单时使其子菜单展示出来
*/
    $(".leftMenu>ul>li").on("click", function(e) {
    	$(this).addClass("active").siblings().removeClass("active");
    })
/**
*点击子菜单展示对应的页面
*/
    $(".leftMenu .secondMenu>li").on("click", function(e) {
    	var urlSrc = $(this).attr("urlSrc");
        var menuState = $(this).parents("li").index() + "&" + $(this).index();
        window.sessionStorage.setItem("menuState", menuState);
    	window.location.href = urlSrc;
    })
/**
*阻止模态框弹出时出现滚动条
*/
    $(".modal").on("show.bs.modal", function(){
        $(this).css("overflow", "hidden");
    })















})