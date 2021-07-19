//表单文档操作面板信息
(function(){
	var sName = wcm.LANG['INFOVIEWDOC'] || '表单文档';
	wcm.PageOper.registerPanels({
		infoviewdocInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 7,
			detailTitle : wcm.LANG['INFOVIEW_DOC_144'] || '表单文档列表详细信息',
			detail : function(){
				return  wcm.LANG['INFOVIEW_DOC_145'] || '我的表单文档列表';
			}
		},
		infoviewdoc : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 5,
			url : '?serviceid=wcm61_infoviewdoc&methodname=infoViewFindById'
		},
		infoviewdocs : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();