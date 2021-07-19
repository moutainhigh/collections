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
		//$("#formpanel_edit").formpanel('option', 'disabled', false);
		$('div[name="username"]').textfield('option', 'disabled', true);
		$('div[name="operatetime"]').datefield('option', 'disabled', true);
		$('div[name="ip"]').textfield('option', 'disabled', true);
		$('div[name="url"]').textareafield('option', 'disabled', true);
		$('div[name="req"]').textareafield('option', 'disabled', true);
		if(priPid != null){
			//$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+'/dictionaryList/editinit.do?dmbId='+dmbId);
			//$('div[name="formpanel_edit"]').formpanel('reload');
			$('#formpanel_edit .jazz-panel-content').loading();
			$("#formpanel_edit").formpanel('option', 'dataurl',rootPath+'/blog/logDetail.do?logid='+priPid);
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
		height="100%" dataurl="" ><!-- readonly="true" -->

		<div name='logid' vtype="hiddenfield" label="ID" labelAlign="right" width="400" labelwidth="130"></div>
		<div name='username' vtype="textfield" label="操作人" labelAlign="right" width="400" labelwidth="130"></div>
			
		<div name='operatetime' vtype="datefield" label="操作时间" labelAlign="right" width="400" labelwidth="130"></div>
		
		<div name='ip' vtype="textfield" label="IP" labelAlign="right" width="400" labelwidth="130"></div>
		
		<div name="url" vtype="textareafield" label="访问地址" labelAlign="right" height="60"
			width="850" labelwidth="130" colspan="2"></div>
		
		<div name="req" vtype="textareafield" label="参数信息" labelAlign="right" height="100"
			width="850" labelwidth="130" colspan="2"></div>

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>