/**
*here is a example of register menus.
*/
(function(){
	return; //skip the following code.
	var reg = wcm.MenuView.register.bind(wcm.MenuView);

	reg({
		key : 'document_import',
		desc : wcm.LANG.extensions_1001 || '文档',
		parent : 'import',
		order : 3,
		cmd : function(event){
		}
	});
	reg({
		key : 'template_import',
		desc : wcm.LANG.extensions_1002 || '模板',
		parent : 'import',
		order : 4,
		cmd : function(event){
		}
	});

	reg({
		key : 'document_export',
		desc : wcm.LANG.extensions_1003 || '所有文档',
		parent : 'export',
		order : 3,
		cmd : function(event){
		}
	});
	reg({
		key : 'template_export',
		desc : wcm.LANG.extensions_1004 || '所有模板',
		parent : 'export',
		order : 4,
		cmd : function(event){
		}
	});

	reg({
		key : 'document_add',
		desc : wcm.LANG.extensions_1001 || '文档',
		parent : 'add',
		order : 3,
		cmd : function(event){
		}
	});
	reg({
		key : 'template_add',
		desc : wcm.LANG.extensions_1002 || '模板',
		parent : 'add',
		order : 4,
		cmd : function(event){
		}
	});
	reg({
		key : 'extendfield_add',
		desc : wcm.LANG.extensions_1005 || '扩展字段',
		parent : 'add',
		order : 5,
		cmd : function(event){
		}
	});
	reg({
		key : 'distribution_add',
		desc : wcm.LANG.extensions_1006 || '站点分发',
		parent : 'add',
		order : 6,
		cmd : function(event){
		}
	});
	reg({
		key : 'replace_add',
		desc : wcm.LANG.extensions_1007 || '替换内容',
		parent : 'add',
		order : 7,
		cmd : function(event){
		}
	});
	reg({
		key : 'docsyndis_add',
		desc : wcm.LANG.extensions_1008 || '栏目分发',
		parent : 'add',
		order : 8,
		cmd : function(event){
		}
	});
	reg({
		key : 'docsyncol_add',
		desc : wcm.LANG.extensions_1009 || '栏目汇总',
		parent : 'add',
		order : 9,
		cmd : function(event){
		}
	});
	reg({
		key : 'workflow_add',
		desc : wcm.LANG.extensions_1010 || '工作流',
		parent : 'add',
		order : 10,
		cmd : function(event){
		}
	});
	reg({
		key : 'photo_add',
		desc : wcm.LANG.extensions_1011 || '图片',
		parent : 'add',
		order : 11,
		cmd : function(event){
		}
	});
})();
