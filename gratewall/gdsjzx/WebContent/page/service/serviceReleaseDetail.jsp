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
<style type="text/css">
.btn{
width:40px;
height:20px;
}
</style>
<script>
	function save() {
		parent.winEdit.window("close");
	}
	
	var update;
	$(function(){
		var priPid = jazz.util.getParameter("dmbId");
		update = jazz.util.getParameter("update");
		if(priPid != null){
			//$('#formpanel_edit .jazz-panel-content').loading();
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
		height="500" dataurl="" readonly="true">
		<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
			 width="400" labelwidth="130"></div>
		<div name='regno' vtype="textfield" label="服务名称" labelAlign="right"
			width="400" labelwidth="130"></div>
		<div name='entname1' vtype="comboxfield" label="服务编号" dataurl="[{checked: true,value: '1',text: '登记信息接口'},{value: '2',text: '12315接口'}]"
			labelAlign="right" width="400" labelwidth="130"></div>
			
		<div name='entname2' vtype="comboxfield" label="接口名称" dataurl="[{checked: true,value: '1',text: '地税局'},{value: '2',text: '登记系统'}]"
			labelAlign="right" width="400" labelwidth="130"></div>
		<div name='entname3' vtype="comboxfield" label="服务类型" dataurl="[{checked: true,value: '1',text: 'FTP'},{value: '2',text: '数据库'}]"
			labelAlign="right" width="400" labelwidth="130"></div>
		<div name='entname32' vtype="textfield" label="服务对象"
			labelAlign="right" width="400" labelwidth="130"></div>
		<div name='we' vtype="textareafield" label="创建时间" 
			labelAlign="right" width="850" labelwidth="130"></div>
		<div name='entname' vtype="comboxfield" label="服务状态" dataurl="[{checked: true,value: '1',text: '启用'},{value: '2',text: '停用'}]"
			labelAlign="right" width="400" labelwidth="130"></div>
			
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
	</div>
</body>
</html>