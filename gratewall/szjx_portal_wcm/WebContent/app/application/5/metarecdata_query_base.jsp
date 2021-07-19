<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr " %>
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
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@include file="../../include/public_processor.jsp"%>
<%!
	//build global parameter nViewId.
	int nViewId = 5;	
	//build global parameter metaView.
	MetaView metaView;
	//build global parameter metaDataDefMgr.
	IMetaDataDefMgr metaDataDefMgr;
	//build global parameter metaViewFields.
	MetaViewFields metaViewFields;
%>
<%!
	//init
	public void jspInit(){
		try{
			metaView = MetaView.findById(nViewId);
			metaDataDefMgr = (IMetaDataDefMgr) DreamFactory.createObjectById("IMetaDataDefMgr");
			WCMFilter filter = new WCMFilter("", "InOutline=1", "FieldOrder desc");
			metaViewFields = metaDataDefMgr.getViewFields(null, metaView, filter);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%>
<%
	//build global parameter objs.
	MetaViewDatas objs = (MetaViewDatas) processor.excute("wcm61_metarecdata", "queryViewDatas");

	String sOrigSelectedIds = CMyString.showNull(processor.getParam("SelectIds"));
	String strSelectedIds = ","+sOrigSelectedIds+",";
	String sCurrOrderBy = CMyString.showNull(processor.getParam("OrderBy"));

	//build global parameter currPager.
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());
%>
<%!
	/**
	*获取当前记录对象的权限值
	*/
	private String getRightValue(User loginUser, MetaViewData metaViewData) throws WCMException{
		String rightValue = "";
        if (loginUser.isAdministrator()) {
			rightValue = RightValue.getAdministratorValues();
		}

		Channel oChannel = metaViewData.getChannel();
		ViewDocument viewDocument = ViewDocument.findById(oChannel,
				metaViewData.getChnlDocId(), null, "cruser");
		rightValue = viewDocument.getRightValue(loginUser).toString();
		return rightValue;
	}	

	/**
	*获取文档的title信息
	*/
	private String getDocumentsTitle(String sDocIds, String sSeprator){
		int[] aDocIds = CMyString.splitToInt(sDocIds, ",");
		StringBuffer sb = new StringBuffer();
		Document doc = null;
		for(int index = 0, length = aDocIds.length; index< length; index++){
			try{
				doc = Document.findById(aDocIds[index], "docTitle");
				sb.append(CMyString.filterForHTMLValue(doc.getTitle())).append("[").append(aDocIds[index]).append("]");
			}catch(Throwable tx){
				sb.append(CMyString.filterForHTMLValue(LocaleServer.getString("metaviewdata.label.reldocNotExist","指定的相关文档不存在"))).append("[").append(aDocIds[index]).append("]").append(sSeprator);
			}
			if(index != length -1) sb.append(sSeprator);
		}
		return sb.toString();
	}

	/**
	*开始处理标题字段的HTML
	*/
	private String beginHandleTitleField(int nObjId, boolean bIsTop, int nModal, boolean bCanView){
		StringBuffer sb = new StringBuffer();
		if(bIsTop) sb.append("<span class='document_topped'></span>");
		sb.append("<span class='record_modal_").append(nModal).append("'></span>");
		sb.append("<a contextmenu='1' unselectable='on' href='#' onclick='return false'");
		String recid = LocaleServer.getString("metaviewdata.label.recId","记录ID");
		sb.append(" title='").append(recid).append(":[").append(nObjId).append("]'");
		if(bCanView)sb.append(" grid_function='view'");
		sb.append(" class='titleField").append(bCanView ? "" : " no_right").append("'");
		sb.append(">");
		return sb.toString();
	}

	/**
	*结束处理标题字段的HTML
	*/
	private String endHandleTitleField(boolean bIsPic){
		StringBuffer sb = new StringBuffer();
		sb.append("</a>");
		if(bIsPic) sb.append("<span class='document_attachpic'></span>");
		return sb.toString();
	}

	/**
	*判断某个用户在某个对象上是否有相应的权限
	*/
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}

	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
%>