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
	$(document).ready(function getcode(){
		$('#regcode').comboxfield('addOption', "咨询举报申诉中心","6100");
		$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/getRegCode.do",
			data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
					+ "&timess=" + new Date() + "&id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var  i = 0; i < dataObj.length; i++) {
					$('#regcode').comboxfield('addOption', dataObj[i].name, dataObj[i].regdepcode);
				}
				}
		});
	});
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
				+ ','
				+ '\''
				+ regcode
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+

				"<div id=\"登记量统计报表（按接收类型分）_16601\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=881 style='border-collapse:\n" + 
			" collapse;table-layout:fixed;width:661pt'>\n"
				+ " <col width=222 style='mso-width-source:userset;mso-width-alt:7104;width:167pt'>\n"
				+ " <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>\n"
				+ " <col width=72 span=3 style='width:54pt'>\n"
				+ " <col width=87 style='mso-width-source:userset;mso-width-alt:2784;width:65pt'>\n"
				+ " <col width=115 style='mso-width-source:userset;mso-width-alt:3680;width:86pt'>\n"
				+ " <col width=72 span=2 style='width:54pt'>\n"
				+ " <tr height=60 style='mso-height-source:userset;height:45.0pt'>\n"
				+ "  <td colspan=9 height=60 class=xl6716601 width=881 style='border-right:1.0pt solid black;\n" + 
			"  height:45.0pt;width:661pt'>登记量统计报表（按接收类型分）</td>\n"
				+ " </tr>\n"
				+ " <tr height=37 style='height:27.75pt'>\n"
				+ "  <td height=37 class=xl6516601 width=222 style='height:27.75pt;border-top:\n" + 
			"  none;width:167pt'>接收方式</td>\n"
				+ "  <td class=xl6616601 width=97 style='border-top:none;width:73pt'>市场监管投诉</td>\n"
				+ "  <td class=xl6616601 width=72 style='border-top:none;width:54pt'>举报</td>\n"
				+ "  <td class=xl6616601 width=72 style='border-top:none;width:54pt'>咨询</td>\n"
				+ "  <td class=xl6616601 width=72 style='border-top:none;width:54pt'>建议</td>\n"
				+ "  <td class=xl6616601 width=87 style='border-top:none;width:65pt'>消委会投诉</td>\n"
				+ "  <td class=xl6616601 width=115 style='border-top:none;width:86pt'>行政监察与效能</td>\n"
				+ "  <td class=xl6616601 width=72 style='border-top:none;width:54pt'>其他</td>\n"
				+ "  <td class=xl6616601 width=72 style='border-top:none;width:54pt'>总计</td>\n"
				+ " </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/quert12315Controller/queryTongJiByType.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random()+"&regcode="+regcode,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr height=24 style='mso-height-source:userset;height:18.0pt'>\n"
									+ "  <td height=24 class=xl6316601 width=222 style='height:18.0pt;width:167pt'>　"
									+ dataObj[i].姓名
									+ "</td>\n"
									+ "  <td class=xl6416601 width=97 style='width:73pt'>"
									+ dataObj[i].市场监管投诉
									+ "　</td>\n"
									+ "  <td class=xl6416601 width=72 style='width:54pt'>　"
									+ dataObj[i].举报
									+ "</td>\n"
									+ "  <td class=xl6416601 width=72 style='width:54pt'>　"
									+ dataObj[i].咨询
									+ "</td>\n"
									+ "  <td class=xl6416601 width=72 style='width:54pt'>　"
									+ dataObj[i].建议
									+ "</td>\n"
									+ "  <td class=xl6416601 width=87 style='width:65pt'>"
									+ dataObj[i].消委会投诉
									+ "　</td>\n"
									+ "  <td class=xl6416601 width=115 style='width:86pt'>"
									+ dataObj[i].行政监察件
									+ "　</td>\n"
									+ "  <td class=xl6416601 width=72 style='width:54pt'>　"
									+ dataObj[i].其他
									+ "</td>\n"
									+ "  <td class=xl6416601 width=72 style='width:54pt'>　"
									+ dataObj[i].总数 + "</td>\n" + " </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("djtj_bytype_down.jsp", "_blank");
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
		title="登记量统计报表(按接受类型分)  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		 <div name='regcode' id="regcode" vtype="comboxfield"  multiple="ture" label="登记部门"
		labelAlign="right" dataurl="return getcode()"labelwidth='100px' width="410" ></div>
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