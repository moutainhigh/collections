//栏目回收站操作面板信息
(function(){
	var sName = wcm.LANG['CHNLRECYCLE'] || '栏目回收站';
	wcm.PageOper.registerPanels({
		chnlrecycleInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		chnlrecycleInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE_SHORT'] || '{0}操作任务', sName),
			displayNum : 5
		},
		chnlrecycleInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
			displayNum : 5
		},
		chnlrecycle : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_channel&methodname=recycleFindbyid'
		},
		chnlrecycles : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();