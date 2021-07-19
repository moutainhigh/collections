<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	/* $(document).ready(function getcode(){
		
	var date = new Date;
		var year = date.getFullYear();
		for (var i = 11; i > 0; i--) {
			$("#tongjinianfen").comboxfield('addOption', year - i, year - i);
		}
		$("#tongjinianfen").comboxfield('addOption', year, year);
		$("#tongjinianfen").comboxfield("reload");

	}); */

	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				jazz.warn("初始日期大于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			jazz.warn("日期为空");
			return;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
				'\'' + begintime + '\''  +','+
				'\'' + endtime + '\''  +','+
				'\'' + hbegintime + '\''  +','+
				'\'' + hendtime + '\''
				+')" > 下     载</a>' + '</div>' 
		+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' +
		"<div id=\"电子商务投诉热点情况统计表_6034\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=850 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:638pt'>\n" + 
		" <col width=33 style='mso-width-source:userset;mso-width-alt:1056;width:25pt'>\n" + 
		" <col width=169 style='mso-width-source:userset;mso-width-alt:5408;width:127pt'>\n" + 
		" <col width=72 span=9 style='width:54pt'>\n" + 
		" <tr height=46 style='mso-height-source:userset;height:34.5pt'>\n" + 
		"  <td colspan=11 height=46 class=xl686034 width=850 style='height:34.5pt;\n" + 
		"  width:638pt'><a name=\"RANGE!A1\">电子商务投诉热点情况统计表</a></td>\n" + 
		" </tr>\n" + 
		" <tr height=21 style='height:15.75pt'>\n" + 
		"  <td colspan=2 rowspan=2 height=63 class=xl636034 width=202 style='height:\n" + 
		"  47.25pt;width:152pt'>项目</td>\n" + 
		"  <td colspan=5 class=xl636034 width=360 style='border-left:none;width:270pt'>涉及主体网站类型（本地/外地）</td>\n" + 
		"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>占投诉登记总量百分比（%）</td>\n" + 
		"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>上一时间段数据</td>\n" + 
		"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>比上一时间段增减（%）</td>\n" + 
		"  <td rowspan=2 class=xl636034 width=72 style='border-top:none;width:54pt'>去年同期数据</td>\n" + 
		" </tr>\n" + 
		" <tr height=42 style='height:31.5pt'>\n" + 
		"  <td height=42 class=xl636034 width=72 style='height:31.5pt;border-top:none;\n" + 
		"  border-left:none;width:54pt'>交易平台类</td>\n" + 
		"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>应用类</td>\n" + 
		"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>服务类</td>\n" + 
		"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>互联网门户网站</td>\n" + 
		"  <td class=xl636034 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>其它</td>\n" + 
		" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/dianZiShangWuReDian.do",
			data : {
				"begintime"		  : begintime ,
		        "endtime"         : endtime,
				"hbegintime"		  : hbegintime,
		        "hendtime"         : hendtime        
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("无数据");
					return;
				}
				var dataObj = eval("(" + data + ")");
				//console.log(dataObj);
				//console.log(dataObj.电子电器商品.应用类+".");
				//console.log(dataObj.电子电器商品["应用类"]+".");
				//console.log(dataObj.电子电器商品[应用类]+"[]");
				//console.log(dataObj.电子电器商品[应用类]+"[]");
				//console.log(dataObj.其它商品);
				htmls+=
					"<tr height=21 style='height:15.75pt'>\n" +
					"  <td rowspan=7 height=147 class=xl706034 style='border-bottom:.5pt solid black;\n" + 
					"  height:110.25pt;border-top:none'>商品</td>\n" + 
					"  <td class=xl646034 style='border-top:none;border-left:none'>电子电器商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.电子电器商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>机械类商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.机械类商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>烟酒饮料食品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.烟酒饮料食品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>建材装饰商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.建材装饰商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>珠宝首饰商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.珠宝首饰商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>日用百货商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.日用百货商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>其他商品</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.其他商品.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.其他商品.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他商品.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td rowspan=8 height=168 class=xl676034 width=33 style='height:126.0pt;\n" + 
					"  border-top:none;width:25pt'>服务</td>\n" + 
					"  <td class=xl646034 style='border-top:none;border-left:none'>游戏娱乐服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.游戏娱乐服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>中介服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.中介服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.中介服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.中介服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>物流快递服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.物流快递服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>旅游服务（订票业务）</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.旅游服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>金融支付服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.金融支付服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>软件服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.软件服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.软件服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.软件服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>即时通信服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.即时通信服务.同比数+    "</td>\n" + 
					" </tr>\n" + 
					" <tr height=21 style='height:15.75pt'>\n" + 
					"  <td height=21 class=xl646034 style='height:15.75pt;border-top:none;\n" + 
					"  border-left:none'>其他服务</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.交易平台类+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.应用类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.服务类+    "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.互联网门户+"</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.其它+      "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.占比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.环比数+    "</td>\n" + 
					"  <td class=xl666034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.环比+      "</td>\n" + 
					"  <td class=xl656034 style='border-top:none;border-left:none'>"+dataObj.其他服务类.同比数+    "</td>\n" + 
					" </tr>";
		/* 		
				for (var i = 0, t = dataObj.length; i < t; i++) {
					htmls +=
						"<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6624518 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
						"  <td class=xl6724518 style='border-top:none;border-left:none'>"+dataObj[i][shangnian]+"</td>\n" + 
						"  <td class=xl6724518 style='border-top:none;border-left:none'>"+dataObj[i][tongjinianfen]+"</td>\n" + 
						"  <td class=xl6824518 style='border-top:none;border-left:none'>"+dataObj[i].cnt+"</td>\n" + 
						" </tr>";
				} */
			htmls += '</table>' + '</div>';
				 var newWim = open("dianzishangwuredian_down.jsp", "_blank");
					window.setTimeout(function() {
						newWim.document.body.innerHTML = htmls;
					}, 500); 
			}
		}); 

	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="电子商务投诉热点情况统计表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="上期登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="上期登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>