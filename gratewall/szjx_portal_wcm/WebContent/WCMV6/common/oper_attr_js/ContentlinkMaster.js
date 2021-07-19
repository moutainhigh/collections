com.trs.wcm.ContentlinkMaster = Class.create("wcm.ContentlinkMaster");
Object.extend(com.trs.wcm.ContentlinkMaster.prototype, com.trs.wcm.AbstractMaster.prototype);
Object.extend(com.trs.wcm.ContentlinkMaster.prototype, {	
	extraParams : function(){
		return this.getHostParams();
	},
	initParams : function(_oPageParameters){
		//获取参数		
		var pObjectIds = _oPageParameters["ObjectIds"];				
		this.setObjectsRight(_oPageParameters["ObjectRights"]||"0");

		this.setOperPanelType(1, "contentlinkInChannel");				
		if(pObjectIds.length > 0){
			this.isHostObjectAttribute = false;
			if(pObjectIds.length == 1){
				this.setOperPanelType(2,"contentlink");
			}else{
				this.setOperPanelType(2,"contentlinks");
			}
			this.setObjectIds(pObjectIds);
		}else{
			this.setOperPanelType(2, "channel");								
		}	
		this.setHostObject("channel", _oPageParameters["channelid"]);		
	},
	saveSuccess : function(){
		$MessageCenter.sendMessage('main','PageContext.updateCurrRows','PageContext',[]);
	},		
	exec : function(_sOprType, _sOprName){
		//根据不同操作类型，执行业务对象中的不同方法，方法和操作的英文名称一一对应
		var hostParams	= this.getHostParams();
		var sObjectIds	= this.getObjectIds();
		Object.extend(hostParams,{ObjectId:sObjectIds,ObjectIds:sObjectIds});
		
		var sOprName = _sOprName.toLowerCase();
		switch(_sOprType){
			case "contentlinkInChannel":	
				if("set" == sOprName){
					FloatPanel.open('./contentlink/contentlink_channel_set.html?' + $toQueryStr(hostParams), '按分类导入系统热词', 600, 300);					
				}else{
					FloatPanel.open('./contentlink/contentlink_channel_add.html?' + $toQueryStr(hostParams), '新建栏目热词', 380,180);	
				}				
				break;	
			case "contentlink" :
			case "contentlinks" :
				if("add" == sOprName){
					FloatPanel.open('./contentlink/contentlink_channel_add.html?' + $toQueryStr(hostParams), '新建栏目热词', 380,180);	
				}else if("edit" == sOprName){
					FloatPanel.open('./contentlink/contentlink_channel_edit.html?' + $toQueryStr(hostParams), '编辑栏目热词', 380,180);	
				}else{
					//delete contentlink.
					$confirm("您确定需要删除选定的热词？",function(){
						$dialog().hide();
						BasicDataHelper.call("wcm6_contentlink","delete",hostParams,true,function(_transport,_json){
							$MessageCenter.sendMessage('main','PageContext.RefreshList',"PageContext");
						})},function(){
							$dialog().hide();
						},"删除确认"
					);
				}
				break;
			case "channel":
				if(com.trs.wcm.domain.ChannelMgr == null){
					alert("没有引入com.trs.wcm.domain.ChannelMgr");
					return;
				}
				var oFunc = $channelMgr[sOprName];
				if(oFunc == null){
					alert("com.trs.wcm.domain.ChannelMgr 中没有定义["+sOprName+"]方法");
					return;
				}
				oFunc.call($channelMgr, sObjectIds, hostParams);
				break;
			default :
				break;
		}		
	}
});
