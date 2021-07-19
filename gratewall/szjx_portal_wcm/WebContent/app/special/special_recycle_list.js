/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_special',
	methodName : 'restoreQuery',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		OrderBy : "CrTime desc",
		"pageSize" : "6",
		"Status" : -1 // 获取已删除的专题
	}
});


wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			SPECIALNAME : sValue
		});
	}
});

wcm.Toolbar.register({
	'restore' : function(){
		var sObjIds = getSelectedListItemIds();
		var nCount = getSelectedListItemIds().length;
		if(nCount<=0){
			Ext.Msg.alert("没有选择要还原的专题.");
			return;
		}
		var oPostData = {
			objectIds : sObjIds
		};
		Ext.Msg.confirm('确实要还原选中的专题吗? ', {
			yes : function(){
				try{
					if(top.ProcessBar) top.ProcessBar.start("还原专题！");
				}catch(error){}
				var helper = new com.trs.web2frame.BasicDataHelper();
				helper.call('wcm61_special','restore', oPostData, true, 
					function(){
						try{
							if(top.ProcessBar) top.ProcessBar.close();
						}catch(error){}
						//页面刷新
						PageContext.loadList(PageContext.params);
						var c_bWin = wcm.CrashBoarder.get(window);
						c_bWin.notify();	
				});
			}
		});
	},
	'delete' : function(){
		var sObjIds = getSelectedListItemIds();
		var nCount = getSelectedListItemIds().length;
		if(nCount<=0){
			Ext.Msg.alert("没有选择要删除的专题.");
			return;
		}
		var oPostData = {
			objectIds : sObjIds,
			drop : true
		};
		Ext.Msg.confirm('确实要删除选中的专题吗? ', {
			yes : function(){
				try{
					if(top.ProcessBar) top.ProcessBar.start("删除专题！");
				}catch(error){}
				var helper = new com.trs.web2frame.BasicDataHelper();
				helper.call('wcm61_special','delete', oPostData, true, 
					function(){
						try{
							if(top.ProcessBar) top.ProcessBar.close();
						}catch(error){}
						//页面刷新
						PageContext.loadList(PageContext.params);
				});
			}
		});
	}
});

/*获取选中的专题*/
function getSelectedListItemIds(){
	var result = [];
	var doms = document.getElementsByClassName('special_checkbox');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			result.push(doms[i].id);
		}
	}
	return result;
}

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '专题'
});

window.m_cbCfg = {
	btns : [
		{
			extraCls : 'wcm-btn-close',
			text : '关闭',
			cmd : function(){
			}
		}
	]
};