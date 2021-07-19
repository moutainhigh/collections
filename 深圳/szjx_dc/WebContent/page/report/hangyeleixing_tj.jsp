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
		var tbegintime = aa.data.tbegintime;
		var tendtime = aa.data.tendtime;
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
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
		var htmls = '<div id=\'divcss1\'>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ','
				+ '\''
				+ tbegintime
				+ '\''
				+ ','
				+ '\''
				+ tendtime
				+ '\''
				+ ','
				+ '\''
				+ hbegintime
				+ '\''
				+ ','
				+ '\''
				+ hendtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'+
				'<div id=\'divcss\'>'
				+ '<a href="#" id=\'pluginurl1\' onclick="downReport1(' + '\''
				+ begintime
				+ '\''
				+ ','
				+ '\''
				+ endtime
				+ '\''
				+ ','
				+ '\''
				+ tbegintime
				+ '\''
				+ ','
				+ '\''
				+ tendtime
				+ '\''
				+ ','
				+ '\''
				+ hbegintime
				+ '\''
				+ ','
				+ '\''
				+ hendtime
				+ '\''
				+ ')" > 下 载 详 情</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+
				"<div id=\"行业类型统计报表_5204\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1104 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:830pt'>\n" + 
				" <col width=262 style='mso-width-source:userset;mso-width-alt:8384;width:197pt'>\n" + 
				" <col width=114 style='mso-width-source:userset;mso-width-alt:3648;width:86pt'>\n" + 
				" <col width=140 style='mso-width-source:userset;mso-width-alt:4480;width:105pt'>\n" + 
				" <col width=142 style='mso-width-source:userset;mso-width-alt:4544;width:107pt'>\n" + 
				" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
				" <col width=116 style='mso-width-source:userset;mso-width-alt:3712;width:87pt'>\n" + 
				" <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>\n" + 
				" <col width=128 style='mso-width-source:userset;mso-width-alt:4096;width:96pt'>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=8 rowspan=3 height=50 class=xl675204 width=1104 style='border-right:\n" + 
				"  .5pt solid black;border-bottom:.5pt solid black;height:37.5pt;width:830pt'>价格投诉举报行业统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='mso-height-source:userset;height:14.25pt'>\n" + 
				" </tr>\n" + 
				" <tr height=12 style='mso-height-source:userset;height:9.0pt'>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl645204 dir=LTR width=262 style='height:14.25pt;\n" + 
				"  border-top:none;width:197pt'>涉及客体类型</td>\n" + 
				"  <td class=xl645204 dir=LTR width=114 style='border-top:none;border-left:none;\n" + 
				"  width:86pt'>数量</td>\n" + 
				"  <td class=xl645204 dir=LTR width=140 style='border-top:none;border-left:none;\n" + 
				"  width:105pt'>占上级总量的百分比</td>\n" + 
				"  <td class=xl645204 dir=LTR width=142 style='border-top:none;border-left:none;\n" + 
				"  width:107pt'>占物价投诉举报总量百分比</td>\n" + 
				"  <td class=xl645204 dir=LTR width=117 style='border-top:none;border-left:none;\n" + 
				"  width:88pt'>去年同期登记量</td>\n" + 
				"  <td class=xl645204 dir=LTR width=116 style='border-top:none;border-left:none;\n" + 
				"  width:87pt'>比去年同期增减</td>\n" + 
				"  <td class=xl645204 dir=LTR width=85 style='border-top:none;border-left:none;\n" + 
				"  width:64pt'>上期登记量</td>\n" + 
				"  <td class=xl645204 dir=LTR width=128 style='border-top:none;border-left:none;\n" + 
				"  width:96pt'>比上一时间段增减</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/hangYeLeiXingTj.do",
					data : "begintime=" + begintime + "&endtime=" + endtime +"&tbegintime="+tbegintime+"&tendtime="+tendtime+"&hbegintime="+hbegintime+"&hendtime="+hendtime
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						var shuLiang=0;
						var tShuLiang=0;
						var hShuLiang=0;
						var tZengJian=0;
						var hZengJian=0;
						var s=100;
						var z=100;
						for (var i = 0; i < dataObj.length; i++) {
							shuLiang+=dataObj[i].数量;
							tShuLiang+=dataObj[i].去年同期登记量;
							hShuLiang+=dataObj[i].上期登记量;
							//s+=dataObj[i].占上级总量的百分比;
							//z+=dataObj[i].占物价举报总量百分比;
						}
						//s=s/2;
						
						if (tShuLiang==0) {
							tZengJian=0.00;
						}else{
							tZengJian=(shuLiang-tShuLiang)/tShuLiang;
						}
						if (hShuLiang==0) {
							hZengJian=0.00;
						}else{
							hZengJian=(shuLiang-hShuLiang)/hShuLiang;
						}
						htmls+= "<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl665204 dir=LTR width=262 style='height:14.25pt;\n" + 
							"  border-top:none;width:197pt'>价格检查</td>\n" + 
							"  <td class=xl655204 dir=LTR width=114 style='border-top:none;border-left:none;\n" + 
							"  width:86pt'>"+(shuLiang/2)+"</td>\n" + 
							"  <td class=xl645204 dir=LTR width=140 style='border-top:none;border-left:none;\n" + 
							"  width:105pt'>"+s.toFixed(2)+"%</td>\n" + 
							"  <td class=xl645204 dir=LTR width=142 style='border-top:none;border-left:none;\n" + 
							"  width:107pt'>"+z.toFixed(2)+"%</td>\n" +
							"  <td class=xl655204 dir=LTR width=117 style='border-top:none;border-left:none;\n" + 
							"  width:88pt'>"+(tShuLiang/2)+"</td>\n" + 
							"  <td class=xl645204 dir=LTR width=116 style='border-top:none;border-left:none;\n" + 
							"  width:87pt'>"+(tZengJian*100).toFixed(2)+"%</td>\n"+
							"  <td class=xl655204 dir=LTR width=85 style='border-top:none;border-left:none;\n" + 
							"  width:64pt'>"+(hShuLiang/2)+"</td>\n" +
							"  <td class=xl655204 dir=LTR width=128 style='border-top:none;border-left:none;\n" ;
							htmls+=
							"  width:96pt'>"+(hZengJian*100).toFixed(2)+"%</td>\n" + 
							" </tr>";
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl665204 dir=LTR width=262 style='height:14.25pt;\n" + 
								"  border-top:none;width:197pt'>"+dataObj[i].涉及客体类型+"</td>\n" + 
								"  <td class=xl655204 dir=LTR width=114 style='border-top:none;border-left:none;\n" + 
								"  width:86pt'>"+(dataObj[i].涉及客体类型=="　　其它"?((dataObj[i].数量)/2):dataObj[i].数量)+"</td>\n" + 
								"  <td class=xl645204 dir=LTR width=140 style='border-top:none;border-left:none;\n" + 
								"  width:105pt'>"+(dataObj[i].涉及客体类型=="　　其它"?((dataObj[i].占上级总量的百分比)/2).toFixed(2):dataObj[i].占上级总量的百分比.toFixed(2))+"%</td>\n" + 
								"  <td class=xl645204 dir=LTR width=142 style='border-top:none;border-left:none;\n" + 
								"  width:107pt'>"+(dataObj[i].涉及客体类型=="　　其它"?((dataObj[i].占物价举报总量百分比)/2).toFixed(2):dataObj[i].占物价举报总量百分比.toFixed(2))+"%</td>\n" + 
								"  <td class=xl655204 dir=LTR width=117 style='border-top:none;border-left:none;\n" + 
								"  width:88pt'>"+(dataObj[i].涉及客体类型=="　　其它"?(dataObj[i].去年同期登记量)/2:dataObj[i].去年同期登记量)+"</td>\n" + 
								"  <td class=xl645204 dir=LTR width=116 style='border-top:none;border-left:none;\n" + 
								"  width:87pt'>"+dataObj[i].比去年同期增减.toFixed(2)+"%</td>\n" + 
								"  <td class=xl655204 dir=LTR width=85 style='border-top:none;border-left:none;\n" + 
								"  width:64pt'>"+(dataObj[i].涉及客体类型=="　　其它"?(dataObj[i].上期登记量)/2:dataObj[i].上期登记量)+"</td>\n" + 
								"  <td class=xl655204 dir=LTR width=128 style='border-top:none;border-left:none;\n" + 
								"  width:96pt'>"+dataObj[i].比上一时间段增减.toFixed(2)+"%</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("hangyeleixing_tj_down.jsp", "_blank");
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
		title="价格投诉举报行业统计表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			
			<div id="tbegintime" name='tbegintime' valuetip='默认为上年同期...' name='tbegintime' vtype="datefield"
			label="同比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="tendtime" name='tendtime' valuetip='默认为上年同期...' vtype="datefield" label="同比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			
			<div id="hbegintime" name='hbegintime' valuetip='默认为上月同期...' name='hbegintime' vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="hendtime" name='hendtime' valuetip='默认为上月同期...' vtype="datefield" label="环比截止日期"
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
