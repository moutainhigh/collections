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
				+ '\'' + regcode + '\'' 
				+ ')" > 下     载</a>' + '</div>' + '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' 
				+"<div id=\"工作联系件统计表_14322\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1254 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:943pt'>\n" + 
				" <col width=72 style='width:54pt'>\n" + 
				" <col width=173 style='mso-width-source:userset;mso-width-alt:5536;width:130pt'>\n" + 
				" <col width=445 style='mso-width-source:userset;mso-width-alt:14240;width:334pt'>\n" + 
				" <col width=85 span=2 style='mso-width-source:userset;mso-width-alt:2720;\n" + 
				" width:64pt'>\n" + 
				" <col width=77 style='mso-width-source:userset;mso-width-alt:2464;width:58pt'>\n" + 
				" <col width=85 style='mso-width-source:userset;mso-width-alt:2720;width:64pt'>\n" + 
				" <col width=106 style='mso-width-source:userset;mso-width-alt:3392;width:80pt'>\n" + 
				" <col width=126 style='mso-width-source:userset;mso-width-alt:4032;width:95pt'>\n" + 
				" <tr height=51 style='mso-height-source:userset;height:38.25pt'>\n" + 
				"  <td colspan=9 height=51 class=xl7014322 width=1254 style='height:38.25pt;\n" + 
				"  width:943pt'>工作联系件统计表</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl6414322 style='height:14.25pt;border-top:none'>编号</td>\n" + 
				"  <td class=xl6414322 style='border-top:none;border-left:none'>登记部门</td>\n" + 
				"  <td class=xl6514322 width=445 style='border-top:none;border-left:none;\n" + 
				"  width:334pt'>主要内容</td>\n" + 
				"  <td class=xl6614322 style='border-top:none;border-left:none'>转派时间</td>\n" + 
				"  <td class=xl6614322 style='border-top:none;border-left:none'>接单时间</td>\n" + 
				"  <td class=xl6714322 width=77 style='border-top:none;border-left:none;\n" + 
				"  width:58pt'>回复时间</td>\n" + 
				"  <td class=xl6614322 style='border-top:none;border-left:none'>办结时间</td>\n" + 
				"  <td class=xl6614322 style='border-top:none;border-left:none'>服务站经办人</td>\n" + 
				"  <td class=xl6614322 style='border-top:none;border-left:none'>监管部门跟踪人</td>\n" + 
				" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/gongZuoLianXiJian.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"stano" : combox_tree,
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
						"  <td height=19 class=xl6814322 style='height:14.25pt;border-top:none'>"+dataObj[i].workno+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].登记部门+"</td>\n" + 
						"  <td class=xl6914322 width=445 style='border-top:none;border-left:none;\n" + 
						"  width:334pt'>"+dataObj[i].主要内容+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].转发时间+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].接单时间+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].回复时间+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].办结时间+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].服务站经办人+"</td>\n" + 
						"  <td class=xl6314322 style='border-top:none;border-left:none'>"+dataObj[i].监管部门跟踪人+"</td>\n" + 
						" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("gongzuolianxijian_lianxijiantongji_down.jsp", "_blank");
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
		title="工作联系件统计表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='100px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='regcode' id="regcode" vtype="comboxfield" multiple="false" label="登记部门"
		labelAlign="right" labelwidth='100px' width="410" ></div>
		
		<div id='combox_tree' name='combox_tree' vtype="comboxtreefield" 
			label="服务站" labelalign="right" labelwidth='100px' width="410"  ></div>
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