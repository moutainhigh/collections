//版本管理操作面板信息
(function(){
	var sName = wcm.LANG['METARECDATA'] || '记录列表';
	wcm.PageOper.registerPanels({
		metarecdataInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		metarecdataInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		metarecdataInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		metarecdataInClassinfo : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCLASSINFO'] || '分类法{0}操作任务', sName),
			displayNum : 5
		},
		metarecdata : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_metarecdata&methodname=findById'
		},
		metarecdatas : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();