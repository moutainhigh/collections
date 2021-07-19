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
</script>
</head>
<body>
	<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth=200  width="100%" height="400" orientation="top" id="tab_name">    
	    <ul>    
	        <li><a href="#tab1">》第一步，配置基本信息</a></li>    
	        <li><a href="#tab2">》第二步，配置方法信息</a></li> 
	        <li><a href="#tab2">》第三步，配置规则信息</a></li>    
	    </ul>    
	    <div>    
	        <div id="tab1">   
				<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel"
					tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
					layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
					height="300" dataurl="" >
			
					<div name='pripid' vtype="hiddenfield" label="ID" labelAlign="right"
						 width="400" labelwidth="130"></div>
					<div name='regno' vtype="textfield" label="所属服务对象" labelAlign="right"
						 width="400" labelwidth="130"></div>
					<div name='dmccb' vtype="textfield" label="任务名称" labelAlign="right"
						width="400" labelwidth="130"></div>
						
					<div name='industryphy' vtype="textfield" label="数据源"
						labelAlign="right"  width="400" labelwidth="130"></div>
						
					<div name='dmlMc' vtype="textfield" label="任务状态" labelAlign="right"
						width="400" labelwidth="130"></div>
					
					<div name='a' vtype="textareafield" label="任务说明" colspan="2" height="60"
						labelAlign="right"  width="850" labelwidth="130"></div>
							
					<div name='b' vtype="textareafield" label="备案说明" colspan="2" height="60"
						labelAlign="right"  width="850" labelwidth="130"></div>
						
					<table align="center"><tr><td><label>备案文件:</label><input type="file" value="浏览"></td></tr></table>
					<!-- <div name="tip" vtype="textfield" label="提示" labelAlign="right"
						width="850" labelwidth="130" defaultvalue="SQL语句中代码值的标识为“value”，文本的标识为“text”。
						示例：select t.wb as text,dm as value from T_PT_DMSJB t" readonly="true"></div> -->
				</div>
			</div>
			<div id="tab2">  
			</div>
			<div id="tab3">  
			</div>
		</div>
	</div>
	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
				<div id="btn1" name="btn1" vtype="button" text="保存"
					icon="../../../style/default/images/save.png" click="save()"></div>
				<div id="btn2" name="btn2" vtype="button" text="返回"
					icon="../../../style/default/images/fh.png" click="back()"></div>
			</div>
</body>
</html>