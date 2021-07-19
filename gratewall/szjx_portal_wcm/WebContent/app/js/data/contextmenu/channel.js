//右键单击导航树上栏目节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.fromTree()) return;
	if(!this.filter(WCMConstants.OBJ_TYPE_CHANNEL)) return;
	var items = this.getArgs().items;
	items.applyCfgs({
		oprKey : 'separate',
		order : 70
	},{
		operItem : ['documentInChannel', 'new'],
		order : 71,
		desc : wcm.LANG['TREE_NEWDOC'] || '新建文档',
		title : "新建文档",
		oprKey : 'newDoc',
		visible : function(){
			var wcmEvent = this.getArgs().wcmEvent;
			var context = wcmEvent.getContext();
			if(context.get('sitetype') != 0) return false;
			return true;
		}
	},{
		operItem : ['channelHost', 'new'],
		order : 72,
		desc : wcm.LANG['TREE_NEWCHILDCHNL'] || '新建子栏目',
		title : "新建子栏目...",
		oprKey : 'newChildChannel'
	},{
		operItem : ['templateInChannel', 'new'],
		order : 73,
		desc : wcm.LANG['TREE_NEWTEMP'] || '新建模板',
		title : "新建模板...",
		oprKey : 'newChnlTemp'
	},{
		operItem : ['templateInChannel', 'import'],
		order : 74,
		desc : wcm.LANG['TREE_IMPORTTEMP'] || '导入模板 ',
		title : "导入模板...",
		oprKey : 'importChnlTemplate'
	});

	items.applyCfgsMapping(function(item){
		var desc = item.desc || '';
		item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISCHNL'] || '这个栏目', '');
	});
});
