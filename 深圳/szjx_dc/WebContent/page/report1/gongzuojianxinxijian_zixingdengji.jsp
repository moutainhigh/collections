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
		$.ajax({
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
		});
		
		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/getStationType.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				$("#combox_tree1").comboxtreefield('option',"dataurl",JSON.parse(data));
				$("#combox_tree1").comboxtreefield("reload");
				}
		});
	});
	
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var stationtype=aa.data.combox_tree1;
		var combox_tree=aa.data.combox_tree;
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
				+ '\'' + combox_tree + '\'' + ',' 
				+ '\'' + stationtype + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+"<div id=\"自行登记消费申诉处理情况统计表_28187\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=665 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:500pt'>\n" + 
				" <col width=300 style='mso-width-source:userset;mso-width-alt:9600;width:225pt'>\n" + 
				" <col width=85 span=3 style='mso-width-source:userset;mso-width-alt:2720;\n" + 
				" width:64pt'>\n" + 
				" <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>\n" + 
				" <tr height=45 style='mso-height-source:userset;height:33.75pt'>\n" + 
				"  <td colspan=5 height=45 class=xl6328187 width=665 style='height:33.75pt;\n" + 
				"  width:500pt'>自行登记消费申诉处理情况统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6428187 style='height:14.25pt;border-top:none'>登记部门</td>\n" + 
				"  <td class=xl6428187 style='border-top:none;border-left:none'>数量</td>\n" + 
				"  <td class=xl6428187 style='border-top:none;border-left:none'>和解数</td>\n" + 
				"  <td class=xl6428187 style='border-top:none;border-left:none'>和解率</td>\n" + 
				"  <td class=xl6428187 style='border-top:none;border-left:none'>涉及消费金额</td>\n" + 
				" </tr>";


		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/ziXingDengJi.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"stano" : combox_tree,
				"stationtype" :stationtype
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
						"  <td height=19 class=xl6528187 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
						"  <td class=xl6528187 style='border-top:none;border-left:none'>"+dataObj[i].cnt+"</td>\n" + 
						"  <td class=xl6528187 style='border-top:none;border-left:none'>"+dataObj[i].success+"</td>\n" + 
						"  <td class=xl6628187 style='border-top:none;border-left:none'>"+dataObj[i].tiaojielv+"</td>\n" + 
						"  <td class=xl6528187 style='border-top:none;border-left:none'>"+dataObj[i].jine+"</td>\n" + 
						" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("gongzuojianxinxijian_zixingdengji_down.jsp", "_blank");
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
		title="自行登记消费申诉处理情况统计表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		
		<div id='combox_tree' name='combox_tree' vtype="comboxtreefield" 
			label="服务站" labelalign="right" multiple="false" labelwidth='100px' width="410"  ></div>
		<div id='combox_tree1' name='combox_tree1' vtype="comboxtreefield" 
			label="服务站类型" labelalign="right" labelwidth='100px' width="410"  ></div>
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