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
				+"<div id=\"表2_31564\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1171 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:883pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:68pt'>\n" + 
				" <col width=74 span=3 style='mso-width-source:userset;mso-width-alt:2368;\n" + 
				" width:56pt'>\n" + 
				" <col width=73 span=4 style='mso-width-source:userset;mso-width-alt:2336;\n" + 
				" width:55pt'>\n" + 
				" <col width=130 style='mso-width-source:userset;mso-width-alt:4160;width:98pt'>\n" + 
				" <col width=74 style='mso-width-source:userset;mso-width-alt:2368;width:56pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
				" <col width=73 style='mso-width-source:userset;mso-width-alt:2336;width:55pt'>\n" + 
				" <tr height=38 style='mso-height-source:userset;height:28.5pt'>\n" + 
				"  <td colspan=14 height=38 class=xl6531564 width=1171 style='border-right:.5pt solid black;\n" + 
				"  height:28.5pt;width:883pt'>各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl6331564 width=73 style='height:28.5pt;\n" + 
				"  border-top:none;width:55pt'>辖区</td>\n" + 
				"  <td colspan=11 class=xl6331564 width=881 style='border-left:none;width:665pt'>《餐饮服务许可证》</td>\n" + 
				"  <td rowspan=2 class=xl6331564 width=144 style='border-top:none;width:108pt'>《食品流通许可证》</td>\n" + 
				"  <td rowspan=2 class=xl6331564 width=73 style='border-top:none;width:55pt'>总计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6331564 width=90 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none;width:68pt'>特大型餐饮</td>\n" + 
				"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>大型餐饮</td>\n" + 
				"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>中型餐饮</td>\n" + 
				"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>小型餐饮</td>\n" + 
				"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>快餐店</td>\n" + 
				"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>小吃店</td>\n" + 
				"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>饮品店</td>\n" + 
				"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>食堂</td>\n" + 
				"  <td class=xl6331564 width=130 style='border-top:none;border-left:none;\n" + 
				"  width:98pt'>集体用餐配送单位</td>\n" + 
				"  <td class=xl6331564 width=74 style='border-top:none;border-left:none;\n" + 
				"  width:56pt'>中央厨房</td>\n" + 
				"  <td class=xl6331564 width=73 style='border-top:none;border-left:none;\n" + 
				"  width:55pt'>合计</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/queryFoodstuff/queryShiPinCanYinXuKe.do",
					data : "begintime=" + begintime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+="<tr height=19 style='height:14.25pt'>\n" +
								" <td height=19 class=xl6431564 width=73 style='height:14.25pt;border-top:none;\n" + 
								" width:55pt'>"+dataObj[i].辖区+"</td>\n" + 
								" <td class=xl6831564 width=90 style='border-top:none;border-left:none;\n" + 
								" width:68pt'>"+dataObj[i].特大型餐饮+"</td>\n" + 
								" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
								" width:56pt'>"+dataObj[i].大型餐饮+"</td>\n" + 
								" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
								" width:56pt'>"+dataObj[i].中型餐饮+"</td>\n" + 
								" <td class=xl6831564 width=74 style='border-top:none;border-left:none;\n" + 
								" width:56pt'>"+dataObj[i].小型餐饮+"</td>\n" + 
								" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].快餐店+"</td>\n" + 
								" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].小吃店+"</td>\n" + 
								" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].饮品店+"</td>\n" + 
								" <td class=xl6831564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].食堂+"</td>\n" + 
								" <td class=xl6931564 width=130 style='border-top:none;border-left:none;\n" + 
								" width:98pt'>"+dataObj[i].集体用餐配送单位+"</td>\n" + 
								" <td class=xl6931564 width=74 style='border-top:none;border-left:none;\n" + 
								" width:56pt'>"+dataObj[i].中央厨房+"</td>\n" + 
								" <td class=xl6931564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].合计+"</td>\n" + 
								" <td class=xl6931564 width=144 style='border-top:none;border-left:none;\n" + 
								" width:108pt'>"+dataObj[i].食品流通+"</td>\n" + 
								" <td class=xl6931564 width=73 style='border-top:none;border-left:none;\n" + 
								" width:55pt'>"+dataObj[i].总计+"</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("shipin_canyin_xuke_down.jsp", "_blank");
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
		title="各辖区持有效《餐饮服务许可证》、《食品流通许可证》主体分类统计表 统计条件">
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