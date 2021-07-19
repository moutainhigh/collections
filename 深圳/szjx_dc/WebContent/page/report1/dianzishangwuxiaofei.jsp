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
	
	/* $(document).ready(function getcode(){
		
	var date = new Date;
		var year = date.getFullYear();
		for (var i = 11; i > 0; i--) {
			$("#tongjinianfen").comboxfield('addOption', year - i, year - i);
		}
		$("#tongjinianfen").comboxfield('addOption', year, year);
		$("#tongjinianfen").comboxfield("reload");

	}); */

	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		//var hbegintime = aa.data.hbegintime;
		//var hendtime = aa.data.hendtime;
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				jazz.warn("初始日期大于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			jazz.warn("日期为空");
			return;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
				'\'' + begintime + '\''  +','+
				//'\'' + endtime + '\''  +','+
				//'\'' + hbegintime + '\''  +','+
				'\'' + endtime + '\''
				+')" > 下     载</a>' + '</div>' 
		+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' +
		"<div id=\"电子商务消费投诉情况统计报表_26225\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=1181 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:886pt'>\n" + 
		" <col width=144 style='mso-width-source:userset;mso-width-alt:4608;width:108pt'>\n" + 
		" <col width=72 span=5 style='width:54pt'>\n" + 
		" <col width=84 style='mso-width-source:userset;mso-width-alt:2688;width:63pt'>\n" + 
		" <col width=72 span=2 style='width:54pt'>\n" + 
		" <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>\n" + 
		" <col width=72 span=5 style='width:54pt'>\n" + 
		" <tr height=36 style='mso-height-source:userset;height:27.0pt'>\n" + 
		"  <td colspan=15 height=36 class=xl6326225 width=1181 style='height:27.0pt;\n" + 
		"  width:886pt'>电子商务消费投诉情况统计报表</td>\n" + 
		" </tr>\n" + 
		" <tr height=19 style='height:14.25pt'>\n" + 
		"  <td height=19 class=xl6426225 style='height:14.25pt;border-top:none'>项目</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>登记量</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>电子商务</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>邮购</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>电话购物</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>电视购物</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>交易平台类</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>应用类</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>服务类</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>互联网门户</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>其他</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>立案处理</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>调解处理</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>没收金额</td>\n" + 
		"  <td class=xl6426225 style='border-top:none;border-left:none'>罚款金额</td>\n" + 
		" </tr>";
		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/dianZiShangWuXiaoFei.do",
			data : {
				"begintime"		  : begintime ,
		        "endtime"         : endtime
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
				for (var i = 0,j=dataObj.length; i < j; i++) {
					htmls+="<tr height=19 style='height:14.25pt'>\n" +
					"  <td height=19 class=xl6526225 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].登记量+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].电子商务+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].邮购+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].电话购物+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].电视购物+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].交易平台类+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].应用类+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].服务类+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].互联网门户+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].其他+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].立案处理+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].调解处理+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].没收金额+"</td>\n" + 
					"  <td class=xl6626225 style='border-top:none;border-left:none'>"+dataObj[i].罚款金额+"</td>\n" + 
					" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("dianzishangwuxiaofei_down.jsp", "_blank");
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
		title="电子商务消费投诉情况统计报表 统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
			
		<div id="hbegintime" name='hbegintime' vtype="datefield"
			label="上期登记开始日期" labelAlign="right" labelwidth='120px' width="410"></div>

		<div id="hendtime" name='hendtime' vtype="datefield" label="上期登记截止日期"
			labelAlign="right" labelwidth='120px' width="410"></div>
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