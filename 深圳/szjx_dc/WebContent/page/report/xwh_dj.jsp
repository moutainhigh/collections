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

				"<div id=\"消委会登记情况表_14598\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=1061 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:796pt'>\n"
				+ " <col width=163 style='mso-width-source:userset;mso-width-alt:5216;width:122pt'>\n"
				+ " <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>\n"
				+ " <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n"
				+ " <col width=72 span=10 style='width:54pt'>\n"
				+ " <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n"
				+ "  <td colspan=13 height=40 class=xl7014598 width=1061 style='border-right:1.0pt solid black;\n" + 
				"  height:30.0pt;width:796pt'>消委会登记情况表</td>\n"
				+ " </tr>\n"
				+ " <tr height=29 style='mso-height-source:userset;height:21.75pt'>\n"
				+ "  <td rowspan=2 height=64 class=xl6514598 width=163 style='border-bottom:1.0pt solid black;\n" + 
				"  height:48.0pt;border-top:none;width:122pt'>登记部门</td>\n"
				+ "  <td colspan=11 class=xl6614598 width=826 style='border-right:1.0pt solid black;\n" + 
				"  border-left:none;width:620pt'>来<span style='mso-spacerun:yes'>  </span>源<span\n" + 
				"  style='mso-spacerun:yes'>  </span>方<span style='mso-spacerun:yes'>  </span>式</td>\n"
				+ "  <td rowspan=2 class=xl6514598 width=72 style='border-bottom:1.0pt solid black;\n" + 
				"  border-top:none;width:54pt'>其他</td>\n"
				+ " </tr>\n"
				+ " <tr height=35 style='height:26.25pt'>\n"
				+ "  <td height=35 class=xl6314598 width=98 style='height:26.25pt;width:74pt'>直接到咨询举报申诉中心</td>\n"
				+ "  <td class=xl6314598 width=80 style='width:60pt'>维权联络点</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>消协</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>协作单位</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>销售企业</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>服务企业</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>生产企业</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>工商部门</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>人大</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>政协</td>\n"
				+ "  <td class=xl6314598 width=72 style='width:54pt'>其他行政部门</td>\n"
				+ " </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryXwh.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr height=20 style='height:15.0pt'>\n"
									+ " <td height=20 class=xl6414598 width=163 style='height:15.0pt;width:122pt'>"
									+ dataObj[i].登记部门
									+ "　</td>\n"
									+ " <td class=xl6314598 width=98 style='width:74pt'>"
									+ dataObj[i].直接到咨询举报申诉中心
									+ "　</td>\n"
									+ " <td class=xl6314598 width=80 style='width:60pt'>"
									+ dataObj[i].维权联络点
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].消协
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].协作单位
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].销售企业
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].服务企业
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].生产企业
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].工商部门
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].人大
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].政协
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].其他行政部门
									+ "　</td>\n"
									+ " <td class=xl6314598 width=72 style='width:54pt'>"
									+ dataObj[i].其他 + "　</td>\n" + "</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("xwh_dj_down.jsp", "_blank");
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
		title="消委会登记情况表  统计条件">
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
