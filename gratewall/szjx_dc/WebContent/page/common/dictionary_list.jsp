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

	$(function() {
		
		$('div[name="pkSysIntegration"]').comboxfield('option','dataurl',rootPath+'/dictionaryList/systemList.do');
		$('div[name="pkSysIntegration"]').comboxfield('reload');
		queryDef();
	});
	
	function queryDef(){
		
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/dictionaryList/query.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	var winEdit;
	function addData(){
		
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/dictionaryList/add.do',
	  			name: 'win',
	  	    	title: '新增代码表',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : true, //true显示窗体边框    false不显示窗体边
	  		   // maximize : true, //默认最大化展开
	  		    closestate : true, //true关闭   false隐藏 
	  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 500, 
	   		});
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["pkCodeTableManager"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="查看" onclick="details(\''+data[i]["codeTableChName"]+'\',\''+data[i]["codeTableEnName"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["pkCodeTableManager"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
								+'</div>';
		}
		return data;
	}
	
	function deleteData(pkid){
		
		
			jazz.confirm("是否确认删除，删除后不可恢复", function(){
				var params = {
						url : rootPath+'/dictionaryList/delete.do',
						params: {pkId:pkid},
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								queryDef();
								jazz.info("删除成功");
							}
						}
				}
				$.DataAdapter.submit(params);
			}, function(){})
		
	}
	
	
	
	function updateData(pkid){
		
			winEdit = top.jazz.widget({
		   	     vtype: 'window',
		   	 
		   	     frameurl: rootPath+'/dictionaryList/update.do?pkCodeTableManager='+pkid,
		  			name: 'win',
		  	    	title: '修改代码表',
		  	    	titledisplay: true,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true,
		  	        minimizable : true, //是否显示最小化按钮
		  		    showborder : false, //true显示窗体边框    false不显示窗体边
		  		    //maximize : true, //默认最大化展开
		  		    closestate : true, //true关闭   false隐藏 
		  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
		  		  	width: 750, 
		          	height: 500, 
		   		});
			 
			 

		
	}
	
	function details(cname,ename){
		
		$("iframe", parent.document).attr("src", rootPath+'/dictionaryList/details.do?codeTableEnName='+ename+'&codeTableChName='+cname);
	
			/* winEdit = jazz.widget({
		   	     vtype: 'window',
		   	     frameurl: rootPath+'/dictionaryList/details.do?codeTableEnName='+ename+'&codeTableChName='+cname,
		  			name: 'win',
		  	    	title: '代码表:'+ename+' '+cname,
		  	    	titlealign: 'left',
		  	    	titledisplay: true,
		  	        width: '100%',
		  	        height: '100%',
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true
		   		}); */
		
	}
	
	function leave(){
		winEdit.window('close'); 
		 
	}

</script>
</head>
<body>
 <div class="title_nav">当前位置：公共管理 > <span>代码集管理</span></div>
 
 
    
 	<div vtype="formpanel" id="formpanel" name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="代码表查询" >

		<div name='pkCodeTableManager' vtype="hiddenfield" label="主键" labelalign="right" width="80%" labelwidth="100"></div> 
		<div name='codeTableChName' vtype="textfield" label="代码表中文名" labelalign="right" width="80%" labelwidth="200"></div>
		<div name='codeTableEnName' vtype="textfield" label="代码表英文名" labelalign="right" width="80%" labelwidth="200" ></div>
		<!-- <div name='codeTableDesc' vtype="textfield" label="表描述" labelalign="right" width="80%" labelwidth="100"></div>
		<div name='pkSysIntegration' vtype="comboxfield" label="所属子系统" labelalign="right" width="80%" labelwidth="100" multiple="false"></div>
		<div name='cacheType' vtype="comboxfield" label="缓存类型" labelalign="right" width="80%" labelwidth="100" 
			dataurl='[{"text":"存储到内存","value":"0"},{"text":"存储到数据库","value":"1"}]'></div> -->
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="queryDef()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
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
				<div name='pkCodeTableManager' key="true" visible="false" ></div>
				<div name='codeTableChName' text="代码表中文名"  textalign="center" sort="true" width='20%'></div>
				<!-- <div name='effectiveMarker' text="有效标记"  visible="false" width='0'></div> -->
				<div name='codeTableEnName' text="代码表英文名" textalign="center" sort="true" width='20%'></div>
				<div name='systemName' text="所属子系统" textalign="center" sort="true" width='15%'></div>
				<!-- <div name='codeTableDesc' text="表描述" textalign="center" sort="true" width='12%'></div> -->
				<div name='cacheType' text="缓存类型" textalign="center" sort="true" width='15%' datatype='comboxfield' 
					dataurl='[{"text":"存储到内存","value":"0"},{"text":"存储到数据库","value":"1"}]'></div>	
				<!-- <div name='codeColumn' text="code字段" textalign="center" sort="true" width='13%'></div>
				<div name='valueColumn' text="value字段" textalign="center" sort="true" width='12%'></div> -->
				<div name='effectiveMarker' text="状态" textalign="center" sort="true" width='14%' datatype='comboxfield'
					dataurl='[{"text":"正常","value":"0"},{"text":"禁用","value":"1"}]'></div> 
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
