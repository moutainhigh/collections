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
		var hbegintime = aa.data.hbegintime;
		var hendtime = aa.data.hendtime;
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
				'\'' + endtime + '\''  +','+
				'\'' + hbegintime + '\''  +','+
				'\'' + hendtime + '\''
				+')" > 下     载</a>' + '</div>' 
		+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>' +
		"<div id=\"电子商务投诉情况统计报表_17275\" align=center x:publishsource=\"Excel\">\n" +
		"\n" + 
		"<table border=0 cellpadding=0 cellspacing=0 width=1219 style='border-collapse:\n" + 
		" collapse;table-layout:automatic;width:915pt'>\n" + 
		" <col width=221 style='mso-width-source:userset;mso-width-alt:7072;width:166pt'>\n" + 
		" <col width=72 span=9 style='width:54pt'>\n" + 
		" <col width=110 style='mso-width-source:userset;mso-width-alt:3520;width:83pt'>\n" + 
		" <col width=72 style='width:54pt'>\n" + 
		" <col width=96 style='mso-width-source:userset;mso-width-alt:3072;width:72pt'>\n" + 
		" <col width=72 style='width:54pt'>\n" + 
		" <tr height=41 style='mso-height-source:userset;height:30.75pt'>\n" + 
		"  <td colspan=14 height=41 class=xl6617275 width=1219 style='height:30.75pt;\n" + 
		"  width:915pt'>电子商务投诉情况统计报表</td>\n" + 
		" </tr>\n" + 
		" <tr height=38 style='height:28.5pt'>\n" + 
		"  <td height=38 class=xl6517275 width=221 style='height:28.5pt;border-top:none;\n" + 
		"  width:166pt'>涉及客体类型</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>电子商务</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>邮购</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>电话购物</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>电视购物</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>交易平台类</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>应用类</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>服务类</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>互联网门户</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>其他</td>\n" + 
		"  <td class=xl6517275 width=110 style='border-top:none;border-left:none;\n" + 
		"  width:83pt'>占投诉登记总量百分比（%）</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>上一时间段数据</td>\n" + 
		"  <td class=xl6517275 width=96 style='border-top:none;border-left:none;\n" + 
		"  width:72pt'>比上一时间段增减（%）</td>\n" + 
		"  <td class=xl6517275 width=72 style='border-top:none;border-left:none;\n" + 
		"  width:54pt'>去年同期数据</td>\n" + 
		" </tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/queryJieAn/dianZiShangWuTouSu.do",
			data : {
				"begintime"		  : begintime ,
		        "endtime"         : endtime,
				"hbegintime"	  : hbegintime,
		        "hendtime"        : hendtime        
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
					var list=dataObj[i];
					for (var i2 = 0,j2=list.length; i2 < j2; i2++) {
						htmls+=
							"<tr height=19 style='height:14.25pt'>\n" +
							"  <td height=19 class=xl6717275 style='height:14.25pt;border-top:none'>"+list[i2].name+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].电子商务+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].邮购+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].电话购物+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].电视购物+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].交易平台类+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].应用类+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].服务类+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].互联网门户+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].其他+"</td>\n" + 
							"  <td class=xl6417275 style='border-top:none;border-left:none'>"+list[i2].占比+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].环比数+"</td>\n" + 
							"  <td class=xl6417275 style='border-top:none;border-left:none'>"+list[i2].环比+"</td>\n" + 
							"  <td class=xl6317275 style='border-top:none;border-left:none'>"+list[i2].同比数+"</td>\n" + 
							" </tr>";
					}
					if (i==j-1) {
						
					}else{
						htmls+="<tr height=19 style='height:14.25pt'>\n" +
						"  <td height=19 class=xl6717275 style='height:14.25pt;border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6417275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6417275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						"  <td class=xl6317275 style='border-top:none;border-left:none;border-right:none'>　</td>\n" + 
						" </tr>";
					}
				}
			htmls += '</table>' + '</div>';
				 var newWim = open("dianzishangwutousu_down.jsp", "_blank");
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
		title="电子商务投诉情况统计报表 统计条件">
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