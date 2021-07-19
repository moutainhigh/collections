<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-新增</title>
<%
	String contextpath = request.getContextPath();
	
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var username;
	var currentDate;
	var pkSmLikeman;
	var pkSmFirm;
	 $(function(){
		$("#phone").children(".jazz-form-text-label").prepend("<font color='red'>*</font>"); 
		$("#email").children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
		
		$('div[name="createrName"]').textfield("option","disabled",true);
		$('div[name="createrTime"]').textfield("option","disabled",true);
		$('div[name="modifierName"]').textfield("option","disabled",true);
		$('div[name="modifierTime"]').textfield("option","disabled",true);
		
		if(pkSmLikeman == null){
			$('div[name="modifierName"]').hide();
			$('div[name="modifierTime"]').hide(); 
			$('div[name="pkSmFirm"]').hiddenfield("setValue",pkSmFirm);
			$('div[name="createrName"]').textfield("setValue",username);
			$('div[name="createrTime"]').textfield("setValue",currentDate);
			$('div[name="modifierName"]').textfield("setValue",username);
			$('div[name="modifierTime"]').textfield("setValue",currentDate);
			
		}
		else{
			$('div[name="smLikeman"]').textfield("option","disabled",true);
			$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
					'/firmList/showLinkman.do?pkSmLikeman='+pkSmLikeman);
			$('div[name="formpanel"]').formpanel('reload');
			
			
		}
		
	});
	
	function initData(res){
		username = res.getAttr("username");
		currentDate = res.getAttr("currentDate");
		pkSmFirm = res.getAttr("pkSmFirm"); 
		pkSmLikeman = res.getAttr("pkSmLikeman");
		 
	}

	function save(){
		var smLikeman = $('div[name="smLikeman"]').textfield('getValue');
		var phone = $('div[name="phone"]').textfield('getValue');
		var email = $('div[name="email"]').textfield('getValue');
		var createrName = $('div[name="createrName"]').textfield('getValue');
		var createrTime = $('div[name="createrTime"]').textfield('getValue');
		var modifierName = $('div[name="modifierName"]').textfield('getValue');
		var modifierTime = $('div[name="modifierTime"]').textfield('getValue');
		var remarks = $('div[name="remarks"]').textareafield('getValue');
		
	
		if(smLikeman == ''||phone == ''||email ==''||phone == ''){
			
				jazz.info("有必填选项未填写，添加失败");
		
		}
	
		else{
			var params = {
				url : rootPath+'/firmList/saveLinkman.do',
				components : [ 'formpanel' ],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						jazz.info("保存成功");
						window.top.$("#frame_maincontent").get(0).contentWindow.queryData();
						back();
						/* $('div[name="pkSmLikeman"]').hiddenfield("setValue",res.getAttr("pkSmLikeman")); */
					} 
					else 
						jazz.info("该联系人已经存在，添加失败");
					
					
				}
			};
			$.DataAdapter.submit(params);
		}
		
	
	} 

	function back() {
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	} 
	
	function fPhone(){
		var result = {}; 
		var code = $("#phone").textfield("getValue");
		var regexp = /^[0-9 -]+$/;
	    if(regexp.test(code)){
	    	result["state"]=true; 
	    	$("#phone").textfield("setValue",code);
	    }else{
	    	result["state"]=false;               
	        result["msg"]="只能输入数字，多个号码用空格隔开";
	    }
	    return result;
	}

</script>

</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='pkSmLikeman' id='pkSmLikeman' vtype="hiddenfield" ></div>
   		<div name='pkSmFirm' id='pkSmFirm' vtype="hiddenfield" ></div>
   		<div name='smLikeman' id='smLikeman' vtype="textfield" label="联系人名" labelalign="right" labelwidth="100" width="80%" rule="must" editable="true" maxlength="50"></div>
		<div name='phone' id='phone' vtype="textfield" label="联系电话" labelalign="right" labelwidth="100" width="80%"  editable="true"  rule="customFunction;fPhone()" maxlength="50"></div>
 		<div name='email' id='email' vtype="textfield" label="联系人邮箱" labelalign="right" labelwidth="100" width="80%"  editable="true"  rule="email" maxlength="250"></div>
		<div name='createrName' vtype="textfield" label="创建人" labelAlign="right" width="80%" labelwidth="100"></div>
		<div name='createrTime' vtype="textfield" label="创建时间" labelAlign="right" width="80%" labelwidth="100"></div>
		<div name='modifierName' vtype="textfield" label="最后修改人" labelAlign="right" width="80%" labelwidth="100"></div>
		<div name='modifierTime' vtype="textfield" label="最后修改时间" labelAlign="right" width="80%" labelwidth="100"></div>
		<div name='remarks' id='remarks' vtype="textareafield"  colspan="2" rowspan="1" label="备注" labelalign="right" labelwidth="100" width="90%" height="150" editable="true" maxlength="1000" ></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="back()"></div>
	    </div>
   </div>
   
</body>
</html>