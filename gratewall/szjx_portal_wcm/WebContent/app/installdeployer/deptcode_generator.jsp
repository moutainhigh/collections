<%--
/*
 *	History			Who			What
 *	2011-02-10		lhm		    产生机构代码
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.support.config.Config" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%
	String sDeptCode = ConfigServer.getServer().getSysConfigValue("DEPARTMENT_CODE", "000014348");
	int nConfigId = 0;
	Config oConfig = ConfigServer.getServer().getSysConfig("DEPARTMENT_CODE");
	if(oConfig != null){
		nConfigId = oConfig.getId();
	}
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>单独设置机构代码 </title>
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
	body{
		font-size:12px;
	}
	.row{
		width:100%;
		margin:10px 0px;
		display:block;
		line-height:24px;
		height:24px;
	}
	.label{
		width:230px;
	}
	.title{
		text-align:center;
		font-size:16px;
	}
</style>
<script language="javascript">
<!--
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
		cbSelf.setSize("500px","200px");
		cbSelf.center();
	}
	function onOk(){
		var deptCodeEl = document.getElementById('DeptCode');
		var deptCodeValue = deptCodeEl.value;
		if(deptCodeValue.length < 0){
			alert("您没有填写机构代码！");
			deptCodeEl.focus();
			return false;
		}
		var param = {
			ConfigId : document.getElementById('ConfigId').value,
			DeptCode : deptCodeValue
		}
		if(m_cb){
			m_cb.notify(param);
			m_cb.hide();
		}
		return false;
	}
//-->
</script>
<body>
<INPUT TYPE="hidden" NAME="ConfigId" id="ConfigId" value="<%=nConfigId%>">
<DIV class="row">
	<SPAN class="label">输入您的机构代码（例如：G0003405）</SPAN>
	<SPAN>
		<INPUT TYPE="text" NAME="DeptCode" id="DeptCode" value="<%=sDeptCode%>">
	</SPAN>
</DIV>
</body>
</html>