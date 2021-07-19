//文档同步操作信息和Mgr定义
Ext.ns('wcm.domain.ChannelSynMgr');
(function(){
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.ChannelSynMgr, {
		//同步指定分发任务中尚未分发的内容
		"synundo" : function(event){
			var oPageParams = {};
			var sId = event.getIds();
			var nCount = sId.toString().split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var sResult = String.format('确实要立即执行这{0}个栏目分发吗?', sHint);
			Ext.Msg.confirm(sResult,{
                yes : function(){
					BasicDataHelper.call("wcm61_docsyn", 
						'synUndoContent', //远端方法名				
						Object.extend(oPageParams, {"ObjectIds": sId,"channelId":event.getHost().getId()}), //传入的参数
						true, //异步
						function(){//响应函数
							//event.getObjs().afterdelete();							
						}
					);
                }
            });
		}
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ChannelSynMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'synundo',
		type : 'channelsyn',
		desc : '立即执行分发',
		title: '立即执行分发',
		rightIndex : 13,
		fn : pageObjMgr['synundo']		
	});

	reg({
		key : 'synundo',
		type : 'channelsyns',
		desc : '立即执行分发',
		title: '立即执行分发',
		rightIndex : 13,
		fn : pageObjMgr['synundo']		
	});
	
})();