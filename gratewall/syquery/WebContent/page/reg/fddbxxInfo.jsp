<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>标记信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<style>
	.class_td1{
			width:15%;
			height:5%;
			padding:4px;
	}
	.class_td2{
			width:35%;
			height:5%;
	}
	
	.class_td3{
			width:2%;
			height:5%;
			padding:4px;
	}
	
	.class_td4{
			width:15%;
			height:5%;
			padding:4px;
	}
	
	.class_td5{
			width:35%;
			height:5%;
	}
	.class_hg{
		height:5%;
		padding:4px;
	}
</style>

<script type="text/javascript">
$(function(){
  		var entityNo=getUrlParam("entityNo");
  		var priPid=getUrlParam("priPid");
  		//获取传递过来的参数，进行初始化请求
  		if(entityNo!=null){
  			$("#jbxxGrid1").gridpanel("hideColumn", "modfydate");
  			$("#jbxxGrid1").gridpanel("showColumn", "approvedate");
  			$("#jbxxGrid2").gridpanel("hideColumn", "modfydate");
  			$("#jbxxGrid2").gridpanel("showColumn", "approvedate");
 			queryHistory(entityNo,priPid);
  		}
 	}); 
 	
	function queryHistory(entityNo,priPid){
		$('#jbxxGrid1').gridpanel('option', 'dataurl',rootPath+
		'/reg/detail.do?flag='+entityNo+'&priPid='+priPid);
		$('#jbxxGrid1').gridpanel('query', [ 'formpanel']);
		
		$('#jbxxGrid2').gridpanel('option', 'dataurl',rootPath+
		'/reg/detail.do?flag='+(parseInt(entityNo)+1)+'&priPid='+priPid);
		$('#jbxxGrid2').gridpanel('query', [ 'formpanel']);
	}

		 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
     
     
     	//数据渲染函数
	   function renderdata(data){
				for(var i=0;i<data.length;i++){
					data[i]["@toolsopration"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'+data[i]["name"]+'</a></div>';
					data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'+data[i]["name"]+'</a></div>';
				}
				return data;
		    }
</script>		 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid1" height="50%" width="100%"  id='jbxxGrid1' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="法定代表人信息"  layout="fit" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 法定代表人信息 -->
			<div name='entitylegalrepresentative' text="委派方" textalign="center"  width="20%"></div>
			<div name='appounit' text="任命单位" textalign="center"  width="20%"></div>
			<div name='historyinfoid' text="历史资料编号" textalign="center"  width="20%"></div>
			<div name='personid' text="公民编号" textalign="center"  width="20%"></div>
			<div name='lerep' text="法定代表人" textalign="center"  width="20%"></div>

	</div>
	
	
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<!-- 卡片 -->
	<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
	<!-- 分页 -->
	<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> -->
	
</div>

<div vtype="gridpanel" name="jbxxGrid2" height="50%" width="100%"  id='jbxxGrid2' dataloadcomplete=""  datarender="renderdata()"
				titledisplay="true" title="股权冻结出质信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
	<div>
	<!-- 冻结股东信息 	-->	
			<div name='corentname' text="相关企业名称" textalign="center"  width="10%"></div>
			<div name='exestate' text="执行状态" textalign="center"  width="7.5%"></div>
			<div name='froam' text="冻结金额" textalign="center"  width="7.5%"></div>
			<div name='sharfroprop' text="股权冻结比例" textalign="center"  width="10%"></div>
			<div name='froauth' text="冻结机关" textalign="center"  width="7.5%"></div>
			<div name='frofrom' text="冻结起始日期" textalign="center"  width="10%"></div>
			<div name='froto' text="冻结截止日期" textalign="center"  width="10%"></div>
			<div name='frodocno' text="冻结文号" textalign="center"  width="7.5%"></div>
			<div name='thawauth' text="解冻机关" textalign="center"  width="7.5%"></div>
			<div name='thawdocno' text="解冻文号" textalign="center"  width="7.5%"></div>
			<div name='thawdate' text="解冻日期" textalign="center"  width="7.8%"></div>
			<div name='frozsign' text="冻结标志" textalign="center"  width="7.5%"></div>
	</div>
	</div>
</div>




</body>
</html>