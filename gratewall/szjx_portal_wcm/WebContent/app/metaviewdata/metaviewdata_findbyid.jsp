<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
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
<%@ include file="../system/status_locale.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ViewDocument)){
		throw new WCMException(CMyString.format(LocaleServer.getString("metaviewdata_findbyid.type","服务(com.trs.components.metadata.service.MetaDataCenterServiceProvider.findbyid)返回的对象类型不为ViewDocument，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}


	ViewDocument viewDocument = (ViewDocument)result;
	boolean bCanEdit = hasRight(loginUser,viewDocument,32);
	int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
	int nDocId = viewDocument.getDocId();

	Channel docChannel = null;
	docChannel = viewDocument.getDocChannel();
	int nStatusId = viewDocument.getStatusId();
	String sStatusName = LocaleServer.getString("metarecdata.label.unknown", "未知");
	if(viewDocument.getStatus()!=null){
		sStatusName = viewDocument.getStatus().getDisp();
	}
	String sCrUser = CMyString.transDisplay(viewDocument.getPropertyAsString("CrUser"));
	String sCrTime = convertDateTimeValueToString(oMethodContext,viewDocument.getPropertyAsDateTime("CrTime"));
	String sChnlName = CMyString.transDisplay(docChannel.getName());
	String sChnlDesc = CMyString.transDisplay(docChannel.getDesc());
	
%>
	<div class="attribute_row descinfo readonly">
        <span title="<%=LocaleServer.getString("metaviewdata.label.recordId", "记录编号")%>:<%=nDocId%>&#13;<%=LocaleServer.getString("metaviewdata.label.cruser", "创建者")%>:<%=sCrUser%>&#13;<%=LocaleServer.getString("metaviewdata.label.crtime", "创建时间")%>:<%=sCrTime%> " style="white-space:nowrap;overflow:hidden;">
            <span WCMAnt:param="metaviewdata_findbyid.jsp.CrUser">用户</span><span class="value"><%=sCrUser%></span>
			<span WCMAnt:param="metaviewdata_findbyid.jsp.CrTime">创建于</span><span class="value"><%=sCrTime%></span>
        </span>
    </div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="metaviewdata_findbyid.jsp.chnlId">所属栏目:</span>
        <span class="wcm_attr_value" title="<%=sChnlName%>[<%=docChannel.getId()%>]"><%=sChnlDesc%></span>
	</div>
	<div class="attribute_row_sep"></div>

<%
	// 改为使用服务获取 modify by ffx@2011-11-14
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap params = new HashMap();
	params.put("ChannelId", docChannel.getId() +"");
	params.put("ChnlDocIds", nRecId +"");
	Statuses statuses = (Statuses)processor.excute("wcm6_status", "queryDocumentStatuses", params);
	
%>
	<div class="attribute_row docstatus <%=(statuses.isEmpty() ? "readonly" : "editable" )%>">
	<span class="wcm_attr_name" WCMAnt:param="metaviewdata_findbyid.jsp.status">状态:</span>
	<span class="wcm_attr_value select" _selectEl="selDocStatus" _serviceId="wcm61_viewdocument" _fieldName="docstatus" _fieldValue="<%=nStatusId%>" _methodName="changestatus">
		<%=getStatusLocale(sStatusName)%>&nbsp;
	</span>
<%
	if(nStatusId != Status.STATUS_ID_DRAFT){		
%>
	<span style="display:none">
		<select name="selDocStatus" style="font-size:12px;">
<%
	boolean bContainStatus = false;
	for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
		Status status = (Status) statuses.getAt(i);
		if(nStatusId == status.getId()) bContainStatus = true;
%>
				<option value="<%=status.getId()%>" title="<%=getStatusLocale(status.getDesc())%>">
					<%=getStatusLocale(status.getDisp())%>
				</option>
<%
	}
	if(!bContainStatus){
%>
				<option value="<%=nStatusId%>" title="<%=getStatusLocale(sStatusName)%>">
					<%=getStatusLocale(sStatusName)%>
				</option>
<%}%>
		</select>
	</span>
<%}%>
</div>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("etaviewdata_findbyid.runExce","metaviewdata_findbyid.jsp运行期异常!"), tx);
}
%>