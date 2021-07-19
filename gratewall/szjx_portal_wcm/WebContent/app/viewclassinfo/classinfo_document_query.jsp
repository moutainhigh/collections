<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.cms.content.ExtendedFields" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentShowFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%@include file="../channel/document_field_init.jsp"%>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Documents oDocuments = (Documents)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

	String sChnlDocSelectFields = oMethodContext.getValue("ChnlDocSelectFields");
	String sDocumentSelectFields = oMethodContext.getValue("DocumentSelectFields");
%>

<%
//4. 构造分页参数,这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;
	String strSelectedIds = "", strExcludeDocIds = "";
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
		strSelectedIds = ","+CMyString.showNull(oMethodContext.getValue("SelectIds"))+",";
		strExcludeDocIds = ","+CMyString.showNull(oMethodContext.getValue("ExcludeDocIds"))+",";
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(oDocuments.size());
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
		<td onclick="wcm.Grid.selectAll('RecId');" width="55" WCMAnt:param="classinfo_document_query.jsp.selectall" class="selAll">全选</td>
		<td WCMAnt:param="abslist.head.edit" width="40" class="td_edit">编辑</td>
		<td grid_sortby="wcmdocument.doctitle"><span WCMAnt:param="documentlist.head.doctitle">文档标题</span><%=getOrderFlag("wcmdocument.doctitle", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.crtime" width="80"><span WCMAnt:param="documentlist.head.crtime">创建时间</span><%=getOrderFlag("wcmchnldoc.crtime", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.cruser" width="70"><span WCMAnt:param="documentlist.head.publishman">发稿人</span><%=getOrderFlag("wcmchnldoc.cruser", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.docchannel" width="70"><span WCMAnt:param="documentlist.head.docchannel">所在栏目</span><%=getOrderFlag("wcmchnldoc.docchannel", sCurrOrderBy)%></td>
		<td grid_sortby="wcmchnldoc.docstatus" width="60"><span WCMAnt:param="documentlist.head.status">状态</span><%=getOrderFlag("wcmchnldoc.docstatus", sCurrOrderBy)%></td>
		<td grid_sortby="wcmdocument.doctype" width="45"><span WCMAnt:param="abslist.head.delete">删除</span><%=getOrderFlag("wcmdocument.doctype", sCurrOrderBy)%></td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	String sLoginUser = loginUser.getName();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Document document = (Document)oDocuments.getAt(i - 1);
			if (document == null)
				continue;
			int nChnlId = document.getChannelId();
			ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(document.getId(), nChnlId);
			ViewDocument viewDocument = ViewDocument.findById(document.getChannel(), currChnlDoc.getId(),sChnlDocSelectFields, sDocumentSelectFields);

			boolean bCanDetail = hasRight(loginUser,viewDocument,34);
			boolean bCanEdit = hasRight(loginUser,document,32);
			boolean bCanDelete = hasRight(loginUser,document,33);

			int nRecId = viewDocument.getChnlDocProperty("RECID", 0);
			int nDocId = viewDocument.getDocId();
			//排除的DOCID处理
			bCanDetail = bCanDetail && strExcludeDocIds.indexOf(","+nDocId+",")==-1;
			boolean bIsSelected = strSelectedIds.indexOf(","+nRecId+",")!=-1;
			//int nChnlId = viewDocument.getChannelId();
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
			//ChnlDoc currChnlDoc = ChnlDoc.findByDocAndChnl(nDocId,nChnlId);
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = currChnlDoc.getInvalidTime();
			if(dtTopInvalidTime != null && bTopped && dtTopInvalidTime.toString() == null){
				bTopForever = true;
			}
			String sDocTypeName = viewDocument.getTypeString();
			int nStatusId = viewDocument.getStatusId();
			String nStatusName = LocaleServer.getString("document_query.label.unknown", "未知");
			if(viewDocument.getStatus()!=null){
				nStatusName = viewDocument.getStatus().getDisp();
			}
			//用transdisplay有点问题,&nbsp;会转成空格,暂时先改成filterForHTMLValue
			String sTitle = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("DOCTITLE"));
			String sCrUser = CMyString.filterForHTMLValue(viewDocument.getPropertyAsString("CrUser"));
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
			int nSiteId = viewDocument.getChannel().getSiteId();
			int nDocKind = viewDocument.getChnlDocProperty("dockind", 0);
%>
	<tr id="tr_<%=nRecId%>" rowid="<%=nRecId%>" class="grid_row <%=sRowClassName%><%=(bIsSelected)?" grid_row_active":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>" right="<%=sRightValue%>" doctype="<%=nDocType%>" isTopped="<%=bTopped%>" channelid="<%=nDocChannelId%>" currchnlid="<%=nChnlId%>" docid="<%=nDocId%>" doctitle="<%=sTitle%>" channelType="<%=docChannel.getType()%>" dockind="<%=nDocKind%>" siteId=<%=nSiteId%>>
		<td><input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RowId" value="<%=nRecId%>" <%=(bCanDetail)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span></td>
		<td><span class="<%=bCanEdit ? "object_edit":"objectcannot_edit no_right"%> grid_function" grid_function="edit">&nbsp;</span></td>
		<td class="doctitle"><a contextmenu="1" unselectable="on" href="#" grid_function="view" title="<%=LocaleServer.getString("ViewDocument.label.DocId","文档ID")%>:[<%=nDocId%>]" WCMAnt:paramattr="title:document_query.jsp.titleTip" id="doctitle_<%=nRecId%>" right_index="32"><span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span><span class="document_modal_<%=nModal%> <%=sDocLinkToCls%><%=sDocMirrorToCls%>"></span><%=sTitle%><span class="<%=(viewDocument.getPropertyAsInt("AttachPic", 0)==1)?"document_attachpic":""%>"></span></a></td>
		<td><%=sCrTime%></td>
		<td><%=sCrUser%></td>
		<td title="<%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%> [ID-<%=docChannel.getId()%>]"><a unselectable="on" href="#" onclick="return false;" grid_function="open_channel" ext_channelid="<%=docChannel.getId()%>" channelType="<%=docChannel.getType()%>" rightValue="<%=getRightValue(loginUser,docChannel)%>"><%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%></a></td>
		<td id="docstatus_<%=nRecId%>"><%=nStatusName%></td>
		<td><span class="<%=bCanDelete ? "object_delete":"objectcannot_delete no_right"%> grid_function" grid_function="delete">&nbsp;</span></td>
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
			CanSort : false
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