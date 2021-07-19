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
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var regcode = aa.data.regcode;
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
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'+
				"<div id=\"市场监管投诉件处理情况统计表_25747\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=830 class=xl6325747\n" + 
				" style='border-collapse:collapse;table-layout:fixed;width:624pt'>\n" + 
				" <col class=xl6325747 width=150 span=2 style='mso-width-source:userset;\n" + 
				" mso-width-alt:4800;width:113pt'>\n" + 
				" <col class=xl6325747 width=72 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 2304;width:54pt'>\n" + 
				" <col class=xl6325747 width=89 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 2848;width:67pt'>\n" + 
				" <col class=xl6325747 width=72 span=4 style='width:54pt'>\n" + 
				" <col class=xl6325747 width=81 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 2592;width:61pt'>\n" + 
				" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
				"  <td colspan=9 height=40 class=xl6425747 width=830 style='height:30.0pt;\n" + 
				"  width:624pt'>市场监管投诉件处理情况统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
				"  <td height=36 class=xl6625747 style='height:27.0pt;border-top:none'>上级单位</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>处理部门</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>信息件数</td>\n" + 
				"  <td class=xl6725747 width=89 style='border-top:none;border-left:none;\n" + 
				"  width:67pt'>去年同期信息件数</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>办结率</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>调解数</td>\n" + 
				"  <td class=xl6625747 style='border-top:none;border-left:none'>调解成功</td>\n" + 
				"  <td class=xl6725747 width=81 style='border-top:none;border-left:none;\n" + 
				"  width:61pt'>调解成功率</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/touSu.do",
					data : "begintime=" + begintime + "&endtime=" + endtime 
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6825747 width=150 style='height:14.25pt;border-top:\n" + 
								"  none;width:113pt'>"+dataObj[i].处室+"</td>\n" + 
								"  <td class=xl6825747 width=150 style='border-top:none;border-left:none;\n" + 
								"  width:113pt'>"+dataObj[i].处理部门+"</td>\n" + 
								"  <td class=xl6925747 style='border-top:none;border-left:none'>"+dataObj[i].信息件+"</td>\n" + 
								"  <td class=xl7025747 style='border-top:none;border-left:none'>"+dataObj[i].同比+"</td>\n" + 
								"  <td class=xl6925747 style='border-top:none;border-left:none'>"+dataObj[i].已办结+"</td>\n" + 
								"  <td class=xl7025747 style='border-top:none;border-left:none'>"+dataObj[i].办结率+"</td>\n" + 
								"  <td class=xl6925747 style='border-top:none;border-left:none'>"+dataObj[i].调解数+"</td>\n" + 
								"  <td class=xl6925747 style='border-top:none;border-left:none'>"+dataObj[i].调解成功数+"</td>\n" + 
								"  <td class=xl7025747 style='border-top:none;border-left:none'>"+dataObj[i].调解成功率+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("tousu_down.jsp", "_blank");
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
		title="市场监管投诉件处理情况统计表 统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
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
