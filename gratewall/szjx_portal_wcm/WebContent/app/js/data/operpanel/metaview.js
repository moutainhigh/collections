//视图操作面板信息
(function(){
	var sName = wcm.LANG['METAVIEW'] || '视图';
	wcm.PageOper.registerPanels({
		metaviewInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		metaviewInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		metaviewInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		metaview : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_metaview&methodname=jFindbyid'
		},
		metaviews : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'ViewDesc',
		type : 'string',
		required : '',
		max_len : '60',
		desc : wcm.LANG.METAVIEW_VIEWDESC || '名称',
		methods : function(){
			var oViewDesc = this.field;
			if(oViewDesc){
				var msg = null;
				if(/[<>;&]/.test(oViewDesc.value)){
					this.warning =  "<font color='red'>" 
						+ (wcm.LANG.METAVIEW_ALERT_7 || "名称中含有特殊字符")
						+ "<b>;&&lt;&gt;</b></font>.";
					return false;
				}
				return true;
			}
		}
	}
]);
