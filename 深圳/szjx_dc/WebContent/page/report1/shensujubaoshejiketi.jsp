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
/* 	$(document).ready(function getcode(){
		$('#yeWuLeiBie').comboxfield('addOption', "全部","");
		$('#yeWuLeiBie').comboxfield('addOption', "工商","CH20");
		$('#yeWuLeiBie').comboxfield('addOption', "消委会","ZH18");
		$('#yeWuLeiBie').comboxfield('addOption', "知识产权","ZH19");
		$('#yeWuLeiBie').comboxfield('addOption', "价格检查","ZH20");
		$('#yeWuLeiBie').comboxfield('addOption', "质量监督","ZH21");
		
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
	}); */
	function queryUrl() {
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		var infotype = aa.data.infotype;
		var yeWuLeiBie = aa.data.yeWuLeiBie;
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
				+ infotype
				+ '\''
				+ ','
				+ '\''
				+ yeWuLeiBie
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div style=\'background:white;color:white;text-align:right;\'>&nbsp;</div>'
				+ "<div id=\"申诉举报涉及客体类型统计_7528\" align=center x:publishsource=\"Excel\">\n" +
				"\n" + 
				"<table border=0 cellpadding=0 cellspacing=0 width=650 style='border-collapse:\n" + 
				" collapse;table-layout:fixed;width:488pt'>\n" + 
				" <col width=504 style='mso-width-source:userset;mso-width-alt:16128;width:378pt'>\n" + 
				" <col width=146 style='mso-width-source:userset;mso-width-alt:4672;width:110pt'>\n" + 
				" <tr height=34 style='mso-height-source:userset;height:25.5pt'>\n" + 
				"  <td colspan=2 height=34 class=xl667528 width=650 style='height:25.5pt;\n" + 
				"  width:488pt'>申诉举报涉及客体类型统计</td>\n" + 
				" </tr>\n" + 
				" <tr height=19 style='height:14.25pt'>\n" + 
				"  <td height=19 class=xl637528 style='height:14.25pt;border-top:none'>涉及客体类别</td>\n" + 
				"  <td class=xl637528 style='border-top:none;border-left:none'>数量</td>\n" + 
				" </tr>";


		$
				.ajax({
					type : "post",
					url : rootPath + "/queryXiaoBao/shenSuJuBaoSheJiKeTi.do",
					data : "begintime=" + begintime + "&endtime=" + endtime
							+ "&infotype="+infotype+"&yeWuLeiBie="+yeWuLeiBie+"&timess=" + new Date() + "&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0,t=dataObj.length; i < t; i++) {
							for (var j = 0,x=dataObj[i].length; j < x; j++) {
								
									htmls+="<tr height=19 style='height:14.25pt'>\n" +
									" <td height=19 class=xl647528 style='height:14.25pt;border-top:none'>"+dataObj[i][j].name+"</td>\n" + 
									" <td class=xl657528 style='border-top:none;border-left:none'>"+dataObj[i][j].nums+"</td>\n" + 
									"</tr>";
							}
							
							if (i+1<t) {
								htmls+="<tr height=19 style='height:18pt'>\n" +
								" <td class=xl657528 style='height:18pt;border-top:none;border-right:none;border-left:none'></td>\n" + 
								" <td class=xl657528 style='border-top:none;border-left:none;border-right:none'></td>\n" + 
								"</tr>";
							}
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("shensujubaoshejiketi_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 500);

	}
</script>

</head>
<body >
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="申诉举报涉及客体类型统计  统计条件">
		<div id="begintime" name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		 <div name='infotype' id="infotype" vtype="comboxfield"  multiple="false" label="信息件类型"
		labelAlign="right" dataurl='[{"text":"举报", "value":"2"},{"text":"投诉", "value":"1"}]' labelwidth='100px' width="410" ></div>
		 <div name='yeWuLeiBie' id="yeWuLeiBie" vtype="comboxfield"  multiple="false" label="涉及客体类型"
		labelAlign="right" dataurl='[{"text":"工商", "value":"CH20"},{"text":"消委会", "value":"ZH18"},{"text":"知识产权", "value":"ZH19"},{"text":"价格检查", "value":"ZH20"},{"text":"质量监督", "value":"ZH21"}]' labelwidth='100px' width="410" ></div>
		
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