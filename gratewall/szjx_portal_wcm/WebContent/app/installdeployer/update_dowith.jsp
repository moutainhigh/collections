<%--
/*
 *	History			Who			What
 *	2012-09-17		lhm		     升级fix的主界面
 *
 */
--%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.deployer.common.DeployerConstants"%>
<%@ page import="com.trs.cluster.TRSWCMClusterServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.update.wcm.UpdateFixController" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@include file="../include/public_server.jsp"%>
<!------- WCM IMPORTS END ------------>
<%	
	//权限的判断
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行更新fix操作！");
	}
	
	//确定是否开始更新
	String sIsConfirm = request.getParameter("isConfirm");
	if(CMyString.isEmpty(sIsConfirm) || !"1".equals(sIsConfirm)){
		throw new WCMException("传入的参数isConfirm无效！");
	}
	
	//当前程序只能有一个在执行
	UpdateFixController controller = UpdateFixController.newInstance(loginUser,true);
	if(controller.isRunning()){
		out.print("已经有一个更新fix程序正在运行。。。");
		return;
	}

	Map needUpdateFixes = controller.getNeedUpdateFixes();
	if(needUpdateFixes.size() == 0){
		out.println("当前系统没有需要更新的fix");
		return;
	}
	ArrayList allFixes = controller.getAllFixes();
	//先清除下内部缓存参数
	controller.resetParams();
	//开始更新
	controller.start();
out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统正在更新fix。。。</title>
<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
		width:100%;
		height:100%;
		overflow:auto;
	}
	.updateItem{
		font-size:14px;
		padding:5px;
		cursor:pointer;
	}
	.processorbar{
		font-size:30px;
		margin-top:30px;
		margin-bottom:30px;
		color:green;
	}
	#conflictfile{
		font-size:16px;
		padding-bottom:10px;
	}
	.errorInfo{
		color:red;
		font-size:30px;
		margin-top:30px;
		margin-bottom:30px;
	}
	.noticeInfo{
		color:red;
		font-size:14px;
		margin-top:15px;
		margin-bottom:15px;
		margin-left:5px;
	}
	.execute_info{
		padding:10px;
	}
	.itemtitle{
		padding-left:20px;
	}
	.notExecute .itemtitle{
		background:url("./images/exe_up.png") no-repeat center left;
	}
	.completed .itemtitle{
		color:green;
		background:url("./images/exe_comp.png") no-repeat center left;
	}
	.warning .itemtitle{
		color:red;
		background:url("./images/exe_warning.png") no-repeat center left;
	}
	.error .itemtitle{
		color:red;
		background:url("./images/exe_error.png") no-repeat center left;
	}
	.active .itemtitle{
		color:green;
		font-weight:bold;
		font-style:italic;
		background:url("./images/exe_act.png") no-repeat center left;
	}
	.detailinfo{
		padding-left:20px;
		font-size:12px;
	}
	.messagetitle{
		font-size:16px;
		margin-bottom:10px;
	}
	.Manualworkinfo{
		font-size:14px;
		padding:10px;
	}
	.filelist{
		font-size:14px;
	}
	fieldset{margin:5px;}
	legend{font-size:16px;font-family:arial;border:0px;margin-left:10px;}
</style>
</head>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/lightbase.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/extrender.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/ajax.js"></script>
<script language="javascript" type="text/javascript" src="../../app/js/easyversion/elementmore.js"></script>
<script language="javascript">
<!--
	var sIsConfirm = '<%=CMyString.filterForJs(sIsConfirm)%>';
	var needUpdateFixSize = '<%=CMyString.filterForJs(needUpdateFixes.size() + "")%>';
	var bStop = false;
	var m_sTimeStamp = null;
//-->
</script>
<script language="javascript" type="text/javascript" src="./update_dowith.js"></script>
<body>
<div class="execute_info" id="execute_info">
	<div class="noticeInfo" id="noticeInfo">
		注意：所有FIX更新完成前建议勿关闭此页面。<br> <br>
		FIX更新日志包括： <br>
		1.更新结果日志：WEB-INF/classes/updatefix/update/logs/{日期随机}.log <br>
		2.手工更新说明（记录此次更新后需要手工执行的操作）：WEB-INF/classes/updatefix/update/logs/{日期随机}_manual.log <br>
		3.冲突文件日志（记录此次更新时有冲突的文件名）：WEB-INF/classes/updatefix/update/logs/{日期随机}_conflict.log <br><br>
		此外,程序将需替换的文件备份至文件夹 WEB-INF/classes/updatefix/update/backup 
	</div>
	<fieldset class="" style="display:none;" id="updateinfo">
		<legend>系统更新信息</legend>
		<div class="allmessage" id="allmessage">
			<div key="UpdatePreDowith" class="updateItem active"><div class="itemtitle">更新的前置处理(下载包、解压、备份、冲突比较等)</div><div class="detailinfo" style="display:none;">
				</div></div>
		<%
			boolean bFirst = false;
			for(int m=0;m<allFixes.size();m++){
				String sKey = (String)allFixes.get(m);
				if(!needUpdateFixes.containsKey(sKey)){
					continue;
				}
				String sValue = (String)needUpdateFixes.get(sKey);
		%>
				<div key="<%=CMyString.filterForHTMLValue(sKey)%>" class="updateItem <%=(bFirst? "active" : "notExecute")%>"><div class="itemtitle"><%=CMyString.transDisplay(sKey)%><BR/>
				<%= CMyString.transDisplay(sValue)%></div><div class="detailinfo" style="display:none;">
				</div></div>
		<%
				bFirst = false;		 
			}
		%>
			<div key="buildResource" class="updateItem notExecute"><div class="itemtitle">合并运行时脚本</div><div class="detailinfo" style="display:none;">
				</div></div>
		</div>
	</fieldset>
	<div class="processorbar" id="processorbar" style="display:none;">
	</div>
	<div class="errorInfo" id="errorInfo" style="display:none;">
		<span id="errortip"></span>
	</div>
	<fieldset style="display:none;" id="conflictfileBox">
		<legend>本次更新冲突文件列表</legend>
		<div class="filelist" id="filelist"></div>
	</fieldset>
	<fieldset style="display:none;"  id="ManualworkinfoBox">
		<legend>手动更新列表</legend>
		<div class="Manualworkinfo" id="Manualworkinfo"></div>
	</fieldset>
</div>
</body>
</html>