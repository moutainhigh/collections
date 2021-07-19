<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>对外出资信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/report/yreport_czxx.js"></script>
 --%>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>
	<script type="text/javascript">
	$(function(){
		var entityNo=getUrlParam("entityNo");
  		var sourceflag=getUrlParam("sourceflag");
 		var year=getUrlParam("year");
  		var priPid=getUrlParam("priPid");
  		//var economicproperty=getUrlParam("economicproperty");
  		//获取传递过来的参数，进行初始化请求
  		if(entityNo!=null){
  			$("#jbxxGrid").gridpanel("hideColumn", "modfydate");
  			$("#jbxxGrid").gridpanel("showColumn", "approvedate");
 			queryHistory(entityNo,priPid,sourceflag,year);
  		}
 	});
	function queryHistory(entityNo,priPid,sourceflag,year){
		$('#jbxxGrid').gridpanel('option', 'dataurl',rootPath+
		'/report/detail.do?flag='+entityNo+'&priPid='+priPid+'&sourceflag='+sourceflag+'&year='+year);
		$('#jbxxGrid').gridpanel('query', [ 'formpanel']);
	}
	function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

	function handler(){
						
	}
							 
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var lisubconam = data[i]["lisubconam"];
		var liacconam = data[i]["liacconam"];
		data[i]["lisubconam"] = '<div class="jazz-grid-cell-inner">'+lisubconam+'万元</div>';
		data[i]["liacconam"] = '<div class="jazz-grid-cell-inner">'+liacconam+'万元</div>';
	}
	return data;
}							 

</script>					 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="95%" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="投资人出资信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
			<!-- 股东及出资信息表 -->
			<div name='invid' text="投资人主键" textalign="center"  width="0%"></div>
			<div name='invname' text="投资人" textalign="center"  width="20%"></div>
			<div name='lisubconam' text="认缴出资" textalign="center" width="13%" ></div>
			<div name='liacconam' text="实缴出资" textalign="center"  width="13%"></div>
			<div name='subcondate' text="出资时间" textalign="center" width="13%"></div>
			<div name='subconformCn' text="出资方式" textalign="center" width="14%"></div>
			<div name='accondate' text="认缴日期" textalign="center" width="13%"></div>
			<div name='acconformCn' text="认缴方式" textalign="center"  width="14%"></div>
			
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>