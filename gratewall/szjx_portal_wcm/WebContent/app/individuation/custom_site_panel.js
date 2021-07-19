//定制的站点／栏目设置抽象
var AbstractCustomTabDealer = {
	lastClickRow : null, //最后单击的列表行
	customContainer : null, //列表容器，右边的列表
	customType		:null,//自定义类型, 如:站点/栏目
	newBtn			: null,//“新建”按钮
	updateBtn		: null,//”修改“按钮
	removeBtn		: null,//“删除”按钮
	selectAllBtn	: null,//“选择全部”按钮
	deselctAllBtn	: null,//”取消全部“按钮
	activeBackgroundColor: 'HIGHLIGHT',
	serviceName		: 'wcm6_individuation',
	saveMethod : 'save',
	deleteIds		: [],

    /*
    *选中(取消选中)所有的checkbox
    *@checked       : 是否选中的标记
    */
	toggleSelectAll : function(checked){
        var inputCollection = $(this.customContainer).getElementsByTagName("input");
        for (var i = 0; i < inputCollection.length; i++){
            inputCollection[i].checked = checked;
        }		
	},

	/*
	*切换自定义行的的活动/非活动行的样式
	*/
	toggleCustomRowStyle : function(domObj, isActive){
		if(domObj){
			if(isActive){
				domObj.style.backgroundColor = this.activeBackgroundColor;
				domObj.style.color = "white";
			}else{
				domObj.style.backgroundColor = "#fff";
				domObj.style.color = 'black';
			}
		}
	},

    /*
    *客户端删除选中的节点信息
    */
	clientRemove : function(){
		if(this.lastClickRow){
			var nextClickRow = Element.next(this.lastClickRow);
			$removeNode(this.lastClickRow);
			var triggerEventDom = Element.last(nextClickRow);
			if(triggerEventDom){
				triggerEventDom.fireEvent("onclick");
			}else{
				this.lastClickRow = null;
			}
		}
	},

    /*
    *获得checkbox选中的节点的id,name和站点或栏目id
    *@return ids此Dom行对象的ids, names:自定义名称；objectIdsValue:站点／栏目id
    */
	getSelectedAll : function(){
        var ids = [];
        var names = [];
        var objectIdsValue = [];
		var isShared = [];
        var rowObj = getFirstHTMLChild($(this.customContainer));
        while(rowObj){
            if(rowObj.getElementsByTagName("input")[0].checked){
                ids.push(rowObj.id);
                names.push(Element.last(rowObj).innerHTML);
                objectIdsValue.push(rowObj.getAttribute("objectIdsValue"));
				isShared.push(rowObj.getAttribute("isShared"))
            }
            rowObj = Element.next(rowObj);
        }
        return {ids:ids, names:names, objectIdsValue:objectIdsValue, isShared: isShared};		
	},

    /*
    *点击新建／修改按钮执行的操作
    *@newUpdateFlag     : 是新建还是修改的标记，可选值：new, update
    */
	newOrUpdate : function(newUpdateFlag){
        if(newUpdateFlag == 'update' && (!this.lastClickRow)){
            Ext.Msg.alert(wcm.LANG['INDIVIDUAL_5'] || "当前没有选中要修改的对象！");
            return;
        }		
        var infoObj = {
            newUpdateFlag   : newUpdateFlag,
            categoryType    : this.customType,
            ids             : this.lastClickRow ? this.lastClickRow.getAttribute("objectIdsValue") : 0,
            //categoryName    : this.lastClickRow ? Element.last(this.lastClickRow).innerHTML.trim().unescapeHTML() : ""
			categoryName    : this.lastClickRow ? Element.last(this.lastClickRow).innerHTML.trim() : "",
			isShared        : this.lastClickRow ? this.lastClickRow.getAttribute("isShared") : 0,
			isAdmin         : getParameter("isAdmin"),
			CustomizeInfo : topHandler.m_CustomizeInfo
        };
		var sFeatures = "dialogHeight:350px;dialogWidth:340px;status:no;";
		var sUrl = WCMConstants.WCM6_PATH + 'individuation/custom_site.html';
        var resultObj = showModalDialog(sUrl, infoObj, sFeatures);
        if(!resultObj) return;
		if(newUpdateFlag != 'update'){//新建
			var sValue = TempEvaler.evaluateTemplater('customRow_template', {
				INDIVIDUATION : {
					INDIVIDUATIONID	: 0,
					OBJECTIDSVALUE	: resultObj.ids,
					PARAMNAME		: "custom" + this.customType,
					PARAMVALUE		: resultObj.categoryName,//.escapeHTML()
					ISSHARED		: resultObj.isShared ? resultObj.isShared : 0
				}
			});
			new Insertion.Bottom(this.customContainer, sValue);
			if(this.lastClickRow == null){
				Element.last(Element.last(this.customContainer)).fireEvent("onclick");
			}
		}else{//修改
			this.lastClickRow.setAttribute("objectIdsValue", resultObj.ids);
			this.lastClickRow.setAttribute("isShared", resultObj.isShared);
			Element.last(this.lastClickRow).innerHTML = resultObj.categoryName;//.escapeHTML();
		}
	},

	setSaveParams : function(aCombine, oHelper){
		var node = getFirstHTMLChild(this.customContainer);
		while(node){
			aCombine.push(oHelper.Combine(this.serviceName, this.saveMethod, {
				objectId		: node.getAttribute("objectid") || 0,
				paramName		: 'custom' + this.customType,
				paramValue		: Element.last(node).innerHTML,
				objectIdsValue	: node.getAttribute("objectIdsValue"),
				isChecked		: node.getElementsByTagName("input")[0].checked ? "1" : "0",
				isShared        : node.getAttribute("isShared") ? node.getAttribute("isShared") : '0'
			}));
			node = Element.next(node);
		}
		if(this.deleteIds.length > 0){
			aCombine.push(oHelper.Combine(this.serviceName, 'delete', {objectIds : this.deleteIds}));
		}
	},

	remove : function(){
        if(!this.lastClickRow){
            Ext.Msg.alert(wcm.LANG['INDIVIDUAL_5'] || "当前没有选中要删除的对象！");
            return;
        }
		this.deleteIds.push(this.lastClickRow.getAttribute("objectId"));
		var nextClickRow = Element.next(this.lastClickRow);
		Ext.removeNode(this.lastClickRow);
		var triggerEventDom = Element.last(nextClickRow) || Element.last(getFirstHTMLChild(this.customContainer));
		if(triggerEventDom){
			triggerEventDom.fireEvent("onclick");
			//triggerEventDom.click();
		}else{
			this.lastClickRow = null;
			this.updateBtn.disabled = true;
			this.removeBtn.disabled = true;
		}
	},

	initAbstractCustomTabEvent : function(){
		Event.observe(this.customContainer, 'click', function(event){		
			event = event || window.event;
			var srcElement = Event.element(event);
			if(!srcElement.getAttribute("triggerEvent")){//单击的不是感兴趣的对象
				return;
			}
			this.toggleCustomRowStyle(this.lastClickRow, false);
			this.lastClickRow = srcElement.parentNode;
			this.toggleCustomRowStyle(this.lastClickRow, true);
			this.updateBtn.disabled = false;
			this.removeBtn.disabled = false;
		}.bind(this));

		Event.observe(this.newBtn, 'click', this.newOrUpdate.bind(this, 'new'));
		Event.observe(this.updateBtn, 'click', this.newOrUpdate.bind(this, 'update'));
		Event.observe(this.removeBtn, 'click', this.remove.bind(this));
		Event.observe(this.selectAllBtn, 'click', this.toggleSelectAll.bind(this, true));
		Event.observe(this.deselectAllBtn, 'click', this.toggleSelectAll.bind(this, false));
	},

	initAbstractCustomTabValue : function(){
		this.updateBtn.disabled = true;
		this.removeBtn.disabled = true;
		this.refresh();
	},

	refresh : function(successCallBack){
		BasicDataHelper.call(this.serviceName, 'query', {
			ParamName : "custom" + this.customType,
			userId	  : topHandler.userId
		}, true, function(transport, json){
			var sValue = TempEvaler.evaluateTemplater('customRow_template', json["INDIVIDUATIONS"]);
			Element.update(this.customContainer, sValue);
/*
			var triggerEventDom = Element.last(getFirstHTMLChild(this.customContainer));
			if(triggerEventDom) triggerEventDom.fireEvent("onclick");
*/
			(successCallBack || AbstractCustomTabDealer.emptyFunction).call(this, transport, json);
		}.bind(this));		
	},
	emptyFunction: function() {}
};

//定制的站点
var CustomSiteTabDealer = {};
Object.extend(CustomSiteTabDealer, AbstractCustomTabDealer);
Object.extend(CustomSiteTabDealer, {
	initCustomSiteTabEvent : function(){
		this.initAbstractCustomTabEvent();
	},

	initCustomSiteTabValue : function(){
		//this.refresh();
		this.initAbstractCustomTabValue();
	},

	initCustomSiteTab : function(){//初始化自定义站点页
		this.customContainer = $('customSiteContainer');
		this.customType = 'Site';
		this.newBtn = $('customSiteNew');
		this.updateBtn = $('customSiteUpdate');
		this.removeBtn = $('customSiteRemove');
		this.selectAllBtn = $('customSiteSelectAll');
		this.deselectAllBtn = $('customSiteDeselectAll');

		this.initCustomSiteTabEvent();
		this.initCustomSiteTabValue();
	}
});

//点击个性化设置页面的"自定义站点"节点时，触发的动作
function customSiteTabLoad(){
	if(!CustomSiteTabDealer.isLoaded){
		CustomSiteTabDealer.initCustomSiteTab();
		CustomSiteTabDealer.isLoaded = true;
	}
}