//分类法菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	var fCls = function(event, descNode){		
		var m = 'addClass';
		if(wcm.AuthServer.isAdmin() && RegsiterMgr.isValidPlugin('metadata')){
			m = 'removeClass';
		}
		Ext.fly(descNode)[m]('disabled');		
	}
	reg({
		key : 'classinfo_add', 
		desc : wcm.LANG['CLASSINFO'] || '分类法',
		parent : 'add',
		order : '15',
		cls	: fCls,
		cmd : function(event){
			wcm.domain.classinfoMgr.add(event);
		}
	});
	reg({
		key : 'classinfo_import', 
		desc : wcm.LANG['CLASSINFO'] || '分类法',
		parent : 'import',
		order : '5',
		cls	: fCls,
		cmd : function(event){
			wcm.domain.classinfoMgr['import'](event);
		}
	});	
})();