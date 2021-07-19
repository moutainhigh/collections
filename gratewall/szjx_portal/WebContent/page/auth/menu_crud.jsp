<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理-维护</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>

<style type="text/css">
</style>
<script type="text/javascript">
var pkSysIntegration;
var pkFunction=null;//主键 就是functioncode
var superFunction=null;
var functionCode=null;
var type=null;
function initData(res){
	 pkSysIntegration = res.getAttr("pkSysIntegration");
	 pkFunction=res.getAttr("pkFunction");
	 superFunction=res.getAttr("superFunction");
	 queryone=res.getAttr("queryone");
	 type =res.getAttr("type");
}
function save() {
	if(pkFunction==null||pkFunction==""){//保存
	    var aa = $("#formpanel").formpanel('getValue'); 	    
	    //alert(JSON.stringify(aa)); 		
		var params = {
			url:rootPath+'/auth/saveSmfunction.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					//closewin();
					window.parent.refreshFuncTree();
					jazz.info("新增成功");
				} else {
 					jazz.info("菜单名已存在，保存失败"); ;
				}
			}
		};
		$.DataAdapter.submit(params);
	}else{//更新
		var params = {
			url:rootPath+'/auth/updateSmfunction.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					//closealterwin();
					window.parent.refreshFuncTree();
					jazz.info("修改成功");
				} else {
/* 					jazz.info("菜单名已存在，保存失败"); */
					jazz.info("字段长度过长，修改失败");
				}
			}
		};
		$.DataAdapter.submit(params);		
	}
}
function closewin(){
	parent.queryFuncMenu();
	parent.win.window("close"); 
}
function closealterwin(){
	parent.queryFuncMenu();
	parent.alterwin.window("close"); 
}

function reset() {
	//$("#formpanel").formpanel('reset');
}
function setSMfunction(){
    $('div[name="functionUrl"]').textfield("option","disabled", true); 
	if(superFunction!=null && superFunction !=""){
		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/auth/setSMFunction.do?pkSysIntegration='+pkSysIntegration+'&superFunction='+superFunction);
			$('div[name="formpanel"]').formpanel('reload');	
			$('div[name="superFuncCode"]').comboxtreefield('option', 'disabled', true);
	}else{
	
		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/auth/setSMFunction.do?pkSysIntegration='+pkSysIntegration);
			$('div[name="formpanel"]').formpanel('reload');	
			$('div[name="superFuncCode"]').comboxtreefield('setValue', 'system');
	}

}
function changeUrl(){
	var result = {}; 
	var url = $("#url").textfield("getValue");
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
$(function(){	
	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
	$('div[name="superFuncCode"]').comboxtreefield('option', 'disabled', true);
	if(type==null||type==""){
		other();
	}else{
		$("#btn1").button('option','disabled',true);//只是显示出来 什么都不做
		$("#btn1").button("hide");//隐藏
	}
	  
    $('div[name="functionType"]').on("comboxfieldchange",function(event, ui){ 

        if(ui.newValue=='1'){ 

            $('div[name="functionUrl"]').textfield("option","disabled", true); 


        }else{ 

            $('div[name="functionUrl"]').textfield("option","disabled", false); 

        } 

    }); 


});
function  other(){
	querysupermenu();//获取上级菜单树状结构
	if(pkFunction==null||pkFunction==""){//新增页面
		setSMfunction();
		 $('div[name="modifierName"]').hide();
		 $('div[name="modifierTime"]').hide();
		 
	}else{//修改页面
		if(queryone=="yes"){//查看页面
	        $("#formpanel").formpanel('option', 'readonly', true); 
	        $("#toolbar").hide();
		}else{
			haveleaf();		
		}
		$('#formpanel').formpanel('option', 'dataurl',rootPath+
				'/auth/getFuncMenuByPK.do?pkFunction='+pkFunction);
		$('#formpanel').formpanel('reload');	
		//$('div[name="pkFunction"]').textfield("option","readonly",true);
		
	}

	$("#pkSysIntegration").hiddenfield("setValue", pkSysIntegration);
		
}


function querysupermenu() {//上级菜单

	$('div[name="superFuncCode"]').comboxtreefield('option', 'dataurl',rootPath+'/auth/getSupermenuTree.do?pkSysIntegration='+pkSysIntegration+'&pkFunction='+pkFunction);
	$('div[name="superFuncCode"]').comboxtreefield('reload');
}
var oldsuperFuncCode=null;
var count=0;
function setOldsuperfuncCode(){
	count=count+1;
	if(count==1){
	oldsuperFuncCode=$('div[name="superFuncCode"]').comboxtreefield('getValue')
	}
}
function change() {//修改菜单 改变层次码 和功能代码
	var newsuperFuncCode=$('div[name="superFuncCode"]').comboxtreefield('getValue');

		var params = {
				url :rootPath+'/auth/ChangelevelCode.do?pkSysIntegration='+pkSysIntegration+'&superFuncCode='+newsuperFuncCode+'&pkFunction='+pkFunction,
				callback : function(data, r, res) {
					if(res.getAttr("add")=='Y'){
						$('div[name="functionCode"]').textfield('setValue',res.getAttr("functionCode"));
						$('div[name="levelCode"]').textfield('setValue',res.getAttr("levelCode"));
						$('div[name="pkFunction"]').hiddenfield('setValue',res.getAttr("functionCode"));	
					}else{
						$('div[name="levelCode"]').textfield('setValue',res.getAttr("levelCode"));	
					}
				}
			};
			$.DataAdapter.submit(params);			
	
}
function haveleaf(){//判断功能类型决定url是否可以更改
	//先判断有无下一层
		$('#functionType').comboxfield('option','disabled',true);//功能类型不容许改变
	var params = {
			//url :rootPath+'/auth/haveleaf.do?pkFunction='+pkFunction,
			url :rootPath+'/auth/isFunc.do?pkFunction='+pkFunction,		
			callback : function(data, r, res) {
			//$('#superFuncCode').comboxtreefield('option', 'readonly',res.getAttr("back"));
			$('#url').textfield('option','disabled',!res.getAttr("back"));
			}
		};
		$.DataAdapter.submit(params);	
}

function goback(){//返回       
	window.parent.goback();
	
}
</script>
</head>
<body>
   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false"  title="新增菜单"
				height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
				
				<div name='pkSysIntegration' id="pkSysIntegration" vtype="hiddenfield" label="系统集成表主键"
					 labelAlign="right"></div>
				<div name='functionName' id="gnMc" vtype="textfield" label="菜单名称"   labelAlign="right" maxlength="50"
					rule="must" width="90%"></div>
				<div name='functionNameShort' id="gnMc" vtype="textfield" label="菜单简称"   labelAlign="right" maxlength="10"
					rule="must" width="90%"></div>	
				<div id='pkFunction' name='pkFunction' vtype="hiddenfield" label="菜单代码"
					labelAlign="right" rule="must" width="90%"
					dataurl=''></div>	
				<div id='functionType' name='functionType' vtype="comboxfield" label="菜单类型"
					labelAlign="right" rule="must" width="90%"
					dataurl="<%=contextpath%>/auth/findFuncType.do"></div>										
				<div name='superFuncCode' id="superFuncCode" vtype="comboxtreefield" label="上级菜单" labelAlign="right" 
					 readonly="" width="90%"  rule="must" defaultvalue="无" 
					 change="change"  onclick="setOldsuperfuncCode()"></div>
				<div name='functionCode' id="gnMc" vtype="textfield" label="菜单代码"   labelAlign="right"
					disabled=true width="90%"></div>	
				<div id='orderNo' name='orderNo' vtype="textfield" label="菜单序号" rule="number" msg="必须为数字"   maxlength="31"
					labelAlign="right"  width="90%"
					dataurl=''></div>
				<div id='url' name='functionUrl' vtype="textfield" label="URL"  maxlength="150"
					rule="customFunction;changeUrl()"
					labelAlign="right"  width="95%" colspan="2" rowspan="1"
					dataurl=''></div>										
				<div id='levelCode' name='levelCode' vtype="textfield" label="层次码" disabled=true
					labelAlign="right" width="95%" colspan="2" rowspan="1"
					dataurl=''></div>		
 				<div id='createrId' name='createrId' vtype="hiddenfield" label="创建人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>
 				<div id='createrName' name='createrName' vtype="textfield" label="创建人" disabled=true
					labelAlign="right"  width="90%" 
					></div>					
 				<div id='createrTime' name='createrTime' vtype="textfield" label="创建时间" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>
					
 				<div  name='modifierId' vtype="hiddenfield" label="修改人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>					
 				<div name='modifierName' vtype="textfield" label="修改人" disabled=true
					labelAlign="right"  width="90%" 
				></div>
 				<div id='modifierTime' name='modifierTime' vtype="textfield" label="修改时间" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>

					
	      		<div name='remarks' colspan="2" rowspan="1"    maxlength="1000"
             	vtype="textareafield" label="备注" labelalign="right" width="95%" height="85">             	
             	</div>   
									
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center" height="80">
					<div id="btn1" name="btn1" vtype="button" text="保 存"
						 align="center" defaultview="1"	icon="" click="save()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						 align="center" defaultview="1"	icon="" click="goback()"></div>
				</div>
			</div>
</body>
</html>