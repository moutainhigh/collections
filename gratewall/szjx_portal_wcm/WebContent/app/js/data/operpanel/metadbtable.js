//元数据操作面板信息
(function(){
	var sName = wcm.LANG['METADBTABLE'] || '元数据';
	wcm.PageOper.registerPanels({
		metadbtableInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		metadbtableInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		metadbtableInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		metadbtable : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_metadbtable&methodname=jFindbyid'
		},
		metadbtables : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'TableName',
		type : 'common_char',
		required : '',
		max_len : '16',
		desc : wcm.LANG.METADBTABLE_36 || '名称'	
	},
	{
		renderTo : 'AnotherName',
		type : 'string',
		required : '',
		max_len : '60',
		desc: wcm.LANG.METADBTABLE_34 || '别名'
	},
	{
		renderTo : 'TableDesc',
		type : 'string',
		required : 'false',
		max_len : '120',
		desc : wcm.LANG.METADBTABLE_35 || '描述'	
	}
]);