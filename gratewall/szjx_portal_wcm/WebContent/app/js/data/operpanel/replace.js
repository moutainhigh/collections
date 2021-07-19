//替换内容操作面板信息
(function(){
	var sName = wcm.LANG['REPLACE'] || '替换内容';
	wcm.PageOper.registerPanels({
		replaceInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		replaceInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		replaceInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		replace : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_replace&methodname=jFindbyid'
		},
		replaces : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'ReplaceName',
		type :'string',
		required :'',
		max_len :'50',
		desc :wcm.LANG.REPLACENAME||'标题',
		methods : PageContext.validExistProperty()
	},
	{
		renderTo : 'ReplaceContent',
		type:'string',
		required:'',
		max_len:'500',
		desc:wcm.LANG.REPLACECONTENT||'内容'
	}
]);