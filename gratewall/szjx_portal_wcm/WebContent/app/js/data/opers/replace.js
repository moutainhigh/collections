//替换内容操作信息和Mgr定义
Ext.ns('wcm.domain.ReplaceMgr');
(function(){
	var m_oMgr = wcm.domain.ReplaceMgr={
		serviceId : 'wcm6_replace',
		getHelper : function(_sServceFlag){
			return new com.trs.web2frame.BasicDataHelper();
		}
	};
	Ext.apply(wcm.domain.ReplaceMgr, {
		//add : function(_sObjectIds, _parameters){
		add : function(event){
			var host = event.getHost();
			var hostId = host.getId();
			var oparameters = "ChannelId="+hostId;
			oparameters += "&ObjectId=0" 
			FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
				wcm.LANG.REPLACE_8||'新建替换内容',
				CMSObj.afteradd(event)
			);
		},

		//edit : function(_sObjectIds, _parameters){
		edit : function(event){
			var sId = event.getIds();
			var host = event.getHost();
			var hostId = host.getId();
			var oparameters = "ChannelId="+hostId;
			oparameters += "&ObjectId=" + sId;
			FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
				wcm.LANG.REPLACE_EDIT_2||'修改替换内容',
				CMSObj.afteredit(event)
			);
		},

		//"delete" : function(_sObjectIds, _oPageParams){
		"delete" : function(event){
			var sIds = event.getIds() + '';
			var hostId = event.getHost().getId();
			var oPageParams = {
				ChannelId : hostId
			};
			var nCount = (sIds.indexOf(',') == -1) ? 1:sIds.split(',').length;
			var sHint = (nCount==1)?'':' '+nCount+' ';
			if (confirm(String.format(wcm.LANG.REPLACE_CONFIRM||('确实要将这{0}个替换内容删除吗?'),sHint))){
				m_oMgr.getHelper().call(m_oMgr.serviceId, 
					'delete', //远端方法名				
					Object.extend(oPageParams, {"ObjectIds": sIds}), //传入的参数
					false, //异步
					function(){//响应函数
						event.getObjs().afterdelete();
					}
				);
			}
		}
	});
})();
(function(){
	var pageObjMgr = wcm.domain.ReplaceMgr;
	var reg = wcm.SysOpers.register;

	reg({
		key : 'edit',
		type : 'replace',
		desc : wcm.LANG.REPLACE_EDIT||'修改这个替换内容',
		title:'修改这个替换内容',
		rightIndex : 13,
		order : 1,
		fn : pageObjMgr['edit'],
		quickKey : 'M'
	});
	reg({
		key : 'delete',
		type : 'replace',
		desc : wcm.LANG.REPLACE_DELETE||'删除这个替换内容',
		title:'删除这个替换内容',
		rightIndex : 13,
		order : 2,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});
	reg({
		key : 'add',
		type : 'replaceInChannel',
		desc : wcm.LANG.REPLACE_NEW||'新建替换内容',
		title:'新建替换内容',
		rightIndex : 13,
		order : 3,
		fn : pageObjMgr['add'],
		quickKey : 'N'
	});
	reg({
		key : 'delete',
		type : 'replaces',
		desc : wcm.LANG.REPLACES_DELETE||'删除这些替换内容',
		title:'删除这些替换内容',
		rightIndex : 13,
		order : 4,
		fn : pageObjMgr['delete'],
		quickKey : ['Delete', 'ShiftDelete']
	});

})();