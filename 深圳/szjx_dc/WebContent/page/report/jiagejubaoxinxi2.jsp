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
		var tbegintime = aa.data.tbegintime;
		var endtime = aa.data.endtime;
		var tendtime = aa.data.tendtime;
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
		var htmls = '<div id="divcss1">'
				+ '<a href="#" id=\'pluginurl1\' onclick="downReport(' + '\''
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
				+ 1
				+ '\''
				+ ')" > 下载(全部)</a>'+
				'</div>'
				+'<div id="divcss2">'
				+ '<a href="#" id=\'pluginurl2\' onclick="downReport(' + '\''
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
				+ 2
				+ '\''
				+ ')" >下载(根据服务类型)</a>'+
				'</div>'
				+ '<div id="divcss3">'
				+ '<a href="#" id=\'pluginurl3\' onclick="downReport(' + '\''
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
				+ 3
				+ '\''
				+ ')" >下 载(根据违法行为种类)</a>'
				+'</div>'+
				"<div id=\"价格举报信息采集表2_12073\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=836 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:627pt'>\n" + 
				" <col width=228 style='mso-width-source:userset;mso-width-alt:7296;width:171pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=72 style='mso-width-source:userset;mso-width-alt:2304;width:54pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=72 style='mso-width-source:userset;mso-width-alt:2304;width:54pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=30 style='mso-height-source:userset;height:22.5pt'>\n" + 
				"  <td colspan=9 height=30 class=xl6712073 width=836 style='border-right:.5pt solid black;\n" + 
				"  height:22.5pt;width:627pt'>价格投诉举报信息采集表2</td>\n" + 
				" </tr>\n" + 
				" <tr height=20 style='mso-height-source:userset;height:15.0pt'>\n" + 
				"  <td height=20 class=xl6312073 width=228 style='height:15.0pt;border-top:none;\n" + 
				"  width:171pt'>项目</td>\n" + 
				"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>办结数</td>\n" + 
				"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同比</td>\n" + 
				"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>查处数</td>\n" + 
				"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同比</td>\n" + 
				"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>经济制裁</td>\n" + 
				"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同比</td>\n" + 
				"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>退还金额</td>\n" + 
				"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
				"  width:54pt'>同比</td>\n" + 
				" </tr>";
				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/jiaGeJuBao2.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+
							"&tbegintime="+tbegintime+"&tendtime="+tendtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0,ii=dataObj.第一部分.length; i <ii; i++) {
							htmls+=
								"<tr height=19 style='mso-height-source:userset;height:14.25pt'>\n" +
								"  <td height=19 class=xl6412073 width=228 style='height:14.25pt;border-top:\n" + 
								"  none;width:171pt'>"+dataObj.第一部分[i].项目+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第一部分[i].项目=="价格检查"||dataObj.第一部分[i].项目=="　　其它"?(((dataObj.第一部分[i].办结数)/2).toFixed(0)):dataObj.第一部分[i].办结数)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第一部分[i].办结同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第一部分[i].项目=="价格检查"||dataObj.第一部分[i].项目=="　　其它"?((dataObj.第一部分[i].查处数)/2):dataObj.第一部分[i].查处数)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第一部分[i].查处同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第一部分[i].项目=="价格检查"||dataObj.第一部分[i].项目=="　　其它"?((dataObj.第一部分[i].经济制裁)/2):dataObj.第一部分[i].经济制裁)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第一部分[i].制裁同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第一部分[i].项目=="价格检查"||dataObj.第一部分[i].项目=="　　其它"?((dataObj.第一部分[i].退还金额)/2):dataObj.第一部分[i].退还金额)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第一部分[i].退还同比+"</td>\n" + 
								" </tr>";
						}
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl1512073 style='height:14.25pt'></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							"  <td class=xl1512073></td>\n" + 
							" </tr>\n" + 
							" <tr height=19 style='height:14.25pt'>\n" + 
							"  <td height=19 class=xl6612073 width=228 style='height:14.25pt;width:171pt'>其中</td>\n" + 
							"  <td colspan=8 class=xl7012073 width=608 style='border-left:none;width:456pt'>　</td>\n" + 
							" </tr>";
						for (var j = 0,jj=dataObj.第二部分.length; j <jj; j++) {
							htmls+=
								"<tr height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6412073 width=228 style='height:14.25pt;border-top:\n" + 
								"  none;width:171pt'>"+dataObj.第二部分[j].项目+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第二部分[j].项目=="　其他"||dataObj.第二部分[j].项目=="全部"?((dataObj.第二部分[j].办结数)/2):dataObj.第二部分[j].办结数)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第二部分[j].办结同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第二部分[j].项目=="　其他"||dataObj.第二部分[j].项目=="全部"?((dataObj.第二部分[j].查处数)/2):dataObj.第二部分[j].查处数)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第二部分[j].查处同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第二部分[j].项目=="　其他"||dataObj.第二部分[j].项目=="全部"?((dataObj.第二部分[j].经济制裁)/2):dataObj.第二部分[j].经济制裁)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第二部分[j].制裁同比+"</td>\n" + 
								"  <td class=xl6512073 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+(dataObj.第二部分[j].项目=="　其他"||dataObj.第二部分[j].项目=="全部"?((dataObj.第二部分[j].退还金额)/2):dataObj.第二部分[j].退还金额)+"</td>\n" + 
								"  <td class=xl6512073 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'>"+dataObj.第二部分[j].退还同比+"</td>\n" + 
								" </tr>";
						}
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6312073 width=228 style='height:14.25pt;border-top:\n" + 
							"  none;width:171pt'>项目</td>\n" + 
							"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
							"  width:60pt'>办结数</td>\n" + 
							"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>同比</td>\n" + 
							"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
							"  width:60pt'>查处数</td>\n" + 
							"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>同比</td>\n" + 
							"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
							"  width:60pt'>经济制裁</td>\n" + 
							"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>同比</td>\n" + 
							"  <td class=xl6312073 width=80 style='border-top:none;border-left:none;\n" + 
							"  width:60pt'>退还金额</td>\n" + 
							"  <td class=xl6312073 width=72 style='border-top:none;border-left:none;\n" + 
							"  width:54pt'>同比</td>\n" + 
							" </tr>";
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("jiagejubaoxinxi2_down.jsp", "_blank");
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
		title="价格投诉举报信息采集表2 统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			
		<div id="tbegintime" valuetip='默认为上年同期...' name='tbegintime' name='tbegintime' vtype="datefield"
			label="同比开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="tendtime" name='tendtime' valuetip='默认为上年同期...' vtype="datefield" label="同比截止日期"
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
