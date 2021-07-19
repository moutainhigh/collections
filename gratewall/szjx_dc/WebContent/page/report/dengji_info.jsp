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
		var strs=new Array();
		strs[0]="宝安";
		strs[1]="南山";
		strs[2]="光明";
		strs[3]="福田";
		strs[4]="罗湖";
		strs[5]="龙岗";
		strs[6]="坪山";
		strs[7]="龙华";
		strs[8]="盐田";
		strs[9]="大鹏";
		strs[10]="前海";
		strs[11]="市民中心";
//		String strs[]={"宝安","南山","光明","福田","罗湖","龙岗","坪山","龙华","盐田","大鹏","前海","市民中心"};
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
				+
				"<div id=\"全系统全流程网上商事登记业务办理情况表_22517\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=717 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:538pt'>\n" + 
				" <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>\n" + 
				" <col width=191 style='mso-width-source:userset;mso-width-alt:6112;width:143pt'>\n" + 
				" <col width=206 style='mso-width-source:userset;mso-width-alt:6592;width:155pt'>\n" + 
				" <col width=132 style='mso-width-source:userset;mso-width-alt:4224;width:99pt'>\n" + 
				" <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=5 rowspan=2 height=54 class=xl7222517 width=717 style='border-right:\n" + 
				"  .5pt solid black;border-bottom:.5pt solid black;height:40.5pt;width:538pt'><span\n" + 
				"  style='mso-spacerun:yes'>  </span>全系统全流程网上商事登记业务办理情况表</td>\n" + 
				" </tr>\n" + 
				" <tr height=35 style='mso-height-source:userset;height:26.25pt'>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl6322517 style='height:28.5pt'>单位</td>\n" + 
				"  <td class=xl6322517 style='border-left:none'>项目</td>\n" + 
				"  <td class=xl6322517 style='border-left:none'>数据</td>\n" + 
				"  <td class=xl6422517 width=132 style='border-top:none;border-left:none;\n" + 
				"  width:99pt'>未办、待签收、签收通过未核准小计</td>\n" + 
				"  <td class=xl6422517 width=97 style='border-top:none;border-left:none;\n" + 
				"  width:73pt'>未办结/已办（%）</td>\n" + 
				" </tr>";

		$
				.ajax({
					type : "post",
					url : rootPath
							+ "/shangShiZhuTi/dengjiBanJieInfo.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < strs.length; i++) {
							htmls += 

								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td rowspan=4 height=76 class=xl6722517 style='border-bottom:.5pt solid black;\n" + 
								"  height:57.0pt;border-top:none'>"+strs[i]+"</td>\n" + 
								"  <td class=xl6522517 style='border-top:none;border-left:none'>总数</td>\n" + 
								"  <td class=xl6522517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["总计"]+"</td>\n" + 
								"  <td rowspan=4 class=xl6722517 style='border-bottom:.5pt solid black;\n" + 
								"  border-top:none'>"+dataObj[strs[i]]["小计"]+"</td>\n" + 
								"  <td rowspan=4 class=xl6922517 style='border-bottom:.5pt solid black;\n" + 
								"  border-top:none'>"+dataObj[strs[i]]["办结率"]+"</td>\n" + 
								" </tr>\n" + 
								" <tr height=19 style='height:14.25pt'>\n" + 
								"  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n" + 
								"  border-left:none'>已办</td>\n" + 
								"  <td class=xl6622517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["已办结"]+"</td>\n" + 
								" </tr>\n" + 
								" <tr height=19 style='height:14.25pt'>\n" + 
								"  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n" + 
								"  border-left:none'>超期</td>\n" + 
								"  <td class=xl6622517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["未办"]+"</td>\n" + 
								" </tr>\n" + 
								" <tr height=19 style='height:14.25pt'>\n" + 
								"  <td height=19 class=xl6522517 style='height:14.25pt;border-top:none;\n" + 
								"  border-left:none'>撤回办结</td>\n" + 
								"  <td class=xl6622517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["撤回办结"]+"</td>\n" + 
								" </tr>";
								
								
							/* 	" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td rowspan=4 height=114 class=xl6522517 style='border-bottom:.5pt solid black;\n" + 
							"  height:85.5pt;border-top:none'>"+strs[i]+"</td>\n" + 
							"  <td class=xl6622517 style='border-top:none;border-left:none'>总数</td>\n" + 
							"  <td class=xl6622517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["总计"]+"</td>\n" + 
							"  <td rowspan=6 class=xl6522517 style='border-bottom:.5pt solid black;\n" + 
							"  border-top:none'>"+dataObj[strs[i]]["小计"]+"</td>\n" + 
							"  <td rowspan=6 class=xl6722517 style='border-bottom:.5pt solid black;\n" + 
							"  border-top:none'>"+dataObj[strs[i]]["办结率"]+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none'>已办</td>\n" + 
							"  <td class=xl6922517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["已办结"]+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none'>未办</td>\n" + 
							"  <td class=xl6922517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["未办"]+"</td>\n" + 
							" </tr>\n" + 
							/* " <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none'>待签收</td>\n" + 
							"  <td class=xl6922517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["待签收"]+"</td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none'>签收通过未核准</td>\n" + 
							"  <td class=xl6922517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["签收通过未核准"]+"</td>\n" + 
							" </tr>\n" +  
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6622517 style='height:14.25pt;border-top:none;\n" + 
							"  border-left:none'>撤回办结</td>\n" + 
							"  <td class=xl6922517 style='border-top:none;border-left:none'>"+dataObj[strs[i]]["撤回办结"]+"</td>\n" + 
							" </tr>\n" + 
							"\n" ; */
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("dengji_info_down.jsp", "_blank");
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
		title="全系统全流程网上商事登记业务办理情况表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
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