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
		var regcode=aa.data.regcode;
		var infotype = aa.data.infotype;
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
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + begintime + '\'' + ',' 
				+ '\'' + endtime + '\'' + ',' 
				+ '\'' + infotype + '\'' + ',' 
				+ '\'' + regcode + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' ;
		$.ajax({
			type : "post",
			url : rootPath + "/queryJingJiSunShi/dengJiRen.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"infotype" : infotype,
				"regcode" :regcode
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("无数据");
					return;
				}
				var dataObj = eval("(" + data + ")");
				var order=dataObj.order;
				var result=dataObj.result;
				htmls+=
					"<div id=\"登记部门_14426\" align=center x:publishsource=\"Excel\">\n" +
					"\n" + 
					"<table border=0 cellpadding=0 cellspacing=0 width=612 style='border-collapse:\n" + 
					" collapse;table-layout:automatic;width:459pt'>\n" + 
					" <col width=324 style='mso-width-source:userset;mso-width-alt:10368;width:243pt'>\n" + 
					" <col width=144 span=2 style='mso-width-source:userset;mso-width-alt:4608;\n" + 
					" width:108pt'>\n" + 
					" <tr height=42 style='mso-height-source:userset;height:31.5pt'>\n" + 
					"  <td colspan="+(order.length+2)+" height=42 class=xl6614426 width=612 style='height:31.5pt;\n" + 
					"  width:459pt'>登记人挽回经济损失总额年度月份对比统计表</td>\n" + 
					" </tr>\n" + 
					" <tr height=19 style='height:14.25pt'>\n" + 
					"  <td height=19 class=xl6314426 style='height:14.25pt;border-top:none'>登记人</td>";
					for (var aaa = 0; aaa < dataObj.order.length; aaa++) {
						htmls+=
							"<td class=xl6314426 style='border-top:none;border-left:none'>"+order[aaa].a+"</td>";
					}
					htmls+=
						"<td class=xl6314426 style='border-top:none;border-left:none'>总计</td>\n" +
						" </tr>";
				
				for (var i = 0, t = result.length; i < t; i++) {
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6414426 style='height:14.25pt;border-top:none'>"+result[i].regdepname+"</td>";
						for (var bbb = 0; bbb < order.length; bbb++) {
							htmls+=
								"<td class=xl6514426 style='border-top:none;border-left:none'>"+result[i][order[bbb].a]+"</td>";
						}
						
						htmls+=
							"<td class=xl6514426 style='border-top:none;border-left:none'>"+result[i].num+"</td>";
					}
				htmls += '</table>' + '</div>';
				var newWim = open("jingjisunshi_nianyue_dengjiren_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 500); 
			}
		});
		 
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="登记人挽回经济损失总额年度月份对比统计表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='infotype' id="infotype" vtype="comboxfield"
			multiple="false" label="信息件类型" labelAlign="right"
			dataurl="inftype.json"	labelwidth='100px' width="410"></div>
		<div name='regcode' id="regcode" vtype="comboxfield" multiple="false" label="登记部门"
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