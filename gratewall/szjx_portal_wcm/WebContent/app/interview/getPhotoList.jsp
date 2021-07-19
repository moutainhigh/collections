<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%> 
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig"%> 
<%@ page import="com.trs.infra.util.CMyFile"%> 
<%@ page import="com.trs.components.wcm.content.persistent.Appendix"%> 
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes"%> 
<%@ page import="com.trs.infra.support.file.FilesMan"%> 
<%@ page import="com.trs.components.wcm.content.domain.AppendixMgr"%> 
<%@ page import="com.trs.DreamFactory"%> 
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement"%> 
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory"%> 
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass"%> 
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent"%> 
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishSyncGlobalTuner"%> 
<%@ page import="java.io.File"%> 
<%@include file="../include/public_server.jsp"%>
<%
	//接收页面参数
	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	int showTitle = currRequestHelper.getInt("showTitle",0);
	int perNum = currRequestHelper.getInt("perNum",0);
	int totalNum = currRequestHelper.getInt("totalNum",0);
	int width = currRequestHelper.getInt("width",0);
	int height = currRequestHelper.getInt("height",0);
	int nTotalNum = currRequestHelper.getInt("ChannelId",0);
	//获取访谈对应的channel
	Channel channel = Channel.findById(nChannelId);
	if (channel == null) {
		System.out.println("哇，图文直播对应的栏目目前为空!");
	}
	WCMFilter filter = new WCMFilter("","","","",totalNum);
	filter.setWhere("docchannel = " + nChannelId);
	Documents documents = Documents.openWCMObjs(null,filter);
	Document document = null;
	String numStyle = "";
	switch(perNum){
		case 2:
			numStyle = "width:50%;";
			break;
		case 3:
			numStyle = "width:33.3%;";
			break;
		case 4:
			numStyle = "width:24.5%;";
			break;
		case 5:
			numStyle = "width:20%;";
			break;
	}
%>
<html>
<head>
<style type="text/css">
	.c_pic{
  font-size:12px;
  padding-top:10px;
  zoom:100%;
  hasLayout:-1;
}
.c_pic_list{
  float:left;
  text-align:center
}
.trs-data-oper{
  width:100%;
}
.c_text{
  line-height:20px;
  padding-top:6px;
  text-align:center;
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
  width:80%;
}
.img{
  border:1px solid #83a9d1;
}
</style>
	<link href="../special/css/widgets.css" rel="stylesheet" type="text/css" />	
	<link href="../special/css/common.css" rel="stylesheet" type="text/css" />
	<link href="../special/design.css" rel="stylesheet" type="text/css" />	
	<link href="../special/toolbar.css" rel="stylesheet" type="text/css" />
	<link href="../js/source/wcmlib/simplemenu/resource/SimpleMenu.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/lightbase.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/WCMConstants.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/data/locale/system.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/extrender.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/elementmore.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Observable.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/FixFF.js"></script>
	<!-- Component Start -->
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/Component.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
	<!-- Component End -->
	<script language="javascript" type="text/javascript" src="../js/source/wcmlib/simplemenu/SimpleMenu.js"></script>
	<!--Ajax Start-->
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/ajax.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/basicdatahelper.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/js/easyversion/web2frameadapter.js"></script>
	<script language="javascript" type="text/javascript" src="getPhotoList.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/special/dataopers4chnl.js"></script>
	<script language="javascript" type="text/javascript" src="../../app/special/dataopers4doc.js"></script>
	<script language="javascript">
	<!--
		wcm.special.data.ElementController.init();
	//-->
	</script>
</head>
<body style="overflow:hidden;margin-top:-5px;">
<%
	String sUrl = "";
	String sTitle = "";
	Appendixes oImageAppendixes = null;
	Appendix appendix = null;
	String sAppendixUrl = "";
	for(int i=0,nLength = documents.size(); i < nLength; i++){
		document = (Document) documents.getAt(i);
		ChnlDoc oChnlDoc = ChnlDoc.findByDocument(document);
		if(document == null) continue;
		oImageAppendixes = getAppMgr().getAppendixes(document, 20);
		appendix = (Appendix) oImageAppendixes.getAt(0);		
		IPublishContent content = PublishElementFactory.makeContentFrom(document, null);
		if(appendix != null){
			sAppendixUrl = getUrl(appendix,content);
			distributeFile(appendix, content);
		}
%>
<DIV class="trs-data-oper " RIGHTVALUE="111111111111111111111111111111111111111111111111111111111111111" ObjType="605" ObjId="<%=oChnlDoc.getDocId()%>" OperTYpe="DOC" CHANNELID="<%=nChannelId%>" RECID="<%=oChnlDoc.getId()%>">
<div class="c_pic_list" style="display:block;<%=perNum < 2 ? "": numStyle%>">
<div class="c_pic" style="margin:0 auto;"><div><a href="#" target="_blank"><img src="<%=CMyString.filterForHTMLValue(sAppendixUrl)%>" width="<%=width%>px" height="<%=height%>px"/></a></div>

<% if(showTitle > 0) {%>
<div class="c_text"><a href="" target="_blank" title="<%=CMyString.filterForHTMLValue(document.getTitle())%>">
<%=CMyString.filterForHTMLValue(document.getTitle())%></a></div>
<%}%>
</div></div>
</DIV>
<%
	}
%>
</body>
</html>
<%!
	private AppendixMgr getAppMgr() {
        return (AppendixMgr) DreamFactory.createObjectById("AppendixMgr");
    }

	private String getUrl(Appendix _appendix, IPublishContent apdOwner) throws WCMException {
		String sFile = _appendix.getFile();
		if (apdOwner == null) {
			throw new WCMException("没有找到UpperHost对象!");
		}

		if (!(apdOwner instanceof IPublishContent)) {
			throw new WCMException("不是IPublishContent对象");
		}
		PublishPathCompass compass = new PublishPathCompass();
		String sURL = compass.getAbsoluteHttpPath(apdOwner);

		return sURL + sFile;
	}

	private void distributeFile(Appendix _appendix, IPublishContent _apdOwner) throws WCMException {
		String sFileName = _appendix.getFile();
		PublishPathCompass compass = new PublishPathCompass();
		try {
			String sSrcFilePathName = null;
			sFileName = CMyFile.extractFileName(sFileName);
			sSrcFilePathName = FilesMan.getFilesMan().mapFilePath(
					sFileName, FilesMan.PATH_LOCAL)
					+ sFileName;

				// 分发文件
				if (sSrcFilePathName != null) {
					if (!CMyFile.fileExists(sSrcFilePathName)) {
						// 文件不存在，作为警告信息发送给用户
						throw new WCMException("磁盘上没有找到文档的附件[" + sFileName + "]");
					} else {
						String sLocalPath = compass.getLocalPath(_apdOwner,
								false);
						PublishSyncGlobalTuner
								.insureLocalPathExists(sLocalPath);
						// edit by liuyou@2008-2-19 下午04:58:26
						// 分发文件的逻辑调整，先判断文件是否存在，再对并发写文件发生的异常进行截获判断
						boolean bNeedDistribute = true;
						File oTargetFile = new File(sLocalPath + sFileName);
						if (!oTargetFile.exists()) {
							try {
								CMyFile.copyFile(sSrcFilePathName, sLocalPath
										+ sFileName);
							} catch (WCMException myEx) {
								if (new File(sSrcFilePathName).exists()
										&& oTargetFile.exists()) {
								} else {
									throw myEx;
								}
							}
						} else {
							bNeedDistribute = false;
						}
					}
				}
		} catch (Exception ex) {
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION,"生成文档附件失败");
		}
	}
%>