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

	//http://localhost:8088/sjbd/datacompre/returnHistoryExsists.do?entityNo=c7f9f0e9-013d-1000-e000-184e0a0e0115

  	$(function(){
  		queryUrl();
 	}); 
	function queryUrl() {
	
		$('#reportListGrid').gridpanel('option', 'dataurl',rootPath+
				'/cognosController/queryNh.do');
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
				var htm = '<a href="javascript:void(0);" onclick="report_detials(\''+data[i]["id"]+'\')">'
					+ "查看报表" + '</a>';
				data[i]["edit"] = htm; 
			
			}
			return data;
		}
	function report_detials(id){
		window.open (rootPath+"/page/report/report_detial.jsp?id="+id, '_blank' ) ;
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
			dataurl='[{"text":"农合1表", "value":"农合1表" }
]'></div> 



<div name='regcode' vtype="comboxfield" label="行政区划"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[{"text":"广东省工商行政管理局", "value":"440000" },
{"text":"广州市工商行政管理局", "value":"440100" },
{"text":"韶关市工商行政管理局", "value":"440200" },
{"text":"深圳市工商行政管理局", "value":"440300" },
{"text":"珠海市工商行政管理局", "value":"440400" },
{"text":"汕头市工商行政管理局", "value":"440500" },
{"text":"佛山市工商行政管理局", "value":"440600" },
{"text":"江门市工商行政管理局", "value":"440700" },
{"text":"湛江市工商行政管理局", "value":"440800" },
{"text":"茂名市工商行政管理局", "value":"440900" },
{"text":"肇庆市工商行政管理局", "value":"441200" },
{"text":"惠州市工商行政管理局", "value":"441300" },
{"text":"梅州市工商行政管理局", "value":"441400" },
{"text":"汕尾市工商行政管理局", "value":"441500" },
{"text":"河源市工商行政管理局", "value":"441600" },
{"text":"阳江市工商行政管理局", "value":"441700" },
{"text":"清远市工商行政管理局", "value":"441800" },
{"text":"东莞市工商行政管理局", "value":"441900" },
{"text":"中山市工商行政管理局", "value":"442000" },
{"text":"潮州市工商行政管理局", "value":"445100" },
{"text":"揭阳市工商行政管理局", "value":"445200" },
{"text":"云浮市工商行政管理局", "value":"445300" }]'></div>
		<div name='year' vtype="comboxfield" label="年份"
			dataurl='[{"text":"2016", "value":"2016" },
{"text":"2017", "value":"2017" },{"text":"2018", "value":"2018" }
]'
			labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='bgq' vtype="comboxfield" label="报告期" labelAlign="right"
			labelwidth='100px' width="410"
			dataurl='[{"text":"1月", "value":"01-03" },
{"text":"2月", "value":"02-03" },
{"text":"3月", "value":"03-03" },
{"text":"4月", "value":"04-03" },
{"text":"5月", "value":"05-03" },
{"text":"6月", "value":"06-03" },
{"text":"7月", "value":"07-03" },
{"text":"8月", "value":"08-03" },
{"text":"9月", "value":"09-03" },
{"text":"10月", "value":"10-03" },
{"text":"11月", "value":"11-03" },
{"text":"12月", "value":"12-03" },
{"text":"年报", "value":"0" },
{"text":"上半年", "value":"06-1" }]'></div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"	icon="../query/queryssuo.png" onclick="queryUrl();"></div>
				
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>

	

			<div vtype="gridpanel" name="reportListGrid"  id="reportListGrid" height="400" width="100%" datarender="renderdata"
				titledisplay="true" title="制式报表信息" dataurl="" layout="fit" showborder="false">
				<div name="toolbar" vtype="toolbar">
<!-- 					<div name="add_button" vtype="button" text="下载" -->
<!-- 						icon="../query/queryssuo.png" onClick="addUser();"></div> -->
				</div>
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column">
					<div>
						<!-- 单行表头 -->
						<div name='id' text="id" textalign="left" visible="false"></div>
						<div name='reportname' text="报表名称" textalign="center"></div>
						<div name='regcode' text="行政区划" textalign="center"></div>
						<div name='year' text="年份" textalign="center"></div>
						<div name='bqg' text="报告期" textalign="left"></div>
						<div name='edit' text="操作" textalign="center"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</body>
</html>