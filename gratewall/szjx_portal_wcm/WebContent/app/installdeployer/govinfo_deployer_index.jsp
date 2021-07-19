<%--
/*
 *	History			Who			What
 *	2011-02-10		lhm		    安装后部署的公共处理页面
 *
 */
--%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cluster.TRSWCMClusterServer" %>
<%@ page import="com.trs.deployer.domain.DeployerController"%>
<%@ page import="com.trs.deployer.common.DeployerConstants"%>
<%@ page import="com.trs.DreamFactory" %>
<%@include file="../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%
	//部署过程实际上是在执行一系列的处理类，某些处理类之间是有先后顺序的，这些有顺序的类可以在此执行
	//以下数组中的类的顺序即为执行的顺序，先执行指定了顺序的这部分类，其他未在此指定的类在后面执行
	String[] handlerOrderArray = new String[]{
		"com.trs.deployer.domain.impl.govinfo.GovSysInitControllerHandler",
		"com.trs.deployer.domain.impl.govinfo.GovinfoViewImportorHandler",
		"com.trs.deployer.domain.impl.govinfo.DeptCodeHandler",
		"com.trs.deployer.domain.impl.govinfo.BuildGovinfoSystem",
		"com.trs.deployer.domain.impl.govinfo.SubSiteCreatorController",
		"com.trs.deployer.domain.impl.govinfo.SubSiteCreatorHandler",
		"com.trs.deployer.domain.impl.govinfo.UserPassWordFileDownloaderHandler",
		"com.trs.deployer.domain.impl.govinfo.GovInfoWebsiteImportor",
		"com.trs.deployer.domain.impl.govinfo.NoticeFilePathHandler",
		"com.trs.deployer.domain.impl.govinfo.GovInfoDefaultValueModifyHandler",
		"com.trs.deployer.domain.impl.govinfo.ResourceDistributeToSitesHandler",
		"com.trs.deployer.domain.impl.govinfo.FirstPageSettingHandler",
		"com.trs.deployer.domain.impl.govinfo.ViewCreatorHandler"
	};
%>
<%	
	//权限的判断
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行政府信息公开服务系统的创建！");
	}
	DeployerController controller = DeployerController.newInstance(loginUser, 1);
	if(controller.isRunning()){
		out.print("已经有一个部署程序正在运行。。。");
		return;
	}
	// 判断是否是集群环境
	boolean hasTRSWCMClusterServer = true;

	TRSWCMClusterServer server = (TRSWCMClusterServer) DreamFactory.createObjectById("TRSWCMClusterServer");
	if(server.getContext()==null){
	    hasTRSWCMClusterServer = false;
	}

	if(!"1".equals(request.getParameter("isConfirm"))){
%>
	<%if(hasTRSWCMClusterServer){%>
		<h3><font color="red">当前运行环境是集群环境，请先启动所有节点后，再到一个具体的节点(而不是前端apache)来完成升级后的部署。</font></h3><BR>
		如果已经启动了所有节点，并且当前访问的是具体节点，直接点击<a href="#"><span style="color:red;" onclick="location.href = location.href + '?isConfirm=1';return false;">这里</span></a>开始部署。
	<%}else{%>
		<BR>
		当前运行环境不是集群环境，可以直接点击<a href="#"><span style="color:red;" onclick="location.href = location.href + '?isConfirm=1';return false;">这里</span></a>开始部署
	<%}%>
<%	
		return;
	}
	controller.resetParams();
	controller.setParameter(DeployerConstants.EXECUTE_ORDER, handlerOrderArray);
	controller.start();
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>正在进行政府信息公开的部署。。。</title>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css"/>
<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
		width:100%;
		height:100%;
		overflow:hidden;
	}
	body{
		background-color:#fcf2ea;
	}
	.allmessage div{
		font-size:14px;
		padding:5px;
		padding-left:20px;
	}
	.processinfo{
		font-size:16px;
		padding:5px;
		padding-bottom:10px;
	}
	.errorInfo{
		font-size:16px;
		color:red;
		padding:5px;
		padding-bottom:10px;
	}
	.notExecute{
		background:url("./images/exe_up.png") no-repeat center left;
	}
	.completed{
		color:green;
		background:url("./images/exe_comp.png") no-repeat center left;
	}
	.active{
		color:green;
		font-weight:bold;
		font-style:italic;
		background:url("./images/exe_act.png") no-repeat center left;
	}
	.execute_info{
		padding:10px;
	}
	/*设置CrashBoard右上角的关闭按钮隐藏
		对应jira问题： WCMUPD-42
	*/
	#deployer-closer{
		display : none;
	}
</style>
</head>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/lightbase.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/extrender.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/ajax.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/elementmore.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Observable.js"></script>
<!--wcm-dialog start-->
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Component.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- CarshBoard Outer End -->
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<script language="javascript">

<!--
var bStop = false;
function queryMessage(){
	if(bStop) return;
	new ajaxRequest({
		url : './handler_execute_message.jsp',
		method : 'post',
		onSuccess : function(trans){
			var sResponse =  trans.responseText;
			eval("var result =" + sResponse );
			if(result.allmessage != '' && $('allmessage').innerHTML.trim() == ''){
				$('allmessage').innerHTML = result.allmessage;
			}
			if(result.processinfo != ''){
				$('errorInfo').innerHTML = result.processinfo;
			}
			var messageParentEl = $('allmessage');
			var messageEls = messageParentEl.getElementsByTagName('DIV');
			if(result.currclassname != ''){
				for(var i=0; i<messageEls.length; i++){
					var el = messageEls[i];
					//未执行完所有的处理类，则当前元素设置为正在进行
					if(result.currclassname == el.getAttribute('currclassname')){
						Element.addClassName(el, 'active');
						//找到一个正在进行的记录，则结束循环
						break;
					}else{
						//之前的元素都设置为完成
						Element.removeClassName(el, 'active');
						Element.addClassName(el, 'completed');
					}
				}
			}
			//需要页面来协助完成的handler
			if("true" == result.bwait.toLowerCase() && result.pageurl != ''){
				var pageURL = result.pageurl;
				if("false" == result.needopenpage.toLowerCase()){
					//仅仅是发出一个请求，不需要弹出页面
					new ajaxRequest({
						url : pageURL,
						method : 'post',
						onSuccess : function(tran){
							//将执行完的信息设置到界面中
							eval("var resultInfo =" + tran.responseText );
							new ajaxRequest({
								url : 'set_handler_excute_param.jsp',
								method : 'post',
								parameters : $toQueryStr(resultInfo),
								onSuccess : function(){
									return false;
								}
							});
						}
					});
				}else{
					//打开信息输入页面
					wcm.CrashBoarder.get('deployer').show({
						title : '部署环境',
						src : pageURL,
						width: '200px',
						height: '150px',
						callback : function(param){
							//发送请求到设置参数的页面中
							new ajaxRequest({
								url : 'set_handler_excute_param.jsp',
								method : 'post',
								parameters : $toQueryStr(param),
								onSuccess : function(){
									// 在回调函数中发送请求，设置是否需要等待参数。避免页面被关闭时，ajax请求被取消，导致无法执行后面的步骤
									// 对应jira问题： WCMUPD-42
									new ajaxRequest({
										url : 'set_handler_excute_param.jsp',
										method : 'post',
										parameters : 'BWAITPAGEINPUTPARAMS=false',
										onSuccess : function(){
										}
									});
								}
							})
						}
					});
				}
				//发个请求，将地址清空
				new ajaxRequest({
					url : 'set_handler_excute_param.jsp',
					method : 'post',
					parameters : "pageurl=",
					onSuccess : function(){
						setTimeout(queryMessage, 1000);
						return false;
					}
				});
				return false;
			}
			//未结束的情况下，再去请求
			if(result.bCompleted == '' || "false" == result.bCompleted.toLowerCase()){
				setTimeout(queryMessage, 1000);
			}else {
				//已经结束，将所有元素的状态设置为完成
				for(var i=0; i<messageEls.length; i++){
					var el = messageEls[i];
					Element.removeClassName(el, 'active');
					Element.addClassName(el, 'completed');
				}
				//设置总的进度信息为完成部署
				document.getElementById("processinfo").innerText = '政府信息公开部署完成！';
			}
		}
	});
	return false;
}
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + encodeURIComponent(value));
	}
	return rst.join('&');
}
Event.observe(window, 'load', queryMessage);
function cancel(){
	bStop = true;
	new ajaxRequest({
		url : './finish_deployer.jsp',
		method : 'post'
	});
	$('processinfo').innerHTML = '部署被终止，请参考安装部署手册进行重新初始化！'
}

// 离开页面之前，终止部署程序
Event.observe(window, 'beforeunload', function(){
	if(!bStop){
		cancel();
		return "离开当前页面会重新开始部署！";
	}
});

//-->
</script>
<body>
<div class="execute_info">
	<div class="processinfo" id="processinfo">
		正在进行政府信息公开的部署,停止部署请点击<a href=""><span style="color:red;" onclick="cancel();return false;">取消</span></a>，并关闭当前窗口。
	</div>
	<div class="errorInfo" id="errorInfo">
	</div>
	<div class="allmessage" id="allmessage">
	</div>
</div>

</body>
</html>