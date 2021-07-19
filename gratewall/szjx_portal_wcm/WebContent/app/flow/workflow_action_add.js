PageContext = {
	loadPage : function(){
		//类型及其参数
		this.loadHandlers();
	},
	loadHandlers : function(){
		var hSysHandlers = PageContext.config;
		//alert(arHandlers);
		var selHandlers = $('selHandlers');
		this.__clearSelect(selHandlers);
		for( var sClzName in hSysHandlers){
			var eOption = document.createElement("OPTION");
			selHandlers.options.add(eOption);
			eOption.value = sClzName;
			eOption.innerText = hSysHandlers[sClzName]['name'];			
		}
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
	getActionProp : function(){
		var result = {};
		Object.extend(result, {
			handler: $('selHandlers').value
		});

		return result;
	}	
}

//for cb
function init(_actionInfo){
	PageContext.index = _actionInfo.index;
	PageContext.actions = _actionInfo.actions;

	var config = $configurator.getWorkflowConfig()['action'];
	if(config == null) {
		Ext.Msg.alert(wcm.LANG['FLOW_43'] || '没有找到关于[action]的配置定义！', function(){});
		return;
	}
	//else
	PageContext.config = config;
	PageContext.loadPage();
}
function submitData(){
		//准备参数
		var actions = PageContext.actions;
		var nCurrItemId = 0;
		var actionProp = PageContext.getActionProp();
		Object.extend(actionProp, {
			flowactionid: Math.random() + ''
		});
		var aNewaction = new WorkflowAction(actionProp);
		nCurrItemId = aNewaction['flowactionid'];
		actions.push(aNewaction);
		//回调
		var cbr = wcm.CrashBoarder.get("Action_Add");
		cbr.notify({'actions': actions, 'itemid': nCurrItemId});
		cbr.hide();
}

function closeframe(){
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
	}
}