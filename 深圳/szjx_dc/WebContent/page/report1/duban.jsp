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
				+"<div id=\"督办统计报表_13574\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1050 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:790pt'>\n" + 
				" <col class=xl6513574 width=203 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 6496;width:152pt'>\n" + 
				" <col class=xl6513574 width=197 style='mso-width-source:userset;mso-width-alt:\n" + 
				" 6304;width:148pt'>\n" + 
				" <col width=65 span=10 style='mso-width-source:userset;mso-width-alt:2080;\n" + 
				" width:49pt'>\n" + 
				" <tr height=47 style='mso-height-source:userset;height:35.25pt'>\n" + 
				"  <td colspan=12 height=47 class=xl6613574 width=1050 style='height:35.25pt;\n" + 
				"  width:790pt'>督办（件数）统计报表</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl6413574 height=59 style='mso-height-source:userset;height:44.25pt'>\n" + 
				"  <td height=59 class=xl6913574 width=203 style='height:44.25pt;border-top:\n" + 
				"  none;width:152pt'>被督办处室</td>\n" + 
				"  <td class=xl6913574 width=197 style='border-top:none;border-left:none;\n" + 
				"  width:148pt'>被督办部门</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>被督办总数</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>被督办一次件数</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>被督办两次件数</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>被督办三件级以上件数</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>环节超时（件/次）</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>受理反馈超时（件/次）</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>调查反馈超时（件/次）</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>办结反馈超时（件/次）</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>自动督办</td>\n" + 
				"  <td class=xl6913574 width=65 style='border-top:none;border-left:none;\n" + 
				"  width:49pt'>人工督办</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/duBan.do",
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
								"  <td height=19 class=xl7013574 width=203 style='height:14.25pt;border-top:\n" + 
								"  none;width:152pt'>"+dataObj[i].处室+"</td>\n" + 
								"  <td class=xl6913574 width=197 style='border-top:none;border-left:none;\n" + 
								"  width:148pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].总数+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].一次+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].两次+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].三次+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].环节超时+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].受理超时+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].调查超时+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].办结超时+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].自动+"</td>\n" + 
								"  <td class=xl6813574 style='border-top:none;border-left:none'>"+dataObj[i].人工+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("duban_down.jsp", "_blank");
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
		title="督办统计报表  统计条件">
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
