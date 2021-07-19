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
				"<div id=\"举报件处理情况统计表_31449\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=828 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:624pt'>\n" + 
				" <col width=144 span=2 style='mso-width-source:userset;mso-width-alt:4608;\n" + 
				" width:108pt'>\n" + 
				" <col width=90 span=6 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
				" width:68pt'>\n" + 
				" <tr height=49 style='mso-height-source:userset;height:36.75pt'>\n" + 
				"  <td colspan=8 height=49 class=xl6331449 width=828 style='height:36.75pt;\n" + 
				"  width:624pt'>举报件处理情况统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
				"  <td height=40 class=xl6431449 style='height:30.0pt;border-top:none'>上级单位</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>处理部门</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>信息件数</td>\n" + 
				"  <td class=xl6631449 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>去年同期信息件数</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>办结率</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>立案数</td>\n" + 
				"  <td class=xl6431449 style='border-top:none;border-left:none'>立案率</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/juBao.do",
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
								" <td height=19 class=xl6531449 width=144 style='height:14.25pt;border-top:\n" + 
								" none;width:108pt'>"+dataObj[i].处室+"</td>\n" + 
								" <td class=xl6531449 width=144 style='border-top:none;border-left:none;\n" + 
								" width:108pt'>"+dataObj[i].处理部门+"</td>\n" + 
								" <td class=xl6731449 style='border-top:none;border-left:none'>"+dataObj[i].信息件+"</td>\n" + 
								" <td class=xl6831449 style='border-top:none;border-left:none'>"+dataObj[i].同比+"</td>\n" + 
								" <td class=xl6731449 style='border-top:none;border-left:none'>"+dataObj[i].已办结+"</td>\n" + 
								" <td class=xl6831449 style='border-top:none;border-left:none'>"+dataObj[i].办结率+"</td>\n" + 
								" <td class=xl6731449 style='border-top:none;border-left:none'>"+dataObj[i].立案数+"</td>\n" + 
								" <td class=xl6831449 style='border-top:none;border-left:none'>"+dataObj[i].立案率+"</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("jubao_down.jsp", "_blank");
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
		title="举报件处理情况统计表  统计条件">
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
