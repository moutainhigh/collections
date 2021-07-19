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
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
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
				+ ','
				+ '\''
				+ hbegintime
				+ '\''
				+ ','
				+ '\''
				+ hendtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+
				"<div id=\"归档审核统计表_3084\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1080 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:816pt'>\n" + 
				" <col width=90 span=12 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
				" width:68pt'>\n" + 
				" <tr height=50 style='mso-height-source:userset;height:37.5pt'>\n" + 
				"  <td colspan=12 height=50 class=xl633084 width=1080 style='height:37.5pt;\n" + 
				"  width:816pt'>归档审核统计报表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=57 class=xl643084 width=90 style='height:42.75pt;\n" + 
				"  border-top:none;width:68pt'>被驳回部门上级单位</td>\n" + 
				"  <td rowspan=2 class=xl653084 style='border-top:none'>被驳回部门</td>\n" + 
				"  <td rowspan=2 class=xl653084 style='border-top:none'>归档审核数</td>\n" + 
				"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>归档审核通过数</td>\n" + 
				"  <td colspan=4 class=xl653084 style='border-left:none'>被驳回数</td>\n" + 
				"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>上一时段数据</td>\n" + 
				"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>环比增减</td>\n" + 
				"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>去年同期数据</td>\n" + 
				"  <td rowspan=2 class=xl643084 width=90 style='border-top:none;width:68pt'>同比增减</td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl643084 width=90 style='height:28.5pt;border-top:none;\n" + 
				"  border-left:none;width:68pt'>使用法律法则有误</td>\n" + 
				"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>证据不充分</td>\n" + 
				"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>反馈信息不完整</td>\n" + 
				"  <td class=xl643084 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>需转派而未转派</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/queryXiaoBao/guiDang.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&hbegintime="+hbegintime+
							"&hendtime="+hendtime+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += 

								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl663084 width=90 style='height:14.25pt;border-top:none;\n" + 
								"  width:68pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl663084 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].部门+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].归档+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].通过数+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].有误+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].证据不充分+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].不完整+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].转派+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].上一时段+"</td>\n" + 
								"  <td class=xl683084 style='border-top:none;border-left:none'>"+dataObj[i].环比+"</td>\n" + 
								"  <td class=xl673084 style='border-top:none;border-left:none'>"+dataObj[i].去年+"</td>\n" + 
								"  <td class=xl683084 style='border-top:none;border-left:none'>"+dataObj[i].同比+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("guidang_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 300);
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="归档审核统计表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			<div id="hbegintime" name='hbegintime' valuetip='默认为上月同期...' name='hbegintime' vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hendtime" name='hendtime' valuetip='默认为上月同期...' vtype="datefield" label="环比截止日期"
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