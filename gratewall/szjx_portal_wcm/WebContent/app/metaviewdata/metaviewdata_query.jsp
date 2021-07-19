<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr " %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="java.sql.Types" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%
//为构造检索框搜索字段所添加的变量。
StringBuffer sSearchFieldInfo = new StringBuffer("[");

try{ 
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%@include file="element_processor.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaViewDatas)){
		throw new WCMException( CMyString.format(LocaleServer.getString("metaviewdata_query.type","服务(com.trs.components.metadata.service.MetaDataCenterServiceProvider.query)返回的对象集合类型不为MetaViewDatas，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}
	MetaViewDatas objs = (MetaViewDatas)result;
	MetaView oMetaView = objs.getMetaView();
	String sIds = objs.getIdListAsString();
	int nViewId = oMetaView.getId();
	boolean bIsMutiTable = oMetaView.isMultiTable();

	IMetaDataDefMgr m_oDataDefMgr = (IMetaDataDefMgr) DreamFactory
            .createObjectById("IMetaDataDefMgr");
	WCMFilter filter = new WCMFilter("", "", "fieldorder desc");
	MetaViewFields oMetaViewFields = m_oDataDefMgr.getViewFields(loginUser, oMetaView, filter );
	if(oMetaViewFields == null){
		oMetaViewFields = new MetaViewFields(loginUser);
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectAll" class="selAll">全选</td>
		
		<td WCMAnt:param="metaviewdata_list.head.edit" width="40">编辑</td>
<!-- ----------------------------------------------------------------------------------------- -->
<%
	String sFieldName = "";
	String sAnotherName = "";
	String sFieldValue ="";
	String sDocStatus ="";
	String sTrueTableName = "";
	int nTitleField = 0;
	int columnNum = 4;
	int nFieldType = 0;
	int nDBType = 0;
	int nModal = 0;
	
	for(int i = 0;i < oMetaViewFields.size(); i++ ){
		MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.getAt(i);
		if (oMetaViewField == null)
			continue;

		//modify by huxuanlai@ 2009/7/13 单表数据存储在wcmmetatableXXX表，多表视图存在wcmmetaviewXXX表中，此为其检索字段的区别
		if(bIsMutiTable){
			sFieldName = oMetaViewField.getPropertyAsString("FIELDNAME");				
		}else{
			sFieldName = oMetaViewField.getPropertyAsString("DBFIELDNAME");				
		}
		sAnotherName = oMetaViewField.getAnotherName();
		nDBType = oMetaViewField.getDBType();
		nFieldType = oMetaViewField.getType();
		sTrueTableName = oMetaView.getTrueTableName();

		if(oMetaViewField.isSearchField()){
			//时间类型放在高级检索中。此处排除 ,大字段不检索
			if(nDBType != 93 && nDBType != 2005 && nFieldType != 8 && nFieldType != 14){
				sSearchFieldInfo.append("{");
				sSearchFieldInfo.append("name:'").append(CMyString.filterForJs(sFieldName)).append("',")
				.append("desc:'").append(CMyString.filterForJs(sAnotherName)).append("',");
				switch(nDBType){
					case 4:
						sSearchFieldInfo.append("type:").append("'int'");
						break;
					case 6:
					case 8:
						sSearchFieldInfo.append("type:").append("'float'");
						break;
					case 12:
						sSearchFieldInfo.append("type:").append("'String'");
						break;
				}
				sSearchFieldInfo.append("},");
			}
		}

		if(oMetaViewField.isInOutline()){
			columnNum++;
			//To add  more field and status 
			if(nFieldType == MetaDataConstants.FIELD_TYPE_HTML || nFieldType == MetaDataConstants.FIELD_TYPE_HTML_CHAR){
	%>
	<td><%=sAnotherName%></td>

	<%		}else{
	
	%>
		<td grid_sortby="<%=sTrueTableName%>.<%=sFieldName%>"><%=sAnotherName%><%=getOrderFlag(sTrueTableName + "." + sFieldName, sCurrOrderBy)%>
		</td>

	<%
		        }
		}
	}
%>



<!-- ----------------------------------------------------------------------------------------- -->
<td WCMAnt:param="metaviewdata_list.head.docstatus" width="50" grid_sortby="WCMCHNLDOC.docstatus">状态</td>
<td WCMAnt:param="metaviewdata_list.head.delete" width="40">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int j = currPager.getFirstItemIndex(); j <= currPager.getLastItemIndex(); j++) {
		try{
			MetaViewData obj = (MetaViewData)objs.getAt(j - 1);
			if (obj == null)
				continue;
			String sRightValue = getRightValue(loginUser, obj).toString();
			String sRowClassName = j%2==0?"grid_row_even":"grid_row_odd";
			int nChnlDocId = obj.getChnlDocId();   //即recid
			int nDocId = obj.getDocumentId();      //即metadataid
			int nChnlID = obj.getChannelId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nChnlDocId+",")!=-1;
			Document oDocument = obj.getDocumnent();
			boolean bIsPic = (oDocument.getPropertyAsInt("AttachPic",0) == 1);
			boolean bCanEdit = hasRight(loginUser,oDocument,32);
			boolean bCanDelete = hasRight(loginUser,oDocument,33);
			boolean bCanView = hasRight(loginUser,oDocument,34);
			boolean bIsTop = obj.isTopped();
			boolean bDraft = true;
			if(obj.getStatusId() == Status.STATUS_ID_DRAFT){
				bDraft = false;
			}

%>
	<tr id="tr_<%=nChnlDocId%>" rowid="<%=nChnlDocId%>" recid="<%=nChnlDocId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" isTopped="<%=bIsTop%>" right="<%=sRightValue%>" currchnlid="<%=nChnlID%>" docid="<%=nDocId%>" bDraft=<%=bDraft%>>
		<td><input type="checkbox" id="cb_<%=nChnlDocId%>" class="grid_checkbox" name="RowId" value="<%=nChnlDocId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nChnlDocId%>"><%=j%></span></td>
		
		<td><span class="<%=bCanEdit ? "object_edit":"objectcannot_edit no_right"%> grid_function" grid_function="edit">&nbsp;</span></td>

<!-- ----------------------------------------------------------------------------------------- -->
<%
	for(int k = 0;k < oMetaViewFields.size(); k++ ){
		MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.getAt(k);
			if (oMetaViewField == null)
				continue;
			sDocStatus = obj.getStatus().getDisp();
			if(oMetaViewField.isInOutline()){
				nFieldType = oMetaViewField.getType();
				if(bIsMutiTable){
					sFieldName = oMetaViewField.getPropertyAsString("FIELDNAME");				
				}else{
					sFieldName = oMetaViewField.getPropertyAsString("DBFIELDNAME");				
				}
				sAnotherName = oMetaViewField.getAnotherName();
				nDBType = oMetaViewField.getDBType();
				try{
					sFieldValue = CMyString.transDisplay(obj.getRealProperty(sFieldName));
				}catch(Throwable tx){
					sFieldValue = "<font color='red'>ERROR</font>";
				}

				nTitleField = oMetaViewField.isTitleField() == true ? 1 : 0;
				nModal = obj.getChnlDocProperty("Modal",CMSConstants.CONTENT_MODAL_ENTITY);			
%>
			<td id="name_<%=nChnlDocId%>" class="<%=(nTitleField==1) ? "title" :"" %>">
					<%
						switch(nFieldType){
							case MetaDataConstants.FIELD_TYPE_TRUEORFALSE:
								out.print("<span>");
								if(sFieldValue.equals("")){
									out.print("");
								}else{
									sFieldValue = sFieldValue.equals("1")==true ? LocaleServer.getString("metaviewdata.label.yes", "是") : LocaleServer.getString("metaviewdata.label.no", "否");
									out.print(tranShow(nModal, nTitleField, sFieldValue, bCanView, bIsTop, bIsPic, nDocId));
								}
								out.print("</span>");
								break;
							case MetaDataConstants.FIELD_TYPE_CHECKBOX:
								out.print("<span>");
								out.print(tranShow(nModal, nTitleField, dealWith_onlyNode(oMetaViewField,obj), bCanView, bIsTop, bIsPic, nDocId));
								out.print("</span>");
								break;
							case MetaDataConstants.FIELD_TYPE_HTML_CHAR :
							case MetaDataConstants.FIELD_TYPE_HTML :
								out.print("<span class='bigText'>");
								out.print(tranShow(nModal, nTitleField,LocaleServer.getString("metaviewdata.label.clob", "大文本"), bCanView, bIsTop, bIsPic, nDocId));
								out.print("</span>");
								break;
							case MetaDataConstants.FIELD_TYPE_RELDOC:
								StringBuffer buffer = new StringBuffer();
								String titleTip = "";
								int[] aRelDocIds = CMyString.splitToInt(sFieldValue, ",");
								int loop = 0;
								try{
									for(loop = 0;loop< aRelDocIds.length;loop++){
										buffer.append(Document.findById(aRelDocIds[loop]).getTitle() + "\n");
									}
									titleTip = buffer.toString();
								}catch(Throwable tx){
									 titleTip = LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在");	
									sFieldValue = "<font color=red>ERROR:</font>" + sFieldValue; 
								}

								out.print("<span class='reldoc' title='" + CMyString.transDisplay(titleTip) + "'>");
								if(sFieldValue != null && !sFieldValue.equals("")){
									out.print(tranShow(nModal, nTitleField,	sFieldValue, bCanView, bIsTop, bIsPic, nDocId));
								}else{
									out.print("&nbsp;");
								}
								out.print("</span>");
								break;
							case MetaDataConstants.FIELD_TYPE_APPENDIX:
								out.print(tranShow(nModal, nTitleField,dealWith_appendix_onlyNode(oMetaViewField, obj), bCanView, bIsTop, bIsPic, nDocId));
								break;
							case MetaDataConstants.FIELD_TYPE_CLASS:
								out.print("<span>");
								out.print(tranShow(nModal, nTitleField,dealWith_class_onlyNode(sFieldValue,
									CMyString.transDisplay(obj.getPropertyAsString(sFieldName))), bCanView, bIsTop, bIsPic ,nDocId));
								out.print("</span>");
								break;
							case MetaDataConstants.FIELD_TYPE_SELFDEFINE:
								if(nDBType == Types.CLOB){
									out.print("<span class='bigText'>");
									out.print(LocaleServer.getString("metaviewdata.label.clob", "大文本"));
									out.print(tranShow(nModal, nTitleField,LocaleServer.getString("metaviewdata.label.clob", "大文本"), bCanView, bIsTop, bIsPic, nDocId));
									break;
								}
								if(nDBType == Types.TIMESTAMP){
									//因为CMyString.transDisplay(obj.getRealProperty(sFieldName))把时间中的空格转义了，故此处用21	
									sFieldValue = CMyString.isEmpty(sFieldValue)?"":sFieldValue.substring(0,21);
									out.print("<span class='timestamp' title='" + sFieldValue + "'>");
									out.print(tranShow(nModal, nTitleField, sFieldValue, bCanView, bIsTop, bIsPic, nDocId));
									out.print("</span>");
									break;
								}
							default:
								out.print("<span title='" + CMyString.transDisplay(sFieldValue) + "'>");
								out.print(tranShow(nModal, nTitleField,CMyString.transDisplay(sFieldValue), bCanView, bIsTop, bIsPic, nDocId));
								out.print("</span>");
						}
					%>
			</td>
<%
		}
	}

%>
<!-- ----------------------------------------------------------------------------------------- -->
		<td><span><%=sDocStatus%></span></td>
		<td><span class="<%=bCanDelete ? "object_delete":"objectcannot_delete no_right"%> grid_function" grid_function="delete">&nbsp;</span></td>

	</tr>
<% 

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	if(sSearchFieldInfo.length() > 1){
		sSearchFieldInfo.setCharAt(sSearchFieldInfo.length() -1 , ']');
	}else{
		sSearchFieldInfo.append("]");
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="<%=columnNum%>" class="no_object_found" WCMAnt:param="metaviewdata_query.jsp.list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	getSearchFieldInfo(<%=sSearchFieldInfo.toString()%>);
	var context = PageContext.getContext();
	Ext.apply(context.params, {	
		VIEWID : <%=nViewId%>,
		IDS    : "<%=sIds%>"
	});
		
	Ext.apply(PageContext, {
		CanSort : <%=objs.canSort()%>
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
</script>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("metaviewdata_query.runExce","metaviewdata_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String getRightValue(User loginUser, MetaViewData oMetaViewData) throws WCMException{
		String rightValue = "";
        if (loginUser.isAdministrator()) {
			rightValue = RightValue.getAdministratorValues();
		}

		Channel oChannel = oMetaViewData.getChannel();
		ViewDocument viewDocument = ViewDocument.findById(oChannel,
				oMetaViewData.getChnlDocId(), null, "cruser");
		rightValue = viewDocument.getRightValue(loginUser).toString();
		return rightValue;
	}
	
%>