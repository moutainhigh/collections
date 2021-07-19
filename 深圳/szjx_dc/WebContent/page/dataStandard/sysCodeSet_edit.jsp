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
 	var dcDmId = null;
	var type = null;
	function initData(res){
		dcDmId = res.getAttr("dcDmId");
		type = res.getAttr("type");
		 
	}
	
	$(function(){
		$("#formpanel_edit .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		$("div[name= 'standardCodeindex']").children(".jazz-form-text-label").prepend("<font color='red'>*</font>"); 
	
		if(type == "add"){
			$('div[name="regname"]').textfield("option","disabled",true);
			$('div[name="regtime"]').textfield("option","disabled",true);
			$('div[name="modname"]').hide();
			$('div[name="modtime"]').hide();
		/* 	setMessage(); */
		}
		if(type == "update"){
			$('div[name="regname"]').textfield("option","disabled",true);
			$('div[name="regtime"]').textfield("option","disabled",true);
			$('div[name="modname"]').textfield("option","disabled",true);
			$('div[name="modtime"]').textfield("option","disabled",true);
			$('div[name="standardCodeindex"]').textfield("option", "disabled", true);
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/syscodelistManager/getCodeSetById.do?dcDmId='+dcDmId);
			$('div[name="formpanel_edit"]').formpanel('reload');
		}
		
		if(type == "check"){
			$("#btn3").button("hide");
			$(".jazz-form-text-label").children("font").remove();//取出必须项 *号
			$("#formpanel_edit").formpanel("option","readonly",true);
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/syscodelistManager/getCodeSetById.do?dcDmId='+dcDmId);
			$('div[name="formpanel_edit"]').formpanel('reload');
		} 
		
	});
/* 	
	function setMessage(){
		$("div[name='formpanel_edit']").formpanel('option','dataurl',rootPath+
				'/codeList/setMessage.do?standardCodeindex='+standardCodeindex+'&type='+type);
		$("div[name='formpanel_edit']").formpanel('reload');
	} */
	
	function save() {
		var dcDmDm = $("div[name='dcDmDm']").textfield('getValue');
		var dcDmMc = $("div[name='dcDmMc']").textfield('getValue');
		
		
		if(dcDmDm == ''||dcDmMc == ''){
			jazz.info("有必填选项未填写，保存失败");
		}else{
			if(type == "add"){
				var params = {
						url : rootPath+'/syscodelistManager/saveCodeSetAdd.do',
						components : [ 'formpanel_edit' ],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								window.top.$("#frame_maincontent").get(0).contentWindow.query();
								back();
								
							} 
							else{
								jazz.info("系统代码集已经存在，添加失败");	
							} 
								
							
						}
					};
					$.DataAdapter.submit(params);
			}
			 if(type == "update"){
				var params = {
						url : rootPath+'/syscodelistManager/saveCodeSetUpdate.do',
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
	
	function ident(){
		var result = {}; 
		var code = $("div[name= 'standardCodeindex']").textfield("getValue");
		var regexp = /^[A-Za-z0-9]+$/;
	    if(regexp.test(code)){
	    	code = code.toUpperCase();
	    	$("div[name= 'standardCodeindex']").textfield("setValue",code);
	    	result["state"]=true; 
	    }else{
	    	result["state"]=false;               
	        result["msg"]="请输入英文或数字";
	    }
	    return result;
		
		
	}
	 
	
	
</script>
</head>
<body>
	<div id="formpanel_edit" name="formpanel_edit" vtype="formpanel" height="100%"
		tableStyleClass="tablecss" titledisplay="false" showborder="false" width="100%"
		layout="table" layoutconfig="{cols:2, columnwidth: ['50%','*']}"
		dataurl="">
		
		  			
		<div name='dcDmId' id="dcDmId" vtype="hiddenfield"  width="90%"></div>
		<div name='dcDmDm' vtype="textfield" colspan="2" rowspan="1"  label="标识符" labelAlign="right"
			rule="must"  width="90%" labelwidth="100" maxlength="8"></div>
	<!-- 	<div name='standardType' vtype="textfield" label="代码集标准" labelAlign="right"
			width="80%" labelwidth="100" maxlength="5"></div> -->
		<div name='dcDmMc' vtype="textfield" label="代码集名称" labelAlign="right" colspan="1" rowspan="1"
			rule="must" width="80%" labelwidth="100" maxlength="50"></div>
		
		<div name='pkDcBusiObject' id='pkDcBusiObject' vtype="comboxfield" label="所属业务系统"  rule="must" labelalign="right" labelwidth="120" width="82%"  dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do"></div>
		
		<div name='representation' vtype="textfield" label="表示" labelAlign="right" colspan="2" rowspan="1" 
			width="90%" labelwidth="100" maxlength="5"></div>
		<div name='regname' vtype="textfield" label="创建人" labelAlign="right"
		    width="80%" labelwidth="100"></div>
		<div name='regtime' vtype="textfield" label="创建时间" labelAlign="right" 
		    width="82%" labelwidth="100"></div>
		<div name='modname' vtype="textfield" label="最后修改人" labelAlign="right" 
			width="80%" labelwidth="100"></div>
		<div name='modtime' vtype="textfield" label="最后修改时间" labelAlign="right"
			width="82%" labelwidth="100"></div>
		<div name='codingMethods' vtype="textareafield"  colspan="2" rowspan="1" label="编码方法" labelAlign="right" 
			 width="90%" labelwidth="100" height="90" maxlength="500"></div>
		<div name='description' vtype="textareafield"  colspan="2" rowspan="1" label="说明" labelAlign="right" 
			 width="92.4%" labelwidth="100" height="90" maxlength="500"></div>

		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine">		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>