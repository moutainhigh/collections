<%
	//build global parameter metaView.
	MetaView metaView = MetaView.findById(m_nViewId);
	IMetaDataDefMgr metaDataDefMgr = (IMetaDataDefMgr) DreamFactory.createObjectById("IMetaDataDefMgr");

	//build global parameter metaViewFields.
	WCMFilter _innerFilter = new WCMFilter("", "InOutline=1", "FieldOrder desc");
	MetaViewFields metaViewFields = metaDataDefMgr.getViewFields(null, metaView, _innerFilter);

	//build (outline and not hidden) parameter metaViewFields
	WCMFilter _outlineFilter = new WCMFilter("", "InOutline=1 and HiddenField=0", "FieldOrder desc");
	MetaViewFields oOutlineMetaViewFields = metaDataDefMgr.getViewFields(null, metaView, _outlineFilter);

	//build global parameter objs.
	MetaViewDatas objs = (MetaViewDatas) processor.excute("wcm61_metaviewdata", "queryViewDatas");

	String sOrigSelectedIds = CMyString.showNull(processor.getParam("SelectIds"));
	String strSelectedIds = ","+sOrigSelectedIds+",";
	String sCurrOrderBy = CMyString.showNull(processor.getParam("OrderBy"));

	//build global parameter currPager.
	CPager currPager = new CPager(processor.getParam("PageSize", 20));
	currPager.setCurrentPageIndex(processor.getParam("CurrPage", 1));
	currPager.setItemCount(objs.size());

	//在此处理相关文档类型的反选逻辑
	boolean bShowRelDocsMaintain = false;
	String sServiceId = "wcm61_metaview", sMethodName = "queryViewsRelatingToCurrView";
	HashMap parameters = new HashMap();
	parameters.put("MetaViewId", String.valueOf(m_nViewId));
	MetaViews relationMetaviews = (MetaViews) processor.excute(sServiceId,sMethodName, parameters);
	if(relationMetaviews.size() > 0){
		bShowRelDocsMaintain = true; 
	}
%>