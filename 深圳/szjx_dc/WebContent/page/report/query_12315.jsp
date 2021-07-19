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
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
/* 	$(document).ready(function getcode(){
		$('#regcode').comboxfield('addOption', "咨询举报申诉中心","6100");
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
				+ ')" > 下     载</a>'
				+ '</div>'+
				"<div id='消费者投诉处理情况表_20970' align=center x:publishsource=\"Excel\" style='padding:8px'>" +
				"<table>" + 
				" <tr >" + 
				"  <td colspan='17' style='text-align:center'>　　消费者投诉处理情况表</td>" + 
				" </tr>" + 
				" <tr>" + 
				"  <td>类别</td>" + 
				"  <td>受理投诉（件）</td>" + 
				"  <td>已处理（件）</td>" + 
				"  <td>调解成功（件）</td>" + 
				"  <td>挽回经济损失（万元）</td>" + 
				"  <td>质量（件）</td>" + 
				"  <td>安全（件）</td>" + 
				"  <td>计量（件）</td>" + 
				"  <td>合同（件）</td>" + 
				"  <td>售后服务（件）</td>" + 
				"  <td>人格尊严（件）</td>" + 
				"  <td>广告（件）</td>" + 
				"  <td>商标（件）</td>" + 
				"  <td>价格（件）</td>" + 
				"  <td>假冒（件）</td>" + 
				"  <td>虚假宣传（件）</td>" + 
				"  <td>其他（件）</td>" + 
				" </tr>";

		$.ajax({
					type : "post",
					url : rootPath
							+ "/quert12315Controller/queryDate.do",
					data : "begintime=" + begintime + "&endtime=" + endtime+"&id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var dataObj = eval("(" + data + ")");
						for (var i = 0; i < dataObj.length; i++) {
							htmls += "<tr><td>"+dataObj[i].weidu+"</td><td>"+dataObj[i].已受理+"</td><td>"+dataObj[i].已处理+"</td><td>"+dataObj[i].调解成功+"</td><td>"+dataObj[i].挽回损失+"</td>";
							htmls+="<td>"+dataObj[i].质量+"</td><td>"+dataObj[i].安全+"</td><td>"+dataObj[i].计量+"</td><td>"+dataObj[i].合同+"</td><td>"+dataObj[i].售后服务+"</td><td>"+dataObj[i].人格尊严+"</td>";
							htmls+="<td>"+dataObj[i].广告+"</td><td>"+dataObj[i].商标+"</td><td>"+dataObj[i].价格+"</td><td>"+dataObj[i].假冒+"</td><td>"+dataObj[i].虚假宣传+"</td><td>"+dataObj[i].其他+"</td>";
							htmls+="</tr>";
						}
						htmls += '</table>' + '</div>';
					}
				});
		var newWim = open("query_12315_down.jsp", "_blank");
		window.setTimeout(function() {
			newWim.document.body.innerHTML = htmls;
		}, 300);
	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="消费者投诉处理情况表  统计条件">
		<div id="begintime" name='begintime' name='endtime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="endtime" name='endtime' vtype="datefield" label="截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div>
			<!-- <div id="hbegintime" name='hbegintime' valuetip='默认为上月同期...' name='hbegintime' vtype="datefield"
			label="环比开始日期" labelAlign="right" labelwidth='100px' width="310"></div> -->
		<!-- <div id="hendtime" name='hendtime' valuetip='默认为上月同期...' vtype="datefield" label="环比截止日期"
			labelAlign="right" labelwidth='100px' width="310"></div> -->
		<!--  <div name='regcode' id="regcode" vtype="comboxfield"  multiple="ture" label="登记部门"
		labelAlign="right" dataurl="return getcode()"labelwidth='100px' width="410" ></div> -->
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