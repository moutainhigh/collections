//工作流菜单信息
(function(){
	var reg = wcm.MenuView.register.bind(wcm.MenuView);
	//回调函数，执行新建操作
	function add(result){
		var oPostData = {
			FlowId :  0,
			LoadView : 2,
			OwnerType : result.hostType,
			OwnerId : result.hostIds,
			ShowButtons : 0
		}
		setTimeout(function(){
			var cb = wcm.CrashBoarder.get('Dialog_Workflow_Editor');
			cb.show({
				title : wcm.LANG['FLOW_ADD_EDIT'] || '新建/修改工作流',
				src :  WCMConstants.WCM6_PATH + 'flow/flow_addedit.jsp',
				reloadable : true,
				top: '40px',
				left: '300px',
				width: '850px',
				height: '500px',
				params : oPostData,
				maskable : true,
				callback : function(_args){
					var result = this;
					var id = result.hostIds;
					var type = result.hostType;
					if(type==101/*channel*/&& _args['id'] > 0/*add success*/) {
						BasicDataHelper.call('wcm6_process', 'enableFlowToChannel', {
								ObjectId: id,
								FlowId: _args['id']
							}, false, function(_transport, _json){					
								$MsgCenter.$main().afteredit();
							}
						);
					}else{
						$MsgCenter.$main().afteredit();
					}
				}.bind(result)
			});
		}, 10);
		return false;
	}
	reg({
		key : 'flow_add', 
		desc : wcm.LANG['FLOW'] || '工作流',
		parent : 'add',
		order : '11',
		cmd : function(event){
			var oPostData1 = {
				isRadio : 1,
				excludeVirtual :1,
				rightIndex : 41,
				ExcludeInfoView : 0
			}
			//type code here
			var host = event.getHost();
			var hostType = host.getIntType();
			Ext.apply(oPostData1, wcm.menu.HostSelectAdapter.execute(event));
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'include/host_select.html?' + $toQueryStr(oPostData1),
					wcm.LANG.CONFIRM_11 || '选择对象', add);
		}
	});
})();