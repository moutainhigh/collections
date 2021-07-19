PageContext = {
	params: {},
	loadPage : function(){
		//类型和名称、运算
		this.loadHandlers();
	},
	loadHandlers : function(){
		var hSysHandlers = PageContext.config;
		var selHandlers = $('selHandlers');
		for( var sClzName in hSysHandlers){
			var eOption = document.createElement("OPTION");
			selHandlers.options.add(eOption);
			eOption.value = sClzName;
			eOption.innerText = hSysHandlers[sClzName]['name'];	
		}
		PageContext.currHandlers = hSysHandlers;
		this.loadDefaultHandler(true);
	},
	loadDefaultHandler : function(_bIsInit){
		//获取当前的handler
		var arHandlers = PageContext.currHandlers;
		if(arHandlers == null || arHandlers.length == 0) {
			return;
		}
		if(PageContext.cond!=null){
			 $('selHandlers').value = PageContext.cond.handler;
			 $('selHandlers').disabled = true;
			 $('txtParamValue').value = PageContext.cond['paramvalue'];
			 $('txtParamValue').select();
			 $('txtParamValue').focus();
		}
		var currHandlerIndex = $('selHandlers').selectedIndex ;
		PageContext.currHandler = PageContext.currHandlers[$('selHandlers').value];
		var currHandler = arHandlers[currHandlerIndex];
		//名称
		var aParam = $v(currHandler, 'param');
		PageContext.params['paramname'] = aParam['NAME'];
		//运算
		var arOperators = $a(aParam, 'operators.operator');
		if(arOperators && arOperators.length > 0) {
			PageContext.params['relationoperator'] = arOperators[0]['VALUE'];
		}
		//this.loadDefaultHandlerParamValue();
		this.loadCurrCondParam();
		//TODO 参数的默认值
		//PageContext.params['paramvalue'] = '';
		Event.observe('selHandlers', 'change', function(){
			PageContext.currHandler = PageContext.currHandlers[$('selHandlers').value];
			var result = PageContext.loadCurrCondParam();
			if(!result)return;
			$('txtParamValue').value = '';
			$('txtParamValue').focus();
		});

	},
	loadDefaultHandlerParamValue : function(){
		//$('txtParamValue').value = PageContext.cond['paramvalue'];
		//$('txtParamValue').focus();
	},
	loadCurrCondParam : function(){
		//准备参数
		//var hSysHandlers = PageContext.config;
		//PageContext.currHandlers = hSysHandlers;
		//PageContext.currHandler = hSysHandlers[PageContext.cond['handler']];
		//创建一个新的condition
		if(PageContext.currHandler == null) {
			return;
		}
		//名称
		var hParams = PageContext.currHandler['params'];
		if(hParams == null) {
			Ext.Msg.alert(wcm.LANG['FLOW_22'] || '配置信息错误。没有指定条件的参数！', function(){});
			return false;
		}
		var aParam = {};
		for( var sName in hParams){
			aParam = hParams[sName];
		}
		if(aParam['NAME'] == undefined){
			$('datafield').style.display = 'none';
			Ext.Msg.alert(wcm.LANG['FLOW_22'] || '配置信息错误。没有指定条件的参数！', function(){});
			return false;
		}
		$('datafield').style.display = '';
		$('spParamName').innerHTML = aParam['DESC'];
		$('hdParamName').value = aParam['NAME'];
		//值校验 与 值单位
		var sValidate = "";
		sValidate += "type:'" + (aParam["TYPE"] || "string") +"',";
		sValidate += aParam["REQUIRED"] == "false" ? "" : "required:''";
		sValidate += ",desc:" + (wcm.LANG['FLOW_41'] || '参数值');
		if(aParam["TYPE"]=='int'){
			sValidate +=',max:10000,min:1';
		}
		else{
			sValidate +=',max_len:50"';
		}
		$('txtParamValue').setAttribute('validation', sValidate);
		//运算
		var arOperators = $a(aParam, 'operators.operator');
		//单位
		var arUnits = $a(aParam, 'units.unit');
		$('txtParamValue').style.width = (arUnits.length == 0) ? '100px' : '50px';
		if(PageContext.cond==null){
			var condProp = PageContext.getNomalCondProp();
			PageContext.__appendSelectionField('operators', arOperators, condProp['relationoperator']);
			PageContext.__appendSelectionField('units', arUnits, condProp['unitvalue']);
		}else{
			PageContext.__appendSelectionField('operators', arOperators, PageContext.cond['relationoperator']);
			PageContext.__appendSelectionField('units', arUnits, PageContext.cond['unitvalue']);
		}
		return true;
	},
	__appendSelectionField : function(_sFieldFlag, _params, _sFieldValue){
		var arParams = _params;
		var eField = $('field_' + _sFieldFlag);
		var eTextField = $('text_' + _sFieldFlag);
		var eSelField = $('sel_' + _sFieldFlag);

		if(arParams == null || arParams.length == 0) {
			Element.hide(eField);
			return;
		}
		if(arParams.length == 1) { //只有一个选项时，显示为平面文字
			var opt = arParams[0];
			eTextField.setAttribute('_value', opt['VALUE']);
			eTextField.innerHTML = opt['NAME'];
			Element.hide(eSelField);
			Element.show(eTextField);
		}else{//多个选项时，显示为一个select控件
			this.__clearSelect(eSelField);
			for (var i = 0; i < arParams.length; i++){
				var opt = arParams[i];
				PageContext.__appendSelectOption(eSelField, opt['VALUE'], opt['NAME']);
			}
			//设置默认值
			var sCurrOperator = (_sFieldValue || '') + '';
			if(sCurrOperator == null || sCurrOperator.trim() == '') {
				sCurrOperator = arParams[0]['VALUE'];
			} 
			eSelField.value = sCurrOperator;
			Element.hide(eTextField);
			Element.show(eSelField);
		}
		Element.show(eField);
	},
	__clearSelect : function(_sSelId){
		var eSelector = $(_sSelId);
		var eOptions = eSelector.options;
		if(eOptions.length > 0) {
			for (var i = eOptions.length; i >0;){
				eOptions.remove(--i);
			}
		}

		delete eSelector;
	},
	__appendSelectOption : function(_select, _sValue, _sText){
		var eSelect = $(_select);
		if(eSelect == null) {
			return;
		}
		//else
		var eOption = document.createElement("OPTION");
		eSelect.add(eOption);
		eOption.value = _sValue;
		eOption.innerText = _sText;		

		delete eSelect;
	},
	__getSelectionFieldValue : function(_sFieldFlag){
		var eTextField = $('text_' + _sFieldFlag);
		var eSelField = $('sel_' + _sFieldFlag);
		return Element.visible(eSelField) ? eSelField.value : eTextField.getAttribute('_value', 2);	
	},
	getNomalCondProp : function(){
		var result = {};
		Object.extend(result, {
			handler: $('selHandlers').value, //类型
			isandoperator: '1', //默认为 'AND'
			paramname: PageContext.params['relationoperator'], //参数名
			relationoperator: PageContext.params['paramname'], //运算符
			paramvalue: PageContext.params['paramvalue'] //参数值
		});

		return result;
	},
	getCondProp : function(){
		var result = {};
		Object.extend(result, {
			handler: $('selHandlers').value,
			paramname: $('hdParamName').value,
			relationoperator: PageContext.__getSelectionFieldValue('operators'),
			unitvalue: PageContext.__getSelectionFieldValue('units'),
			paramvalue: $('txtParamValue').value
		});

		return result;
	}

}

//for cb
function init(_condInfo){
	PageContext.isNew = (_condInfo.cond == null);

	PageContext.cond = _condInfo.cond;
	PageContext.index = _condInfo.index;
	PageContext.conds = _condInfo.conds;

	var config = $configurator.getWorkflowConfig()['condition'];
	if(config == null) {
		Ext.Msg.alert(wcm.LANG['FLOW_40'] || '没有找到关于[condition]的配置定义！');
		return;
	}
	PageContext.config = config;
	PageContext.loadPage();	
}
function submitData(){
	if (window.parent){
		if(!validate($('txtParamValue'), event)) return false;
		var conds = PageContext.conds;
		var nCurrItemId = 0;
		var condProp = PageContext.getCondProp();

		//创建一个新的condition
		Object.extend(condProp, {
			flowconditionid: Math.random() + ''
		});
		if(PageContext.isNew){
			var aNewCond = new WorkflowCondition(condProp);
			nCurrItemId = aNewCond['flowconditionid'];
			aNewCond['isandoperator'] = '1';
			conds.push(aNewCond);
		}else{
			nCurrItemId = PageContext.cond['flowconditionid'];
			Object.extend(PageContext.cond, condProp);
			PageContext.cond['edited'] = true;
			conds[PageContext.index - 1] = PageContext.cond; 
		}

		//更新到父窗口的规则
		var cbr = wcm.CrashBoarder.get("Cond_Add");
		cbr.notify({'conds': conds, 'itemid': nCurrItemId});
		cbr.hide();
	}
}

function validate(_eInput, event){
	if(_eInput.tagName != 'INPUT' && _eInput.tagName != 'TEXTAREA') {
		return false;
	}
	var result = ValidationHelper.validAndDisplay(_eInput);
	delete _eInput;
	return result;

}