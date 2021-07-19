<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_import.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_data_build.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_function.jsp"%>
<%!int m_nViewId = <TRS_VIEW FIELD="ViewInfoId"/>;%>
<%out.clear();%>
<%
try{ 
	String bCkxOrRadio = request.getParameter("BCKXORRADIO");
	String sDocId = request.getParameter("DOCID");
	if(bCkxOrRadio==""||bCkxOrRadio==null)bCkxOrRadio = "checkbox";
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" style="width:50px;" WCMAnt:param="list.selectAll" class="selAll">全选</td>		
		<td WCMAnt:param="metaviewdata_list.head.edit" style="width:40px;">编辑</td>
<!-- ----------------------------------------------------------------------------------------- -->
<TRS_ViewFields InOutline="true" type="list">
	<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="1">
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12">
			<td class="titleFieldCol">
		</TRS_Condition>
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12" not="true">
			<td class="titleFieldCol" grid_sortby="<TRS_ViewField Field='FullFieldName' />">
		</TRS_Condition>
				<TRS_ViewField field="AnotherName" filterForHTML="true" />
			</td>
	</TRS_Condition>		
	<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="1" not="true">
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12">
			<td>
		</TRS_Condition>
		<TRS_Condition Condition="@FieldType" Type="INT" REFERENCE="12" not="true">
			<td grid_sortby="<TRS_ViewField Field='FullFieldName' />">
		</TRS_Condition>
				<TRS_ViewField field="AnotherName" filterForHTML="true" />
			</td>
	</TRS_Condition>		
</TRS_ViewFields>
<!-- ----------------------------------------------------------------------------------------- -->
		<td WCMAnt:param="metaviewdata_list.head.docstatus" style="width:50px;" grid_sortby="WCMCHNLDOC.docstatus">状态</td>
		<td WCMAnt:param="metaviewdata_list.head.delete" style="width:40px;">删除</td>
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
			Document document = obj.getDocumnent();
			if(document == null)
				continue;
			int nModal = obj.getChnlDocProperty("Modal", CMSConstants.CONTENT_MODAL_ENTITY);			
			boolean bIsPic = (document.getPropertyAsInt("AttachPic",0) == 1);
			boolean bCanEdit = hasRight(loginUser,document,32);
			boolean bCanDelete = hasRight(loginUser,document,33);
			boolean bCanView = hasRight(loginUser,document,34);
			boolean bIsTop = obj.isTopped();

			//chnldoc
			String dtTopInvalidTime = obj.getChnlDocProperty("INVALIDTIME");
			boolean bTopForever = false;//是否永久置顶
			if(bIsTop && dtTopInvalidTime == null){
				bTopForever = true;
			}

			
			String sDocStatus = obj.getStatus().getDisp();
%>
	<tr id="tr_<%=nChnlDocId%>" rowid="<%=nChnlDocId%>" recid="<%=nChnlDocId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" isTopped="<%=bIsTop%>" right="<%=sRightValue%>" currchnlid="<%=nChnlID%>" docid="<%=nDocId%>" viewid="<%=m_nViewId%>">
		<td><input type="<%=bCkxOrRadio%>" id="cb_<%=nChnlDocId%>" class="grid_checkbox" name="RowId" value="<%=nChnlDocId%>" _value="<%=j%>" <%=(bIsSelected)?"checked":""%> <%=(nDocId+"").equals(sDocId)?"disabled":""%>/><span class="grid_index" id="grid_index_<%=nChnlDocId%>"><%=j%></span></td>		
		<td><span class="<%=bCanEdit ? "object_edit":"objectcannot_edit no_right"%> grid_function" grid_function="edit">&nbsp;</span></td>
<!-- ----------------------------------------------------------------------------------------- -->
<TRS_ViewFields InOutline="true" type="list">
	<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="1">
		<td class="titleFieldCol">
			<%=beginHandleTitleField(nDocId, bIsTop, bTopForever, nModal, bCanView)%>			
			<TRS_ViewField field="_html" />
			<%=endHandleTitleField(bIsPic)%>				
		</td>
	</TRS_Condition>		
	<TRS_Condition Condition="@TitleField" Type="INT" REFERENCE="1" not="true">
		<td>
			<TRS_ViewField field="_html" />
		</td>
	</TRS_Condition>
</TRS_ViewFields>
<!-- ----------------------------------------------------------------------------------------- -->
		<td><span><%=sDocStatus%></span></td>
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
		<tr><td colspan="<%=metaViewFields.size()+4%>" class="no_object_found" WCMAnt:param="metaviewdata_query.jsp.list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	var context = PageContext.getContext();
	Ext.apply(context.params, {	
		VIEWID : <%=m_nViewId%>
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
	throw new WCMException("metaviewdata_query.jsp运行期异常!", tx);
}
%>