//流转文档操作面板信息
(function(){
	var sName = wcm.LANG['IFLOWCONTENT'] || '流转文档';
	var m_sDetailTemplate = [
		'<B>{6}:&nbsp;</B>{0}<br>',
		'<span class="flowcontent_attr_value" title="{1}"><B>{9}:&nbsp</B>{1}</span></br>',
		'<B>{7}:&nbsp;</B>{2}<br>',
		'<B>{8}:</B>&nbsp;&nbsp;{3}<br>',
		'{4}<br>',
		'{5}<br>',
		''
	].join('');
	function getContentTypeDesc(type){
		if(type==605)return wcm.LANG['IFLOWCONTENT_50'] || '文档';
	}
	wcm.PageOper.registerPanels({
		myFlowDocList : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 5,
			detailTitle : wcm.LANG['IFLOWCONTENT_51'] || '工作列表详细信息',
			detail : function(){
				return wcm.LANG['IFLOWCONTENT_52'] || '我的工作列表';
			}
		},
		flowContentInHost : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', '我的工作列表'),
			displayNum : 5,
			detailTitle : wcm.LANG['IFLOWCONTENT_51'] || '工作列表详细信息'
		},
		iflowcontent : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 8,
			detail : function(cmsobjs, opt){
				var obj = cmsobjs.getAt(0);
				return String.format(m_sDetailTemplate, getContentTypeDesc(obj.getProperty('contenttype')),
					$transHtml(obj.getProperty('contenttitle')), obj.getProperty('cruser'),
					obj.getProperty('crtime') , obj.getPropertyAsString('FlagDesc', '') ,
					obj.getPropertyAsString('Comment', ''), wcm.LANG['IFLOWCONTENT_85']||'类型', 
					wcm.LANG['IFLOWCONTENT_86']||'创建者', wcm.LANG['IFLOWCONTENT_87']||'创建时间',wcm.LANG['IFLOWCONTENT_89']||'标题');
			}
		},
		iflowcontents : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 8
		}
	});
})();
