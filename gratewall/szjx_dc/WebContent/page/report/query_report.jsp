<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>制式报表查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
td {
	text-align: center;
}

.jazz-pagearea {
	height: 0px;
}
</style>

<script>


  	$(function(){
  		queryUrl();
 	}); 
	function queryUrl() {
		$('#reportListGrid').gridpanel('option', 'dataurl',rootPath+
				'/cognosController/queryReport.do');
		$('#reportListGrid').gridpanel('query', [ 'formpanel']);
	}
	

	function reset() {
		$("#formpanel").formpanel('reset');
	}
	
	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }

		//数据渲染函数
	   function renderdata(event,obj){
			var data = obj.data;
			for(var i=0;i<data.length;i++){
				var htm = '<a href="javascript:void(0);" onclick="report_detials(\''+data[i]["id"] + '\',\'' + data[i]["reportname"] +'\')">'
					+ "查看报表" + '</a>';
				data[i]["edit"] = htm; 
			
			}
			return data;
		}
	function report_detials(id,name){
		var path = rootPath+"/page/report/report_detial.jsp?id="+id;
		if(name.indexOf("综合") != -1 ){
			path += "&isFixed=1";
		}
		window.open (path, '_blank' ) ;
	}

		

</script>
</head>
<body>


	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
		title="查询条件">

		<div name='reportname' vtype="comboxfield" label="报表名称"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[{"text":"内资1表", "value":"内资1表" },
{"text":"内资2表", "value":"内资2表" },
{"text":"内资3表", "value":"内资3表" },
{"text":"个体1表", "value":"个体1表" },
{"text":"个体3表", "value":"个体3表" },
{"text":"综合1表", "value":"综合1表" },
{"text":"农合1表", "value":"农合1表" },
{"text":"个体4表", "value":"个体4表" },
{"text":"个体2表", "value":"个体2表" },
{"text":"外资3表", "value":"外资3表" },
{"text":"外资2表", "value":"外资2表" },
{"text":"外资1表", "value":"外资1表" }]'></div>
	<!-- 	<div name='regcode' vtype="comboxfield" label="行政区划"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[{"text":"广东省工商行政管理局", "value":"440000" },
						{"text":"深圳市", "value":"440100" },
						{"text":"深圳市", "value":"440200" },
						{"text":"深圳市", "value":"440300" },
						{"text":"深圳市", "value":"440400" },
						{"text":"深圳市", "value":"440500" },
						{"text":"深圳市", "value":"440600" },
						{"text":"深圳市", "value":"440700" },
						{"text":"深圳市", "value":"440800" },
						{"text":"深圳市", "value":"440900" },
						{"text":"深圳市", "value":"441200" },
						{"text":"深圳市", "value":"441300" },
						{"text":"深圳市", "value":"441400" },
						{"text":"深圳市", "value":"441500" },
						{"text":"深圳市", "value":"441600" },
						{"text":"深圳市", "value":"441700" },
						{"text":"深圳市", "value":"441800" },
						{"text":"深圳市", "value":"441900" },
						{"text":"深圳市", "value":"442000" },
						{"text":"深圳市", "value":"445100" },
						{"text":"深圳市", "value":"445200" },
						{"text":"深圳市", "value":"445300" }]'></div> -->
		<div name='year' vtype="comboxfield" label="年份"	 
		dataurl='[{"text":"2017", "value":"2017" },{"text":"2018", "value":"2018" },
				[{"text":"2019", "value":"2019" },{"text":"2020", "value":"2020" },
				[{"text":"2021", "value":"2021" },{"text":"2022", "value":"2022" }]' labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='bgq' vtype="comboxfield" label="报告期" labelAlign="right"
			labelwidth='100px' width="410"
			dataurl='[{"text":"1月", "value":"01-03" },
					{"text":"2月", "value":"02-03" },
					{"text":"3月", "value":"03-03" },
					{"text":"一季度季报", "value":"03-02" },
					{"text":"4月", "value":"04-03" },
					{"text":"5月", "value":"05-03" },
					{"text":"6月", "value":"06-03" },
					{"text":"二季度季报", "value":"06-02" },
					{"text":"上半年年报", "value":"06-01" },
					{"text":"7月", "value":"07-03" },
					{"text":"8月", "value":"08-03" },
					{"text":"9月", "value":"09-03" },
					{"text":"三季度季报", "value":"09-02" },
					{"text":"10月", "value":"10-03" },
					{"text":"11月", "value":"11-03" },
					{"text":"12月", "value":"12-03" },
					{"text":"四季度季报", "value":"12-02" },
					{"text":"年报", "value":"12-01" }]'></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>

			<div vtype="gridpanel" name="reportListGrid"  id="reportListGrid" height="400" width="100%" datarender="renderdata"
				titledisplay="true" title="报表信息" dataurl="" layout="fit" showborder="false">
				
<!-- 				<div name="toolbar" vtype="toolbar">  	-->
<!-- 					<div name="add_button" vtype="button" text="下载" -->
<!-- 						icon="../query/queryssuo.png" onClick="addUser();"></div> -->
<!-- 				</div>									-->
				
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column">
					<div>
						<!-- 单行表头 -->
						<div name='id' text="" textalign="left" visible="false"></div>
						<div name='reportname' text="报表名称" textalign="center"></div>
						<!-- <div name='regcode' text="行政区划" textalign="center"></div> -->
						<div name='year' text="年份" textalign="center"></div>
						<div name='bgq' text="报告期" textalign="center"></div>
						<div name='edit' text="操作" textalign="center"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
			 </div>
</body>
</html>