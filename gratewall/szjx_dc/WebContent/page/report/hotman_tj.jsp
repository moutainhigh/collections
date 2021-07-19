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
		var checktype = aa.data.checktype;
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
				+ checktype
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+ "<div id=\"热点投诉人咨询举报投诉件统计报表_20434\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=936 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:702pt'>\n"
				+ " <col width=72 span=13 style='width:54pt'>\n"
				+ " <tr height=42 style='mso-height-source:userset;height:31.5pt'>\n"
				+ "  <td colspan=13 height=42 class=xl6720434 width=936 style='border-right:1.0pt solid black;\n" + 
				"  height:31.5pt;width:702pt'>热点投诉人咨询举报投诉件统计报表</td>\n"
				+ " </tr>\n"
				+ " <tr height=37 style='height:27.75pt'>\n"
				+ "  <td height=37 class=xl6320434 width=72 style='height:27.75pt;border-top:none;\n" + 
				"  width:54pt'>热点投诉人</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>稽查局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>福田局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>罗湖局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>南山局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>盐田局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>龙岗局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>宝安局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>光明局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>坪山局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>龙华局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>大鹏局</td>\n"
				+ "  <td class=xl6420434 width=72 style='border-top:none;width:54pt'>总数</td>\n"
				+ " </tr>";
		;
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryHotMan.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random()
							+ "&checktype=" + checktype,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr height=20 style='height:15.0pt'>\n"
									+ " <td height=20 class=xl6520434 width=72 style='height:15.0pt;width:54pt'><span\n" + 
						" lang=EN-US>　"
									+ dataObj[i].persname
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>"
									+ dataObj[i].市市场稽查局
									+ "　</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>"
									+ dataObj[i].福田局
									+ "　</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].罗湖局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].南山局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>"
									+ dataObj[i].盐田局
									+ "　</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].龙岗局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].宝安局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].光明局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].坪山局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'><span lang=EN-US>　"
									+ dataObj[i].龙华局
									+ "</span></td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'>"
									+ dataObj[i].大鹏局
									+ "　</td>\n"
									+ " <td class=xl6620434 width=72 style='width:54pt'>　"
									+ dataObj[i].总数 + "</td>\n" + "</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("hotman_tj_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);

	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:1, columnwidth: ['62%','*']}" height="100%"
		title="热点投诉人咨询举报投诉件统计报表  统计条件">

		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"
			style="margin: 100 auto" align="center"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310" align="center"></div>

		<div name="checktype" vtype="checkboxfield" label="信息件类型"
			labelalign="left" width="1000"
			dataurl="[{value: '1',text: '市场监管投诉'},{value: '2',text: '举报'}]"
			class="jazz-field-comp jazz-checkboxfield-comp"
			style="width: 1000px; margin-left: 35px; padding-top: 15px;"></div>
		<!-- 			dataurl="[{value: '1',text: '市场监管投诉'},{value: '2',text: '举报'},{value: '3',text: '咨询'},{value: '4',text: '建议'},{value: '6',text: '消委会投诉'},{value: '8',text: '行政监察件'},{value: '9',text: '其他'}]"
 -->
		<div style="height: 100px;"></div>
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