<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.cms.content.Locker" %>
<%@ page import="com.trs.cms.content.LockerMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@include file="../../include/public_server.jsp"%>
<%
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	String wordfile = oMethodContext.getValue("wordfile");
	String pdfOrword = oMethodContext.getValue("PDFWORD");
	if(wordfile == null || wordfile.trim().length() == 0)
		out.println("false");
	//分析是否为锁定状态
	else if(wordfile.equals("USERISNG"))
	{
		out.println(wordfile);
	}
	else
	{
		//得到对应的文档的路径
		if(pdfOrword == null || pdfOrword.trim().length() == 0 ||pdfOrword.equals("pdf"))
			;
		else
			wordfile = wordfile.replaceAll(".pdf",".doc");
		java.io.File file = new java.io.File(wordfile);
		if(!file.exists())
			out.println("false");
		else
		{
			int nObjType = 100;
			int nObjId= 33;
			Locker locker = ((LockerMgr) DreamFactory.createObjectById("LockerMgr")).getLocker(nObjType, nObjId,  true);
			locker.unlock();
			out.println(wordfile);
		}
	}
%>