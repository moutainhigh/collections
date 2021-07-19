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
		var endtime = aa.data.endtime;
			
		if (endtime.length == 0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+

				"<div id=\"表1 累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布_4864\" align=center\n" +
				"x:publishsource=\"Excel\">\n" + 
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=792 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:594pt'>\n" + 
				" <col width=72 span=11 style='width:54pt'>\n" + 
				" <tr height=48 style='mso-height-source:userset;height:36.0pt'>\n" + 
				"  <td colspan=11 height=48 class=xl664864 width=792 style='height:36.0pt;\n" + 
				"  width:594pt'>累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布</td>\n" + 
				" </tr>\n" + 
				" <tr height=22 style='height:16.5pt'>\n" + 
				"  <td height=22 class=xl644864 style='height:16.5pt;border-top:none'>区划</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>南山区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>福田区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>宝安区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>罗湖区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>龙岗区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>龙华区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>盐田区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>光明新区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>坪山新区</td>\n" + 
				"  <td class=xl654864 style='border-top:none;border-left:none'>大鹏新区</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/qianHaiLeiJi.do",
					data : "endtime=" + endtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=22 style='height:16.5pt'>\n" +
								"  <td height=22 class=xl644864 style='height:16.5pt;border-top:none'>"+dataObj[i].区划+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].南山区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].福田区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].宝安区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].罗湖区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].龙岗区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].龙华区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].盐田区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].光明新区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].坪山新区+"</td>\n" + 
								"  <td class=xl654864 style='border-top:none;border-left:none'>"+dataObj[i].大鹏新区+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("qianhai_leiji_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);		
	}
</script>


</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="累计入驻前海商务秘书公司且在营业执照上标注实际经营场所的企业区域分布  统计条件">
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