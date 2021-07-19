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
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
		var regcode = aa.data.regcode;
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
				+ ','
				+ '\''
				+ hbegintime
				+ '\''
				+ ','
				+ '\''
				+ hendtime
				+ '\''
				+ ','
				+ '\''
				+ regcode
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+

				"<div id=\"信息件登记量统计表_2175\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1104 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:829pt'>\n" + 
				" <col width=105 style='mso-width-source:userset;mso-width-alt:3360;width:79pt'>\n" + 
				" <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>\n" + 
				" <col width=72 span=9 style='width:54pt'>\n" + 
				" <col width=94 style='mso-width-source:userset;mso-width-alt:3008;width:71pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
				" <tr height=43 style='mso-height-source:userset;height:32.25pt'>\n" + 
				"  <td colspan=14 height=43 class=xl632175 width=1104 style='height:32.25pt;\n" + 
				"  width:829pt'>信息件登记量统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=38 class=xl642175 style='height:28.5pt;border-top:none'>姓名</td>\n" + 
				"  <td rowspan=2 class=xl642175 style='border-top:none'>直接回复数</td>\n" + 
				"  <td rowspan=2 class=xl642175 style='border-top:none'>预登记数</td>\n" + 
				"  <td colspan=6 class=xl642175 style='border-left:none'>正式登记数</td>\n" + 
				"  <td rowspan=2 class=xl642175 style='border-top:none'>登记量</td>\n" + 
				"  <td rowspan=2 class=xl652175 width=72 style='border-top:none;width:54pt'>上一时段数据</td>\n" + 
				"  <td rowspan=2 class=xl652175 width=94 style='border-top:none;width:71pt'>环比增减</td>\n" + 
				"  <td rowspan=2 class=xl652175 width=72 style='border-top:none;width:54pt'>去年同期数据</td>\n" + 
				"  <td rowspan=2 class=xl652175 width=100 style='border-top:none;width:75pt'>同比增减</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl642175 style='height:14.25pt;border-top:none;\n" + 
				"  border-left:none'>工商</td>\n" + 
				"  <td class=xl642175 style='border-top:none;border-left:none'>价检</td>\n" + 
				"  <td class=xl642175 style='border-top:none;border-left:none'>质监</td>\n" + 
				"  <td class=xl642175 style='border-top:none;border-left:none'>知识产权</td>\n" + 
				"  <td class=xl642175 style='border-top:none;border-left:none'>消委会</td>\n" + 
				"  <td class=xl642175 style='border-top:none;border-left:none'>合计</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/queryXiaoBao/xinXiDengJi.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&hbegintime="+hbegintime+
							"&hendtime="+hendtime+ "&timess=" + new Date() + "&id=" + Math.random()+"&regcode="+regcode,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += 
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl662175 style='height:14.25pt;border-top:none'>"+dataObj[i].姓名+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].直接回复数+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].预登记+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].工商+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].价检+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].质监+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].知识+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].消委会+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].合计+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].登记量+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].上一时段+"</td>\n" + 
								"  <td class=xl682175 style='border-top:none;border-left:none'>"+dataObj[i].环比增减+"</td>\n" + 
								"  <td class=xl672175 style='border-top:none;border-left:none'>"+dataObj[i].去年同期+"</td>\n" + 
								"  <td class=xl682175 style='border-top:none;border-left:none'>"+dataObj[i].同比增减+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("xinxidengji_down.jsp", "_blank");
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
		title="信息件登记量统计表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			<div id="hbegintime" name='hbegintime' valuetip='默认为上月同期...' name='hbegintime' vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hendtime" name='hendtime' valuetip='默认为上月同期...' vtype="datefield" label="环比截止日期"
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