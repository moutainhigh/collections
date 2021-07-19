/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm61_flow',
	methodName : 'jqueryChannelsUseingFlow',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		ObjectId : PageContext.getParameter('FlowId')
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	//TODO type filters here
]);

/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['FLOW_2'] || '个',
	TypeName : wcm.LANG['FLOW_3'] || '栏目'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'disableFlow' : function(event){
		disableflow(event);
	} 
});

 /*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_CHANNEL,
	hostType : (function(){
		return 'Flow';
	})(),
	hostId :  (function(){
		return PageContext.getParameter('FlowId');
	})(),
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		Ext.apply(context, {
			relateType : bIsChannel ? 'channelHost' : 'websiteHost'
		});
		return context;
	}
});

window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'disableFlows',
		name : wcm.LANG['FLOW_CANCEL_USE'] ||'取消分配'
	},{
		cmd : 'refresh',
		name : wcm.LANG['FLOW_REFRESH'] || '刷新'
	},{
		cmd : 'close',
		name :  wcm.LANG['FLOW_CLOSE'] || '关闭'
	}],
	withclose:false,
	size : [550, 300]
};

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_CHANNEL,
	beforeafterselect : function(event){
		PageContext.currEvent = event;
		return true;
	}
});
function disableFlows(){
	var event = PageContext.currEvent;
	if(event.getIds().length > 1){
		Ext.Msg.confirm(wcm.LANG['FLOW_49'] || '确实要取消所选栏目上的工作流分配吗? ', {
			yes : function(){
				BasicDataHelper.call('wcm6_process','removeChannelEmployersOfFlow', {"ObjectIds": event.getIds(), "FlowId": event.getHost().getId()}, true, 
					function(){
						PageContext.refreshList(null, []);
					}
				);
			}
		});
		 
	}
	else{
		disableflow(event);
	}
	return false;
}
function disableflow(event){
	var host = event.getHost();
	var channelId = event.getIds().join();
	if(channelId.length == 0){
		Ext.Msg.alert(wcm.LANG['FLOW_57'] || "请选择要取消的栏目");
		return false;
	}
	else if(channelId.length==1)
	var confirmInfo = wcm.LANG['FLOW_34'] || '确实要取消该栏目上的流程吗?';
	else confirmInfo = wcm.LANG['FLOW_58'] || '确实要取消这些栏目上的流程吗?';
	Ext.Msg.confirm(confirmInfo, {
		yes : function(){
			BasicDataHelper.call('wcm6_process','disableFlowToChannel', {
				'ObjectId': channelId,
				'FlowId': host.getId()
			},
			false, function(){
				PageContext.refreshList(null, []);
				return false;	
			});
		}
	});	
	return false;
}
function refresh(){
	PageContext.refreshList(null, []);
	return false;
}