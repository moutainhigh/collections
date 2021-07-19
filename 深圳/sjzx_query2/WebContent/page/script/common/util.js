/**
 * 日期格式化公共方法
 */
Date.prototype.format =function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};

//接收参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (r != null){
		var paramValue = r[2];
		paramValue = decodeURI(paramValue);
		return paramValue;
	}else{
		return null; //返回参数值
	}
}

function paramsEncodeURI(params){
	if(typeof params=="object" ){
		for(var p in params){
			params[p]=encodeURI(params[p]);
		}
	}else if(typeof params=="string"){
		params=encodeURI(params);
	}
	return params;
}