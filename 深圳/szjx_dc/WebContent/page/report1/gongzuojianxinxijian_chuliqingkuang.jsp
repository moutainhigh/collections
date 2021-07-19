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
			url : rootPath + "/quert12315Controller/getRegCode.do",
			data : "id=" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var datas=eval("(" + data + ")");
				$('#regcode').comboxfield('addOption', "咨询举报申诉中心","6100");
				for(var i=0,j=data.length;i<j;i++){
					$('#regcode').comboxfield('addOption', datas[i].name,datas[i].regdepcode);
				}
				//$("#regcode").comboxfield('option',"dataurl",JSON.parse(data));
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
				+
				"<div id=\"信息件工作联系件登记处理情况统计表_16211\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=1063 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:797pt'>\n" + 
				" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
				" <col width=303 style='mso-width-source:userset;mso-width-alt:9696;width:227pt'>\n" + 
				" <col width=100 style='mso-width-source:userset;mso-width-alt:3200;width:75pt'>\n" + 
				" <col width=80 span=7 style='mso-width-source:userset;mso-width-alt:2560;\n" + 
				" width:60pt'>\n" + 
				" <tr height=46 style='mso-height-source:userset;height:34.5pt'>\n" + 
				"  <td colspan=10 height=46 class=xl6816211 width=1063 style='height:34.5pt;\n" + 
				"  width:797pt'><a name=\"RANGE!A1\"><span style='mso-spacerun:yes'> </span><font\n" + 
				"  class=\"font616211\">信息件工作联系件登记处理情况统计表</font></a></td>\n" + 
				" </tr>\n" + 
				" <tr height=38 style='height:28.5pt'>\n" + 
				"  <td height=38 class=xl6316211 style='height:28.5pt;border-top:none'>工单类型</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>登记部门</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>登记数量</td>\n" + 
				"  <td class=xl6416211 width=80 style='border-top:none;border-left:none;\n" + 
				"  width:60pt'>比上年同期增长率</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>已办结</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>办结率</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>成功调解</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>成功调解率</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>未处理</td>\n" + 
				"  <td class=xl6316211 style='border-top:none;border-left:none'>未处理率</td>\n" + 
				" </tr>";
			$.ajax({
			type : "post",
			url : rootPath + "/queryXiaoBao/xinXiJianGongZuoJian.do",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"regcode" :regcode,
				"stano" :combox_tree
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
						"  <td height=19 class=xl6516211 style='height:14.25pt;border-top:none'>"+dataObj[i].waretype+"</td>\n" + 
						"  <td class=xl6616211 width=303 style='border-top:none;border-left:none;\n" + 
						"  width:227pt'>"+dataObj[i].bumen+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].dengjishu+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].tongzeng+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].banjie+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].banjielv+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].tiaojie+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].tiaojielv+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].weichuli+"</td>\n" + 
						"  <td class=xl6716211 style='border-top:none;border-left:none'>"+dataObj[i].weichulilv+"</td>\n" + 
						" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("gongzuojianxinxijian_chuliqingkuang_down.jsp", "_blank");
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
		title="信息件工作联系件登记处理情况统计表 统计条件">
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