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
		/* $.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/getRegCode.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#combox_tree").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#combox_tree").comboxtreefield("reload");
				}
		}); */
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/getRegDep.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#regcode").comboxfield('option',"dataurl",JSON.parse(data));
				$("#regcode").comboxfield("reload");
				}
		});
	});
	
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var regcode=aa.data.regcode;
		//var combox_tree=aa.data.combox_tree;
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
	
	var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' 
				+ '\'' + begintime + '\'' + ',' 
				+ '\'' + endtime + '\'' + ',' 
				+ '\'' + regcode + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+"<div id=\"工作联系件统计表_14322\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<div id=\"工作联系件处理情况表_29107\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=809 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:608pt'>\n" + 
				" <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>\n" + 
				" <col width=117 span=2 style='mso-width-source:userset;mso-width-alt:3744;\n" + 
				" width:88pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=117 style='mso-width-source:userset;mso-width-alt:3744;width:88pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <tr height=31 style='height:23.25pt'>\n" + 
				"  <td colspan=8 height=31 class=xl6329107 width=809 style='height:23.25pt;\n" + 
				"  width:608pt'>工作联系件处理情况表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6429107 style='height:14.25pt;border-top:none'>编号</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>发送对象数量</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>已接收到服务站</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>接收率</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>已回复的服务站</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>回复率</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>已办结的服务站</td>\n" + 
				"  <td class=xl6429107 style='border-top:none;border-left:none'>办结率</td>\n" + 
				" </tr>";

			$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/chuLiQingKuang.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"regcode" :regcode
			},
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				if (data==null||data=="null"||data.length==0) {
					alert("无数据");
					return;
				}
				var dataObj = eval("(" + data + ")");
				for (var i = 0, t = dataObj.length; i < t; i++) {
					htmls +=

						"<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6529107 style='height:14.25pt;border-top:none'>"+dataObj[i].编号+"</td>\n" + 
						"  <td class=xl6629107 style='border-top:none;border-left:none'>"+dataObj[i].发送数量+"</td>\n" + 
						"  <td class=xl6629107 style='border-top:none;border-left:none'>"+dataObj[i].接收数量+"</td>\n" + 
						"  <td class=xl6729107 style='border-top:none;border-left:none'>"+dataObj[i].接收率+"</td>\n" + 
						"  <td class=xl6629107 style='border-top:none;border-left:none'>"+dataObj[i].回复数量+"</td>\n" + 
						"  <td class=xl6729107 style='border-top:none;border-left:none'>"+dataObj[i].回复率+"</td>\n" + 
						"  <td class=xl6629107 style='border-top:none;border-left:none'>"+dataObj[i].办结数量+"</td>\n" + 
						"  <td class=xl6729107 style='border-top:none;border-left:none'>"+dataObj[i].办结率+"</td>\n" + 
						" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("gongzuolianxijian_chuliqingkuang_down.jsp", "_blank");
					window.setTimeout(function() {
						newWim.document.body.innerHTML = htmls;
					}, 500); 
			}
		});
		
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="工作联系件处理情况表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='regcode' id="regcode" vtype="comboxfield" multiple="false" label="登记部门"
		labelAlign="right" labelwidth='100px' width="410" ></div>
		
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