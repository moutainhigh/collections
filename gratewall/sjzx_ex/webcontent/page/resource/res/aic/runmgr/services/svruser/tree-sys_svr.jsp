<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<freeze:html width="650" height="300">
<head>
<title>用户服务树状展示列表</title>
<link href="<%=request.getContextPath()%>/script/lib/skin-vista/ui.dynatree.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.dynatree.js"></script>

<style>

.b{ font-weight:bold;}
.tr_bg{background: #F4FAFB}
.text_l{ text-align: left;}
.text_r{ text-align: right;}

.clear_lb{
	border-left:0;
}
.clear_rb{
	border-right:0;
}
.clear_tb{
	border-top:0;
}

td.rb{border-right:1px solid #aed0ea;}
td.bb{border-bottom:1px solid #aed0ea;}
td.lb{border-left:1px solid #aed0ea;}
td.tb{border-top:1px solid #aed0ea;}
td.bb{border:1px solid #aed0ea;}	

#tree { 
      vertical-align: top; 
      width: 250px; 
      border:0px;
      padding:0px;
} 
#tree ul{		
	border:0px;
	background-color:#F4FAFB;
}
#s1 ul li{
	list-style-type:none;
}
#s1 ul li span{
	width:300px;
}

#s1 table tr {
	background-color: white;
}

.popDiv {
	display:none;
	width:280px;
	height:70px;
	background-color:#D1EEEE;
	border:1px solid gray;
	padding-left:5px;
	position:absolute;
	padding-top:3px;
	z-index:1021;
	padding-right:5px;
	left: 350px;
	top: 30px;
}


span.pause a:link {
    color:#B4B4B4;
    text-decoration:underline;
}

span.pause a:visited {
    color:#B4B4B4;
    text-decoration:none;
}

span.pause a:hover {
    color:#B4B4B4;
    text-decoration:none;
}

span.pause a:active {
    color:#B4B4B4;
    text-decoration:none;
}
.dynatree-title{
	height: 20px;
	line-height:20px;
}
.servP{
	display:none;
}
.tya{background-color:RGB(245, 250, 250);}
.tyaT td{border:1px solid #bbb;height:30px;}
.servP td{border:1px solid #bbb;}
#page_serv a{margin-right:5px;}
</style>
</head>
<freeze:body>
<freeze:title caption="用户信息" />
  <%
  DataBus db = (DataBus)request.getAttribute("freeze-databus");
  DataBus db2 = db.getRecord("info-base");
  String uid = db2.getValue("sys_svr_user_id");
  System.out.println(db);

   %>
<freeze:errors/>
<script language="javascript">
var uid = getFormFieldValue("info-base:sys_svr_user_id");

//目前只支持webservice选择
var serviceCanUse = new Array;
var viewCanUse = new Array;
var userCanCopy = new Array;
var lastSvrKey ='';
var firstKey = "";
var snameTemp="";
var sidTemp="";
var uidTemp = "<%=uid%>";
var obj = null;

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
  
   initTree();
 //  initSvrSource();
  
  // initUserCopy();

   $('#btn_config').click(function(){
   		if($('#configDiv').height()>500){
   			$('#configDiv').css("height","450");
   			$('#configDiv').css("overflow-y","scroll");
   		}
   		$('#configDiv').show();
   		$('#s2').hide();
   		$('#s3').hide();
   })
   
   $("#svrSourceTd input").click(function(){
   		if( $(this).val()=='2' ){
   			$('#s2').show();
   			$('#s3').show();
   			$('#s1').hide();
   		}else if( $(this).val()=='1' ){
   			$('#s2').hide();
   			$('#s3').hide();
   			$('#s1').show();
   		}
   		if($('#configDiv').height()>500){
   			$('#configDiv').css("height","450");
   			$('#configDiv').css("overflow-y","scroll");
   		}else{
   			$('#configDiv').css("height","auto");
   			$('#configDiv').css("overflow-y","auto");
   		}
   })
   
   if($("#interInfo").height()>600){
   	$("#interInfo").css("height","600");
   	$("#interInfo").css("overflow-y","scroll");
   }
   if($("#interInfo").height()>600){
   	$("#interInfo").css("height","600");
   	$("#interInfo").css("overflow-y","scroll");
   }
   if($("#tree").width()>245){
   	$("#tree").css("width","246");
   	$("#tree").css("overflow-x","scroll");
   }
}

function initTree(){
	
	var page = new pageDefine("/txn50200021.ajax", "查询某个用户的服务树");
	page.addParameter(  "info-base:sys_svr_user_id","select-key:sys_svr_user_id" );
	page.callAjaxService("callBack()");
	
}

function callBack(errCode, errDesc, xmlResults){
		if(errCode!='000000'){
			alert("查询某个用户的服务树时发生错误！")
			return
		}

	 var userIds =  _getXmlNodeValues(xmlResults,'record:sys_svr_user_id');
	 var svrIds = _getXmlNodeValues(xmlResults,'record:sys_svr_service_id');
	 var svrName =  _getXmlNodeValues(xmlResults,'record:svr_name');
	 var svrType =  _getXmlNodeValues(xmlResults,'record:svr_type');
	 var isLimit =  _getXmlNodeValues(xmlResults,'record:is_limit');
	 var maxRecords =  _getXmlNodeValues(xmlResults,'record:max_records');
	 var isPause =  _getXmlNodeValues(xmlResults,'record:is_pause');
     var s = new Array;
     if(userIds!=null&&userIds.length>0){
     	firstKey = svrIds[0];
    	 for(var j=0; j<userIds.length; j++){
    	 	s.push({'title':"("+svrType[j]+")"+svrName[j],'svrName':svrName[j],'key':svrIds[j],'stype':svrType[j],'isLimit':isLimit[j],'maxRecords':maxRecords[j],'user_id':userIds[j],'is_pause':isPause[j]})
    	 }
    	lastSvrKey =  svrIds[userIds.length-1];
     }
   
     
     var r1 =  {"title": "共享接口"+"("+userIds.length+")", "isFolder": true, "key": "1", "children": s }
     var r2 = {"title": "采集接口(0)", "isFolder": true, "key": "2" }
     var root = new Array;
     root.push(r1);
     root.push(r2);

     obj = $("#tree").dynatree({
           children: root,
           minExpandLevel: 2,
		   onActivate: function(node) {
			  if( node.data.key ){
			  		if(node.data.key!="1"&&node.data.key!=""&&node.data.key!="2"){
			  			if(node.data.stype!=""&&node.data.stype=="W"){
				  				var page = new pageDefine( "/txn50200022.do", "用户服务WS配置查看" );
								//tree-sys_svr.jsp
								page.addValue(node.data.user_id, "select-key:sys_svr_user_id");
								page.addValue(node.data.key, "select-key:sys_svr_service_id");
								page.addValue(node.data.isLimit, "select-key:is_limit");
								page.addValue(node.data.stype, "select-key:stype");
								page.addValue(node.data.svrName,'select-key:svr_name');
								page.addValue(node.data.is_pause,'select-key:is_pause');
								var win = getIframeByName('svr_config');
								if( win != null ){
									page.goPage( null, win );
								}
				  				//alert(node.data.key + ' -- ' +node.data.user_id+' -- '+node.data.isLimit+' --' + node.data.stype)
				  		}else if(node.data.stype!=""&&( node.data.stype=="V" || node.data.stype=="CV") ){
				  				var page = new pageDefine( "/txn50200023.do", "用户服务视图配置查看" );
								//tree-sys_svr.jsp
								page.addValue(node.data.user_id, "select-key:sys_svr_user_id");
								page.addValue(node.data.key, "select-key:sys_svr_service_id");
								//page.addValue(node.data.isLimit, "select-key:is_limit");
								page.addValue(node.data.stype, "select-key:stype");
								var win = getIframeByName('svr_config');
								if( win != null ){
									page.goPage( null, win );
								}
				  			
			  			}
			  		}
				}
			
			}
		});
	//将暂停的节点置灰
	var child=root[0].children;
	for(var i=0;i<child.length;i++){
	   if(child[i].is_pause=='1'){
	      var tnode = $("#tree").dynatree("getTree").getNodeByKey(child[i].key);
	      tnode.data.addClass="pause";
	      tnode.render();
	   }
	}
	
	//默认查询用户第一个服务情况	
	if(firstKey!=""){
	   	var fnode = $("#tree").dynatree("getTree").getNodeByKey(firstKey);
	   	fnode.activate();
	}
	 initSvrSource();
}

function initSvrSource(){
	var page = new pageDefine("/txn50200024.ajax", "查询某个用户可以配置的WebService服务或视图");
	page.addParameter(  "info-base:sys_svr_user_id","select-key:sys_svr_user_id" );
	page.callAjaxService("callBack2()");
}

function callBack2(errCode, errDesc, xmlResults){
	if(errCode!='000000'){
			alert("查询某个用户可以配置的WebService服务或视图时发生错误！")
			return
	}
	  var svrName =  _getXmlNodeValues(xmlResults,'record:svr_name');
	  var svrIds =   _getXmlNodeValues(xmlResults,'record:sys_svr_service_id');
	  var stype = _getXmlNodeValues(xmlResults,'record:stype');
	  if(svrIds!=null&&stype!=null&&svrIds.length>0){
	 	 for(var j=0; j<svrIds.length; j++){
	 		if( stype[j]!=null&&stype[j]=='W'){
	 			serviceCanUse.push({ 'id':svrIds[j],'name':svrName[j] } );
	 		}else if( stype[j]!=null&&stype[j]=='V'){
	 			viewCanUse.push({ 'id':svrIds[j],'name':svrName[j] } );
	 		}	
	 	 }
	  }
	createSvrSource();
	initUserCopy();
}

function initUserCopy(){
	var page = new pageDefine("/txn50200025.ajax", "查询某个用户可以拷贝的用户");
	page.addParameter(  "info-base:sys_svr_user_id","select-key:sys_svr_user_id" );
	page.callAjaxService("callBack3()");
}

function callBack3(errCode, errDesc, xmlResults){
	if(errCode!='000000'){
			alert("查询某个用户可以拷贝的用户时发生错误！")
			return
	}
	  var names =  _getXmlNodeValues(xmlResults,'record:user_name');
	  var ids =   _getXmlNodeValues(xmlResults,'record:sys_svr_user_id');
	  var loginNames = _getXmlNodeValues(xmlResults,'record:login_name');
	  if(names!=null&&names.length>0){
	 	 for(var j=0; j<names.length; j++){  
	 		userCanCopy.push({ 'id':ids[j],'name':names[j],'login': loginNames[j] } );
	 	 }
	  }
	createUserSource();
}

function createSvrSource(){
	 var str = "<td><table width='100%' cellpadding=0 cellspacing=0 border=0 >" ;
	 if(serviceCanUse.length>0){
	 str+="<tr style='color:#003366;background:#FFFFEE;'><td height='30' width='10%' align='center'>序号</td><td height='30' width='80%'>服务名称</td><td width='10%' align='center'>选择</td></tr>"
	 str+="<tr><td colspan='3'>";
	 	for(var j=0; j<serviceCanUse.length; j++){
	 		if( j==0 || j%10 == 0){
	 			str+= "<table cellspacing='0' cellpadding='1' border='1' style='border-collapse:collapse;border:1px solid #666;' class='servP' width='100%' id='servP_"+j+"'>";
	 			str+="<tr><td height='30' width='10%' align='center'>"+(j+1)+"</td><td width='80%'>"+serviceCanUse[j].name+"</td><td width='10%' align='center'><input type='radio' name='svrs' sid='"+serviceCanUse[j].id+"' sname='"+serviceCanUse[j].name+"'></td></tr>"
	 		}else if(j%10 == 9){
	 			str+="<tr><td height='30' width='10%' align='center'>"+(j+1)+"</td><td width='80%'>"+serviceCanUse[j].name+"</td><td width='10%' align='center'><input type='radio' name='svrs' sid='"+serviceCanUse[j].id+"' sname='"+serviceCanUse[j].name+"'></td></tr>"
				str+="</table>";
	 		}else{
	 			str+="<tr><td height='30' width='10%' align='center'>"+(j+1)+"</td><td width='80%'>"+serviceCanUse[j].name+"</td><td width='10%' align='center'><input type='radio' name='svrs' sid='"+serviceCanUse[j].id+"' sname='"+serviceCanUse[j].name+"'></td></tr>"
			}
	 	}
	 	str+="</table></td></tr><tr><td align='right' height='30' colspan='3'><div id='page_serv'></div></td></tr>";
	 }
	 str+="</table></td>";
	 $('#s1').html(str);
	 servPage(0,Math.ceil(j/10),0);
}

/*新增用户接口分页显示*/
function servPage(index,totalP,goP){
	var pCount = $(".servP").length;
	$(".servP").css("display","none");
	if(index<0 || index>=pCount){
		index = 0;
	}
	if(goP==-1){
		document.getElementById("servP_"+index*10).style.display = 'block';
	}else if(goP==pCount){
		document.getElementById("servP_"+(totalP-1)*10).style.display = 'block';
	}else if(goP==1){
		document.getElementById("servP_"+index*10).style.display = 'block';
	}else{
		document.getElementById("servP_0").style.display = 'block';
	}
	var cont = "<a href='javascript:servPage(0,"+totalP+",0)'>首页</a>";
	cont += "<a href='javascript:servPage("+(index-1)+","+totalP+",-1)'>上一页</a>";
	cont += "<a href='javascript:servPage("+(index+1)+","+totalP+",1)'>下一页</a>";
	cont += "<a href='javascript:servPage("+(pCount-1)+","+totalP+","+totalP+")'>末页</a>";
	cont += "共有"+pCount+"页";
	$("#page_serv").html(cont);
}

function createUserSource(){
	var str = "<td>选择用户:";
	var str2 = "<td id='userSvrTD'>选择服务:" ;
	  if(userCanCopy.length>0){
	  	str+="<select name='userSelected' onchange='setUserSvrSeleted();' id='userSelected' ><option value='0'>请选择...</option>";
	  		for(var j =0; j<userCanCopy.length; j++){
	  			str+="<option name='userSelected' value='"+userCanCopy[j].id+ "'>" +userCanCopy[j].name+"("+userCanCopy[j].login+")</option>"
	  		}
	  	str+="</select>"
	  }
	 str+="</td>";
	 str2+="</td>"
	// alert(str);
	 $('#s2').html(str);
	 $('#s3').html(str2);
	 
}
function setUserSvrSeleted(){
	var s = $("#userSelected option:selected").val();
	if(s&&s!="0"){
		var page = new pageDefine("/txn50200029.ajax", "查询某个用户可以拷贝的用户的服务列表");
		page.addParameter(  "info-base:sys_svr_user_id","select-key:sys_svr_user_id" );
		page.addValue( s,"select-key:sys_svr_user_id2" );
		page.callAjaxService("callBack4()");
	}
}
function callBack4(errCode, errDesc, xmlResults){
	if(errCode!='000000'){
			alert("查询某个用户可以拷贝的用户的服务列表时发生错误！")
			return
	}
	var sname =  _getXmlNodeValues(xmlResults,'record:svr_name');
    var sid =   _getXmlNodeValues(xmlResults,'record:sys_svr_service_id');
	var cfgid = _getXmlNodeValues(xmlResults,'record:sys_svr_config_id');
	var str="<table class='tyaT' cellspacing='0' cellpadding='1' border='1' style='background:#FFFFFF;border-collapse:collapse;border:1px solid #666;' width='100%' ><tr style='color:#003366;background:#FFFFEE;'><td height='30' width='10%' align='center'>序号</td><td hegiht='30' width='70%'>服务名称</td><td hegiht='30' width='20%' align='center'>选择</td></tr>";
	 if(sid&&sid.length>0){
	 	for(var j=0; j<sid.length; j++){
	 		str+="<tr><td hegiht='30' width='10%' align='center'>"+(j+1)+"</td><td hegiht='30' width='70%'>"+sname[j]+"</td><td hegiht='30' width='20%' align='center'><input type='radio' name='svrs' sid='"+sid[j]+"' sname='"+sname[j]+"'  cfgid='"+cfgid[j]+"'></td></tr>"
	 	}
	 }
	str+="</table>"
	$('#userSvrTD').html(str);
	 
}

function addNode(name,sid,uid){
	var node1 = $("#tree").dynatree("getTree").getNodeByKey(sid);
	var node  = $("#tree").dynatree("getTree").getNodeByKey('1');
	if(node1!=null){
		$("#tree").dynatree("getTree").reactivate(node1)
		//node1.activate();
	}else{
		var str = "(W)"+name;
		if(node!=null){
			node.addChild({title: str, key: sid, stype:'W', isLimit:'0',user_id:uid });
		}
		var node2 = $("#tree").dynatree("getTree").getNodeByKey(sid);
		node2.activate();
	}
	
}

function endConfig(){
	$('#configDiv').hide();
	var a = $("input[name='svrSource']:checked").val() ;
	if(a&&a=='1'){
		//选择服务
		$("input[name='svrs']:checked").each(function(){
			var sid = $(this).attr('sid');
			var name = $(this).attr('sname');
			//addNode(name,sid)
			
			var uid = getFormFieldValue("info-base:sys_svr_user_id");
			var userName =  "<freeze:out value='${info-base.user_name}'/>"
			//alert(userName+' -- '+ uid+" -- "+ sid);
			var page = new pageDefine( "insert-sys_svr_config_steps2.jsp","xx","modal", 800, 600);
			//tree-sys_svr.jsp
			page.addValue( sid, "svrId");
			page.addValue( uid , "userId");
			page.addValue( name,'svrName');
			page.addValue( userName, "userName");
			page.addValue("new","actiont");
			page.updateRecord( );
		})
	}else if(a&&a=='2'){
		//选择拷贝用户
		$("input[name='svrs']:checked").each(function(){
			var sid = $(this).attr('sid') ;
			var cfgid = $(this).attr('cfgid');
			var sname = $(this).attr('sname');
			if(cfgid&&cfgid!=""){
					sidTemp = sid;
					snameTemp = sname;
					var page = new pageDefine("/txn50200030.ajax", "拷贝某个用户可以拷贝的用户服务配置信息");
					page.addParameter(  "info-base:sys_svr_user_id","select-key:sys_svr_user_id" );
					page.addValue( sid,"select-key:sys_svr_service_id" );
					page.addValue( cfgid,"select-key:sys_svr_config_id" );
					page.callAjaxService("callBack5()");
			}
		})
	}
	window.location.reload();
}

function callBack5(errCode, errDesc, xmlResults){
	if(errCode!='000000'){
			alert("拷贝某个用户可以拷贝的用户服务配置信息时发生错误！")
			return
	}
	alert('拷贝用户服务成功！')
	//alert(snameTemp +' sss ' + sidTemp +' fff ' + uidTemp);
	addNode(snameTemp,sidTemp,uidTemp);
	
	$('#userSelected').val('0');
}

function closeConfig(){
 	 $('#configDiv').hide();
}

function resetParentTree(svrName,svrId,userId){
	var node1 = $("#tree").dynatree("getTree").getNodeByKey(svrId);
	if(node1!=null){
		$("#tree").dynatree("getTree").reactivate(node1)
	}
}

function showDiv(which){
	document.getElementById(which).style.display = 'block';	
	if(which == 'editip'){
		document.getElementById( 'ip1').focus();
	}else{
		document.getElementById( 'pwd').focus();
	}
}

function hideDiv(){
	document.getElementById('editip').style.display='none';
	document.getElementById('editpwd').style.display='none';
	document.getElementById( 'pwd').value='';
	for(var ii = 1;ii<5;ii++)
		document.getElementById("ip"+ii).value = NULL;
}
var ip_bind = '';
function updateIP(){
	var ip1 = document.getElementById("ip1").value.trim();
	var ip2 = document.getElementById("ip2").value.trim();
	var ip3 = document.getElementById("ip3").value.trim();
	var ip4 = document.getElementById("ip4").value.trim();
	ip_bind = ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
	var ip_flag = checkIP(ip_bind);
	if(ip_flag == false){	
		return;
	}
	
    var page = new pageDefine( "/txn50200027.ajax", "修改用户绑定IP");
    page.addValue(ip_bind,"record:ip_bind");
    page.addValue("1","record:is_ip_bind");
    page.addParameter( "info-base:sys_svr_user_id","record:sys_svr_user_id" );
    page.callAjaxService('updateUserIP()');
}
	
function updateUserIP(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
//			alert("修改成功.");
			document.getElementById("ip").innerHTML  = ip_bind;
			document.getElementById("editip").style.display = 'none';	
			for(var ii = 1;ii<5;ii++)
				document.getElementById("ip"+ii).value = ''; 
		}else{
		alert("修改绑定IP出错.");
		for(var i = 1;i<5;i++)
			document.getElementById("ip"+i).value = ''; 
	}
}

function updatePWD(){
	var pwd = document.getElementById("pwd").value;
    var page = new pageDefine( "/txn50200028.ajax", "修改用户绑定IP");
	page.addValue(pwd,"record:password");
    page.addParameter( "info-base:sys_svr_user_id","record:sys_svr_user_id" );
    page.callAjaxService('updateUserPWD()');
}
	
	function updateUserPWD(errCode, errDesc, xmlResults){
	if(errCode=='000000'){
//			alert("成功");
			var newpwd = document.getElementById('pwd').value;
			document.getElementById("userpwd").innerHTML  = newpwd;
			document.getElementById("editpwd").style.display = 'none';
		}else{
			alert("修改密码出错.");
			document.getElementById("pwd").value = '';
	}
}

function checkIP(ipstr){
	if(ipstr.length<7){
		alert("请输入完整IP");
		return false;
	}
	var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;  
    var arr = reg.test(ipstr);  
     if(!arr){  
     	alert("绑定IP错误.");
         return false;
     }
}

function ip_onkeyup(index){
	if(document.getElementById("ip"+index).value.length == 3){
		index++;
		document.getElementById("ip"+index).focus();
	}
}

function changeClass(sid,state){
   var tnode = $("#tree").dynatree("getTree").getNodeByKey(sid);
   if(state=='0'){
     tnode.data.addClass="pause";
   }else{
     tnode.data.addClass="";
   }
   tnode.render();
}

_browse.execute( '__userInitPage()' );
	
</script>

<div id='configDiv' style='display:none;  
					position:absolute; z-index:9005;left:50%;
					margin-top:3px; margin-left:-200px;padding-right:15px;'>
	  
	   	 
		<table width="450px" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0" > 
			<tr>
				<td style="padding:0px;">
				<table width="100%" border="0" cellSpacing="0" cellPadding="0">
					<tr><td class="leftTitle"></td><td class="secTitle"></td>
						<td class="centerTitle">
							<freeze:button name="btnEndConfig" caption="完成" id='btnEndConfig'  onclick='endConfig()' styleClass="grid-menu"/>
							<freeze:button name="btnEndConfig" caption="关闭" id='btnEndConfig'  onclick='closeConfig()' styleClass="grid-menu"/>
						</td><td class="rightTitle"></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr><td style="padding:0px;">
				<table style="background:#fff;border:2px solid #006699;border-collapse:collapse;" width="100%" border="0" cellSpacing="0" cellPadding="0">
				<tr>  
				<td style="border:1px solid #6699CC;background:#FFF;" height='30'>接口类型:<select name='svr_type' disabled>
												<option value='1' selected>WebService</option>
												<option value='2' >共享视图</option>
												<option value='2'>自定义视图</option>
										   </select>
               </td>
            </tr>
            <tr style='display:block;' >  
               <td style="border:1px solid #6699CC;background:#FFF;"  id='svrSourceTd' height='30'>接口来源:
					<input type='radio' name="svrSource" id='svrSource1' value='1' checked /><label for="svrSource1">引用全局</label>
					<input name='svrSource' id='svrSource2' type='radio' value='2' /><label for="svrSource2">复制已有</label>
               </td>
			</tr>
			<tr id='s1' style='display:block'>
			</tr>
			<tr id='s2' class=framerow style='display:block'>
		
			</tr>
			<tr id='s3' class=framerow style='display:block'>
		
			</tr>
				</table>
			</td></tr>	   
	    </table>
	
</div>

<div id="editpwd" style="background-color:#ccc;" class="popDiv">
	<span style="float:right;cursor:pointer;" onclick="hideDiv();">×</span>
		用户名称:<freeze:out value="${info-base.user_name}"/> <hr />
		<table width="95%">
		<tr><td align="center">密码:<input type="text"  name="pwd" id="pwd" /></td>
		<td>
		<table cellspacing="0" cellpadding="0" class="button_table">
		<tr><td class="btn_left"></td><td>
		<input type="button" class="menu" onclick="updatePWD();" value="确认" />
		</td><td class="btn_right"></td></tr></table>
		</td>
		</tr>
		</table>
		<center>
</div>
<div id="editip" style="background-color:#ccc;" class="popDiv">
		<span style="float:right;cursor:pointer;" onclick="hideDiv();">×</span>
		用户名称:<freeze:out value="${info-base.user_name}"/> <hr />
		<center>绑定IP:<input onkeyup="return ip_onkeyup(1);" style="width:30px;" maxlength="3" id="ip1" value="" type="text" title="绑定IP地址" >.
	  <input onkeyup="return ip_onkeyup(2);" style="width:30px;" id="ip2" value="" maxlength="3" type="text" title="绑定IP地址" >.
	  <input onkeyup="return ip_onkeyup(3);" style="width:30px;" id="ip3" value="" maxlength="3" type="text" title="绑定IP地址" >.
	  <input  style="width:30px;" id="ip4" value="" maxlength="3" type="text" title="绑定IP地址" >
		<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
		<input type="button" id="" class="menu" onclick="updateIP();" value="确认" />
		</td><td class="btn_right"></td></tr></table>
		</center>
</div>
<div id="containter" >
<table width="95%" align="center" cellpadding="0" cellspacing="0" border="0"  height="100%">
	<tr height="40px">
    	<td colspan="2">
			<table width="100%">
			<tr>
				<td class="text_l bb" width="8%" style="color:#1F62B3;border:0px;display:none;">用户信息</td>
				<td class="text_l bb" style="color:#1F62B3;background:#FFF;">
				 &nbsp;用户名称:<freeze:out value="${record.user_name}"/> 
				 &nbsp;用户代码:<freeze:out value="${record.login_name}"/> 
				 &nbsp;用户密码:<SPAN id="userpwd"><freeze:out value="${record.password}"/> </SPAN>
				 <span style="padding-left:5px;"><img title="修改密码" id="pwdimg" onclick="showDiv('editpwd');" style="margin-top:2px; cursor:pointer;" width="16px" height="16px" src="<%=request.getContextPath()%>/images/edit_ip.gif" /></span>

				 &nbsp;绑定IP:<span id="ip"><freeze:out value="${record.ip_bind}"/></span>
				<span style="padding-left:5px;"><img title="修改绑定IP" id="ipimg" onclick="showDiv('editip');" style="margin-top:2px; cursor:pointer;" width="16px" height="16px" src="<%=request.getContextPath()%>/images/edit_ip.gif" /></span>
				</td> 
				 <td width="15%" align="right">
				 <freeze:button name="record_addRecord" caption="返回" onclick="goBack();" styleClass="menu"/>
				</td>
	       	 </tr>
	   		 </table>       
        </td>
    </tr>
    <tr>
    	<td rowspan="2" valign="top" >
	    	<div id="interInfo" style="width:100%;"><table width="98%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
				    <tr><td >
				<table width="100%" cellspacing="0" cellpadding="0" border="0">
					<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;接口信息</td>
						<td class="centerTitle">
						<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
						<INPUT style="WIDTH: 50px" class='grid-menu' id="btn_config" value='增加' type=button  name='btn_config' />
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
						</td>
						<td class="rightTitle"></td></tr>
				</table> 
 			</td>
 		</tr>    
				    <tr class="framerow" style="backgroud-color:#ebffed;" >
			         	 <td style="padding:0px;" colspan='2'><div id="tree" style="border:2px solid #006699;"></div></td>
			        </tr>
	    	</table></div>
    	</td>
    	<td width="80%" valign="top" >
		 <iframe id='svr_config'  name='svr_config' scrolling='no' frameborder=0 width=100% height=100% style="display:block;background-color:#F4FAFB;">
		</iframe>
        </td>
    </tr>
    <tr> <td></td></tr>
   
</table>
</div> 
<freeze:frame property="info-base">
  	<freeze:hidden property="sys_svr_user_id" />
</freeze:frame>
</freeze:body>
<script language="javascript">
</script>
</freeze:html>
