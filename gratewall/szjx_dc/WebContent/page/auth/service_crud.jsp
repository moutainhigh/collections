<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var pkSmServices=null;
var type=null;//类型（新增  修改 查看 ）
function initData(res){
 	type=res.getAttr("type");
 	pkSmServices=res.getAttr("pkSmServices");
/*	pkSysIntegration = res.getAttr("pkSysIntegration");
	pkRole=res.getAttr("pkRole"); */
}
function save() {

	if(type=="add"){//新增

 	    var aa = $("#formpanel").formpanel('getValue'); 	
 		var params = {
			url:rootPath+'/auth/saveSmServices.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					
					jazz.info("新增成功");
					refreshService();
					goback();
				
				} else {
					jazz.info("服务信息表主键已存在，请重新创建"); 
		
				}
			}
		};
		$.DataAdapter.submit(params);  
		
	}else if(type=="update"){//更新
 		var params = {
			url:rootPath+'/auth/updateSmServices.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					jazz.info("成功修改");
					refreshService();
					goback();
					
				} else {
					jazz.info("修改失败");
				}
			}
		};
		$.DataAdapter.submit(params);	  
	}else if(type=="view"){
		jazz.info("查看状态不能保存");
	}
	
}
function changeUrl(){
	var result = {}; 
	var url = $("#serviceUrl").textfield("getValue");
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var str="^((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var go = strRegex+"|"+str;
	var regexp = new RegExp(go); 
    if(regexp.test(url)){
    	result["state"]=true; 
    }else{
    	result["state"]=false;               
        result["msg"]="不能为空,且必须是有效的url地址";
    }
    return result;
}
function setRole(){

		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/auth/setSmService.do?type='+type+'&pkSmServices='+pkSmServices);
		$('div[name="formpanel"]').formpanel('reload');	
}
function setRole2(){
    $('div[name="pkSmLikeman"]').comboxfield("option","dataurl", rootPath+'/auth/findPkSmLikemanBypkSmServices.do?pkSmServices='+pkSmServices); 
    $('div[name="pkSmLikeman"]').comboxfield("reload"); 	
    setRole();
}
$(function(){
		 $('div[name="pok"]').hide(); 
		
 	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
	$("#serviceNo").children(".jazz-form-text-label").prepend("<font  color='red'>*</font>");

	if(type=="add"){
		$("#pkSmServices").children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
		
		setRole();
		 $('div[name="modifierName"]').hide();
		 $('div[name="modifierTime"]').hide();		
	}else if(type=="update"){
		setRole2();
		$('div[name="pkSmServices"]').hide();
		//setRole();
		$("#pkSmServices").textfield("option","readonly",true);
	}else if(type=="view"){	
		$("#btn1").button("hide");
		$(".jazz-form-text-label").children("font").remove();//取出必须项 *号
		$('div[name="pkSmServices"]').hide();
		setRole2();
		$("#formpanel").formpanel("option","readonly",true);
		$("#btn1").button('option','disabled',true);//只是显示出来 什么都不做
	}
    $('div[name="pkSmFirm"]').on("comboxfieldchange",function(event, ui){ 
    	if($('div[name="pkSmFirm"]').comboxfield('getValue').length>0){
	    	$('div[name="pkSmLikeman"]').comboxfield("option","disabled", true);
	            $('div[name="pkSmLikeman"]').comboxfield("option","dataurl", rootPath+'/auth/findServicePkSmLikemanByPksys.do?pkSmFirm='+ui.newValue); 
	            $('div[name="pkSmLikeman"]').comboxfield("reload"); 
	            $('div[name="pkSmLikeman"]').comboxfield("option","disabled", false); 
    	}else{
    		$('div[name="pkSmLikeman"]').comboxfield("option","disabled", true);
    	}
    }); 
});
function changeCode(){
	var result = {}; 
	var code = $("#pkSmServices").textfield("getValue");
	var regexp = /^[a-zA-Z]+$/;
    if(regexp.test(code)){
    	if(code.length<=0){
    		result["state"]=false; 
    		result["msg"]="不能为空,且必须英文字母";
    	}else{
    		result["state"]=true; 
    	}
    }else{
    	result["state"]=false;               
        result["msg"]="不能为空,且必须英文字母";
    }
    return result;
}
function changeNo(){
	var result = {}; 
	var code = $("#serviceNo").textfield("getValue");
	var regexp = /^\d{0,}$/;
	
    if(regexp.test(code)){
    	if(code.length<=0){
    		result["state"]=false; 
    		result["msg"]="不能为空，且必须为数字";
    	}else{
    		result["state"]=true; 
    	}
    	
    }else{
    	result["state"]=false;               
        result["msg"]="不能为空，且必须为数字";
    }
    return result;
}
function goback(){
	leave();
	//parent.win.window("close"); 
}
function refreshService(){
	window.top.$("#frame_maincontent").get(0).contentWindow.queryMenu();
}
function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

</script>
</head>
<body>
	   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" 
				height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
				
				<div name='pkSmServices' id="pkSmServices" vtype="textfield"  label="主键" maxlength="32" rule="customFunction;changeCode()"
					 labelAlign="right"  width="90%"></div>
				<div name='pok' id="pok" vtype="textfield" label=""  width="90%" readonly="true" labelAlign="right"></div>					 
				 
				<div  name='serviceName' vtype="textfield" label="服务名称" maxlength="50"
					labelAlign="right" width="90%"
					dataurl=''></div>	
				<div id='serviceNo' name='serviceNo' vtype="textfield" label="服务编号"  maxlength="31" rule="customFunction;changeNo()"  
					labelAlign="right" rule="must" width="90%"
					dataurl=''></div>			
					
					
										
									 
				<div name='pkSysIntegration' id="pkSysIntegration" vtype="comboxfield" label="所属系统"   labelAlign="right"
					rule="must" width="90%"		dataurl="<%=contextpath%>/auth/findServicePkSysIntegration.do" ></div>
				<div id='serviceType' name='serviceType' vtype="comboxfield" label="服务类型"
					labelAlign="right" rule="must" width="90%"
					dataurl="<%=contextpath%>/auth/findServiceType.do"></div>	
					
				<div id='agreementType' name='agreementType' vtype="comboxfield" label="协议类型"
					labelAlign="right" rule="must" width="90%"
					dataurl="<%=contextpath%>/auth/findAgreementType.do"></div>							
				<div id='serviceUrl' name='serviceUrl' vtype="textfield" label="服务地址" rule="customFunction;changeUrl()" maxlength="150"
					labelAlign="right"  width="95%" colspan="2" rowspan="1"></div>							
					
				<div name='pkSmFirm' id="pkSmFirm" vtype="comboxfield" label="厂商"   labelAlign="right"
					dataurl="<%=contextpath%>/auth/findServicePkSmFirme.do" rule="must"
					 width="90%"></div>					
				<div  name='pkSmLikeman' vtype="comboxfield" label="联系人" 
					labelAlign="right" width="90%" dataurl="" 
					dataurl=''></div>		
						
 				<div id='createrId' name='createrId' vtype="hiddenfield" label="创建人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>					
 				<div name='createrName' vtype="textfield" label="创建人" disabled=true
					labelAlign="right"  width="90%" 
				></div>
 				<div id='createrTime' name='createrTime' vtype="textfield" label="创建时间" disabled=true
					labelAlign="right"  width="90%" 
				></div>
					
 				<div  name='modifierId' vtype="hiddenfield" label="修改人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>					
 				<div name='modifierName' vtype="textfield" label="修改人" disabled=true
					labelAlign="right"  width="90%" 
				></div>
 				<div id='modifierTime' name='modifierTime' vtype="textfield" label="修改时间" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>	
        		<div name='serviceDisc' colspan="2" rowspan="1"    maxlength="1000"
             	vtype="textareafield" label="服务描述" labelalign="right" width="90%" height="85">             	
             	</div> 											
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
					<div id="btn1" name="btn1" vtype="button" text="保 存"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="save()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>
  
  
</body>

</html>