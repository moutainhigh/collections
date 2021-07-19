<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	

	function save() {
		parent.winEdit.window("close");
	}
	
	
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+'/dictionaryList/editinit.do?dmbId='+dmbId);
			//$('div[name="formpanel_edit"]').formpanel('reload');
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/query/detail.do?priPid='+priPid);
			$("#formpanel_edit").formpanel('reload', "null", function(){$('#formpanel_edit .jazz-panel-content').loading('hide');});
			queryUrl();
		}
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	function queryUrl() {
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
				'/query/list.do');
		$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var pripid = data[i]["pripid"];
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">查看</a> <a href="javascript:void(0);" onclick="update1(\''+pripid+'\')">修改</a>  <a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除</a></div>';
		}
		return data;
	}
	
	function update1(dmbId){ 
		winEdit = jazz.widget({
	  	     vtype: 'window',
		   	     frameurl: './dataEdit.jsp?dmbId='+dmbId+'&update=true',
		  			name: 'win',
		  	    	title: '修改数据项',
		  	    	titlealign: 'left',
		  	    	titledisplay: true,
		  	        width: 900,
		  	        height: 550,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true
		   		});
	}
	function del(dmbId){
		jazz.confirm("该操作将删除该代码表中所有数据，是否删除？", function(){
			/* var params = {
					url : rootPath+'/dictionaryList/delete.do',
					components: ['gridpanel'],
					callback : function(data, r, res) { 
						if (res.getAttr("back") == 'success'){
							queryDef();
							jazz.info("删除成功");
						}
					}
			} */
			jazz.info("删除成功");
			$.DataAdapter.submit(params);
		}, function(){});
	}
	function detail(dmbId){
		winEdit = jazz.widget({
		     vtype: 'window',
		     frameurl: './dataDetail.jsp?dmbId='+dmbId+'&update=true',
		  			name: 'win',
		  	    	title: '查看数据项详细',
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
		/* winEdit = jazz.widget({
	  	     vtype: 'window',
		   	     frameurl: './dataEdit.jsp?update=true',
		  			name: 'win',
		  	    	title: '新增数据项',
		  	    	titlealign: 'left',
		  	    	titledisplay: true,
		  	        width: 900,
		  	        height: 550,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true
		   		}); */
		   		window.location.href="<%=request.getContextPath()%>/page/service/dataEdit.jsp?update=true";
	}
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="200" dataurl="" >

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="服务对象名称" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='dmccb' vtype="textfield" label="表名称" labelAlign="right"
			width="400" labelwidth="130"></div>
			
		<div name='industryphy' vtype="textfield" label="表中文名"
			labelAlign="right"  width="400" labelwidth="130"></div>
			
		<div name='dmlMc' vtype="textfield" label="表类型" labelAlign="right"
			width="400" labelwidth="130"></div>
		
		<div name='a' vtype="textareafield" label="表描述" colspan="2" height="60"
			labelAlign="right"  width="850" labelwidth="130"></div>
				
			
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
    	</div>
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' ></div>
				<div name='regno' text="数据项名称" textalign="center"  width="20%"></div>
				<div name='enttype' text="中文名" textalign="center"  width="10%"></div>
				<div name='regorg' text="数据项类型（长度）" textalign="center"  width="20%"></div>
				<div name='estdate' text="主键" textalign="center"  width="15%"></div>
				<div name='industryco' text="对应代码表" textalign="center"  width="15%"></div>
				<div name='custom' text="操作" textalign="center"  width="20%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
	
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
</body>
</html>