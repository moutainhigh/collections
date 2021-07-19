Ext.apply(PageContext, {
	tabEnable : false,
	operEnable : false,
	filterEnable : false,
	gridDraggable : false,
	serviceId : 'wcm61_flow',
//	serviceId : '/wcm/app/flow/flow_my.jsp',
	methodName : 'queryOptionalFlows',
	/**/
	objectType : 'flow',
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		PageSize : 12
	}
});
Ext.apply(wcm.Grid, {
	rowType : function(){
		return WCMConstants.OBJ_TYPE_FLOW;
	},
	rowInfo : {
	}
	 
});
Ext.apply(PageContext, {
	getContext : function(){
		var context = this.getContext0();
		var bIsChannel = !!getParameter("ChannelId");
		Ext.apply(context, {
			OwnerType : getParameter("OwnerType"),
			OwnerId : getParameter("OwnerId")
		});
		return context;
	},
	//**
	getPageParams : function(info){
		this.params = Ext.Json.toUpperCase(location.search.parseQuery());
		Ext.applyIf(this.params, Ext.Json.toUpperCase(PageContext.initParams));
		return Ext.apply(this.params, Ext.Json.toUpperCase(info));
	},
	//*/
	/**/
	pageFilters : (function(){
		if(!PageContext.filterEnable)return null;
		var filters = new wcm.PageFilters({
			displayNum : 6,
			filterType : getParameter('FilterType') || 0
		});
		filters.register([
			//TODO type filters here
			{
				desc : wcm.LANG['FOLW_LIST'] || '工作流列表',
				type : 0
			}
		]);
		return filters;
	}())
});
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['FLOW_2'] || '个',
	TypeName : wcm.LANG['FLOW'] || '工作流'
});
Event.observe(window, 'load', function(){
	wcm.ListQuery.register({
		/*检索控件追加到的容器*/
		container : 'search', 
		/*是否追加“全部”这个检索项, default to false*/
		appendQueryAll : true,
		/*是否自动加载, default to true*/
		autoLoad : true,
		/*检索项的内容*/
		items : [
			{name : 'FlowName', desc : wcm.LANG['FLOW_NAME'] || '工作流名称', type : 'string'},
			{name : 'FlowDesc', desc : wcm.LANG['FLOW_DESC'] || '工作流描述', type : 'string'},
			{name : 'CrUser', desc : wcm.LANG['SELECT_CRUSER'] || '创建者', type : 'string'},
			{name : 'FlowId', desc : wcm.LANG['FLOW_ID'] || '工作流ID', type : 'int'}
		],
		/*执行检索按钮时执行的回调函数*/
		callback : function(params){
		   PageContext.loadList(params);

		}
	});
});
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});

Ext.apply(wcm.Grid, {
	initInChannel : function(info){
		var context = PageContext.getContext();
		var oCmsObjs = CMSObj.createEnumsFrom({
			objType : info.objType
		}, context);
		if(info.objId!=0){
			oCmsObjs.addElement(CMSObj.createFrom(info));
		}
		oCmsObjs.afterselect();
	}
});

function submitData(){
	var nLastFlowId = getParameter('CurrFlowId');
	var arrEles = document.getElementsByName('FlowId');
	var nCurrFlowId = 0;

	if($('chkNone').checked){
		nCurrFlowId = 0;	
	}else{
		for(var i=0,n=arrEles.length;i<n;i++){
			if(arrEles[i].checked){
				nCurrFlowId = arrEles[i].value;
				break;
			}
		}
	}
	if(nCurrFlowId==0 && !$('chkNone').checked) {
		Ext.Msg.$fail(wcm.LANG['FLOW_48'] || '请选择一个工作流或者点击取消选择！');
		return false;
	}
	nCurrFlowId = nCurrFlowId==0? ($('chkNone').checked ? 0 : nLastFlowId) : nCurrFlowId;
	var rArgs = {
		ChannelId: getParameter('ChannelId'),
		disabled: $('chkNone').checked,
		FlowId: nCurrFlowId,
		nOldFlowId : getParameter('CurrFlowId'),
		FlowName: $('chk_' + nCurrFlowId) != null ? $('chk_' + nCurrFlowId).getAttribute('_name', 2) : ''
	};
	var cbr = wcm.CrashBoarder.get("Dialog_Workflow_Selector");
	cbr.hide();
	cbr.notify(rArgs);
	return false;
}

Event.observe('unselect', 'click', function(event){
	if($('chkNone').checked){
		$('chkNone').checked = false;
		disableTempSelect(false);
	}else{
		$('chkNone').checked = true;
		disableTempSelect(true);
	}
});

var m_bFirstShowMask = true;
function disableTempSelect(_bFlag){
	if(_bFlag === false) {
		Element.hide('divMask');
		Element.show('divcontent');
	}else{	
		if(m_bFirstShowMask) {
			Position.clone($('divcontent'), $('divMask'));
			m_bFirstShowMask = false;
		}
		Element.hide('divcontent');
		Element.show('divMask');
	}
}

function selectFlow(_flowEl){
	this.params.selectedFlowId = _flowEl.value;		
	flowEl = null;
	delete _flowEl;
}
function disableSelected(_bFlag){
	if(_bFlag === false) {
		Element.hide('divMask');
		Element.show('selQueryType');
	}else{	
		if(m_bFirstShowMask) {
			Position.clone($('tblContent'), $('divMask'));
			m_bFirstShowMask = false;
		}
		Element.hide('selQueryType');
		Element.show('divMask');
	}
}

Event.observe(document, 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	if(dom.className == "sp_name"){
		var _id = dom.getAttribute("_id");
		dom = $('chk_'+_id);
		if($('chk_'+_id).checked){
			$('chk_'+_id).checked = false;
		}else{
			$('chk_'+_id).checked = true;
		}
		if($('chkNone').checked){
			$('chkNone').checked = false;
		}
	}
})
window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG['FLOW_CONFIRM'] || '确定',
			cmd : function(){
				return submitData();	
			}
		},
		 {
			text : wcm.LANG['FLOW_REFRESH'] || '刷新',
			cmd : function(){
				PageContext.refreshList(null, []);
				return false;		
			}
		},

		{
			text : wcm.LANG['FLOW_CANCEL'] ||'取消'
		}
	]
};
