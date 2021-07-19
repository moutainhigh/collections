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
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		height="495" dataurl="" >

		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="监控对象" labelAlign="right"
			 width="400" labelwidth="130" readonly="true"></div>
		<div name='dmccb' vtype="textfield" label="监控指标" labelAlign="right"
			width="400" labelwidth="130" readonly="true"></div>
			
		<div name='industryphy' vtype="comboxfield" label="实际值（阀值）" 
		labelAlign="right"  width="400" labelwidth="130" readonly="true"></div>
			
		<div name='dmlMc' vtype="textfield" label="警情发生时间" labelAlign="right" readonly="true"
			width="400" labelwidth="130"></div>
		
		<div name="sql" vtype="textareafield" label="发生原因" labelAlign="right" height="60"
			width="850" labelwidth="130" colspan="2"></div>
			
		<div name="sql1" vtype="textareafield" label="处理方法" labelAlign="right" height="60"
			width="850" labelwidth="130" colspan="2"></div>
			
		<div name="sql2" vtype="textfield" label="处理耗时" labelAlign="right" height="60"
			width="850" labelwidth="130" defaultvalue="10分" colspan="2"></div>
		<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
			width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
			示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn1" name="btn1" vtype="button" text="保存"
				icon="../../../style/default/images/fh.png" click="back()"></div>
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>