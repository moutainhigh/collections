/*!
 * File:WorkflowMaster.js,工作流相关操作对象
 *
 *	History			Who			What
 *	2007-03-22		wenyh		created.
 *
 */

com.trs.wcm.WorkflowMaster = Class.create("wcm.WorkflowMaster");
Object.extend(com.trs.wcm.WorkflowMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.WorkflowMaster.prototype, {	
	extraParams : function(){		
		return Object.extend({
			fieldsToHtml: 'flowname,flowdesc'
		}, this.getHostParams());
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["objectids"];

		//工作流属主
		this.flowOwner = {}; 
		if(_oPageParameters["OwnerType"]){			
			Object.extend(this.flowOwner, {
				OwnerType: _oPageParameters["OwnerType"], 
				OwnerId: _oPageParameters["OwnerId"]
			});
		}
		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		
		//设置第一块操作面板的类型
		var sHostObjectType, sHostObjectId;		
		if(_oPageParameters["siteid"]){
			this.setOperPanelType(1, "workflowInSite");
			sHostObjectType = 'website';
			sHostObjectId = _oPageParameters["siteid"];
		}
		else if(_oPageParameters["SiteType"]){
			this.setOperPanelType(1, 'workflowInSys');			
			sHostObjectType = 'WebSiteRoot';
			sHostObjectId = _oPageParameters["SiteType"];
		}
	
		//设置第二块操作面板的类型		
			//===未选中对象====//
			if(pObjectIds == null || pObjectIds.length == 0){				
				this.setOperPanelType(2, sHostObjectType);
			}
			//===选中单个对象====//
			else if(pObjectIds != null && pObjectIds.length == 1){
				this.setOperPanelType(2, "workflow");				
			}
			//===选中多个对象====//
			else if(pObjectIds != null && pObjectIds.length > 1){
				this.setOperPanelType(2, "workflows");			
			}
		

		//设置Host的信息，必须和Host的操作类型一致		
		this.setHostObject(sHostObjectType, sHostObjectId);

		//设置Object属性
		if(pObjectIds != null && pObjectIds.length > 0){
			this.setObjectIds(pObjectIds);
		}	
		
		//只要拥有工作流的新建、修改、删除或者导入权限中的一个权限，便自动拥有“分配工作流到栏目”的操作权限
		if(_oPageParameters['objectrights'] && _oPageParameters['objectrights'].length > 0) {
			this.OpersCanNotDo = this.OpersCanNotDo || {};
			this.OpersCanNotDo["workflow"] = {
				'assign': !checkRight(_oPageParameters['objectrights'][0], [41, 42, 43, 45])
			};		
		}

	},
	saveSuccess : function(_bIgnoreUpdate){
		if(!_bIgnoreUpdate){
			$MessageCenter.sendMessage('main','Grid.updateColumn','Grid',this.RowColumnInfo);
		}
	},
		
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		Object.extend(hostParams,this.flowOwner);
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		var sOprName = _sOprName.toLowerCase();
		switch(_sOprType){
			case "workflow":
			case "workflows":
			case "workflowInSite":
			case "workflowInSys":
				if (window.WorkflowMgr == null) {
					alert("没有引入WorkflowMgr");
					return;
				}
				var oFunc = WorkflowMgr[sOprName];
				if(oFunc == null){
					alert("WorkflowMgr 中没有定义["+sOprName+"]方法");
					return;
				}

				//ge gfc add @ 2007-7-12 11:35 根据操作面板不同，决定传入什么样的OwnerType和OwnerId
				if(_sOprType == 'workflowInSite') {
					Object.extend(hostParams, {
						OwnerType: 103, 
						OwnerId: this.params['HostObjectId']						
					});
				}else if(_sOprType == 'workflowInSys') {
					Object.extend(hostParams, {
						OwnerType: 1, 
						OwnerId: this.params['HostObjectId']						
					});
				}
				oFunc.call(WorkflowMgr, sObjectIds, hostParams);
				break;	
				
			case "website": 
				if(com.trs.wcm.domain.WebSiteMgr == null){
					alert("没有引入com.trs.wcm.domain.WebSiteMgr");
					return;
				}
				var oFunc = $webSiteMgr[sOprName];
				if(oFunc == null){
					alert("com.trs.wcm.domain.WebSiteMgr 中没有定义["+sOprName+"]方法");
					return;
				}
				oFunc.call($webSiteMgr, sObjectIds, hostParams);
				break;
			case "WebSiteRoot":
				alert("TODO...\n WebSiteRoot");
				break;

			default:
				alert("[WorkflowMaster.js]此操作类型未定义! _sOprType=[" + _sOprType + "]");
				break;
		}
	}
});