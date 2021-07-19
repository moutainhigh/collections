<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	ViewDocuments viewDocuments = (ViewDocuments)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

//3. 构造分页参数，这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1,nCurrDocId = 0;
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
		nCurrDocId = oMethodContext.getValue("CURRDOCID", 0);
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(viewDocuments.size());

	response.setHeader("Num",String.valueOf(currPager.getItemCount()));
	response.setHeader("PageSize",String.valueOf(currPager.getPageSize()));
	response.setHeader("PageCount",String.valueOf(currPager.getPageCount()));
	response.setHeader("CurrPageIndex",String.valueOf(currPager.getCurrentPageIndex()));
	response.setHeader("CanSort",String.valueOf(viewDocuments.canSort()));
	out.clear();
%>

<%
//5. 遍历生成表现
	String sLoginUser = loginUser.getName();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ViewDocument viewDocument = (ViewDocument)viewDocuments.getAt(i - 1);
			if (viewDocument == null)
				continue;
			boolean bCanDetail = hasRight(loginUser,viewDocument,34);
			boolean bCanPreview = hasRight(loginUser,viewDocument,38);
			int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
			int nDocId = viewDocument.getDocId();
			boolean bIsCurrDoc = (nCurrDocId==nDocId);
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
			//TODO
			int nModal = viewDocument.getChnlDocProperty("MODAL", 0);
			String sDocTypeName = viewDocument.getTypeString();
			int nStatusId = viewDocument.getStatusId();
			String nStatusName = "未知";
			if(viewDocument.getStatus()!=null){
				nStatusName = viewDocument.getStatus().getDisp();
			}
			String sTitle = CMyString.transDisplay(viewDocument.getPropertyAsString("DOCTITLE"));
			String sCrUser = viewDocument.getPropertyAsString("CrUser");
			String sCrTime = convertDateTimeValueToString(oMethodContext,viewDocument.getPropertyAsDateTime("CrTime"));


			String sDocLinkToCls = "", sDocMirrorToCls = "";
			if(nModal == ChnlDoc.MODAL_ENTITY && sLoginUser.equals(sCrUser)){
				String sDocLinkTo = viewDocument.getPropertyAsString("DocLinkTo");
				if(!CMyString.isEmpty(sDocLinkTo)){
					sDocLinkToCls = "linkto";
				}

				String sDocMirrorTo = viewDocument.getPropertyAsString("DocMirrorTo");
				if(!CMyString.isEmpty(sDocMirrorTo)){
					sDocMirrorToCls = "mirrorto";
				}
			}
%>
	<DIV class="grid_row<%=(bIsCurrDoc)?" grid_row_curr":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>" grid_rowid="<%=nRecId%>" right="<%=sRightValue%>" doctype="<%=nDocType%>" isTopped="<%=bTopped%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" docid='<%=nDocId%>' sortableRightIndex="32">
		<SPAN class="grid_column" style="width:50px">
			<input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RecId" value="<%=nRecId%>" <%=(bCanDetail)?"grid_function=\"multi\"":"disabled"%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span>
		</SPAN>
		<SPAN class="grid_column <%=(bCanPreview)?"object_preview":"objectcannot_preview"%>" style="width:30px" grid_function="preview" right_index="38">
			
		</SPAN>
		<SPAN class="grid_column_autowrap doctitle" style="font-size:14px" align="left">
			<a href="#" onclick="return false;" grid_function="chnldoc_edit" title="[文档-<%=nDocId%>]" id="doctitle_<%=nRecId%>" right_index="32"><span class="<%=(bTopped)?"document_topped":""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)==1)?"document_attachpic":""%>"></span></a>
		</SPAN>
		<SPAN class="grid_column" style="width:60px">
			<%=sCrTime%>
		</SPAN>
		<SPAN class="grid_column" style="width:55px" title="<%=sCrUser%>">
			<%=sCrUser%>
		</SPAN>
		<!--SPAN class="grid_column" style="width:65px">
			<%=CMyString.showNull(viewDocument.getPropertyAsString("DocEditor"))%>
		</SPAN-->
		<SPAN class="grid_column" style="width:65px" title="[<%=docChannel.getId()%>]<%=docChannel.getDispDesc()%>">
			<a href="#" onclick="return false;" grid_function="open_channel" ext_channelid="<%=docChannel.getId()%>" channelType="<%=docChannel.getType()%>" rightValue="<%=getRightValue(loginUser,docChannel)%>"><%=docChannel.getDispDesc()%></a>
		</SPAN>
		<SPAN class="grid_column" style="width:30px"  id="docstatus_<%=nRecId%>">
			<%=nStatusName%>
		</SPAN>
		<SPAN class="grid_column <%=(bCanDetail)?"doctype_"+nDocType:"cannot_doctype_"+nDocType%>" title="<%=sDocTypeName%>" style="width:22px;border-right:0;cursor:pointer" <%=(bCanDetail)?"grid_function=\"chnldoc_view\"":""%> right_index="30">
		</SPAN>
	</DIV>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<%!
	private String getPageAttributes(CPager _pager) {
		String sRetVal = "";
		sRetVal += "Num:"+String.valueOf(_pager.getItemCount());
		if (_pager.getPageSize() > 0){
			sRetVal += ",PageSize:"+String.valueOf(_pager.getPageSize());
			sRetVal += ",PageCount:"+String.valueOf(_pager.getPageCount());
			sRetVal += ",CurrPageIndex:"+String.valueOf(_pager.getCurrentPageIndex());
		}
		return sRetVal;
	}
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
	private String getRightValue(User loginUser, Channel _channel) throws WCMException{
        String rightValue = "";
        if (loginUser.isAdministrator()
                || loginUser.equals(_channel.getCrUser())) {
            rightValue = RightValue.getAdministratorValues();
        } else {
            rightValue = getRightValueByMember(_channel, loginUser)
                    .toString();
        }
		return rightValue;
	}
%>