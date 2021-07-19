var Configurator = {
	loadWorkflowConfig : function(_fAfterLoad){
		var func = _fAfterLoad || Prototype.emptyFunction;
		if(window.top.WorkFlowConfig != null) {
			func(this.getWorkflowConfig());
			return;
		}
		//else
		BasicDataHelper.call('wcm6_process', 'getConfigContent', null, false, function(_trans, _json){
			try{
				//alert(Object.parseSource(_json));
				window.top.WorkFlowConfig = _json;
				func(this.getWorkflowConfig());
			}catch(err){
				//TODO
				Ext.Msg.alert(getErrorStack(err));
			}	
		}.bind(this));		
	},
	getWorkflowConfig : function(){
		if(window.top.WorkFlowConfig == null) {
			return null;
		}
		//alert(window.top.m_WorkflowConfig)
		if(window.top.m_WorkflowConfig == null) {
			window.top.m_WorkflowConfig = {
				action: this.__loadHandlerConfg('workflow-config.action-handlers.handler'),
				condition: this.__loadHandlerConfg('workflow-config.condition-handlers.handler'),
				touser: this.__loadHandlerConfg('workflow-config.tousers-creators.tousers-creator', true),
				initvalue_creators: this.__loadHandlerConfg('workflow-config.initvalue-creators.initvalue-creator')
			};
		}
		return window.top.m_WorkflowConfig;
	},
	__loadHandlerConfg : function(_sXPath, _bIndexByName){
		var config = window.top.WorkFlowConfig;
		var arHandlers = $a(config, _sXPath);
		if(!arHandlers) {
			return;
		}
		var hHandlerMap = {};
		for (var i = 0; i < arHandlers.length; i++){
			var handler = arHandlers[i];
			//1.load properties, 基本信息为: 
			// {name: sName, params: [object], class: sClz, nodevalue: sNodeVal}
			var entity = {};
			for( var sName in handler){
				var val = handler[sName];
				if(val == null) {
					continue;
				}
				//else
				entity[sName.toLowerCase()] = val;
			}
			entity['class'] = entity['class-name'];
			delete entity['class-name'];

			var sClzName = handler['CLASS-NAME'];
			var sName = handler['NAME'];
			//2.load params
			var rawParams = $a(handler, 'param');
			var hParamsMap = {};
			if(rawParams != null && rawParams.length > 0) {
				for (var j = 0; j < rawParams.length; j++){//以name为键值，加载所有的param
					var aParam = rawParams[j];
					hParamsMap[aParam['NAME'].toUpperCase()] = aParam;
				}				
			}
			entity['params'] = hParamsMap;

			//3._bIndexByName参数决定是由Name做索引，还是Class做索引（默认为Class做索引）
			if(_bIndexByName == true) {
				hHandlerMap[sName]		= entity;
				//alert(Object.parseSource(entity))
			}else{
				hHandlerMap[sClzName]	= entity;	
			}

		}
		return hHandlerMap;
	}
};

var $configurator = Configurator;
