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
				+ '</div>'+
				"<div id=\"职业打假人（表8）_14056\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1536 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1160pt'>\n" + 
				" <col width=186 style='mso-width-source:userset;mso-width-alt:5952;width:140pt'>\n" + 
				" <col width=90 span=15 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
				" width:68pt'>\n" + 
				" <tr height=40 style='mso-height-source:userset;height:30.0pt'>\n" + 
				"  <td colspan=16 height=40 class=xl6714056 width=1536 style='border-right:.5pt solid black;\n" + 
				"  height:30.0pt;width:1160pt'>职业打假人</td>\n" + 
				" </tr>\n" + 
				" <tr height=34 style='height:25.5pt'>\n" + 
				"  <td height=34 class=xl6314056 style='height:25.5pt;border-top:none'>单位名称</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>本地登记的职业打假人数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>投诉数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>人均投诉数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>受理数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>调解成功数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>调解成功率</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>提出索赔金额</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>人均提出索赔金额</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>实际赔付金额</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>人均实际赔付金额</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>赔付金额占索赔金额比例</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>举报数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>人均举报数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>查实数</td>\n" + 
				"  <td class=xl6414056 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>举报查实率</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/DaJiaRen.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=

								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6614056 style='height:14.25pt;border-top:none'>"+dataObj[i].单位名称+"</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].本地登记的职业打假人数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].投诉数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].人均投诉数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].投诉受理数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].调解成功数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].调解成功率+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].提出索赔金额+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].人均提出索赔金额+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].实际赔付金额+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].人均实际赔付金额+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].赔付金额占索赔金额比例+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].举报数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].人均举报数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].查实数+"　</td>\n" + 
								"  <td class=xl6514056 width=90 style='border-top:none;border-left:none;\n" + 
								"  width:68pt'>"+dataObj[i].举报查实率+"　</td>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("dajiaren_down.jsp", "_blank");
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
		title="职业打假人  统计条件">
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