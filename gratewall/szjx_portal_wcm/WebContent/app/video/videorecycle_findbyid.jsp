<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.components.video.VideoDocUtil"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.video.content.VideoDoc"%>
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
    Channel docChannel = null;
    docChannel = viewDocument.getDocChannel();
    String sChnlDesc = CMyString.transDisplay(docChannel.getDesc());
	if(!(result instanceof VideoDoc)){
		throw new WCMException(CMyString.format(
				LocaleServer.getString("videorecycle_findbyid.jsp.confirm","服务(com.trs.ajaxservice.VideoDocumentServiceProvider.findbyid)返回的对象集合类型不为ViewDocument，而为[{0}]，请确认。"),new Object[] {result.getClass()}));
				
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
	for (int i = 0 ; i < xvideos.size(); i++) {
		xvideo = (XVideo) xvideos.get(i);
		String fileName = xvideo.getFileName();
		if(fileName == null) {
			continue;
		}
		token.append(LocaleServer.getString("videorecycle_findbyid.jsp.videoStream","<Strong>视频流")).append(i + 1);
		if (xvideo.getBitrate() > 0) {
			token.append("&nbsp;(").append(xvideo.getBitrate()).append("kbps)");
		}
		token.append("</Strong><br>");
		token.append(fileName.substring(fileName.lastIndexOf('/') + 1));
	}
	
	boolean bCanEdit = false;
	String sEditable = bCanEdit?"editable":"readonly";
	int nStatusId = obj.getStatusId();
%>
<%
	WCMFilter filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
	Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
	for (int i = statuses.size() - 1; i >= 0; i--) {
		Status status = (Status) statuses.getAt(i);
		if (status == null
				|| !hasRight(loginUser, obj, status
						.getRightIndex())) {
			statuses.removeAt(i, false);
		}
	}
	
%>
<div id="template_video" select="ViewDocument">	
	<div class="attribute_row <%=sEditable%> main_attr">
		<span class="wcm_attr_value" _fieldName="doctitle" _fieldValue="<%=sTitle%>" validation="required:'true',type:'string',max_len:'200',desc:'标题'" validation_desc="标题" WCMAnt:paramattr="validation_desc:videorecycle_findbyid.jsp.headTitle" _serviceId="wcm6_viewdocument" title="<%=sTitle%>"><%=sTitle%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row" style="padding-left:5px;font-size:12px;width:95%;">
		<span title='ID:<%=currDocument.getId()%>&#13;<%=LocaleServer.getString("video.label.cruser", "创建者")%>:<%=currDocument.getPropertyAsString("cruser")%>&#13;<%=LocaleServer.getString("video.label.crtime", "创建时间")%>:<%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%>' style="white-space:nowrap;overflow:hidden;"><span style="font-size:10px;" WCMAnt:param="videorecycle_findbyid.jsp.cruser">用户</span><span style="color:red;font-weight:bold"><%=currDocument.getPropertyAsString("cruser")%></span><span style="font-size:10px;" WCMAnt:param="videorecycle_findbyid.jsp.crtime">创建于</span><span style="color:red;font-weight:bold;font-size:10px;"><%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%></span>
		</span>
	</div>
	<div class="attribute_row readonly">
		<%=token.toString() %>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="videorecycle_findbyid.jsp.time">时长</span>
		<span class="wcm_attr_value" WCMAnt:param="videorecycle_findbyid.jsp.sec"><%=xvideo.getDuration()%>秒</span>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="videorecycle_findbyid.jsp.lenth">宽高</span>
		<span class="wcm_attr_value"><%=xvideo.getWidth()%> * <%=xvideo.getHeight()%></span>
	</div>
	<div class="attribute_row readonly">
		<span WCMAnt:param="video_findbyid.jsp.toClass" class="wcm_attr_name" >栏目</span>
		<span class="wcm_attr_value"><%=sChnlDesc%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="videorecycle_findbyid.jsp.author">作者</span>
		<span class="wcm_attr_value" _fieldName="DocAuthor"  _fieldValue="<%=sAuthor%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'作者'" validation_desc="作者" WCMAnt:paramattr="validation_desc:videorecycle_findbyid.jsp.author" _serviceId="wcm6_viewdocument" title="<%=sAuthor%>"><%=sAuthor%></span>
	</div>	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="videorecycle_findbyid.jsp.people">人物</span>
		<span class="wcm_attr_value" _fieldName="DocPeople" _fieldValue="<%=sPeople%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'人物'" validation_desc="人物" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.people"  _serviceId="wcm6_viewdocument" title="<%=sPeople%>"><%=sPeople%></span>
	</div>	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="videorecycle_findbyid.jsp.keywords">关键词</span>
		<span class="wcm_attr_value" _fieldName="DOCKEYWORDS" _fieldValue="<%=sKeywords%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'关键词'" validation_desc="关键词" WCMAnt:paramattr="validation_desc:video_findbyid.jsp.keywords" _serviceId="wcm6_viewdocument" title="<%=sKeywords%>"><%=sKeywords%></span>
	</div>		
	<div class="attribute_row_sep"></div>
	<div class="attribute_row docstatus  <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="videorecycle_findbyid.jsp.picStatus">视频状态:</span>
			<span class="wcm_attr_value select" _selectEl="selDocStatus" _fieldName="docstatus" _fieldValue="<%=nStatusId%>">
			<%=currDocument.getStatusName()%>
		<span style="display:none">
			<select name="selDocStatus">
	<%
		for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
			Status status = (Status) statuses.getAt(i);
			if(nStatusId!=10&&status.getId()==10)continue;//去掉已发
	%>
					<option value="<%=status.getId()%>" title="<%=status.getDesc()%>">
						<%=status.getDisp()%>
					</option>
	<%
		}
	%>
			</select>
		</span>	
	</div>
	<%
		if(currDocument.getStatusId() == 10){
	%>
		<div class="attribute_row readonly">
			<span class="wcm_attr_name" WCMAnt:param="videorecycle_findbyid.jsp.pubTime">发布时间:</span>
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
	throw new WCMException(LocaleServer.getString("videorecycle_findbyid.jsp.runError","videorecycle_findbyid.jsp运行期异常!"), tx);
}
%>