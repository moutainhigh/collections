//工作流操作面板信息
(function(){
	var sName = wcm.LANG['FLOW'] || '工作流';
	wcm.PageOper.registerPanels({
		flowInSys : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		flowInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		flowInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		flow : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_flow&methodname=jFindbyid',
			params : function(def, opt){
				if(location.href.indexOf('flow_chnl_list.html')!=-1){
					return 'FromChnl=1';
				}
				return '';
			}
		},
		flows : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();
ValidationHelper.bindValidations([
	{
		renderTo : 'flowName',
		type:'string',
		required:'',
		max_len:'50',
		desc: wcm.LANG['FLOW_NAME'] || '工作流名称',
		methods : PageContext.validExistProperty()
	}
]);