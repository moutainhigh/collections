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
		
	var date = new Date;
		var year = date.getFullYear();
		for (var i = 11; i > 0; i--) {
			$("#tongjinianfen").comboxfield('addOption', year - i, year - i);
		}
		$("#tongjinianfen").comboxfield('addOption', year, year);
		$("#tongjinianfen").comboxfield("reload");

	});

	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var tongjinianfen = aa.data.tongjinianfen;
		var shangnian = tongjinianfen - 1;
		var shangnian=shangnian.toString();
		if (tongjinianfen==0) {
			jazz.warn("请选择统计年份");
			return ;
		}
		var htmls = '<div>' + '<a href="#" id=\'pluginurl\' onclick="downReport(' + 
				'\'' + tongjinianfen + '\''  +','+
				'\'' + shangnian + '\''
				+')" > 下     载</a>' + '</div>' 
		+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' +
		"<div id=\"消委会投诉增长或下降居前十位的商品和服务_24518\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=594 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:446pt'>\n" + 
		" <col width=216 style='mso-width-source:userset;mso-width-alt:6912;width:162pt'>\n" + 
		" <col width=100 span=2 style='mso-width-source:userset;mso-width-alt:3200;\n" + 
		" width:75pt'>\n" + 
		" <col width=178 style='mso-width-source:userset;mso-width-alt:5696;width:134pt'>\n" + 
		" <tr height=34 style='mso-height-source:userset;height:25.5pt'>\n" + 
		"  <td colspan=4 height=34 class=xl6324518 width=594 style='height:25.5pt;\n" + 
		"  width:446pt'>消委会投诉增长或下降居前十位的商品和服务</td>\n" + 
		" </tr>\n" + 
		" <tr height=19 style='height:14.25pt'>\n" + 
		"  <td height=19 class=xl6524518 style='height:14.25pt;border-top:none'>商品和服务名称</td>\n" + 
		"  <td class=xl6524518 style='border-top:none;border-left:none'>"+shangnian+"</td>\n" + 
		"  <td class=xl6524518 style='border-top:none;border-left:none'>"+tongjinianfen+"</td>\n" + 
		"  <td class=xl6524518 style='border-top:none;border-left:none'>变化幅度</td>\n" + 
		" </tr>";

		 $.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/xiaoWeiHuiQianShi.do",
			data : {
				"tongjinianfen"		  : tongjinianfen      ,
		        "shangnian"         : shangnian        
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
						"  <td height=19 class=xl6624518 style='height:14.25pt;border-top:none'>"+dataObj[i].name+"</td>\n" + 
						"  <td class=xl6724518 style='border-top:none;border-left:none'>"+dataObj[i][shangnian]+"</td>\n" + 
						"  <td class=xl6724518 style='border-top:none;border-left:none'>"+dataObj[i][tongjinianfen]+"</td>\n" + 
						"  <td class=xl6824518 style='border-top:none;border-left:none'>"+dataObj[i].cnt+"</td>\n" + 
						" </tr>";
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("xiaoweihuizengjianpaiming_down.jsp", "_blank");
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
		title="消委会投诉增长或下降居前十位的商品和服务 统计条件">
		 <div name='tongjinianfen' id="tongjinianfen" vtype="comboxfield"  multiple="false"
		 label="统计年份" labelAlign="right" labelwidth='120px' width="410" ></div>
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