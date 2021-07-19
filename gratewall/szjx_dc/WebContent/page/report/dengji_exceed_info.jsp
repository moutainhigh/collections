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
				+
				"<div id=\"全流程网上商事登记业务超期办理情况表_13186\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=695 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:522pt'>\n" + 
				" <col width=78 style='mso-width-source:userset;mso-width-alt:2496;width:59pt'>\n" + 
				" <col width=155 style='mso-width-source:userset;mso-width-alt:4960;width:116pt'>\n" + 
				" <col width=234 style='mso-width-source:userset;mso-width-alt:7488;width:176pt'>\n" + 
				" <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>\n" + 
				" <col width=123 style='mso-width-source:userset;mso-width-alt:3936;width:92pt'>\n" + 
				" <tr height=56 style='mso-height-source:userset;height:42.0pt'>\n" + 
				"  <td colspan=5 height=56 class=xl6413186 width=695 style='height:42.0pt;\n" + 
				"  width:522pt'>全流程网上商事登记业务超期办理情况表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6313186 style='height:14.25pt'>序号</td>\n" + 
				"  <td class=xl6313186 style='border-left:none'>流程号</td>\n" + 
				"  <td class=xl6313186 style='border-left:none'>大厅</td>\n" + 
				"  <td class=xl6313186 style='border-left:none'>办理时限</td>\n" + 
				"  <td class=xl6313186 style='border-left:none'>办理人员</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/shangShiZhuTi/dengjiExceedInfo.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += 
								"<tr height=19 style='height:14.25pt'>\n" +
								" <td height=19 class=xl6513186 style='height:14.25pt;border-top:none'>"+(i+1)+"</td>\n" + 
								" <td class=xl6613186 style='border-top:none;border-left:none'>"+dataObj[i].applyNo+"</td>\n" + 
								" <td class=xl6613186 style='border-top:none;border-left:none'>"+dataObj[i].windowName+"</td>\n" + 
								" <td class=xl6613186 style='border-top:none;border-left:none'>"+dataObj[i].timeExceed+"</td>\n" + 
								" <td class=xl6613186 style='border-top:none;border-left:none'>"+dataObj[i].userName+"</td>\n" + 
								"</tr>";

						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("dengji_exceed_info_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="全流程网上商事登记业务超期办理情况表  统计条件">
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