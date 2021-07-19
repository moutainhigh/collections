<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.Reports" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.service.impl.DocumentService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	String sDocFileName = currRequestHelper.getString("DocFileName");
	if(sDocFileName==null || sDocFileName.trim().length()==0){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "上传的导入文件名为空，操作中止！");
	}

	String sXslFileName = currRequestHelper.getString("XslFileName");
	String sXslExistFileName = currRequestHelper.getString("XslExistFileName");
	String sXmlFileName = currRequestHelper.getString("XmlFileName");

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel==null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "没有找到ID为"+"["+nChannelId+"]的频道");
	}
	
	//0:由WCM导出的XML文件，1:由TRS数据库导出的XML文件，2:其它类型的XML文件
	int nXmlSource = currRequestHelper.getInt("XmlSource", 0); 
	//0:上传XSL，1:选择已上传XSL
	int nXslSource = currRequestHelper.getInt("XslSource", 0);
	//所有文档全部装载至当前频道 0:否 1:是
	int nToCurrentChannel = currRequestHelper.getInt("ToCurrentChannel", 1);
	//出现重复标题文档时 0:装载 1:跳过
	int nJumpRepeat = currRequestHelper.getInt("JumpRepeat", 1);

//5.权限校验
	
//6.业务代码
	DocumentService currDocumentService = (DocumentService)DreamFactory.createObjectById("IDocumentService");


	FilesMan currFilesMan = FilesMan.getFilesMan();
	String sFilePath = "";

	try{
		sFilePath = currFilesMan.mapFilePath(sDocFileName, FilesMan.PATH_LOCAL);
		//服务器端获取路径，使用PATH_LOCAL
	}catch(Exception e){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "FilesMan 获取上传导入文件路径失败！", e);
	}
	sDocFileName = sFilePath + sDocFileName;	//构造完整的上传文件名（包含路径名）
	System.out.println(sDocFileName);

	sXslFileName = sXslExistFileName;

	if(sXslFileName!=null && sXslFileName.trim().length()>0){
		try{
			sFilePath = currDocumentService.getMyDocumentImportSourceFilePath();
		}catch(Exception e){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "FilesMan 获取上传Xsl文件路径失败！", e);
		}
		sXslFileName = sFilePath + sXslFileName;	//构造完整的上传文件名（包含路径名）
	}

	if(IS_DEBUG) {
		System.out.println("XmlSource:"+nXmlSource);
		System.out.println("ChannelId:"+nChannelId);
		System.out.println("DocFileName:"+sDocFileName);
		System.out.println("XslFileName:"+sXslFileName);
		System.out.println("XmlFileName:"+sXmlFileName);
		System.out.println("JumpRepeat:"+(nJumpRepeat==1));
		System.out.println("ToCurrentChannel:"+(nToCurrentChannel==1));
	}

	Reports currReports = null;
	if(nXmlSource == 1) {
		currReports = currDocumentService.importTRSDocuments(sDocFileName, nChannelId, sXmlFileName, (nJumpRepeat==1), (nToCurrentChannel==1));
	} else {
		currReports = currDocumentService.importDocuments(sDocFileName, nChannelId , sXslFileName, (nJumpRepeat==1), (nToCurrentChannel==1));
	}

//7.结束
	out.clear();
%>
<%
//	currRequestHelper.handleOpReports("../include/operation_reports.jsp", currReports);
%>