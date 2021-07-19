//短消息操作面板信息
(function(){
	var sName = wcm.LANG['MESSAGE'] || '短消息';
	var m_sDetailTemplate = [
		'<span class="message_attr_value" title="{0}"><B>{3}:&nbsp</B>{0}</span>',
		'<B>{4}:&nbsp;</B>{1}<br>',
		'<B>{5}:</B>&nbsp;&nbsp;{2}<br>',
		''
	].join('');
	function getContentTypeDesc(type){
		if(type==605)return wcm.LANG['IFLOWCONTENT_50'] || '文档';
	}
	wcm.PageOper.registerPanels({
		myMsgListInRoot : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', wcm.LANG['MESSAGE_LIST'] || '短消息列表'),
			displayNum : 5,
			detailTitle : wcm.LANG['MESSAGE_6'] ||'短消息列表详细信息'
		},
		myMsgList : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', wcm.LANG['MESSAGE_LIST'] || '短消息列表'),
			displayNum : 5,
			detailTitle : wcm.LANG['MESSAGE_6'] || '短消息列表详细信息',
			detail : function(){
				return wcm.LANG['MESSAGE_7'] || '我的短消息列表';
			}
		},
		message : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7,
			url : '?serviceid=wcm61_message&methodname=jFindbyid',
			detail : function(cmsobjs, opt){
				var obj = cmsobjs.getAt(0);
				return String.format(m_sDetailTemplate, $transHtml(obj.getProperty('title')),
					obj.getProperty('cruser'),
					obj.getProperty('crtime'),wcm.LANG['MESSAGE_2'] || "标题",
					wcm.LANG['MESSAGE_4'] ||"发送者",
					wcm.LANG['MESSAGE_5'] ||"发送时间");
			}
		},
		messages : {
			title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
			displayNum : 7
		}
	});
})();