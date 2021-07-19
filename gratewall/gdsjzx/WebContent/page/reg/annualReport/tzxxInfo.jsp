<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>对外投资信息</title>
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
  			$("#jbxxGrid1").gridpanel("hideColumn", "modfydate");
  			$("#jbxxGrid1").gridpanel("showColumn", "approvedate");
 			queryHistory(entityNo,priPid,sourceflag,year);
  		}
 	});
	function queryHistory(entityNo,priPid,sourceflag,year){
		//(parseInt(entityNo)+1)
		$('#jbxxGrid1').gridpanel('option', 'dataurl',rootPath+
				'/report/detail.do?flag='+entityNo+'&priPid='+priPid+'&sourceflag='+sourceflag+'&year='+year);
		$('#jbxxGrid1').gridpanel('query', [ 'formpanel']);
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
	/* for(var i=0;i<data.length;i++){
		var lisubconam = data[i]["lisubconam"];
		var liacconam = data[i]["liacconam"];
		data[i]["lisubconam"] = '<div class="jazz-grid-cell-inner">'+lisubconam+'万元</div>';
		data[i]["liacconam"] = '<div class="jazz-grid-cell-inner">'+liacconam+'万元</div>';
	} */
	return data;
}							 

</script>					 
</head>
<body >
  	<div vtype="gridpanel" name="jbxxGrid1" height="95%" width="100%"  id='jbxxGrid1' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="对外投资信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
				<!--对外投资信息表 -->
			<div name='entname' text="企业名称" textalign="center"  width="18%"></div>
			<div name='regno' text="注册号" textalign="center" width="18%"></div>
			<div name='lerep' text="法定代表人" textalign="center" width="13%"></div>
			<div name='tel' text="电话" textalign="center" width="15%"></div>
			<div name='dom' text="地址" textalign="center" width="36%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
</body>
</html>