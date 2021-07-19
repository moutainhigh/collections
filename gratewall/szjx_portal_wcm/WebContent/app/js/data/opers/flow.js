//工作流操作信息和Mgr定义
Ext.ns('wcm.domain.FlowMgr');
(function(){
	var m_oFlowMgr = wcm.domain.FlowMgr;
	var serviceId = 'wcm6_process';
	function __checkNoRecords(_oHostInfos){
		if(_oHostInfos.num <= 0) {
			Ext.Msg.$fail(wcm.LANG['FLOW_33'] || '没有任何要操作的工作流！');
			return false;
		}
		return true;
	}
	function setFlowCallBack(_args){
		var nFlowId = _args['FlowId'];
		var nOldFlowId = _args['nOldFlowId'];
		if(_args['disabled'] == true && nOldFlowId != 0){
			BasicDataHelper.call('wcm6_process','disableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId: 0},true,function(_transport,_json){					
				$MsgCenter.$main().afteredit();
				//CMSObj.afteredit(nFlowId)();
			});
		}else if(nFlowId != nOldFlowId){							
			BasicDataHelper.call('wcm6_process','enableFlowToChannel',{ObjectId:_args['ChannelId'],FlowId:nFlowId},true,function(_transport,_json){					
				$MsgCenter.$main().afteredit();
				//CMSObj.afteredit(nFlowId)();
			});			
		}	
	}
	function assignFlowCallBack(m_nFlowId){
		return function (sSelIds){
			if(sSelIds.length == 0) {
				Ext.Msg.$fail(wcm.LANG['FLOW_23'] || '请选择要设置的栏目！');
				return false;
			}
			var params = {ObjectIds:sSelIds.join(','),FlowId:m_nFlowId};		
			BasicDataHelper.call('wcm6_process','setChannelEmployersOfFlow',params,true,function(_transport,_json){
				Ext.Msg.$timeAlert((wcm.LANG['FLOW_24'] || '设置成功！'), 3);
				FloatPanel.close();
			});		

			return false;
		}
	}
	function _addEditCallBack(event,addOrEdit){
		var arrIds = event.getIds();
		var host = event.getHost();
		var flowObj = event.getObjs().getAt(0);
		var nLoadView = 1;
		if(flowObj!=null && wcm.AuthServer.hasRight(flowObj.right, 42)){
			nLoadView = 2;
		}
		else if(flowObj==null && wcm.AuthServer.hasRight(host.right, 41)){
			nLoadView = 2;
		}
		if(addOrEdit==0){
			var o_params = {
				FlowId :  0,
				LoadView : nLoadView,
				OwnerType : host.getIntType(),
				OwnerId : host.getId(),
				ShowButtons : '0'
			}
		}
		else {
			var o_params = {
				FlowId : event.getIds().join(),
				LoadView : nLoadView,
				OwnerType : host.getIntType(),
				OwnerId : host.getId(),
				ShowButtons : '0'
			}
		}
	}
	function _addEdit(event, addOrEdit, params){
		var arrIds = event.getIds();
		var host = event.getHost();
		var flowObj = event.getObjs().getAt(0);
		var nLoadView = 1;
		if(flowObj!=null && wcm.AuthServer.hasRight(flowObj.right, 42)){
			nLoadView = 2;
		}
		else if(flowObj==null && wcm.AuthServer.hasRight(host.right, 41)){
			nLoadView = 2;
		}
		if(addOrEdit==0){
			var o_params = {
				FlowId :  0,
				LoadView : nLoadView,
				OwnerType : host.getIntType(),
				OwnerId : host.getId(),
				ShowButtons : '0'
			}
		}
		else {
			var o_params = {
				FlowId : event.getIds().join(),
				LoadView : nLoadView,
				OwnerType : host.getIntType(),
				OwnerId : host.getId(),
				ShowButtons : '0'
			}
		}
		Ext.apply(o_params, params||{});
		wcm.Flow_AddEdit_Selector._addEditFlow(o_params, function flowAddeditCallBack(_args){
			var event = this;
			var fnEvent = addOrEdit==0/*add*/ ? CMSObj.afteradd(event)
					: CMSObj.afteredit(event);
			var host = event.getHost();
			if(host.getIntType()==101/*channel*/
					&& _args['id'] > 0/*add success*/) {
				BasicDataHelper.call('wcm6_process', 'enableFlowToChannel', {
						ObjectId: host.getId(),
						FlowId: _args['id']
					}, false, function(_transport, _json){
						fnEvent(_args['id']); 
					}
				);
			}else{
				fnEvent(_args['id']);
			}
		}.bind(event));
	}
	Ext.apply(wcm.domain.FlowMgr, {
		//type here
		add : function(event){
			_addEdit(event,0);
		},
		edit : function(event){
			_addEdit(event,1);
		},
		view : function(event){
			_addEdit(event,1,{readonly:true});
		},
		assign : function(event){
			var context = event.getContext();
			var currObj = event.getObjs().getAt(0);
			var m_nFlowId = currObj.getId();
			Object.extend(context, {FlowId : event.getIds().join()});
			var params = {
				IsRadio : 0,
				ExcludeTop : 1,
				ExcludeLink : 1,
				ShowOneType : 1,
				MultiSites : 0,
				RightIndex : 42,
				ExcludeInfoview : 0,
				ExcludeOnlySearch : 1
			}
			BasicDataHelper.call('wcm6_process','getChannelsUseingFlow',{ObjectId:m_nFlowId,OnlyReturnIds:true,IdsValueType:0},false,function(_transport,_json){			
				var ids = _transport.responseText||'';
				if(ids.trim().length > 0){
					Object.extend(params,{SELECTEDCHANNELIDS : ids});
				}
				var nFlowOwnerType = currObj.getPropertyAsInt('ownerType' ,0);
				var nFlowOwnerId = currObj.getPropertyAsInt('ownerId' ,0);
				if(nFlowOwnerType == 103){
					Object.extend(params,{MultiSites : 0,
						 CurrSiteId : 	context.siteid || nFlowOwnerId					
					});
				}else if(nFlowOwnerType == 101){
					Object.extend(params,{MultiSites : 0});
					if(context.siteid)
					Object.extend(params,{CurrSiteId : context.siteid});				   
				}else{
					Object.extend(params,{SiteTypes : nFlowOwnerId,CurrSiteType : context.sitetype || nFlowOwnerId});
				}
				var currSiteType = event.getContext().get("siteType") || 0  ;
				Object.extend(params,{CurrSiteType:currSiteType,MultiSites : 0});
				var currHostType = event.getHost().getProperty('objType');//获得当前工作流对象的上级对象的objType属性
				if(currHostType == "channel"){
					//假如是栏目工作流的时候，就显示当前库的栏目选择树
					Object.extend(params,{SiteTypes:currSiteType,MultiSites : 0});
				}
				FloatPanel.open({
					src : WCMConstants.WCM6_PATH + 'include/channel_select.html',
					title : wcm.LANG['FLOW_70'] || '分配工作流',
					callback : assignFlowCallBack(m_nFlowId),
					dialogArguments : params
				});
			});
			
		},
		'import' : function(event){
			var context = event.getContext();
			var params = {
				OwnerId: context.OwnerId,
				OwnerType: context.OwnerType
			}
			FloatPanel.open(WCMConstants.WCM6_PATH + 'flow/flow_import.html?' + $toQueryStr(params), 
				wcm.LANG['FLOW_IMPORT'] ||'工作流导入', CMSObj.afteradd(event));
		},
		'export' : function(event){
			if(!__checkNoRecords(event.getContext())) {
				return;
			}
			var oPostData = {
				objectIds: event.getIds().join()
			};
			BasicDataHelper.call(serviceId, 'exportFlows', oPostData, true, function(_trans, _json){
				var sFileUrl = _trans.responseText;
				var frm = document.getElementById("ifrmDownload");
				if(frm == null) {
					frm = document.createElement('iframe');
					frm.style.height = 0;
					frm.style.width = 0;
					document.body.appendChild(frm);
				}
				sFileUrl = WCMConstants.WCM6_PATH + "file/read_file.jsp?DownName=" + sFileUrl + "&FileName=" + sFileUrl;
				frm.src = sFileUrl;
			
			}.bind(this));	
		},
		'delete' : function(event){
			var o_params = {
				objectids : event.getIds().join()
			}
			var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_employment_info.jsp';
			wcm.CrashBoarder.get('workflow_info_dialog').show({
				title : wcm.LANG['FLOW_32'] || '系统提示信息',
				reloadable : true,
				src : sUrl,
				width: '450px',
				height: '220px',
				params : o_params,
				maskable : true,
				callback : function(){
					var event = this;
					var host = event.getHost();
					BasicDataHelper.call(serviceId,'delete', Object.extend(o_params,{
							'ObjectIds': event.getIds().join(), 
							'drop': true
						}), 
					true, 
					function(){
						//event.getObjs().afterdelete();
						CMSObj.afterdelete(event)();
					});
					return false;
				}.bind(event)
			});
		},
		setemployer : function(event){
			//alert(Ext.toSource(event));
			var id = event.getIds().join()|| 0;
			var context = event.getContext();
			var host = event.getHost();
			var params = {
				OwnerType:  context.OwnerType,
				OwnerId:  context.OwnerId,
				ChannelId: host.getId(),
				CurrFlowId: id
			};
			wcm.Flow_AddEdit_Selector.selectFlow(params,setFlowCallBack);
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.FlowMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'add',
		type : 'flowInChannel',
		isHost : true,
		desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
		title:'新建工作流',
		rightIndex : 41,
		order : 2,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'setemployer',
		type : 'flowInChannel',
		isHost : true,
		desc : wcm.LANG['FLOW_SET'] || '设置工作流',
		title:'设置工作流',
		rightIndex : 13,
		order : 3,
		fn : pageObjMgr['setemployer']
	});
	reg({
		key : 'edit',
		type : 'flow',
		desc : wcm.LANG['FLOW_EDIT'] || '编辑这个工作流',
		title:'编辑这个工作流',
		rightIndex : 42,
		order : 4,
		fn : pageObjMgr['edit'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
				return false;
			}
			return true;
		},
		quickKey : 'M'
	});
	reg({
		key : 'delete',
		type : 'flow',
		desc : wcm.LANG['FLOW_DELETE'] || '删除这个工作流',
		title:'删除这个工作流',
		rightIndex : 43,
		order : 5,
		fn : pageObjMgr['delete'],
		isVisible : function(event){
			var host = event.getHost();
			if(host.getType()==WCMConstants.OBJ_TYPE_CHANNEL){
				return false;
			}
			return true;
		},
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'assign',
		type : 'flow',
		desc : wcm.LANG['FLOW_ASSIGN'] || '分配这个工作流到栏目',
		title:'分配这个工作流到栏目',
		rightIndex : 42,
		order : 6,
		fn : pageObjMgr['assign']
	});
	reg({
		key : 'export',
		type : 'flow',
		desc : wcm.LANG['FLOW_EXPORT'] ||'导出这个工作流',
		title:'导出这个工作流',
		rightIndex : 44,
		order : 7,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});
	reg({
		key : 'add',
		type : 'flowInSite',
		isHost : true,
		desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
		title:'新建工作流',
		rightIndex : 41,
		order : 8,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'import',
		type : 'flowInSite',
		isHost : true,
		desc : wcm.LANG['FLOW_37'] || '导入工作流',
		title:'导入工作流',
		rightIndex : 45,
		order : 9,
		fn : pageObjMgr['import'],
		quickKey : 'I'
	});
	reg({
		key : 'add',
		type : 'flowInSys',
		isHost : true,
		desc : wcm.LANG['FLOW_ADD'] || '新建工作流',
		title:'新建工作流',
		rightIndex : 41,
		order : 10,
		fn : pageObjMgr['add'],
		quickKey : 'N'

	});
	reg({
		key : 'import',
		type : 'flowInSys',
		isHost : true,
		desc : wcm.LANG['FLOW_37'] || '导入工作流',
		title:'导入工作流',
		rightIndex : 45,
		order : 11,
		fn : pageObjMgr['import'],
		quickKey : 'I'
	});
	reg({
		key : 'delete',
		type : 'flows',
		desc : wcm.LANG['FLOWS_DELETE'] || '删除这些工作流',
		title:'删除这些工作流',
		rightIndex : 43,
		order : 12,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']

	});
	reg({
		key : 'export',
		type : 'flows',
		desc : wcm.LANG['FLOWS_EXPORT'] || '导出这些工作流',
		title:'导出这些工作流',
		rightIndex : 44,
		order : 13,
		fn : pageObjMgr['export'],
		quickKey : 'X'
	});

})();