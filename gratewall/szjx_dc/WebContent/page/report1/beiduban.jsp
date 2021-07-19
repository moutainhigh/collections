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
				+ 
				"<div id=\"被督办部门类型统计表_6905\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1530 class=xl636905\n" + 
				" style='border-collapse:collapse;table-layout:fixed;width:1148pt'>\n" + 
				" <col class=xl636905 width=90 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 2880;width:68pt'>\n" + 
				" <col class=xl636905 width=72 span=20 style='width:54pt'>\n" + 
				" <tr height=31 style='height:23.25pt'>\n" + 
				"  <td colspan=21 height=31 class=xl706905 width=1530 style='height:23.25pt;\n" + 
				"  width:1148pt'>被督办部门类型统计报表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=57 class=xl646905 width=90 style='height:42.75pt;\n" + 
				"  border-top:none;width:68pt'>被督办部门上级单位</td>\n" + 
				"  <td rowspan=2 class=xl646905 width=72 style='border-top:none;width:54pt'>被督办部门</td>\n" + 
				"  <td rowspan=2 class=xl686905 style='border-top:none'>督办件数</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>受理督办</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>调查督办</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>办结督办</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>分派</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>审批</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>决策</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>申请办结</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>办结确认</td>\n" + 
				"  <td colspan=2 class=xl676905 style='border-left:none'>再次办结</td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl646905 width=72 style='height:28.5pt;border-top:none;\n" + 
				"  border-left:none;width:54pt'>督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未督办数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>分派数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未分派数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>审批数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未审批数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>决策数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未决策数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>申请数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未申请数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>确认数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未确认数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>再次办结数</td>\n" + 
				"  <td class=xl646905 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>未再次办结数</td>\n" + 
				" </tr>";


		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/beiDuBan.do",
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
								"  <td height=19 class=xl656905 width=90 style='height:14.25pt;border-top:none;\n" + 
								"  width:68pt'>"+dataObj[i].上级单位+"</td>\n" + 
								"  <td class=xl656905 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].督办件数+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].受理+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].调查+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].办结+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].分派+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].审批+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].决策+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].申请办结+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].办结确认+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>"+dataObj[i].再次督办+"</td>\n" + 
								"  <td class=xl666905 style='border-top:none;border-left:none'>　</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("beiduban_down.jsp", "_blank");
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
		title="被督办部门类型统计表 统计条件">
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
