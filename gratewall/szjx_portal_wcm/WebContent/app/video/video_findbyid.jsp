<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.video.content.VideoDoc"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof VideoDoc){
			return ((ViewDocument)_objCurrent).hasRight(_currUser,_nRightIndex);
		}
		else if(_objCurrent instanceof Document){
			return DocumentAuthServer.hasRight(_currUser,((Document)_objCurrent).getChannel(),(Document)_objCurrent,_nRightIndex);
		}
		else if(_objCurrent instanceof ChnlDoc){
			return DocumentAuthServer.hasRight(_currUser,(ChnlDoc)_objCurrent,_nRightIndex);
		}
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
	private String trimNull(String _sOldName) throws WCMException{
		return _sOldName == null ? "" : _sOldName;
	}
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	ViewDocument viewDocument = (ViewDocument)result;
	boolean bCanEdit = hasRight(loginUser,viewDocument,32);
	int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
	int nDocId = viewDocument.getDocId();
	int nChnlId = viewDocument.getChannelId();
	int nDocChannelId = viewDocument.getDocChannelId();
	Channel docChannel = null;
	docChannel = viewDocument.getDocChannel();
	String sRightValue = viewDocument.getRightValue(loginUser).toString();
	boolean bTopped = viewDocument.isTopped();
	int nDocType = viewDocument.getPropertyAsInt("DOCTYPE", 0);
	//chnldoc
	ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
	int nModal = currChnlDoc.getPropertyAsInt("MODAL", 0);
	String sDocTypeName = viewDocument.getTypeString();
	int nStatusId = viewDocument.getStatusId();
	String nStatusName = LocaleServer.getString("document_findbyid.label.unknown", "??????");
	String sChnlDesc = CMyString.transDisplay(docChannel.getDesc());
	if(viewDocument.getStatus()!=null){
		nStatusName = viewDocument.getStatus().getDisp();
	}
	if(!(result instanceof VideoDoc)){
		throw new WCMException(CMyString.format(
				LocaleServer.getString("video_findbyid.jsp.confirm","??????(com.trs.ajaxservice.VideoDocumentServiceProvider.findbyid)?????????????????????????????????ViewDocument?????????[{0}]???????????????"),new Object[] {result.getClass()}));
				
	}
	VideoDoc obj = (VideoDoc)result;	
	Document currDocument = Document.findById(obj.getDocId());
	
	String sTitle = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getTitle()));
	String sAuthor = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getAuthorName()));
	String sPeople = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getPeople()));
	String sKeywords = CMyString.filterForHTMLValue(CMyString.showNull(currDocument.getKeywords()));
	
			
	List xvideos = obj.getXvideos();
	if(xvideos == null) {
		return;
	}
	XVideo xvideo = null;
	StringBuffer token = new StringBuffer();
	String fileNameStr = new String();
	for (int i = 0 ; i < xvideos.size(); i++) {
		xvideo = (XVideo) xvideos.get(i);
		String fileName = xvideo.getFileName();
		if(fileName == null) {
			continue;
		}
		token.append(LocaleServer.getString("video_findbyid.jsp.videoStream","<Strong>?????????")).append(i + 1);
		if (xvideo.getBitrate() > 0) {
			token.append("&nbsp;(").append(xvideo.getBitrate()).append("kbps)");
		}
		token.append("</Strong><br>");
		token.append(fileName.substring(fileName.lastIndexOf('/') + 1));
		fileNameStr = fileName.substring(fileName.lastIndexOf('/') + 1);
	}
	String sEditable = bCanEdit?"editable":"readonly";
%>
<%
	// ??????????????????????????????WCM?????????????????????????????????
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap params = new HashMap();
	params.put("ChannelId", nChnlId +"");
	params.put("ChnlDocIds", nRecId +"");
	Statuses statuses = (Statuses)processor.excute("wcm6_status", "queryDocumentStatuses", params);
	
%>
<div id="template_video" select="ViewDocument">	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_value" _fieldName="doctitle" _fieldValue="<%=sTitle%>" validation="required:'true',type:'string',max_len:'200',desc:'??????'" validation_desc="??????" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.headTitle" _serviceId="wcm61_video" title="<%=sTitle%>"><%=sTitle%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row" style="padding-left:5px;font-size:12px;width:95%;">
	<%
	if(xvideo.getCreateType()==50){
	
%>
<span title='ID:<%=currDocument.getId()%>&#13;<%=LocaleServer.getString("video.label.cruser", "?????????")%>:<%=currDocument.getPropertyAsString("cruser")%>&#13;<%=LocaleServer.getString("video.label.crtime", "????????????")%>:<%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%>' style="white-space:nowrap;overflow:hidden;"><span style="font-size:10px;" WCMAnt:param="video_findbyid.jsp.by">???</span><span style="color:red;font-weight:bold"><%= VSConfig.getMASIP()%></span><span style="font-size:10px;" WCMAnt:param="video_findbyid.jsp.passat">?????????</span><span style="color:red;font-weight:bold;font-size:10px;"><%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%></span>
		</span>
	<%
}else{
	
%>
		<span title='ID:<%=currDocument.getId()%>&#13;<%=LocaleServer.getString("video.label.cruser", "?????????")%>:<%=currDocument.getPropertyAsString("cruser")%>&#13;<%=LocaleServer.getString("video.label.crtime", "????????????")%>:<%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%>' style="white-space:nowrap;overflow:hidden;"><span style="font-size:10px;" WCMAnt:param="video_findbyid.jsp.user">??????</span><span style="color:red;font-weight:bold"><%=currDocument.getPropertyAsString("cruser")%></span><span style="font-size:10px;" WCMAnt:param="video_findbyid.jsp.crtime">?????????</span><span style="color:red;font-weight:bold;font-size:10px;"><%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%></span>
		</span>
			<%
}
	
%>
	</div>
	<div class="attribute_row readonly">
	<span title='?????????:<%=fileNameStr%>&#13;' style="white-space:nowrap;overflow:hidden;" WCMAnt:paramattr="title:video_findbyid.jsp.videoName">
		<%=token.toString() %>
	</span>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="video_findbyid.jsp.time">??????</span>
		<span class="wcm_attr_value" WCMAnt:param="video_findbyid.jsp.sec"><%=xvideo.getDuration()%>???</span>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="video_findbyid.jsp.lenth">??????</span>
		<span class="wcm_attr_value"><%=xvideo.getWidth()%> * <%=xvideo.getHeight()%></span>
	</div>
	<div class="attribute_row readonly">
		<span WCMAnt:param="video_findbyid.jsp.toClass" class="wcm_attr_name">??????</span>
		<span class="wcm_attr_value"><%=sChnlDesc%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="video_findbyid.jsp.author">??????</span>
		<span class="wcm_attr_value" _fieldName="DocAuthor"  _fieldValue="<%=sAuthor%>" title="<%=sAuthor%>" style="color:#09549F" validation="type:'string',max_len:'50',desc:'??????'" validation_desc="??????" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.author" _serviceId="wcm61_viewdocument"><%=sAuthor%></span>
	</div>	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="video_findbyid.jsp.people">??????</span>
		<span class="wcm_attr_value" _fieldName="DocPeople" _fieldValue="<%=sPeople%>" style="color:#09549F" title="<%=sPeople%>" validation="type:'string',max_len:'200',desc:'??????'" validation_desc="??????" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.people"  _serviceId="wcm61_viewdocument"><%=sPeople%></span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="video_findbyid.jsp.keywords">?????????</span>
		<span class="wcm_attr_value" _fieldName="DOCKEYWORDS" _fieldValue="<%=sKeywords%>" style="color:#09549F" title="<%=sKeywords%>" validation="type:'string',max_len:'200',desc:'?????????'" validation_desc="?????????" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.keywords" _serviceId="wcm61_viewdocument"><%=sKeywords%></span>
	</div>		
	<div class="attribute_row_sep"></div>
	<div class="attribute_row docstatus <%=(statuses.isEmpty() ? "readonly" : "editable" )%>">
		<span class="wcm_attr_name" WCMAnt:param="video_findbyid.jsp.picStatus">????????????:</span>
			<span class="wcm_attr_value select" _selectEl="selDocStatus" _fieldName="docstatus" _fieldValue="<%=nStatusId%>" _serviceId="wcm61_viewdocument" _methodName="changestatus">
			<%=nStatusName%>&nbsp;
		</span>
		<span style="display:none">
			<select name="selDocStatus">
	<%
		boolean bContainStatus = false;
		for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
			Status status = (Status) statuses.getAt(i);
			if(nStatusId == status.getId()) bContainStatus = true;
	%>
					<option value="<%=status.getId()%>" title="<%=status.getDesc()%>">
						<%=status.getDisp()%>
					</option>
<%
		}
		if(!bContainStatus){
%>
				<option value="<%=nStatusId%>" title="<%=nStatusName%>">
					<%=nStatusName%>
				</option>
<%}%>
			</select>
		</span>	
	</div>
	<%
		if(currDocument.getStatusId() == 10){
	%>
		<div class="attribute_row readonly">
			<span class="wcm_attr_name" WCMAnt:param="video_findbyid.jsp.pubTime">????????????:</span>
			<span class="wcm_attr_value" title="<%=currDocument.getPubTime()%>"><%=currDocument.getPubTime().toString("yy-MM-dd HH:mm")%></span>
		</div>
	<%
		}
	%>
	<div class="attribute_row_sep"></div>
 </div>
<script>
	var calSpIds = ['DOCRELTIME_SPAN'];
	var calBuTds = ['embed'];
	for (var i = 0; i < calSpIds.length; i++){
		wcm.TRSCalendar.get({
			input : calSpIds[i],
			dtFmt : "yy-mm-dd hh:MM:ss",
			withtime : true,
			getValue : calGetValue(calSpIds[i]),
			setValue : calSetValue(calSpIds[i]),
			handler : calBuTds[i]
		});
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("video_findbyid.jsp.error","video_findbyid.jsp???????????????!"), tx);
}
%>