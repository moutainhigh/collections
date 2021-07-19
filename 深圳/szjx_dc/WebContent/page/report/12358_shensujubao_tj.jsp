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
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+
				"<div id=\"12358申诉举报统计报表_21436\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1928 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1446pt'>\n" + 
				" <col width=296 style='mso-width-source:userset;mso-width-alt:9472;width:222pt'>\n" + 
				" <col width=60 span=26 style='mso-width-source:userset;mso-width-alt:1920;\n" + 
				" width:45pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=31 style='height:23.25pt'>\n" + 
				"  <td colspan=28 height=31 class=xl7021436 width=1928 style='border-right:.5pt solid black;\n" + 
				"  height:23.25pt;width:1446pt'>12358投诉举报统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6421436 dir=LTR width=296 style='height:14.25pt;\n" + 
				"  width:222pt'>承办部门</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>价检分局</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>罗湖</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>福田</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>南山</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>宝安</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>龙岗</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>盐田</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>光明</td>\n" + 
				"  <td colspan=2 class=xl6521436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>坪山</td>\n" + 
				"  <td colspan=2 class=xl6421436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>大鹏</td>\n" + 
				"  <td colspan=2 class=xl6421436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>龙华</td>\n" + 
				"  <td colspan=2 class=xl6421436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>稽查局</td>\n" + 
				"  <td colspan=2 class=xl6421436 dir=LTR width=120 style='border-left:none;\n" + 
				"  width:90pt'>注册局</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=72 style='border-left:none;width:54pt'>合计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6521436 dir=LTR width=296 style='height:14.25pt;\n" + 
				"  border-top:none;width:222pt'>项　　　　目</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>申诉</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
				"  width:45pt'>举报</td>\n" + 
				"  <td class=xl6521436 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>登记量</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/shenSuJuBao_12358.do",
					data : "begintime=" + begintime + "&endtime=" + endtime 
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" +
								"  <td height=20 class=xl6621436 dir=LTR width=296 style='height:15.0pt;\n" + 
								"  border-top:none;width:222pt'>"+dataObj[i].项目+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].价检申诉)/2:dataObj[i].价检申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].价检举报)/2:dataObj[i].价检举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].罗湖申诉)/2:dataObj[i].罗湖申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].罗湖举报)/2:dataObj[i].罗湖举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].福田申诉)/2:dataObj[i].福田申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].福田举报)/2:dataObj[i].福田举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].南山申诉)/2:dataObj[i].南山申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].南山举报)/2:dataObj[i].南山举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].宝安申诉)/2:dataObj[i].宝安申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].宝安举报)/2:dataObj[i].宝安举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].龙岗申诉)/2:dataObj[i].龙岗申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].龙岗举报)/2:dataObj[i].龙岗举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].盐田申诉)/2:dataObj[i].盐田申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].盐田举报)/2:dataObj[i].盐田举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].光明申诉)/2:dataObj[i].光明申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].光明举报)/2:dataObj[i].光明举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].坪山申诉)/2:dataObj[i].坪山申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].坪山举报)/2:dataObj[i].坪山举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].大鹏申诉)/2:dataObj[i].大鹏申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].大鹏举报)/2:dataObj[i].大鹏举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].龙华申诉)/2:dataObj[i].龙华申诉)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=60 style='border-top:none;border-left:none;\n" + 
								"  width:45pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].龙华举报)/2:dataObj[i].龙华举报)+"</td>\n" + 
								"  <td class=xl6821436 style='border-top:none;border-left:none'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].注册局申诉)/2:dataObj[i].注册局申诉)+"</td>\n" + 
								"  <td class=xl6821436 style='border-top:none;border-left:none'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].注册局举报)/2:dataObj[i].注册局举报)+"</td>\n" + 
								"  <td class=xl6821436 style='border-top:none;border-left:none'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].稽查局申诉)/2:dataObj[i].稽查局申诉)+"</td>\n" + 
								"  <td class=xl6821436 style='border-top:none;border-left:none'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].稽查局举报)/2:dataObj[i].稽查局举报)+"</td>\n" + 
								"  <td class=xl6721436 dir=LTR width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+((dataObj[i].项目=="价格检查"||dataObj[i].项目=="　　其它")?(dataObj[i].合计行)/2:dataObj[i].合计行)+"</td>\n" + 
								" </tr>";

						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("12358_shensujubao_tj_down.jsp", "_blank");
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
		title="12358投诉举报统计表 统计条件">
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
