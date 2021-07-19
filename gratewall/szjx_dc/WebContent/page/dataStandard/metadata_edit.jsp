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
	var pkDcStandardDataelement = null;
	var type = null;
	function initData(res){
		pkDcStandardDataelement = res.getAttr("pkDcStandardDataelement");
		type = res.getAttr("type");
		 
	}
	
	$(function(){
		$("#formpanel_edit .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		$("div[name= 'identifier']").children(".jazz-form-text-label").prepend("<font color='red'>*</font>"); 
	
		if(type == "add"){
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide();
			setMessage();
		}
		if(type == "update"){
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').textfield("option","disabled",true);
			$('div[name="modifierTime"]').textfield("option","disabled",true);
			$('div[name="identifier"]').textfield("option", "disabled", true);
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/metadataList/getMetadataById.do?pkDcStandardDataelement='+pkDcStandardDataelement);
			$('div[name="formpanel_edit"]').formpanel('reload');
		}
		if(type == "check"){
			$("#btn3").button("hide");
			$(".jazz-form-text-label").children("font").remove();//取出必须项 *号
			$("#formpanel_edit").formpanel("option","readonly",true);
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/metadataList/getMetadataById.do?pkDcStandardDataelement='+pkDcStandardDataelement);
			$('div[name="formpanel_edit"]').formpanel('reload');
		}
		
	});
	
	function setMessage(){
		$("div[name='formpanel_edit']").formpanel('option','dataurl',rootPath+
				'/metadataList/setMessage.do?pkDcStandardDataelement='+pkDcStandardDataelement+'&type='+type);
		$("div[name='formpanel_edit']").formpanel('reload');
	}
	
	function save() {
		var identifier = $("div[name='identifier']").textfield('getValue');
		var columnNane = $("div[name='columnNane']").textfield('getValue');
		var columnNaneCn = $("div[name='columnNaneCn']").textfield('getValue');
		var dataType = $("div[name='dataType']").textfield('getValue');
		
		
		//alert(pkSysIntegration);
		if(identifier == ''||columnNane == ''||columnNaneCn==''||
				dataType==''){
			jazz.info("有必填选项未填写，保存失败");
		}else{
			if(type == "add"){
				var params = {
						url : rootPath+'/metadataList/saveAdd.do',
						components : [ 'formpanel_edit' ],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								window.top.$("#frame_maincontent").get(0).contentWindow.query();
								back();
								
							} 
							else 
								jazz.info("标准数据元已经存在，添加失败");
							
						}
					};
					$.DataAdapter.submit(params);
			}
			if(type == "update"){
				var params = {
						url : rootPath+'/metadataList/saveUpdate.do',
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
		var code = $("div[name= 'identifier']").textfield("getValue");
		var regexp = /^[A-Za-z0-9]+$/;
	    if(regexp.test(code)){
	    	result["state"]=true; 
	    	$("div[name= 'identifier']").textfield("setValue",code);
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

		<div name='pkDcStandardDataelement' vtype="hiddenfield" label="主键" labelAlign="right"></div>
		<div name='identifier' vtype="textfield" label="标识符" labelAlign="right"
			rule="customFunction;ident()"  width="90%" labelwidth="100" maxlength="8"></div>
		<div name='columnNane' vtype="textfield" label="字段名称" labelAlign="right"
			rule="must" width="90%" labelwidth="100"  maxlength="32"></div>
		<div name='columnNaneEn' vtype="textfield" label="字段英文名称"  labelAlign="right" colspan="2" rowspan="1"
			width="93.8%" labelwidth="100" maxlength="50"></div>
		<div name='columnNaneCn' vtype="textfield" label="字段中文名称" labelAlign="right" colspan="2" rowspan="1"
			rule="must" width="93.8%" labelwidth="100" maxlength="50"></div>
		
		<div name='pkDcStandardSpec' vtype="comboxfield" label="标准规范" labelAlign="right" 
			width="90%" labelwidth="100" dataurl="<%=contextpath%>/metadataList/queryStandardSpec.do"></div> 
	    <div name='dataType' vtype="textfield" label="数据类型" labelAlign="right" 
			rule="must" width="90%" labelwidth="100" maxlength="5"></div> 
		<div name='dataLength' vtype="textfield" label="数据长度" labelAlign="right"
			rule="number" msg="请输入数字" width="90%" labelwidth="100"  maxlength="30"></div>
		<div name='dataFormat' vtype="textfield" label="数据格式" labelAlign="right"
			width="90%" labelwidth="100"  maxlength="32"></div>	
		<div name='unit' vtype="textfield" label="计量单位" labelAlign="right"
			width="90%" labelwidth="100"  maxlength="20"></div>	
		<div name='version' vtype="textfield" label="版本"
			labelAlign="right" width="90%" labelwidth="100" maxlength="7"></div>
		<div name='createrName' vtype="textfield" label="创建人" labelAlign="right"
		    width="90%" labelwidth="100"></div>
		<div name='createrTime' vtype="textfield" label="创建时间" labelAlign="right" 
		    width="90%" labelwidth="100"></div>
		<div name='modifierName' vtype="textfield" label="最后修改人" labelAlign="right" 
			width="92%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelAlign="right"
			width="92%" labelwidth="100"></div>
		<div name='columnDesc' vtype="textareafield"  colspan="2" rowspan="1" label="表示内容" labelAlign="right" 
			 width="96.4%" labelwidth="100" height="90" maxlength="500"></div>
		<div name='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelAlign="right" 
			 width="96.4%" labelwidth="100" height="90" maxlength="1000"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" class="toolLine">		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>