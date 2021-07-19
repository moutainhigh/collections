Ext.ns("wcm.MasterSelector");
(function(){
	Ext.apply(wcm.MasterSelector, {
		/**
		*@_oParams		Object
		*				templateIds  默认选中的模板
		*				templateType 需要过滤的模板类型, any(-1),nested(1),outline(1),detail(2)
		*@_fCallBack	返回结果之后，执行的回调函数。函数参数为
		*				arg1:{selectedIds: [id1,id2...], selectedNames: [name1,name2...]}
		*/
		selectMaster : function(_oParams, _fCallBack, _title){
			wcm.CrashBoarder.get("master_select_list").show({
				title : _title||'选择母板',
				src : WCMConstants.WCM6_PATH + 'special/master/master_select_list.html',
				width:'650px',
				height:'450px',
				maskable:true,
				params : _oParams,
				callback : function(_args){
					if((_fCallBack || Ext.emptyFn)(_args, this) !== false){
						this.close();
					}
				}				
			});
		}
	});
})();
