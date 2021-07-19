<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>表管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	

	function save() {
		parent.winEdit.window("close");
		/* var dmbId = $("div[name='dmbId']").textfield('getValue');
		var dmbMc = $("div[name='dmbMc']").textfield('getValue');
		var sjccLx = $("div[name='sjccLx']").comboxfield('getValue');
		var dmbLx = $("div[name='dmbLx']").comboxfield('getValue');
		var hcLx = $("div[name='hcLx']").comboxfield('getValue');
		var qyBj = $("div[name='qyBj']").comboxfield('getValue');
		if(dmbId == ''||dmbMc == ''||sjccLx==''||dmbLx==''||hcLx==''||qyBj==''){
			jazz.info("有必填选项未填写，添加失败");
		}else{
			var sql = $("div[name='sql']").textareafield('getValue');
			var sqlnew = encodeURI(encodeURI(sql));
			sqlnew = sqlnew.replace(/'/g, "#");
			$("div[name='sql']").textareafield('setValue',sqlnew);
			var params = {
				url : rootPath+'/dictionaryList/save.do?update='+update,
				components : [ 'formpanel_edit' ],
				
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						parent.queryDef();
						parent.winEdit.window("close");
						jazz.info("保存成功");
					} else if (res.getAttr("back") == 'sameId'){
						$("div[name='sql']").textfield('setValue',sql);
						jazz.error("代码表ID已经存在，添加失败");
					} else if (res.getAttr("back") == 'empty'){
						jazz.error("sql不能为空");
					} else if (res.getAttr("back") == 'sql'){
						jazz.error("通用表和独立表不能有自定义SQL");
					}else{
						$("div[name='sql']").textfield('setValue',sql);
						jazz.error("sql错误");
					}
				}
			};
			$.DataAdapter.submit(params);
		} */
	}
	
	/*function initData(res){
		var dmbId = res.getAttr("dmbId");
		$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
		'/page/editinit.do?dmbId='+dmbId);
		$('div[name="formpanel_edit"]').formpanel('reload');
	}*/
	
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
		}
		queryUrl();
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	
	
	function queryUrl() {
		$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
			$("#zzjgGrid").gridpanel("showColumn", "modfydate");
		$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
				'/query/list.do');
		$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
	}
	function rowclick(event,data){
		window.location.href="<%=request.getContextPath()%>/page/general/detail.jsp?priPid="+data.pripid;
	}
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var pripid = data[i]["pripid"];
			data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除</a></div>';
		}
		return data;
	}
	function del(pripid){
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
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="auto" dataurl="">

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			 width="400" labelwidth="130"></div>
			
		<div name='regno1' vtype="textfield" label="主表信息" colspan="2" readonly="true"
			 width="850" labelwidth="130" labelAlign="left" draggable="false"></div>
		<div name='regno' vtype="textfield" label="数据源" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='entname' vtype="comboxfield" label="名称"
			labelAlign="right"  width="400" labelwidth="130"
			dataurl='[{"text":"登记","value":"1"},{"text":"执法","value":"2"}]'></div>
		<div name='dmccb' vtype="textfield" label="业务系统" labelAlign="right"
			width="400" labelwidth="130"></div>
		<div name='dmlMc' vtype="textfield" label="选择字段" labelAlign="right"
			width="400" labelwidth="130"></div>
			
		<div name='industryco' vtype="textfield" label="请选择关联表信息" readonly="true"
			width="850" labelwidth="130" labelAlign="left" colspan="2" ></div>
			
		<div name='industryphy' vtype="comboxfield" label="数据源"
			labelAlign="right" width="400" labelwidth="130"
			dataurl='[{"text":"业务","value":"1"},{"text":"系统","value":"2"},{"text":"历史","value":"3"}]'></div>
		<div name='servicestate' vtype="comboxfield" label="关联表" labelAlign="right"
			width="400" labelwidth="130" ></div>
		<div name='regorg' vtype="comboxfield" label="关联字段" 
			labelAlign="right" width="400" labelwidth="130" ></div>
		<div name='regorg1' vtype="textareafield" label="注释" colspan="2"
			labelAlign="right" width="850" labelwidth="130" height="60"></div>
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/save.png" click="save()"></div>
			<div id="btn2" name="btn2" vtype="button" text="返回"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
	
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="99%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid'  width="1%"></div>
				<div name='regorg' text="数据源名" textalign="center"  width="15%"></div>
				<div name='enttype' text="业务系统" textalign="center"  width="15%"></div>
				<div name='industryco' text="物理表" textalign="center"  width="15%"></div>
				<div name='regno' text="物理表中文名" textalign="center"  width="10%"></div>
				<div name='servicestate' text="表类型" textalign="center"  width="10%"></div>
				<div name='estdate' text="管理人" textalign="center"  width="10%"></div>
				<div name='industryphy' text="管理日期" textalign="center"  width="10%"></div>
				<div name='custom' text="操作" textalign="center"  width="10%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>