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
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+
				"<div id=\"消委会登记信息涉及金额统计表_16545\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=795 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:597pt'>\n" + 
				" <col width=530 style='mso-width-source:userset;mso-width-alt:16960;width:398pt'>\n" + 
				" <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:68pt'>\n" + 
				" <col width=88 style='mso-width-source:userset;mso-width-alt:2816;width:66pt'>\n" + 
				" <col width=87 style='mso-width-source:userset;mso-width-alt:2784;width:65pt'>\n" + 
				" <tr height=28 style='height:21.0pt'>\n" + 
				"  <td colspan=4 height=28 class=xl6516545 width=795 style='border-right:1.0pt solid black;\n" + 
				"  height:21.0pt;width:597pt'>消委会登记信息涉及金额统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
				"  <td height=20 class=xl6816545 width=530 style='height:15.0pt;border-top:none;\n" + 
				"  width:398pt'>消费类型</td>\n" + 
				"  <td class=xl6916545 width=90 style='border-top:none;width:68pt'>涉及金额</td>\n" + 
				"  <td class=xl6916545 width=88 style='border-top:none;width:66pt'>案值</td>\n" + 
				"  <td class=xl6916545 width=87 style='border-top:none;width:65pt'>经济损失</td>\n" + 
				" </tr>";
				
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryXwhMoney.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += 
								"<tr height=20 style='height:15.0pt'>\n" +
								"  <td height=20 class=xl6416545 width=530 style='height:15.0pt;width:398pt'>"+dataObj[i].消费类型+"　</td>\n" + 
								"  <td class=xl6316545 width=90 style='width:68pt'><span lang=EN-US>"+dataObj[i].涉及金额+"　</span></td>\n" + 
								"  <td class=xl6316545 width=88 style='width:66pt'><span lang=EN-US>"+dataObj[i].案值+"　</span></td>\n" + 
								"  <td class=xl6316545 width=87 style='width:65pt'><span lang=EN-US>"+dataObj[i].经济损失+"　</span></td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("xwh_dj_money_down.jsp", "_blank");
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
		title="消委会登记信息涉及金额统计表  统计条件">
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
