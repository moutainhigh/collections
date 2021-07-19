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
	var standardCodeindex = null;
	var codeindexCode = null;
	function initData(res){
		type = res.getAttr("type");
		standardCodeindex = res.getAttr("standardCodeindex");
		codeindexCode = res.getAttr("codeindexCode");
	}
	
	
	$(function(){
		
		$('div[name="standardCodeindex"]').textfield('setValue',standardCodeindex);
		$('div[name="standardCodeindex"]').textfield('option','disabled',true);
		
		if(type == "update"){
			$('div[name="codeindexCode"]').textfield('option','disabled',true);
			$('div[name="formpanel_edit"]').formpanel('option','dataurl',rootPath+
					'/codeDataList/show.do?standardCodeindex='+standardCodeindex+'&codeindexCode='+codeindexCode);
			$('div[name="formpanel_edit"]').formpanel('reload');
		}
	});
	
	function save(){
		var codeindexCode = $('div[name="codeindexCode"]').textfield('getValue');
		var codeindexValue = $('dic[name="codeindexValue"]').textfield('getValue');
		if(codeindexCode == ''||codeindexValue == ''){
			jazz.info("有必填选项未填写，保存失败");
		}
		else{
			if(type == "add"){
				var params = {
						url : rootPath+'/codeDataList/saveCodeDataAdd.do?standardCodeindex='+standardCodeindex,
						components : [ 'formpanel_edit' ],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								window.top.$("#frame_maincontent").get(0).contentWindow.query();
								back();
								
							} 
							else 
								jazz.info("该标准代码已经存在，添加失败");
							
						}
					};
					$.DataAdapter.submit(params);
			}
			
			if(type == "update"){
				var params = {
						url : rootPath+'/codeDataList/saveCodeDataUpdate.do',
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
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	}

/*  	
	function ident(){
		var result = {}; 
		var code = $("div[name= 'identifier']").textfield("getValue");
		var regexp = /^[A-Za-z0-9]+$/;
	    if(regexp.test(code)){
	    	result["state"]=true; 
	    	code = code.toUpperCase();
	    	$("div[name= 'identifier']").textfield("setValue",code);
	    }else{
	    	result["state"]=false;               
	        result["msg"]="请输入英文或数字";
	    }
	    return result;
		
		
	}
	  */
	
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel" height="100%"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		dataurl="">

		
		<div name='standardCodeindex' vtype="textfield" label="标识符" labelAlign="right"
			 width="80%" labelwidth="100" maxlength="8"></div>
		<div name='codeindexCode' vtype="textfield" label="代码值" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="10"></div>
		<div name='codeindexValue' vtype="textfield" colspan="2" rowspan="1" label="代码内容" labelAlign="right"
			rule="must" width="90%" labelwidth="100" maxlength="500"></div>
		<div name='illustrate' vtype="textareafield"  colspan="2" rowspan="1" label="说明" labelAlign="right" 
			 width="90%" labelwidth="100" height="170" maxlength="1000"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine">		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>