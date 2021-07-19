<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="java.io.Writer" %>
<%@ page import="java.io.OutputStreamWriter" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.cms.content.Locker" %>
<%@ page import="com.trs.cms.content.LockerMgr" %>
<%@include file="../../include/public_server.jsp"%>
<%@include file="govinfo_xml_create_include.jsp"%>

<%
	//添加锁定信息
	int nObjType = 100;
	int nObjId= 33;
	Locker locker = ((LockerMgr) DreamFactory.createObjectById("LockerMgr")).getLocker(nObjType, nObjId,  true);
	if(locker.isLocked()){
		System.out.println("有人正在生成纸制目录的信息");
		out.println("USERISNG");
		return;
	}
	else
		locker.lock(loginUser,30);
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	String startYear = oMethodContext.getValue("STARTYEAR");
	System.out.println(startYear+"****************");
	//TODO 在此初始化一下参数,如:
	if(startYear == null)
		setYearSpan("2008,2008");
	else
		setYearSpan(startYear+","+startYear);
	setClassInfoIds("832");
%>

<%
	Writer writer = null;
	String sFileName = null;
	try{
		//get the xml file name.
		FilesMan aFilesMan = FilesMan.getFilesMan();
		sFileName = aFilesMan.getNextFilePathName(FilesMan.FLAG_PROTECTED, ".xml");
		//save the xml file name to protected directory.
		Element rootElement = toXML();
		writer = new OutputStreamWriter(new FileOutputStream(sFileName), "utf-8");
		writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
		rootElement.write(writer);
	}catch(Exception e){	
		e.printStackTrace();
	}finally{
		if(writer != null){
			try{
				writer.close();
			}catch(Exception e){
			}
		}
	}
	//set the FileName to application.
	CMyFile.extractFileName(sFileName);
	//XML生成成功后，调用WORD文档的生成程序 WORD文档的命名规则是 当前的毫秒数.doc
	java.util.Date date = new java.util.Date();
	long millionTime = date.getTime();
	String wordfile = ConfigServer.getServer().getSysConfigValue("WORD_PATH", "C://")+millionTime+".doc";
	String pdffile = ConfigServer.getServer().getSysConfigValue("WORD_PATH", "C://")+millionTime+".pdf";
	//得到生成WORD文档的信息
	com.wcm.pdf.CreateThreadForPDF pdf = new com.wcm.pdf.CreateThreadForPDF(sFileName,wordfile,pdffile,startYear);
	//调用word的生成程序
	pdf.createWord();
	//返回最后的结果
	out.println(pdffile);
%>