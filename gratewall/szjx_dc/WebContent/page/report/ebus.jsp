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
	<style>
	body{overflow-x:hidden; }
	#tableData td{border: solid 1px #a0c6e5; height: 20px;}
	
	</style>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:1, columnwidth: ['62%','*']}" height="250"
		title="电子商务投诉情况统计表">

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
			<div name="query_button" vtype="button" text="查询"	icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"	icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
	
	<div id="tableData">
	
	</div>
	
	
	<script type="text/javascript" charset="UTF-8">


	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	
	function queryUrl() {
		$("div[name='query_button']").button('disable');
		$("div[name='query_button']").removeAttr("onclick");
		
		
		$("div[name='query_button']").button('enable');
		$("div[name='query_button']").attr("onclick","queryUrl()");
		var aa = $("#formpanel").formpanel('getValue');
		var data1 = aa.data.dengji01;
		var data2 = aa.data.dengji02;
		var data3 = aa.data.dengji03;
		var data4 = aa.data.dengji04;
		
		if (data1.length == 0) {
			alert("登记开始日期为空");
			return;
		}
		
		if (data2.length == 0) {
			alert("登记结束日期为空");
			return;
		}
		
		
		
		 if (data1.length != 0 && data2.length != 0) {
			if (data1 > data2) {
				alert("初始日期大于截止日期");
				return;
			}
			if (data1 == data2) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		
		 
		 if(data3.length != 0){
			 if(data4.length == 0){
				 alert("上期登记截止日期不能为空");
			 }
			 
			 if (data3.length != 0 && data4.length != 0) {
					if (data3 > data4) {
						alert("上期登记初始日期大于上期登记截止日期");
						return;
					}
					if (data3 == data4) {
						alert("上期登记初始日期等于上期登记截止日期");
						return;
					}
				}
		 }
		 
		 
		
			
		
		

		
		
		$.ajax({
			url:"../../bus/ebus.do",
			type:'post',
			dataType:'json',
			data:{
				data1:data1,
				data2:data2,
				data3:data3,
				data4:data4,
			},
			
			beforeSend:function(data){
				$("div[name='query_button'] .button-text").html("查询中...");
			},
			success:function(data){
				$("#tableData").empty();
				$("div[name='query_button'] .button-text").html("查询");
				var data = data.data[0].data;
				if(data=="0"){
					return;
				}else{
					var html = "<table width='100%' cellspacing='1' border='0' cellpadding='0'>";
					html+="<tr><td>涉及客体类型</td>"+
					"<td>电子商务</td>"+
					"<td>邮购</td>"+
					"<td>电话购物</td>"+
					"<td>电视购物</td>"+
					"<td>交易品台类</td>"+
					"<td>应用类</td>"+
					"<td>服务类</td>"+
					"<td>互联网门户</td>"+
					"<td>其他</td>"+
					"<td>占投诉登记总量百分比()%)</td>"+
					"<td>上一时间段数据</td>"+
					"<td>上一时间段增减</td>"+
					"<td>去年同期数据</td>"+
					"</tr>";
					for(var i in data){
						html+="<tr>"+
						"<td>"+data[i].a+"</td>"+
						"<td>"+data[i].b1+"</td>"+
						"<td>"+data[i].b2+"</td>"+
						"<td>"+data[i].b3+"</td>"+
						"<td>"+data[i].b4+"</td>"+
						"<td>"+data[i].c1+"</td>"+
						"<td>"+data[i].c2+"</td>"+
						"<td>"+data[i].c3+"</td>"+
						"<td>"+data[i].c4+"</td>"+
						"<td>"+data[i].c5+"</td>"+
						"<td>"+data[i].cnt+"</td>"+
						"<td>"+data[i].laseCnt+"</td>"+
						"<td>"+data[i].total+"</td>"+
						"<td>"+data[i].total+"</td>"+
						"</tr>";
					}
					html+="</table>";
					$("#tableData").append(html);
				}
			},
			error:function(data){
				$("div[name='query_button'] .button-text").html("查询");
				
			}
			
		});
	}
	
	
	
	function isEmpty(val) {
		val = $.trim(val);
		if (val == null)
			return true;
		if (val == undefined || val == 'undefined')
			return true;
		if (val == "")
			return true;
		if (val.length == 0)
			return true;
		if (!/[^(^\s*)|(\s*$)]/.test(val))
			return true;
		return false;
	}

	function isNotEmpty(val) {
		return !isEmpty(val);
	}

</script>
</body>
</html>