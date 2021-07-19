//模板操作面板信息
(function(){
	var sName = wcm.LANG['TEMPLATE'] || '模板';
	wcm.PageOper.registerPanels({
		templateInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		templateInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		templateInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		template : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_template&methodname=jFindbyid'
		},
		templates : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'tempname',
		type :'filename',
		required :'',
		max_len :'50',
		desc :wcm.LANG.TEMPLATE_TEMPNAME||'模板名称',
		methods : PageContext.validExistProperty(function(oParams){
			var nRootId = PageContext.event.getObj().getPropertyAsInt("rootId");
			Ext.apply(oParams, {
				RootId : nRootId
			});
		})
	},
	{
		renderTo : 'TempDesc',
		type:'string',
		max_len:'200',
		desc:wcm.LANG.TEMPLATE_TEMPDESC||'模板描述'
	},
	{
		renderTo : 'TempExt',
		type :'common_char',
		required :'',
		desc :wcm.LANG.TEMPLATE_TEXNAME||'文件扩展名',
		max_len :'50'		
	},
	{
		renderTo : 'OutputFileName',
		type :'common_char',
		required :'',
		desc :wcm.LANG.TEMPLATE_OUTLINENAME||'发布文件名',
		max_len :'50'		
	}
]);