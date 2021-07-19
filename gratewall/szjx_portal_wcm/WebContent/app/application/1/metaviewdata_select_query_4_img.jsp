<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ include file="/app/application/common/metaviewdata_query_include_import.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_data_build.jsp"%>
<%@ include file="/app/application/common/metaviewdata_query_include_function.jsp"%>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="java.util.List,java.util.Map,java.util.Iterator,java.util.ArrayList" %>

<%!int m_nViewId = 1;%>
<%out.clear();%>
<%
try{ 
	String bCkxOrRadio = request.getParameter("BCKXORRADIO");
	if(bCkxOrRadio==""||bCkxOrRadio==null)bCkxOrRadio = "checkbox";
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" style="width:50px;" WCMAnt:param="list.selectAll" class="selAll">全选</td>		
		
<!-- ----------------------------------------------------------------------------------------- -->

	
		
		
			<td class="titleFieldCol" grid_sortby="WCMMetaTablehpMetaData.fileName">
		
				文件名
			</td>
			
			

<!-- ----------------------------------------------------------------------------------------- -->
<!--
		<td WCMAnt:param="metaviewdata_list.head.docstatus" style="width:50px;" grid_sortby="WCMCHNLDOC.docstatus">状态</td>
-->
		
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	List imageFields = getImageFields(m_nViewId);

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

			String sTitleHtmlValue = CMyString.filterForHTMLValue(document.getTitle());
			String sDocStatus = obj.getStatus().getDisp();
%>
	<tr id="tr_<%=nChnlDocId%>" rowid="<%=nChnlDocId%>" recid="<%=nChnlDocId%>" imgs="<%=getImageValues(obj, imageFields)%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" isTopped="<%=bIsTop%>" right="<%=sRightValue%>" currchnlid="<%=nChnlID%>" docid="<%=nDocId%>" channelid="<%=nChnlID%>" doctitle="<%=sTitleHtmlValue%>" viewid="<%=m_nViewId%>">
		<td><input type="<%=bCkxOrRadio%>" id="cb_<%=nChnlDocId%>" class="grid_checkbox" name="RowId" value="<%=nChnlDocId%>" _value="<%=j%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nChnlDocId%>"><%=j%></span></td>		
		
<!-- ----------------------------------------------------------------------------------------- -->

	
		<td class="titleFieldCol">
			<%=beginHandleTitleField(nDocId, bIsTop, bTopForever, nModal, bCanView)%>			
			<!--默认-->
<%=CMyString.transDisplay(obj.getRealProperty("fileName"), true)%>
			<%=endHandleTitleField(bIsPic)%>				
		</td>
			
	

<!-- ----------------------------------------------------------------------------------------- -->
<!--
		<td><span><%=sDocStatus%></span></td>
-->
		
	</tr>
<% 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="<%=metaViewFields.size()+1%>" class="no_object_found" WCMAnt:param="metaviewdata_query.jsp.list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
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
	throw new WCMException(LocaleServer.getString("metaviewdata_select_query.jsp.label.runtimeexception","metaviewdata_query.jsp运行期异常!"), tx);
}
%>


<%!

	private IMetaDataDefCacheMgr getMetaDataDefCacheMgr(){
		 return (IMetaDataDefCacheMgr) DreamFactory.createObjectById("IMetaDataDefCacheMgr");
	}

	/**
	*获取视图下所有的图片视图字段，含有分辨率的图片
	*/
	private List getImageFields(int nViewId)throws WCMException{
		Map appendixFields = getMetaDataDefCacheMgr().getMetaViewFields(nViewId, MetaDataConstants.FIELD_TYPE_APPENDIX);
		List imageFields = new ArrayList(appendixFields.size());

        for (Iterator iterator = appendixFields.values().iterator(); iterator.hasNext();) {
            MetaViewField oViewField = (MetaViewField) iterator.next();
			String sFieldName = oViewField.getName();

			//the format is xxx_200_300
			String[] info = sFieldName.split("_");
			if(info.length < 3){
				continue;
			}
			String sWidth = info[info.length - 2];
			String sHeight = info[info.length - 1];
			int nWidth = 0;
			int nHeight = 0;
			try{
				nWidth = Integer.parseInt(sWidth);
				nHeight = Integer.parseInt(sHeight);
			}catch(Exception e){
				//just skip it.
				continue;
			}

			imageFields.add(new String[]{sFieldName, sWidth + "_" + sHeight});

        }		
		return imageFields;
	}

	/**
	*获取指定视图记录在指定图片字段下，存在的图片的尺寸列表
	*/
	private String getImageValues(MetaViewData obj, List imageFields) throws Exception{
		StringBuffer sbResult = new StringBuffer(imageFields.size() * 4);
		for(int i = 0, length = imageFields.size(); i < length; i++){
			String[] fieldInfo = (String[]) imageFields.get(i);
			if(CMyString.isEmpty(obj.getPropertyAsString(fieldInfo[0]))){
				continue;
			}
			sbResult.append(fieldInfo[1]).append(",");
		}
		if(sbResult.length() > 0){
			sbResult.setLength(sbResult.length() - 1);
		}
		return sbResult.toString();
	}
%>