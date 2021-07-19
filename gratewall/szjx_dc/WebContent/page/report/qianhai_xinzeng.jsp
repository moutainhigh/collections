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
				"<div id=\"表5 前海蛇口新增数量统计_12531\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1097 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:824pt'>\n" + 
				" <col width=126 style='mso-width-source:userset;mso-width-alt:4032;width:95pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=194 style='mso-width-source:userset;mso-width-alt:6208;width:146pt'>\n" + 
				" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=147 style='mso-width-source:userset;mso-width-alt:4704;width:110pt'>\n" + 
				" <col width=72 span=2 style='width:54pt'>\n" + 
				" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
				" <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>\n" + 
				" <tr height=37 style='mso-height-source:userset;height:27.75pt'>\n" + 
				"  <td colspan=10 height=37 class=xl6412531 width=1097 style='border-right:.5pt solid black;\n" + 
				"  height:27.75pt;width:824pt'>前海蛇口新增数量统计</td>\n" + 
				" </tr>\n" + 
				" <tr height=27 style='mso-height-source:userset;height:20.25pt'>\n" + 
				"  <td height=27 class=xl6312531 style='height:20.25pt;border-top:none'>商事主体户数</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>同比</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>商事主体注册资本(万元)</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>企业户数</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>同比</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>企业注册资本(万元)</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>个体户数</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>同比</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>个体注册资本(万元)</td>\n" + 
				"  <td class=xl6312531 style='border-top:none;border-left:none'>港澳台户数</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/qianHaiXinZeng.do",
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
								"  <td height=19 class=xl6712531 style='height:14.25pt;border-top:none'>"+dataObj[i].商事主体户数+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].商事主体同比+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].商事主体注册资本+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].企业户数+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].企业同比+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].企业注册资本+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].个体户数+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].个体同比+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].个体注册资本+"</td>\n" + 
								"  <td class=xl6712531 style='border-top:none;border-left:none'>"+dataObj[i].港澳台户数+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("qianhai_xinzeng_down.jsp", "_blank");
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
		title="前海蛇口新增数量统计  统计条件">
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