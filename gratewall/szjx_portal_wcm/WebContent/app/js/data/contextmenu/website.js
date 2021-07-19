//右键单击导航树上站点节点的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.fromTree()) return;
	if(!this.filter(WCMConstants.OBJ_TYPE_WEBSITE)) return;
	var items = this.getArgs().items;
	items.applyCfgs({
		oprKey : 'separate',
		order : 50
	},{
		operItem : ['websiteHost', 'new'],
		order : 51,
		desc : wcm.LANG['TREE_NEWCHNL'] || '新建栏目',
		oprKey : 'newChannel'
	},{
		operItem : ['templateInSite', 'new'],
		order : 52,
		desc : wcm.LANG['TREE_NEWTEMP'] || '新建模板',
		oprKey : 'newTemplate'
	},{
		operItem : ['templateInSite', 'import'],
		order : 53,
		desc : wcm.LANG['TREE_IMPORTTEMP'] || '导入模板',
		oprKey : 'importTemplate'
	});
	items.applyCfgsMapping(function(item){
		var desc = item.desc || '';
		item.desc = desc.replace(wcm.LANG['TREE_REPLACE_THISSITE'] || '这个站点', '');
	});
});