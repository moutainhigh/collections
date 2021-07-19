<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.io.File" %>
<%
//4.初始化(获取数据)
	String strFileName = currRequestHelper.getString("FILENAME");
	if(CMyString.isEmpty(strFileName)){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("document_addedit_label_11","文件名为空."));
	}
	//安全性问题的处理
	if (strFileName.indexOf("/") >= 0 || strFileName.indexOf("\\") >= 0) {
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,"传入的文件名不合法！");
	}

//5.权限校验

//6.业务代码	
	DocumentService currDocumentService = (DocumentService)DreamFactory.createObjectById("IDocumentService");
	String strAddFilePath = currDocumentService.getMyDocumentImportSourceFilePath() + strFileName;

	File file = new File(strAddFilePath);
	if(file.exists()) {
		file.delete();
	}

//7.结束
	out.clear();
%>