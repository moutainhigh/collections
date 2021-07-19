/*-------------中间内容请求的服务相关信息--------------*/
var nChannelId = getParameter('channelId');
Ext.apply(PageContext, {
	serviceId : 'wcm61_template',
	methodName : 'jQueryForSpecial',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"HostType" : 101,
		"hostId" : nChannelId,
		"ContainsChildren" : true,
		OrderBy : "lastModifiedTime desc",
		"pageSize" : "8"
	}
});


function selectChannel(nFolderId, nFolderType, nObjId, nObjType, sTempName){
	wcm.CrashBoarder.get('channel_select').show({
		title : String.format('将模板[{0}]分配给栏目',sTempName),
		src : '../template/channel_select_forCB.html?HostId=' + nFolderId+'&HostType='+nFolderType+"&TemplateId="+nObjId+"&TemplateType="+nObjType,
		width:'400px',
		height:'400px',
		maskable : true,
		callback : function(_json){
		}
	});	
}

wcm.ListQuery.register({
	callback : function(sValue){
		PageContext.loadList({
			TEMPNAME : sValue
		});
	}
});

wcm.Toolbar.register({
	addVisualTemplate : function(){
		addTemplate(0, 101, nChannelId);
	}
});

/*新建可视化模板*/
function addTemplate(objId, nHostType, nHostId){
	var postData = {
		objectid : objId,
		hosttype : nHostType,
		hostid : nHostId
	}
	wcm.CrashBoarder.get('visualtemplate_add').show({
		title : '新建可视化模板',
		src : WCMConstants.WCM6_PATH + 'template/visualtemplate_add_edit.jsp',
		width:'700px',
		height:'400px',
		maskable:true,
		params : postData,
		callback : function(){
			PageContext.loadList();
			this.hide();
		}
	});
}

/*获取选中的模板*/
function getSelectedListItemIds(){
	var result = [];
	var doms = document.getElementsByClassName('grid_checkbox');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			result.push(doms[i].value);
		}
	}
	return result;
}

Ext.apply(PageContext.PageNav,{
	UnitName : '个',
	TypeName : '模板'
});

window.m_cbCfg = {
	btns : [
		{
			extraCls : 'wcm-btn-close',
			text : '关闭'
		}
	]
};

//处理列表全选的功能
var bSelAll = false;
function selectAll(){
	if(!bSelAll){
		bSelAll = true;
	}else{
		bSelAll = false;
	}
	var doms = document.getElementsByName("RowId");
	if(bSelAll){
		for (var i = 0,nLen = doms.length; i < nLen; i++){
			doms[i].checked = true;
		}
	}else{
		for (var i = 0,nLen = doms.length; i < nLen; i++){
			doms[i].checked = false;
		}
	}
}