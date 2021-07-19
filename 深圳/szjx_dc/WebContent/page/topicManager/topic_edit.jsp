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
<style type="text/css">
.jazz-field-comp-in2{
	background-color:transparent;
}
</style>
<script>

	var type = null;
	var createUser = null;
	var createTime = null;
	function initData(res){
		type = res.getAttr("type");
		createUser = res.getAttr("createUser");
		createTime = res.getAttr("createTime");
	}
	
	
	$(function(){
		
		$('div[name="createUser"]').textfield('setValue',createUser);
		$('div[name="createUser"]').textfield('option','disabled',true);
		$('div[name="createTime"]').textfield('setValue',createTime);
		$('div[name="createTime"]').textfield('option','disabled',true);
		
		if(type == "update"){
			$('div[name="codeindexCode"]').textfield('option','disabled',true);
			$('div[name="formpanel_edit"]').formpanel('option','dataurl',rootPath+
					'/codeDataList/show.do?standardCodeindex='+standardCodeindex+'&codeindexCode='+codeindexCode);
			$('div[name="formpanel_edit"]').formpanel('reload');
		}
	});
	
	function save(){
		var topicName = $('div[name="topicName"]').textfield('getValue');
		var topicDescription = $('div[name="topicDescription"]').textfield('getValue');
		if(topicName == ''){
			jazz.info("有必填选项未填写，保存失败");
		}
		else{
			if(type == "add"){
				var params = {
						url : rootPath+'/topicManager/saveAddTopic.do?createUser='+createUser+'&createTime='+createTime,
						components : [ 'formpanel_edit' ],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								window.top.$("#frame_maincontent").get(0).contentWindow.query();
								back();
								
							} 
							else 
								jazz.info("该业务主题已经存在，添加失败");
							
						}
					};
					$.DataAdapter.submit(params);
			}
			
			if(type == "update"){
				var params = {
						url : rootPath+'/codeDataList/saveCodeDataUpdate.do?createUser='+ createUser +'&createTime =' + createTime,
						components : [ 'formpanel_edit' ],
						callback : function(data,r,res){
							if(res.getAttr("back") == 'success'){
								window.top.$("#frame_maincontent").get(0).contentWindow.query();
								back();
							}
							else{
								jazz.info("编辑失败");
							}
						}
				};
				$.DataAdapter.submit(params);
			}
		}
		
	}
	
	function back() {
		window.top.$("#tableWindow").get(0).contentWindow.leave();
	}
	
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel" height="100%"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		dataurl="">

		
		<div name='topicName' vtype="textfield" label="业务主题名称" labelAlign="right"
			 width="80%" labelwidth="100" maxlength="8"></div>
		<div name='topicDescription' vtype="textfield" label="业务主题描述" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="10"></div>
		<div name='createUser' vtype="textfield" label="创建人" labelAlign="right"
			 width="80%" labelwidth="100" maxlength="8"></div>
		<div name='createTime' vtype="textfield" label="创建时间" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="10"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine">		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>