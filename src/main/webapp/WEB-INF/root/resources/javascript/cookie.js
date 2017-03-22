/**
*setCookie:储存cookie; 
*getCookie:获取cookie;
*name：    键值;
*value：   储存值;
*/
var setCookie = function(name,value,expireday){
	var dts = new Date();
	dts.setDate(dts.getDate() + expireday);
	if(!expireday){
		document.cookie = name + "=" + encodeURIComponent(value);
	}else{
		document.cookie = name + "=" + encodeURIComponent(value) + ";expire=" + dts.toUTCString();
	}
};

var getCookie = function(name){
	if(document.cookie.length > 0){
		positionStart = document.cookie.indexOf(name);
		if(positionStart != -1){
			positionStart += name.length+1;
		}
		positionEnd = document.cookie.indexOf(";",positionStart);
		if(positionEnd == -1){
			positionEnd = document.cookie.length;
		}
		return decodeURIComponent(document.cookie.substring(positionStart, positionEnd));
	}else{
		return "";
	}
};








































































