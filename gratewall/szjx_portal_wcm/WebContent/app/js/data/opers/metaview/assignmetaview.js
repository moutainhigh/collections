//视图操作信息和Mgr定义
Ext.ns('wcm.domain.MetaViewMgr','wcm.domain.MetaViewService');
(function(){
	var m_oMgr = wcm.domain.MetaViewMgr;
	var m_sServiceId = "wcm61_metaview";
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}

	function assignMetaViewCallBack(_nMetaViewId){
		//alert("_nMetaViewId=" + _nMetaViewId);
		
		return function (sSelIds){
			if(sSelIds.length == 0) {
				Ext.Msg.$fail('请选择要设置的栏目！');
				return false;
			}
			//设置视图到栏目中，首先获取到所有栏目，如果没有在这上面的，都进行取消掉！
			var params = {ObjectIds:sSelIds.join(','),ViewId:_nMetaViewId};
			getHelper().call(m_sServiceId,'setChannelEmployersOfMetaView',params,true,function(_transport,_json){
				Ext.Msg.$timeAlert('设置成功！', 3);
				FloatPanel.close();
			});

			return false;
		}
		
	}

	Ext.apply(m_oMgr, {
		assign : function(event){

			var params = {
				IsRadio : 0,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ShowOneType : 1,
				MultiSites : 0,
				RightIndex : -1,
				ExcludeInfoview : 0,
				ExcludeOnlySearch : 1
			}
			//1、获取当前视图ID
			var _nMetaViewId = event.getObj().getId();
			//alert("分配这个视图到栏目，viewID="+ _nMetaViewId);

			//2、根据_arrIds视图ID获取到当前视图分配到的这些个栏目。返回栏目IDS
			getHelper().call(m_sServiceId,'getChannelsUseingMetaView',{ObjectId:_nMetaViewId,OnlyReturnIds:true,IdsValueType:0},false,function(_transport,_json){			
				var ids = _transport.responseText||'';
				//alert("IDS===" + ids);
				if(ids.trim().length > 0){
					Object.extend(params,{SELECTEDCHANNELIDS : ids});
				}
				
			});
			
			//3、弹出来栏目选择树
			FloatPanel.open({
				src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
				title : '分配视图到栏目',
				callback : assignMetaViewCallBack(_nMetaViewId),
				dialogArguments : params
			});
			
		}
	});

})();


(function(){
	var pageObjMgr = wcm.domain.MetaViewMgr;
	var reg = wcm.SysOpers.register;
	//在资源库的视图列表可以选择一个视图,然后分配给多个栏目,而不需要为每个栏目设置一次.
	reg({
		key : 'assign',
		type : 'MetaView',
		desc : '分配这个视图到栏目',
		title:'分配这个视图到栏目',
		rightIndex : -1,
		order : 5,
		fn : pageObjMgr['assign']
	});
	
})();