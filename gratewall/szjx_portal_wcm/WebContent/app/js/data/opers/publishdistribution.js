//站点分发操作信息和Mgr定义
Ext.ns('wcm.domain.PublishDistributionMgr');
(function(){
	var m_oMgr = wcm.domain.PublishDistributionMgr;
	function getHelper(){
		return new com.trs.web2frame.BasicDataHelper();
	}
	Ext.apply(wcm.domain.PublishDistributionMgr, {
		/**
		 * @param : wcm.CMSObjEvent event
		 */
		add : function(event){
			var oPageParams = event.getContext();
			Object.extend(oPageParams,{"ObjectId":0});
			Object.extend(oPageParams,{"siteId":event.getHost().getId()});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_1 || '新建站点分发', CMSObj.afteradd(event)
			);
		},
		edit : function(event){
			var oPageParams = event.getContext();
			var sId = event.getIds().join();
			Object.extend(oPageParams,{"ObjectId":sId});
			Object.extend(oPageParams,{"siteId":event.getHost().getId()});
			FloatPanel.open(WCMConstants.WCM6_PATH +
					'publishdistribution/publishdistribution_add_edit.jsp?' + $toQueryStr(oPageParams), wcm.LANG.PUBLISHDISTRUBUTION_VALID_3 || '修改站点分发', CMSObj.afteredit(event)
			);
		},
		//删除单个和多个对象
		"delete" : function(event){
			var sId = event.getIds();
			var nCount = sId.toString().split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var sResult = String.format("确定要删除这{0}个分发点?",sHint);
			Ext.Msg.confirm(sResult,{
                yes : function(){
                    BasicDataHelper.call("wcm6_distribution", 
						'delete', //远端方法名				
						Object.extend(PageContext.params, {"ObjectIds": sId}), //传入的参数
						true, //异步
						function(){//响应函数
							event.getObjs().afterdelete();
						}
					);
                }
            });
		},
			//启用单个和多个对象
		"enable" : function(event){
			var sId = event.getIds();
			var nCount = sId.toString().split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var params = {};
			var sResult = String.format("确定要启用这{0}个分发点?",sHint);
			Ext.Msg.confirm(sResult,{
                yes : function(){
					Object.extend(params, {"ObjectIds" :sId.join(),'Enable':true});
					BasicDataHelper.call('wcm6_distribution', 
						'updateStatus', //远端方法名				
						params, //传入的参数
						false, //异步
						function(){//响应函数
							event.getObjs().afteredit();
						}
					);
                }
            });
		},
		//禁用单个和多个对象
		"disable" : function(event){
			var sId = event.getIds();
			var nCount = sId.toString().split(',').length;
			var params = {};
			var sHint = (nCount==1)?'':' '+nCount+' ';
			var sResult = String.format('确定要禁用这{0}个分发点?',sHint);
			Ext.Msg.confirm(sResult,{
                yes : function(){
					Object.extend(params, {"ObjectIds" :sId.join(),'Enable':false});
					BasicDataHelper.call('wcm6_distribution', 
						'updateStatus', //远端方法名				
						params, //传入的参数
						false, //异步
						function(){//响应函数
							event.getObjs().afteredit();
						}
					);
                }
            });
		},
		//禁用所有对象
		disableall : function(event){
			var params = {"FolderType" : 103};
			Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_12 ||'确实要将所有分发点都禁用吗?',{
                yes : function(){
					Object.extend(params, {'FolderId':event.getHost().getId()});
					Object.extend(params, {'Enable':false});
					BasicDataHelper.call('wcm6_distribution', 
						'updateStatusAll', //远端方法名				
						params, //传入的参数
						false, //异步
						function(){//响应函数
							event.getObjs().afteredit();
						}
					);
                }
            });	
		},
		//启用所有对象
		enableall : function(event){
			var params = {"FolderType" : 103};
			Ext.Msg.confirm(wcm.LANG.PUBLISHDISTRUBUTION_VALID_13 ||'确实要将所有分发点都启用吗?',{
                yes : function(){
//					ProcessBar.init(wcm.LANG.PUBLISHDISTRUBUTION_VALID_7 ||'启用进度');
//					ProcessBar.addState(wcm.LANG.PUBLISHDISTRUBUTION_VALID_8 ||'正在启用...', 2);
//					ProcessBar.start();
					Object.extend(params, {'FolderId':event.getHost().getId()});
					Object.extend(params, {'Enable':true});
					BasicDataHelper.call('wcm6_distribution', 
						'updateStatusAll', //远端方法名				
						params, //传入的参数
						false, //异步
						function(){//响应函数
//							ProcessBar.next();
							event.getObjs().afteredit();
						}
					);
                }
            });			
		}
		//*/
		//type here
	});
})();
(function(){
	var pageObjMgr = wcm.domain.PublishDistributionMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'publishdistribution',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_14 ||'修改这个分发点',
		title:'修改这个分发点',
		rightIndex : 1,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'disable',
		type : 'publishdistribution',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_15 ||'禁用这个分发点',
		title:'禁用这个分发点',
		rightIndex : 1,
		order : 2,
		fn : pageObjMgr['disable']
	});
	reg({
		key : 'enable',
		type : 'publishdistribution',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_16 ||'启用这个分发点',
		title:'启用这个分发点',
		rightIndex : 1,
		order : 3,
		fn : pageObjMgr['enable']
	});
	reg({
		key : 'delete',
		type : 'publishdistribution',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这个分发点',
		title:'删除这个分发点',
		rightIndex : 1,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'publishdistributionInSite',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_18 ||'新建分发点',
		title:'新建分发点',
		rightIndex : 1,
		order : 5,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'disableall',
		type : 'publishdistributionInSite',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_19 ||'禁用所有分发点',
		title:'禁用所有分发点',
		rightIndex : 1,
		order : 6,
		fn : pageObjMgr['disableall']
	});
	reg({
		key : 'enableall',
		type : 'publishdistributionInSite',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_20 ||'启用所有分发点',
		title:'启用所有分发点',
		rightIndex : 1,
		order : 7,
		fn : pageObjMgr['enableall']
	});
	reg({
		key : 'disable',
		type : 'publishdistributions',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_21 ||'禁用这些分发点',
		title:'禁用这些分发点',
		rightIndex : 1,
		order : 8,
		fn : pageObjMgr['disable']
	});
	reg({
		key : 'enable',
		type : 'publishdistributions',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_22 || '启用这些分发点',
		title:'启用这些分发点',
		rightIndex : 1,
		order : 9,
		fn : pageObjMgr['enable']
	});
	reg({
		key : 'delete',
		type : 'publishdistributions',
		desc : wcm.LANG.PUBLISHDISTRUBUTION_VALID_17 ||'删除这些分发点',
		title:'删除这些分发点',
		rightIndex : 1,
		order : 10,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();