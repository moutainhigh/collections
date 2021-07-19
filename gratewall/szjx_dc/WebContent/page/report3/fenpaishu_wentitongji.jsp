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
		
		/* if (inftype.length == 0) {
			alert("信息件类型为空");
			return;
		} */
		
		// 获取月份
		var month = parseInt(begintime.split("-")[1]);
		
		var htmls = '<div>'
				+ '<a href="#" id="pluginurl" onclick="downReport('+ '\''
				+ begintime +'\',\'' + endtime + '\',\'' + inftype + '\''
				+ ')"><font color="blue" size="5px">下  载</font></a>'
				+ '</div>'
				+ '<div id="问题性质统计表_16963" align=center x:publishsource="Excel">'
				+ '<table border=0 cellpadding=0 cellspacing=0 width=533 style=\'border-collapse:'
				+ ' collapse;table-layout:automatic;width:400pt\'>'
				+ ' <col width=379 style=\'mso-width-source:userset;mso-width-alt:12128;width:284pt\'>'
				+ ' <col width=77 span=2 style=\'mso-width-source:userset;mso-width-alt:2464;'
				+ ' width:58pt\'>'
				+ ' <tr height=33 style=\'mso-height-source:userset;height:24.75pt\'>'
				+ '  <td colspan=3 height=33 class=xl6416963 width=533 style=\'height:24.75pt;'
				+ '  width:400pt\'>咨询举报问题分派数年度月份对比统计报表</td>'
				+ ' </tr>'
				+ ' <tr height=22 style=\'height:16.5pt\'>'
				+ '  <td height=22 class=xl6316963 style=\'height:16.5pt;border-top:none\'>　</td>'
				+ '  <td class=xl6516963 style=\'border-top:none;border-left:none\'>' + month + '月份</td>'
				+ '  <td class=xl6516963 style=\'border-top:none;border-left:none\'>总计</td>'
				+ ' </tr>'
		$.ajax({
			url : rootPath + "/queryFenPai/wenTiTongJi.do",
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
					return;
				}
				
				for (var i = 0; i < data.length; i++) {
					htmls += 
						' <tr height=19 style=\'height:14.25pt\'>'
						+ ' <td height=19 class=xl6816963 style=\'height:14.25pt\'>' + data[i].name + '</td>'
						+ '	  <td class=xl6616963>'+ data[i].value +'</td>'
						+ '	  <td class=xl6716963 style=\'border-top:none;border-left:none\'>'+ data[i].value +'</td>'
						+ '	 </tr>';
				}
			}
		});
				
		htmls += '</table></div>';
		
		var newWim = open("fenpaishu_wentitongji_down.jsp", "_blank");
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
		layoutconfig="{cols:3, columnwidth: ['33%','33%', '34%']}" height="100%"
		title="问题性质统计表  统计条件">
		<div id="begintime" name='begintime' vtype="datefield" label="开始日期" 
			labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="结束日期"
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