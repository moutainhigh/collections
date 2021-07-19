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
	var pkCodeTableManager;
	var username;
	var currentDate;
	$(function() {
		$("#formpanel_edit .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		$("div[name= 'codeTableEnName']").children(".jazz-form-text-label").prepend("<font color='red'>*</font>"); 
		
		$('div[name="createrName"]').textfield("option","disabled",true);
		$('div[name="createrTime"]').textfield("option","disabled",true);
		$('div[name="modifierName"]').textfield("option","disabled",true);
		$('div[name="modifierTime"]').textfield("option","disabled",true);
		if(pkCodeTableManager == null){
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide();
			$('div[name="createrName"]').textfield("setValue",username);
			$('div[name="createrTime"]').textfield("setValue",currentDate);
			$('div[name="modifierName"]').textfield("setValue",username);
			$('div[name="modifierTime"]').textfield("setValue",currentDate);
		}else{
			$('div[name="formpanel_edit"]').formpanel('option', 'dataurl',rootPath+
					'/dictionaryList/show.do?pkCodeTableManager='+pkCodeTableManager);
			$('div[name="formpanel_edit"]').formpanel('reload');
			$('div[name="codeTableEnName"]').textfield("option", "disabled", true);
				
		}
		$('div[name="pkSysIntegration"]').comboxfield('option','dataurl',rootPath+'/dictionaryList/systemList.do');
		$('div[name="pkSysIntegration"]').comboxfield('reload');
		
		
	});
	
	function initData(res){
		pkCodeTableManager = res.getAttr("pkCodeTableManager");
		username = res.getAttr("username");
		currentDate = res.getAttr("currentDate");
		// alert(pkCodeTableManager);
		 
	}

	

	function save() {
		var pkSysIntegration = $("div[name='pkSysIntegration']").comboxfield('getValue');
		var codeTableChName = $("div[name='codeTableChName']").textfield('getValue');
		var codeTableEnName = $("div[name='codeTableEnName']").textfield('getValue');
		var codeTableDesc = $("div[name='codeTableDesc']").textareafield('getValue');
		var effectiveMarker = $("div[name='effectiveMarker']").comboxfield('getValue');
		var codeColumn = $("div[name='codeColumn']").textfield('getValue');
		var valueColumn = $("div[name='valueColumn']").textfield('getValue');
		var cacheType = $("div[name='cacheType']").comboxfield('getValue');
		
		//alert(pkSysIntegration);
		if(pkSysIntegration == ''||codeTableChName == ''||codeTableEnName==''||
				effectiveMarker==''||codeColumn==''||valueColumn==''||cacheType==''){
			jazz.info("有必填选项未填写，添加失败");
		}else{
			var params = {
				url : rootPath+'/dictionaryList/save.do',
				components : [ 'formpanel_edit' ],
				
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						//jazz.info("保存成功");
						window.top.$("#frame_maincontent").get(0).contentWindow.queryDef();
						//parent.win.window("close");
						 back();
						/* $("div[name=pkCodeTableManager]").hiddenfield("setValue",res.getAttr("pkCodeTableManager")); */
					} 
					else 
						jazz.info("代码表已经存在，添加失败");
					
				}
			};
			$.DataAdapter.submit(params);
		}
	}
	
	
	
	function back() {
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	}
	
	function enName(){
		var result = {}; 
		var code = $("div[name= 'codeTableEnName']").textfield("getValue");
		var regexp = /^[a-zA-Z][a-zA-Z_]*$/;
	    if(regexp.test(code)){
	    	result["state"]=true; 
	    	code = code.toUpperCase();
	    	$("div[name= 'codeTableEnName']").textfield("setValue",code);
	    }else{
	    	result["state"]=false;               
	        result["msg"]="请输入代码表英文名";
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

		<div name='pkCodeTableManager' vtype="hiddenfield" label="主键" labelAlign="right"></div>
		<div name='codeTableEnName' vtype="textfield" label="代码表英文名" labelAlign="right"
			rule="customFunction;enName()"  width="80%" labelwidth="100" maxlength="20"></div>
		<div name='codeTableChName' vtype="textfield" label="代码表中文名" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="50"></div>
		<div name='pkSysIntegration' vtype="comboxfield" label="所属子系统" labelAlign="right"
			rule="must" width="80%" labelwidth="100"></div>
		
		<div name='codeColumn' vtype="textfield" label="code字段" labelAlign="right" 
			rule="must" width="80%" labelwidth="100" maxlength="15"></div>
		<div name='valueColumn' vtype="textfield" label="value字段" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="15"></div>
		<div name='cacheType' vtype="comboxfield" label="存储类型" rule="must"
			labelAlign="right" rule="must" width="80%" labelwidth="100"
			dataurl='[{"text":"存储到内存","value":"0"},{"text":"存储到数据库","value":"1"}]' defaultvalue='1'></div>
		<div name='effectiveMarker' vtype="comboxfield" label="状态" labelAlign="right" rule="must" width="80%" 
			labelwidth="100" dataurl='[{"text":"正常","value":"0"},{"text":"禁用","value":"1"}]' defaultvalue='0'></div>
		<div name='createrName' vtype="textfield" label="创建人" labelAlign="right"
		    width="80%" labelwidth="100"></div>
		<div name='createrTime' vtype="textfield" label="创建时间" labelAlign="right" 
		    width="80%" labelwidth="100"></div>
		<div name='modifierName' vtype="textfield" label="最后修改人" labelAlign="right" 
			width="80%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelAlign="right"
			width="80%" labelwidth="100"></div>
		<div name='codeTableDesc' vtype="textareafield"  colspan="2" rowspan="1" label="描述" labelAlign="right" 
			 width="90%" labelwidth="100" height="110" maxlength="1000"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>