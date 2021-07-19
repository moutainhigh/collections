//右键单击导航树上站点节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
	var items = this.getArgs().items;
	var wcmEvent = this.getArgs().wcmEvent;
	var context = wcmEvent.getContext();
	if(context.get('sitetype') != 4) return;
	//排除链接型栏目（资源库中）
	if(context.get('chnltype') == 11) return;
	items.applyCfgs({
		oprKey : 'addrecord',
		desc : wcm.LANG['METAVIEWDATA_126'] || '新建记录',
		title:'新建一条记录...',
		iconCls : 'addrecord',
		order : 71.1,
		cmd : function(wcmEvent){
			var obj = wcmEvent.getObj();			
			var params = {
				DocumentId : 0,
				FromEditor : 1,
				SiteId:0,
				ChannelId:obj.getId()
			};
			$openMaxWin('/wcm/app/metaviewdata/document_addedit_redirect.jsp?' + $toQueryStr(params));
		},
		visible : function(){
			var wcmEvent = this.getArgs().wcmEvent;
			var context = wcmEvent.getContext();
			return wcm.AuthServer.checkRight(context.right, 31);
			//return wcm.AuthServer.checkRight(context.right, 31) ? '' : 'display-none';
		}
	});
});
