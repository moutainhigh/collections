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
var pkDcDataSource=null;
var type=null;//类型（新增  修改 查看 ）
function initData(res){
 	type=res.getAttr("type");
 	
 	pkDcDataSource=res.getAttr("pkDcDataSource");
	
}
function preparesave(){

	    var aa = $("#formpanel").formpanel('getValue'); 	
	
		var params = {
		url:rootPath+'/dataSource/DataSourceTest.do',
		components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					
				save();
		
				
				} else {
					jazz.info("不能连接到数据源，请先测试！！");
				}
			}
		};
		$.DataAdapter.submit(params);  		
}

function save() {

	if(type=="add"){//新增
		
 	    var aa = $("#formpanel").formpanel('getValue'); 	
 		var params = {
			url:rootPath+'/dataSource/saveDcDataSourceBO.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {		
					jazz.info("新增成功");
					goback();
				} else {
					jazz.info("创建失败"); 
		
				}
			}
		};
		$.DataAdapter.submit(params);  
		
	}else if(type=="update"){//更新
	    var aa = $("#formpanel").formpanel('getValue');
	  //  alert(JSON.stringify(aa)); 
  		var params = {
			url:rootPath+'/dataSource/updateDcDataSourceBO.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					jazz.info("成功修改");
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
function showform(){

 		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/dataSource/getDcDataSourceBO.do?type='+type+'&pkDcDataSource='+pkDcDataSource);
		$('div[name="formpanel"]').formpanel('reload');	 
}
function datasourceType(){
	$("#dataSourceType").radiofield('option', 'dataurl',rootPath+"/dataSource/findKeyValueDcDmDstypeBODefault.do");
	//$('#dataSourceType').radiofield('reload');	
/* 	var params = {
   			url:rootPath+"/dataSource/findKeyValueDcDmDstypeBODefault.do",
   			params: {pkId:pkDcDataSource},
   			callback : function(data,obj,res) {
   				if (res.getAttr("back") == 'success') {
   					 alert(JSON.stringify(res.getAttr("obj")));
   					$('div[name="dataSourceType"]').radiofield('reload');	
   					//$("#dataSourceType").radiofield('option', 'dataurl',JSON.stringify(res.getAttr("obj")));
   				} else {
   					jazz.info("连接不成功，请检查用户名、密码等是否正确！！");
   				}		   				
   			}
   		};
   		$.DataAdapter.submit(params);	 */
}
$(function(){
	 $('div[name="pok"]').hide(); 

	 
 	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
 	$('div[name="dataSourceIp"]').children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
 	//$("#dataSourceType").radiofield('option', 'dataurl',rootPath+"/dataSource/findKeyValueDcDmDstypeBODefault.do");
 	if(type=="add"){
		
		//$('div[name="userName"]').children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
	}else if(type=="update"){
		 showform();
		
	}else if(type=="view"){	
		$("#btn1").button("hide");
		$("#btn3").button("hide");
		$(".jazz-form-text-label").children("font").remove();//取出必须项 *号

		 showform();
		$("#formpanel").formpanel("option","readonly",true);
	}

});
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
function DataSourceTest(){

	    var aa = $("#formpanel").formpanel('getValue'); 	
	//	    alert(aa);
		var params = {
		url:rootPath+'/dataSource/DataSourceTest.do',
		components : [ 'formpanel' ],
		callback : function(data, r, res) {
			if (res.getAttr("back") == 'success') {
		
				jazz.info("成功连接 ！！");
	
			
			} else {
				jazz.info("连接不成功，请检查用户名、密码等是否正确！！");
			}
		}
	};
	$.DataAdapter.submit(params);  	
	
}


/* function useNameRule(){
	var result = {}; 
	var code = $('div[name="userName"]').textfield("getValue");
	result =noNullAndEnglish(code);
	return result;
} */
function  noNullAndEnglish(code){
	var result = {}; 
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

function iprule(){
	var result = {}; 
	var code = $('div[name="dataSourceIp"]').textfield("getValue");	
	if(isIp(code)){//是IP吗
    	if(code.length<=0){
    		result["state"]=false; 
    		result["msg"]="不能为空,且必须为数字ip如172.30.1.1";
    	}else{
    		result["state"]=true; 
    	}
	}else{
		result["state"]=false; 
		result["msg"]="不能为空,且必须为数字ip如172.30.1.1";
	}
	return result;  
}
//验证是否为IP
function isIp(code){
    var t1=true;
	var regexp = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;


   t1 = regexp.test(code);
   if(!t1){
   	return t1;
   }
   $.each(code.split("."), function(i, num) {
       //切割开来，每个都做对比，可以为0，可以小于等于255，但是不可以0开头的俩位数
       //只要有一个不符合就返回false
       if(num.length > 1 && num.charAt(0) === '0'){
           //大于1位的，开头都不可以是‘0’
           t1= false;
       }else if(parseInt(num , 10) > 255){
           //大于255的不能通过
           t1= false;
       }
   	});

return t1;

}
 
</script>
</head>
<body>
	   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" 
				height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">

  			
				<div name='pkDcDataSource' id="pkDcDataSource" vtype="hiddenfield"  label="数据源主键" maxlength="32" 
					 labelAlign="right"  width="90%"></div>
					 
 		         <div id="dataSourceType" name="dataSourceType" vtype="hiddenfield"  labelwidth="100"
		            label="数据源类型" labelalign="right" width="95%"  colspan="2" 
		           <%--  dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDstypeBO.do"  --%>
		           dataurl="[{value: 'WebService',text: 'WebService'},{value: 'Ftp',text: 'Ftp'},{checked: true,value: 'DB',text: '数据库'}]"
		            >
		        <!--     dataurl="[{value: 'WebService',text: 'WebService'},{value: 'Ftp',text: 'Ftp'},{checked: true,value: 'DB',text: '数据库'}]" -->
		        </div> 
				<div  name='dataSourceName' vtype="textfield" label="数据源名称" maxlength="50" labelwidth="100"
					labelAlign="right" width="90%"
					></div>	
<!-- 				<div id='busiSys' name='busiSys' vtype="comboxfield" label="所属对象"
					labelAlign="right" rule="must" width="90%"
					dataurl=""></div> -->
					
				<div id='dbType' name='dbType' vtype="comboxfield" label="数据库类型" labelwidth="100"
					labelAlign="right" rule="must" width="90%" 
					dataurl="<%=contextpath%>/dataSource/findKeyValueDcDmDbtypeBO.do"
					
					></div>						
				<div  name='userName' vtype="textfield" label="数据源用户名" maxlength="50"  labelwidth="100"  rule="must"
					labelAlign="right" width="90%" defaultvalue="" maxlength="20" 
				></div>					
				<div  name='pwd' vtype="passwordfield" label="数据源密码" maxlength="20" labelwidth="100" rule="must"
					labelAlign="right" width="90%" defaultvalue="" 
				></div>							
				<div  name='dataSourceIp' vtype="textfield" label="数据源IP" maxlength="15" labelwidth="100" rule="customFunction;iprule()" 
					labelAlign="right" width="90%" defaultvalue=""
				></div>	
				<div  name='accessPort' vtype="textfield" label="访问端口" maxlength="15" rule="must" labelwidth="100"
					labelAlign="right" width="90%" defaultvalue=""
				></div>					
				<div  name='dbInstance' vtype="textfield" label="数据库实例" maxlength="20" rule="must" labelwidth="100"
					labelAlign="right" width="90%"	defaultvalue=""
				></div>	
				
				<div name='pkDcBusiObject' id="pkDcBusiObject" vtype="comboxfield" label="业务系统"   labelAlign="right" labelwidth="100"
					rule="" width="90%"		dataurl="<%=contextpath%>/dataSource/findKeyValueDcBusiObjectBO.do" ></div>																											
        		<div name='remarks' colspan="2" rowspan="1"    maxlength="1000" labelwidth="100"
         	    	vtype="textareafield" label="数据源描述" labelalign="right" width="94.83%" height="85">             	
             	</div> 											
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center" class="toolLine">
					<div id="btn3" name="btn3" vtype="button" text="测试"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="DataSourceTest()"></div>
					<div id="btn1" name="btn1" vtype="button" text="保 存"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="preparesave()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>
  
  
</body>

</html>