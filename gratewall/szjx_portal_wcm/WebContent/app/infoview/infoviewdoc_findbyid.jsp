<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
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
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	ViewDocument viewDocument = (ViewDocument)result;
	boolean bCanEdit = hasRight(loginUser,viewDocument,32);
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
	String nStatusName = LocaleServer.getString("infoviewdoc.findbyid.staturname","未知");
	if(viewDocument.getStatus()!=null){
		nStatusName = viewDocument.getStatus().getDisp();
	}
	//Source docSource = viewDocument.getSource();
	//改用document对象取
	Document currDocument = Document.findById(nDocId);
	String docSourceName = currDocument.getPropertyAsString("DOCSOURCENAME")==null?"":currDocument.getPropertyAsString("DOCSOURCENAME");
	if(currDocument == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("infoviewdoc_findbyid.jsp.docnotfound", "没有找到ID为{0}的文档"), new int[]{nDocId}));
		//"没有找到ID为"+ nDocId + "的文档");
	}
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
	String sHostServiceAttr = "_serviceId=\"wcm61_infoviewdoc\"";
	String sHostMethodAttr = "_methodName=\"save\"";
%>

<div class="attribute_row doctitle <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="doctitle" _fieldValue="<%=sTitle%>"  channelId="<%=nChnlId%>
		validation="max_len:'200',min_len:'1',type:'string',required:true,desc:'标题'" title="<%=sTitle%>" validation_desc="标题" WCMAnt:paramattr="validation_desc:infoviewdoc_findbyid.jsp.title1" _serviceId="wcm61_infoviewdoc" <%=sHostMethodAttr%>>
		<%=sTitle%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("template.label.documentId", "文档编号")%>：<%=nDocId%>&#13;<%=LocaleServer.getString("template.label.cruser", "创建者：")%><%=sCrUser%>&#13;<%=LocaleServer.getString("template.label.crtime", "创建时间：")%><%=sCrTime%>&#13;RecID:<%=nRecId%>" style="white-space:nowrap;overflow:hidden;">
		<span WCMAnt:param="infoviewdoc_findbyid.jsp.user">用户</span><span class="value"><%=sCrUser%></span><span WCMAnt:param="infoviewdoc_findbyid.jsp.createDate">创建于</span><span class="value"><%=sCrTime%></span>
	</span>
</div>
<div class="attribute_row docchannel readonly">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.toChannel">所属栏目:</span>
	<span class="wcm_attr_value" title="<%=sChnlDesc%>[(<%=docChannel.getId()%>)]">
		<%=sChnlName%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<%
	// 改为使用服务获取 modify by ffx@2011-11-14
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap params = new HashMap();
	params.put("ChannelId", nChnlId +"");
	params.put("ChnlDocIds", nRecId +"");
	Statuses statuses = (Statuses)processor.excute("wcm6_status", "queryDocumentStatuses", params);
%>
<div class="attribute_row docstatus <%=statuses.isEmpty() ? "readonly" : "editable"%>">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.status">状态:</span>
	<span class="wcm_attr_value select" _selectEl="selDocStatus" _fieldName="docstatus" _fieldValue="<%=nStatusId%>" _serviceId="wcm61_viewdocument" _methodName="changestatus">
		<%=nStatusName%>&nbsp;
	</span>
	<span style="display:none">
		<select name="selDocStatus" style="font-size:12px;">
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
<div class="attribute_row docpeople <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.headTitle1">首页标题:</span>
	<span class="wcm_attr_value" _fieldName="DOCPEOPLE" _fieldValue="<%=sDocPeople%>"
	validation="max_len:'200',type:'string',desc:'首页标题'" validation_desc="首页标题" WCMAnt:paramattr="validation_desc:infoviewdoc_findbyid.jsp.headTitle" _serviceId="wcm61_infoviewdoc" <%=sHostMethodAttr%>>
		<%=sDocPeople%>&nbsp;
	</span>
</div>
<div class="attribute_row subdoctitle <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.subTitle1">副标题:</span>
	<span class="wcm_attr_value" _fieldName="SubDocTitle" _fieldValue="<%=sSubDocTitle%>" 
	validation="max_len:'200',type:'string',desc:'副标题'" validation_desc="副标题" WCMAnt:paramattr="validation_desc:infoviewdoc_findbyid.jsp.subTitle" _serviceId="wcm61_infoviewdoc" <%=sHostMethodAttr%>>
		<%=sSubDocTitle%>&nbsp;
	</span>
</div>
<div class="attribute_row dockeywords <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.dockeywords1">关键词:</span>
	<span class="wcm_attr_value" _fieldName="DOCKEYWORDS" _fieldValue="<%=sDocKeyWords%>" 
	validation="max_len:'200',type:'string',desc:'关键词'" validation_desc="关键词" WCMAnt:paramattr="validation_desc:infoviewdoc_findbyid.jsp.dockeywords" _serviceId="wcm61_infoviewdoc" <%=sHostMethodAttr%>>
		<%=sDocKeyWords%>&nbsp;
	</span>
</div>
<div class="attribute_row docsource <%=sEditable%>" style="overflow:visible;">
	<span class="wcm_attr_name" WCMAnt:param="infoviewdoc_findbyid.jsp.docsource">来源:</span>
	<span class="wcm_attr_value" style="overflow:visible" _fieldName="DOCSOURCENAME" _fieldValue="<%=docSourceName%>" _serviceId="wcm6_document" name="txtDocSource" id="txtDocSource" title="<%=docSourceName%>" >
	<input type="text" id="DocSourceName"  name="DocSourceName" style="background-color:#D9D9D9" value="<%=docSourceName%>" autocomplete="off">
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
	throw new WCMException(LocaleServer.getString("infoviewdoc_findbyid.jsp.label.runtimeexception", "infoviewdoc_findbyid.jsp运行期异常!"), tx);
}
%>