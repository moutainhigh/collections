Ext.apply(PageContext, {
	tabEnable : true,
	operEnable : false,
	filterEnable : true,
	gridDraggable : !getParameter("doSearch"),
	serviceId : 'wcm61_viewdocument',
	methodName : 'queryAppendixesForSelect',
	/**/
	objectType : 'Appendix',
	initParams : {
		PageSize : 5,
		AppendixType : 50,
		DocumentId : getParameter("documentId") || 0
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return 'Appendix';
	}
});

Ext.apply(PageContext, {
	getContext : function(){
		var context0 = this.getContext0();
		return context0;
	},
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 4,
			FilterType : PageContext.getParameter('FilterType') || 50
		});
		filters.register([
			{desc: wcm.LANG.document_attachments_select_101 || '全部附件', type:50},
			{desc: wcm.LANG.document_attachments_select_102 || '图片', type:20},
			{desc: wcm.LANG.document_attachments_select_103 || '文件', type:10},
			{desc: wcm.LANG.document_attachments_select_104 || '链接', type:40}
		]);
		return filters;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName :  wcm.LANG.document_attachments_select_105 || '个',
	TypeName :  wcm.LANG.document_attachments_select_106 || '附件'
});

 
 