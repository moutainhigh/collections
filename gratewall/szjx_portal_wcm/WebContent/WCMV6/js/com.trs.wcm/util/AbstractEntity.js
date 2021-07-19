$package('com.trs.wcm.util');

Abstract.ElementContainer = Class.create();
Abstract.ElementContainer.prototype = {
	initialize: function(_oObjRelDef, _oClzsDef){
		/*{
			sDatonId :	//如:websites/channels
			sSrcId : 	//如:_SiteId/_chnlId
			checkboxId :  //如:chk_
			editPanelId : //如:'txt_dispname_',
			rights : //如:'_rights',
			innerTabularId : //如:'inner_tabular_',
			tabularId :  //如:'tabular_'
		}*/
		Object.extend(this, _oObjRelDef || {});
		/*{
			outer_box			:  // 外围元素
			disactive_element	:  // 非活动元素
			active_element		:  // 活动元素
			checkbox			:  // 复选框
		}*/
		this.oClzsDef  = _oClzsDef;
		this.selectedIds = [];
		this._registerBaseEvents();
	},
	getHelper : function(){
		if(!this.helper){
			this.helper = new com.trs.web2frame.BasicDataHelper();
		}
		return this.helper;
	},
	/**
	*获得要执行的目标信息
	*/
	getExecuteTarget : function(event, element){
		event = window.event || event;
		var eventType = event.type.toLowerCase();
		var tempNode = element ? element : Event.element(event);
		while(tempNode && tempNode.id != this.sDatonId 
				&& tempNode.tagName.toLowerCase() != 'body'){
			if(tempNode.getAttribute(eventType + "_fun") != null){
				return {fun:tempNode.getAttribute(eventType + "_fun"), element:tempNode};
			}
			tempNode = tempNode.parentNode;
		}
		return null;
	},
	/**
	*事件分发处理器
	*/
	dispatchEvent : function(event, element){
		var target = this.getExecuteTarget(event, element);
		if(!target || !target["fun"]) return;
		try{
			if(this[target["fun"]](target["element"], event)){
				this.dispatchEvent(event, target["element"].parentNode);
			}
		}catch(error){}
	},
	/**
	*事件中心，初始化一些事件相关函数
	*/
	initEventCenter : function(){
		this.clickElement = function(element, event){
			this.toggleCurrent(event, element.getAttribute(this["sSrcId"]));		
		};
		this.mousemoveContainer = function(element, event){
			if(this.lastSrcId && (operator = $('tb_operators_' + this.lastSrcId))){
				Element.hide(operator);
				delete this.lastSrcId;
			}			
		};
		this.mousemoveElement = function(element, event){
			if(this.lastSrcId && (operator = $('tb_operators_' + this.lastSrcId))){
				Element.hide(operator);
				delete this.lastSrcId;
			}
			if(element){
				this.lastSrcId = element.getAttribute(this["sSrcId"]);
				Element.show('tb_operators_' + this.lastSrcId);
			}
		};
		this.clickOperator = function(element, event){
			var sOptType = element.getAttribute(this["optType"]);
			var sSrcId = element.getAttribute(this["sSrcId"]);
			this["mgr"][sOptType](sSrcId);
			var chk = $('chk_' + sSrcId);
			if(chk == null || !chk.checked){
				this.toggleCurrent(event, sSrcId);
			}
		};
		this.clickDesc = function(element, event){
			if(element.selected && (inputElement = $(element.id+"_"))){
				element.style.display = 'none';
				inputElement.style.display = '';
				inputElement.select();
				inputElement.focus();
				inputElement.onmousedown = function(event){
					Event.stop(window.event || event);
				};
				inputElement.onblur = this._blurInput;
				inputElement.onkeydown = this._keydownInput;
				return false;
			}
			return true;//继续冒泡处理
		};
		var caller = this;
		this._blurInput = function(event){
			if(caller._validInput(this)){
				this.onmousedown = null;
				this.onblur = null;
				this.onkeydown = null;
				var oRelateElement = $(this.id.substring(0, this.id.length-1));
				this.style.display = 'none';
				oRelateElement.style.display = '';
				var formatedValue = this.value.escapeHTML();
				if(oRelateElement.innerHTML != formatedValue){
					oRelateElement.innerHTML = formatedValue;
					var postData = {};
					postData[this.name] = this.value;
					caller.updateDesc(postData);
				}
			}
		};
		this._keydownInput = function(event){
			event = window.event || event;
			if(event.keyCode == Event.KEY_RETURN){
				caller._blurInput.call(this, event);
			}
		};
		this._validInput = function(inputElement){
			var validInfo = ValidatorHelper.valid(inputElement);
			var warning = validInfo.getWarning();
			if(!validInfo.isValid()){
				$alert(validInfo.getWarning(), function(){
					$dialog().hide();
					this.value = $(this.id.substring(0, this.id.length-1)).innerHTML.unescapeHTML();
					setTimeout(function(){this.focus();this.select();}.bind(this),10);
				}.bind(inputElement));
				return false;
			}
			return true;
		};
	},
	getSelectedIds : function(){
		this.keepEffect();
		return this.selectedIds;
	},
	keepEffect : function(){//将this.selectedIds中无效的id去掉
		var tempIdArray = [];
		for (var i = 0; i < this.selectedIds.length; i++){
			if($(this.innerTabularId + this.selectedIds[i])){
				tempIdArray.push(this.selectedIds[i]);
			}
		}
		this.selectedIds = tempIdArray;
	},
	getRights : function(){
		var rights = [];
		for (var i = 0; i < this.selectedIds.length; i++){
			var tempNode = $(this.innerTabularId + this.selectedIds[i]);
			if(tempNode == null) continue;
			rights.push(tempNode.getAttribute(this.rights));
		}
		return rights;
	},
	reset : function(){
		this.selectedIds = [];
	},
	toggleCurrent : function(event, nSrcId){
		event = window.event || event;
		var innerTabularDom = $(this.innerTabularId + nSrcId);
		if(innerTabularDom == null) return;
		var checkBoxDom = $(this.checkboxId + nSrcId);
		if(event.ctrlKey || Event.element(event) == checkBoxDom){//多选
			if(Element.hasClassName(innerTabularDom, this.oClzsDef.active_element)){//已经选中，本次取消选中
				checkBoxDom.checked = false;
				checkBoxDom.defaultChecked = false;
				UIEditPanel.unselect(this.editPanelId + nSrcId);
				Element.removeClassName(innerTabularDom, this.oClzsDef.active_element);
				this.selectedIds = this.selectedIds.without(nSrcId);
			}else{//原先未选中,本次要选中
				checkBoxDom.checked = true;
				checkBoxDom.defaultChecked = true;
				UIEditPanel.select(this.editPanelId + nSrcId);
				Element.addClassName(innerTabularDom, this.oClzsDef.active_element);
				this.selectedIds.push(nSrcId);
			}
		}else{//单选			
			var needSelected = !checkBoxDom.checked;
			for (var i = 0; i < this.selectedIds.length; i++){//取消所有的选中状态
				var tempInnerTabularDom = $(this.innerTabularId + this.selectedIds[i]);
				if(tempInnerTabularDom == null) continue;
				var tempCheckBoxDom = $(this.checkboxId + this.selectedIds[i]);
				tempCheckBoxDom.checked = false;
				tempCheckBoxDom.defaultChecked = false;
				UIEditPanel.unselect(this.editPanelId + this.selectedIds[i]);
				Element.removeClassName(tempInnerTabularDom, this.oClzsDef.active_element);
			}
			this.reset();
			if(needSelected){
				//选中当前单击节点
				checkBoxDom.checked = true;
				checkBoxDom.defaultChecked = true;
				UIEditPanel.select(this.editPanelId + nSrcId);
				Element.addClassName(innerTabularDom, this.oClzsDef.active_element);
				this.selectedIds.push(nSrcId);
			}
		}
		setTimeout(this.inspect.bind(this, this.selectedIds), 100);
	},
	toggleAll : function(){
		this.reset();
		var needSelected = false;
		var nodesParent = $(this.sDatonId);
		var tempNode = getFirstHTMLChild(nodesParent);
		if(!Element.hasClassName(tempNode, this.oClzsDef.outer_box)){
			setTimeout(this.inspect.bind(this, []), 100);
			return;
		}
		while(tempNode){
			var nSrcId = tempNode.getAttribute(this.sSrcId);
			var checkBoxDom = $(this.checkboxId + nSrcId);
			if(!checkBoxDom.checked){
				needSelected = true;
				break;
			}
			tempNode = getNextHTMLSibling(tempNode);
		}

		if(needSelected){//选中全部
			tempNode = getFirstHTMLChild(nodesParent);
			while(tempNode){
				var nSrcId = tempNode.getAttribute(this.sSrcId);	
				var checkBoxDom = $(this.checkboxId + nSrcId);
				this.selectedIds.push(nSrcId);
				if(!checkBoxDom.checked){
					var innerTabularDom = $(this.innerTabularId + nSrcId);				
					Element.addClassName(innerTabularDom, this.oClzsDef.active_element);
					checkBoxDom.checked = true;
					checkBoxDom.defaultChecked = true;
					UIEditPanel.select(this.editPanelId + nSrcId);
				}
				tempNode = getNextHTMLSibling(tempNode);
			}
		}else{//全部取消
			tempNode = getFirstHTMLChild(nodesParent);
			while(tempNode){
				var nSrcId = tempNode.getAttribute(this.sSrcId);		
				var checkBoxDom = $(this.checkboxId + nSrcId);
				if(checkBoxDom.checked){
					var innerTabularDom = $(this.innerTabularId + nSrcId);	
					Element.removeClassName(innerTabularDom, this.oClzsDef.active_element);		
					checkBoxDom.checked = false;
					checkBoxDom.defaultChecked = false;
					UIEditPanel.unselect(this.editPanelId + nSrcId);
				}
				tempNode = getNextHTMLSibling(tempNode);
			}
		}
		setTimeout(this.inspect.bind(this, this.selectedIds), 100);
	},
	toggleCertains : function(_aRowIds){
		_aRowIds = _aRowIds || [];
		this.reset();
		var nodesParent = $(this.sDatonId);
		var tempNode = getFirstHTMLChild(nodesParent);
		if(!Element.hasClassName(tempNode, this.oClzsDef.outer_box)){
			setTimeout(this.inspect.bind(this, []), 100);
			return;
		}
		while(tempNode){
			var nSrcId = tempNode.getAttribute(this.sSrcId);
			if(_aRowIds.include(nSrcId)){//需要选中这个节点
				this.selectedIds.push(nSrcId);
				var checkBoxDom = $(this.checkboxId + nSrcId);
				if(!checkBoxDom.checked){//当前没有选中
					checkBoxDom.checked = true;
					checkBoxDom.defaultChecked = true;
					var innerTabularDom = $(this.innerTabularId + nSrcId);				
					Element.addClassName(innerTabularDom, this.oClzsDef.active_element);
					UIEditPanel.select(this.editPanelId + nSrcId);
				}				 
			}else{//不需要选中这个节点
				var checkBoxDom = $(this.checkboxId + nSrcId);
				if(checkBoxDom && checkBoxDom.checked){//当前选中了这个节点
					checkBoxDom.checked = false;
					checkBoxDom.defaultChecked = false;
					var innerTabularDom = $(this.innerTabularId + nSrcId);				
					Element.removeClassName(innerTabularDom, this.oClzsDef.active_element);
					UIEditPanel.unselect(this.editPanelId + nSrcId);					
				}
			}
			tempNode = getNextHTMLSibling(tempNode);
		}
		setTimeout(this.inspect.bind(this, this.selectedIds), 100);
	},
	clearSelected : function(){
		this.keepEffect();
		for (var i = 0; i < this.selectedIds.length; i++){
			var nSrcId = this.selectedIds[i];
			var checkBoxDom = $(this.checkboxId + nSrcId);
			checkBoxDom.checked = false;
			checkBoxDom.defaultChecked = false;
			var innerTabularDom = $(this.innerTabularId + nSrcId);				
			Element.removeClassName(innerTabularDom, this.oClzsDef.active_element);
			UIEditPanel.unselect(this.editPanelId + nSrcId);
		}		
	},
	selectOne : function(count){
		if(this.selectedIds.length > 0){
			var startDomObj = $(this.tabularId + this.selectedIds.last());
			var tempNode = startDomObj;
			if(count > 0) var moveMethod = getNextHTMLSibling;
			else var moveMethod = getPreviousHTMLSibling;
			count = Math.abs(count);
			while(count > 0 && tempNode){
				tempNode = moveMethod(tempNode);
				count--;
			}
			if(!tempNode){
				tempNode = startDomObj;
			}
			var nSrcId = tempNode.getAttribute(this.sSrcId);
			return this._selectBySrcId(nSrcId);
		}else{
			var nSrcId = getFirstHTMLChild($(this.sDatonId)).getAttribute(this.sSrcId);
			if(!nSrcId) return;
			return this._selectBySrcId(nSrcId);
		}		
	},
	_selectBySrcId : function(nSrcId){
		var checkBoxDom = $(this.checkboxId + nSrcId);
		checkBoxDom.checked = true;
		checkBoxDom.defaultChecked = true;
		var innerTabularDom = $(this.innerTabularId + nSrcId);				
		Element.addClassName(innerTabularDom, this.oClzsDef.active_element);
		UIEditPanel.select(this.editPanelId + nSrcId);	
		return nSrcId;
	},
	selectPre : function(event){
		this.clearSelected();		
		this.selectedIds = [this.selectOne(-1)];
		setTimeout(this.inspect.bind(this), 100);
	},
	selectNext : function(){
		this.clearSelected();	
		this.selectedIds = [this.selectOne(1)];		
		setTimeout(this.inspect.bind(this), 100);
	},
	selectAbove : function(){
		this.clearSelected();	
		this.selectedIds = [this.selectOne(-this.getListGridLength())];	
		setTimeout(this.inspect.bind(this), 100);
	},
	selectBelow : function(event){
		this.clearSelected();	
		this.selectedIds = [this.selectOne(this.getListGridLength())];	
		setTimeout(this.inspect.bind(this), 100);
	},
	setListGridLength : function(){
		this.curListGridLength = 0;
		var rootNode = $(this.sDatonId);
		var tempNodeFirst = getFirstHTMLChild(rootNode);
		if(!tempNodeFirst){
			return;
		}
		this.curListGridLength = 1;
		var tempNodeSecond = getNextHTMLSibling(tempNodeFirst);
		while(tempNodeSecond){
			if(tempNodeFirst.offsetTop != tempNodeSecond.offsetTop){
				break;
			}
			this.curListGridLength++;
			tempNodeFirst = tempNodeSecond;
			tempNodeSecond = getNextHTMLSibling(tempNodeFirst);
		}
	},
	getListGridLength : function(){
		if(this.curListGridLength) return this.curListGridLength;
		this.setListGridLength();
		return this.curListGridLength;
	},
	_registerBaseEvents : function(){
		Object.extend(this, PageEventHandler);
		this.ctrlA = function(){
			this.toggleAll();
			return false;
		};
		this.keyUp = function(event){
			this.selectAbove(event);
			return false;
		};
		this.keyLeft = function(event){
			this.selectPre(event);
			return false;
		};
		this.keyDown = function(event){
			this.selectBelow(event);
			return false;
		};
		this.keyRight = function(event){
			this.selectNext(event);
			return false;
		};
	}
}

if(window.UIEditPanel){
	Object.extend(UIEditPanel,{
		select : function(_sId){
			var ePanel = $(_sId);
			ePanel.style.backgroundColor = '#29557B';
			ePanel.style.color = '#fff';
			ePanel.selected = true;
		},	
		unselect : function(_sId){
			var ePanel = $(_sId);
			ePanel.style.backgroundColor = '#F5F5F5';
			ePanel.style.color = '#000';
			ePanel.selected = false;
		},
		setValue : function(_sId, value){
			var ePanel = $(_sId);
			ePanel.innerHTML = $trans2Html(value);
			var inputElement = $(ePanel.id+"_");
			if(inputElement){
				inputElement.value = value;
			}
		}
	});
}

try{
	com.trs.wcm.SimpleDragger._destory_ = function(){
		var oDraggers = com.trs.wcm._Draggers;
		if(!oDraggers){
			return;
		}
		for (var i = 0; i < oDraggers.length; i++){
			var oDragger = oDraggers[i];
			if(oDragger.root){
				oDragger.root.Dragger = null;
				oDragger.root.onmousedown = null;
				oDragger.root = null;
			}
		}
		com.trs.wcm._Draggers = [];
	};
}catch(error){}