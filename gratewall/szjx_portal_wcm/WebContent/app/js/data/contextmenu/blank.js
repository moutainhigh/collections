//右键单击导航树上空白处的处理
wcm.cms.menu.CMSMenu.on('beforeshow', function(){
	if(!this.filter()) return;
	var items = this.getArgs().items;
	items.applyCfgs({
		oprKey : 'quicklocatemenu',
		desc : wcm.LANG['TREE_QUICKLOCATE'] || '快速定位',
		iconCls : 'nav_option_locate',
		cmd : function(){
			try{
				var treeWin = $('nav_tree').contentWindow;
				treeWin.TblFunction['quickLocate']();
			}catch(error){
				alert(error.message);
				//just skip it.
			}
		}
	},{
		oprKey : 'individuate',
		desc : wcm.LANG['TREE_INDIVIDUATE'] || '设置定制的站点',
		iconCls : 'nav_option_more',
		cmd : function(){
			try{
				var treeWin = $('nav_tree').contentWindow;
				treeWin.TblFunction['moreAction']();
			}catch(error){
				//just skip it.
			}
		}
	},{
		oprKey : 'refresh',
		desc : wcm.LANG['TREE_REFRESH'] || '刷新',
		iconCls : 'nav_option_refresh',
		cmd : function(){
			try{
				var treeWin = $('nav_tree').contentWindow;
				treeWin.TblFunction['refreshTree']();
			}catch(error){
				//just skip it.
			}
		}
	});
});
