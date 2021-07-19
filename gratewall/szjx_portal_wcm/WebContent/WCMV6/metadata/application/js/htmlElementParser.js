var HTMLElementParser = {
	itemSplit : "~",		//每一项分割符
	textValueSplit : '`',	//某一项中的key-value分割符
	aName : 'controler',	//容器的A子节点，为了快速定位容器节点而添加的锚点
	index : 0,

	/**-----------------------style config start------------------------------**/
	checkboxContainerDefaultStyle	: 'input_checkbox_container',
	radioContainerDefaultStyle		: 'input_radio_container',
	selectContainerDefaultStyle		: 'select_container',
	appendixContainerDefaultStyle	: 'appendix_container',
	classinfoContainerDefaultStyle	: 'classinfo_container',
	classinfoSelectDefaultStyle		: 'selectclassinfo',
	editorContainerDefaultStyle		: 'classinfo_container',
	openEditorDefaultStyle			: 'openeditor',
	appendixContainerDefaultStyle	: 'appendix_container',
	appendixBrowserDefaultStyle		: 'appendix_browser',
	appendixDeleteDefaultStyle		: 'appendix_delete',
	/**-----------------------style config end--------------------------------**/

	/**-----------------------classInfo id config start------------------------------**/
	classInfoTextIdSuffix		: '_Text',
	classInfoSelectIdSuffix		: '_SelectClassInfo',
	/**-----------------------classInfo id config end--------------------------------**/

	/**-----------------------editor id config start------------------------------**/
	openEditorIdSuffix		: '_openEditor',
	/**-----------------------editor id config end--------------------------------**/

	/**-----------------------appendix id config start------------------------------**/
	appendixIframeIdSuffix		: '_iframe',
	appendixBrowserBtnIdSuffix	: '_browser_btn',
	appendixDeleteBtnIdSuffix	: '_delete_btn',
	appendixTextIdSuffix		: '_text',	
	/**-----------------------appendix id config end--------------------------------**/

	/**-----------------------other fields config start------------------------------**/
	otherFieldsContainerId		: 'otherFieldsContainer',
	otherFieldsPageURL			: 'WCMV6/metadata/application/page/related_fields_list.jsp',
	/**-----------------------other fields config end--------------------------------**/

	/**
	*parse the custom elements in the current page.
	@param elementInfos. format:
		[
			{
				containerSuffix: '_Container',
				classStyle		: '',
				type    : 'checkbox', 
				name    : '',
				items   : "1`中国~2`美国~3`日本",
				selectedValue:'1,2'
			},
			{
				containerSuffix: '_Container',
				classStyle		: '',
				type    : 'radio', 
				name    : '',
				items   : "1`中国~2`美国~3`日本",
				selectedValue:'2'
			},
			{
				containerSuffix: '_Container',
				classStyle		: '',
				type    : 'select', 
				name    : '',
				items   : "1`中国~2`美国~3`日本",
				selectedValue:'2'
			}
		];
	*/
	parse : function(elementInfos){
		if(!elementInfos){
			elementInfos = this.getElementInfos();
		}
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
	},
	/**
	*like a interceptor. pre process the elementInfos.
	*@return elementInfos
	*/
	getElementInfos : function(){
		var elementInfos = [];
		var aAs = document.getElementsByName(this.aName);
		for (var i = 0; i < aAs.length; i++){
			var divNode = aAs[i].parentNode;
			elementInfos.push({
				container		: divNode,
				classStyle		: divNode.getAttribute("_classStyle"),
				type			: divNode.getAttribute("_type"),
				name			: divNode.getAttribute("_name"),
				items			: divNode.getAttribute("_items"),
				selectedValue	: divNode.getAttribute("_value")
			});			
		}
		return elementInfos;
	},
	/**
	*when dealing with checkbox, radio, select, pre process the items.
	*@return aResultItems, format:[[value, label],[value, label]...]
	*/
	getItems : function(elementInfo){
		var sItems = elementInfo.items.escapeHTML();
		var aItems = sItems.split(this.itemSplit);
		var aResultItems = [];

		for (var i = 0; i < aItems.length; i++){
			var aItem = aItems[i].split(this.textValueSplit);
			if(aItem.length > 1){
				aResultItems.push([aItem[1], aItem[0]]);
			}else{
				aResultItems.push([aItem[0], aItem[0]]);
			}
		}
		return aResultItems;
	},
	/**
	*是否为标题字段
	*/
	isTitleField : function(element){
		return false;
	},
	dealWith__onlyNode : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var aItems = this.getItems(elementInfo);
		var sClassStyle = " class='" + (elementInfo.classStyle || this[elementInfo.type.toLowerCase() + "ContainerDefaultStyle"]) + "'";
		var sHTML = "";
		for (var i = 0; i < aItems.length; i++){
			var isChecked = ("," + elementInfo.selectedValue + ",").indexOf("," + aItems[i][0] + ",") >= 0;
			if(!isChecked) continue;
			var sId = " id=" + elementInfo.name + "_" + i;
			if(this.isTitleField(oContainer)){
				sHTML += "<a"  + sClassStyle + sId + " href='' onclick='return false;'>" + aItems[i][1] + "</a>";
			}else{
				sHTML += "<span" + sClassStyle + sId + ">" + aItems[i][1] + "</span>";
			}
		}
		oContainer.innerHTML = sHTML;
	},
	getOtherTableFields : function(sTableName){
		return [[]];
	},
	getViewId : function(){
		return getPageParams()["viewId"];
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
			var nViewId = this.getViewId();
			var params = "?viewId=" + nViewId + "&tableName=" + sTableName + "&selectFields=" + aSelectDBFields + "&fieldDescs=" + encodeURIComponent(aFieldDescs.join());
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
			}.bind(this, sTableName);
		}.bind(this));
	},
	aNotNullElementsCache : [],
	setValidElements : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		if(!oContainer){
			return;
		}
		var bRequired = oContainer.getAttribute("required");
		if(bRequired == null || bRequired == '0'){
			return;
		}
		var sDesc = oContainer.getAttribute("_desc") || elementInfo.name;
		this.aNotNullElementsCache.push([sDesc, elementInfo.name]);
	},
	dealWith_checkbox : function(elementInfo){
		this.setValidElements(elementInfo);
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var aItems = this.getItems(elementInfo);
		var sName = " name=" + elementInfo.name;
		var sClassStyle = " class='" + (elementInfo.classStyle || this.checkboxContainerDefaultStyle) + "'";
		var sHTML = "";
		for (var i = 0; i < aItems.length; i++){
			var _sId = elementInfo.name + "_" + i;
			var sId = " id=" + _sId;
			var sChecked = ("," + elementInfo.selectedValue + ",").indexOf("," + aItems[i][0] + ",") >= 0 ? " checked" : "";
			var sValue = " value='" + aItems[i][0] + "'";
			sHTML += "<span" + sClassStyle + "><input type=checkbox" + sName + sId + sChecked + sValue + " />";
			sHTML += "<label for=" + _sId + ">" + aItems[i][1] + "</label></span>";
		}
		oContainer.innerHTML = sHTML;
	},
	dealWith_radio : function(elementInfo){
		this.setValidElements(elementInfo);
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var aItems = this.getItems(elementInfo);
		var sName = " name=" + elementInfo.name;
		var sClassStyle = " class='" + (elementInfo.classStyle || this.radioContainerDefaultStyle) + "'";
		var sHTML = "";
		for (var i = 0; i < aItems.length; i++){
			var _sId = elementInfo.name + "_" + i;
			var sId = " id=" + elementInfo.name + "_" + i;
			var sChecked = (aItems[i][0] == elementInfo.selectedValue ? " checked" : "");
			var sValue = " value='" + aItems[i][0] + "'";
			sHTML += "<span" + sClassStyle + "><input type=radio" + sName + sId + sChecked + sValue + " />";
			sHTML += "<label for=" + _sId + ">" + aItems[i][1] + "</label></span>";
		}
		oContainer.innerHTML = sHTML;
	},
	dealWith_select : function(elementInfo){
		this.setValidElements(elementInfo);
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var aItems = this.getItems(elementInfo);
		var sClassStyle = " class='" + (elementInfo.classStyle || this.selectContainerDefaultStyle) + "'";
		var sHTML = "<select" + sClassStyle + " name=" + elementInfo.name + " id=" + elementInfo.name + " >";
		if(oContainer.getAttribute("_black") == 'true'){
			sHTML += "<option value=''>--请选择--</option>";
		}
		for (var i = 0; i < aItems.length; i++){
			var sSelected = (aItems[i][0] == elementInfo.selectedValue ? " selected" : "");
			sHTML += "<option value=" + aItems[i][0] + sSelected + ">" + aItems[i][1] + "</option>";
		}
		sHTML += "</select>";
		oContainer.innerHTML = sHTML;
		//init the select value.
		var oSelect = $(elementInfo.name);
		oSelect.value = elementInfo.selectedValue;
		if(oSelect.selectedIndex == -1 && oSelect.options.length > 0){
			oSelect.selectedIndex = 0;
		}
	},
	dealWith_classinfo_onlyNode : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sClassStyle = " class='" + (elementInfo.classStyle || this.classinfoContainerDefaultStyle) + "'";
		//get extend style for selectClassInfo.
		var sClassInfoTextId = elementInfo.name + this.classInfoTextIdSuffix;
		var sClassDesc = oContainer.getAttribute("_classDesc") || "";
		if(elementInfo.selectedValue != 0){
			var sClassDesc = $trans2Html(sClassDesc) || "";
		}
		//var sClassDesc = $trans2Html(oContainer.getAttribute("_classDesc")) || "";
		if(sClassDesc == ""){
			var aCurrentValue = [];
		}else{
			var aClassDesc = sClassDesc.split(",");
			var aClassId = (elementInfo.selectedValue || "").split(",");
			var aCurrentValue = [];
			for (var i = 0; i < aClassId.length; i++){
				aCurrentValue.push((aClassDesc[i] || "") + "[" + (aClassId[i]||"") + "]");
			}
		}
		var sHTML = "<span" + sClassStyle + " >";
		if(this.isTitleField(oContainer)){
			sHTML += "<a id='" + sClassInfoTextId + "' href='' onclick='return false;'>" + aCurrentValue.join() + "</a>";
		}else{
			sHTML += "<span id='" + sClassInfoTextId + "'>" + aCurrentValue.join() + "</span>";
		}
		sHTML += "</span>";
		oContainer.innerHTML = sHTML;		
	},
	/**
	*container->attribute :_selectClassInfoStyle, _classId
	*/
	dealWith_classinfo : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sClassStyle = " class='" + (elementInfo.classStyle || this.classinfoContainerDefaultStyle) + "'";
		//get extend style for selectClassInfo.
		var sSelectClassInfoStyle = " class='" + (oContainer.getAttribute("_selectClassInfoStyle") || this.classinfoSelectDefaultStyle) + "'";
		var sClassInfoTextId = elementInfo.name + this.classInfoTextIdSuffix;
		var sSelectClassInfoId = elementInfo.name + this.classInfoSelectIdSuffix;
		var sClassDesc = oContainer.getAttribute("_classDesc") || "";
		if(elementInfo.selectedValue != 0){
			var sClassDesc = $trans2Html(sClassDesc) || "";
		}
		//var sClassDesc = $trans2Html(oContainer.getAttribute("_classDesc")) || "";
		if(sClassDesc == ""){
			var aCurrentValue = [];
		}else{
			var aClassDesc = sClassDesc.split(",");
			var aClassId = (elementInfo.selectedValue || "").split(",");
			var aCurrentValue = [];
			for (var i = 0; i < aClassId.length; i++){
				aCurrentValue.push((aClassDesc[i] || "") + "[" + (aClassId[i]||"") + "]");
			}
		}
		var sHTML = "<span" + sClassStyle + " >";
		sHTML += "<input _type='classInfo' type='hidden' name='" + elementInfo.name + "' id='" + elementInfo.name + "' value='" + elementInfo.selectedValue + "'/>"
		sHTML += "<div id='" + sSelectClassInfoId + "'" + sSelectClassInfoStyle + "></div>";
		sHTML += "<span id='" + sClassInfoTextId + "'>" + aCurrentValue.join() + "</span>";
		sHTML += "</span>";
		oContainer.innerHTML = sHTML;
		
		//bind the click event.
		Event.observe(sSelectClassInfoId, 'click', function(){
			if(oContainer.getAttribute("_classId") == "0"){
				$alert("没有设置字段的分类法信息！");
				return;
			}
			ClassInfoMgr.selectClassInfoTree({
				objectId : oContainer.getAttribute("_classId"),
				treeType : oContainer.getAttribute("_treeType"),
				selectedValue : $(elementInfo.name).value
			}, function(_args){
				if(_args.ids.length == 0){
//					$(sClassInfoTextId).innerHTML = "<span style='color:red;'>未选择[0]</span>";
//					$(elementInfo.name).value = 0;
					$(sClassInfoTextId).innerHTML = "<span style='color:red;'>未选择</span>";
					$(elementInfo.name).value = "";
				}else{
					//$(sClassInfoTextId).innerHTML = _args.names + "[" + _args.ids + "]";
					var aResult = [];
					for (var i = 0, length = _args.ids.length; i < length; i++){
						aResult.push(_args.names[i] + "[" + _args.ids[i] + "]");
					}
					$(sClassInfoTextId).innerHTML = $trans2Html(aResult.join(","));
					$(elementInfo.name).value = _args.ids.join();
				}
			});
		});
	},
	dealWith_editor_onlyNode : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sAttachElementSuffix = oContainer.getAttribute("_attachElementSuffix") || "_frame";
		SetValueByEditor(elementInfo.name + sAttachElementSuffix, window[elementInfo.name]);
	},
	dealWith_editor : function(elementInfo){
		this.setValidElements(elementInfo);
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sOpenEditorStyle = " class='" + (oContainer.getAttribute("_openEditorStyle") || this.openEditorDefaultStyle) + "'";
		var sOpenEditorId = elementInfo.name + this.openEditorIdSuffix;
		var sHTML = "<div id='" + sOpenEditorId + "'" + sOpenEditorStyle + "></div>";
		oContainer.innerHTML = sHTML;

		var sAttachElementSuffix = oContainer.getAttribute("_attachElementSuffix") || "_frame";
		SetValueByEditor(elementInfo.name + sAttachElementSuffix, window[elementInfo.name]);

		//bind events.
		Event.observe(sOpenEditorId, 'click', dealWithEditorEvent.bind(window, elementInfo.name, sAttachElementSuffix));
/*
		Event.observe(elementInfo.name + sAttachElementSuffix, 'click', function(){
			if($(elementInfo.name + sAttachElementSuffix).innerHTML.trim() == ""){
				dealWithEditorEvent(elementInfo.name, sAttachElementSuffix);
			}
		});
		Event.observe(elementInfo.name + sAttachElementSuffix, 'dblclick', function(){
			if($(elementInfo.name + sAttachElementSuffix).innerHTML.trim() != ""){
				dealWithEditorEvent(elementInfo.name, sAttachElementSuffix);
			}
		});
*/
		//cache the name, for initializing the data, when saving the data.
		SimpleEditorCache.push([elementInfo.name, elementInfo.name + sAttachElementSuffix]);
	},
	dealWith_appendix_onlyNode : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sClassStyle = (elementInfo.classStyle ? elementInfo.classStyle : this.appendixContainerDefaultStyle);
		if(elementInfo.selectedValue){
			 sClassStyle += " appendix_type_none";
		}
		sClassStyle = " class='" + sClassStyle + "' ";
		var sHTML = "<span" + sClassStyle + ">";
		var sId = elementInfo.name + "_" + this.index;
		sHTML += "<a id='" + sId + "' href='' onclick='return false;' class='appendix_type'></a>";
		sHTML += "</span>";
		oContainer.innerHTML = sHTML;

		//bind the click event.
		if(oContainer.getAttribute("_isTitleField") != "1"){
			Event.observe(sId, 'click', function(){
				FileDownloader.download("/wcm/file/read_file.jsp?FileName=" + elementInfo.selectedValue);
			});
		}
		var index = elementInfo.selectedValue.lastIndexOf(".");
		var suffix = elementInfo.selectedValue.substr(index+1);
		$(sId).style.backgroundImage = "url(../../../images/metadata/file_type/type_" + suffix + ".gif)";
	},
	dealWith_appendix : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		var sClassStyle = " class='" + (elementInfo.classStyle ? elementInfo.classStyle : this.appendixContainerDefaultStyle) + "'";
		var sBrowserStyle = " class='" + (oContainer.getAttribute("_browserCssStyle") || this.appendixBrowserDefaultStyle) + "'"; 
		var sDeleteStyle = " class='" + (oContainer.getAttribute("_deleteCssStyle") || this.appendixDeleteDefaultStyle) + "'"; 
		var sHTML = "<span" + sClassStyle + ">";
		var browserBtnName = elementInfo.name + this.appendixBrowserBtnIdSuffix;
		var deleteBtnName = elementInfo.name + this.appendixDeleteBtnIdSuffix;
		var appendixText = elementInfo.name + this.appendixTextIdSuffix;
		sHTML += "<input type='hidden' name='" + elementInfo.name + "' id='" + elementInfo.name + "' value='" + elementInfo.selectedValue + "'/>"
		sHTML += "<div id='" + browserBtnName + "'" + sBrowserStyle + "></div>";
		sHTML += "<div id='" + appendixText + "' style='margin-left:5px;'>" + elementInfo.selectedValue + "</div>";
		sHTML += "<div id='" + deleteBtnName + "'" + sDeleteStyle + " style='display:none;'></div>";
		sHTML += "</span>"
		oContainer.innerHTML = sHTML;
		if(elementInfo.selectedValue.trim() != ''){
			$(deleteBtnName).style.display = '';
		}

		//bind the click event.
		var fileUploader = new FileUploader(browserBtnName, function(sValue){
			var index = sValue.lastIndexOf("\\") + 1;
			$(appendixText).innerHTML = sValue.substr(index);
			$(deleteBtnName).style.display = '';
		}, function(sFileName){
			$(elementInfo.name).value = sFileName;
			$(appendixText).innerHTML = sFileName;	
			$(deleteBtnName).style.display = 'none';
		});
		Event.observe(deleteBtnName, 'click', function(){
			fileUploader.reset();
			$(elementInfo.name).value = "";
			$(appendixText).innerHTML = "";	
			$(deleteBtnName).style.display = 'none';
		});
	},
	dealWith_inputselect : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		if(!oContainer) return;
		var sAttachElement = oContainer.getAttribute("_attachElement");
		var sSelectId = sAttachElement + "_sel";

		//init the value.
		var _value = $F(sAttachElement);
		var options = $(sSelectId).options;
		for (var i = 0; i < options.length; i++){
			if(options[i].getAttribute("_value") == _value){
				$(sAttachElement).value = options[i].value;
				break;
			}
		}

		Event.observe(sSelectId, 'click', function(event){
			var oInput = $(sAttachElement);
			var oSelect = $(sSelectId);
			oSelect.value = oInput.value;
		});
		Event.observe(sSelectId, 'change', function(event){
			var oInput = $(sAttachElement);
			var oSelect = $(sSelectId);
			oInput.value = oSelect.value;
			ValidationHelper.forceValid(oInput);			
		});
	},
	dealWith_suggestion : function(elementInfo){
		var oContainer = elementInfo.container || $(elementInfo.name + elementInfo.containerSuffix);
		if(!oContainer) return;
		var sAttachElement = oContainer.getAttribute("_attachElement");
		var sSelectId = sAttachElement + "_sel";

		//init the value.
		var _value = $F(sAttachElement);
		var options = $(sSelectId).options;
		for (var i = 0; i < options.length; i++){
			if(options[i].getAttribute("_value") == _value){
				$(sAttachElement).value = options[i].value;
				break;
			}
		}

		Event.observe(sSelectId, 'click', function(event){
			var oInput = $(sAttachElement);
			var oSelect = $(sSelectId);
			oSelect.value = oInput.value;
		});
		Event.observe(sSelectId, 'change', function(event){
			var oInput = $(sAttachElement);
			var oSelect = $(sSelectId);
			oInput.value = oSelect.value;
			ValidationHelper.forceValid(oInput);			
		});
		Event.observe(sAttachElement, 'focus', function(event){
			showInputSelectSuggestion(sAttachElement);
		});

		Event.observe(sAttachElement, 'keyup', function(event){
			event = window.event || event;
			switch(event.keyCode){
				case Event.KEY_RETURN :
					if(window['lastActiveRowCache'] 
							&& window['lastActiveRowCache'][sAttachElement]){
						var activeRow = window['lastActiveRowCache'][sAttachElement];
						$(sAttachElement).value = activeRow.getAttribute("value");
						hideInputSelectSuggestion(sAttachElement);
					}
					break;
				case Event.KEY_UP :
				case Event.KEY_DOWN :
					var nextActiveRow = null;
					if(window['lastActiveRowCache'] 
							&& window['lastActiveRowCache'][sAttachElement]){
						var lastActiveRow = window['lastActiveRowCache'][sAttachElement];
						var methodName = event.keyCode == Event.KEY_UP ? getPreviousHTMLSibling : getNextHTMLSibling;
						nextActiveRow = methodName(lastActiveRow);
					}
					setActiveItem(sAttachElement, nextActiveRow);
					var container = $(sAttachElement + "_suggestion");
					nextActiveRow = window['lastActiveRowCache'][sAttachElement];
					container.scrollTop = nextActiveRow.offsetTop;
					$(sAttachElement).value = nextActiveRow.getAttribute("value");
					break;
				default:
					showInputSelectSuggestion(sAttachElement);
					break;
			}
		});
		Event.observe(sAttachElement, 'blur', function(event){
			var container = $(sAttachElement + "_suggestion");
			if(!container) return;
			event = window.event || event;
			if(!Position.within(container, 
					Event.pointerX(event), Event.pointerY(event))){
				hideInputSelectSuggestion(sAttachElement);
			}
		});

		putInputSelectCache(sAttachElement, sSelectId);
	}
};


/*----------------------------------editor start------------------------------------------*/
var SimpleEditorCache = [];
top.getValueForEditor = function(sElementName){
	var oElement = $(sElementName);
	if(oElement.tagName == "INPUT" || oElement.tagName == "TEXTAREA"){
		return oElement.value || '';
	}else{
		return _GetContent_(oElement);
	}
};

function SetValueByEditor(_sElement, _sContent){
	var oElement = $(_sElement);
	if(oElement.tagName == "INPUT" || oElement.tagName == "TEXTAREA"){
		oElement.value = _sContent;
		ValidationHelper.forceValid(oElement);
	}else{
		if(oElement.tagName == "IFRAME"){
			if(oElement.readyState != 'complete'){
				oElement.onreadystatechange = function(){
					try{
						_SetContent_(oElement, _sContent);
						if(oElement.readyState == 'complete'){
							oElement.onreadystatechange = null;
						}
					}catch(err){
						//alert(err.message);
					}
				};
			}else{
				try{
					_SetContent_(oElement, _sContent);
				}catch(error){
					//alert(error.message);
				}
			}
		}
	}
}
function _GetContent_(oElement){
	if(oElement.contentWindow.getHTML){
		return oElement.contentWindow.getHTML();	
	}else{
		return oElement.contentWindow.document.body.innerHTML;
	}
}
function _SetContent_(oElement, _sContent){
	if(oElement.contentWindow && oElement.contentWindow.document.readyState == 'complete'){
		if(oElement.contentWindow.setHTML){
			oElement.contentWindow.setHTML(_sContent);
		}else{
			oElement.contentWindow.document.body.innerHTML = _sContent;
			if(oElement.getAttribute("contenteditable") == "true"){
				oElement.contentWindow.document.body.contentEditable = true;
			}
			oElement.style.border = '1px solid silver';
		}
	}else{
		throw new Error("没有加载完成");
	}
}

var oSimpleEditorDialog = null;
function transTxt(_sTxt){
	if(!/<.*>/.test(_sTxt)){
		return _sTxt.replace(/\n/g,'<br>');
	}
	return _sTxt;
}

/**
*@param	_oParams a Object. format : {
		sToolBar	: 'Title',
		sElementCmd	: '',
		fCallBack	: null,
		nWdith		: 580,
		nHeight		: 350
	}
*
*/
function OpenSimpleEditor(_oParams){
	var oParams = {
		sToolBar	: 'MetaData',
		sElementCmd	: '',
		fCallBack	: null,
		channelId	: getPageParams()["channelId"],
		nWidth		: "800px",
		nHeight		: "450px"
	};
	Object.extend(oParams, _oParams || {});
	var sUrl = getWebURL() + "WCMV6/simpleeditor/index.html";
	//var sUrl = 'simpleeditor/index.html';
	if(oSimpleEditorDialog==null){
		oSimpleEditorDialog = TRSDialogContainer.register('Trs_Simple_Editor', 
				'编辑器', sUrl, oParams.nWidth, oParams.nHeight, true);
	}
	oSimpleEditorDialog.onFinished = oParams.fCallBack;
	TRSCrashBoard.setMaskable(true);
	TRSDialogContainer.display('Trs_Simple_Editor',{
		ChannelId : oParams['channelId'],
		toolbar : oParams.sToolBar,
		valueEval : oParams.sElementCmd
	});
}

function dealWithEditorEvent(name, sAttachElementSuffix){
	OpenSimpleEditor({
		sToolBar	: 'MetaData',
		sElementCmd	: 'top.getValueForEditor("' + name + sAttachElementSuffix + '")',
		fCallBack	: SetValueByEditor.bind(null, name + sAttachElementSuffix),
		nWidth		: "800px",
		nHeight		: "450px"
	});
}

function keepContent(sName, sContent, sDefaultValue){
	var nObjectId = getParameter("objectId");
	if(nObjectId != "" && nObjectId != 0){
		window[sName] = sContent || "";
	}else{
		window[sName] = sContent || sDefaultValue || "";
	}
	return "";
}
/*----------------------------------editor end------------------------------------------*/

/*----------------------------input select relation start-------------------------------*/
function putInputSelectCache(sFieldName, sSelectId){
	if(!window.InputSelectCache){
		window.InputSelectCache = {};
	}
	var result = [];
	var options = $(sSelectId).options;
	for (var i = 0; i < options.length; i++){
		result.push({label:options[i].text, value:options[i].value});
	}
	//排序所有item
	result = result.sort(function(var1, var2){
        return var1.label.localeCompare(var2.label);
	});
	window.InputSelectCache[sFieldName] = result;
}

function hideInputSelectSuggestion(sFieldName){
	var container = $(sFieldName + "_suggestion");
	if(container){
		container.style.display = 'none';
	}
	var iframe = $(sFieldName + "_iframe");
	if(iframe){
		iframe.style.display = 'none';
	}
}

function setActiveItem(sFieldName, srcElement){
	if(window['lastActiveRowCache'] && window['lastActiveRowCache'][sFieldName]){
		Element.removeClassName(window['lastActiveRowCache'][sFieldName], 'activeRow');
	}
	if(srcElement == null){
		srcElement = getFirstHTMLChild($(sFieldName + "_suggestion"));
	}
	if(srcElement == null) return;
	Element.addClassName(srcElement, 'activeRow');
	if(!window['lastActiveRowCache']){
		window['lastActiveRowCache'] = {};
	}
	window['lastActiveRowCache'][sFieldName] = srcElement;
}

function showInputSelectSuggestion(sFieldName){
	var options = window.InputSelectCache[sFieldName];
	if(!options || options.length <= 0) return;
	var oInput = $(sFieldName);
	var iframe = $(sFieldName + "_iframe");
	var container = $(sFieldName + "_suggestion");

	if(!container){
		//创建iframe遮布
		iframe = document.createElement("iframe");
		iframe.id = sFieldName + '_iframe';
		iframe.className = 'inputSelect_iframe';
		iframe.style.display = 'none';
		iframe.src = 'about:blank';
		oInput.parentNode.insertBefore(iframe, oInput);
		
		//创建div容器
		container = document.createElement("div");
		container.id = sFieldName + '_suggestion';
		container.className = 'inputSelect_suggestion';
		container.style.display = 'none';
		oInput.parentNode.insertBefore(container, oInput);
		
		//设置div内容
		var sHTML = "";
		for (var i = 0; i < options.length; i++){
			sHTML += "<div triggerEvent='true' value='" + options[i].value + "'>";
			sHTML += options[i].label;
			sHTML += "</div>";
		}
		container.innerHTML = sHTML;

		//邦定div容器的一些事件
		Event.observe(container, 'mouseover', function(event){
			var event = window.event || event;
			var srcElement = Event.element(event);
			if(!srcElement.getAttribute("triggerEvent")){
				return;
			}
			setActiveItem(sFieldName, srcElement)
		});
		Event.observe(container, 'click', function(event){
			var event = window.event || event;
			var srcElement = Event.element(event);
			if(srcElement.getAttribute("triggerEvent")){
				oInput.value  = srcElement.getAttribute("value");
				hideInputSelectSuggestion(sFieldName);
			}else{
				oInput.focus();
			}
		});
		Event.observe(container, 'blur', function(event){
			hideInputSelectSuggestion(sFieldName);
		});
	}

	//定位到指定的item
	var tempNode = getFirstHTMLChild(container);
	while(tempNode){
		var sValue = tempNode.getAttribute("value");
		if(sValue.startsWith(oInput.value)){
			setActiveItem(sFieldName, tempNode);
			container.scrollTop = tempNode.offsetTop;
			break;
		}
		tempNode = getNextHTMLSibling(tempNode);
	}
	container.style.display = '';
	iframe.style.display = '';
	//set position
	Position.clone(oInput, container, {setHeight:false, offsetTop:oInput.offsetHeight});
	Position.clone(container, iframe, {offsetTop:container.scrollTop});
}	

/*----------------------------input select relation end---------------------------------*/

/*----------------------------------destroy ...------------------------------------------*/
Event.observe(window, 'unload', function(){
	top.fFieldSelectedCallBack = null;

	//destory cache, InputSelectCache, SimpleEditorCache...
	if(window.SimpleEditorCache){
		for (var i = 0; i < SimpleEditorCache.length; i++){
			delete SimpleEditorCache[i];
		}
		SimpleEditorCache = null;
	}
	if(window.InputSelectCache){
		for (var fieldName in InputSelectCache){
			$destroy(InputSelectCache[fieldName]);
		}
		window.InputSelectCache = null;
	}
});

