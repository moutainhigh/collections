/*---------------------------------对象操作的开始-----------------------------*/
/**
*获取操作的抽象类
*/
var AbstractOperatorsHelper = {
	/**
	*根据当前的element对象，获取当前用户可执行的操作信息
	*@param		element		操作触发时的dom元素对象
	*@return	json对象
	*/
	getOperators : function(element){
		//根据当前的操作名成，element对象，获取操作需要的参数信息
		//TODO ...
	},

	/**
	*页面销毁时做的处理
	*/
	destroy : function(){
		//TODO ...
	}
};


/**
*获取文档操作的具体实现类
*/
var DocumentOperatorsHelper = {
	/**
	*已经获取的菜单操作项
	*/
	operators : null,

	/**
	*操作的业务对象
	*/
	objectMgr : $chnlDocMgr,

	/**
	*操作operKey的前缀
	*/
	prefix : 'chnldoc_',

	/**
	*是否和操作面板的操作一样，而不是采用自己的手动操作配置
	*/
	isSameAsOperAttrPanel : false,

	/**
	*根据当前的element对象，获取当前用户可执行的操作信息
	*/
	getOperators : function(element){
		if(this.operators != null){
			return this.operators;
		}		
		var sOperatorMethod = this.isSameAsOperAttrPanel ? "getOperAttrPanelOperators" : "getCustomOperators";
		var mOperators = this[sOperatorMethod]();
		this.operators = mOperators;
		return mOperators;
	},

	/**
	*获取属性面板的配置操作
	*/
	getOperAttrPanelOperators : function(){
		var aOperators = null;
		try{
			aOperators = $oap().com.trs.wcm.AllOperators["chnldoc"];
		}catch(error){
			//just skip it.
		}
		if(aOperators == null || aOperators.length <= 0){
			return [];
		}

		var mOperators = [];
		for (var i = 0, length = aOperators.length; i < length; i++){
			var operators = aOperators[i];
			if(operators["operKey"] == "seperate"){
				mOperators.push("/");
				continue;
			}
			mOperators.push({
				oprKey : operators["operKey"],
				desc : operators["operName"],
				iconCls : this.prefix + operators["operKey"],
				cls : this.cls.bind(this, operators["operKey"], operators["rightIndex"]),
				cmd : this.cmd.bind(this, operators["operKey"])
			});
		}	
		return mOperators;
	},

	/**
	*获取自己配置的操作
	*/
	getCustomOperators : function(){
		/**
		eg.
		item:
			{
				oprKey : operators["operKey"],
				desc : "发布历史",
				iconCls : this.prefix + operators["operKey"],
				cls : this.cls.bind(this, operators["operKey"], operators["rightIndex"]),
				cmd : this.cmd.bind(this, operators["operKey"])
			}
		*/
		var mOperators = [
			{
				oprKey : 'edit',
				desc : "编辑",
				rightIndex : 32
			},
			{
				oprKey : 'docpositionset',
				desc : "调整顺序",
				rightIndex : 32
			},
			{
				oprKey : 'trash',
				desc : "删除",
				rightIndex : 33
			},
			'/',
			{
				oprKey : 'changeStatus',
				desc : "改变状态",
				rightIndex : 35
			},
			{
				oprKey : 'detailpublish',
				desc : "发布这篇文档细览",
				rightIndex : 39
			},
			{
				oprKey : 'recallpublish',
				desc : "撤销发布这篇文档",
				rightIndex : 39
			},
			/*
			{
				oprKey : operators["operKey"],
				desc : "发布历史",
			},
			*/
			'/',
			{
				oprKey : 'copy',
				desc : "复制",
				rightIndex : 34
			},
			{
				oprKey : 'quote',
				desc : "引用",
				rightIndex : 34
			},
			'/',
			{
				oprKey : 'export',
				desc : "文档导出",
				rightIndex : 34
			},
			'/',
			{
				oprKey : 'backup',
				desc : "产生版本",
				rightIndex : 32
			}
		];

		//init the default value.
		for (var i = 0, length = mOperators.length; i < length; i++){
			var operators = mOperators[i];
			if(operators["operKey"] == "/"){
				continue;
			}
			if(operators["iconCls"] == null){
				operators["iconCls"] = this.prefix + operators["oprKey"];
			}
			if(operators["cls"] == null){
				operators["cls"] = this.cls.bind(this, operators["oprKey"], operators["rightIndex"]);
			}
			if(operators["cmd"] == null){
				operators["cmd"] = this.cmd.bind(this, operators["oprKey"]);
			}
		}
		return mOperators;
	},

	/**
	*configs of operators, handle the custom.
	*/
	operatorConfigs : {
		/**
		*add some implements here....
		*/
		/*
		edit : {
			cls : function(nRightIndex, oParams){
				//TODO...
				return '';
			},
			cmd : function(sObjectIds, oParams){
				//TODO...
				this.objectMgr[sOperKey](sObjectIds, oParams);
			}
		}
		*/
		docpositionset : {
			cls : function(nRightIndex, oParams){
				try{
					var oNodeInfo = $nav().getFocusedNodeInfo();
					if(oNodeInfo["type"] == 's'){
						return 'wcm_display_none';
					}
				}catch(error){
				}
				return '';
			}
		}
	},

	/**
	*全局的默认菜单样式处理
	*/
	cls : function(sOperKey, nRightIndex){
		//获取操作的参数
		sOperKey = sOperKey.toLowerCase();
		var oParams = ContextMenuMgr.getParams(sOperKey);
		var sRightValue = oParams["RightValue"] || "0";
		
		//处理文档的特殊逻辑,不可选，则没有操作
		var nChnlDocId = ContextMenuMgr.getChnlDocId();
		if($('cb_' + nChnlDocId) && $('cb_' + nChnlDocId).disabled){
			return 'wcm_display_none'; //没有权限，则隐藏
		}

		if(!_hasRight(sRightValue, nRightIndex)){
			return 'wcm_display_none'; //没有权限，则隐藏
		}

		//进行了定制处理
		var oOperatorConfig = this.operatorConfigs[sOperKey];
		if(oOperatorConfig && oOperatorConfig.cls){
			return oOperatorConfig["cls"].call(this, nRightIndex, oParams);
		}

		//默认处理
		//TODO ...
		return '';
	},

	/**
	*全局的默认单击菜单项的处理
	*/
	cmd : function(sOperKey){
		//获取操作的参数
		sOperKey = sOperKey.toLowerCase();
		var sObjectIds = ContextMenuMgr.getObjectIds(sOperKey);
		var oParams = ContextMenuMgr.getParams(sOperKey);

		//进行了定制处理
		var oOperatorConfig = this.operatorConfigs[sOperKey];
		if(oOperatorConfig && oOperatorConfig.cmd){
			oOperatorConfig["cmd"].call(this, sObjectIds, oParams);
			return;
		}
		
		//默认处理
		this.objectMgr[sOperKey](sObjectIds, oParams);
	},

	/**
	*页面销毁时做的处理
	*/
	destroy : function(){
		//delete operators
		if(this.operators != null){
			var operators = this.operators;
			for (var i = 0; i < operators.length; i++){
				var operator = operators[i];
				delete operator["oprKey"];
				delete operator["desc"];
				delete operator["iconCls"];
				delete operator["cls"];
				delete operator["cmd"];
				delete operators[i];
			}
			delete this.operators;
		}

		for (var prop in this.operatorConfigs){
			delete this.operatorConfigs[prop];
		}
		delete this.operatorConfigs;

		delete this.objectMgr;
	}
};


/*---------------------------------对象操作参数的开始-----------------------------*/
/**
*获取参数的抽象类
*/
var AbstractParamsHelper = {
	/**
	*根据当前的操作名称，oElement对象，获取操作需要的参数信息
	*@param		sOperName	当前操作的标识
	*@param		element		操作的Dom对象
	*@return	json对象
	*/
	getParams : function(sOperName, oElement){
		//根据当前的操作名称，oElement对象，获取操作需要的参数信息
		//TODO ...
	},

	getChnlDocId : function(oElement){
		//TODO...
		return "0";
	},

	getObjectIds : function(sOperName, oElement){
		//TODO...
		return "0";
	},

	/**
	*获取当前操作的对象
	*/
	getElement : function(event){
		//TODO...
	},
	
	/**
	*是否为触发右键菜单的事件源
	*/
	isTriggerElement : function(event){
		return this.getElement(event) != null;
	},

	/**
	*页面销毁时做的处理
	*/
	destroy : function(){
		//TODO...
	}
};


/**
*获取参数的具体实现类
*/
var DocumentParamsHelper = {
	/**
	*设置哪些字段需要chnlDocId
	*/
	chnlDocMapping : {
		'trash' : true,
		'changestatus' : true,
		'detailpublish' : true,
		'recallpublish' : true,
		'copy' : true,
		'quote' : true,
		'export' : true
	},
	getParams : function(sOperName, oElement){
		//根据当前的操作名成，element对象，获取操作需要的参数信息
		if(oElement == null) return {};

		var oParams = {
			RightValue : oElement.getAttribute("right") || 0,
			channelid : oElement.getAttribute("channelid") || 0,
			currchnlid : oElement.getAttribute("currchnlid") || 0
		};
		var sObjectId = this.getObjectIds(sOperName, oElement);
		var aKey = ["docids", "documentid", "documentids", "docid"];
		for (var i = 0; i < aKey.length; i++){
			oParams[aKey[i]] = sObjectId;
		}
		oParams["channelids"] = oParams["channelid"];
		oParams["currchnlids"] = oParams["currchnlid"];
		return oParams;
	},

	getChnlDocId : function(oElement){
		if(oElement != null){
			return oElement.getAttribute("grid_rowid") || 0;
		}
	},

	getObjectIds : function(sOperName, oElement){
		if(oElement != null){
			sOperName = sOperName.toLowerCase();
			if(this.chnlDocMapping[sOperName]){
				return oElement.getAttribute("grid_rowid") || 0;
			}
			return oElement.getAttribute("docId") || 0;
		}
		return 0;
	},

	/**
	*获取当前操作的对象
	*/
	getElement : function(event){
		var tempNode = Event.element(event);
		while(tempNode){
			if(tempNode.tagName == "BODY"){
				return null;
			}
			if(Element.hasClassName(tempNode, "grid_row")){
				return tempNode;
			}
			tempNode = tempNode.parentNode;
		}
		return null;
	},

	/**
	*是否为触发右键菜单的事件源
	*/
	isTriggerElement : function(event){
		var tempNode = Event.element(event);
		while(tempNode){
			if(tempNode.tagName == "BODY"){
				return false;
			}
			if(tempNode.id && tempNode.id.indexOf("doctitle") >= 0){
				return true;
			}
			tempNode = tempNode.parentNode;
		}
		return false;
	},

	/**
	*页面销毁时做的处理
	*/
	destroy : function(){
		
	}
};


/*---------------------------------对象右键菜单的开始-----------------------------*/
/**
*右键操作处理类
*/
var ContextMenuMgr = {
	/**
	*菜单的具体实现
	*/
	simpleMenu : null, 

	/**
	*操作参数构造器
	*/
	abstractParamsHelper : AbstractParamsHelper,

	setParamsHelper : function(abstractParamsHelper){
		this.abstractParamsHelper = abstractParamsHelper;
	},
	getParamsHelper : function(){
		return this.abstractParamsHelper;
	},
	getChnlDocId : function(oElement){
		var oElement = this.getAttribute("element");
		var oParamsHelper = this.getParamsHelper();
		return oParamsHelper["getChnlDocId"](oElement);
	},
	getObjectIds : function(sOperName){
		var oElement = this.getAttribute("element");
		var oParamsHelper = this.getParamsHelper();
		return oParamsHelper["getObjectIds"](sOperName, oElement);
	},
	getParams : function(sOperName){
		var oElement = this.getAttribute("element");
		var oParamsHelper = this.getParamsHelper();
		return oParamsHelper["getParams"](sOperName, oElement);
	},
	getElement : function(event){
		if(event == null){
			return this.getAttribute("element");
		}
		var oParamsHelper = this.getParamsHelper();
		return oParamsHelper["getElement"](event);
	},
	isTriggerElement : function(event){
		var oParamsHelper = this.getParamsHelper();
		return oParamsHelper["isTriggerElement"](event);
	},

	/**
	*菜单操作构造器
	*/
	abstractOperatorsHelper : AbstractOperatorsHelper,

	setOperatorsHelper : function(abstractOperatorsHelper){
		this.abstractOperatorsHelper = abstractOperatorsHelper;
	},
	getOperatorsHelper : function(){
		return this.abstractOperatorsHelper;
	},	
	getOperators : function(){
		var oElement = this.getAttribute("element");
		var oOperatorsHelper = this.getOperatorsHelper();
		return oOperatorsHelper["getOperators"](oElement);
	},

	/**
	*属性的相关操作
	*/
	attributes : {},
	setAttribute : function(sName, oValue){
		this.attributes[sName.toUpperCase()] = oValue;
	},
	getAttribute : function(sName){
		return this.attributes[sName.toUpperCase()];
	},
	removeAttribute : function(sName){
		delete this.attributes[sName.toUpperCase()];
	},
	clearAttribute : function(){
		for(var sPropName in this.attributes){
			delete this.attributes[sPropName];
		}
	},

	/**
	*初始化
	*/
	init : function(abstractParamsHelper, abstractOperatorsHelper){
		this.setParamsHelper(abstractParamsHelper);
		this.setOperatorsHelper(abstractOperatorsHelper);
		this.bindEvents();
	},

	/**
	*相关事件的初始化
	*/
	bindEvents : function(){
		Event.observe(document, 'contextmenu', this.contextMenu.bind(this));
		Event.observe(window, 'unload', this.destroy.bind(this));
	},

	/**
	*单击鼠标右键的处理
	*/
	contextMenu : function(event){
		event = window.event || event;
		Event.stop(event);
		if(!this.isTriggerElement(event)) return;
		var element = this.getElement(event);
		if(element == null) return;

		//保存参数
		this.setAttribute("element", element);

		//获取菜单操作
		var oMenuOperators = this.getOperators();
		if(oMenuOperators.length <= 0) return;

		if(!this.simpleMenu){
			this.simpleMenu = new com.trs.menu.SimpleMenu();
		}

		//显示菜单
		this.simpleMenu.show(oMenuOperators, {
			x : Event.pointerX(event),
			y : Event.pointerY(event)
		});
	},

	/**
	*页面销毁时的处理
	*/
	destroy : function(){
		if(this.simpleMenu){
			this.simpleMenu.destroy();
			delete this.simpleMenu;
		}

		var oParamsHelper = this.getParamsHelper();
		oParamsHelper.destroy();
		delete this.abstractParamsHelper;

		var oOperatorsHelper = this.getOperatorsHelper();
		oOperatorsHelper.destroy();
		delete this.abstractOperatorsHelper;

		this.clearAttribute();
		delete this.attribute;
	}
};

Event.observe(window, 'load', ContextMenuMgr.init.bind(ContextMenuMgr, DocumentParamsHelper, DocumentOperatorsHelper), false);