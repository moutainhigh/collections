//操作面板的设置
var OperatorTabDealer = {
	lastClickOperatorTypeRow : null, //最后单击的OperatorType行，左边的列表
	lastOperatorType : null, //最后单击的OperatorType类型，左边的列表
	lastClickOperatorRow : null,//最后单击的Operator行，右边的列表
	operatorTypeContainer : null,//OperatorType容器，左边的列表
	operatorContainer : null, //Operator容器，右边的列表
	operatorDisplayNumber : null,//显示个数输入控件
	coverBackgroundColor : '#E6E6FA',
	operatorActiveBackgroundColor: 'HIGHLIGHT',
	serviceName : 'wcm6_individuation',
	saveMethod : 'save',
	paramName : 'operator',//数据库中对应的自定义变量名

	//生成Operator列表
	createOperators : function(operatorType,bDirecct){		
		if($(operatorType) && bDirecct){
			$(operatorType).style.display = '';
		}else{
			BasicDataHelper.JspRequest("operators_create.jsp", {
				oprType	: operatorType
			}, true, function(transport, json){
				var result = new Array();
				var opers = wcm.SysOpers.getOpersByType(operatorType);
				if(bDirecct && transport.responseText.trim() != ""){
					var operators = transport.responseText.trim().split(";")[1].split(":")[1];
					for(var m=0,nLength = operators.split(",").length; m < nLength; m++ ){
						for(var n=0, nOperLength=opers.length; n<nOperLength; n++){
							if(opers[n].key == operators.split(",")[m]){
								result.push(opers[n]);
								break;
							}
						}
					}
				}else{
					result = opers;
				}
			var typeOperator = document.createElement("div");
			typeOperator.id = operatorType;
			typeOperator.style.display = '';
			if(!bDirecct){
				operatorContainer.innerHTML = "";
			}
			operatorContainer.appendChild(typeOperator);
			OperatorTabDealer.createOperatorRowInnerHTML(typeOperator, result, operatorType);

			if($(OperatorTabDealer.lastOperatorType)){
				OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);
			}
			}, function(transport, json){
				alert("获得可用操作失败");
			});
		}
	},
	
	/**
	*生成操作命令类型的innerHTML
	*@typeOperator 同一类型操作命令行的容器
	*@operatorTypeObj　类型操作对象
	*/
	createOperatorRowInnerHTML : function(typeOperator, operatorTypeObj, operatorType){
		for (var i = 0; i < operatorTypeObj.length; i++){			
			
			var operatorIconRow = document.createElement("div");
			operatorIconRow.className = "oper_item_row_icon " + operatorTypeObj[i]["key"];

			var operatorRow = document.createElement("div");
			operatorRow.className = "oper_item_row";
			operatorRow.setAttribute("title", operatorTypeObj[i]["title"] || operatorTypeObj[i]["desc"]);
			operatorRow.setAttribute("operKey", operatorTypeObj[i]["key"]);
			operatorRow.setAttribute("operType", operatorType);
			operatorRow.appendChild(operatorIconRow);

			var operatorDescRow = document.createElement("div");
			operatorDescRow.innerText = operatorTypeObj[i]["desc"];
			operatorDescRow.className = "oper_item_row_desc";
			operatorRow.appendChild(operatorDescRow);
			
			typeOperator.appendChild(operatorRow);
		}		
	},

	failure : function(message){//Ajax请求失败执行的回调函数
		alert("Ajax 请求失败！\n" + (message||""));	
	},

	//切换操作命令类型的活动/非活动行的样式
	toggleOperatorTypeStyle : function(domObj, isActive){
		if(domObj){
			if(isActive){
				domObj.style.backgroundColor = 'highlight'; 
				domObj.style.color = 'white';  
			}else{
				domObj.style.backgroundColor = "#fff";
				domObj.style.color = 'black';    
			}
		}	
	},

	//切换操作命令的活动/非活动行的样式
	toggleOperatorStyle : function(domObj, isActive){
		if(domObj){
			if(isActive){
				if(domObj.style.backgroundColor.toUpperCase() == this.coverBackgroundColor){
					domObj.setAttribute("_inCover_", true);
				}
				domObj.style.backgroundColor = this.operatorActiveBackgroundColor; 
				domObj.style.color = 'white';  
			}else{
				if(domObj.getAttribute("_inCover_")){
					domObj.style.backgroundColor = this.coverBackgroundColor;
					domObj.removeAttribute("_inCover_");
				}else{
					domObj.style.backgroundColor = "#fff";
				}
				domObj.style.color = 'black';    
			}
		}	
	},

	//将直接显示的命令保持别的背景颜色
	keepCoverOperator : function(displayOperatorNum){
		var node = getFirstHTMLChild($(OperatorTabDealer.lastOperatorType));
		while(node){
			if(displayOperatorNum > 0){
				if(node.className.indexOf("seperate") >= 0){
					displayOperatorNum++;
				}
				if(node.style.backgroundColor.toUpperCase() != this.operatorActiveBackgroundColor){
					node.style.backgroundColor = this.coverBackgroundColor;
				}
				if(!node.getAttribute("_inCover_")){
					node.setAttribute("_inCover_", true);
				}
			}else{
				if(node.style.backgroundColor.toUpperCase() != this.operatorActiveBackgroundColor){
					node.style.backgroundColor = "#fff";
				}
				if(node.getAttribute("_inCover_")){
					node.removeAttribute("_inCover_");
				}
			}			
			node = getNextHTMLSibling(node);
			displayOperatorNum--;
		}

	},

	/*
	*获得自定义操作类型的ｉｄ和显示命令个数
	*/
	getOperatorTypeId : function(successCallBack, failureCallBack){
		BasicDataHelper.call(this.serviceName, 'query', {
			userId			: topHandler.userId,
			paramName		: this.paramName,
			paramValue		: this.lastOperatorType,
			selectFields	: 'individuationid,objectIdsValue'
		}, true, function(transport, json){
			(successCallBack || Prototype.emptyFunction)(transport, json);
		}, function(transport, json){
			(failureCallBack || Prototype.emptyFunction)(transport, json);
		});
	},

	afterClick : function(){
		OperatorTabDealer.canDealWith = true;
	},

	//初始化操作面板页事件
	initOperatorTabEvent : function(){
		Event.observe(this.operatorTypeContainer, 'keydown', function(event){
			event = window.event || event;
			var keyCode = event.keyCode;
			if(keyCode != Event.KEY_UP && keyCode != Event.KEY_DOWN){
				return true;
			}
			var methodName = keyCode == Event.KEY_UP ? getPreviousHTMLSibling : getNextHTMLSibling;
			var nextSelectRow = OperatorTabDealer.lastClickOperatorTypeRow;
			while(nextSelectRow != null){
				nextSelectRow = methodName(nextSelectRow);
				if(nextSelectRow && nextSelectRow.getAttribute("type")){
					break;
				}
			}
			var bScrollIntoVeiw = false;
			if(nextSelectRow == null){
				if(keyCode == Event.KEY_DOWN){
					nextSelectRow = getFirstHTMLChild(OperatorTabDealer.operatorTypeContainer);
				}else{
					nextSelectRow = getLastHTMLChild(OperatorTabDealer.operatorTypeContainer);
				}
				bScrollIntoVeiw = true;
			}
			if(nextSelectRow && OperatorTabDealer.canDealWith){
				OperatorTabDealer.canDealWith = false;
				nextSelectRow.fireEvent('onclick');
				if(!bScrollIntoVeiw){
					if(keyCode == Event.KEY_DOWN){
						OperatorTabDealer.operatorTypeContainer.scrollTop += nextSelectRow.offsetHeight;
					}else{
						OperatorTabDealer.operatorTypeContainer.scrollTop -= nextSelectRow.offsetHeight;
					}
				}else{
					nextSelectRow.scrollIntoView();
				}
			}
			return false;
		});

		//邦定操作类型行的click事件
		Event.observe(this.operatorTypeContainer, 'click', function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			var operatorType = srcElement.getAttribute('type');

			if(!operatorType){//单击的不是感兴趣的对象
				return;
			}
			if(OperatorTabDealer.lastClickOperatorTypeRow == srcElement){
				return;
			}
			
			//生成指定类型的所有操作
			OperatorTabDealer.createOperators(operatorType,true);

			//切换左边列表选中的样式，并记录最后单击元素
			OperatorTabDealer.toggleOperatorTypeStyle(OperatorTabDealer.lastClickOperatorTypeRow, false);
			OperatorTabDealer.lastClickOperatorTypeRow = srcElement;
			OperatorTabDealer.toggleOperatorTypeStyle(OperatorTabDealer.lastClickOperatorTypeRow, true);

			//隐藏右边对应的操作容器，并清空保留的记录
			if(OperatorTabDealer.lastOperatorType){
				$(OperatorTabDealer.lastOperatorType).style.display = 'none';
				OperatorTabDealer.toggleOperatorStyle(OperatorTabDealer.lastClickOperatorRow, false);
				OperatorTabDealer.lastClickOperatorRow = null;
			}
			OperatorTabDealer.lastOperatorType = operatorType;
			
			//从服务器端载入当前自定义ＩＤ
			OperatorTabDealer.getOperatorTypeId(function successCallBack(transport, json){
				try{
					OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("isLoaded", true);
					var objectId = $v(json,"INDIVIDUATIONS.INDIVIDUATION.INDIVIDUATIONID") || 0;
					var displayNumber = 7;
					if(operatorType.toLowerCase().indexOf('in') >=0)
						displayNumber = 5;
					if(objectId != 0){
						var objectIdsValue = $v(json,"INDIVIDUATIONS.INDIVIDUATION.OBJECTIDSVALUE");
						displayNumber = objectIdsValue.split(";")[0].split(":")[1];
					}
					var lastClickRow = OperatorTabDealer.lastClickOperatorTypeRow;
					if(lastClickRow.getAttribute("changed")){
						displayNumber = lastClickRow.getAttribute("displayNumber");
					}else{
						lastClickRow.setAttribute("displayNumber", displayNumber);
					}
					lastClickRow.setAttribute("objectId", objectId);
					OperatorTabDealer.operatorDisplayNumber.value = displayNumber;
					OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);	
					OperatorTabDealer.afterClick();
				}catch(error){}
			}, OperatorTabDealer.afterClick);
		});
				
		//邦定操作行的mousedown事件
		Event.observe(this.operatorContainer, 'mousedown', function(event){
			event = window.event || event;
			var srcElement = Event.element(event);
			
			if(srcElement.className.indexOf('oper_item_row') < 0 ){//单击的不是感兴趣的对象
				return;
			}
			if(srcElement.className != "oper_item"){
				srcElement = srcElement.parentNode;
			}
			OperatorTabDealer.toggleOperatorStyle(OperatorTabDealer.lastClickOperatorRow, false);
			OperatorTabDealer.lastClickOperatorRow = srcElement;
			OperatorTabDealer.toggleOperatorStyle(OperatorTabDealer.lastClickOperatorRow, true);

			//处理拖动
			var operatorContainer = OperatorTabDealer.operatorContainer;	
			var oldNextSibling = null;
			(function __onDragStart(){
				try{
					(operatorContainer.setCapture || Prototype.emptyFunction)();
				}catch(error){
				}
				oldNextSibling = getNextHTMLSibling(OperatorTabDealer.lastClickOperatorRow);
			})();
			var __onDrag = function(){
				var currY = Event.pointerY(event) + operatorContainer.scrollTop;
				var nodes =$(OperatorTabDealer.lastOperatorType).childNodes;
				for (var i = 0; i < nodes.length; i++){
					if(nodes[i] == OperatorTabDealer.lastClickOperatorRow) continue;
					var offset = Position.cumulativeOffset(nodes[i]);
					var offsetHalfHeight = nodes[i].offsetHeight / 2;
					
					if(((offset[1] - offsetHalfHeight) <= currY) && (currY < (offset[1] + offsetHalfHeight))){
						$(OperatorTabDealer.lastOperatorType).insertBefore(OperatorTabDealer.lastClickOperatorRow, nodes[i]);			
						break;
					}
					if((i+1 == nodes.length) && currY > offset[1]+offsetHalfHeight){//移到最后
						$(OperatorTabDealer.lastOperatorType).insertBefore(OperatorTabDealer.lastClickOperatorRow);			
						break;					
					}
				}
			};
			var __onDragEnd = function(){
				try{
					(operatorContainer.releaseCapture || Prototype.emptyFunction)();
				}catch(error){
				}
				Event.stopObserving(operatorContainer, 'mousemove', __onDrag);
				Event.stopObserving(operatorContainer, 'mouseup', __onDragEnd);		
				var newNextSibling = getNextHTMLSibling(OperatorTabDealer.lastClickOperatorRow);
				OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);
				if(oldNextSibling != newNextSibling){
					OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", true);
				}
			};
			Event.observe(operatorContainer, 'mousemove', __onDrag);
			Event.observe(operatorContainer, 'mouseup', __onDragEnd);
		});

		//绑定重置按钮的单击事件
		Event.observe('resetOperatorBtn', 'click', function(){	
			OperatorTabDealer.createOperators(OperatorTabDealer.lastOperatorType,false);	
			var objectId = OperatorTabDealer.lastClickOperatorTypeRow.getAttribute("objectId");
			if(objectId == "0"){
				OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", false);
			}else{
				OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", true);
			}
			OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);	
		});

		//绑定下移按钮的单击事件
		Event.observe('moveDownBtn', 'click', function(){
			if(!OperatorTabDealer.lastClickOperatorRow) return;
			var nextOperatorRow = getNextHTMLSibling(OperatorTabDealer.lastClickOperatorRow);
			if(nextOperatorRow){
				nextOperatorRow.insertAdjacentElement("afterEnd" ,OperatorTabDealer.lastClickOperatorRow);

				OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);
				OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", true);
			}
		});

		//绑定上移按钮的单击事件
		Event.observe('moveUpBtn', 'click', function(){
			if(!OperatorTabDealer.lastClickOperatorRow) return;
			var previousOperatorRow = getPreviousHTMLSibling(OperatorTabDealer.lastClickOperatorRow);
			if(previousOperatorRow){
				previousOperatorRow.insertAdjacentElement("beforeBegin" ,OperatorTabDealer.lastClickOperatorRow);

				OperatorTabDealer.keepCoverOperator(OperatorTabDealer.operatorDisplayNumber.value);
				OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", true);
			}		
		});		

		//修改命令显示个数时触发的事件
		Event.observe(this.operatorDisplayNumber, 'blur', function(){
			var oldValidValue = OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("displayNumber") || '';
			var displayNumber = OperatorTabDealer.operatorDisplayNumber.value.trim();
			if(!displayNumber.match(/^\d{1,}$/)){
				alert("输入格式错误,必须为数字");
				OperatorTabDealer.operatorDisplayNumber.value = oldValidValue;
				OperatorTabDealer.operatorDisplayNumber.select();
				OperatorTabDealer.operatorDisplayNumber.focus();
				return false;
			}
			if(parseInt(displayNumber) < 1){
				alert("输入值小于最小值:1");
				OperatorTabDealer.operatorDisplayNumber.value = oldValidValue;
				OperatorTabDealer.operatorDisplayNumber.select();
				OperatorTabDealer.operatorDisplayNumber.focus();
				return false;
			}
			if(parseInt(displayNumber) > 100){
				alert("输入值大于最大值:100");
				OperatorTabDealer.operatorDisplayNumber.value = oldValidValue;
				OperatorTabDealer.operatorDisplayNumber.select();
				OperatorTabDealer.operatorDisplayNumber.focus();
				return false;
			}
			OperatorTabDealer.keepCoverOperator(displayNumber);
			OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("displayNumber", displayNumber);
			OperatorTabDealer.lastClickOperatorTypeRow.setAttribute("changed", true);
		});
	},

	getObjectIdsValue : function(node){
		var displayNum = node.getAttribute("displayNumber");
		var objectIdsValue = [];
		var node = getFirstHTMLChild($(node.getAttribute("type")));		
		while(node){
			objectIdsValue.push(node.getAttribute("operKey"));
			node = getNextHTMLSibling(node);
		}	
		return "displayNumber:" + displayNum + ";order:" + objectIdsValue.join(",");
	},

	setSaveParams : function(aCombine, oHelper){
		var node = getFirstHTMLChild(this.operatorTypeContainer);
		while(node){
			if(node.getAttribute("changed")){
				aCombine.push(oHelper.Combine(this.serviceName, this.saveMethod, {
					objectId		: node.getAttribute("objectid") || 0,
					paramName		: this.paramName,
					paramValue		: node.getAttribute("type"),
					objectIdsValue	: this.getObjectIdsValue(node)
				}));
			}
			node = getNextHTMLSibling(node)
		}
	},
	//初始化操作面板页的值
	initOperatorTabValue : function(){
		//TODO初始化默认控件的值
		this.operatorDisplayNumber.value = topHandler.operatorDisplayNumber || 5;
		//选中操作类型的第一行			
		getFirstHTMLChild(OperatorTabDealer.operatorTypeContainer).fireEvent('onclick');		
	},

	//初始化操作面板页
	initOperatorTab : function(){
		this.operatorTypeContainer = $('operatorTypeContainer');
		this.operatorContainer = $('operatorContainer');
		this.operatorDisplayNumber = $('operatorDisplayNumber');
		this.initOperatorTabEvent();
		this.initOperatorTabValue();
		this.canDealWith = true;
	}
};

//点击个性化设置页面的“操作面板”节点时，触发的动作
function operatorTabLoad(){
	if(!OperatorTabDealer.isLoaded){
		OperatorTabDealer.initOperatorTab();
		OperatorTabDealer.isLoaded = true;
	}
}

function getNextHTMLSibling(domNode){
	if(domNode == null) return null;
	var tempNode = domNode.nextSibling;
	while(tempNode && tempNode.nodeType != 1){
		tempNode = tempNode.nextSibling;
	}
	//fix <p>ab<b>sf</p>ddsf</b>sd<div>dsf</div>d
	return ((tempNode == null)||(tempNode.parentNode != domNode.parentNode)) ? null : tempNode;
}
function getPreviousHTMLSibling(domNode){
	if(domNode == null) return null;
	var tempNode = domNode.previousSibling;
	while(tempNode && tempNode.nodeType != 1){
		tempNode = tempNode.previousSibling;
	}
	return tempNode;
}
function getFirstHTMLChild(domNode){
	if(domNode == null) return null;
	for (var i = 0; i < domNode.childNodes.length; i++){
		if(domNode.childNodes[i].nodeType == 1){
			return domNode.childNodes[i];
		}
	}
	return null;
}
function getLastHTMLChild(domNode){
	if(domNode == null) return null;
	for (var i = domNode.childNodes.length-1; i >= 0; i--){
		if(domNode.childNodes[i].nodeType == 1){
			return domNode.childNodes[i];
		}
	}
	return null;
}
//Event.observe(window, 'load', OperatorTabDealer.initOperatorTab.bind(OperatorTabDealer));