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
				"<div id=\"12365投诉处置工作效率与工作效果分析表（表7）_9364\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1528 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1146pt'>\n" + 
				" <col width=183 style='mso-width-source:userset;mso-width-alt:5856;width:137pt'>\n" + 
				" <col width=72 span=2 style='width:54pt'>\n" + 
				" <col width=100 span=5 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
				" width:75pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=100 span=6 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
				" width:75pt'>\n" + 
				" <tr height=43 style='mso-height-source:userset;height:32.25pt'>\n" + 
				"  <td colspan=15 height=43 class=xl679364 width=1528 style='height:32.25pt;\n" + 
				"  width:1146pt'>12365投诉处置工作效率与工作效果分析表</td>\n" + 
				" </tr>\n" + 
				" <tr height=60 style='mso-height-source:userset;height:45.0pt'>\n" + 
				"  <td height=60 class=xl639364 style='height:45.0pt;border-top:none'>单位名称</td>\n" + 
				"  <td class=xl649364 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>投诉数</td>\n" + 
				"  <td class=xl649364 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>受理数</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>登记到提出拟办意见的平均时长（天）</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>办理每起投诉的平均时长（天）</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>办理每起投诉的平均通话次数（次）</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>办理每起投诉的平均通话时长（秒）</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>提出索赔金额</td>\n" + 
				"  <td class=xl649364 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>实际赔付金额</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>赔付金额占索赔金额比例</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>通话满意率</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>调解成功数</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>调解成功率</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>投诉立案数</td>\n" + 
				"  <td class=xl649364 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>投诉立案率</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/BanLiXiaoLv.do",
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
								"  <td height=19 class=xl669364 style='height:14.25pt;border-top:none'>"+dataObj[i].单位名称+"</td>\n" + 
								"  <td class=xl659364 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj[i].投诉数+"　</td>\n" + 
								"  <td class=xl659364 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj[i].投诉受理数+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].登记到提出拟办意见的平均时长+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].办理每起投诉的平均时长+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].办理每起投诉的平均通话次数+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].办理每起投诉的平均通话时长+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].提出索赔金额+"　</td>\n" + 
								"  <td class=xl659364 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].实际赔付金额+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].赔付金额占索赔金额比例+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].通话满意率+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].调解成功数+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].调解成功率+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].投诉立案数+"　</td>\n" + 
								"  <td class=xl659364 width=100 style='border-top:none;border-left:none;\n" + 
								"  width:75pt'>"+dataObj[i].投诉立案率+"　</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("12365_xiaolv_down.jsp", "_blank");
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
		title="12365投诉处置工作效率与工作效果分析表  统计条件">
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