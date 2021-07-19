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
		height="100%" dataurl="" readonly="true">

		<div name='logid' vtype="hiddenfield" label="ID" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='servicename' vtype="textfield" label="服务名称" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='serviceobject' vtype="textfield" label="服务对象" labelAlign="right"
			width="400" labelwidth="130"></div>
			
		<div name='servicetype' vtype="comboxfield" label="服务类型" dataurl="[{checked: true,value: '1',text: '内部系统'},{value: '2',text: '外部系统'}]"
			labelAlign="right"  width="400" labelwidth="130"></div>
		<div name='runstate' vtype="comboxfield" label="执行状态" dataurl="[{checked: true,value: '1',text: '成功'},{value: '2',text: '失败'}]"
			labelAlign="right"  width="400" labelwidth="130"></div>
			
		<div name='starttime' vtype="textfield" label="服务开始时间" labelAlign="right"
			width="400" labelwidth="130"></div>
		<div name='endtime' vtype="textfield" label="服务结束时间" labelAlign="right"
			width="400" labelwidth="130"></div>
			
		<div name='countsum' vtype="textfield" label="采集数量" labelAlign="right"
			width="400" labelwidth="130"></div>
		
		<div name="url" vtype="textareafield" label="访问地址" labelAlign="right" height="60"
			width="850" labelwidth="130" colspan="2"></div>
		
		<div name="req" vtype="textareafield" label="参数信息" labelAlign="right" height="100"
			width="850" labelwidth="130" colspan="2"></div>
			
		<div name="res" vtype="textareafield" label="返回信息" labelAlign="right" height="100"
			width="850" labelwidth="130" colspan="2"></div>

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>