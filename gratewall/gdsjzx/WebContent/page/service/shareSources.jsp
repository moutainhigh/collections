<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>共享资源查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">

.jazz-pagearea{
	height: 0px;
}

.cardli{
	padding:12px 0px 10px 0px;
	list-style-type:none;
	height: 5px;
	font-style: normal;
	font-weight: bolder;
	margin-left: 10px; 
	color:black;
	line-height: 5px;
}

#card{
	width:100%;
	margin-left:auto; margin-right:auto; 
	background-color:white; 
	border: 1px solid #CCBBFF; 
	position:relative;
	float:none;
	height:100%;
}
.cardclass{
	display: inline-block;
	 *zoom: 1;
    *display: inline;
	width:257px;
	margin:10px 7px;
	border: 1px solid #CCBBFF; 
	height:175px;
	background-color: #E8EAEC;
	
}
.color {
    background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top , #E7E9EC, #E7E9EC) repeat scroll 0 0;
    border: 1px solid #0076a3;
    color: #F8F9FB;
}
.small {
    font-size: 11px;
}
.div {
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    cursor: pointer;
    font: 14px/100% Arial,Helvetica,sans-serif;
    outline: medium none;
    text-decoration: none;
    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.3);
    vertical-align: baseline;
}
.classspan{
		text-align:center;
		float:right;
		margin-right:15%;
}
.jazz-paginator jazz-paginator-ext{
	bottom:opx;
	position: absolute;
}

</style>
<script>
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
		{ name:"主体库", open:true,
			children: [
				{ name:"12315主题库",
					children: [
						{ name:"违法广告"},
						{ name:"网格"},
						{ name:"TcaseInfo"}
					]},
				{ name:"产商品主题",isParent:true},
				{ name:"人员主题",isParent:true}
			]}
	];
$(document).ready(function(){
	$("#treeDemo").tree({setting: setting, data: zNodes});
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/shareResource/querysr.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
});

function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/shareResource/querysr.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}


function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		data[i]['tablecode'] = '<a href="javascript:void(0);" onclick="detail(\''+data[i]['gxzyid']+'\')">'+data[i]['tablecode']+'</a>';
	
		if(data[i]['isstart']==0){
 			data[i]['isstart']='启动';
	 	}else{
	 		data[i]['isstart']='停用';
	 	}
	}
 	
 	/* data[i]['tabletype']==
 	data[i]['state']= ''; */
	return data;
}

function start(rsid){
			$.ajax({
				   type: "POST",
				   url: "/gdsjzx/shareResource/querytstate.do",
				   data: "state=0&rsid="+rsid,
				   success: function(msg){
				   	$('#'+rsid).empty();
				     var htmlstart='状态:启用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否:<a id="bindstate2'+rsid+'" href="javascript:void(0);" onclick="stop(\''+rsid+'\')">停用 </a>';
				     	$('#'+rsid).append(htmlstart);
				     	$('#bindstate2'+rsid).bind('click',function(event){
								stop(rsid);
					    	event.stopPropagation();
					});
				   }
	});
	}
function stop(rsid){
	$.ajax({
				   type: "POST",
				   url: "/gdsjzx/shareResource/querytstate.do",
				   data: "state=1&rsid="+rsid,
				   success: function(msg){
				   	$('#'+rsid).empty();
				   	 var htmlstop='状态:停用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否:<a id="bindstate1'+rsid+'" href="javascript:void(0);" onclick="start(\''+rsid+'\')">启用</a>';
				     $('#'+rsid).append(htmlstop);
				     $('#bindstate1'+rsid).bind('click',function(event){
								start(rsid);
					    	event.stopPropagation();
					});
				   }
		});
}

function detail(rsid){
	winEdit = jazz.widget({
	     vtype: 'window',
	   	     frameurl: '<%=request.getContextPath()%>/page/service/shareResourceDetail.jsp?rsid='+rsid,
	  			name: 'win',
	  	    	title: '详情',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
function add(){
		window.location.href="<%=request.getContextPath()%>/page/service/shareSourcesAdd.jsp";
}

function reset() {
	$("#formpanel").formpanel('reset');
}

</script>
</head>
<body style="overflow:none;"> 
<div>位置：数据服务>共享资源目录</div>

	<div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" datarender="renderdata()"
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="150" title="查询条件">
		<div name='tablecode' vtype="textfield" label="表名" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='tablename' vtype="textfield" label="表中文名" labelAlign="right" labelwidth='100px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid"  width="100%"  id='zzjgGrid' datarender="renderdata()"
titledisplay="true" title="共享资源列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="98%"  >
			<div>
				<div name='gxzyid' width="0%"></div>
				<div name='subject' text="业务模块" textalign="center"  width="20%"></div>
				<div name='tablecode' text="库表名" textalign="center"  width="20%"></div>
				<div name='tablename' text="库表中文名" textalign="center"  width="20%"></div>
				<div name='lastmodifyperson' text="最后修改人" textalign="center"  width="15%"></div>
				<div name='dataquantity' text="数据量" textalign="center"  width="10%"></div>
				<div name='modifytime' text="更新时间" textalign="center"  width="15%"></div>
				<!-- <div name='description' text="描述" textalign="center" width="42%" ></div> -->
				<!-- <div name='caozuo' text="操作" textalign="center"  width="24%"></div> -->
			</div>
		</div>
		<!-- 分页 -->
		<div vtype="gridtable" name="grid_table" ></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	</div>
</body>
</html>