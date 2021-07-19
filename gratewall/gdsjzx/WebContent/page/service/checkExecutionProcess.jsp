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
	});
	
	function back() {
		parent.winEdit.window("close");
	}
	function yes(){
		window.location.href="<%=request.getContextPath()%>/page/service/inspectionResults.jsp";
	}
</script>
</head>
<body>
	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='pripid' width="0" ></div>
				<div name='regno' text="检查点信息" textalign="center"  width="20%"></div>
				<div name='entname' text="执行状态" textalign="center"  width="20%"></div>
				<div name='regorg' text="执行时间" textalign="center"  width="20%"></div>
				<div name='entname' text="问题点" textalign="center"  width="20%"></div>
				<div name='regorg' text="检查点" textalign="center"  width="20%"></div>
			</div>
		</div>
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="确定" click="yes()"></div>
    	</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table" ></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>