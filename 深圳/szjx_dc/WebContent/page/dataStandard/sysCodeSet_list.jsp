<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title></title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
 
<script>

	$(function(){
		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				query();
			}
		}); 
		query();
	});

	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function query(){
		
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/syscodelistManager/query.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["dcDmId"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="系统代码集" onclick="details(\''+data[i]["dcDmId"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["dcDmId"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
								+'</div>';
			data[i]["dcDmDm"] = '<a href="javascript:void(0)" title="查看" onclick="checkData(\''+data[i]["dcDmId"]+'\')">'+data[i]["dcDmDm"]+' </a>';
		}
		return data;
	}
	
	
	function deleteData(dcDmId){
		
		jazz.confirm("是否确认删除，删除后不可恢复", function(){
			var params = {
					url : rootPath+'/syscodelistManager/delete.do',
					params: {dcDmId:dcDmId},
					callback : function(data, r, res) { 
						if (res.getAttr("back") == 'success'){
							query();
							jazz.info("删除成功");
						}
					}
			}
			$.DataAdapter.submit(params);
		})
	
	}
	
	function details(dcDmId){
		$("iframe", parent.document).attr("src", rootPath+'/sysCodeDataList/getCodeDataPage.do?dcDmId='+dcDmId);
	}

	
	var win;
	function createNewWindow(title,frameurl,winWidth,winHeight){
		var width = 0;
		var height = 0;
		if(winWidth==""||winWidth==undefined){
			width = 680;
		}else{
			width = winWidth;
		}
		
		if(winHeight==""||winHeight==undefined){
			height = 380;
		}else{
			height = winHeight;
		}

	    win = top.jazz.widget({ 
	    	  
	        vtype: 'window', 
	        name: 'win', 
	        title: title, 
	        width: width, 
	        height: height, 
	        modal:true, 
	        visible: true ,
			showborder : true, //true显示窗体边框    false不显示窗体边
			closestate: false,
			minimizable : true, //是否显示最小化按钮
			titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
			frameurl: rootPath+frameurl
	    }); 
	}
	function addData(){
		var title = "新增系统代码集";
		var type = "add";
		var frameurl= ""+'/syscodelistManager/toSysCodeSet.do?type='+type;
		createNewWindow(title,frameurl,700,400);
	}
	
	function updateData(dcDmId){
		var title="编辑系统代码集";
		var type="update";
		var frameurl= ""+'/syscodelistManager/toSysCodeSet.do?type='+type+'&dcDmId='+dcDmId;
		createNewWindow(title,frameurl,700,400);
	}
	
	
	function checkData(dcDmId){
		var title = "查看系统代码集";
		var type = "check";
		var frameurl= ""+'/syscodelistManager/toSysCodeSet.do?type='+type+'&dcDmId='+dcDmId;
		createNewWindow(title,frameurl,700,400);
	}
	
	
	function leave(){
		win.window('close'); 
		 
	}
	
</script>
</head>
<body>
 <div class="title_nav">当前位置：资源管理> 数据标准管理> <span>系统代码集管理</span></div>
 
 	<style>
 		.container{background: #F8F9FB;}
 		.containerWap{width: 95%;margin: 0 auto;}
 	
 	</style>
    
<div class="container">
	<div class="containerWap">
 		<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['30%','35%','*']}" title="系统代码集查询" >
			
			
			<div class="labelTitle" name='dcDmDm' vtype="textfield" label="标识符" labelalign="right" width="90%" labelwidth="80"></div>
			<div class="labelTitle" name='dcDmMc' vtype="textfield" label="系统代码名集称" labelalign="left" width="90%" labelwidth="100" ></div>
			<div class="labelTitle" name='pkDcBusiObject' id='pkDcBusiObject' vtype="comboxfield" label="所属业务系统" labelalign="left" labelwidth="100" width="90%"  dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do"></div>
			
			
			<div id="toolbar" name="toolbar" vtype="toolbar" >		
			    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="query()"></div>
			    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
		  	 </div>
		</div>
	</div>
</div>
	
	
	
	<div vtype="gridpanel" name="gridpanel" width="100%" showborder="false" isshowselecthelper="true" 
		selecttype="0" datarender="renderdata()" dataurl="" layout="fit" titledisplay="false" rowselectable="false">
		<div name="toolbar" vtype="toolbar">
			<div id="add_button" name="add_button" vtype="button" align="right" text="增加" 
				iconurl="<%=contextpath %>/static/images/other/gridadd3.png" click="addData()"></div>
			
			
		</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<!-- 单行表头 -->
				<div name='dcDmId'  key="true" visible="false"></div>
				<div name='dcDmDm' text="标识符"  textalign="center" sort="true" width='15%'></div>
				<div name='dcDmMc' text="代码集名称" textalign="center" sort="true" width='17%'></div>
				<div name="pkDcBusiObject"  text="所属业务系统" textalign="center" sort="true" width="17%"
						datatype="comboxfield" dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do">
				</div>
				<div name='representation' text="表示" textalign="center" sort="true" width='15%'></div> 
				<div name='dataFrom' text="数据来源" textalign="center" sort="true"  dataurl='[{"text":"自动同步","value":"1"},{"text":"手动录入","value":"2"}]' width='17%'></div>
				<div name="custom" text="操作" textalign="center" ></div>
			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->

		<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	</div>

 
</body>
</html>
