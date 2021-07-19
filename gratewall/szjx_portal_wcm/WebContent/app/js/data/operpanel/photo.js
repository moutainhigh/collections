//图片操作面板信息
(function(){
	var sName = wcm.LANG['PHOTO'] || '图片';
	wcm.PageOper.registerPanels({
		photoInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		photoInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		photoInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		photo : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_photo&methodname=jFindbyid',
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
		photos : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();