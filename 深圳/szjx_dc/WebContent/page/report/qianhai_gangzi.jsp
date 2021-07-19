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

				"<div id=\"表2前海片区港资背景企业行业分布_25667\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=489 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:367pt'>\n" + 
				" <col width=254 style='mso-width-source:userset;mso-width-alt:8128;width:191pt'>\n" + 
				" <col width=119 style='mso-width-source:userset;mso-width-alt:3808;width:89pt'>\n" + 
				" <col width=116 style='mso-width-source:userset;mso-width-alt:3712;width:87pt'>\n" + 
				" <tr height=24 style='height:18.0pt'>\n" + 
				"  <td colspan=3 height=24 class=xl6825667 dir=LTR width=489 style='border-right:\n" + 
				"  .5pt solid black;height:18.0pt;width:367pt'>表2<span\n" + 
				"  style='mso-spacerun:yes'>  </span>前海片区港资背景企业行业分布</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6425667 style='height:14.25pt;border-top:none'>行业类型</td>\n" + 
				"  <td class=xl6525667 style='border-top:none;border-left:none'>累计企业</td>\n" + 
				"  <td class=xl6525667 style='border-top:none;border-left:none'>新增企业</td>\n" + 
				" </tr>";
				
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/qianHaiGangZi.do",
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
								" <td height=19 class=xl6625667 style='height:14.25pt;border-top:none'>"+dataObj[i].行业类型+"</td>\n" + 
								" <td class=xl6725667 style='border-top:none;border-left:none'>"+dataObj[i].累计企业+"</td>\n" + 
								" <td class=xl6725667 style='border-top:none;border-left:none'>"+dataObj[i].新增企业+"</td>\n" + 
								"</tr>"
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("qianhai_gangzi_down.jsp", "_blank");
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
		title="前海片区港资背景企业行业分布  统计条件">
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