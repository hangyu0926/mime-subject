;function accountAdd(){
	var start=$("#auction-starting").val().trim();
	var auctionMax=$("#auction-max").val().trim();
	var auctionDay=$("#auction-day").val().trim();
	var auctionYuan=$("#auction-yunxu").val().trim();
	var auctionMin=$("#auction-min").val().trim();
	var auctionMaxmoney=$("#auction-maxmoney").val().trim();
	if(start.length==0 || auctionMax.length==0 || auctionDay.length==0 ||auctionYuan.length==0 || auctionMin.length==0 ||auctionMaxmoney.length==0){
		return;
	};	
	start=parseInt(start);
	auctionMax=parseInt(auctionMax);
	auctionDay=parseInt(auctionDay);
	auctionYuan=parseInt(auctionYuan);
	auctionMin=parseInt(auctionMin);
	auctionMaxmoney=parseInt(auctionMaxmoney);
	if(start>auctionMax){
		global_dialog.error("起拍单价要小于最大单价",function(){
			closeAlertDialog();
		});
		return 
	};
	if(auctionYuan>auctionDay){
		global_dialog.error("竞拍周期要小于撤销时间",function(){
			closeAlertDialog();
		});
		return 
	};
	if(auctionMin>auctionMaxmoney){
		global_dialog.error("最大加价要大于最小加价",function(){
			closeAlertDialog();
		});
		return 
	};
	global_ajax("updateConfig",{
		"startAmt":start,
		"maxAmt":auctionMax,
		"stockTime":auctionDay,
		"backTime":auctionYuan,
		"minAdd":auctionMin,
		"maxAdd":auctionMaxmoney
	},function(data){
		global_dialog.error("配置成功",function(){
			closeAlertDialog();
		});
	},"post",function(data){
		global_dialog.error(data.desc,function(){
			closeAlertDialog();
		});
	});
};