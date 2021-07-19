<%--
/*
 *	History			Who			What
 *	2011-02-10		lhm		    产生机构代码
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行政府信息公开服务系统的创建！");
	}

	String sFileName = request.getParameter("FileName");
	if(CMyString.isEmpty(sFileName)){
		return;
	}
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>下载用户密码文件 </title>
</head>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<style type="text/css">
	html,body{
		font-size:12px;
		padding:0px;
		margin:0px;
		width:100%;
		height:100%;
		overflow:hidden;
	}
	.row{
		width:100%;
		margin:10px 0px;
		display:block;
		line-height:24px;
		height:24px;
	}
</style>
<script language="javascript">
<!--
	var sFileName = '<%=CMyString.filterForJs(sFileName)%>';
	var m_cb = null;
	window.m_cbCfg = {
		btns : [
			{
				text : '确定',
				cmd : function(){
					return onOk();
				}
			}
		]
	};
	function init(param){
		m_cb = wcm.CrashBoarder.get(window);
		var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
		cbSelf.setSize("350px","200px");
		cbSelf.center();
	}
	function onOk(){
		var params = {
			DownLoadFinished:'true'
		}
		if(m_cb){
			m_cb.notify(params);
			m_cb.hide();
		}
		return false;
	}
	function download(){
		if(sFileName == null) return;
		var sUrl = "./read_file.jsp?FileName=" + sFileName;
		var downloadFrame = document.getElementById('downloadFrame');
		if(downloadFrame == null){
			downloadFrame = document.createElement("iframe");
			downloadFrame.id = 'downloadFrame';
			document.body.appendChild(downloadFrame);
		}
		downloadFrame.src = sUrl;
	}

//删除用户信息文件
Event.observe(window, 'unload', function(){
	if(sFileName == null) return;
	new ajaxRequest({
		url : 'build_system_check.jsp',
		method : 'post',
		parameters : 'fileName=' + sFileName
	});

});
//-->
</script>
<body>
<DIV class="row">
	<a href="#" onclick='download()'>下载用户密码文件</a>
</DIV>
<DIV class="row">
	<div style="color:red;">
		下载完成后，请点击”确认“按钮
	</div>
</DIV>


</body>
</html>