Ext.ns('com.trs.ui');
/**
*栏目选择树扩展，当资源变量【所属栏目】为空时，栏目选择树默认展开到当前可视化模板所在的栏目
*/
(function(){

	Event.observe(window, 'load', function(){
		var chnlTreeComponent = com.trs.ui.ComponentMgr.get('所属栏目');
		if(!chnlTreeComponent) return;
		var config = chnlTreeComponent.initConfig;
		if(!config || (config.treeType === undefined)) return;
		
		try{
			config.currchnlid = top.nHostType == 101 ? top.nHostId : 0;
		}catch(error){
			//just skip it.
		}

	});
})();