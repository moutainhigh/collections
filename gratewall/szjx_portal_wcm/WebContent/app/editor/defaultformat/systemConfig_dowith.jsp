<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.cluster.FileMsgProcessor"%>
<%
	String sPostData = request.getParameter("postData");
	String sStatus = request.getParameter("sysStatus");
	String sIsForWord = request.getParameter("isForWord");
	String sAttribute = sPostData + "status:" + sStatus + ";"+ "isForWord:" + sIsForWord + ";";
	sAttribute = new String(sAttribute.getBytes("iso-8859-1"),"utf-8");
	String sDomain = ConfigServer.getServer().getInitProperty("WCM_PATH");
	String sPath = sDomain + "/app/editor/defaultformat/systemConfig.txt";
	CMyFile.writeFile(sPath, sAttribute);
	FileMsgProcessor.send(sDomain.replace('\\','/') + "/app/editor/defaultformat/systemConfig.txt");
%>
	<!--AJAX-->
	<script src="../../../app/js/runtime/myext-debug.js"></script>
	<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script> 
	<script src="../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script> 
	<!--AJAX end-->
	<script src="document_defaultFormat_config.js"></script>
	<script src="read.js"></script>
<script type="text/javascript">
	var oCurrStyle = getCssStyle("<%=sAttribute%>");
	var strCurrStyle = transObj2Str(oCurrStyle);
	var text = getStyle(oCurrStyle,strCurrStyle);
	var oData = {
		cssText : text
	}
	var sURL = "setEditorStyle.jsp";
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.JspRequest(
		sURL,
		oData,  true,
		function(transport, json){
			
		}
	);
	window.close();
</script>