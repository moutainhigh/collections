<%
/** Title:			infoview_document_list_of_channel.jsp
 *  Description:
 *		WCM5.2 自定义表单的文档列表页面（./infoview/document_list_of_channel.jsp）。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2006.04.17
 *  Vesion:			1.0
 *	Update Logs:
 *		wenyh@20060512,自定义表单栏目的文档不能移动/复制
 *		wenyh@20060524	调整列表页的一些显示
 *		wenyh@20070228  修正不能文档不能撤稿的问题
 *
 *  Parameters:
 *		see infoview_document_list_of_channel.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.presentation.wcm.content.DocumentsPageHelper" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.helper.InfoViewNewQueryHelper" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="com.trs.components.infoview.InfoViewHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewDocuments" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewDocument" %>
<%@ page import="com.trs.infra.util.database.DataType" %>
<%@ page import="com.trs.infra.util.database.FieldInfo" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.process.IFlowContent" %>
<%@ page import="com.trs.cms.process.IFlowServer" %>
<%@ page import="com.trs.cms.process.engine.ContentProcessInfo" %>
<%@ page import="com.trs.cms.process.FlowContentFactory" %>
<%@ page import="java.util.HashMap"%>
<%@include file="./infoview_public_include.jsp"%>

<%-- ----- WCM IMPORTS END ---------- --%>

<%--  页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../../include/validate_publish.jsp"%>
<%
	try{
%>
<%!boolean IS_DEBUG = false;%>
<%
try{
	int	nChannelId		= currRequestHelper.getInt("ChannelId", 0);
	//ge gfc add @ 2008-1-8 兼容从V6链过来的情况
	if(nChannelId == 0) {
		nChannelId		= currRequestHelper.getInt("channelid", 0);
		currRequestHelper.setValue("ChannelId", String.valueOf(nChannelId));
	}
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("infoview_document_list_of_channel.getFailed","获取ID为[{0}]的栏目失败！"),new int[]{nChannelId}));
	}
	InfoView infoview = InfoViewHelper.getInfoView(currChannel);
	if(infoview == null){
		//throw new WCMException("该栏目不是自定义表单类型的栏目或者该栏目没有配置有效的自定义表单，无法编辑其下的文档！");
%>
		<script language="javascript">
			Ext.Msg.warn(String.format('该栏目不是自定义表单类型的栏目或者该栏目没有配置有效的自定义表单，无法编辑其下的文档！'));
		</script>
<%
		return;
	}
	int nInfoViewId = infoview.getId();
	//判断是否可发布
	boolean bIsCanPub = validatePublish(currChannel, loginUser);

	//判断按钮是否可用
	DocumentsPageHelper currPageHelper = new DocumentsPageHelper(loginUser, currRequestHelper, currChannel);

	boolean canDoPub = currPageHelper.canDoPub();
	boolean canDelete = currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_DEL);
	boolean canDoRecycle =  currPageHelper.hasRight(WCMRightTypes.CHNL_RECYCLE);
	boolean canDoMove = currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_MOVE);
	boolean canDoCopy = currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_COPY);
	boolean canDoQuote = currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_QUOTE);

	String sPubButtonStyle = currPageHelper.canDoPub()?"bt_table":"bt_table_disable";
	String sAddButtonStyle = currPageHelper.canDoAddDoc()?"bt_table":"bt_table_disable";
	String sDelButtonStyle = currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_DEL)
							?"bt_table":"bt_table_disable";

	String sMoveBtnStyle = canDoMove ? "bt_table" : "bt_table_disable";
	String sCopyBtnStyle = canDoCopy ? "bt_table" : "bt_table_disable";
	String sQuoteBtnStyle =  currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.DOC_QUOTE)?"bt_table":"bt_table_disable";
	String sZidingyiBtnStyle =  currPageHelper.canDoOperteOnNotEmpty(WCMRightTypes.CHNL_EDIT)?"bt_table":"bt_table_disable";
	
	String sRecycleBtnStyle =  currPageHelper.hasRight(WCMRightTypes.CHNL_RECYCLE)
							?"bt_table":"bt_table_disable";
	boolean canDoExportExcel = currPageHelper.hasRight(WCMRightTypes.DOC_OUTLINE);
	String sExportExcelBtnStyle =  canDoExportExcel?"bt_table":"bt_table_disable";

	String sRightValue = currPageHelper.getLogicRightValue();//getRightValue(Channel.OBJ_TYPE, nChannelId, loginUser);
	String sOutlineFields = CMyString.showNull(CMyString.showNull(currChannel.getOutlineFields(), infoview.getOutlineFields()));
	
	String sOutlineDBFields = currChannel.getPropertyAsString("OutlineDBFields");
	if(CMyString.isEmpty(sOutlineDBFields)){
		sOutlineDBFields =  CMyString.showNull(infoview.getOutlineDBFields());
	}
	//自定义视图的字段列表
	String[] saFieldName = splitFieldName(sOutlineFields);
	String[] saFieldDicplay = InfoViewNewQueryHelper.getFieldsDisplay(saFieldName);
	String[] saDBFieldName = splitFieldName(sOutlineDBFields);
	currRequestHelper.setValue("SelectFields", CMyString.join(saDBFieldName, ","));
	String sOrderField = CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType = CMyString.showNull(currRequestHelper.getOrderType());
	if(CMyString.isEmpty(sOrderField)){
		sOrderField = CMyString.showNull(infoview.getOrderField());
		if(!CMyString.isEmpty(sOrderField)){
			sOrderField = getSortField(sOrderField, infoview);
		}
	}
	currRequestHelper.setValue("OrderField", sOrderField);
	String sSearchXml = CMyString.filterForHTMLValue("<SearchXML/>");
	//检索数据,TODO
	currRequestHelper.setValue("PageIndex", String.valueOf(currRequestHelper.getInt("CurrPage", 1)));
	InfoViewDocuments oInfoViewDocuments = InfoViewNewQueryHelper.queryData(currRequestHelper);
	CPager currPager = new CPager(currRequestHelper.getInt("PageSize", 20));
	currPager.setItemCount(oInfoViewDocuments.size());
	currPager.setCurrentPageIndex( currRequestHelper.getInt("CurrPage", 1) );
	String sSearchFields = CMyString.showNull(infoview.getSearchFields());
	String sSearchDBFields = CMyString.showNull(infoview.getSearchDBFields());
	String sDocStatus = CMyString.showNull(currRequestHelper.getString("DocStatus"));
	boolean zFirstColumn = true;
	
//7.结束
	out.clear();
%>
		 
		<textarea style="display:none" id="TemplateXML"><%=infoview.getTemplateFileContent()%></textarea>
		<form id="frmOutlineFields" style="display:none" method="post" name="frmOutlineFields" action="./channel_save_outlinefields.jsp">
			<input type="hidden" name="ChannelId" value="<%=nChannelId%>">
			<input type="hidden" name="SelectedFields" value="">
			<input type="hidden" name="url" value="">
		</form>
		<form name="frmSearch" style="display:none" id="frmSearch" onsubmit="return false;"> 
			<input type="hidden" name="DocStatus" value="<%=CMyString.filterForHTMLValue(sDocStatus)%>">
			<input type="hidden" name="ChannelId" value="<%=nChannelId%>">
			<input type="hidden" name="ChannelType" value="13">
			<input type="hidden" 
		name="SearchXML"value="<%=sSearchXml%>"/>
		<iframe name="ifrmDownload" width=0 height=0 src=""></iframe>
		</form>
		<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
			<tr id="grid_head" class="grid_head">
				<td onclick="wcm.Grid.selectAll();" width="30" WCMAnt:param="list.selectall" class="selAll">全选</td>
<%
				for (int column = 0; column < saFieldName.length; column++) {
					String sFieldName = saFieldName[column];
					String sSortFieldName = getSortField(sFieldName, infoview);
					if (sSortFieldName!=null) {
						String sWidth = "60px";
						if(zFirstColumn)sWidth = "300px";
						else sWidth = getFieldWidth(sFieldName);
%>						
						<td style="white-space: nowrap;width:<%=sWidth%>"><%= PageViewUtil.getHeadTitle(sSortFieldName, saFieldDicplay[column], sOrderField, sOrderType)%></td>
<%						zFirstColumn = false;
					}else if("POSTIP".equalsIgnoreCase(sFieldName)){
%>
						<td style="white-space: nowrap;width:60px;" WCMAnt:param="infoview_document_list_of_channel.poster">发稿人IP</td>
<%
					} else {
%>
						<td style="white-space: nowrap;width:30px;"><%= saFieldDicplay[column] %></td>
<%
					}
				}
%>
			</tr>
<tbody class="grid_body" id="grid_body">
<%
	zFirstColumn = true;
	String strSelectedIds = "", sCurrOrderBy = "";
	String sOrigSelectedIds = CMyString.showNull(currRequestHelper.getString("SELECTIDS"));
	if (!CMyString.isEmpty(sOrigSelectedIds)) {
		strSelectedIds = ","+sOrigSelectedIds+",";
	}	

int nIndex = 0;
if(!oInfoViewDocuments.isEmpty()){
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++, nIndex++) {
		try {
			InfoViewDocument oInfoViewDocument = (InfoViewDocument)oInfoViewDocuments.getAt(i-1);
			if (oInfoViewDocument == null){
				continue;
			}
			int nDocId = oInfoViewDocument.getDocId();
			int nDocChannelId = oInfoViewDocument.getDocChannelId();
			//注释的原因是与wcm文档列表一致，当用户有查看概览，没有查看细览权限的时候，应该可以查看自己创建的文档列表
			/*
			if(!oInfoViewDocument.hasRight(loginUser, WCMRightTypes.DOC_BROWSE)){
				continue;
			}*/
			zFirstColumn = true;
			int nDocType = oInfoViewDocument.getPropertyAsInt("DOCTYPE", 0);
			int nRecId = oInfoViewDocument.getChnlDocProperty("RECID", 0);
			int nChnlId = oInfoViewDocument.getChannelId();
			Channel docChannel = oInfoViewDocument.getChannel();
			String sTitle = CMyString.transDisplay(oInfoViewDocument.getPropertyAsString("DOCTITLE"));
			boolean bIsSelected = strSelectedIds.indexOf(","+nRecId+",")!=-1;
			//RightValue rightValue = getRightValue(obj, oMethodContext, loginUser);
			//String sRightValue = rightValue.toString();
			boolean bCanEdit = oInfoViewDocument.hasRight(loginUser,WCMRightTypes.DOC_EDIT);
			boolean bCanPreview = oInfoViewDocument.hasRight(loginUser, WCMRightTypes.DOC_PREVIEW);
			boolean bCanBrowse = oInfoViewDocument.hasRight(loginUser, WCMRightTypes.DOC_BROWSE);
			boolean bCanDetail = oInfoViewDocument.hasRight(loginUser, WCMRightTypes.DOC_BROWSE);
			boolean bTopped = oInfoViewDocument.isTopped();
			ChnlDoc currChnlDoc = ChnlDoc.findById(nRecId);
			boolean bTopForever = false;//是否永久置顶
			CMyDateTime dtTopInvalidTime = currChnlDoc.getInvalidTime();
			if(dtTopInvalidTime != null && bTopped && dtTopInvalidTime.toString() == null){
				bTopForever = true;
			}
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";

			//获取是否让文档开始流转的标识
			boolean bCanInFlow = false;
			IFlowContent content = FlowContentFactory.makeFlowContent(605, nDocId);
			if (content != null) {
				IFlowServer m_oFlowServer = (IFlowServer) DreamFactory.createObjectById("IFlowServer");
				ContentProcessInfo oProcessInfo = m_oFlowServer.getProcessInfoOfContent(content);
				int nFlowId =  oProcessInfo.getContentFlowId();
				if(oProcessInfo!=null && nFlowId >0){
					boolean bIsAdministrator = loginUser.isAdministrator();
					bCanInFlow = oProcessInfo.canInFlow() && (bIsAdministrator || loginUser.equals(oProcessInfo.getContent().getCrUser()));
				}
			}
%>
	<tr id="tr_<%=nRecId%>" rowid="<%=nRecId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?"grid_row_active":""%> <%=(bCanDetail)?"grid_selectable_row":"grid_selectdisable_row"%>"  doctype="<%=nDocType%>" right="<%=oInfoViewDocument.getRightValue(loginUser).toString()%>" docid="<%=nDocId%>" doctitle="<%=sTitle%>" isTopped="<%=bTopped%>" channelid="<%=nChnlId%>" currchnlid="<%=nChnlId%>" channelType="13" CanInFlow="<%=bCanInFlow%>">
		<td><input type="checkbox" id="cb_<%=nRecId%>" class="grid_checkbox" name="RowId" value="<%=nRecId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRecId%>"><%=i%></span></td>
<%
			for (int column = 0; column < saDBFieldName.length; column++) {
				String sFieldName = saDBFieldName[column];
				if (sFieldName.startsWith("_") && !sFieldName.equalsIgnoreCase("_DocStatus")) {
				    if ("_EDIT".equalsIgnoreCase(sFieldName)) {
%>
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%>" grid_function="edit">&nbsp;</span></td>
<%
					} else if ("_PREVIEW".equalsIgnoreCase(sFieldName)) {
%>
		<td><span class="<%=(bCanPreview)?"object_preview":"objectcannot_preview"%>" 
		grid_function="preview">&nbsp;</span></td>
<%
					} else {
				        //未知的预定义的类型，输出空占位符
%>
		<td align="center">&nbsp;</td>
<%
					}	
				} else {
				    if (zFirstColumn) {
				        zFirstColumn = false;
%>
		<td id="docTitle_<%=nRecId%>" class="doctitle rowtd"><span class="<%=(bTopped)?(bTopForever?"document_topped_forEver":"document_topped"):""%>"></span><a contextmenu="1" unselectable="on" href="#" onclick="return false;" grid_function="<%=bCanBrowse ? "chnldoc_browse" : ""%>" title="<%=LocaleServer.getString("infoview.document.list.of.channel.docid","文档Id :")%> <%=nDocId%>&#13;<%=LocaleServer.getString("infoview.document.list.of.channel.ownerchannel","所在栏目:")%> <%=CMyString.filterForHTMLValue(docChannel.getDispDesc())%>" id="doctitle_<%=nRecId%>"><%=bCanBrowse? getDocchannelHtml(oInfoViewDocument):""%><%= PageViewUtil.toHtml(getFieldValue(oInfoViewDocument, sFieldName)) %></a></td>
<%
					} else {
%>
		<td style="white-space: nowrap; padding-right: 3px; padding-left: 2px;TEXT-ALIGN:left;"><%= PageViewUtil.toHtml(getFieldValue(oInfoViewDocument, sFieldName)) %></td>

<%					}
				}
}//end  for
%>
	</tr>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, CMyString.format(LocaleServer.getString("infoview_document_list_of_channel.document.not.get","获取第[{0}]篇文档的属性失败！"),new int[]{i}),ex);
		}
	}//end for	
%>
</tbody>
<%
	}else{
%>

	<tbody id="grid_NoObjectFound">
		<tr><td colspan="<%=saFieldName.length+1%>" class="no_object_found" WCMAnt:param="infoview_ducument_list_of_channel.jsp.none">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
<%
	}
%>
</table>
<%!
	private final static String[] DEFAULT_OUTLINE_FILEDS_NAME = {"_EDIT", "DOCTITLE", "_PREVIEW", "CRTIME", "CRUSER", "_DOCSTATUS"};
	private final static String m_sDEFAULT_OUTLINE_FILEDS_NAME = "_EDIT,DOCTITLE,_PREVIEW,CRTIME,CRUSER,_DOCSTATUS";
	private static HashMap m_fieldWidthMap = new HashMap();
%>
<%

	String sCurrOutLineFields = (sOutlineFields==null || sOutlineFields.equals("")) ? m_sDEFAULT_OUTLINE_FILEDS_NAME : sOutlineFields;
	StringBuffer sbJson = new StringBuffer();
	sbJson.append("[null,{");
	sbJson.append("m_nCurrChannelId:'");
	sbJson.append(nChannelId);
	sbJson.append("',m_sCurrOutlineFields:'");
	sbJson.append(sCurrOutLineFields);
	sbJson.append("',m_sSearchFields:'");
	sbJson.append(sSearchFields);
	sbJson.append("',m_nDocumentType:'");
	sbJson.append(Document.OBJ_TYPE);
	sbJson.append("',m_sSearchXML:'");
	sbJson.append(sSearchXml);
	sbJson.append("',m_sDocStatus:'");
	sbJson.append(sDocStatus);
	sbJson.append("',m_nInfoViewId:'");
	sbJson.append(nInfoViewId);
	sbJson.append("'}]");
%>
<script>
 outPutArgs(<%=sbJson.toString()%>);
	try{
		Ext.apply(PageContext, {
			CanSort : <%=oInfoViewDocuments.canSort()%>
		});
		wcm.Grid.init({
			OrderBy : '<%=CMyString.filterForJs(sOrderField)%>',
			RecordNum : <%=currPager.getPageCount()%>
		});

		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		Ext.Msg.$alert(err.message);
		//Just skip it.
	}
</script>
</HTML>
<%
}
finally{
//6.资源释放
}
%>
<%!
	private String getSortField(String sFieldName, InfoView infoview) throws WCMException {
		if(sFieldName.startsWith("_") && !"_DOCSTATUS".equalsIgnoreCase(sFieldName)){
    	    return null;
		}
		if("POSTIP".equalsIgnoreCase(sFieldName)){
			return null;
		}
    	if ("_DOCSTATUS".equalsIgnoreCase(sFieldName)) {
			return "WCMChnlDoc.DocStatus";
		}
		String sTableFieldName = InfoViewHelper.getDBFieldByField(
			infoview.getId(), sFieldName, true);
		int nIndexDot = sTableFieldName.indexOf(".");
		if(nIndexDot!=-1){
			String sTrueTableName = sTableFieldName.substring(0, nIndexDot);
			sFieldName = sTableFieldName.substring(nIndexDot+1);
			FieldInfo oFieldInfo = DBManager.getDBManager().getFieldInfo(
									sTrueTableName, sFieldName);
			if(oFieldInfo == null) return null;
			if(oFieldInfo.getDataType()==null
				|| oFieldInfo.getDataType().getType()==java.sql.Types.CLOB
				|| oFieldInfo.getDataType().getType()==java.sql.Types.LONGVARCHAR
				/*大字段不支持排序*/
				){
				return null;
			}
		}
		return sTableFieldName;
	}
    private String[] splitFieldName(String sOutlineFields) throws WCMException {
        if (sOutlineFields == null || sOutlineFields.length() <= 0) {
            return DEFAULT_OUTLINE_FILEDS_NAME;
        }
        String[] fields = CMyString.split(sOutlineFields, ",");
        if (fields == null || fields.length <= 0) {
            return DEFAULT_OUTLINE_FILEDS_NAME;
        }
        return fields;
	}

%>

<%!
	private String getDocchannelHtml(InfoViewDocument _infoviewDoc){
		int nModal = _infoviewDoc.getChnlDocProperty("Modal", 0);
		String sAlt = (nModal > 0 ? LocaleServer.getString("infoviewdoc.list.channel.queotdoc","引用文档") : "");
		try{
			sAlt =  CMyString.format(LocaleServer.getString("infoviewdoc.list.channel.ownchannl","所属栏目[{0}-{1}]"),new Object[]{_infoviewDoc.getChannel().getName(),String.valueOf(_infoviewDoc.getChannelId())});
		}catch(Throwable ex){
			//just skip it
		}
		return "<span title=\"" + sAlt + " \" class=\"document_modal_" + nModal + "\"></span>";
	}

	private String getFieldValue(InfoViewDocument _infoviewDoc, String _sFieldName)
		throws WCMException {
		if(_sFieldName.equalsIgnoreCase("POSTIP")){
			Document currDocument = _infoviewDoc.getDocument();
			return CMyString.showNull(currDocument.getAttributeValue("IP"));
		}
		if(_sFieldName.equalsIgnoreCase("_DocStatus")){
			if(_infoviewDoc.getStatus() == null){
				return "";
			}
			return _infoviewDoc.getStatus().getDisp();
		}
		if(_sFieldName.equalsIgnoreCase("DocChannel")){
			return _infoviewDoc.getChannel().getName();
		}
		int nIndexDot = _sFieldName.indexOf(".");
		if(nIndexDot!=-1){
			_sFieldName = _sFieldName.substring(nIndexDot+1);
		}
		if(_sFieldName.equals("CRTIME")){
			CMyDateTime fieldValue = _infoviewDoc.getPropertyAsDateTime(_sFieldName);
			return fieldValue.toString("MM-dd HH:mm");
		}

		return _infoviewDoc.getPropertyAsString(_sFieldName, _infoviewDoc.getChnlDocProperty(_sFieldName));
	}
	private String getFieldWidth(String sFieldName){
		if(m_fieldWidthMap.size() == 0){
			initFieldWidthMap();
		}
		String sWidth = (String)m_fieldWidthMap.get(sFieldName);
		if(CMyString.isEmpty(sWidth)){
			sWidth = "60px";
		}
		return sWidth;
	}
	private void initFieldWidthMap(){
		m_fieldWidthMap.put("RANDOMSERIAL","80px");
		m_fieldWidthMap.put("_DOCSTATUS","40px");
		m_fieldWidthMap.put("CRUSER","40px");
	}
%>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("infoview_document_list_of_channel.exception","iflowcontent_query.jsp运行期异常!"), tx);
}
%>