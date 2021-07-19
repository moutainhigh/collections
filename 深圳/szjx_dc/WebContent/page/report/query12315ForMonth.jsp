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
/* 	$(document).ready(function getcode(){
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
	}); */
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
				"<div id=\"消费者投诉处理情况表_20970\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=903 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:678pt'>\n" + 
				" <col width=211 style='mso-width-source:userset;mso-width-alt:6752;width:158pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=100 span=4 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
				" width:75pt'>\n" + 
				" <col width=110 span=2 style='mso-width-source:userset;mso-width-alt:3520;\n" + 
				" width:83pt'>\n" + 
				" <tr height=37 style='mso-height-source:userset;height:27.75pt'>\n" + 
				"  <td colspan=8 height=37 class=xl6920970 width=903 style='height:27.75pt;\n" + 
				"  width:678pt'>　　消费者投诉处理情况表</td>\n" + 
				" </tr>\n" + 
				" <tr height=28 style='mso-height-source:userset;height:21.0pt'>\n" + 
				"  <td rowspan=2 height=48 class=xl7020970 style='height:36.0pt;border-top:none'>消费者投诉处理情况</td>\n" + 
				"  <td rowspan=2 class=xl7120970 style='border-top:none'>单位</td>\n" + 
				"  <td colspan=2 class=xl7220970 width=200 style='border-left:none;width:150pt'>本年情况</td>\n" + 
				"  <td colspan=2 class=xl7220970 width=200 style='border-left:none;width:150pt'>上年情况</td>\n" + 
				"  <td rowspan=2 class=xl6420970 width=110 style='border-top:none;width:83pt'>本年累计比上年同期增减</td>\n" + 
				"  <td rowspan=2 class=xl6420970 width=110 style='border-top:none;width:83pt'>本年累计比上年同期增减%</td>\n" + 
				" </tr>\n" + 
				" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
				"  <td height=20 class=xl6420970 width=100 style='height:15.0pt;border-top:none;\n" + 
				"  border-left:none;width:75pt'>本月</td>\n" + 
				"  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>1-本月累计</td>\n" + 
				"  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>本月</td>\n" + 
				"  <td class=xl6420970 width=100 style='border-top:none;border-left:none;\n" + 
				"  width:75pt'>1-本月累计</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/quert12315ForMonthController/queryDate.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += 
								"<tr height=19 style='height:14.25pt'>\n" +
								" <td height=19 class=xl6520970 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
								" <td class=xl6620970 style='border-top:none;border-left:none'>"+dataObj[i].unit+"</td>\n" + 
								" <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n" + 
								" width:75pt'>"+dataObj[i].now+"</td>\n" + 
								" <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n" + 
								" width:75pt'>"+dataObj[i].nowsum+"</td>\n" + 
								" <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n" + 
								" width:75pt'>"+dataObj[i].upyear+"</td>\n" + 
								" <td class=xl6720970 width=100 style='border-top:none;border-left:none;\n" + 
								" width:75pt'>"+dataObj[i].upyearSum+"</td>\n" + 
								" <td class=xl6720970 width=110 style='border-top:none;border-left:none;\n" + 
								" width:83pt'>"+dataObj[i].zjshu+"</td>\n" + 
								" <td class=xl6820970 width=110 style='border-top:none;border-left:none;\n" + 
								" width:83pt'>"+dataObj[i].zjlv+"</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("query_12315_for_month_down.jsp", "_blank");
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
		title="消费者投诉处理情况表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			<!-- <div id="hbegintime" name='hbegintime' valuetip='默认为上月同期...' name='hbegintime' vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div> -->
		<!-- <div id="hendtime" name='hendtime' valuetip='默认为上月同期...' vtype="datefield" label="环比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div> -->
		<!--  <div name='regcode' id="regcode" vtype="comboxfield"  multiple="ture" label="登记部门"
		labelAlign="right" dataurl="return getcode()"labelwidth='100px' width="410" ></div> -->
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