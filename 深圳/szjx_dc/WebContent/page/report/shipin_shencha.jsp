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
		//var regs=["食药准入处","罗湖局","福田局","南山局","宝安局","龙岗局","盐田局","光明局","坪山局","龙华局","大鹏局"];
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
				+','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'+
				"<div id=\"食品审查1表_24322\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=888 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:667pt'>\n" + 
				" <col width=122 style='mso-width-source:userset;mso-width-alt:3904;width:92pt'>\n" + 
				" <col width=72 span=3 style='width:54pt'>\n" + 
				" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
				" <col width=118 style='mso-width-source:userset;mso-width-alt:3776;width:89pt'>\n" + 
				" <col width=72 span=4 style='width:54pt'>\n" + 
				" <tr height=31 style='height:23.25pt'>\n" + 
				"  <td colspan=10 height=31 class=xl6324322 width=888 style='border-right:.5pt solid black;\n" + 
				"  height:23.25pt;width:667pt'>食品审查中心审查状况表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=3 height=76 class=xl6624322 width=122 style='height:57.0pt;\n" + 
				"  border-top:none;width:92pt'>办理部门</td>\n" + 
				"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>首次审查工作时效</td>\n" + 
				"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>复验审查工作时效</td>\n" + 
				"  <td rowspan=3 class=xl6624322 width=72 style='border-top:none;width:54pt'>照片上传工作时效</td>\n" + 
				"  <td rowspan=3 class=xl6724322 width=144 style='border-top:none;width:108pt'>整改率（首次审查未通过家数/审查任务总家数）</td>\n" + 
				"  <td rowspan=3 class=xl6724322 width=118 style='border-top:none;width:89pt'>通过率（审查通过家数/审查任务总家数）</td>\n" + 
				"  <td colspan=4 class=xl6724322 width=288 style='border-left:none;width:216pt'>主体业态</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=3 height=19 class=xl6724322 width=216 style='height:14.25pt;\n" + 
				"  border-left:none;width:162pt'>餐饮服务经营者</td>\n" + 
				"  <td rowspan=2 class=xl6724322 width=72 style='border-top:none;width:54pt'>食品销售经营者</td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl6724322 width=72 style='height:28.5pt;border-top:none;\n" + 
				"  border-left:none;width:54pt'>大型</td>\n" + 
				"  <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>中型</td>\n" + 
				"  <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>饮品店、糕点店</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/queryFoodstuff/queryShiPinShenCha.do",
					data : "begintime=" + begintime+"&endtime="+endtime
							+ "&timess=" + new Date() ,
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0,ii=dataObj.length; i < ii; i++) {
							htmls+=
								"</tr>\n" +
								"<tr height=19 style='height:14.25pt'>\n" + 
								" <td height=19 class=xl6724322 width=122 style='height:14.25pt;border-top:\n" + 
								" none;width:92pt'>"+dataObj[i].部门+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].首次审查工作时效+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].复验审查工作时效+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].照片上传工作时效+"　</td>\n" + 
								" <td class=xl6724322 width=144 style='border-top:none;border-left:none;\n" + 
								" width:108pt'>"+dataObj[i].整改率+"　</td>\n" + 
								" <td class=xl6724322 width=118 style='border-top:none;border-left:none;\n" + 
								" width:89pt'>"+dataObj[i].通过率+"%</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].大型+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].中型+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].饮品店糕点店+"　</td>\n" + 
								" <td class=xl6724322 width=72 style='border-top:none;border-left:none;\n" + 
								" width:54pt'>"+dataObj[i].食品销售+"　</td>\n" + 
								"</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("shipin_shencha_down.jsp", "_blank");
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
		title="食品审查  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' name='endtime' vtype="datefield"
			label="截止日期" labelAlign="right" labelwidth='100px' width="310"></div>
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