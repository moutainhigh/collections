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
	$(document).ready(function getcode(){
		//$('#regcode').comboxfield('addOption', "咨询举报申诉中心","6100");
		$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/getRegCode.do",
			data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
					+ "&timess=" + new Date() + "&id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var  i = 0; i < dataObj.length; i++) {
					$('#regcode').comboxfield('addOption', dataObj[i].name, dataObj[i].regdepcode);
				}
				}
		});
	});
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
				+ ','
				+ '\''
				+ regcode
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+ "<div id=\"热点投诉人举报投诉撤诉统计报表_12771\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=976 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:732pt'>\n"
				+ " <col width=112 style='mso-width-source:userset;mso-width-alt:3584;width:84pt'>\n"
				+ " <col width=72 span=12 style='width:54pt'>\n"
				+ " <tr height=44 style='mso-height-source:userset;height:33.0pt'>\n"
				+ "  <td colspan=13 height=44 class=xl6712771 width=976 style='border-right:1.0pt solid black;\n" + 
				"  height:33.0pt;width:732pt'>热点投诉人举报投诉撤诉统计报表</td>\n"
				+ " </tr>\n"
				+ " <tr height=20 style='height:15.0pt'>\n"
				+ "  <td height=20 class=xl6312771 width=112 style='height:15.0pt;border-top:none;\n" + 
				"  width:84pt'>热点投诉人</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>稽查局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>福田局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>罗湖局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>南山局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>盐田局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>龙岗局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>宝安局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>光明局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>坪山局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>龙华局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>大鹏局</td>\n"
				+ "  <td class=xl6412771 width=72 style='border-top:none;width:54pt'>总数</td>\n"
				+ " </tr>\n" + "";
		$
				.ajax({
					type : "post",
					url : rootPath + "/quert12315Controller/queryHotManChe.do",
					data : "begintime=" + begintime + "&endtime=" + endtime + "&regcode="+regcode
							+ "&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr height=20 style='height:15.0pt'>\n"
									+ " <td height=20 class=xl6512771 width=112 style='height:15.0pt;width:84pt'>"
									+ dataObj[i].persname
									+ "　</td>\n"
									+ " <td class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].市市场稽查局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].福田局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].罗湖局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].南山局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].盐田局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].龙岗局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].宝安局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].光明局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].坪山局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].龙华局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].大鹏局
									+ "　</td>\n"
									+ " <td  class=xl6612771 width=72 style='width:54pt'>　"
									+ dataObj[i].总数 + "　</td>\n" + "</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("hotman_che_tj_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 200);
	}
</script>
</head>
<body><!--  multiple="ture"  -->
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="热点投诉人举报投诉撤诉统计报表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
 		<div name='regcode' id="regcode" vtype="comboxfield"   label="登记部门"
		labelAlign="right" dataurl="return getcode()"labelwidth='100px' width="410" ></div>
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
