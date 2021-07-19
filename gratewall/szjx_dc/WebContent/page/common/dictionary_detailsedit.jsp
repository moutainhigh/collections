<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var codeTableEnName;
	var code;
	var update;
	var username;
	var date;
	
	$(function() {
		
		$('div[name="code"]').children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
		$('div[name="modifierName"]').textfield("option","disabled",true);
		$('div[name="modifierTime"]').textfield("option","disabled",true);
		
		if(code == null){
			$('div[name="createrName"]').textfield("setValue",username);
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("setValue",date);
			$('div[name="createrTime"]').textfield("option","disabled",true);
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide();
		}else{
 			
			$('div[name="code"]').textfield("option", "disabled", true); 
			$('div[name="createrName"]').textfield("option","disabled",true);
			$('div[name="createrTime"]').textfield("option","disabled",true)
			var params = {
				url:rootPath+'/dictionaryDetails/show.do?code='+code+'&codeTableEnName='+codeTableEnName,
				callback : function(data, r, res) {
						$('div[name="code"]').textfield("setValue",res.getAttr("code"));	
						$('div[name="name"]').textfield("setValue",res.getAttr("name"));
						$('div[name="nameShort"]').textfield("setValue",res.getAttr("nameShort"));
						$('div[name="fCode"]').textfield("setValue",res.getAttr("fCode"));
						$('div[name="chooseMark"]').comboxfield("setValue",res.getAttr("chooseMark"));
						$('div[name="createrName"]').textfield("setValue",res.getAttr("createrName"));
						$('div[name="createrTime"]').textfield("setValue",res.getAttr("createrTime"));
						$('div[name="modifierName"]').textfield("setValue",res.getAttr("modifierName"))
						$('div[name="modifierTime"]').textfield("setValue",res.getAttr("modifierTime"))
							}
						};
				$.DataAdapter.submit(params);	

			
				
			}
	});
	
	function initData(res){
		username = res.getAttr("username");
		date = res.getAttr("currentDate");
		codeTableEnName = res.getAttr("codeTableEnName");
		code = res.getAttr("code");
		update = res.getAttr("update");
		
		
		// alert(pkCodeTableManager);
		 
	}
	
	function save(){
		//alert(codeTableEnName);
		var hiddenfield = $("div[name='hiddenfield']").hiddenfield('getValue');
		var code = $("div[name='code']").textfield('getValue');
		var name = $("div[name='name']").textfield('getValue');
		var nameShort = $("div[name='nameShort']").textfield('getValue');
		var fCode = $("div[name='fCode']").textfield('getValue');
		var shooseMark = $("div[name='shooseMark']").comboxfield('getValue');
		var createrName = $("div[name='createrName']").textfield('getValue');
		var createrTime = $("div[name='createrTime']").textfield('getValue');
		
		
		//alert(pkSysIntegration);
		if(code == ''||name==''||nameShort==''||shooseMark==''){
			jazz.info("有必填选项未填写，添加失败");
		}else{
			var params = {
				url : rootPath+'/dictionaryDetails/save.do?codeTableEnName='+codeTableEnName+'&update='+update,
				components : [ 'formpanel_edit' ],
				
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						//jazz.info("保存成功");
						window.top.$("#frame_maincontent").get(0).contentWindow.query();
						back();
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
	
	function isChn(){
		var result = {}; 
		var code = $("div[name= 'code']").textfield("getValue");
		var regexp =  /^[A-Za-z0-9]+$/;
	    if(regexp.test(code)){
	    	result["state"]=true; 
	    	code = code.toUpperCase();
	    	$("div[name= 'code']").textfield("setValue",code);
	    }else{
	    	result["state"]=false;               
	        result["msg"]="只能输入数字和英文";
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
		<div name='hiddenfield' vtype='hiddenfield' width="80%" ></div> 
		<div name='code' vtype="textfield" label="代码" labelAlign="right"
			rule="customFunction;isChn()" width="80%" labelwidth="100" maxlength="16"></div>
		<div name='name' vtype="textfield" label="名称" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="25"></div>
		<div name='nameShort' vtype="textfield" label="简称" labelAlign="right"
			rule="must" width="80%" labelwidth="100" maxlength="25"></div>
		<div name='fCode' vtype="textfield" label="父节点代码" labelAlign="right"
		    width="80%" labelwidth="100" maxlength="16"></div>
		<div name='chooseMark' vtype="comboxfield" label="选用标记" labelAlign="right" rule="must" 
			width="80%" labelwidth="100" dataurl='[{"text":"选用","value":"0"},{"text":"停用","value":"1"}]' defaultvalue='0'></div>
		<div name='createrName' vtype="textfield" label="创建人" labelalign="right" labelwidth="100" width="80%" editable="false"></div>
		<div name='createrTime' vtype="textfield" label="创建时间" labelalign="right" labelwidth="100" width="80%" editable="false"></div>
		<div name='modifierName' vtype="textfield" label="最后修改人" labelalign="right" labelwidth="100" width="80%" editable="false"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelalign="right" labelwidth="100" width="80%" editable="false"></div>
		
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保 存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返 回" defaultview="1" align="center" click="back()"></div>
	    </div>
	</div>

</body>
</html>