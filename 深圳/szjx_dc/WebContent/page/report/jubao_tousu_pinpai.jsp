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
				"<div id=\"举报、投诉热点产品品牌或企业统计表（表5）_9289\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=830 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:625pt'>\n" + 
				" <col width=70 style='mso-width-source:userset;mso-width-alt:2240;width:53pt'>\n" + 
				" <col width=200 span=2 style='mso-width-source:userset;mso-width-alt:6400;\n" + 
				" width:150pt'>\n" + 
				" <col width=90 span=4 style='mso-width-source:userset;mso-width-alt:2880;\n" + 
				" width:68pt'>\n" + 
				" <tr height=26 style='mso-height-source:userset;height:19.5pt'>\n" + 
				"  <td colspan=7 height=26 class=xl689289 width=830 style='border-right:.5pt solid black;\n" + 
				"  height:19.5pt;width:625pt'>举报、投诉热点产品品牌或企业统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=26 style='mso-height-source:userset;height:19.5pt'>\n" + 
				"  <td rowspan=2 height=46 class=xl639289 style='height:34.5pt;border-top:none'>序号</td>\n" + 
				"  <td rowspan=2 class=xl649289 width=200 style='border-top:none;width:150pt'>产品名称</td>\n" + 
				"  <td rowspan=2 class=xl649289 width=200 style='border-top:none;width:150pt'>品牌或生产企业</td>\n" + 
				"  <td colspan=2 class=xl649289 width=180 style='border-left:none;width:136pt'>举报</td>\n" + 
				"  <td colspan=2 class=xl649289 width=180 style='border-left:none;width:136pt'>投诉</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl679289 height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
				"  <td height=20 class=xl649289 width=90 style='height:15.0pt;border-top:none;\n" + 
				"  border-left:none;width:68pt'>举报数</td>\n" + 
				"  <td class=xl649289 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>查实数</td>\n" + 
				"  <td class=xl649289 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>投诉数</td>\n" + 
				"  <td class=xl649289 width=90 style='border-top:none;border-left:none;\n" + 
				"  width:68pt'>受理数</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/juBaoTouSuPinPai.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=21 style='height:15.75pt'>\n" +
								" <td height=21 class=xl659289 style='height:15.75pt;border-top:none'><span\n" + 
								" lang=EN-US>"+(i+1)+"</span></td>\n" + 
								" <td class=xl659289 style='border-top:none;border-left:none'>"+dataObj[i].产品名称+"　</td>\n" + 
								" <td class=xl659289 style='border-top:none;border-left:none'>"+dataObj[i].品牌或生产企业+"<span\n" + 
								" style='mso-spacerun:yes'> </span></td>\n" + 
								" <td class=xl669289 style='border-top:none;border-left:none'>"+dataObj[i].举报数+"　</td>\n" + 
								" <td class=xl669289 style='border-top:none;border-left:none'>"+dataObj[i].举报查实数+"　</td>\n" + 
								" <td class=xl669289 style='border-top:none;border-left:none'>"+dataObj[i].投诉数+"　</td>\n" + 
								" <td class=xl669289 style='border-top:none;border-left:none'>"+dataObj[i].投诉受理数+"　</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("jubao_tousu_pinpai_down.jsp", "_blank");
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
		title="举报、投诉热点产品品牌或企业统计表  统计条件">
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