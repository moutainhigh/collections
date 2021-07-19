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
#ooo{
float:right;
}
</style>

<script>


  	$(function(){
  		queryUrl();
 	}); 
	function queryUrl() {
		$('#reportListGrid').gridpanel('option', 'dataurl',rootPath+
				'/cognosController/queryReport_change.do');
		$('#reportListGrid').gridpanel('query', [ 'formpanel']);
	}
	
	function getDistrict(code){
		if(!code)
			return "";
		code = parseInt(code);
		switch(code){
			case 1      : return "深圳市";
			case 440303 : return "罗湖区";
			case 440304 : return "福田区";
			case 440305 : return "南山区";
			case 440306 : return "宝安区";
			case 440307 : return "龙岗区";
			case 440308 : return "盐田区";
			case 440309 : return "光明新区";
			case 440310 : return "坪山新区";
			case 440342 : return "龙华新区";
			case 440343 : return "大鹏新区";
			case 440399 : return "其他";
		}
		return "";
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
			var isValid;
			if ("${sessionScope.USER_DOOR_ROLE_CODE}"==2) {
				for(var i=0;i<data.length;i++){
					isValid = data[i]["isvalid"];
					data[i]["regcode"] = getDistrict(data[i]["regcode"]);
					if(isValid == '0'){ //报表是已启用的
						data[i]["isvalid"] = '<a>'+'<font color="green">已上传</font></a>';
					}else{//停用中
						data[i]["isvalid"] = '<a>'+'<font color="red">已上传</font></a>';
					}
				}
			}else{
				for(var i=0;i<data.length;i++){
					isValid = data[i]["isvalid"];
					data[i]["regcode"] = getDistrict(data[i]["regcode"]);
					if(isValid == '0'){ //报表是已启用的
						data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="green">已上传</font></a>';
					}else{//停用中
						data[i]["isvalid"] = '<a href="javascript:void(0);" onclick="changeValid(\''+data[i]["id"]+'\','+isValid+')"><font color="red">已上传</font></a>';
						}
					}
				}
				return data;
			}
		
		
	   function changeValid(id, isValid) {
			var msg = null;
			msg = isValid == 0 ? "是否停用?" : "是否启用?";
			if(confirm(msg)){
				$.ajax({
					   type: "POST",
					   url: rootPath+"/cognosController/changeValid.do",
					   data: {
						   id:id,
						   isValid:isValid == 0 ? 1 : 0
					   },
					   success: function(msg){
					       alert(msg);
					       queryUrl();
					   }
			      });
			}
		}
		
	   function openWindowForOne(){
		  //window.open("dajiaren_down.jsp",'center');
		  var l=(screen.availWidth-1000)/2;
			var t=(screen.availHeight-500)/2; 
		   window.open('upload_one.jsp','newprintWin','resizable=yes,width=1000,height=500,top='+t+',left='+l+',toolbar=yes,menubar=yes,location=yes,status=yes')
	   }
	

		

</script>
</head>
<body>


	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
		title="查询条件">

		<div name='reportname' vtype="textfield" label="报表名称" labelAlign="right" width="410" labelwidth="100px"></div>
	 	<div name='regcode' vtype="comboxfield" label="行政区划"
			labelAlign="right" labelwidth='100px' width="410"
			dataurl='[ {"text":"深圳市", "value":"001" },
						{"text":"罗湖区", "value":"440303" },
						{"text":"福田区", "value":"440304" },
						{"text":"南山区", "value":"440305" },
						{"text":"宝安区", "value":"440306" },
						{"text":"龙岗区", "value":"440307" },
						{"text":"盐田区", "value":"440308" },
						{"text":"光明新区", "value":"440309" },
						{"text":"坪山新区", "value":"440310" },
						{"text":"龙华新区", "value":"440342" },
						{"text":"大鹏新区", "value":"440343" },
						{"text":"其他", "value":"440399" }]'>
		</div>
		<div name='year' vtype="comboxfield" label="年份"
			dataurl='[
{"text":"2017", "value":"2017" },{"text":"2018", "value":"2018" },
{"text":"2019", "value":"2019" },{"text":"2020", "value":"2020" },
{"text":"2021", "value":"2021" },{"text":"2022", "value":"2022" }
]' labelAlign="right" labelwidth='100px' width="410"></div>
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

		<div id="ooo" vtype='toolbar' location="bottom"
			align="right" style="background:#f8f9fb">
			<div name="oneUpload" vtype="button" text="单个上传"
				icon="../query/queryssuo.png" onclick="openWindowForOne();"></div>
			<div name="moreUpload" vtype="button" text="批量上传"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	
			<div vtype="gridpanel" name="reportListGrid"  id="reportListGrid" height="400" width="100%" datarender="renderdata"
				titledisplay="true" title="报表信息" dataurl="" layout="fit" showborder="false">
<!-- 				<div name="toolbar" vtype="toolbar">								-->
<!-- 					<div name="add_button" vtype="button" text="下载" 				-->
<!-- 						icon="../query/queryssuo.png" onClick="addUser();"></div> 	-->
<!--				</div>																-->
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column">
					<div>
						<!-- 单行表头 -->
				<div name='id' text="" textalign="left" visible="false"></div>
				<div name='reportname' text="报表名称" textalign="center"></div>
				<div name='regcode' text="行政区划" textalign="center"></div>
				<div name='year' text="年份" textalign="center"></div>
				<div name='bgq' text="报告期" textalign="center"></div>
				<div name='isvalid' text="报表状态" textalign="center"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>



</body>
</html>