PageContext = {
	rules: null,
	currItemId: 0,
	funcs: {},
	loadPage : function(_bUpdated){
		if (PageContext.rules.length == 0){
			Element.hide('divItemDetail');
			if(_bUpdated != false) {
				window.setTimeout(function(){
				//	TRSCrashBoard.setMaskable(false);
					PageContext.editRule(0);
				//	TRSCrashBoard.setMaskable(true);
				}, 1200);
			}
		}else{
			Element.show('divItemDetail');
		}
		var json = {RULES: this.prepareRuleVals(PageContext.rules)};
		var sValue = TempEvaler.evaluateTemplater('rules_template', json, {})
		Element.update($('divRuleList'), sValue);
		
		//alert(sValue)
		window.setTimeout(function(){
			this.__registerDragElements('rules_tbody');
			var eRows = $('rules_tbody').rows;
			var nCurrItemIndex = 0;
			for(var i=0; i < eRows.length; i++){
				var element = eRows[i];
				if(_bUpdated == false && element.getAttribute('_id', 2) == PageContext.currItemId) {
					nCurrItemIndex = i;
				}
				Event.observe(element, 'click', this.selectItem.bindAsEventListener(this, element));
			}
			if(eRows.length > 0) {
				if(_bUpdated != false ) {
					PageContext.currItemId = eRows[0].getAttribute('_id', 2);
					nCurrItemIndex = 0;
				}
				//determine the selected item style
				Element.addClassName(eRows[nCurrItemIndex], 'selected_item');
				this.loadItem(nCurrItemIndex);
			}
		}.bind(this), 10);

		//预先加载条件和操作编辑页面
		/*window.setTimeout(function(){
			$('frmCondEdit').src = './workflow_cond_edit.html';
			$('frmActionEdit').src = './workflow_action_edit.html';
		}, 2000);//*/
	},
	updateCurrRuleItem : function(){
		if(PageContext.currRule == null) {
			return;
		}
		var eRows = $('rules_tbody').rows;
		var eCurrItem = $('RuleDesc_' + PageContext.currItemId);
		if(eCurrItem == null) {
			return;
		}
		//else
		var rule = PageContext.currRule;
		var sConditions = this.prepareCondVals(rule.conditions);
		sConditions = (sConditions ? sConditions : wcm.LANG['NONE_1'] || '无');
		var sActions = this.prepareActionVals(rule.actions) || '';
		var str = ' - (' + sConditions + ') ' + sActions;
		Element.update(eCurrItem, str);
		eCurrItem.setAttribute('title', rule['rulename'] + wcm.LANG['CONDITION'] || '\n条件:  ' 
			+ sConditions + wcm.LANG['NOPER'] || '\n操作: ' + sActions);
		//alert(eCurrItem.outerHTML)
		delete eCurrItem;
	},
	loadItem : function(_nIndex){
		if(_nIndex < 0 || _nIndex >= PageContext.rules.length) {
			return;
		}
		PageContext.currRule = PageContext.rules[_nIndex];
		//$('spRuleName').innerHTML = PageContext.currRule['rulename'];

		this.loadConditions(_nIndex);
		this.loadActions(_nIndex);
	},
	loadConditions : function(_nIndex){
		//关闭可能已经打开了的编辑视窗
		notifyParent2CloseFrame('condition');
		var rule = _nIndex ? PageContext.rules[_nIndex] : PageContext.currRule;
		var json = com.trs.util.JSON.toUpperCase(rule.conditions);
		var sValue = TempEvaler.evaluateTemplater('conds_template', json, {})
		Element.update($('divList_condition'), sValue);
		this.__registerDragElements('conds_tbody');
		//alert(sValue);
	},
	loadActions : function(_nIndex){
		//关闭可能已经打开了的编辑视窗
		notifyParent2CloseFrame('action');
		var rule = _nIndex ? PageContext.rules[_nIndex] : PageContext.currRule;
		//alert(rule.actions.length)
		//alert(Object.parseSource(rule.actions))
		var json = com.trs.util.JSON.toUpperCase(rule.actions);
		//alert(Object.parseSource(json))
		var sValue = TempEvaler.evaluateTemplater('actions_template', json, {})
		Element.update($('divList_action'), sValue);
		this.__registerDragElements('actions_tbody');		
	},
	setCondOperator : function(_eSpan, _nItemOrder){
		var _nItemOrder = parseInt(_nItemOrder);
		if(_nItemOrder < 0) {
			return;
		}
		var flag = _eSpan.innerHTML.trim().toUpperCase();
		_eSpan.innerHTML = (flag == 'AND') ? 'OR' : 'AND';
		_eSpan.title = (flag == 'AND') ? (wcm.LANG['FLOW_88'] || '点击切换到AND') :  (wcm.LANG['FLOW_87'] || '点击切换到OR');
		PageContext.currRule.conditions[_nItemOrder - 1]['isandoperator'] = (flag == 'AND') ? '0' : '1';
		PageContext.updateCurrRuleItem();

		delete _eSpan;
	},
	selectItem : function(event, _element){
		if(Event.element(event).tagName.toUpperCase() == 'IMG') {
			return;
		}
		var eRows = $('rules_tbody').rows;
		var nCurrItemOrder = 0;
		for(var i=0; i < eRows.length; i++){
			var element = eRows[i];
			if(element == _element) {
				if(!Element.hasClassName(element, 'selected_item')) {
					Element.addClassName(element, 'selected_item');
				}
				PageContext.currItemId = element.getAttribute('_id', 2);
				nCurrItemOrder = element.getAttribute('_order', 2);
			}else{
				if(Element.hasClassName(element, 'selected_item')) {
					Element.removeClassName(element, 'selected_item');
				}			
			}
		}
		this.loadItem(nCurrItemOrder - 1);
		delete eCurrItem;
	},
	editRule: function(_nIndex){
		var ruleInfo = _nIndex ? PageContext.rules[_nIndex - 1] : null;
		var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_rule_addedit.html';
		wcm.CrashBoarder.get('Rule_AddEdit').show({
			title : wcm.LANG['OPER_RULE'] || '新建/修改规则',
			src : sUrl,
			width: '300px',
			height: '70px',
			reloadable : true,
			params : {rule: ruleInfo, index: _nIndex, rules: PageContext.rules || []},
			maskable : true,
			callback : function(_args){
				PageContext.rules = Object.clone(_args.rules, true);
				PageContext.currItemId = _args.itemid;
				PageContext.loadPage(false);
			}
		});
	},
	addEditCond : function(_nIndex){
		var conditions = PageContext.currRule.conditions;
		var condInfo = _nIndex ? conditions[_nIndex - 1] : null;
		if(this.m_oCondDialog == null) {
			var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_cond_add_edit.html';
			wcm.CrashBoarder.get('Cond_Add').show({
				title : wcm.LANG['ADD_CONDITION'] || '新建/修改条件',
				src : sUrl,
				width: '330px',
				height: '80px',
				reloadable : true,
				params : {cond: condInfo, index: _nIndex, conds: conditions || []},
				maskable : true,
				callback : function(_args){
					PageContext.currRule.conditions = Object.clone(_args.conds, true);
					PageContext.updateCurrRuleItem();
					window.setTimeout(function(){
						PageContext.loadConditions();
						//PageContext.editCond(_args.conds.length);
					}.bind(this), 10);
				}
			});
		}
	},
	addAction : function(){
		var actions = PageContext.currRule.actions;
		if(this.m_oActionDialog == null) {
			var sUrl = WCMConstants.WCM6_PATH + 'flow/workflow_action_add.html';
			wcm.CrashBoarder.get('Action_Add').show({
				title : wcm.LANG['ADD_OPER'] || '增加操作', 
				src : sUrl,
				width: '325px',
				height: '80px',
				reloadable : true,
				params : {action: null, index: 0, actions: actions || []},
				maskable : true,
				callback : function(_args){
					PageContext.currRule.actions = Object.clone(_args.actions, true);
					PageContext.updateCurrRuleItem();
					window.setTimeout(function(){
						PageContext.loadActions();
						PageContext.editAction(_args.actions.length);
					}.bind(this), 10);
				}
			});
		}
		 		
	},	
	editAction: function(_nIndex){
		this.__highlightSelectedRow('actions_tbody', _nIndex);
		notify2ShowMe('action');
		var actions = PageContext.currRule.actions;
		var actionInfo = _nIndex ? actions[_nIndex - 1] : null;
		var params = {action: actionInfo, index: _nIndex, actions: actions || []};
		var frm = $('frmActionEdit');
		if(frm.src == 'about:blank') {
			frm.src = './workflow_action_edit.html';
			Event.observe(frm, 'load', function(){
				frm.contentWindow.document.FRAME_NAME = 'action';
				frm.contentWindow.initPage(params);
			}, false);
			PageContext.funcs['action'] = function(_args){
				PageContext.currRule.actions = Object.clone(_args.actions, true);
				PageContext.updateCurrRuleItem();
				window.setTimeout(function(){
					PageContext.loadActions();
				}.bind(this), 10);
			}
		}else{
			if(frm.contentWindow && frm.contentWindow.initPage) {
				frm.contentWindow.initPage(params);
			}
		}		
	},
	deleteRule : function(event){
		if(!confirm(wcm.LANG['FLOW_19'] || '确认要删除此规则吗？')) {
			return;
		}
		//else
		event = event || window.event;
		var eIcon = Event.element(event);
		var nIndex = eIcon.getAttribute('_order', 2);
		var arrRules = PageContext.rules;
		delete arrRules[nIndex - 1];
		PageContext.rules = arrRules.compact();
		this.loadPage(false);
	},
	deleteCond : function(event, _params){
		var sTitle = wcm.LANG['FLOW_20'] || '确认要删除此条件吗？';
		var nIndex = 0;
		if(_params != null) {
			sTitle = _params['title'];
			nIndex = _params['index'];
		}else{
			event = event || window.event;
			var eIcon = Event.element(event);
			nIndex = eIcon.getAttribute('_order', 2);		
		}
		if(!confirm(sTitle)) {
			return false;
		}
		//else
		var arConds = PageContext.currRule.conditions;
		delete arConds[nIndex - 1];
		PageContext.currRule.conditions = arConds.compact();
		PageContext.updateCurrRuleItem();
		window.setTimeout(function(){
			PageContext.loadConditions();
		}.bind(this), 10);

		return true;
	},
	deleteAction : function(event, _params){
		var sTitle = wcm.LANG['FLOW_21'] || '确认要删除此操作吗？';
		var nIndex = 0;
		if(_params != null) {
			sTitle = _params['title'];
			nIndex = _params['index'];
		}else{
			event = event || window.event;
			var eIcon = Event.element(event);
			nIndex = eIcon.getAttribute('_order', 2);		
		}
		if(!confirm(sTitle)) {
			return false;
		}
		var arActions = PageContext.currRule.actions;
		delete arActions[nIndex - 1];
		PageContext.currRule.actions = arActions.compact();
		PageContext.updateCurrRuleItem();
		window.setTimeout(function(){
			PageContext.loadActions();
		}.bind(this), 10);

		return true;
	},
	__registerDragElements : function(_sTBodyId){
		var eTBody = $(_sTBodyId);
		if(eTBody == null) {
			return;
		}
		//else
		var eRows = eTBody.rows;
		var nCurrItemIndex = 0;
		for(var i=0; i < eRows.length; i++){
			var element = eRows[i];
			var d = new TBodyDragger(element);
			d.tbodyId = _sTBodyId;
		}
		eRows = null;
		delete eTBody;
	},
	__highlightSelectedRow : function(_sTbodyId, _nSelectedIndex){
		var eRows = $(_sTbodyId).rows;
		for(var i=0; i < eRows.length; i++){
			if(_nSelectedIndex == (i + 1)) {
				Element.addClassName(eRows[i], 'selected_item');
				continue;
			}
			//else
			Element.removeClassName(eRows[i], 'selected_item');
		}		
	}
};
Object.extend(PageContext, {
	prepareRuleVals : function(_rules){
		var result = [];
		for (var i = 0; i < _rules.length; i++){
			var rule = _rules[i];
			var temp = {};
			for( var sName in rule){
				var val = rule[sName]
				if(!String.isString(val)) {
					continue;
				}
				temp[sName.toUpperCase()] = val;
			}
			temp['CONDTIONS'] = this.prepareCondVals(rule.conditions);
			temp['ACTIONS'] = this.prepareActionVals(rule.actions);
			result.push(temp);
		}
		return result;
	},
	prepareCondVals : function(_conds){
		if(_conds.length == 0) {
			return null;
		}
		var c = _conds[0];
		var result = c['paramname'] + ' ' + c['relationoperator'] + ' \'' + c['paramvalue'] + '\'';
		if(_conds.length == 1) {
			return result;
		}
		//else length is bigger than '1'
		for (var i = 1; i < _conds.length; i++){
			var c = _conds[i];
			//alert($toQueryStr(c))
			result += (c['isandoperator'].trim().toLowerCase() == '0') ? ' OR ' : ' AND ';
			result +=  c['paramname'] + ' ' + c['relationoperator'] + ' \'' + c['paramvalue'] + '\'';
		}		
		return result;
	},
	prepareActionVals : function(_actions){
		var result = [];
		if(_actions.length == 0) {
			return null;
		}
		//else
		var hSysHandlerCfgMap = $configurator.getWorkflowConfig()['action'];
		if(hSysHandlerCfgMap == null) {
			return [];
		}
		if(_actions['inline'] == null) {
			for (var i = 0; i < _actions.length; i++){
				var a = _actions[i];
				var action = {params: []};
				//定位到其中一个action-handler的系统配置
				var sysHandler = hSysHandlerCfgMap[a['handler']];
				if(sysHandler == null) {
					continue;
				}
				//得到action名称
				action['name'] = sysHandler['name'];
				
				//得到参数的名称和值
				var hParamsMap = sysHandler['params'];
				var json = com.trs.util.JSON.parseXml(
						com.trs.util.XML.loadXML('<PARAMS>' + a['params'] + '</PARAMS>'));
				var paramVal = $v(json, 'params');
				var arParamsDesc = [];
				for( var sName in paramVal){
					var paramCfg = hParamsMap[sName.toUpperCase()];
					if(paramCfg == null) {
						continue;
					}
					var param = {};
					param['desc'] = paramCfg['DESC'];
					param['name'] = sName;
					param['value'] = paramVal[sName]['NODEVALUE'];
					action.params.push(param);
					arParamsDesc.push(param['desc'] + ':' + param['value']);
				}//*/
				//alert(Object.parseSource(action))
				action['ParamsDesc'] = arParamsDesc.join(',');
				a['inline'] = action;
				result.push(action['name']);
			}
		}
		
		
		return result.join(',');
	}
});

// define for init
function init(_rules) {
	if(_rules && _rules.length > 0) {
		PageContext.rules = _rules.compact();
	}else{
		PageContext.rules = [];
	}
	//首先加载完成config，以使得以后各个需要的地方不再需要异步加载
	$configurator.loadWorkflowConfig(function(_config){
		window.setTimeout(function(){
			PageContext.loadPage();
		}, 1000);
	});
	
}
//TRSCrashBoard.setMaskable(true);

Object.extend(PageContext, {
	doAfterMove : function(_sTBodyId){
		switch(_sTBodyId){
			case 'rules_tbody':
				PageContext.rules = this.__saveOrder(_sTBodyId, PageContext.rules);
				PageContext.loadPage(false);
				break;
			case 'conds_tbody':
				PageContext.currRule.conditions = this.__saveOrder(_sTBodyId, PageContext.currRule.conditions);
				PageContext.loadConditions();
				break;
			case 'actions_tbody':
				PageContext.currRule.actions = this.__saveOrder(_sTBodyId, PageContext.currRule.actions);
				PageContext.loadActions();
				break;
			default:
				break;
		}
	},
	__saveOrder : function(_sTBodyId, _arEntities){
		var tBody = $(_sTBodyId);
		var arr = _arEntities;
		if(arr ==null || arr.length == 0)
			return;
		var newArr = [];
		var rows = tBody.rows;
		for(var i=0;i<rows.length;i++){
			var iOldOrder = rows[i].getAttribute("_order",2);
			iOldOrder = parseInt(iOldOrder);
			newArr.push(arr[iOldOrder-1]);
		}
		arr = null;
		
		return newArr;
	}
});

function getOptDesc(_sFlag){
	var flag = _sFlag.trim().toLowerCase();
	if(flag == '0' || flag == 'false') {
		return 'OR';
	}
	return 'AND';
}
function getOptTitle(_sFlag){
	var flag = _sFlag.trim().toLowerCase();
	if(flag == '0' || flag == 'false') {
		return 'AND';
	}
	return 'OR';
}
function getCondDesc(_sHandler, _sParamVal, _sUnitVal){
	var hSysHandlerCfgMap = $configurator.getWorkflowConfig()['condition'];
	var handler = null;
	if(hSysHandlerCfgMap == null || (handler = hSysHandlerCfgMap[_sHandler]) == null) {
		var nPos = _sHandler.lastIndexOf('.');
		if(nPos == -1) {
			return _sHandler;
		}
		//else
		return _sHandler.substr(nPos + 1);
	}
	//else
	//名称
	var hParams = hSysHandlerCfgMap[_sHandler]['params'];
	if(hParams == null) {
		Ext.Msg.alert(wcm.LANG['FLOW_22'] || '配置信息错误。没有指定条件的参数！');
		return '';
	}
	var aParam = {};
	for( var sName in hParams){
		aParam = hParams[sName];
	}
	//值单位
	var sUnitDesc = '';
	var arUnits = $a(aParam, 'units.unit');
	if(arUnits && arUnits.length > 0) {
		for (var i = 0; i < arUnits.length; i++){
			var unit = arUnits[i];
			if(unit['VALUE'] == _sUnitVal) {
				sUnitDesc = unit['NAME'];
				break;
			}
		}
	}

	return (handler['name'] + ' ' + _sParamVal + ' ' + sUnitDesc).toLowerCase();
}

//for mock of cb
function notify2ShowMe(_sFrameName){
	var colFrm = $('colFrm_' + _sFrameName);
	var colList = $('colList_' + _sFrameName);
	if(colFrm == null || colList == null) {
		return;
	}
	
	var dataItems = colList.getElementsByTagName('span');
	for (var i = 0; i < dataItems.length; i++){
		var dataItem = dataItems[i];
		if(!Element.hasClassName(dataItem, 'data_item')) {
			continue;
		}
		//else
		dataItem.style.width = m_nBoxWidth * 0.40
			- (Element.hasClassName(dataItem, 'data_item_narrow') ? 50 : 0);
	}
	//else
	colList.style.width = '45%';
	Element.show(colFrm);
}
function notifyParent2CloseFrame(_sFrameName){
	var colFrm = $('colFrm_' + _sFrameName);
	var colList = $('colList_' + _sFrameName);
	if(colFrm == null || colList == null) {
		return;
	}
	var dataItems = colList.getElementsByTagName('span');
	for (var i = 0; i < dataItems.length; i++){
		var dataItem = dataItems[i];
		if(!Element.hasClassName(dataItem, 'data_item')) {
			continue;
		}
		//else
		dataItem.style.width = m_nBoxWidth - 60
			- (Element.hasClassName(dataItem, 'data_item_narrow') ? 50 : 0);
	}
	//else
	Element.hide(colFrm);
	colList.style.width = '100%';
}
function notifyParent2Finish(_sFrameName, _args){
	if(PageContext.funcs[_sFrameName] == null) {
		return;
	}
	//else
	PageContext.funcs[_sFrameName](_args);
}

function doBeforeHide(){
	if(Element.visible($('colFrm_condition'))) {
		var frm = $('frmCondEdit');
		if(frm && frm.contentWindow['doBeforeHide']) {
			frm.contentWindow.doBeforeHide();
		}
	}

	if(Element.visible($('colFrm_action'))) {
		var frm = $('frmActionEdit');
		if(frm && frm.contentWindow['doBeforeHide']) {
			frm.contentWindow.doBeforeHide();
		}
	}

}

Object.extend(TBodyDragger.prototype,{
	_move : function(_eCurr,_iPosition,_eTarget){
		//_iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
		return PageContext.doAfterMove(this.tbodyId);
	}
});
function thansferRuleName(_ruleName){
	return $transHtml(_ruleName);
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