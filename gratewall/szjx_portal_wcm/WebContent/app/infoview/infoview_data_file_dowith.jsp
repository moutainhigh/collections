<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.*" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@include file="./infoview_public_include.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	File obj = (File)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

	String sFilePath = obj.getAbsolutePath();
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	out.clear();
	try{
		bis = new BufferedInputStream(new FileInputStream(sFilePath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buffer = new byte[1024];
		int length;
		while ((length = bis.read(buffer)) != -1) {
			 bos.write(buffer, 0, length);
		}
		bos.flush();
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if(bis != null){
			try{
				bis.close();
			}catch(Exception e){}
		}
		if(bos != null){
			try{
				bos.close();
			}catch(Exception e){}
		}
	}
	if(true) return;
%>