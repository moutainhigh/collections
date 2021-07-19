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
				+ "<div id=\"话务员登记热点投诉人件统计报表_23301\" align=center x:publishsource=\"Excel\">\n"
				+ "\n"
				+ "<table border=0 cellpadding=0 cellspacing=0 width=1930 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:1448pt'>\n"
				+ " <col width=72 span=9 style='width:54pt'>\n"
				+ " <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>\n"
				+ " <col width=72 span=12 style='width:54pt'>\n"
				+ " <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:68pt'>\n"
				+ " <col width=95 style='mso-width-source:userset;mso-width-alt:3040;width:71pt'>\n"
				+ " <col width=72 span=2 style='width:54pt'>\n"
				+ " <tr height=39 style='mso-height-source:userset;height:29.25pt'>\n"
				+ "  <td colspan=27 height=39 class=xl6523301 width=1930 style='border-right:1.0pt solid black;\n" + 
				"  height:29.25pt;width:1448pt'>话务员登记热点投诉人件统计报表</td>\n"
				+ " </tr>\n"
				+ " <tr height=37 style='height:27.75pt'>\n"
				+ "  <td height=37 class=xl6823301 width=72 style='height:27.75pt;border-top:none;\n" + 
				"  width:54pt'>姓名</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>总数</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>电话</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>短信</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>来人</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>来函</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>传真</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>互联网络</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>留言</td>\n"
				+ "  <td class=xl6923301 width=89 style='border-top:none;width:67pt'>市府12345转件</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>其他</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>政府在线</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>电子邮件</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>直通车</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>来信</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>上级部门交办</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>民心桥</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>其它部门转办</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'><span\n" + 
				"  lang=EN-US>QQ</span></td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>微信</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>三打两建办</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>局长邮箱</td>\n"
				+ "  <td class=xl6923301 width=90 style='border-top:none;width:68pt'>异地消费申诉转办件</td>\n"
				+ "  <td class=xl6923301 width=95 style='border-top:none;width:71pt'>全国价格举报平台来件</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>消费通</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>消委会转件</td>\n"
				+ "  <td class=xl6923301 width=72 style='border-top:none;width:54pt'>全国12315平台转件</td>\n"
				+ " </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/quert12315Controller/queryYuWuHot.do",
			data : "begintime=" + begintime + "&endtime=" + endtime+"&regcode="+regcode
					+ "&timess=" + new Date() + "&id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var dataObj = eval("(" + data + ")");
				for (var i = 0; i < dataObj.length; i++) {
					htmls+=

						"<tr height=20 style='height:15.0pt'>\n" +
						" <td height=20 class=xl6323301 width=72 style='height:15.0pt;width:54pt'><span\n" + 
						" lang=EN-US>"+dataObj[i].regperson+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].总数+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>　"+dataObj[i].电话+"</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].短信+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>　"+dataObj[i].来人+"</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].来函+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].传真+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>　"+dataObj[i].互联网络+"</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].留言+"　</span></td>\n" + 
						" <td class=xl6423301 width=89 style='width:67pt'><span lang=EN-US>"+dataObj[i].市府12345转件+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].其他+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].政府在线+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].电子邮件+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].直通车+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>　"+dataObj[i].来信+"</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].上级部门交办+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].民心桥+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].其它部门转办+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].qq服务+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].微信平台+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'><span lang=EN-US>"+dataObj[i].三打两建办+"　</span></td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'>"+dataObj[i].局长邮箱+"　</td>\n" + 
						" <td class=xl6423301 width=90 style='width:68pt'>"+dataObj[i].异地消费申诉转办件+"　</td>\n" + 
						" <td class=xl6423301 width=95 style='width:71pt'>　"+dataObj[i].全国价格举报平台来件+"</td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'>　"+dataObj[i].消费通+"</td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'>　"+dataObj[i].消委会转件+"</td>\n" + 
						" <td class=xl6423301 width=72 style='width:54pt'>　"+dataObj[i].全国12315平台转件+"</td>\n" + 
						"</tr>";
				}
				htmls += '</table>' + '</div>';
			}
		});
		var newWim = open("huawu_hot_down.jsp", "_blank");
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
		title="话务员登记热点投诉人件统计报表  统计条件">
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