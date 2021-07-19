/*-------------中间内容请求的服务相关信息--------------*/
Ext.apply(PageContext, {
	serviceId : 'wcm61_templateArg',
	methodName : 'jQuery',
	/**/
	initParams : {
		"FieldsToHTML" : "",
		"SelectFields" : "",
		"hostType" : !!getParameter("SiteId") ? '103' : '101',
		"hostid" : !!getParameter("SiteId") ? getParameter("SiteId") : getParameter("ChannelId")
	}
});

/*-------------指定列表上的过滤器--------------*/
PageContext.setFilters([
	{
		desc : wcm.LANG['TEMPLATEARG_28'] || '模板变量列表',
		type : 0
	}
]);
//路径信息
Ext.apply(PageContext.literator, {
	enable : true,
	width : 350
});
Ext.apply(PageContext.literator.params, {
	tracesite : true
});

/*-------------指定各种情况下右侧的操作面板--------------*/
/*
*设置第一块操作面板的类型；
*第二块和第三块面板的类型通过如下方式获取：
*如果列表页面没有选择记录，则为导航树节点对应的类型(如：website,channel);
*如果列表当前选中一条记录，则类型为当前列表的rowType(如：chnldoc);
*如果列表当前选中多条记录，则类型为当前列表的rowType+s(如：chnldocs);
*操作面板中操作的具体定义在文件wcm/app/js/data/opers/xxx.js中；
*/
PageContext.setRelateType(
	 !!getParameter("ChannelId") ? 'templateArgInChannel' : 'templateArgInSite'
);
/*-------------指定分页的相关信息--------------*/
Ext.apply(PageContext.PageNav,{
	UnitName : wcm.LANG['TEMPLATEARG_29'] || '个',
	TypeName : wcm.LANG['TEMPLATEARG'] || '模板变量'
});

/*-------------列表上操作入口的函数--------------*/
PageContext.addGridFunctions({
	'edit' : function(event){
		wcm.domain.TemplateArgMgr['listEdit'](event);
	}
});

/*-------------指定页面底部的标签--------------*/
/*
*各种底部标签的具体定义在文件wcm/app/js/data/tab/xxx.js中；
*/
PageContext.setTabs({
	displayNum : 6,
	activeTabType : 'templateArg'
});

/*-------------页面上下文环境信息--------------*/
Ext.apply(PageContext, {
	objectType : WCMConstants.OBJ_TYPE_TEMPLATEARG,
	_buildParams : function(wcmEvent, eventType, valueDom){
		if(!'save' == eventType)return;
		return {
			TemplateId : wcmEvent.getObj().getPropertyAsString('templateId'),
			HostId : PageContext.hostId,
			HostType : PageContext.intHostType,
			ParameterName : wcmEvent.getObj().getPropertyAsString('argName'),
			PrexName : wcmEvent.getObj().getPropertyAsString('PrexName')

		};
	}
});
var m_oArgs = {};
function prepareArgs(arr){
	//初始化
	m_oArgs = {};
	for(var i=0,n=arr.length;i<n;i++){
		var argInfo = arr[i];
		if(argInfo==null)continue;
		var argName = argInfo.name;
		var enumvalue = argInfo.enumvalue;
		var display = argInfo.display;
		m_oArgs[argName] = m_oArgs[argName] || [];
		var isExist = false;
		for(index =0; index < m_oArgs[argName].length; index ++){
			if(m_oArgs[argName][index][0] != enumvalue)continue;
			isExist = true;
			break;
		}
		if(isExist==false){
			m_oArgs[argName].push([enumvalue, display]);
		}
	}
}
function $transHtml(_sContent) {
	if (_sContent == null)
		return '';

	var nLen = _sContent.length;
	if (nLen == 0)
		return '';

	var result = '';
	for (var i = 0; i < nLen; i++) {
		var cTemp = _sContent.charAt(i);
		switch (cTemp) {
		case '<': // 转化：< --> &lt;
			result += '&lt;';
			break;
		case '>': // 转化：> --> &gt;
			result += '&gt;';
			break;
		case '"': // 转化：" --> &quot;
			result += '&quot;';
			break;
		default:
			result += cTemp;
		}// case
	}// end for
	return result;
}
function popupEditor(_sInputId, _sParamName,event){
	Event.stop(window.event||event);
	var param = {
			'templateId' :  _sInputId,
			'parameterName' : _sParamName
		}
	var templateId = _sInputId;
	var sTitle = '修改参数值';
	var sUrl = WCMConstants.WCM6_PATH + 'template/template_arg_texteditor.html';
	wcm.CrashBoarder.get('Text_Editor').show({
		title : sTitle,
		src : sUrl,
		width: '300px',
		height: '200px',
		reloadable : true,
		params : {text: $(_sInputId).getAttribute("value")},
		maskable : true,
		callback : function(_args){
			if(_args['text']!=null)
				$(_sInputId).value = _args['text'];
			var cbr = wcm.CrashBoarder.get("Text_Editor");
			$(_sInputId).focus();
			$(_sInputId).select();
			cbr.close();
		}.bind(param)
	});
}

Ext.apply(wcm.Grid, {
	gridBodySelectStart : function(event){
		//Event.stop(event.browserEvent || event);
		return false;
	}
});