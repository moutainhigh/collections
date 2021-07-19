<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
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
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
%>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%@include file="../system/status_locale.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	ViewDocument viewDocument = (ViewDocument)result;
	boolean bCanEdit = false;//hasRight(loginUser,viewDocument,32);
	int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
	int nDocId = viewDocument.getDocId();
	int nChnlId = viewDocument.getChannelId();
	int nDocChannelId = viewDocument.getDocChannelId();
	Channel docChannel = null;
	if(oMethodContext.getValue("ChannelId",0)!=0){
		docChannel = viewDocument.getDocChannel();
	}
	else{
		docChannel = viewDocument.getChannel();
	}
	String sRightValue = viewDocument.getRightValue(loginUser).toString();
	boolean bTopped = viewDocument.isTopped();
	int nDocType = viewDocument.getPropertyAsInt("DOCTYPE", 0);
	//chnldoc
	ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
	int nModal = currChnlDoc.getPropertyAsInt("MODAL", 0);
	String sDocTypeName = viewDocument.getTypeString();
	int nStatusId = viewDocument.getStatusId();
	String nStatusName = LocaleServer.getString("docrecycle_findbyid.label.unknow", "未知");
	if(viewDocument.getStatus()!=null){
		nStatusName = viewDocument.getStatus().getDisp();
	}
	Source docSource = viewDocument.getSource();
	//System.out.println(docSource);
	//if(docSource != null){
	//	System.out.println(docSource.getDesc());
	//}
	String sTitle = CMyString.transDisplay(viewDocument.getPropertyAsString("DOCTITLE"));
	String sCrUser = viewDocument.getPropertyAsString("CrUser");
	CMyDateTime dtValue = new CMyDateTime();
	if( nModal == ChnlDoc.MODAL_LINK || nModal == ChnlDoc.MODAL_MIRROR){
		dtValue = currChnlDoc.getPropertyAsDateTime("CrTime");
	}else{
		dtValue = viewDocument.getPropertyAsDateTime("CrTime");
	}
	String sCrTime = convertDateTimeValueToString(oMethodContext,dtValue);
	String sChnlDesc = CMyString.transDisplay(docChannel.getDesc());
	String sChnlName = CMyString.transDisplay(docChannel.getName());
	String sEditable = bCanEdit?"editable":"readonly";
	String sDocPeople = CMyString.transDisplay(viewDocument.getPropertyAsString("DOCPEOPLE"));
	String sSubDocTitle = CMyString.transDisplay(viewDocument.getPropertyAsString("SubDocTitle"));
	String sDocKeyWords = CMyString.transDisplay(viewDocument.getPropertyAsString("DOCKEYWORDS"));
	String sDocPubUrl = viewDocument.getPropertyAsString("DocPubUrl");
%>
<div class="attribute_row doctitle <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="doctitle" _fieldValue="<%=sTitle%>" title="<%=sTitle%>">
		<%=sTitle%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("viewDocument.label.docId", "文档编号")%>:&nbsp;<%=nDocId%>&#13;<%=LocaleServer.getString("viewDocument.label.cruser", "创建者")%>:&nbsp;<%=sCrUser%>&#13;<%=LocaleServer.getString("viewDocument.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>&#13;RecID:&nbsp;<%=nDocId%>" style="white-space:nowrap;overflow:hidden;" >
		<span WCMAnt:param="docrecycle_findbyid.jsp.cruser">用户</span><span class="value"><%=sCrUser%></span><span WCMAnt:param="docrecycle_findbyid.jsp.crtime">创建于</span><span class="value"><%=sCrTime%></span>
	</span>
</div>
<div class="attribute_row docchannel readonly">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.doctype">类型:</span>
	<span class="wcm_attr_value" title="<%=sDocTypeName%>">
		<%=sDocTypeName%>&nbsp;
	</span>
</div>
<div class="attribute_row docchannel readonly">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.chnlname">所属栏目:</span>
	<span class="wcm_attr_value" title="<%=sChnlDesc%> [ID-<%=docChannel.getId()%>]">
		<%=sChnlDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<%
	WCMFilter filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
	Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
	for (int i = statuses.size() - 1; i >= 0; i--) {
		Status status = (Status) statuses.getAt(i);
		if (status == null
				|| !hasRight(loginUser, viewDocument, status
						.getRightIndex())) {
			statuses.removeAt(i, false);
		}
	}
%>
<div class="attribute_row docstatus <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.status">状态:</span>
	<span class="wcm_attr_value select" _selectEl="selDocStatus" _fieldName="docstatus" _fieldValue="<%=nStatusId%>">
		<%=getStatusLocale(nStatusName)%>&nbsp;
	</span>
	<span style="display:none">
		<select name="selDocStatus">
<%
	for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
		Status status = (Status) statuses.getAt(i);
		if(status.getId()==10)continue;//去掉已发
%>
				<option value="<%=status.getId()%>" title="<%=status.getDesc()%>">
					<%=getStatusLocale(status.getDisp())%>
				</option>
<%
	}
%>
		</select>
	</span>
</div>
<div class="attribute_row docpeople <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.docpeople">首页标题:</span>
	<span class="wcm_attr_value" _fieldName="DOCPEOPLE" _fieldValue="<%=sDocPeople%>">
		<%=sDocPeople%>&nbsp;
	</span>
</div>
<div class="attribute_row subdoctitle <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.subdoctitle">副标题:</span>
	<span class="wcm_attr_value" _fieldName="SubDocTitle" _fieldValue="<%=sSubDocTitle%>">
		<%=sSubDocTitle%>&nbsp;
	</span>
</div>
<div class="attribute_row dockeywords <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.dockey">关键词:</span>
	<span class="wcm_attr_value" _fieldName="DOCKEYWORDS" _fieldValue="<%=sDocKeyWords%>">
		<%=sDocKeyWords%>&nbsp;
	</span>
</div>
<div class="attribute_row docsource <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="docrecycle_findbyid.jsp.source">来源:</span>
	<span class="wcm_attr_value suggestion" _suggestionFn="filterDocSources" _fieldName="DocSource" _fieldValue="<%=docSource!=null?docSource.getId():0%>">
		<%=docSource!=null?docSource.getDesc():""%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<%
	if(!CMyString.isEmpty(sDocPubUrl)){
%>
<div class="attribute_row docpuburl readonly">
	<a href="<%=sDocPubUrl%>" target="_blank"><%=sDocPubUrl%></a>
</div>
<div class="attribute_row_sep"></div>
<%
	}
%>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("docrecycle_findbyid.runtime.error", "docrecycle_findbyid.jsp运行期异常!"),tx);
}
%>