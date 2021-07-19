<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		if(_objCurrent instanceof ViewDocument){
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
	if(!(result instanceof ViewDocument)){
		throw new WCMException(CMyString.format(LocaleServer.getString("recycle_findbyid.jsp.service", "服务(com.trs.ajaxservice.PhotoServiceProvider.findbyid)返回的对象类型不为ViewDocument，而为{0}，请确认。"), new Object[]{result.getClass()}));
		//"服务(com.trs.ajaxservice.PhotoServiceProvider.findbyid)返回的对象类型不为ViewDocument，而为"+(result.getClass())+"，请确认。");
	}
	ViewDocument obj = (ViewDocument)result;	
	String nStatusName = LocaleServer.getString("photo_findbyid.label.unknown", "未知");
	if(obj.getStatus()!=null){
		nStatusName = obj.getStatus().getDisp();
	}
	Document currDocument = Document.findById(obj.getDocId());
	boolean bCanEdit = hasRight(loginUser,obj,32);
	String sEditable = "readonly"; //废稿箱列表禁止编辑
	int nStatusId = obj.getStatusId();
	String  sTitle = CMyString.filterForHTMLValue(currDocument.getTitle());
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
<div id="template_photo" select="ViewDocument">	
	<div class="attribute_row <%=sEditable%> main_attr">
		<span class="wcm_attr_value" _fieldName="doctitle" _fieldValue="<%=sTitle%>" validation="required:'true',type:'string',max_len:'200',desc:'标题'" validation_desc="标题" WCMAnt:paramattr="validation_desc:photo_findbyid.jsp.headTitle" _serviceId="wcm6_viewdocument" title="<%=sTitle%>"><%=sTitle%></span>
	</div>
	<div class="attribute_row_sep"></div>
	<div class="attribute_row" style="padding-left:5px;font-size:12px;width:95%;">
		<span title='ID:<%=currDocument.getId()%>&#13;<%=LocaleServer.getString("photo.label.cruser", "创建者")%>:<%=CMyString.filterForHTMLValue(currDocument.getPropertyAsString("cruser"))%>&#13;<%=LocaleServer.getString("photo.label.crtime", "创建时间")%>:<%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%>' style="white-space:nowrap;overflow:hidden;"><span style="font-size:10px;" WCMAnt:param="photo_findbyid.jsp.cruser">用户</span><span style="color:red;font-weight:bold"><%=CMyString.filterForHTMLValue(currDocument.getPropertyAsString("cruser"))%></span><span style="font-size:10px;" WCMAnt:param="photo_findbyid.jsp.crtime">创建于</span><span style="color:red;font-weight:bold;font-size:10px;"><%=currDocument.getCrTime().toString("yy-MM-dd HH:mm")%></span>
		</span>
	</div>
	<div class="attribute_row readonly">
		<span WCMAnt:param="photo_findbyid.jsp.picTitle" class="wcm_attr_name">图片标题：</span>
		<span class="wcm_attr_value" title="<%=sTitle%>"><%=sTitle%></span>
	</div>
	<div class="attribute_row readonly">
		<span WCMAnt:param="photo_findbyid.jsp.toClass" class="wcm_attr_name">所在分类：</span>
		<span class="wcm_attr_value" title="<%=CMyString.filterForHTMLValue(currDocument.getChannelName())%>[<%=currDocument.getChannelId()%>]"><%=CMyString.filterForHTMLValue(currDocument.getChannel().getDesc())%></span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="photo_findbyid.jsp.author_a">作者：</span>
		<span class="wcm_attr_value" _fieldName="DocAuthor"  _fieldValue="<%=CMyString.filterForHTMLValue(trimNull(currDocument.getAuthorName()))%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'作者'" validation_desc="作者" WCMAnt:paramattr="validation_desc:photo_findbyid.jsp.author" _serviceId="wcm6_viewdocument"><%=CMyString.filterForHTMLValue(trimNull(currDocument.getAuthorName()))%></span>
	</div>	
	<div class="attribute_row_sep"></div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="photo_findbyid.jsp.people_a">人物：</span>
		<span class="wcm_attr_value" _fieldName="DocPeople" _fieldValue="<%=CMyString.filterForHTMLValue(trimNull(currDocument.getPeople()))%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'人物'" validation_desc="人物" WCMAnt:paramattr="validation_desc:photo_findbyid.jsp.people"  _serviceId="wcm6_viewdocument"><%=CMyString.filterForHTMLValue(trimNull(currDocument.getPeople()))%></span>
	</div>	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px" WCMAnt:param="photo_findbyid.jsp.location_a">地点：</span>
		<span class="wcm_attr_value" _fieldName="DocPlace" _fieldValue="<%=CMyString.filterForHTMLValue(trimNull(currDocument.getPlace()))%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'地点'" validation_desc="地点" WCMAnt:paramattr="validation_desc:photo_findbyid.jsp.location" _serviceId="wcm6_viewdocument"><%=CMyString.filterForHTMLValue(trimNull(currDocument.getPlace()))%></span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="photo_findbyid.jsp.time">时间：</span>
		<span class="wcm_attr_value" id="DOCRELTIME_SPAN" name="DOCRELTIME_SPAN" title="<%=trimNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm:ss"))%>"><%=trimNull(currDocument.getReleaseTime().toString("yyyy-MM-dd HH:mm:ss"))%></span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="photo_findbyid.jsp.keywords_a">关键词：</span>
		<span class="wcm_attr_value" _fieldName="DOCKEYWORDS" _fieldValue="<%=CMyString.filterForHTMLValue(trimNull(currDocument.getKeywords()))%>" style="color:#09549F" validation="type:'string',max_len:'100',desc:'关键词'" validation_desc="关键词" WCMAnt:paramattr="validation_desc:photo_findbyid.jsp.keywords" _serviceId="wcm6_viewdocument"><%=CMyString.filterForHTMLValue(trimNull(currDocument.getKeywords()))%></span>
	</div>		
	<div class="attribute_row_sep"></div>
	<div class="attribute_row docstatus <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="photo_findbyid.jsp.picStatus">图片状态：</span>
			<span class="wcm_attr_value select" _selectEl="selDocStatus" _fieldName="docstatus" _fieldValue="<%=nStatusId%>" _serviceId="wcm61_viewdocument" _methodName="changestatus">
			<%=CMyString.filterForHTMLValue(nStatusName)%>&nbsp;
		</span>
		<span style="display:none">
			<select name="selDocStatus">
	<%
		for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
			Status status = (Status) statuses.getAt(i);
			if(nStatusId!=10&&status.getId()==10)continue;//去掉已发
	%>
					<option value="<%=status.getId()%>" title="<%=CMyString.filterForHTMLValue(status.getDesc())%>">
						<%=CMyString.filterForHTMLValue(status.getDisp())%>
					</option>
	<%
		}
	%>
			</select>
		</span>	
	</div>
	<%
		if(currDocument.getStatusId() == -10){
	%>
		<div class="attribute_row readonly">
			<span class="wcm_attr_name" WCMAnt:param="photo_findbyid.jsp.pubTime">发布时间：</span>
			<span class="wcm_attr_value" title="<%=currDocument.getPubTime()%>"><%=currDocument.getPubTime().toString("yyyy-MM-dd HH:mm")%></span>
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
			dtFmt : "yyyy-mm-dd HH:mm:ss",
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
	throw new WCMException(LocaleServer.getString("recycle_findbyid.jsp.label.runtimeexception", "photo_findbyid.jsp运行期异常!"), tx);
}
%>