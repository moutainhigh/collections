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
<script type="text/javascript">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
					+
				"<div id=\"各辖区持有效《食品经营许可证》主体分类统计表_1266\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1666 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1255pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <col width=74 style='mso-width-source:userset;mso-width-alt:2368;width:56pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <col width=90 span=2 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
				" width:68pt'>\n" + 
				" <col width=73 span=2 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
				" width:55pt'>\n" + 
				" <col width=74 span=4 style='mso-width-source:userset;mso-width-alt:2368;\n" + 
				" width:56pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <col width=140 style='mso-width-source:userset;mso-width-alt:4480;width:105pt'>\n" + 
				" <col width=73 span=2 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
				" width:55pt'>\n" + 
				" <col width=123 span=2 style='mso-width-source:userset;mso-width-alt:3936;\n" + 
				" width:92pt'>\n" + 
				" <col width=73 span=3 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
				" width:55pt'>\n" + 
				" <tr height=27 style='height:20.25pt'>\n" + 
				"  <td colspan=20 height=27 class=xl641266 width=1666 style='border-right:.5pt solid black;\n" + 
				"  height:20.25pt;width:1255pt'>各辖区持有效《食品经营许可证》主体分类统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl631266 width=73 style='height:28.5pt;\n" + 
				"  border-top:none;width:55pt'>辖区</td>\n" + 
				"  <td colspan=6 class=xl631266 width=473 style='border-left:none;width:357pt'>食品销售经营者</td>\n" + 
				"  <td colspan=8 class=xl631266 width=655 style='border-left:none;width:494pt'>餐饮服务经营者</td>\n" + 
				"  <td colspan=4 class=xl631266 width=392 style='border-left:none;width:294pt'>单位食堂</td>\n" + 
				"  <td rowspan=2 class=xl631266 width=73 style='border-top:none;width:55pt'>总计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl631266 width=74 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none;width:56pt'>商场超市</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>便利店</td>\n" + 
				"  <td class=xl631266 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>食品贸易商</td>\n" + 
				"  <td class=xl631266 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>酒类批发商</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>其它</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>合计</td>\n" + 
				"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>大型餐馆</td>\n" + 
				"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>中型餐馆</td>\n" + 
				"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>小型餐馆</td>\n" + 
				"  <td class=xl631266 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>微小餐饮</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>饮品店</td>\n" + 
				"  <td class=xl631266 width=140 style='border-top:none;border-left:none;\n" + 
				"  width:105pt'>中央厨房集体配送</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>其它</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>合计</td>\n" + 
				"  <td class=xl631266 width=123 style='border-top:none;border-left:none;\n" + 
				"  width:92pt'>学校幼儿园食堂</td>\n" + 
				"  <td class=xl631266 width=123 style='border-top:none;border-left:none;\n" + 
				"  width:92pt'>机关企事业单位</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>其它</td>\n" + 
				"  <td class=xl631266 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>合计</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/queryFoodstuff/queryShiPinXuKe.do",
					data : "begintime=" + begintime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							var tm = "";
							
							if(dataObj[i].辖区==null){
								tm ="总计"
							}else{
								tm = dataObj[i].辖区;
							}
							
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl631266 width=73 style='height:14.25pt;border-top:none;\n" + 
								"  width:55pt'>"+tm+"</td>\n" + 
								"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
								"  width:56pt'>"+dataObj[i].食品商场超市+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].食品便利店+"</td>\n" + 
								"  <td class=xl671266 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].食品贸易商+"</td>\n" + 
								"  <td class=xl671266 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].酒类批发商+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].食品其他+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].食品合计+"</td>\n" + 
								"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
								"  width:56pt'>"+dataObj[i].大型餐馆+"</td>\n" + 
								"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
								"  width:56pt'>"+dataObj[i].中型餐馆+"</td>\n" + 
								"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
								"  width:56pt'>"+dataObj[i].小型餐馆+"</td>\n" + 
								"  <td class=xl671266 width=74 style='border-top:none;border-left:none;\n" + 
								"  width:56pt'>"+dataObj[i].微小餐饮+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].饮品店+"</td>\n" + 
								"  <td class=xl671266 width=140 style='border-top:none;border-left:none;\n" + 
								"  width:105pt'>"+dataObj[i].中央+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].餐饮其他+"</td>\n" + 
								"  <td class=xl671266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].餐饮合计+"</td>\n" + 
								"  <td class=xl681266 width=123 style='border-top:none;border-left:none;\n" + 
								"  width:92pt'>"+dataObj[i].学校食堂+"</td>\n" + 
								"  <td class=xl681266 width=123 style='border-top:none;border-left:none;\n" + 
								"  width:92pt'>"+dataObj[i].机关食堂+"</td>\n" + 
								"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].其他单位食堂+"</td>\n" + 
								"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].食堂合计+"</td>\n" + 
								"  <td class=xl681266 width=73 style='border-top:none;border-left:none;\n" + 
								"  width:55pt'>"+dataObj[i].总计+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("shipin_xuke_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);		
	}

				</script>


</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="各辖区持有效《食品经营许可证》主体分类统计表  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="有效期" labelAlign="right" labelwidth='100px' width="310"></div>
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