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
				"<div id=\"表3 港资背景企业数量_29149\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=474 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:356pt'>\n" + 
				" <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>\n" + 
				" <col width=130 style='mso-width-source:userset;mso-width-alt:4160;width:98pt'>\n" + 
				" <col width=103 style='mso-width-source:userset;mso-width-alt:3296;width:77pt'>\n" + 
				" <col width=146 style='mso-width-source:userset;mso-width-alt:4672;width:110pt'>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=4 height=19 class=xl6529149 width=474 style='border-right:.5pt solid black;\n" + 
				"  height:14.25pt;width:356pt'>前海片区港资背景企业数量</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6329149 style='height:14.25pt;border-top:none'>项目</td>\n" + 
				"  <td class=xl6329149 style='border-top:none;border-left:none'>本月新增</td>\n" + 
				"  <td class=xl6329149 style='border-top:none;border-left:none'>同比增长</td>\n" + 
				"  <td class=xl6329149 style='border-top:none;border-left:none'>本月累计</td>\n" + 
				" </tr>";

							
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/qianHaiGangZiSl.do",
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
								"  <td height=19 class=xl6329149 style='height:14.25pt;border-top:none'>"+dataObj[i].项目+"</td>\n" + 
								"  <td class=xl6329149 style='border-top:none;border-left:none'>"+dataObj[i].本月新增+"</td>\n" + 
								"  <td class=xl6429149 style='border-top:none;border-left:none'>"+dataObj[i].同比增长+"</td>\n" + 
								"  <td class=xl6329149 style='border-top:none;border-left:none'>"+dataObj[i].本月累计+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("qianhai_gangzi_sl_down.jsp", "_blank");
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
		title="港资背景企业数量  统计条件">
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