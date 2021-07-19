<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据) 
	int nDocumentId = currRequestHelper.getInt("PhotoId",0);
	Document document = Document.findById(nDocumentId);
	if(document == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_download.jsp.pic_notfound", "没有找到指定ID为[{0}]的图片!"), new int[]{nDocumentId}));
		//"没有找到ID为"+ nDocumentId + "的图片！");
	}
//5.权限校验
//6.业务代码
	try{
		String path = document.getAttributeValue("SRCFILE");
			
//7.结束
		out.clear();
		out.println(path);
	}catch(Exception ex){
		throw new WCMException(LocaleServer.getString("photo_download.jsp.label.export_original_pic_error", "导出原图过程中出现错误！"),ex);
	}
%>