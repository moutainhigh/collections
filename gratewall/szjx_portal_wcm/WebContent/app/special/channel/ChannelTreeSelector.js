Ext.ns("wcm.ChannelTreeSelector");
(function(){
	Ext.apply(wcm.ChannelTreeSelector, {
		/**
		*@_oParams		
		*				treeType   树是单选还是多选
		*				selectedValue  默认选中的节点
		*@_fCallBack	返回结果之后，执行的回调函数。函数参数为
		*				arg1:{ids: [id1,id2...], names: [name1,name2...]}
		*/
		selectChannelTreeTree : function(params, fCallBack){
			var selectedChannelIds = params["selectedValue"];
			var radOrCkx = params["treeType"];
			var oparams = {
				isRadio:radOrCkx,
				ExcludeSelf:0,
				SelectedChannelIds:selectedChannelIds,
				ExcludeTop:1,
				ExcludeVirtual:1,
				MultiSiteType:0,
				CurrChannelId : params["CurrChannelId"] || 0,
				SiteTypes:'0'
			};
			wcm.CrashBoarder.get("default_select_channel").show({
				title : "选择栏目",
				src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
				width: '400px',
				height: '450px',
				maskable : true,
				params:oparams,
				callback : function(_args){
					if(fCallBack){
						fCallBack(_args)
					}
				}
			});
		}
	});
})();
