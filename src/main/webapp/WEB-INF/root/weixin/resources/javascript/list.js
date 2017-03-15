mui.ready(function() {
/**
*根据显示器屏幕大小来设定html的fontSize
*/
	var width = document.documentElement.clientWidth;
	var height = document.documentElement.clientHeight;
    var fontSize =document.documentElement.style.fontSize;
	var interval = "";
	var ori = ("orientationchange" in window) ? "orientationchange" : "resize";
    
    if (width > 0 && (width / fontSize !== 10)) {
      document.documentElement.style.fontSize = (width / 10) + "px"; 
    }
    document.getElementsByTagName("body")[0].style.width = ""+width+"px";
    document.getElementsByTagName("body")[0].style.height = ""+height+"px";
	window.addEventListener(ori, function(event){  
		clearInterval(interval);
		interval = setTimeout(function(){
			var width = document.documentElement.clientWidth;
			var height = document.documentElement.clientHeight;
		    var fontSize =document.documentElement.style.fontSize;
		    if (width > 0 && (width / fontSize !== 10)) {
		      document.documentElement.style.fontSize = (width / 10) + "px";
		    }
		    document.getElementsByTagName("body")[0].style.width = ""+width+"px";
		    document.getElementsByTagName("body")[0].style.height = ""+height+"px";
		},300);
	});  

    var global_type = (!window.location.href.split("?")[1]) ? "" : window.location.href.split("?")[1].split("=")[1];
    var historyState = (!window.location.href.split("?")[1].split("=")[2]) ? false : true;
    var global_tatal = "";
    var global_projectCondition = {
		pageSize: 20,
		pageNo: 1,
		categoryQuery: [],//项目分类条件
		stageQuery: [],   //项目融资阶段条件
		progressQuery: [],//项目进展条件
		keyword: ''
    }
/**
*数据加载请求方法
*/
	var getChoiceProject = function(isEmpty) {
		addrUrl = (global_type == "follow")?"queryRecommendProjectList":"getProjectList";
		global_ajax(addrUrl, global_projectCondition, function(data) {
				var recommentArray = data.detailInfo.projectSummaryVOList;
				var len = recommentArray.length;
				var recommentArrayList = null;
				var recommentlist = "";
				var standardlist = "";
				var dirUrl = "/xypms/weixin/views/info.html?projectId=";
				var picUrl = "";
				var picName = "";
				var img = "";
				global_tatal =Math.ceil(data.detailInfo.totalCount/20);
				if(len == 0) {
					$("#xy-projecterList-recomment").addClass("hide");
					$("#xy-projecterList-standard").addClass("hide");
					$("#noResult").removeClass("hide");
				} else {
					$("#xy-projecterList-recomment").removeClass("hide");
					$("#xy-projecterList-standard").removeClass("hide");
					$("#noResult").addClass("hide");
				}
				$.each(recommentArray, function(index, val) {
					recommentArrayList = recommentArray[index];
					picName = recommentArrayList.pictures;
					recommentArrayList.topCategory = !recommentArrayList.topCategory?"":recommentArrayList.topCategory+ " · ";
					if(recommentArrayList.pictures.length == 0) {
						picUrl = "";
						picName = "";
						img = "";
					}else {
						picUrl = (recommentArrayList.pictures[0]['url'] == null) ? "" : recommentArrayList.pictures[0]['url'];
						picName = (recommentArrayList.pictures[0]['picName'] == null) ? "" : recommentArrayList.pictures[0]['picName'];
						if(!picUrl) {
							img = "";
						} else {
							img = '<img class="images hide" src=""  data-src="' + picUrl + '" alt="图片LOGO">';
						}
					}
					var URL = dirUrl + recommentArrayList.projectId;
					if(recommentArrayList.recommendedMark == 1) {
						recommentlist += '<li>'
						+ '<a href='+URL+' target="_self">'
						+ '<div class="xy-projecterList-recommend-list-logo">' + img + '</div>'
						+ '<div class="xy-projecterList-recommend-list-right">'
						+ '<h4 class="xy-projecterList-recommend-list-right-companyName">'+recommentArrayList.projectName+'</h4>'
						+ '<span class="xy-projecterList-recommend-list-right-time">'+recommentArrayList.buildTime+'</span>'
						+ '<p class="xy-projecterList-recommend-list-right-companyDescription mui-ellipsis">'+recommentArrayList.summary+'<p>'
						+ '<div class="xy-projecterList-recommend-list-right-companyIntroduce clearfix">'
						+ '<span class="xy-projecterList-recommend-list-right-companyIntroduce-listOne">'+recommentArrayList.financingStage+'</span>'
						+ '<span class="xy-projecterList-recommend-list-right-companyIntroduce-listTwo">'+ recommentArrayList.topCategory + recommentArrayList.secondCategory +'</span>'
						+ '</div>'
						+ '</div>'
						+ '</a>'
						+ '</li>';
					} else {
						standardlist += '<li>'
						+ '<a href='+URL+' target="_self">'
						+ '<div class="xy-projecterList-recommend-list-logo">' + img + '</div>'
						+ '<div class="xy-projecterList-recommend-list-right">'
						+ '<h4 class="xy-projecterList-recommend-list-right-companyName">'+recommentArrayList.projectName+'</h4>'
						+ '<span class="xy-projecterList-recommend-list-right-time">'+recommentArrayList.buildTime+'</span>'
						+ '<p class="xy-projecterList-recommend-list-right-companyDescription mui-ellipsis">'+recommentArrayList.summary+'<p>'
						+ '<div class="xy-projecterList-recommend-list-right-companyIntroduce clearfix">'
						+ '<span class="xy-projecterList-recommend-list-right-companyIntroduce-listOne">'+recommentArrayList.financingStage+'</span>'
						+ '<span class="xy-projecterList-recommend-list-right-companyIntroduce-listTwo">'+ recommentArrayList.topCategory + recommentArrayList.secondCategory+'</span>'
						+ '</div>'
						+ '</div>'
						+ '</a>'
						+ '</li>';
					}
				})
				if(!isEmpty){
					$("#xy-projecterList-recomment").find(".xy-projecterList-recommend-list").append(recommentlist);
					$("#xy-projecterList-standard").find(".xy-projecterList-recommend-list").append(standardlist);
				} else {
					$("#xy-projecterList-recomment").find(".xy-projecterList-recommend-list").empty().append(recommentlist);
					$("#xy-projecterList-standard").find(".xy-projecterList-recommend-list").empty().append(standardlist);
				}

				if($("#xy-projecterList-recomment").find(".xy-projecterList-recommend-list li").length > 0) {
					$("#xy-projecterList-recomment").removeClass("hide");
				} else {
					$("#xy-projecterList-recomment").addClass("hide");
				}
				if($("#xy-projecterList-standard").find(".xy-projecterList-recommend-list li").length > 0) {
					$("#xy-projecterList-standard").removeClass("hide");
				} else {
					$("#xy-projecterList-standard").addClass("hide");
				}
				document.getElementById("projectSearchSubmit").classList.remove("choiceSelected");
				
				var num = document.getElementsByClassName('images').length;
			    var img = $(".images");
			    for(var i = 0; i < num; i++) {
			    	if($(img[i]).parent().offset().top < height && $(img[i]).parent().offset().top > 0) {
			    		$(img[i]).attr("src", $(img[i]).attr("data-src"));
			    		$(img[i]).removeClass("hide");
			    	}
			    }
		}, "post")
	};
/**
*图片预加载原理
*/
    lazyload(); //页面载入完毕加载可是区域内的图片
    window.onscroll = lazyload;
    function lazyload() { //监听页面滚动事件
		var num = document.getElementsByClassName('images').length;
	    var img = $(".images");
	    for(var i = 0; i < num; i++) {
	    	if($(img[i]).parent().offset().top < height && $(img[i]).parent().offset().top > 0) {
	    		$(img[i]).attr("src", $(img[i]).attr("data-src"));
	    		$(img[i]).removeClass("hide");
	    	}
	    }
    }
/**
*mui.init页面初始化
*pullRefresh上拉加载数据
*/
	mui.init({
		pullRefresh:{
	        container:"#scrollWrapperContent",//待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
	        up:{
	            height:100,//可选.默认50.触发上拉加载拖动距离
	            auto:false,//可选,默认false.自动上拉加载一次
	            contentrefresh : "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
	            contentnomore:'没有更多数据',//可选，请求完毕若没有更多数据时显示的提醒内容；
	            callback :function(){
			        if(global_projectCondition.pageNo >= global_tatal) {
			        	this.endPullupToRefresh(true)//console.log(global_projectCondition.pageNo+"+"+global_tatal) ;
			        } else {
		            	global_projectCondition.pageNo += 1;
			            this.endPullupToRefresh(false);
					    getChoiceProject(false);
			            mui('#scrollWrapperContent').pullRefresh().disablePullupToRefresh();
			            setTimeout(function(){
			            	mui('#scrollWrapperContent').pullRefresh().enablePullupToRefresh();
			            },1000)
			        }
	            } //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
	        }
	    }
	})
/**
*侧边栏多项选择滚动区域初始化
*/
	mui('#scrollWrapperChoice').scroll({
	    deceleration:0.0005//阻尼系数,系数越小滑动越灵敏
	});
/**
*主页展示部分滚动到上次浏览的地方
*/  
    var lastPosition = "";
    if(!window.sessionStorage.getItem("topPosition") || !historyState) {
        lastPosition = 0;
    } else {
    	lastPosition = parseFloat(window.sessionStorage.getItem("topPosition")) - parseFloat(window.sessionStorage.getItem("BT"));
    }
	mui('#scrollWrapperContent').scroll({
	    startY: lastPosition,//(window.sessionStorage.getItem("topPosition")>=0) ? window.sessionStorage.getItem("topPosition")-window.sessionStorage.getItem("BT") : -((Math.abs(window.sessionStorage.getItem("topPosition"))-window.sessionStorage.getItem("BT")*2),
	    bounce: true,
	    indicators: false,
	    deceleration:0.0005//阻尼系数,系数越小滑动越灵敏
	});
/**
*删除掉下拉的滑块
*/
	$("#scrollWrapperContent").find(".mui-scrollbar-vertical").eq(0).css('display', 'none');
/**
*禁止使用下拉刷新
*/
	var setDisOrEnablePullRefresh = function(){
		var oY = "";
		var nY = "";
		var disY = "";
		document.querySelector("#scrollWrapperContent").addEventListener("touchstart", function(e){
			oY = e.changedTouches[0].clientY;
		})
		document.querySelector("#scrollWrapperContent").addEventListener("touchend", function(e){
			nY = e.changedTouches[0].clientY;
			disY = nY - oY;
			if(disY < 0) {
				mui('#scrollWrapperContent').pullRefresh().enablePullupToRefresh();
			} else {
				mui('#scrollWrapperContent').pullRefresh().disablePullupToRefresh();
			}
		})
	}
	setDisOrEnablePullRefresh();

/**
*判断是新进来的还是历史记录返回
*true : 使用缓存的数据
*false： 从后台加载
*/

    if(historyState){
		global_projectCondition = $.extend(global_projectCondition,$.parseJSON(window.sessionStorage.getItem("projectCondition")));
		$("#xy-projecterList-recomment>ul").append(window.sessionStorage.getItem("recomment"));
		$("#xy-projecterList-recomment>ul").append(window.sessionStorage.getItem("standard"));
		document.querySelector(".xy-projecterList-head").style.top = ""+window.sessionStorage.getItem("HT")+"px";
        mui("#projectChoice li span").each(function() {
            if(this.getAttribute("dataIndex") == window.sessionStorage.getItem("status")) {
            	this.classList.add("choiceSelected");
            }else{
            	this.classList.remove("choiceSelected");
            }
        })
		if(window.sessionStorage.getItem("recomment") != "") {
			$("#xy-projecterList-recomment").removeClass("hide");
		} else {
			$("#xy-projecterList-recomment").addClass("hide");
		}

		if(window.sessionStorage.getItem("standard") != "") {
			$("#xy-projecterList-standard").removeClass("hide");
		} else {
			$("#xy-projecterList-standard").addClass("hide");
		}

        if(window.sessionStorage.getItem("standard") != "" || window.sessionStorage.getItem("recomment") != "") {
        	$("#noResult").addClass("hide");
        } else {
        	$("#noResult").removeClass("hide");
        }
       
		if(global_projectCondition.stageQuery.length == 1) {//融资阶段
			$("#xy-projecterCon-head-choice-content-financingStage>div").eq(global_projectCondition.stageQuery[0]).addClass("choiceSelected");
    	    $(".financingStage").html($("#xy-projecterCon-head-choice-content-financingStage>div").eq(global_projectCondition.stageQuery[0]).html());
		} else if(global_projectCondition.stageQuery.length == 5) {
			$("#xy-projecterCon-head-choice-content-financingStage>div").eq(0).addClass("choiceSelected");
    	    $(".financingStage").html($("#xy-projecterCon-head-choice-content-financingStage>div").eq(0).html());
		}

		if(global_projectCondition.progressQuery.length == 1) {//项目进展
			$("#xy-projecterCon-head-choice-content-projectDebriefing>div").eq(global_projectCondition.progressQuery[0]).addClass("choiceSelected");
    	    $(".projectDebriefing").html($("#xy-projecterCon-head-choice-content-projectDebriefing>div").eq(global_projectCondition.progressQuery[0]).html());
		} else if(global_projectCondition.progressQuery.length == 5) {
			$("#xy-projecterCon-head-choice-content-projectDebriefing>div").eq(0).addClass("choiceSelected");
    	    $(".projectDebriefing").html($("#xy-projecterCon-head-choice-content-projectDebriefing>div").eq(0).html());
		}

		if(global_projectCondition.categoryQuery.length !=  0){
			$.each(global_projectCondition.categoryQuery, function(index, val){
			     $("#projecterListOption>li>ul>li").eq(val-7).addClass("choiceSelected");
			})
		}
        $("#projecterListOption li ul li").each(function(index, val) {
        	if($(this).hasClass("choiceSelected")) {
        	    $(this).parent().prev().addClass("choiceSelected");
        	}
        })

		$("#projectSearch").val(global_projectCondition.keyword);

    } else {
    	getChoiceProject(true);
    }
/**
*导航浮动设置
*/  
	mui("#projecterListContent").on("swipeup", "#scrollWrapperContent", function(e){
		if(Math.abs($(".xy-projecterList-head").offset().top) == 0) {
			if ($(".xy-projecterList-content-box").height() <= height) {
			    return
		    } else {
				$(".xy-projecterList-head").attr("style", "");
				$(".xy-projecterList-head").addClass("pullUp");
		 		$(".xy-projecterList-head").removeClass("pullDown");
		    }
		}
	})
	mui("#projecterListContent").on("swipedown", "#scrollWrapperContent", function(e){
		if($(".xy-projecterList-head").offset().top == 0) {
            return;
		} else {
			$(".xy-projecterList-head").attr("style", "");
			$(".xy-projecterList-head").removeClass("pullUp");
	 		$(".xy-projecterList-head").addClass("pullDown");
	 		setTimeout(function(){
			    $(".xy-projecterList-head").css("top", "0px");
	 		}, 400)
		}
	})

    var setNavFixed = function(){
		var oY = "";
		var nY = "";
		var disYH = "";
		var isTrue = "";
		var lastTopH = (!historyState) ? $(".xy-projecterList-head").offset().top : window.sessionStorage.getItem("HT");
		var T = $(".xy-projecterList-head").height();
		var B = (!historyState) ? $(".xy-projecterList-head").offset().top : window.sessionStorage.getItem("HT");
    	
    	mui("#projecterListContent").on("touchstart", "#scrollWrapperContent", function(e){
			oY = e.changedTouches[0].clientY;
			if(document.querySelector(".xy-projecterList-head").classList.contains("pullUp")) {
				$(".xy-projecterList-head").removeClass("pullUp");
				$(".xy-projecterList-head").css("top", ""+-T+"px");
			} else if (document.querySelector(".xy-projecterList-head").classList.contains("pullDown")) {
				$(".xy-projecterList-head").removeClass("pullDown");
				$(".xy-projecterList-head").css("top", "0px");
			} else if (lastTopH != 0 && lastTopH != -T) {
				$(".xy-projecterList-head").removeClass("pullDown");
				$(".xy-projecterList-head").removeClass("pullUp");
				$(".xy-projecterList-head").css("top", ""+lastTopH+"px");
			}
    	})
    	mui("#projecterListContent").on("touchmove", "#scrollWrapperContent", function(e){
			nY = e.changedTouches[0].clientY;
			disYH = nY - oY + parseFloat(lastTopH);
		    disYH = (disYH <-T) ?-T : disYH;
		    disYH = (disYH > 0) ? 0 : disYH;
			if ($(".xy-projecterList-content-box").height() <= height) {
			    return
		    } else {
			    $(".xy-projecterList-head").css({"top":""+disYH+"px", "transition":"top 0s"});
		    }
    	})
    	mui("#projecterListContent").on("touchend", "#scrollWrapperContent", function(e){
			lastTopH = $(".xy-projecterList-head").offset().top;
        })
    }
    setNavFixed();
/**
*点击项目跳转页面
*/
	mui(".xy-projecterList-recommend-list").on("tap", "a", function(e){
	    var href = "";
		if(historyState) {
			href = window.location.href;
		} else {
			href = window.location.href + "=true";
		}
		history.replaceState({},"",href);
	    var sxx = $.param(global_projectCondition);
	    var conOne = $("#xy-projecterList-recomment .xy-projecterList-recommend-list").html();
	    var conTwo = $("#xy-projecterList-standard .xy-projecterList-recommend-list").html();
	    window.sessionStorage.setItem("recomment", conOne);
	    window.sessionStorage.setItem("standard", conTwo);
	    window.sessionStorage.setItem("projectCondition", JSON.stringify(global_projectCondition));
        window.sessionStorage.setItem("BT", $("#projecterListContent").offset().top);
        window.sessionStorage.setItem("HT", $(".xy-projecterList-head").offset().top);
        mui("#projectChoice li span").each(function() {
            if(this.classList.contains("choiceSelected")) {
            	window.sessionStorage.setItem("status", this.getAttribute("dataIndex"));
            }
        })
        window.sessionStorage.setItem("topPosition", $("#xy-projecterList-content-box").offset().top);
		window.location.href = this.getAttribute("href");
	})
/**
*点击页面的筛选按钮弹出右侧菜单栏
*/   
	document.getElementById("projectListBombBox").style.display = "none";
	mui("#projectChoice").on("tap", "#projectFilter span", function(event) {   
		var bombBox = document.getElementById("projectListBombBox");
		var leftMenu = document.getElementById("projectListLeftMenu");
		var bg = document.getElementById("bgBackdrop");
		
		mui("#projectChoice li span").each(function () {
			this.classList.remove("choiceSelected");
		})
		this.classList.add("choiceSelected");
		leftMenu.style.display = "block";
		bg.classList.remove("hide");
		document.getElementById("xy-projecterCon-head-choice-content").classList.remove("slideDown");
		document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.add("hide");
		document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.add("hide");
		document.getElementById("bgBackdropTwo").classList.add("hide");
		if(!bombBox.classList.contains("pullRight")) {
			bombBox.style.display = "block";
			setTimeout(function(){
				bombBox.classList.add("pullRight");
			}, 50)
			return 
		}
	})
/**
*点击蒙版收起右侧菜单栏
*/	
	mui("#projectListLeftMenu").on("tap", "#bgBackdrop", function(event) {
		var bombBox = document.getElementById("projectListBombBox");
		var leftMenu = document.getElementById("projectListLeftMenu");
		var bg = document.getElementById("bgBackdrop");
		var len = global_projectCondition.categoryQuery.length;
		var dataIndex = "";
		if(bombBox.classList.contains("pullRight")) {
		    mui("#scrollWrapperChoice").scroll().scrollTo(0,0,0);
		    bombBox.classList.remove("pullRight");
		    bg.classList.add("hide");
		    setTimeout(function(){
			    leftMenu.style.display = "none";
                mui("#projecterListOption li h4").each(function() {
                	this.classList.remove("choiceSelected");
                });
			    mui("#projecterListOption li ul li").each(function() {
    		        this.classList.remove("choiceSelected");
      				dataIndex = this.getAttribute("dataIndex");
      				for(var i = 0; i<len; i++) {
      					if(dataIndex == global_projectCondition.categoryQuery[i]) {
	      					this.classList.add("choiceSelected");
		    		        this.parentNode.previousSibling.previousSibling.classList.add("choiceSelected");
      					}
      				}
    	        });
		    }, 400)
		}
	})
/**
*右侧弹出框确定按钮，点击收起右侧菜单,并请求数据
*/
	mui("#projectListBombBox").on("tap", "#bombBoxDetermine", function(event) {
		var bombBox = document.getElementById("projectListBombBox");
		var leftMenu = document.getElementById("projectListLeftMenu");
		var bg = document.getElementById("bgBackdrop");

		bombBox.classList.remove("pullRight");
		bg.classList.add("hide");
	    setTimeout(function(){
		    leftMenu.style.display = "none";
	    }, 400)
        global_projectCondition.categoryQuery = [];
        mui("#projecterListOption li ul li.choiceSelected").each(function() {
            global_projectCondition.categoryQuery.push(this.getAttribute("dataIndex"));
        })
    	mui('#scrollWrapperContent').pullRefresh().refresh(true);
        global_projectCondition.pageNo  = 1;
    	getChoiceProject(true);
    	mui("#scrollWrapperContent").scroll().scrollTo(0,0,0);
	})
/**
*弹出融资阶段选择下拉框
*/
	mui("#projectChoice").on("tap", ".xy-projecterCon-head-choice-title-financingStage span", function(event) {
		var choiceContent = document.getElementById("xy-projecterCon-head-choice-content");
		mui("#projectChoice li span").each(function () {
			this.classList.remove("choiceSelected");
		})
		this.classList.add("choiceSelected");
		if(document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.contains("hide")) {
			choiceContent.classList.add("slideDown");
			document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.remove("hide");
			document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.add("hide");
			document.getElementById("bgBackdropTwo").classList.remove("hide");
		} else {
			choiceContent.classList.remove("slideDown");
			document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.add("hide");
			document.getElementById("bgBackdropTwo").classList.add("hide");
		}
	})
/**
*弹出项目进展选择下拉框
*/
	mui("#projectChoice").on("tap", ".xy-projecterCon-head-choice-title-projectDebriefing span", function(event) {
		var choiceContent = document.getElementById("xy-projecterCon-head-choice-content");
		mui("#projectChoice li span").each(function () {
			this.classList.remove("choiceSelected");
		})
		this.classList.add("choiceSelected");
		if(document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.contains("hide")) {
			choiceContent.classList.add("slideDown");
			document.getElementById("bgBackdropTwo").classList.remove("hide");
			document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.remove("hide");
			document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.add("hide");
		} else {
			choiceContent.classList.remove("slideDown");
			document.getElementById("bgBackdropTwo").classList.add("hide");
			document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.add("hide");
		}
	})
/**
*收起选择下拉框
*/
	mui("body").on("tap", "#bgBackdropTwo", function(event) {
		document.getElementById("bgBackdropTwo").classList.add("hide");
		document.getElementById("xy-projecterCon-head-choice-content").classList.remove("slideDown");
	})
/**
*融资阶段5个选项的选择
*/
    mui("#xy-projecterCon-head-choice-content-financingStage").on("tap", "div", function(event) {
    	var dv = event.target;
    	var dataIndex = parseInt(dv.getAttribute("dataIndex"));
    	var txt = this.innerHTML;
    	if(dataIndex == 0) {
    		global_projectCondition.stageQuery = [1,2,3,4,5];
    	    document.querySelector(".financingStage").innerHTML = "融资阶段";
    	} else {
    		global_projectCondition.stageQuery = [dataIndex];
	    	document.querySelector(".financingStage").innerHTML = txt;
    	}
		mui("#xy-projecterCon-head-choice-content-financingStage div").each(function () {
			this.classList.remove("choiceSelected");
		})
		dv.classList.add("choiceSelected");
		document.getElementById("xy-projecterCon-head-choice-content-financingStage").classList.add("hide");
    	document.getElementById("xy-projecterCon-head-choice-content").classList.remove("slideDown");
		document.getElementById("bgBackdropTwo").classList.add("hide");
        mui('#scrollWrapperContent').pullRefresh().refresh(true);
        global_projectCondition.pageNo = 1;
    	getChoiceProject(true);
    	mui("#scrollWrapperContent").scroll().scrollTo(0,0,0);
    })
/**
*项目进展5个选项的选择
*/
    mui("#xy-projecterCon-head-choice-content-projectDebriefing").on("tap", "div", function(event) {
    	var dv = event.target;
    	var dataIndex = parseInt(dv.getAttribute("dataIndex"));
    	var txt = this.innerHTML;
        if(dataIndex == 0) {
    		global_projectCondition.progressQuery = [1,2,3,4,5];
    	    document.querySelector(".projectDebriefing").innerHTML = "项目进展";
        } else {
			global_projectCondition.progressQuery = [dataIndex];
	    	document.querySelector(".projectDebriefing").innerHTML = txt;
        }
		mui("#xy-projecterCon-head-choice-content-projectDebriefing div").each(function () {
			this.classList.remove("choiceSelected");
		})
		dv.classList.add("choiceSelected");
		document.getElementById("xy-projecterCon-head-choice-content-projectDebriefing").classList.add("hide");
    	document.getElementById("xy-projecterCon-head-choice-content").classList.remove("slideDown");
		document.getElementById("bgBackdropTwo").classList.add("hide");
        mui('#scrollWrapperContent').pullRefresh().refresh(true);
        global_projectCondition.pageNo = 1;
    	getChoiceProject(true);
    	mui("#scrollWrapperContent").scroll().scrollTo(0,0,0);
    })
/**
*右侧菜单栏的多项选测
*/    
    mui("#projecterListOption").on("tap", "li ul li", function(event) {
    	var dv = event.target;
    	if(dv.classList.contains("choiceSelected")) {
    		dv.classList.remove("choiceSelected");
    		if($(dv).parent().find(".choiceSelected").length == 0) {
    			$(dv).parent().prev().removeClass("choiceSelected");
    		}
    	} else {
            dv.classList.add("choiceSelected");
    		$(dv).parent().prev().addClass("choiceSelected");
    	}
    })

    $("#projecterListOption h4").on("tap" ,function(e) {
    	if($(this).hasClass("choiceSelected")) {
    		$(this).removeClass("choiceSelected");
    		$(this).next().children().removeClass("choiceSelected");
    	} else {
    		$(this).addClass("choiceSelected");
    		$(this).next().children().addClass("choiceSelected");
    	}
    })

/**
*右侧菜单栏的清除按钮，清除所有选项
*/
    mui("#projectListBombBox").on("tap", "#clearProjecterOption", function() {
	    mui("#projecterListOption li h4").each(function() {
	    	this.classList.remove("choiceSelected");
	    });
    	mui("#projecterListOption li ul li").each(function() {
    		this.classList.remove("choiceSelected");
    	});
    	global_projectCondition.categoryQuery = [];
    }) 
/**
*导航栏的输入
*/
    mui("#headSearch").on("input", "#projectSearch", function(event) {
   	    var dv = event.target;
   	    document.getElementById("iconClear").classList.add("show");
    })

    document.getElementById("projectSearch").onfocus = function(e){
   	    this.placeholder = "";
    }
    document.getElementById("projectSearch").onblur = function(e){
   	    this.placeholder = "请输入关键字";
   	    document.getElementById("iconClear").classList.remove("show");
    }
/**
*点击输入框的清除按钮，就可以清空输入框内的内容
*/
    mui("#headSearch").on("tap", "#iconClear", function(event) {
   	    this.classList.remove("show");
   	    document.getElementById("projectSearch").value = "";
    })

/**
*点击搜索按钮查询结果
*/
    mui("#headSearch").on("tap", "#projectSearchSubmit", function(event) {
        global_projectCondition.keyword = document.getElementById("projectSearch").value;
   	    this.classList.add("choiceSelected");
        global_projectCondition.pageNo = 1;
   	    getChoiceProject(true);
        mui('#scrollWrapperContent').pullRefresh().refresh(true);
   	    mui("#scrollWrapperContent").scroll().scrollTo(0,0,0);
    })
})


