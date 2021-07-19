<%--
/*
 *	History			Who			What
 *	2011-02-10		lhm		    产生机构代码
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限导入专题的资源和风格！");
	}
	//还没有处理完成
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>设置是否需要导入专题的资源和风格 </title>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
</head>
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
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
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
	input{
		padding-left:10px;
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
		cbSelf.setSize("350px","150px");
		cbSelf.center();
	}
	function onOk(){
		var radioEls = document.getElementsByName("importWidgetAndStyle");
		var value = false;
		if(radioEls[0] && radioEls[0].checked){
			value = true;
		}
		var param = {
			IMPORTWIDGETANDSTYLE : value
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
<DIV class="row" id="content" style="">
	需要导入专题的资源和风格： <input type="radio" name="importWidgetAndStyle" value="true" /> 是<input type="radio" name="importWidgetAndStyle" value="false" checked/>否
</DIV>
<br>
<font color="red">如果选择导入，以前存在的同名的资源和页面风格会被覆盖；如果不重新导入，也可以在系统部署完成后，根据需要手动导入资源和风格</font>
</body>
</html>