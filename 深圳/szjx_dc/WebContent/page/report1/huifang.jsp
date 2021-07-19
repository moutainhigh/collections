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
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var regcode = aa.data.regcode;
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
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'+
				"<div id=\"回访统计表_4496\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=846 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:635pt'>\n" + 
				" <col width=141 span=2 style='mso-width-source:userset;mso-width-alt:4512;\n" + 
				" width:106pt'>\n" + 
				" <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>\n" + 
				" <col width=180 style='mso-width-source:userset;mso-width-alt:5760;width:135pt'>\n" + 
				" <col width=72 span=4 style='width:54pt'>\n" + 
				" <tr height=31 style='height:23.25pt'>\n" + 
				"  <td colspan=8 height=31 class=xl644496 width=846 style='height:23.25pt;\n" + 
				"  width:635pt'>回访统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl654496 style='height:14.25pt;border-top:none'>处室</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>处理部门</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>回访件数</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>占信息类型总量（%）</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>属实</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>不属实</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>无法联系</td>\n" + 
				"  <td class=xl654496 style='border-top:none;border-left:none'>其他</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/huiFang.do",
					data : "begintime=" + begintime + "&endtime=" + endtime 
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=

								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl664496 width=141 style='height:14.25pt;border-top:none;\n" + 
								"  width:106pt'>"+dataObj[i].处局+"</td>\n" + 
								"  <td class=xl664496 width=141 style='border-top:none;border-left:none;\n" + 
								"  width:106pt'>"+dataObj[i].处理单位+"</td>\n" + 
								"  <td class=xl674496 style='border-top:none;border-left:none'>"+dataObj[i].回访数+"</td>\n" + 
								"  <td class=xl684496 style='border-top:none;border-left:none'>"+dataObj[i].占比+"</td>\n" + 
								"  <td class=xl674496 style='border-top:none;border-left:none'>"+dataObj[i].属实+"</td>\n" + 
								"  <td class=xl674496 style='border-top:none;border-left:none'>"+dataObj[i].不属实+"</td>\n" + 
								"  <td class=xl674496 style='border-top:none;border-left:none'>"+dataObj[i].无法联系+"</td>\n" + 
								"  <td class=xl674496 style='border-top:none;border-left:none'>"+dataObj[i].其他+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("huifang_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="回访统计表  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
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
