//视图菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);	
	reg({
		key : 'metaview_add', 
		desc : wcm.LANG['METAVIEW'] || '视图',
		parent : 'add',
		order : '14',
		cls : function(event, descNode){
			var m = 'addClass';
			if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
				m = 'removeClass';
			}
			Ext.fly(descNode)[m]('disabled');
		},
		cmd : function(event){
			wcm.domain.MetaViewMgr.add(event);
		}
	});	
	reg({
		key : 'metaview_import', 
		desc : wcm.LANG['METAVIEW'] || '视图',
		parent : 'import',
		order : '14',
		cls : function(event, descNode){
			var m = 'addClass';
			if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
				m = 'removeClass';
			}
			Ext.fly(descNode)[m]('disabled');
		},
		cmd : function(event){
			wcm.domain.MetaViewMgr['import'](event);
		}
	});
})();