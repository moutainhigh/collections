//视图字段操作面板信息
(function(){
	var sName = wcm.LANG['METAVIEWFIELD'] || '视图字段';
	wcm.PageOper.registerPanels({
		metaviewfieldInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		metaviewfieldInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5
		},
		metaviewfieldInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		metaviewfield : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_metaviewfield&methodname=jFindbyid'
		},
		metaviewfields : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();

ValidationHelper.bindValidations([
	{
		renderTo : 'FieldName',
		type :'common_char2',
		required :'',
		max_len :'30',
		desc :wcm.LANG.METAVIEWFIELD_FIELDNAME||'英文名称',
		methods : function(){
			var eleValue = this.field.value;
			if(eleValue){
				if(containKeyWords(eleValue)){
					this.warning = String.format("[<font color=\'red\'>{0}</font>]为系统保留字!",eleValue);
					return false;
				}			
				PageContext.validExistProperty({objType : 1886731157});
			}
			return true;
		}
	},
	{
		renderTo : 'AnotherName',
		type:'string2',
		required:'',
		max_len:'100',
		desc:wcm.LANG.METAVIEWFIELD_ANOTHERNAME||'中文名称'
	}
]);