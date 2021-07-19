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
		var hendtime = aa.data.hendtime;
		var hbegintime = aa.data.hbegintime;
		var checktype=aa.data.checktype;
		var regioncheck=aa.data.regioncheck;
		var htmls;
		var types;
		if (checktype==1){
			types="投诉";
		}else  if (checktype==2){
			types="举报";
		}else{
			types="举报/投诉";
		}
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
		if (regioncheck==2) {
				htmls= '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ','
				+ '\''
				+ hendtime
				+ '\''
				+ ','
				+ '\''
				+ hbegintime
				+ '\''
				+ ','
				+ '\''
				+ checktype
				+ '\''
				+ ','
				+ '\''
				+ regioncheck
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+
				"<div id=\"投诉举报各辖区异动提示表_31012\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1002 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:751pt'>\n" + 
				" <col width=279 style='mso-width-source:userset;mso-width-alt:8928;width:209pt'>\n" + 
				" <col width=328 style='mso-width-source:userset;mso-width-alt:10496;width:246pt'>\n" + 
				" <col width=81 style='mso-width-source:userset;mso-width-alt:2592;width:61pt'>\n" + 
				" <col width=79 style='mso-width-source:userset;mso-width-alt:2528;width:59pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=83 style='mso-width-source:userset;mso-width-alt:2656;width:62pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=39 style='mso-height-source:userset;height:29.25pt'>\n" + 
				"  <td colspan=7 height=39 class=xl6331012 width=1002 style='border-right:1.0pt solid black;\n" + 
				"  height:29.25pt;width:751pt'>投诉/举报各辖区异动提示表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=39 class=xl6931012 style='height:29.25pt'>承办区局</td>\n" + 
				"  <td rowspan=2 class=xl6631012 width=328 style='width:246pt'>承办科所</td>\n" + 
				"  <td rowspan=2 class=xl6631012 width=81 style='width:61pt'>承办量</td>\n" + 
				"  <td colspan=2 class=xl6631012 width=159 style='border-left:none;width:119pt'>同比变化</td>\n" + 
				"  <td colspan=2 class=xl6631012 width=155 style='border-left:none;width:116pt'>环比变化</td>\n" + 
				" </tr>\n" + 
				" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
				"  <td height=20 class=xl6631012 width=79 style='height:15.0pt;border-top:none;\n" + 
				"  border-left:none;width:59pt'>数量增减</td>\n" + 
				"  <td class=xl6631012 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>比例增减</td>\n" + 
				"  <td class=xl6631012 width=83 style='border-top:none;border-left:none;\n" + 
				"  width:62pt'>数量增减</td>\n" + 
				"  <td class=xl6631012 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>比例增减</td>\n" + 
				" </tr>";


			$.ajax({
				type : "post",
				url : rootPath + "/quert12315Controller/touSuYiDong.do",
				data : "begintime=" + begintime + "&endtime=" + endtime
						+ "&timess=" + new Date() + "&id=" + Math.random()
						+"&hendtime="+hendtime+"&hbegintime="+hbegintime+"&checktype="+checktype+"&regioncheck="+regioncheck,
				dataType : "text",
				async : false,
				cach : false,
				success : function(data) {
					var dataObj = eval("(" + data + ")");
					for (var i = 0,b=dataObj.length; i < b; i++) {
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							" <td height=19 class=xl6831012 style='height:14.25pt;border-top:none'>"+dataObj[i].上级单位+"</td>\n" + 
							" <td class=xl6731012 width=328 style='border-top:none;border-left:none;\n" + 
							" width:246pt'>"+dataObj[i].承办单位+"</td>\n" + 
							" <td class=xl6731012 width=81 style='border-top:none;border-left:none;\n" + 
							" width:61pt'>　"+dataObj[i].承办量+"</td>\n" + 
							" <td class=xl6731012 width=79 style='border-top:none;border-left:none;\n" + 
							" width:59pt'>"+dataObj[i].同比数量增减+"　</td>\n" + 
							" <td class=xl6731012 width=80 style='border-top:none;border-left:none;\n" + 
							" width:60pt'>　"+dataObj[i].同比比例增减+"</td>\n" + 
							" <td class=xl6731012 width=83 style='border-top:none;border-left:none;\n" + 
							" width:62pt'>　"+dataObj[i].环比数量增减+"</td>\n" + 
							" <td class=xl6731012 width=72 style='border-top:none;border-left:none;\n" + 
							" width:54pt'>　"+dataObj[i].环比比例增减+"</td>\n" + 
							"</tr>";
					}
					htmls += '</table>' + '</div>';
				}
			});
			var newWim = open("tousu_yidong_tj_down_kesuo.jsp", "_blank");
			window.setTimeout(function() {
				newWim.document.body.innerHTML = htmls;
			}, 200);
			}
		else{
			htmls= '<div>'
			+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
			+ begintime
			+ '\''
			+ ','
			+ '\''
			+ endtime
			+ '\''
			+ ','
			+ '\''
			+ hendtime
			+ '\''
			+ ','
			+ '\''
			+ hbegintime
			+ '\''
			+ ','
			+ '\''
			+ checktype
			+ '\''
			+ ','
			+ '\''
			+ regioncheck
			+ '\''
			+ ')" > 下     载</a>'
			+ '</div>'
			+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
			+
			"<div id=\"投诉举报各辖区异动提示表_31012\" align=center x:publishsource=\"Excel\">\n" +
			"\n" + 
			"<table border=0 cellpadding=0 cellspacing=0 width=773 style='border-collapse:\n" + 
			" collapse;table-layout:fixed;width:580pt'>\n" + 
			" <col width=368 style='mso-width-source:userset;mso-width-alt:11776;width:276pt'>\n" + 
			" <col width=82 style='mso-width-source:userset;mso-width-alt:2624;width:62pt'>\n" + 
			" <col width=81 style='mso-width-source:userset;mso-width-alt:2592;width:61pt'>\n" + 
			" <col width=79 style='mso-width-source:userset;mso-width-alt:2528;width:59pt'>\n" + 
			" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
			" <col width=83 style='mso-width-source:userset;mso-width-alt:2656;width:62pt'>\n" + 
			" <tr height=39 style='mso-height-source:userset;height:29.25pt'>\n" + 
			"  <td colspan=6 height=39 class=xl6631012 width=773 style='border-right:1.0pt solid black;\n" + 
			"  height:29.25pt;width:580pt'>"+types+"各辖区异动提示表</td>\n" + 
			" </tr>\n" + 
			" <tr height=19 style='height:14.25pt'>\n" + 
			"  <td rowspan=2 height=39 class=xl6531012 width=368 style='height:29.25pt;\n" + 
			"  width:276pt'>承办区局</td>\n" + 
			"  <td rowspan=2 class=xl6531012 width=82 style='width:62pt'>承办量</td>\n" + 
			"  <td colspan=2 class=xl6531012 width=160 style='border-left:none;width:120pt'>同比变化</td>\n" + 
			"  <td colspan=2 class=xl6531012 width=163 style='border-left:none;width:122pt'>环比变化</td>\n" + 
			" </tr>\n" + 
			" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
			"  <td height=20 class=xl6331012 width=81 style='height:15.0pt;border-top:none;\n" + 
			"  border-left:none;width:61pt'>数量增减</td>\n" + 
			"  <td class=xl6331012 width=79 style='border-top:none;border-left:none;\n" + 
			"  width:59pt'>比例增减</td>\n" + 
			"  <td class=xl6331012 width=80 style='border-top:none;border-left:none;\n" + 
			"  width:60pt'>数量增减</td>\n" + 
			"  <td class=xl6331012 width=83 style='border-top:none;border-left:none;\n" + 
			"  width:62pt'>比例增减</td>\n" + 
			" </tr>";
		$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/touSuYiDong.do",
			data : "begintime=" + begintime + "&endtime=" + endtime
					+ "&timess=" + new Date() + "&id=" + Math.random()
					+"&hendtime="+hendtime+"&hbegintime="+hbegintime+"&checktype="+checktype+"&regioncheck="+regioncheck,
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0,b=dataObj.length; i < b; i++) {
					htmls+=

						"<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6431012 width=368 style='height:14.25pt;border-top:\n" + 
						"  none;width:276pt'>"+dataObj[i].承办单位+"</td>\n" + 
						"  <td class=xl6431012 width=82 style='border-top:none;border-left:none;\n" + 
						"  width:62pt'><span lang=EN-US>"+dataObj[i].承办量+"　</span></td>\n" + 
						"  <td class=xl6431012 width=81 style='border-top:none;border-left:none;\n" + 
						"  width:61pt'><span lang=EN-US>"+dataObj[i].同比数量增减+"　</span></td>\n" + 
						"  <td class=xl6431012 width=79 style='border-top:none;border-left:none;\n" + 
						"  width:59pt'><span lang=EN-US>"+dataObj[i].同比比例增减+"　</span></td>\n" + 
						"  <td class=xl6431012 width=80 style='border-top:none;border-left:none;\n" + 
						"  width:60pt'><span lang=EN-US>"+dataObj[i].环比数量增减+"　</span></td>\n" + 
						"  <td class=xl6431012 width=83 style='border-top:none;border-left:none;\n" + 
						"  width:62pt'><span lang=EN-US>"+dataObj[i].环比比例增减+"　</span></td>\n" + 
						" </tr>";
				}
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("tousu_yidong_tj_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);
		}
	}
			</script>
	</head>
	<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="投诉举报各辖区异动提示表 统计条件">
		<div id="begintime" name='begintime'  vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hbegintime" name='hbegintime'  vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hendtime" name='hendtime' vtype="datefield" label="环比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div name="checktype" vtype="checkboxfield" label="信息件类型"
			labelalign="left" width="500"
			dataurl="[{value: '1',text: '投诉'},{value: '2',text: '举报'}]"
			class="jazz-field-comp jazz-checkboxfield-comp"
			style="width: 1000px; margin-left: 35px; padding-top: 15px;"></div>
		
		<div name="regioncheck" vtype="checkboxfield" label="统计范围"
			labelalign="center" width="500"
			dataurl="[{value: '1',text: '区/局'},{value: '2',text: '科/所'}]"
			class="jazz-field-comp jazz-checkboxfield-comp"
			style="width: 1000px; margin-left: 35px; padding-top: 15px;"></div>
		
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