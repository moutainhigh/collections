<%
/** Title:			infoview_document_addedit_dowith.jsp
 *  Description:
 *		WCM5.2 自定义表单的文档添加和修改的实现
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2006.04.17
 *  Vesion:			1.0
 *	Update Logs:
 *
 *  Parameters:
 *		see infoview_document_addedit_dowith.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%@include file="../include/public_server.jsp"%>

<%-- @include file="../../include/debug_dump_request.jsp" --%>

<%
	// 初始化参数 —— 栏目
	// 仅仅测试指定的栏目是否存在、栏目类型是否相符、以及栏目是否设置了正确的表单
	int nChannelId	= currRequestHelper.getInt("ChannelId", 0);
	if (nChannelId <= 0){
		throw new WCMException( LocaleServer.getString("infoview_document_addedit_dowith.object.not.found","栏目Id为0，无法找到栏目！"));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if (currChannel == null){
		throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.not.found","无法找到栏目！"));
	}
	if (currChannel.getType() != Channel.TYPE_INFOVIEW){
		throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.not.fit","该栏目不是自定义表单栏目，无法查看其下的文档！"));
	}
	IInfoViewService oInfoViewService = getInfoViewService();
	List listEmployed = oInfoViewService.getEmployedInfoViews(currChannel);
	if (listEmployed == null || listEmployed.size() <= 0) {
		throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.not.config","该栏目没有配置有效的自定义表单，无法查看其下的文档！"));
	}
	//当表单文档是从普通栏目打开的修改页面的时候，为了修改后进行刷新列表，需要准备一些参数
	int nCurrChannelId = currRequestHelper.getInt("CurrChannelId", 0);
	Channel oCurrChannel = Channel.findById(nCurrChannelId);
	boolean bFromNormalReffer = false;
	if(oCurrChannel != null){
		bFromNormalReffer = oCurrChannel.getType() != Channel.TYPE_INFOVIEW;
	}
	int nChnlDocId = currRequestHelper.getInt("ChnlDocId", 0);
	InfoView infoview = (InfoView) listEmployed.get(0);
%>

<%
	// 初始化参数 —— 文档
	// 仅仅测试指定的文档是否存在
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document currDocument = null;
	String	sNewFlowDocMapData	= null;
	if(nDocumentId > 0){
		currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("infoview_document_addedit_dowith.getFail","获取ID为[{0}]的文档失败！"),new int[]{nDocumentId}));
		}
	} else{
		currDocument = Document.createNewInstance();
        currDocument.setChannel(nChannelId);
        currDocument.setType(Document.DOC_TYPE_NORMAL);

		//ge gfc add @ 2007-11-22 13:11 
		sNewFlowDocMapData	= currRequestHelper.getString("NewFlowDocMapData");
	}
%>

<%
	// 权限校验
	int nFlowDocId  = currRequestHelper.getInt("FlowDocId", 0);
	if(nDocumentId > 0){
		boolean hasRight = false;
		if(nFlowDocId > 0) {
			hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_EDIT);
		}else{
			hasRight = AuthServer.hasRight(loginUser, currDocument, WCMRightTypes.DOC_EDIT);
		}
		if(!hasRight){
			if(nFlowDocId <= 0) {
				throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, CMyString.format(LocaleServer.getString("infoview_document_addedit_dowith.noRight","您没有权限修改ID为[{0}]的文档！"),new int[]{nDocumentId}));
			}
		}
		
		if(!currDocument.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED,CMyString.format(LocaleServer.getString("infoview_document_addedit_dowith.locked","文档[{0}][{1}]被用户［{2}］锁定！您不能修改！"),new String[]{currDocument.getTitle(),String.valueOf(currDocument.getId()),currDocument.getLockerUserName()}));
		}
	}else{
		if(currChannel != null && !AuthServer.hasRight(loginUser, currChannel, WCMRightTypes.DOC_ADD)){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,LocaleServer.getString("infoview_document_addedit_dowith.not.permit", "您没有权限新建文档！"));
		}
	}
%>

<%
	//设置自定义表单ID，同时也标示该文档为自定义表单数据
	currDocument.setFlag(infoview.getId());

	//获取用户提交的表单数据
	String	sObjectXML	= currRequestHelper.getString("ObjectXML");
	//转换成为<code>	org.dom4j.Document</code>
	org.dom4j.Document dom4jDocument = oInfoViewService.getDOM4JDocument(sObjectXML);
	
	//获取文档标题
	//String sDocTitle = oInfoViewService.getDocumentTitle(infoview, dom4jDocument);

	//获取用户提交的数据标题
	String	sDocTitle	= currRequestHelper.getString("DocTitle");
	//保存数基本属性
	//currDocument.setTitle(sDocTitle);
	//wenyh@2006-06-12 设置表单文档的标题和编号
	//add by liuhm@2010-11-17 因为导入的时候也可能有这样的需求，将设置编号的代码进行了合并，放在了保存文档前的监听中
	setTitle(currDocument,oInfoViewService,infoview,dom4jDocument,sDocTitle);

	//正文构造器
	String sDocContent = InfoViewDataHelper.buildContent(infoview, currDocument, dom4jDocument);
	currDocument.setHtmlContent(sDocContent);

	boolean bFlowDocInFlow = false;
	String sFlowDocPostXMLData = null;
	if(nDocumentId > 0) {
		//FlowDocId这是一个冗余参数，为了增强页面的校验
		if(nFlowDocId > 0){
			sFlowDocPostXMLData = currRequestHelper.getString("FlowDocPostXMLData");
			bFlowDocInFlow = (sFlowDocPostXMLData != null);
		}
		//看是否需要略过文档保存
		boolean bSkipDocSaving = currRequestHelper.getBoolean("SkipDocSaving", false);
		if(!bSkipDocSaving) {
			currDocument = getDocumentService().saveInfoViewDocument(currDocument,dom4jDocument);
			//重新指定一次标题，这时候dom4jDocument对象中的数据才是完整的
			setTitle(currDocument,oInfoViewService,infoview,dom4jDocument,sDocTitle);
			//ge gfc modify @ 2008-1-15 如果需要同时处理工作流，则save时须特定的权限校验规则
			//getDocumentService().save(currDocument, null, null);
			if(bFlowDocInFlow) {
				getDocumentService().save(currDocument, null, null, nFlowDocId);
			}else{
				getDocumentService().save(currDocument, null, null);
			}
		}
		
	}else{
		currDocument = getDocumentService().saveInfoViewDocument(currDocument
			, dom4jDocument, sNewFlowDocMapData);
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title> new document </title>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function refresh(){
	var nflowdocid = "<%=nFlowDocId%>"
	if(nflowdocid>0){
		try{
			window.opener.CMSObj.createFrom({
				objType : 'IFlowContent',
				objId : <%=currDocument.getId()%>
			}).afteredit();
		}catch(err){
		}
	}else if('<%=bFromNormalReffer%>' == 'true'){
		try{
			window.opener.CMSObj.createFrom({
				objType : "document",
				objId : <%=currDocument.getId()%>
			}, {chnldocId : <%=nChnlDocId%>}).aftersave();
		}catch(err){
		} 
	}else{
		try{
			window.opener.CMSObj.createFrom({
				objType : "infoviewdoc",
				objId : <%=currDocument.getId()%>
			}).aftersave();
		}catch(err){
		} 
	}
	window.close();
}
</script>
</head>
<body>
<%
	if(bFlowDocInFlow) {
%>
<table id="tblProcessing" border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
	<tr>
		<td valign="middle" align="center">
			<div style="padding: 15px; border: 1px solid silver; width: 400px; background: aliceblue; font-family: arial" >
				<%=LocaleServer.getString("infoview.document.addedit.dowith.saveinfo", "表单已保存，正在处理表单流转....")%>
			</div>
		</td>
	</tr>
</table>
<textarea id="PostXMLData" name="PostXMLData" style="display:none;"><%=sFlowDocPostXMLData%></textarea>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script>
	var m_sFlowDocPostXMLData = $('PostXMLData').value;
	if(m_sFlowDocPostXMLData.trim()!='') {
		new ajaxRequest({
			url : '/wcm/center.do',
			postBody: m_sFlowDocPostXMLData,
			contentType : 'multipart/form-data',
			method : 'post',
			onComplete : function(_trans, _json){
				refresh();
			},
			on500 : function(_trans, _json){
				Element.hide('tblProcessing');
				alert(_trans.responseText);
			}
		});
	}else{
		refresh();
	}
</script>
<%
	}
	else{
%>
<script>
	refresh();
</script>
<%
	}
%>
</body>
</html>
 

<%!
	private IChannelService getChannelService() throws WCMException{
		try{
			return ServiceHelper.createChannelService();
		} catch (WCMException ex){
			throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.channel.not.found","对不起，无法找到栏目应用！请检查您的WCM！"));
		}
	}
%>

<%!
	private IDocumentService getDocumentService() throws WCMException{
		try{
			return ServiceHelper.createDocumentService();
		} catch (WCMException ex){
			throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.document.not.found","对不起，无法找到文档应用！请检查您的WCM！"));
		}
	}
%>

<%!
	private IInfoViewService getInfoViewService() throws WCMException{
		try{
			return ServiceHelper.createInfoViewService();
		} catch (WCMException ex){
			throw new WCMException(LocaleServer.getString("infoview_document_addedit_dowith.form.not.found","对不起，无法找到自定义表单应用！请检查您的WCM！"));
		}
	}
%>

<%!
	private void setTitle(Document _currDocument,IInfoViewService _service,InfoView _infoView,org.dom4j.Document _xmlDocument, String _sNewTitle) throws WCMException{
		//set title.
		if (CMyString.isEmpty(_sNewTitle)) {
		    _sNewTitle = _service.getDocumentTitle(_infoView,_xmlDocument);
		    if (CMyString.isEmpty(_sNewTitle)) {
		        _sNewTitle = LocaleServer.getString("infoview.document.addedit.dowith.newtitle", "自定义表单数据");
		    }
		}
		_currDocument.setTitle(_sNewTitle);
	}

%>