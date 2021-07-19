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
		var regioncheck=aa.data.regioncheck;
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
				+ regioncheck
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+

				"<div id=\"20　　 年   月消费者投诉举报办结情况一览表_25179\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1296 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:972pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=216 style='mso-width-source:userset;mso-width-alt:6912;width:162pt'>\n" + 
				" <col width=72 span=14 style='width:54pt'>\n" + 
				" <tr height=38 style='mso-height-source:userset;height:28.5pt'>\n" + 
				"  <td colspan=16 height=38 class=xl6725179 width=1296 style='border-right:1.0pt solid black;\n" + 
				"  height:28.5pt;width:972pt'>"+"</span>消费者投诉举报办结情况一览表</td>\n" + 
				" </tr>\n" + 
				" <tr height=20 style='height:15.0pt'>\n" + 
				"  <td rowspan=2 height=57 class=xl7025179 width=72 style='border-bottom:1.0pt solid black;\n" + 
				"  height:42.75pt;border-top:none;width:54pt'>序号</td>\n" + 
				"  <td rowspan=2 class=xl7125179 width=216 style='border-bottom:1.0pt solid black;\n" + 
				"  border-top:none;width:162pt'>承办单位</td>\n" + 
				"  <td colspan=2 class=xl7325179 width=144 style='border-right:1.0pt solid black;\n" + 
				"  border-left:none;width:108pt'>承办情况</td>\n" + 
				"  <td colspan=7 class=xl7325179 width=504 style='border-right:1.0pt solid black;\n" + 
				"  border-left:none;width:378pt'>投诉办结情况</td>\n" + 
				"  <td colspan=5 class=xl7325179 width=360 style='border-right:1.0pt solid black;\n" + 
				"  border-left:none;width:270pt'>举报办结情况</td>\n" + 
				" </tr>\n" + 
				" <tr height=37 style='height:27.75pt'>\n" + 
				"  <td height=37 class=xl6425179 width=72 style='height:27.75pt;width:54pt'>本期</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>同比</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>承办</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>已办结</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>办结率</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>调解成功数</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>调解成功率</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>诉转案数</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>立案率</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>承办</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>已办结</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>办结率</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>立案数</td>\n" + 
				"  <td class=xl6425179 width=72 style='width:54pt'>立案率</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/touSuBanJieTj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&regioncheck="+regioncheck
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=20 style='height:15.0pt'>\n" +
								"  <td height=20 class=xl6325179 width=72 style='height:15.0pt;width:54pt'>"+ (i+1) +"　</td>\n" + 
								"  <td class=xl6425179 width=216 style='width:162pt'>"+ dataObj[i].承办单位 +"　</td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].承办本期 +"　</span></td>\n" + 
								"  <td class=xl6625179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].同比 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉承办 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉已办结 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉办结率+"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉调解成功数 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉调解成功率 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉诉转案件+"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].投诉立案率 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].举报承办 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].举报已办结 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].举报办结率 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].举报立案数 +"　</span></td>\n" + 
								"  <td class=xl6525179 width=72 style='width:54pt'><span lang=EN-US>"+ dataObj[i].举报立案数率+"　</span></td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("tousu_banjie_tj_down.jsp", "_blank");
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
		title="消费者投诉举报办结情况一览表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div name="regioncheck" vtype="radiofield" label="统计范围"
			labelalign="center" width="500"
			dataurl="[{checked:true,value: '1',text: '区/局'},{value: '2',text: '科/所'}]"
			class="jazz-field-comp jazz-checkboxfield-comp"
			style="width: 1000px; margin-left: 35px; padding-top: 15px;"></div>
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