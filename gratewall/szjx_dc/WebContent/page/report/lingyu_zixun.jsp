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
				+ '</div>'+
				"<div id=\"各领域咨询业务数量统计表（表1）_24032\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1720 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1290pt'>\n" + 
				" <col width=200 style='mso-width-source:userset;mso-width-alt:6400;width:150pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <col width=80 style='mso-width-source:userset;mso-width-alt:2560;width:60pt'>\n" + 
				" <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>\n" + 
				" <tr class=xl6324032 height=46 style='mso-height-source:userset;height:34.5pt'>\n" + 
				"  <td colspan=16 height=46 class=xl6624032 width=1720 style='height:34.5pt;\n" + 
				"  width:1290pt'>各领域咨询业务数量统计表</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl6324032 height=19 style='height:14.25pt'>\n" + 
				"  <td rowspan=2 height=74 class=xl6424032 style='height:55.5pt;border-top:none'>单位</td>\n" + 
				"  <td rowspan=2 class=xl6524032 width=120 style='border-top:none;width:90pt'>有效咨询业务量&nbsp;</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>质量</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>计量</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>标准</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>特种设备</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>认证认可</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>生产许可证</td>\n" + 
				"  <td colspan=2 class=xl6524032 width=200 style='border-left:none;width:150pt'>其他</td>\n" + 
				" </tr>\n" + 
				" <tr class=xl6324032 height=55 style='mso-height-source:userset;height:41.25pt'>\n" + 
				"  <td height=55 class=xl6524032 width=80 style='height:41.25pt;border-top:none;\n" + 
				"  border-left:none;width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>数量</td>\n" + 
				"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
				"  width:90pt'>占有效咨询业务量的比例（%）</td>\n" + 
				" </tr>";

				$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/lingYuZiXun.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls+=
								"<tr class=xl6324032 height=19 style='height:14.25pt'>\n" +
								"  <td height=19 class=xl6424032 style='height:14.25pt;border-top:none'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].有效咨询业务量+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].质量数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].质量占有效咨询业务量的比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].计量数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].计量占有效咨询业务量的比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].标准数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].标准占有效咨询业务量的比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].特种设备数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].特种设备占有效咨询业务量的比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].认证认可数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].认证认可占有效咨询业务量的比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].生产许可证数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].生产许可证占有效咨询业务量比例+"　</td>\n" + 
								"  <td class=xl6524032 width=80 style='border-top:none;border-left:none;\n" + 
								"  width:60pt'>"+dataObj[i].其他数量+"　</td>\n" + 
								"  <td class=xl6524032 width=120 style='border-top:none;border-left:none;\n" + 
								"  width:90pt'>"+dataObj[i].其他占有效咨询业务量的比例+"　</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
				var newWim = open("lingyu_zixun_down.jsp", "_blank");
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
		title="各领域咨询业务数量统计表  统计条件">
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