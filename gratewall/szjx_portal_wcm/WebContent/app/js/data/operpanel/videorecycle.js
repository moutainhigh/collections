//视频操作面板信息
(function(){
	var sName = wcm.LANG['VIDEORECYCLE'] || '视频';
	wcm.PageOper.registerPanels({
		vInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		videorecycleInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		videorecycleInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		videorecycle : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_video&methodname=recyleFindById',
			params : function(opt){
				var host = opt.event.getHost()
				switch(host.getType()){
					case "website" :
						return "siteId=" + host.getId();
					case "channel" :
						return "channelId=" + host.getId();
				}
			}
		},
		videorecycles : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();