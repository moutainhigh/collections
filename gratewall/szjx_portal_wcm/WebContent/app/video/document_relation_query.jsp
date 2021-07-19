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
	int nPageSize = -1, nPageIndex = 1, nCurrDocId = 0;
	String strSelectedIds = "", strExcludeDocIds = "";
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
		strSelectedIds = ","+CMyString.showNull(oMethodContext.getValue("SelectIds"))+",";
		nCurrDocId = oMethodContext.getValue("CurrDocId", 0);
		strExcludeDocIds = ","+CMyString.showNull(oMethodContext.getValue("ExcludeDocIds"))+",";
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(viewDocuments.size());
	String sCurrOrderBy = CMyString.showNull(oMethodContext.getValue("OrderBy"));
	out.clear();
%>
<%!
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll('RecId');" width="50" WCMAnt:param="document_query.jsp.selectall" class="selAll">全选</td>
		<td grid_sortby="wcmdocument.doctitle" width="100%"><span WCMAnt:param="documentlist.head.doctitle">文档标题</span><%=getOrderFlag("wcmdocument.doctitle", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.crtime" width="80"><span WCMAnt:param="documentlist.head.crtime">创建时间</span><%=getOrderFlag("wcmchnldoc.crtime", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.cruser" width="70"><span WCMAnt:param="documentlist.head.publishman">发稿人</span><%=getOrderFlag("wcmchnldoc.cruser", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.docstatus" width="40"><span WCMAnt:param="documentlist.head.status">状态</span><%=getOrderFlag("wcmchnldoc.docstatus", sCurrOrderBy)%></td>
		<td grid_sortby="wcmdocument.doctype" width="40"><span WCMAnt:param="abslist.head.type">类型</span><%=getOrderFlag("wcmdocument.doctype", sCurrOrderBy)%></td>
	</tr>
	<tbody class="grid_body" id="grid_body">
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
			//排除的DOCID处理
			bCanDetail = bCanDetail && strExcludeDocIds.indexOf(","+nDocId+",")==-1;
			boolean bIsSelected = nCurrDocId==nDocId || strSelectedIds.indexOf(","+nRecId+",")!=-1;
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
			//chnldoc
			ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
			String sDocTypeName = viewDocument.getTypeString();
			int nStatusId = viewDocument.getStatusId();
			String nStatusName = LocaleServer.getString("document_query.label.unknown", "未知");
			if(viewDocument.getStatus()!=null){
				nStatusName = viewDocument.getStatus().getDisp();
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
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";

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
	<tr id="tr_<%=nRecId%>" rowid="<%=nRecId%>" class="<%=(bCanDetail)?"grid_row":""%> <%=sRowClassName%><%=(bIsSelected)?" grid_row_active":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>" right="<%=sRightValue%>" doctype="<%=nDocType%>" isTopped="<%=bTopped%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" docid="<%=nDocId%>" doctitle="<%=sTitle%>">
		<td><input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RowId" value="<%=nRecId%>" <%=(bCanDetail)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span></td>
		<td class="doctitle" title="[<%=nDocId%>]" id="doctitle_<%=nRecId%>"><span class="<%=(bTopped)?"document_topped":""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)==1)?"document_attachpic":""%>"></span></td>
		<td><%=sCrTime%></td>
		<td><%=sCrUser%></td>
		<td id="docstatus_<%=nRecId%>"><%=nStatusName%></td>
		<td><span class="<%=(bCanDetail)?"doctype_"+nDocType:"cannot_doctype_"+nDocType%>" title="<%=sDocTypeName%>" style="width:30px;border-right:0;" right_index="34">&nbsp;
		</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr>
			<td colspan="8" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		</tr>
	</tbody>
</table>
<script>
	try{
		Ext.apply(PageContext, {
			CanSort : <%=viewDocuments.canSort()%>
		});
		wcm.Grid.init({
			OrderBy : '<%=sCurrOrderBy%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>

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