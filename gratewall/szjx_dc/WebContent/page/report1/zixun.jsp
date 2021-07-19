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
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+"<div id=\"咨询件处理情况统计表_22260\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=648 class=xl6422260\n" + 
				" style='border-collapse:collapse;table-layout:fixed;width:486pt'>\n" + 
				" <col class=xl6422260 width=144 span=2 style='mso-width-source:userset;\n" + 
				" mso-width-alt:4608;width:108pt'>\n" + 
				" <col class=xl6422260 width=72 style='width:54pt'>\n" + 
				" <col class=xl6422260 width=144 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 4608;width:108pt'>\n" + 
				" <col class=xl6422260 width=72 span=2 style='width:54pt'>\n" + 
				" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
				"  <td colspan=6 height=40 class=xl6322260 width=648 style='height:30.0pt;\n" + 
				"  width:486pt'>咨询件处理情况统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6522260 style='height:14.25pt;border-top:none'>上级部门</td>\n" + 
				"  <td class=xl6522260 style='border-top:none;border-left:none'>处理部门</td>\n" + 
				"  <td class=xl6522260 style='border-top:none;border-left:none'>信息件数</td>\n" + 
				"  <td class=xl6522260 style='border-top:none;border-left:none'>去年同期信息件数</td>\n" + 
				"  <td class=xl6522260 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl6522260 style='border-top:none;border-left:none'>办结率</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/ziXun.do",
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
								"  <td height=19 class=xl6622260 width=144 style='height:14.25pt;border-top:\n" + 
								"  none;width:108pt'>"+dataObj[i].处室+"</td>\n" + 
								"  <td class=xl6622260 width=144 style='border-top:none;border-left:none;\n" + 
								"  width:108pt'>"+dataObj[i].处理部门+"</td>\n" + 
								"  <td class=xl6722260 style='border-top:none;border-left:none'>"+dataObj[i].信息件+"</td>\n" + 
								"  <td class=xl6822260 style='border-top:none;border-left:none'>"+dataObj[i].同比+"</td>\n" + 
								"  <td class=xl6722260 style='border-top:none;border-left:none'>"+dataObj[i].已办结+"</td>\n" + 
								"  <td class=xl6822260 style='border-top:none;border-left:none'>"+dataObj[i].办结率+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("zixun_down.jsp", "_blank");
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
		title="咨询件处理情况统计表  统计条件">
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
