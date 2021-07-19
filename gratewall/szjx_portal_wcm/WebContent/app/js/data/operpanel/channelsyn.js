//文档同步操作面板信息
(function(){
	var sName = wcm.LANG['CHANNELSYN'] || '文档同步';
	wcm.PageOper.registerPanels({
		channelsynInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		channelsynInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		channelsynInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
			displayNum : 5
		},
		channelsyn : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_channelsyn&methodname=jFindbyid',
			params : function(opt){
				return "channelId=" + opt.event.getHost().getId() + "&asTarget=false";
			}
		},
		channelsyns : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();
