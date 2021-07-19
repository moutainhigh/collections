//模板操作面板信息
 (function(){
	var sName = wcm.LANG['TEMPLATEARG'] || '模板变量';
	var m_sNameTemplate = '<B>&nbsp;{1}:&nbsp;&nbsp;</B>{0}<br>';
	var m_sTypeTemplate = '<B>&nbsp;{1}:&nbsp;&nbsp;</B>{0}<br>';
	var m_sValueTemplate = '<span class="arg_detail_value" title="{0}"><B>{1}:&nbsp</B>{0}</span>'
	 
	 
	function getEditInfo(obj){
		var sArgName = obj.getProperty('argName');
		var value = $transHtml(obj.getProperty('value'));
		var type = obj.getProperty('type');
		return String.format(m_sNameTemplate,sArgName,wcm.LANG['TEMPLATEARG_0'] || '名称') 
				+ String.format(m_sTypeTemplate,type, wcm.LANG['TEMPLATEARG_20'] || '类型') 
				+ String.format(m_sValueTemplate, value==null ? "" : value, wcm.LANG['TEMPLATEARG_1'] || '参数值');
	}
	wcm.PageOper.registerPanels({
		templateInRoot : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
			displayNum : 5
		},
		templateArgInSite : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
			displayNum : 5,
			detail : function(){
				return  wcm.LANG['TEMPLATEARG_5'] || '模板变量信息';
			}
		},
		templateArgInChannel : {
			isHost : true,
			title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
			displayNum : 5
		},
		templateArg : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			detail : function(cmsobjs, opt){
				var obj = cmsobjs.getAt(0);
				return getEditInfo(obj);
			}
		},
		templateArgs : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();