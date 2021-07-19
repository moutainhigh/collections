<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script src="/dc/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="/dc/static/script/jazz.js" type="text/javascript"></script>
<script src="/dc/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="/dc/static/script/jquery.form.js" type="text/javascript"></script>
<script src="/dc/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<script type="text/javascript" charset="UTF-8">


	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	
	function queryUrl() {
		$("#query_button").button('disable');
		
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.dengji01;
		var endtime = aa.data.dengji02;
		
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		
		
		var begintime1 = aa.data.dengji03;
		var endtime1 = aa.data.dengji04;
		
		
		if (begintime1.length != 0 && endtime1.length != 0) {
			if (begintime1 > endtime1) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime1 == endtime1) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime1.length == 0 || endtime1.length == 0) {
			alert("日期为空");
			return;
		}
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:1, columnwidth: ['62%','*']}" height="100%"
		title="电子商务消费投诉处理情况统计表">

		<div name="dengJiDate" lable="" labelwidth="0" labelalign="right"
			width="400" style="margin-left: 25px;">
			<div name="dengji01" id="dengji1a"	vtype="datefield" label="登记日期" labelwidth="120" labelalign="right"
				width="240" valuetip="开始时间……" tooltip="" rule=""></div>
			<span id="toDates1"	style="text-align: center; height: 48px;line-height: 63px; margin-left: -11px; margin-right: -11px;">至</span>
			
			<div name="dengji02" id="dengji02"	vtype="datefield" labelwidth="0" labelalign="right" width="140"
				valuetip="结束时间……" tooltip="" rule=""></div>
		</div>


		<div name="dengJiDate2" lable="" labelwidth="0" labelalign="right"	width="600" style="margin-left: 25px;">
			<div name="dengji03" id="dengji03"	vtype="datefield" label="上期登记日期" labelwidth="120" labelalign="right"
				width="240" valuetip="开始时间……" tooltip="" rule=""></div>
			<span id="toDates2"
				style="text-align: center; height: 48px;  line-height: 63px; margin-left: -11px; margin-right: -11px;">至</span>
			<div name="dengji04" id="dengji04"		vtype="datefield" labelwidth="0" labelalign="right" width="140"
				valuetip="结束时间……" tooltip="" rule=""></div>
		</div>


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