//分类法操作面板信息
(function(){
	var sName = wcm.LANG['CLASSINFO'] || '分类法';
	wcm.PageOper.registerPanels({
		classinfoInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		classinfo : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_classinfo&methodname=jFindbyid'
		},
		classinfos : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'CName',
		type :'string',
		required :'',
		max_len :'50',
		desc :wcm.LANG.TEMPLATE_TEMPNAME||'名称',
		methods : PageContext.validExistProperty({objType : 694710472})
	},
	{
		renderTo : 'CDesc',
		type:'string',
		max_len:'100',
		desc:wcm.LANG.TEMPLATE_TEMPDESC||'描述'
	}
]);