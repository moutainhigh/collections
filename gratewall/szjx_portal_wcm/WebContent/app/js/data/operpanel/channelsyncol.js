//文档同步操作面板信息
(function(){
	var sName = wcm.LANG['CHANNELSYNCOL'] || '文档同步';
	wcm.PageOper.registerPanels({
		channelsyncolInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		channelsyncolInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		channelsyncolInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL_SHORT'] || '{0}操作任务', sName),
			displayNum : 5
		},
		channelsyncol : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_channelsyncol&methodname=jFindbyid',
			params : function(opt){
				//alert(Ext.toSource(opt.event));
				return "channelId=" + opt.event.getHost().getId() + "&asTarget=true";
			}
		},
		channelsyncols : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();
