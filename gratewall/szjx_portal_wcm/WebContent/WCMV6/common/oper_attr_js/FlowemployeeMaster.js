/*!
 * File:FlowemployeeMaster.js,栏目[工件流]标签:当前栏目使用的流程显示
 *
 *	History			Who			What
 *	2007-03-29		wenyh		created.
 *
 */

com.trs.wcm.FlowemployeeMaster = Class.create("wcm.FlowemployeeMaster");
Object.extend(com.trs.wcm.FlowemployeeMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.FlowemployeeMaster.prototype, {	
	extraParams : function(){
		var params = Object.extend({
			fieldsToHtml: 'flowname,flowdesc,owner.name'
		}, this.getHostParams());
		return params;
	},
	initParams : function(_oPageParameters){	
		//alert($toQueryStr(_oPageParameters))
		//alert(_oPageParameters['objectrights'])
		//获取参数		
		var pFlowId = _oPageParameters["FlowId"] || 0;
		
		//工作流属主
		this.flowOwner = {};
		if(_oPageParameters["OwnerType"]){			
			Object.extend(this.flowOwner,{OwnerType:_oPageParameters["OwnerType"],OwnerId:_oPageParameters["OwnerId"]});
		}
		//设置权限
		this.setObjectsRight(_oPageParameters['objectrights'],'0');
		this.HostRight = this.ObjectRight;
		
		//设置第一块操作面板的类型			
		this.setOperPanelType(1,"flowOfChannel");
	
		//设置第二块操作面板的类型		
		if(pFlowId > 0){				
			this.setOperPanelType(2, "flowemployee");
			this.setObjectIds(pFlowId);	
		}else {
			this.setOperPanelType(2, "flowemployees");
			this.setObjectIds(["",""]);//play with tricks.
		}	

		//设置Host的信息，必须和Host的操作类型一致		
		this.setHostObject("flowOfChannel", _oPageParameters["ChannelId"]);		
	},
	saveSuccess : function(){
		$MessageCenter.sendMessage('main','PageContext.loadFlow','PageContext',[]);
	},
		
	//操作响应
	exec : function(_sOprType, _sOprName){
		var sObjectIds	= this.getObjectIds();
		var hostParams	= this.getHostParams();
		Object.extend(hostParams,this.flowOwner);
		
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		var sOprName = _sOprName.toLowerCase();
		switch(_sOprType){
			case "flowOfChannel":
			case "flowemployee":
			case "flowemployees":			
				if (window.WorkflowMgr == null) {
					alert("没有引入WorkflowMgr");
					return;
				}
				var oFunc = WorkflowMgr[sOprName];
				if(oFunc == null){
					alert("WorkflowMgr 中没有定义["+sOprName+"]方法");
					return;
				}
				//ge gfc modify @ 2007-5-13 21:25
				//oFunc.call(WorkflowMgr, sObjectIds, hostParams);
				//alert($toQueryStr(PageContext.params))
				var params = Object.extend({}, hostParams);
				if(PageContext.params['siteid']) {
					Object.extend(params, {
						siteid: PageContext.params['siteid']
					});
				}
				//栏目-工作流视图之上新建时，默认新建到栏目所在站点
				if(PageContext.params['FlowId'] == null || PageContext.params['FlowId'] == 0) {
					//ge gfc modify @ 2007-7-19 13:25 在栏目-工作流视图上新建工作流时，只能新建到站点之上
					/*if(hostParams['flowOfChannelid'] > 0) {
						Object.extend(params, {
							OwnerId: hostParams['flowOfChannelid'], 
							OwnerType: 101
						})
					}else{
						Object.extend(params, {
							OwnerId: PageContext.params['siteid'], 
							OwnerType: 103
						})
					}//*/
					Object.extend(params, {
						OwnerId: PageContext.params['siteid'], 
						OwnerType: 103
					})

				}else{
					Object.extend(params, {
						OwnerId: $('hdOwnerId').value,
						OwnerType: $('hdOwnerType').value
					});
				}
				
				
				oFunc.call(WorkflowMgr, sObjectIds, params);

				break;

			default:
				alert("[FlowemployeeMaster.js]此操作类型未定义! _sOprType=[" + _sOprType + "]");
				break;
		}
	}
});