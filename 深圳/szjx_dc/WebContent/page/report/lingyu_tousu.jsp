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
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
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
				+ '</div>'+

				"<div id=\"各领域投诉业务数量统计表（表2）_24374\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1466 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1103pt'>\n" + 
				" <col width=204 style='mso-width-source:userset;mso-width-alt:6528;width:153pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <col width=69 style='mso-width-source:userset;mso-width-alt:2208;width:52pt'>\n" + 
				" <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>\n" + 
				" <tr class=xl6524374 height=30 style='mso-height-source:userset;height:22.5pt'>\n" + 
				"  <td colspan=16 height=30 class=xl6724374 width=1466 style='border-right:.5pt solid black;\n" + 
				"  height:22.5pt;width:1103pt'>各领域投诉业务数量统计表</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl6524374 height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=57 class=xl6324374 style='height:42.75pt;border-top:\n" + 
				"  none'>单位</td>\n" + 
				"  <td rowspan=2 class=xl6424374 width=72 style='border-top:none;width:54pt'>投诉总数</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>质量</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>计量</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>标准</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>特种设备</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>认证认可</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>生产许可证</td>\n" + 
				"  <td colspan=2 class=xl6424374 width=170 style='border-left:none;width:128pt'>其他</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl6524374 height=38 style='mso-height-source:userset;height:28.5pt'>\n" + 
				"  <td height=38 class=xl6424374 width=69 style='height:28.5pt;border-top:none;\n" + 
				"  border-left:none;width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				"  <td class=xl6424374 width=69 style='border-top:none;border-left:none;\n" + 
				"  width:52pt'>数量</td>\n" + 
				"  <td class=xl6424374 width=101 style='border-top:none;border-left:none;\n" + 
				"  width:76pt'>占投诉总数的比例（%）</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/lingYuTouSu.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=

								"<tr height=19 style='mso-height-source:userset;height:14.25pt'>\n" +
								"  <td height=19 class=xl6324374 style='height:14.25pt;border-top:none'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl6624374 width=72 style='border-top:none;border-left:none;\n" + 
								"  width:54pt'><span lang=EN-US>"+dataObj[i].投诉总数+"</span></td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].质量数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].质量占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].计量数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].计量占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].标准数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].标准占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].特种设备数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].特种设备占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].认证认可数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].认证认可占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].生产许可证数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].生产许可证占投诉的比例+"　</td>\n" + 
								"  <td class=xl6624374 width=69 style='border-top:none;border-left:none;\n" + 
								"  width:52pt'>"+dataObj[i].其他数量+"　</td>\n" + 
								"  <td class=xl6624374 width=101 style='border-top:none;border-left:none;\n" + 
								"  width:76pt'>"+dataObj[i].其他占投诉的比例+"　</td>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("lingyu_tousu_down.jsp", "_blank");
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
		title="各领域投诉业务数量统计表  统计条件">
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