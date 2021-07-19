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
				+ "<div id=\"各分局科所投诉举报登记办理情况_326\" align=center x:publishsource=\"Excel\">\n" +
				"<table border=0 cellpadding=0 cellspacing=0 width=1121 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:841pt'>\n" + 
				" <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>\n" + 
				" <col width=87 style='mso-width-source:userset;mso-width-alt:2784;width:65pt'>\n" + 
				" <col width=72 span=13 style='width:54pt'>\n" + 
				" <tr height=65 style='mso-height-source:userset;height:48.75pt'>\n" + 
				"  <td colspan=15 height=65 class=xl78326 width=1121 style='border-right:.5pt solid black;\n" + 
				"  height:48.75pt;width:841pt'>各分局科所投诉举报登记办理情况</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td colspan=2 rowspan=3 height=57 class=xl63326 width=185 style='border-right:\n" + 
				"  .5pt solid black;border-bottom:.5pt solid black;height:42.75pt;width:139pt'>承办单位</td>\n" + 
				"  <td colspan=4 class=xl69326 style='border-right:.5pt solid black;border-left:\n" + 
				"  none'>登记情况</td>\n" + 
				"  <td colspan=9 class=xl69326 style='border-right:.5pt solid black;border-left:\n" + 
				"  none'>办结情况</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl72326 style='height:14.25pt;border-top:none;border-left:\n" + 
				"  none'>申诉</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>举报</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>总计</td>\n" + 
				"  <td rowspan=2 class=xl73326 width=72 style='border-bottom:.5pt solid black;\n" + 
				"  border-top:none;width:54pt'>登记比例（%）</td>\n" + 
				"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
				"  none'>申诉</td>\n" + 
				"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
				"  none'>举报</td>\n" + 
				"  <td colspan=3 class=xl74326 style='border-right:.5pt solid black;border-left:\n" + 
				"  none'>总计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl72326 style='height:14.25pt;border-top:none;border-left:\n" + 
				"  none'>登记量</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>登记量</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>登记量</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>分派数</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl72326 style='border-top:none;border-left:none'>办结率(%)</td>\n" + 
				" </tr>";
		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/fenJuBanLi.do",
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
								"  <td height=19 class=xl81326 width=98 style='height:14.25pt;border-top:none;\n" + 
								"  width:74pt'>"+dataObj[i].处室+"</td>\n" + 
								"  <td class=xl81326 width=87 style='border-top:none;border-left:none;\n" + 
								"  width:65pt'>"+dataObj[i].单位+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].投诉登记+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].举报登记+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].总登记量+"</td>\n" + 
								"  <td class=xl83326 style='border-top:none;border-left:none'>"+dataObj[i].登记比例+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].投诉分派+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].投诉完成+"</td>\n" + 
								"  <td class=xl83326 style='border-top:none;border-left:none'>"+dataObj[i].投诉办结率+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].举报分派+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].举报完成+"</td>\n" + 
								"  <td class=xl83326 style='border-top:none;border-left:none'>"+dataObj[i].举报办结率+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].分派总计+"</td>\n" + 
								"  <td class=xl82326 style='border-top:none;border-left:none'>"+dataObj[i].完成总计+"</td>\n" + 
								"  <td class=xl83326 style='border-top:none;border-left:none'>"+dataObj[i].总完成率+"</td>\n" + 
								" </tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("fenjubanli_down.jsp", "_blank");
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
		title="各分局科所投诉举报登记办理情况  统计条件">
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
