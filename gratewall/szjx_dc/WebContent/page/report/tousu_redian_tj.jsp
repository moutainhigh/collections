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
		var regioncheck = aa.data.regioncheck;
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
				+ "<div id=\"各辖区投诉举报热点排名一览表_24798\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=947 style='border-collapse:\n" + 
			" collapse;table-layout:fixed;width:711pt'>\n"
				+ " <col width=422 style='width:317pt'>\n"
				+ " <col width=273 style='mso-width-source:userset;mso-width-alt:8736;width:205pt'>\n"
				+ " <col width=252 style='mso-width-source:userset;mso-width-alt:8064;width:189pt'>\n"
				+ " <tr height=31 style='height:23.25pt'>\n"
				+ "  <td colspan=3 height=31 class=xl6524798 width=947 style='border-right:.5pt solid black;\n" + 
			"  height:23.25pt;width:711pt'>各辖区投诉/举报热点排名一览表</td>\n"
				+ " </tr>\n"
				+ " <tr height=19 style='height:14.25pt'>\n"
				+ "  <td height=19 class=xl6324798 width=422 style='height:14.25pt;border-top:none;\n" + 
			"  width:317pt'>承办区局</td>\n"
				+ "  <td class=xl6324798 width=273 style='border-top:none;border-left:none;\n" + 
			"  width:205pt'>投诉类别热点（前三名）</td>\n"
				+ "  <td class=xl6324798 width=252 style='border-top:none;border-left:none;\n" + 
			"  width:189pt'>投诉行业热点（前三名）</td>\n"
				+ " </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/touSuReDianTj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random()
							+ "&regioncheck=" + regioncheck,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0, b = dataObj.length; i < b; i++) {
							if (dataObj[i].length == 1) {
								htmls += "<tr height=19 style='height:14.25pt'>\n"
										+ " <td rowspan="+dataObj[i].length+" height=19 class=xl6324798 width=422 style='height:14.25pt;\n" + 
						" border-top:none;width:317pt'>"
										+ dataObj[i][0].承办区局
										+ "</td>\n"
										+ " <td class=xl6424798 width=273 style='border-top:none;border-left:none;\n" + 
						" width:205pt'>1."
										+ dataObj[i][0].投诉类别热点
										+ "（"
										+ dataObj[i][0].数量
										+ "）</td>\n"
										+ " <td class=xl6424798 width=252 style='border-top:none;border-left:none;\n" + 
						" width:189pt'>1."
										+ dataObj[i][0].投诉行业热点
										+ "（"
										+ dataObj[i][0].数量1
										+ "）</td>\n"
										+ "</tr>";
								continue;
							} else if(dataObj[i].length == 0){
								continue;
							}else{
								htmls += "<tr height=19 style='height:14.25pt'>\n"
										+ " <td rowspan="+dataObj[i].length+" height=19 class=xl6324798 width=422 style='height:14.25pt;\n" + 
						" border-top:none;width:317pt'>"
										+ dataObj[i][0].承办区局
										+ "</td>\n"
										+ " <td class=xl6424798 width=273 style='border-top:none;border-left:none;\n" + 
						" width:205pt'>1."
										+ dataObj[i][0].投诉类别热点
										+ "（"
										+ dataObj[i][0].数量
										+ "）</td>\n"
										+ " <td class=xl6424798 width=252 style='border-top:none;border-left:none;\n" + 
						" width:189pt'>1."
										+ dataObj[i][0].投诉行业热点
										+ "（"
										+ dataObj[i][0].数量1
										+ "）</td>\n"
										+ "</tr>";
								for (var j = 1, jj = dataObj[i].length; j < jj; j++) {
									htmls += "<tr height=19 style='height:14.25pt'>\n"
											+ "  <td height=19 class=xl6424798 width=273 style='height:14.25pt;border-top:\n" + 
							"  none;border-left:none;width:205pt'>"
											+ (j + 1)
											+ "."
											+ dataObj[i][j].投诉类别热点
											+ "（"
											+ dataObj[i][j].数量
											+ "）</td>\n"
											+ "  <td class=xl6424798 width=252 style='border-top:none;border-left:none;\n" + 
							"  width:189pt'>"
											+ (j + 1)
											+ "."
											+ dataObj[i][j].投诉行业热点
											+ "（"
											+ dataObj[i][j].数量1
											+ "）</td>\n"
											+ " </tr>";
								}
							}
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("tousu_redian_tj_down.jsp", "_blank");
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
		title="各辖区投诉举报热点排名一览表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div name="regioncheck" vtype="checkboxfield" label="统计范围"
			labelalign="center" width="500"
			dataurl="[{value: '1',text: '区/局'},{value: '2',text: '科/所'}]"
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