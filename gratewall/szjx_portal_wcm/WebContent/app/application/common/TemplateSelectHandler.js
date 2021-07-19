/**
*注册单击"模板选择"的处理
*/
Event.observe(window, 'load', function(){
	Event.observe('selectTemplate', 'click', function(){
		var params = {channelId:m_nChannelId,templateType:2,selectType:"radio",templateIds:$("spDetailTemp").getAttribute('tempIds')||0};
		wcm.TemplatSelector.selectTemplate(params, function(_args){
			var sName = _args.selectedNames[0] || "无";
			var sId = _args.selectedIds[0] || 0;
			Element.update("spDetailTemp", sName);
			$("spDetailTemp").setAttribute('tempIds', sId);
		});
	});
});