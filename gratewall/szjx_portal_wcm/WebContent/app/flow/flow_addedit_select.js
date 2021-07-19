Ext.ns("wcm.Flow_AddEdit_Selector");
(function(){
	Ext.apply(wcm.Flow_AddEdit_Selector, {
		selectFlow : function(_oParams, _fCallBack){
			var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_select.html';
			wcm.CrashBoarder.get('Dialog_Workflow_Selector').show({
				title : wcm.LANG['FLOW_60'] || '选择工作流',
				reloadable : true,
				src : sUrl,
				width: '500px',
				height: '250px',
				params : _oParams,
				maskable : true,
				callback : function(_args){			
					if((_fCallBack || Ext.emptyFn)(_args, this) !== false){
						this.close();
					} 
				}
			})
		},
		_addEditFlow : function(o_params,_fCallBack){
			var width = "850px";
			var height = "500px";
			if(screen.width <= 1024){
				width = "650px";
				height = "450px";
			 }
			var cb = wcm.CrashBoarder.get('Dialog_Workflow_Editor');
			cb.show({
				title : wcm.LANG['FLOW_ADD_EDIT'] || '新建/修改工作流',
				src :  WCMConstants.WCM6_PATH + 'flow/flow_addedit.jsp',
				reloadable : true,
				width: width,
				height: height,
				params : o_params,
				maskable : true,
				callback : function(_args){
					if((_fCallBack || Ext.emptyFn)(_args, this) !== false){
						this.close();
					}
				}
			});
		}
	});
})();
