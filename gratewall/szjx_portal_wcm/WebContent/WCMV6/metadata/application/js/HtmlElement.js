Ext.ns("wcm");
/**
 * @class wcm.ComponentMgr
*/
wcm.ComponentMgr =  function(){
	var all = {};
	var types = {};
	return {
		register : function(c){
			all[c.getId().toUpperCase()] = c;
		},
		unregister : function(c){
			delete all[c.getId().toUpperCase()];
		},
		get : function(id){
			if(!id) return null;
			return all[(id.id || id).toUpperCase()];
		},
		registerType : function(type, cls){
			types[type.toUpperCase()] = cls;
			cls.type = type;
		},
		create : function(config, defaultType){
			return new types[(config["type"] || defaultType).toUpperCase()](config);
		}
	};
}();

wcm.XElement = function(config){
	Ext.apply(this, config);
	wcm.ComponentMgr.register(this);
	this.initComponent(config);
};

Ext.apply(wcm.XElement, {
	AUTO_ID : 0
});

Ext.extend(wcm.XElement, wcm.util.Observable, {
	initComponent : function(){
	},
	getId : function() {
		return this.id || (this.id = "wcm-el-" + (++wcm.XElement.AUTO_ID));
	},
	render : Ext.emptyFn
});

wcm.MultiTable = Ext.extend(wcm.XElement, {
	initComponent : function(config){
		wcm.MultiTable.superclass.initComponent.apply(this, arguments);
		this.addEvents('beforeshow', 'afterhide');
	},
	render : function(){
		wcm.MultiTable.superclass.parse.apply(this, arguments);
	}
});

wcm.XElements = Ext.extend(wcm.XElement, {
	initComponent : function(config){
		wcm.XElements.superclass.initComponent.apply(this, arguments);
		this.addEvents('beforerender', 'afterrender');
	}
});

wcm.PageElements = new wcm.XElements({id : 'G_Page_Elements'});


/**
*compatible tips : override HTMLElementParser for the list and other pages of metadata plugin of wcmv6.
*/
Ext.apply(HTMLElementParser, {
	parse : function(elementInfos){
		if(!elementInfos){
			elementInfos = this.getElementInfos();
		}
		if(wcm.PageElements.fireEvent('beforerender') === false) return;
		for (var i = 0; i < elementInfos.length; i++){
			var elementInfo = elementInfos[i];
			if(!elementInfo) continue;
			if(!elementInfo.type){
				alert(elementInfo.name + "没有指定相应的type属性");
				continue;
			}
			var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
			var onlyNode = oContainer.getAttribute("_onlyNode");
			if(onlyNode == true || onlyNode == "true"){
				if(this["dealWith_" + (elementInfo.type.toLowerCase()) + "_onlyNode"]){
					this["dealWith_" + (elementInfo.type.toLowerCase()) + "_onlyNode"](elementInfo);
					this.index++;
				}else{
					this["dealWith__onlyNode"](elementInfo);
				}
			}else{
				this["dealWith_" + (elementInfo.type.toLowerCase())](elementInfo);
			}
			delete elementInfo["container"];
		}
		wcm.PageElements.fireEvent('afterrender');
	},
	dealWith_fromothertable : function(elementInfo){
		var oOtherFieldsContainer = top.$(this.otherFieldsContainerId);
		if(oOtherFieldsContainer == null){
			//脱离了WCM的的主页面，所以需要自己创建
			var oIframe = document.createElement("iframe");
			oIframe.id = "otherFieldsContainer";
			oIframe.src = getWebURL() + "WCMV6/blank.html";
			oIframe.frameborder = 0;
			oIframe.style.position = 'absolute';
			oIframe.style.zIndex = 999;
			oIframe.style.width = "400px";
			oIframe.style.height = "200px";
			oIframe.style.display = 'none';
			oIframe.style.filter = 'alpha(opacity=95)';
			document.body.appendChild(oIframe);
		}
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sTableName = oContainer.getAttribute("_referTo");
		if(!wcm.ComponentMgr.get(sTableName)){
			new wcm.MultiTable({id : sTableName});
		}
		Event.observe(oContainer, 'click', function(){
			var oOtherFieldsContainer = top.$(this.otherFieldsContainerId);
			var offsets = Position.getPageInTop(oContainer);
			oOtherFieldsContainer.style.left = offsets[0] + oContainer.offsetWidth;
			oOtherFieldsContainer.style.top = offsets[1] + oContainer.offsetHeight;
			var sTableName = oContainer.getAttribute("_referTo");
			var aSelectFields = [];
			var aFieldDescs = [];
			var aSelectDBFields = [];
			var aFieldInfos = this.getOtherTableFields(sTableName);
			for (var i = 0; i < aFieldInfos.length; i++){
				if(aFieldInfos[i].length > 0){
					aSelectDBFields.push(aFieldInfos[i][0]);
					aFieldDescs.push(aFieldInfos[i][1]);
					aSelectFields.push(aFieldInfos[i][2]);
				}
			}
			var multiTable= wcm.ComponentMgr.get(sTableName);
			var oParams = {};
			if(multiTable.fireEvent('beforeshow', oParams) === false) return;
			var nViewId = this.getViewId();
			var params = "?viewId=" + nViewId + "&tableName=" + sTableName + "&selectFields=" + aSelectDBFields + "&fieldDescs=" + encodeURIComponent(aFieldDescs.join());
			params += "&" + $toQueryStr(oParams);
//			if(oOtherFieldsContainer.src != (this.otherFieldsPageURL + params)){
				oOtherFieldsContainer.src = getWebURL() + this.otherFieldsPageURL + params;
//			}
			setTimeout(function(){
				Element.show(oOtherFieldsContainer);
				oOtherFieldsContainer.focus();
			}, 100);
			top.fFieldSelectedCallBack = function(sTableName, params){
				aFieldInfos.id = params.id;
				for (var i = 0; i < aSelectFields.length; i++){
					$(aSelectDBFields[i] + "_" + aSelectFields[i]).innerHTML = params[i] || "";
				}
				var multiTable= wcm.ComponentMgr.get(sTableName);
				multiTable.fireEvent('afterhide', params);
			}.bind(this, sTableName);
		}.bind(this));
	}
});
