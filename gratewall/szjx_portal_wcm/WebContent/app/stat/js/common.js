/*
*  子页面中需要引入的JS文件
*	这个文件中定义了搜索组件 和tab标签组件的使用，防止需要在每一个子页面中进行配置，导致对这些组件配置项进行修改时的繁琐
*/
var SQLINDEX = {
	DOC_STAT : {
		DOC_NUM:1,
		DOC_STATUS:2
	}
}

var SEARCH_CONFIG = {
	USER_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"发稿人",
				value:"username"
			},{
				text:"部门名称",
				value:"gname"
		}]
	},
	GROUP_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"部门名称",
				value:"gname"
		}]
	},
	CHANNEL_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"栏目名称",
				value:"chnlname"
		},{
				text:"站点名称",
				value:"sitename"
		}]
	},
	SITE_DEFAULT : {
		select_time_defaultValue:0,
		select_item:[{
				text:"站点名称",
				value:"sitename"
		}]
	},
	CHANNEL_HITSCOUNT : {
		select_item:[{
			text:"栏目名称",
			value:"hostName"
		},{
			text:"部门名称",
			value:"GroupName"
		},{
			text:"站点名称",
			value:"siteName"
		}]	
	},
	SITE_HITSCOUNT : {
		select_item:[{
			text:"站点名称",
			value:"SiteName"
		}]
	},
	DOCUMENT_HITSCOUNT : {
		select_item:[{
			text:"稿件名称",
			value:"DocTitle"
		},{
			text:"发稿人",
			value:"DocCrUser"
		},{
			text:"部门名称",
			value:"GroupName"
		}]
	},
	DOCUMENT_HITSCOUNT_BYUSER : {
		select_item:[{
			text:"栏目名称",
			value:"hostName"
		},{
			text:"站点名称",
			value:"siteName"
		}]	
	},
	GROUP_HITSCOUNT : {
		select_item:[{
			text:"部门名称",
			value:"GroupName"
		}]
	},
	SPECIAL_HITSCOUNT : {
		select_item:[{
			text:"专题名称",
			value:"HostName"
		},{
			text:"部门名称",
			value:"GroupName"
		}]
	}
}
var TABS_CONFIG = {
	USER_DEFAULT : {
		items:[{
			key:'table',
			url:'user_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'user_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'user_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'user_trendchart.jsp',
			desc:'走势图'
		}]
	},
	GROUP_DEFAULT : {
		items:[{
			key:'table',
			url:'group_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'group_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'group_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'group_trendchart.jsp',
			desc:'走势图'
		}]
	},
	CHANNEL_DEFAULT : {
		items:[{
			key:'table',
			url:'chnl_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'chnl_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'chnl_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'chnl_trendchart.jsp',
			desc:'走势图'
		}]
	},
	SITE_DEFAULT : {
		items:[{
			key:'table',
			url:'site_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'site_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'site_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'site_trendchart.jsp',
			desc:'走势图'
		}]
	},
	CHANNEL_HITSCOUNT : {
		items:[{
			key:'table',
			url:'channel_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'channel_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'channel_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'channel_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	SITE_HITSCOUNT : {
		items:[{
			key:'table',
			url:'site_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'site_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'site_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'site_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	SPECIAL_HITSCOUNT : {
		items:[{
			key:'table',
			url:'special_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'special_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'special_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'special_trendchart.jsp',
			desc:'走势图'
		}]	
	},
	GROUP_HITSCOUNT : {
		items:[{
			key:'table',
			url:'group_datatable.jsp',
			desc:'数据表'
		},{
			key:'barchart',
			url:'group_barchart.jsp',
			desc:'柱状图'
		},{
			key:'piechart',
			url:'group_piechart.jsp',
			desc:'饼状图'
		},{
			key:'trendchart',
			url:'group_trendchart.jsp',
			desc:'走势图'
		}]	
	}
	
}

/*
*  将JSON格式转换成检索字符串
*/
var parseJsonToParams = function(jsonObject){
	if(jsonObject==null)
		return '';
	if(Ext.isSimpType(jsonObject))
		return jsonObject + '';
	var vReturn = [];
	for(var paramName in jsonObject){
		if(!paramName || !jsonObject[paramName])continue;
		vReturn.push(paramName+"="+encodeURIComponent(jsonObject[paramName]));
	}
	return vReturn.join('&');
}

/*
*  时间相关方法
*/
function fmt2Digit(n){
	return n>=10 ? n : '0' + n;
}
Date.prototype.format = function(fm){
	if(!fm)return "";
	var dt=this;
	fm = fm.replace(/yyyy/ig,dt.getFullYear());
	var y = "" + dt.getFullYear();
	y = y.substring(y.length-2);
	return fm.replace(/yy/ig, y)
		.replace(/mm/g,fmt2Digit(dt.getMonth()+1))
		.replace(/dd/ig,fmt2Digit(dt.getDate()))
		.replace(/hh/ig,fmt2Digit(dt.getHours()))
		.replace(/MM/g,fmt2Digit(dt.getMinutes()))
		.replace(/ss/ig,fmt2Digit(dt.getSeconds()));
}

/*
*  给返回按钮添加事件
*/
Event.observe(window,"load",function(){
	var el =$("return_btn");
	if(!el) return;
	Event.observe(el,"click",function(){
		// 只需要TimeItem 参数
		var params = {
			TimeItem: getParameter("TimeItem")
		}
		if(el.getAttribute("url")){
			var sUrl = el.getAttribute("url");
			if(sUrl.indexOf("?")>0)
				location.href=sUrl+parseJsonToParams(params);
			else
				location.href=sUrl+"?"+parseJsonToParams(params);
		}else
			history.go(-1);
	});
});

/*
*  获取某一类radio和checkbox的值
*/
function $F$F(name){
	var eles = document.getElementsByName(name);
	var rst = [], v;
	for(var i=0; i<eles.length; i++){
		if(eles[i].tagName!="INPUT")continue;
		if(eles[i].checked)
			rst.push($F(eles[i]));
	}
	return rst.join(',');
}


/*
*  获取页面中最合适的页面分页大小
*/
function getBestPageSize(){
	var bestSize = 15;
	var screenHeight = window.screen.height;
	if(screenHeight>=1000)bestSize = 20;
	else if(screenHeight>=900)bestSize = 15;
	else bestSize = 10;
	return bestSize;
}