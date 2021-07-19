var BuildToViewer = {
	tableIds : null,
	tableContainer : 'TableContainer',
	fieldContainer : 'FieldContainer',
	viewContainer : 'ViewContainer',

	tableRowIdPrefix : 'table_',
	fieldContainerIdPrefix : 'fieldContainer_',
	viewRowIdPrefix : 'view_',

	lastTableRowId : null,
	lastFieldContainerId : null,
	lastViewRowId : null,

	servicesName : 'wcm6_MetaDataDef',
	findTableMethodName : 'findDBTableInfosByIds',
	queryFieldMethodName : 'queryDBFieldInfos',
	queryViewFieldMethodName : 'queryViewFieldInfos',
	importViewFromXML : 'importViewFromXML',

	rowCss : 'row',
	normalRowCss : 'row_normal',
	activeRowCss : 'row_active',

	fieldSelectAllBtn : 'fieldSelectAllBtn',
	fieldDeselectAllBtn : 'fieldDeselectAllBtn',
	viewUpBtn : 'viewUpBtn',
	viewDownBtn : 'viewDownBtn',
	viewDeleteBtn : 'viewDeleteBtn',
	okBtn : 'okBtn',
	closeBtn : 'closeBtn',

	fieldCBIdPrefix : 'fcb_',
	
	viewHead : 'viewHead',

	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();
	},
	initData : function(){
		var tableIds = this.tableIds;
		if(!tableIds){
			alert('传入参数不正确[tableIds]');
			return;
		}
		this.loadTable(tableIds);
	},
	loadTable :  function(tableIds){
		this.getHelper().Call(this.servicesName, this.findTableMethodName, {
			pagesize:-1,
			objectIds : tableIds,
			fieldstohtml : 'ANOTHERNAME'
		}, true, this.tableLoaded.bind(this));
	},
	tableLoaded : function(transport, json){
        var sValue = TempEvaler.evaluateTemplater('tableTemplate', json);
        Element.update(this.tableContainer, sValue);	
		var firstRow = getFirstHTMLChild($(this.tableContainer));
		firstRow.getElementsByTagName("span")[1].click();
		firstRow.getElementsByTagName("input")[0].checked = true;
	},
	loadField : function(tableInfoId){
		if(this.lastFieldContainerId){
			Element.hide(this.lastFieldContainerId);
		}
		this.lastFieldContainerId = this.fieldContainerIdPrefix + tableInfoId;
		if($(this.lastFieldContainerId)){
			Element.show(this.lastFieldContainerId);
		}else{
			this.getHelper().Call(this.servicesName, this.queryFieldMethodName, {
				pagesize:-1,
				TableInfoId : tableInfoId,
				fieldstohtml : 'ANOTHERNAME'
			}, true, this.fieldLoaded.bind(this));
		}
	},
	fieldLoaded : function(transport, json){
		var oContainer = document.createElement("div");
		oContainer.id = this.lastFieldContainerId;
		$(this.fieldContainer).appendChild(oContainer);
        var sValue = TempEvaler.evaluateTemplater('fieldTemplate', json);
        Element.update(oContainer, sValue);	
	},
	toggleStyle : function(element, newStyle, oldStyle){
		if(!$(element)) return;
		Element.removeClassName(element, oldStyle);
		Element.addClassName(element, newStyle);
	},
	clickTableContainer : function(event){
		event = window.event || event;
		var srcElement = Event.element(event);		
		if(!srcElement.getAttribute('triggerEvent')) return;
		if(this.lastTableRowId){
			this.toggleStyle(this.lastTableRowId, this.normalRowCss, this.activeRowCss);
		}
		this.lastTableRowId = this.tableRowIdPrefix + srcElement.getAttribute("_id");
		this.toggleStyle(this.lastTableRowId, this.activeRowCss, this.normalRowCss);
		this.loadField(srcElement.getAttribute("_id"));
	},
	clickFieldContainer : function(event){
		event = window.event || event;
		var srcElement = Event.element(event);		
		if(!srcElement.getAttribute('triggerEvent')) return;
		if(srcElement.tagName.toUpperCase() == 'INPUT'){
			var fieldId = srcElement.getAttribute('_id');
			if(srcElement.checked){//checked
				this.attachRowToViewContainer(fieldId, srcElement);
			}else{//unchecked				
				this.detachRowToViewContainer(fieldId);
			}
		}
	},
	attachRowToViewContainer : function(fieldId, srcElement){
		if($(this.viewRowIdPrefix + fieldId)) return;
		if(!this.viewId){//新建
			var rowObj = this.getRowObject(srcElement);
			var json = {
				METAVIEWFIELDS : [
					{
						METAVIEWFIELD : {
							/*
							DBFIELD		: fieldId,
							DBFIELDNAME	: this.getRowObject(srcElement).innerText,
							TABLENAME	: $(this.lastTableRowId).innerText
							*/

							DBFIELD		: fieldId,
							//ANOTHERNAME	: rowObj.innerText,
							ANOTHERNAME	: $transHtml(rowObj.getElementsByTagName("label")[0].innerHTML),
							DBFIELDNAME	: rowObj.getAttribute("title"),
							TABLENAME	: $(this.lastTableRowId).getAttribute("title")
						}
					}
				]
			}
		}else{//修改
			var rowObj = this.getRowObject(srcElement);
			var json = {
				METAVIEWFIELDS : [
					{
						METAVIEWFIELD : {
							DBFIELD		: fieldId,
//							ANOTHERNAME	: rowObj.innerText,
							ANOTHERNAME	: $transHtml(rowObj.getElementsByTagName("label")[0].innerHTML),
							DBFIELDNAME	: rowObj.getAttribute("title"),
							TABLENAME	: $(this.lastTableRowId).getAttribute("title")
						}
					}
				]
			}
		}
        var sValue = TempEvaler.evaluateTemplater('viewTemplate', json);

		//处理了一个字段都不存在的情况下，Insertion.Bottom产生的不合理的地方
		var aInput = $(this.viewContainer).getElementsByTagName("input");
		if(aInput.length > 0){
			new Insertion.Bottom(this.viewContainer, sValue);
		}else{
			Element.update(this.viewContainer, sValue);
		}
	},
	detachRowToViewContainer : function(fieldId){
		var eRow = $(this.viewRowIdPrefix + fieldId);
		if(eRow){
			$(this.viewContainer).removeChild(eRow);
		}
	},
	getRowObject : function(element){
		element = $(element);
		while(element && element.tagName.toUpperCase() != 'BODY'){
			if(Element.hasClassName(element, this.normalRowCss)){
				return element;
			}
			element = element.parentNode;
		}
		return null;
	},
	mdViewContainer : function(event){
		event = window.event || event;
		srcElement = Event.element(event);		
		if(!srcElement.getAttribute('triggerEvent')) return;
		if(this.lastViewRowId){
			this.toggleStyle(this.lastViewRowId, this.normalRowCss, this.activeRowCss);
		}
		//set defaultChecked
		var eRow = this.getRowObject(srcElement);
		var inputArray = eRow.getElementsByTagName("input");
		for (var i = 0; i < inputArray.length; i++){
			inputArray[i].defaultChecked = inputArray[i].checked;
		}
		this.lastViewRowId = eRow.id;
		this.toggleStyle(this.lastViewRowId, this.activeRowCss, this.normalRowCss);
		Event.observe(this.viewContainer, 'mousemove', this.mmViewContainer);
		Event.observe(this.viewContainer, 'mouseup', this.muViewContainer);
	},
	mmViewContainer : function(event){
		var viewContainer = $(BuildToViewer.viewContainer);
		viewContainer.setCapture();
		var oLastViewRow = $(BuildToViewer.lastViewRowId);
		var currY = Event.pointerY(event) + viewContainer.scrollTop;
		var nodes = viewContainer.childNodes;
		for (var i = 0; i < nodes.length; i++){
			if(nodes[i].nodeType != 1) continue;
			if(nodes[i] == oLastViewRow) continue;
			var offset = Position.cumulativeOffset(nodes[i]);
			var offsetHalfHeight = nodes[i].offsetHeight / 2;
			
			if(((offset[1] - offsetHalfHeight) <= currY) && (currY < (offset[1] + offsetHalfHeight))){
				viewContainer.insertBefore(oLastViewRow, nodes[i]);			
				break;
			}
			if((i+1 == nodes.length) && currY > offset[1]+offsetHalfHeight){//移到最后
				viewContainer.insertBefore(oLastViewRow);			
				break;					
			}
		}
	},
	muViewContainer : function(event){
		$(BuildToViewer.viewContainer).releaseCapture();
		Event.stopObserving(BuildToViewer.viewContainer, 'mousemove', BuildToViewer.mmViewContainer);
		Event.stopObserving(BuildToViewer.viewContainer, 'mouseup', BuildToViewer.muViewContainer);
	},
	moveViewRowPosition : function(sDirection, event){
		if(!this.lastViewRowId) return;
		var oLastViewRow = $(this.lastViewRowId);
		if(!oLastViewRow) return;
		//set defaultChecked
		var inputArray = oLastViewRow.getElementsByTagName("input");
		for (var i = 0; i < inputArray.length; i++){
			inputArray[i].defaultChecked = inputArray[i].checked;
		}
		if(sDirection == 'up'){
			var oPrevious = getPreviousHTMLSibling(oLastViewRow);
			if(!oPrevious) return;
			$(this.viewContainer).insertBefore(oLastViewRow, oPrevious);
		}else{
			var oNext = getNextHTMLSibling(oLastViewRow);
			if(!oNext) return;
			$(this.viewContainer).insertBefore(oLastViewRow, getNextHTMLSibling(oNext));
		}
	},
	deleteViewRow : function(event){
		if(!this.lastViewRowId) return;
		var oLastViewRow = $(this.lastViewRowId);
		if(!oLastViewRow) return;
		var tempViewRow = getNextHTMLSibling(oLastViewRow);
		var sFieldId = oLastViewRow.id.split("_")[1];
		if($(this.fieldCBIdPrefix + sFieldId)){
			$(this.fieldCBIdPrefix + sFieldId).checked = false;
		}
		Element.remove(oLastViewRow);
		if(tempViewRow){
			this.toggleStyle(tempViewRow, this.activeRowCss, this.normalRowCss);
		}
		this.lastViewRowId = tempViewRow;
	},
	fieldSelectAll : function(){
		var ckArray = $(this.lastFieldContainerId).getElementsByTagName("input");
		for (var i = 0; i < ckArray.length; i++){
			ckArray[i].checked = true;
			this.attachRowToViewContainer(ckArray[i].getAttribute("_id"), ckArray[i]);
		}
	},
	fieldDeselectAll : function(){
		var ckArray = $(this.lastFieldContainerId).getElementsByTagName("input");
		for (var i = 0; i < ckArray.length; i++){
			ckArray[i].checked = false;
			this.detachRowToViewContainer(ckArray[i].getAttribute("_id"));
		}		
		this.lastViewRowId = null;
	},
	saveData : function(){
		//1.TODO valid the fields.	
		var viewName = $F('viewName').trim();
		if(viewName.length <= 0){
			$alert('视图名称不能为空。');
			$('viewName').focus();
			return false;
		}
		if(viewName.byteLength() > 60){
			$alert('视图名称大于最大长度60。');
			$('viewName').focus();
			return false;
		}
		$beginSimplePB("正在创建视图");
		this.getHelper().Call(this.servicesName, this.importViewFromXML, {
			ViewId : this.viewId || 0,
			DataXML : this.getPostData()
		}, true, this.dataSaved.bind(this));
	},
	getPostData : function(){
		var mainTableId = "";
		var otherTableIds = [];
		var tableRArray = $(this.tableContainer).getElementsByTagName("input");
		for (var i = 0; i < tableRArray.length; i++){
			if(tableRArray[i].checked){
				mainTableId = tableRArray[i].value;
			}else{
				otherTableIds.push(tableRArray[i].value);
			}
		}
		var viewId = this.viewId || '0';
		var viewName = $transHtml($F('viewName'));
		var nHiddenAppendix = $('hiddenAppendix').checked ? 1 : 0;
		var sXML = "<view-fields id='" + viewId + "' cname='" + viewName + "' main-table-id='" + mainTableId + "' other-table-ids='" + otherTableIds.join() + "' hiddenAppendix='" + nHiddenAppendix + "'>";
		var tempRow = getFirstHTMLChild($(this.viewContainer));
		while(tempRow){
			var fieldId = tempRow.getAttribute("_id") || 0;
			sXML += '<view-field id="' + fieldId + '">';
			sXML += "<DBField>" + tempRow.id.split("_")[1] + "</DBField>";
			var inputArray = tempRow.getElementsByTagName("input");
			for (var i = 0; i < inputArray.length; i++){
				sXML += "<" + inputArray[i].name + ">" + (inputArray[i].checked ? 1 : 0) + "</" + inputArray[i].name + ">";
			}
			tempRow = getNextHTMLSibling(tempRow);
			sXML += "</view-field>";
		}
		sXML += "</view-fields>";
		return sXML;
	},
	dataSaved : function(){
		$endSimplePB();
		this.onAfterSave();
		this.closeWin();
	},
	onAfterSave : function(){
		//do something here. eg. refresh main list.		
	},
	closeWin : function(){
		try{
			if (window.parent){
				window.parent.notifyParent2CloseMe(document.FRAME_NAME);
			}
		}catch(error){
			//防止选择树iframe没有加载完成就单击确定按钮而导致脚本错误
		}
	},
	clickViewHead : function(event){
		event = window.event || event;
		srcElement = Event.element(event);		
		var key = srcElement.getAttribute("key");		
		if(!key) return;
		var inputArrays = document.getElementsByName(key);
		var needSelectAll = false;
		for (var i = 0; i < inputArrays.length; i++){
			if(!inputArrays[i].checked){
				needSelectAll = true;
				break;
			}
		}
		for (var i = 0; i < inputArrays.length; i++){
			if(!inputArrays[i].disabled){
				inputArrays[i].checked = needSelectAll;
			}
		}
	},

	initEvent : function(){
		Event.observe(this.tableContainer, 'click', this.clickTableContainer.bind(this));
		Event.observe(this.fieldContainer, 'click', this.clickFieldContainer.bind(this));
		Event.observe(this.viewContainer, 'mousedown', this.mdViewContainer.bind(this));
		Event.observe(this.viewUpBtn, 'click', this.moveViewRowPosition.bind(this, 'up'));
		Event.observe(this.viewDownBtn, 'click', this.moveViewRowPosition.bind(this, 'down'));
		Event.observe(this.viewDeleteBtn, 'click', this.deleteViewRow.bind(this));
		Event.observe(this.fieldSelectAllBtn, 'click', this.fieldSelectAll.bind(this));
		Event.observe(this.fieldDeselectAllBtn, 'click', this.fieldDeselectAll.bind(this));
		Event.observe(this.okBtn, 'click', this.saveData.bind(this));
		Event.observe(this.closeBtn, 'click', this.closeWin.bind(this));
		Event.observe(this.viewHead, 'click', this.clickViewHead.bind(this));
	}
};


/**---------------------------------the entrance for js start----------------------------*/
/*
//单页面调试的入口
Event.observe(window, 'load', function(){
	BuildToViewer.initEvent();
	if(getParameter("tableIds") != 0){
		BuildToViewer.tableIds = getParameter("tableIds");
		BuildToViewer.initData();
	}else if(getParameter("viewId") != 0){
		BuildToViewer.viewId = getParameter("viewId");
		BuildToViewer.loadData();
	}
});
/**/

//嵌入在CrashBoard中的入口
function init(params){
	BuildToViewer.initEvent();
	if(params.tableIds){
		BuildToViewer.tableIds = "" + params.tableIds;
		BuildToViewer.initData();
	}else if(params.viewId){
		BuildToViewer.viewId = "" + params.viewId;
		BuildToViewer.loadData();
	}
}
/**---------------------------------the entrance for js end----------------------------*/

/**---------------------------------some funciton called by tag parser start--------------------------------*/
function getViewDesc(tableAnotherName, tableName, fieldAnotherName, fieldName, anotherName){
	var desc = tableAnotherName || tableName;
	desc += "." + (fieldAnotherName || fieldName);
	if(anotherName){
		desc = anotherName + "(" + desc + ")";
	}
	return desc;
}

function isChecked(sCurrentValue){
	return sCurrentValue == "1" ? "checked" : "";
}
function isDisabled(otitleFeild){
//	if(titleFeild=="1"){
//		oOutline.checked = true;
//	}
	return otitleFeild == "1" ? "disabled" : "";

}
function setTitleEvent(oTitleRadio){
	//清除掉概览显示的disabled状态
	var aInOutline = document.getElementsByName("InOutline");
	for(var index = 0; index < aInOutline.length; index++){
		aInOutline[index].disabled = false;
	}
	//处理当前选中的概览显示的checkbox状态
	var sId = oTitleRadio.getAttribute("_id");
	var inOutlinechk = $("InOutline_" + sId);
	if(oTitleRadio.checked){
		inOutlinechk.checked = true;
		inOutlinechk.disabled = true;		
	}else{
		inOutlinechk.disabled = false;
	}
}
/**---------------------------------some funciton called by tag parser end----------------------------------*/

/**---------------------------------extend BuildToViewer for View Fields start----------------------------*/
Object.extend(BuildToViewer, {
	viewId						: null,
	queryViewFieldsMethodName	: 'queryViewFieldInfos',
	queryTableMethodName		: 'queryDBTableInfos',
	findViewMethodName			: 'findViewById',

	//over ride the fieldLoaded. no override the method again.
	fieldLoaded : function(transport, json){
		this.fieldLoaded0(transport, json);
		if(!this.viewId) return;
		Element.eachChild(this.lastFieldContainerId, function(element){
			var oInputs = element.getElementsByTagName("input");
			if(oInputs.length <= 0) return;
			var sFieldId = oInputs[0].getAttribute("_id");
			if($(this.viewRowIdPrefix + sFieldId)){
				oInputs[0].checked = true;
			}
		}.bind(this));
	},
	onAfterSave : function(){
		if(!this.viewId) return;
		try{
			var oMainWin = top.$main();
			if(oMainWin.ViewInfos && oMainWin.ViewInfos.RefreshList){
				oMainWin.ViewInfos.RefreshList();
			}else if(oMainWin.PageContext && oMainWin.PageContext.RefreshList){
				oMainWin.PageContext.RefreshList();
			}
		}catch(error){
			//just skip it.
		}
	},
	loadData : function(){
		var oHelper = this.getHelper();
        var aCombine = [];
        aCombine.push(oHelper.Combine(this.servicesName,this.queryViewFieldsMethodName,{
			viewId:this.viewId,
			pagesize:-1,
			FieldsToHTML:'DBFIELDNAME,FIELDNAME,TABLENAME,ANOTHERNAME'
		})); 
        aCombine.push(oHelper.Combine(this.servicesName,this.queryTableMethodName,{
			viewId:this.viewId,
			pagesize:-1,
			FieldsToHTML:'TABLENAME,ANOTHERNAME'
		})); 
        aCombine.push(oHelper.Combine(this.servicesName,this.findViewMethodName,{
			objectId:this.viewId,
//			FieldsToHTML:'VIEWDESC,VIEWNAME,MAINTABLENAME'
			FieldsToHTML:'VIEWNAME,MAINTABLENAME,HIDDENAPPENDIX'
		})); 
        oHelper.MultiCall(aCombine, this.dataLoaded.bind(this));        
	},
	dataLoaded : function(transport, json){
		//1.update the viewfields.
        var sValue = TempEvaler.evaluateTemplater('viewTemplate', json["MULTIRESULT"]);
        Element.update(this.viewContainer, sValue);	

		//2.update the tables.
        sValue = TempEvaler.evaluateTemplater('tableTemplate', json["MULTIRESULT"]);
        Element.update(this.tableContainer, sValue);	

		//3.update the view desc
		var sViewDesc = com.trs.util.JSON.value(json, "MULTIRESULT.METAVIEW.VIEWDESC");
		$('viewName').value = sViewDesc;
		
		//3.2 update the hiddenAppendix.
		var nHiddenAppendix = com.trs.util.JSON.value(json, "MULTIRESULT.METAVIEW.HIDDENAPPENDIX");
		$('hiddenAppendix').checked = nHiddenAppendix == 1 ? true : false;

		//4.init the primary table checked.
		var sMainTableId = com.trs.util.JSON.value(json, "MULTIRESULT.METAVIEW.MAINTABLEID");
		var oIsPrimarys = document.getElementsByName("isPrimary");
		for (var i = 0; i < oIsPrimarys.length; i++){
			oIsPrimarys[i].disabled = true;
			if(oIsPrimarys[i].value == sMainTableId){
				oIsPrimarys[i].checked = true;
				
				//5.init the fields info.
				var oRow = this.getRowObject(oIsPrimarys[i]);
				oRow.getElementsByTagName("span")[1].click();
				break;
			}
		}
	}
});
/**---------------------------------extend BuildToViewer for View Fields end----------------------------*/
