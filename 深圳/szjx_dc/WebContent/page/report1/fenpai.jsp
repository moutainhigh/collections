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
				+"<div id=\"分派至监管所信息件数据统计_14439\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1086 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:815pt'>\n" + 
				" <col width=99 style='mso-width-source:userset;mso-width-alt:3168;width:74pt'>\n" + 
				" <col width=109 style='mso-width-source:userset;mso-width-alt:3488;width:82pt'>\n" + 
				" <col width=72 span=10 style='width:54pt'>\n" + 
				" <col width=86 style='mso-width-source:userset;mso-width-alt:2752;width:65pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=44 style='mso-height-source:userset;height:33.0pt'>\n" + 
				"  <td colspan=14 height=44 class=xl6614439 width=1086 style='height:33.0pt;\n" + 
				"  width:815pt'>分派至监管所信息件数据统计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl6414439 style='height:28.5pt;border-top:none'>辖区局</td>\n" + 
				"  <td rowspan=2 class=xl6414439 style='border-top:none'>监管所</td>\n" + 
				"  <td colspan=2 class=xl6414439 style='border-left:none'>咨询</td>\n" + 
				"  <td colspan=2 class=xl6414439 style='border-left:none'>举报</td>\n" + 
				"  <td colspan=2 class=xl6414439 style='border-left:none'>市场监管投诉</td>\n" + 
				"  <td colspan=2 class=xl6414439 style='border-left:none'>建议</td>\n" + 
				"  <td colspan=2 class=xl6414439 style='border-left:none'>其他</td>\n" + 
				"  <td rowspan=2 class=xl6414439 style='border-top:none'>监管所总计</td>\n" + 
				"  <td rowspan=2 class=xl6514439 width=72 style='border-top:none;width:54pt'>各分局所有信息件总计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6314439 width=72 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none;width:54pt'>汇总</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>办结量</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>汇总</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>办结量</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>汇总</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>办结量</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>汇总</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>办结量</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>汇总</td>\n" + 
				"  <td class=xl6314439 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>办结量</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/fenPai.do",
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
								" <td height=19 class=xl6314439 width=99 style='height:14.25pt;border-top:none;\n" + 
								" width:74pt'>"+dataObj[i].辖区局+"</td>\n" + 
								" <td class=xl6314439 width=109 style='border-top:none;border-left:none;\n" + 
								" width:82pt'>"+dataObj[i].监管所+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].咨询汇总+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].咨询办结量+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].举报汇总+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].举报办结量+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].市场监管投诉汇总+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].市场监管投诉办结量+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].建议汇总+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].建议办结量+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].其他汇总+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].其他办结量+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].监管所总计+"</td>\n" + 
								" <td class=xl6714439 style='border-top:none;border-left:none'>"+dataObj[i].分局总计+"</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("fenpai_down.jsp", "_blank");
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
		title="分派至监管所信息件数据统计  统计条件">
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
