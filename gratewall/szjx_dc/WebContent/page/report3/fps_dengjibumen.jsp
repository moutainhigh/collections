<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title>分派数</title>
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
<script type="text/javascript" charset="UTF-8">
	// 查询 
	function queryUrl() {
		var value = $("#formpanel").formpanel('getValue');
		var begintime = value.data.begintime;
		var endtime = value.data.endtime;
		var inftype = value.data.inftype;
		var flag = false;  //查询数据标志位  false：有数据；true：无数据
		
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("开始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("开始日期等于截止日期");
				return;
			}
		}
		
		var htmls = '';
		
		$.ajax({
			url : rootPath + "/queryFenPai/registDept.do",
			type : "post",
			data : {
				"begintime" : begintime,
				"endtime" : endtime,
				"inftype" : inftype
			},
			dataType : "json",
			async : false,
			cache : false,
			success : function(data) {
				
				if (!data) {  // data为null
					alert("暂无数据！");
					flag = true;
					return;
				}
				
				var month = data.month;
				var query = data.query;
				
				htmls = '<div>'
					+ '<a href="#" id="pluginurl" onclick="downReport('+ '\''
					+ begintime +'\',\'' + endtime + '\',\'' + inftype + '\''
					+ ')"><font color="blue" size="5px">下  载</font></a>'
					+ '</div>'
					+ '<div id="登记部门_2360" align=center x:publishsource="Excel">'
					+ '<table border=0 cellpadding=0 cellspacing=0 width=406 style=\'border-collapse:'
					+ ' collapse;table-layout:automatic;width:305pt\'>'
					+ ' <col width=252 style=\'mso-width-source:userset;mso-width-alt:8064;width:189pt\'>'
					+ ' <col width=76 style=\'mso-width-source:userset;mso-width-alt:2432;width:57pt\'>'
					+ ' <tr height=40 style=\'mso-height-source:userset;height:30.0pt\'>'
					+ '  <td colspan='+ (2 + month.length) +' height=40 class=xl632360 width=406 style=\'height:30.0pt;'
					+ '  width:305pt\'>登记部门分派数年度月份对比统计报表</td>'
					+ ' </tr>'
					+ ' <tr height=20 style=\'height:15.0pt\'>'
					+ '  <td height=20 class=xl652360 style=\'height:15.0pt;border-top:none\'>　</td>';
					
				for (var i = 0; i < month.length; i++) {
					htmls += '  <td class=xl652360 style=\'border-top:none;border-left:none\'>'+ month[i].regtime +'</td>';
				}
				
				htmls += '  <td class=xl652360 style=\'border-top:none;border-left:none\'>总计</td></tr>';
				
				for (var i = 0; i < query.length; i++) {
					htmls += '<tr height=20 style=\'height:15.0pt\'>'
					  + '<td height=20 class=xl642360 style=\'height:15.0pt;border-top:none\'>'+ query[i].name +'</td>';
					for (var j = 0; j < month.length; j++) {
						htmls += '<td class=xl662360 style=\'border-top:none;border-left:none\'>'+query[i][month[j].regtime]+'</td>'
					}
					htmls += '<td class=xl662360 style=\'border-top:none;border-left:none\'>'+ query[i].num +'</td></tr>'
				}
				htmls += '</table></div>';
			}
		});
				
		
		if (flag) {	// 查无数据，则不进入下载页面
			return;
		}
		var newWim = open("fps_dengjibumen_down.jsp", "_blank");
		window.setTimeout(function(){
			newWim.document.body.innerHTML = htmls;
		}, 500);
	}
	
	// 重置 
	function reset() {
		$("#formpanel").formpanel('reset');
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="登记部门分派数年度月份对比统计报表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield" label="开始日期" 
			labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="inftype" name="inftype" vtype="comboxfield" label="信息件类型" dataurl="inftype.json"
			labelAlign="right" labelwidth="100px" width="310"></div>
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