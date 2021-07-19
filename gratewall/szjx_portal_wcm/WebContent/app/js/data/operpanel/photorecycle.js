//废稿箱操作面板信息
(function(){
	var sName = wcm.LANG['DOCRECYCLE'] || '废稿箱';
	wcm.PageOper.registerPanels({
		photorecycleInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		photorecycleInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		photorecycleInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		photorecycle : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_photo&methodname=recyleFindById',
			params : function(opt){
				//alert(Ext.toSource(opt.event));
				 return (opt.event.getHost().getIntType()==103)?"siteid=" + opt.event.getHost().getId() : "channelid=" + opt.event.getHost().getId();
			}
		},
		photorecycles : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();