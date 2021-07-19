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
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+ "<div id=\"话务员登记提交量统计报表_15891\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=669 style='border-collapse:\n" + 
			" collapse;table-layout:fixed;width:503pt'>\n"
				+ " <col width=78 style='mso-width-source:userset;mso-width-alt:2496;width:59pt'>\n"
				+ " <col width=72 span=4 style='width:54pt'>\n"
				+ " <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>\n"
				+ " <col width=72 style='width:54pt'>\n"
				+ " <col width=74 style='mso-width-source:userset;mso-width-alt:2368;width:56pt'>\n"
				+ " <col width=72 style='width:54pt'>\n"
				+ " <tr height=25 style='height:18.75pt'>\n"
				+ "  <td colspan=9 height=25 class=xl6515891 width=669 style='border-right:1.0pt solid black;\n" + 
			"  height:18.75pt;width:503pt'>话务员登记提交量统计报表</td>\n"
				+ " </tr>\n"
				+ " <tr height=37 style='height:27.75pt'>\n"
				+ "  <td height=37 class=xl6815891 width=78 style='height:27.75pt;border-top:none;\n" + 
			"  width:59pt'>姓名</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>市场监管投诉</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>举报</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>咨询</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>建议</td>\n"
				+ "  <td class=xl6915891 width=85 style='border-top:none;width:64pt'>消委会投诉</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>其他</td>\n"
				+ "  <td class=xl6915891 width=74 style='border-top:none;width:56pt'>行政监察与效能</td>\n"
				+ "  <td class=xl6915891 width=72 style='border-top:none;width:54pt'>总数</td>\n"
				+ " </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryYuWuTj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random()+"&regcode="+regcode,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=20 style='height:15.0pt'>\n" +
								"  <td height=20 class=xl6315891 width=78 style='height:15.0pt;width:59pt'><span\n" + 
								"  lang=EN-US>"+ dataObj[i].姓名 +"</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].市场监管投诉  + "</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].举报  + "</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].咨询  + "</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].建议  + "</span></td>\n" + 
								"  <td class=xl6415891 width=85 style='width:64pt'><span lang=EN-US>" + dataObj[i].消委会投诉  + "</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].其他  + "</span></td>\n" + 
								"  <td class=xl6415891 width=74 style='width:56pt'><span lang=EN-US>" + dataObj[i].行政监察件  + "</span></td>\n" + 
								"  <td class=xl6415891 width=72 style='width:54pt'><span lang=EN-US>" + dataObj[i].总数  + "</span></td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("huawu_tj_down.jsp", "_blank");
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
		title="话务员登记提交量统计报表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		 <div name='regcode' id="regcode" vtype="comboxfield" multiple="ture" label="登记部门"
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