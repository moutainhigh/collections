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
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ "<div id=\"登记信息涉及金额统计表_18824\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=630 style='border-collapse:\n" + 
			" collapse;table-layout:fixed;width:473pt'>\n"
				+ " <col width=223 style='mso-width-source:userset;mso-width-alt:7136;width:167pt'>\n"
				+ " <col width=149 style='mso-width-source:userset;mso-width-alt:4768;width:112pt'>\n"
				+ " <col width=134 style='mso-width-source:userset;mso-width-alt:4288;width:101pt'>\n"
				+ " <col width=124 style='mso-width-source:userset;mso-width-alt:3968;width:93pt'>\n"
				+ " <tr height=38 style='mso-height-source:userset;height:28.5pt'>\n"
				+ "  <td colspan=4 height=38 class=xl6718824 width=630 style='border-right:1.0pt solid black;\n" + 
			"  height:28.5pt;width:473pt'>登记信息涉及金额统计表</td>\n"
				+ " </tr>\n"
				+ " <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n"
				+ "  <td height=20 class=xl6418824 width=223 style='height:15.0pt;border-top:none;\n" + 
			"  width:167pt'>消费类型</td>\n"
				+ "  <td class=xl6518824 width=149 style='border-top:none;width:112pt'>涉及金额</td>\n"
				+ "  <td class=xl6518824 width=134 style='border-top:none;width:101pt'>案值</td>\n"
				+ "  <td class=xl6518824 width=124 style='border-top:none;width:93pt'>经济损失</td>\n"
				+ " </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/quert12315Controller/queryDjMoney.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr height=20 style='height:15.0pt'>\n"
									+ "  <td height=20 class=xl6618824 width=223 style='height:15.0pt;width:167pt'>"
									+ dataObj[i].消费类型
									+ "　</td>\n"
									+ "  <td class=xl6318824 width=149 style='width:112pt'><span lang=EN-US>"
									+ dataObj[i].涉及金额
									+ "　</span></td>\n"
									+ "  <td class=xl6318824 width=134 style='width:101pt'><span lang=EN-US>　"
									+ dataObj[i].案值
									+ "</span></td>\n"
									+ "  <td class=xl6318824 width=124 style='width:93pt'><span lang=EN-US>　"
									+ dataObj[i].经济损失 + "</span></td>\n"
									+ " </tr>";

						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("dj_of_money_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="登记信息涉及金额统计表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>

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