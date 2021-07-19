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
	UpdateFixController controller = null;
try{
	try{
		controller = UpdateFixController.newInstance(loginUser,true);
	}catch(Throwable thae){
		out.print("更新FIX环境有问题：" + thae.getMessage());
		return;
	}
	if(controller.isRunning()){
		out.print("已经有一个更新fix程序正在运行。。。");
		return;
	}

	//判断离线状态
	String sUnlineInfo = "";
	boolean bConfigFixURL = controller.isConfigFixUrl();
	if(!bConfigFixURL){
		sUnlineInfo = "由于未在文件[classes/updatefix/config.properties]配置FIX_URL参数，视为离线状态，请确保已经手工下载FIX信息到最新！ <br>请关注产品最新的发布介质，将最新的FIX包和index文件拷贝至[WEB-INF/classes/updatefix/update/download]目录下，再进行更新。";
	}else if(!controller.validFixIndexFile()){
		sUnlineInfo = "由于无法访问FIX服务器，视为离线状态，请确保已经手工下载FIX信息到最新！ <br>请关注产品最新的发布介质，将最新的FIX包和index文件拷贝至[WEB-INF/classes/updatefix/update/download]目录下，再进行更新。";
	}
	

	//校验当前离线状态下，是否已經下载好了index文件
	boolean bNeedManualDownIndex = false;
	if(!CMyString.isEmpty(sUnlineInfo) && !controller.isExistsIndexFile()){
		bNeedManualDownIndex = true;
	}

	//获取当前需要更新的fix信息

	String sAllMessage = "";
	boolean bNeedUpdate = false;
	boolean hasTRSWCMClusterServer = true;
	
	if(!bNeedManualDownIndex){
		sAllMessage = controller.getCurrUpgradeMsg(true);
		Map needUpdateFixes = controller.getNeedUpdateFixes();
		bNeedUpdate = needUpdateFixes.size() > 0;
		
		// 判断是否是集群环境
		TRSWCMClusterServer server = (TRSWCMClusterServer) DreamFactory.createObjectById("TRSWCMClusterServer");
		if(server.getContext()==null){
			hasTRSWCMClusterServer = false;
		}
	}

out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统当前更新信息。。。</title>
<style type="text/css">
	html,body{
		padding:0px;
		margin:0px;
		width:100%;
		height:100%;
		overflow:auto;
		font-size:14px;
	}
	.errorInfo{
		font-size:16px;
		color:red;
		padding:5px;
		padding-bottom:10px;
	}
	.execute_info{
		padding:10px;
	}
	.messagetitle{
		font-size:20px;
		margin-bottom:10px;
	}
	.unupdate{
		color:red;
	}
	.onlinetitle{
		color:red;
		font-size:20px;
		margin-bottom:10px;
	}
</style>
<script language="javascript">
<!--
	function confirmUpdate(){
		location.href = './update_dowith.jsp?isConfirm=1';
	}
//-->
</script>
</head>
<body>
<div class="execute_info">
 
	<div class="messagetitle">
	<%if(!CMyString.isEmpty(sUnlineInfo)){%>
		<div class="onlinetitle"><%=sUnlineInfo%></div>
	<%}%>
	</div>
 
	<div class="messagetitle">
	<%if(bNeedUpdate){
		if(hasTRSWCMClusterServer){
	%>
		发现当前集群节点有新的更新包，请关闭其他集群节点的应用，再<a href="#"><span style="color:red;" onclick="confirmUpdate();return false;">点击</span></a>更新当前节点，每个集群节点需要单独更新
	<%}else{%>
		发现系统有新的更新包，请<a href="#"><span style="color:red;" onclick="confirmUpdate();return false;">点击</span></a>开始更新
	<%	}
	}else{
		String sInfo = "";
		if(bNeedManualDownIndex){
			sInfo = "请先到公司ftp下载最新的Index文件以及FIX包到WCM应用的[WEB-INF/classes/updatefix/update/download]目录！";
		}else{
			sInfo = "未发现新的更新包，当前系统已经是最新环境！";
			if(hasTRSWCMClusterServer){
				sInfo = "当前访问的集群节点已经是最新，请确认其他节点是否需要更新！";
			}
		}
		out.println(sInfo);
	}%>
	</div>
	<div class="allmessage" id="allmessage">
		<%=transDisplay(sAllMessage,true)%>
	</div>
</div>
</body>
</html>
<%!
	
public static String transDisplay(String _sContent, boolean _bChangeBlank) {
        if (_sContent == null)
            return "";

        char[] srcBuff = _sContent.toCharArray();
        int nSrcLen = srcBuff.length;

        StringBuffer retBuff = new StringBuffer(nSrcLen * 2);

        int i;
        char cTemp;
        for (i = 0; i < nSrcLen; i++) {
            cTemp = srcBuff[i];
            switch (cTemp) {
            case '\n':
                // 再处理段首和段尾的时候处理
                if (_bChangeBlank)
                    retBuff.append("<br/>");
                else
                    retBuff.append(cTemp);
                break;
            default:
                retBuff.append(cTemp);
            }// case
        }

        // 如果替换了空格，直接返回，否则还需要
        if (_bChangeBlank)
            return retBuff.toString();

        // 需要特殊处理段首和段尾
        return CMyString.replaceParasStartEndSpaces(retBuff.toString());

    }		
%>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException("获取系统更新信息出现异常！", tx);
}

%>