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
		var begintime = aa.data.begintime;
			
		if (endtime.length == 0 ||begintime.length==0) {
			alert("日期为空");
			return;
		}
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+','
				+'\''+endtime+'\''
				+ ')" > 下     载</a>'
				+ '</div>'+
				"<div id=\"前海企业住所统计_1142\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=550 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:413pt'>\n" + 
				" <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>\n" + 
				" <col width=123 style='mso-width-source:userset;mso-width-alt:3936;width:92pt'>\n" + 
				" <col width=108 style='mso-width-source:userset;mso-width-alt:3456;width:81pt'>\n" + 
				" <col width=113 style='mso-width-source:userset;mso-width-alt:3616;width:85pt'>\n" + 
				" <col width=93 style='mso-width-source:userset;mso-width-alt:2976;width:70pt'>\n" + 
				" <tr height=34 style='mso-height-source:userset;height:25.5pt'>\n" + 
				"  <td colspan=5 height=34 class=xl631142 width=550 style='border-right:.5pt solid black;\n" + 
				"  height:25.5pt;width:413pt'>前海企业入驻情况及场所注明情况统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl661142 style='height:14.25pt;border-top:none'>新增入驻</td>\n" + 
				"  <td class=xl661142 style='border-top:none;border-left:none'>占比</td>\n" + 
				"  <td class=xl661142 style='border-top:none;border-left:none'>累计入驻</td>\n" + 
				"  <td class=xl661142 style='border-top:none;border-left:none'>累计占比</td>\n" + 
				"  <td class=xl661142 style='border-top:none;border-left:none'>注明场所</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/qianHaiRuZhu.do",
					data : "endtime=" + endtime+"&begintime="+begintime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=

								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl661142 style='height:14.25pt;border-top:none'>"+dataObj[i].新增入驻+"</td>\n" + 
								"  <td class=xl661142 style='border-top:none;border-left:none'>"+dataObj[i].占比+"</td>\n" + 
								"  <td class=xl661142 style='border-top:none;border-left:none'>"+dataObj[i].累计入驻+"</td>\n" + 
								"  <td class=xl661142 style='border-top:none;border-left:none'>"+dataObj[i].累计占比+"</td>\n" + 
								"  <td class=xl661142 style='border-top:none;border-left:none'>"+dataObj[i].注明场所+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("qianhai_ruzhu_down.jsp", "_blank");
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
		title="前海企业住所统计  统计条件">
		<div id="begintime" name='begintime' vtype="datefield" label="开始日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
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