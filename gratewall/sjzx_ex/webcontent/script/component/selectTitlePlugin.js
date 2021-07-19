/**
 * Author : Barry
 * 模拟Select, 给Option加上Title
 * 参数列表：第1个，第2个 ... 第n个下拉框的ID，最后一个参数是该下拉框是否从缓存读取数据,该参数可以为空，默认为不会动态读取数据(true) 
 */
function setShowTitleSelectList(){
	var isIE7 = window.navigator.userAgent.toUpperCase().indexOf("MSIE 7") >= 0 ? true : false;
	// IE8 按照IE7的表现来展示。
	var isIE8 = window.navigator.userAgent.toUpperCase().indexOf("MSIE 8") >= 0 ? true : false;
//	alert("isIE7:" + isIE7 + ", isIE8:" + isIE8);
	if (isIE8){
		isIE7 = true;
	}
	var optionHeight = 15;
	var selectedItem;	
	
	var optionContainerDiv = document.getElementById("_optionContainerDiv");
	
	if (!optionContainerDiv){
		optionContainerDiv = document.createElement("div");
		optionContainerDiv.id = "_optionContainerDiv";
		optionContainerDiv.className = "optionContainer";
		document.body.appendChild(optionContainerDiv);
		
		// 如果有滚动条的时候防止出现错位情况
		if (document.getElementById("body-div")){
		    document.getElementById("body-div").attachEvent("onscroll", function(){
                optionContainerDiv.style.display = "none";
                document.getElementById("optionContainerFrame").style.display = "none";
            });
		}
	}	
	
	var selectLength = arguments.length;
	
	// 是否不会动态改变数据，默认为true, 如果是动态改变数据的，需要设置最后一个参数为false;
	var noDataChange = true;
	if (selectLength > 1 
		&& typeof (arguments[selectLength - 1]) == "boolean" ){
		noDataChange = arguments[selectLength - 1];
		selectLength = selectLength - 1;		
	}
	
	for (var i = 0; i < selectLength; i ++){
//		if (typeof(arguments[i]) != "string"){
//			alert("传入的第【" + i + "】个参数不是字符串！");
//			return false;
//		}
		// 遮盖IFRAME
		var optionContainerFrame = document.getElementById("optionContainerFrame");
		var selectObj;
		
		if (typeof(arguments[i]) == "string"){
			selectObj = document.getElementById(arguments[i]);
		}else if(typeof(arguments[i]) == "object"){
			selectObj = arguments[i];
		}else{
			alert("传入的第【" + i + "】个参数有问题！");
			return false;
		}
		
		//如果是IE7的话，在每个下拉框上添加一个新透明DIV，点击这个DIV的时候响应
		if (isIE7 && !selectObj.enveLopObj){
			var optionEnveLopDiv = document.createElement("div");
			optionEnveLopDiv.className = "enveLopDiv";
			optionEnveLopDiv.style.display = selectObj.style.display;
			optionEnveLopDiv.selectObj = selectObj.id;
			optionEnveLopDiv.onclick = function(){
				document.getElementById(this.selectObj).onmousedown();
			};
			optionEnveLopDiv.innerHTML = "123";
			selectObj.parentNode.appendChild(optionEnveLopDiv);
			optionEnveLopDiv.id = selectObj.id + "_EnveLopDiv";
			selectObj.enveLopObj = optionEnveLopDiv.id;
			selectObj.onpropertychange = function(){
				var p = this;
				if (event.type && event.type == "propertychange" 
						&& event.propertyName && event.propertyName == "style.display"){
					document.getElementById(p.enveLopObj).style.display = p.style.display;
				}
				p = null;
			};
		}		
		
		// 绑定下拉框对象的事件
		selectObj.onbeforeactivate = function(){return false};
		selectObj.onmouseover = function(){this.setCapture()};
		selectObj.onmouseout = function(){this.releaseCapture()};
		selectObj.onmousedown = function(){
			var p = this;
			// 如果当前已经打开，则关闭选择框，并返回
			if(selectedItem == p.getAttribute("id")){
				if (optionContainerDiv.style.display=="block"){
					optionContainerDiv.style.display = "none";
					optionContainerFrame.style.display = "none";
					return false;
				}else if (noDataChange){
					optionContainerDiv.style.display = "block";
					optionContainerFrame.style.display = "block";
					optionContainerDiv.focus();
					return false;
				}
			}
		    
			// 初始化对象的坐标
			var cood = getElementPos(p);
			// 获取记录总数
			var size = p.children.length > 10 ? 10 : p.children.length;
			// 是否需要查询条件
			var isNeedQuery = p.children.length > 20 ? true : false;
			// 设置DIV和遮盖IFRAME的位置和大小信息
			var offsetHeight = p.offsetHeight;
			var offsetWidth  = p.offsetWidth;
			var offsetLeft   = cood.x;
			var offsetTop    = cood.y;
			if (offsetWidth < 200 && isNeedQuery){
				offsetWidth = 200;
			}
			
			// 便利select，得到最大文本数，用来计算显示的宽度
			var maxLength = 0;
			for (var getOptionLengthIndex = 0; getOptionLengthIndex < p.children.length; getOptionLengthIndex ++){
				var text = p.children[getOptionLengthIndex].text;
				if (text){
					var l = text.length;
					if ( l > maxLength){
						maxLength = l;
					}
				}
				text = null;
			}
			// 计算宽度，但是不要大于500px
			if (maxLength * 13 > 200){
				offsetWidth = maxLength * 13 > 500 ? 500 : maxLength * 13;
			}
			
			optionContainerDiv.innerHTML = '<div id="_selectQueryInputDiv" name="_selectQueryInputDiv" style="height:20px;width:100%;display:none;font-size:12px;color:#0000ff;overflow:hidden">过滤条件<input id="_selectQueryInput" name="_selectQueryInput" type="text" class="queryInput"/></div><div id="_selectQueryTempDiv" class="selectQueryResultContainer"></div>';
			var _tempDiv = document.getElementById("_selectQueryTempDiv");
			var _selectQueryInputDiv = document.getElementById("_selectQueryInputDiv");
			var _selectQueryInput = document.getElementById("_selectQueryInput");
			
			var st = document.getElementById("body-div") ? document.getElementById("body-div").scrollTop : 0; 
			
			optionContainerDiv.style.top     = offsetHeight + offsetTop - st;
			optionContainerFrame.style.top   = offsetHeight + offsetTop - st;
			optionContainerDiv.style.left    = offsetLeft;
			optionContainerFrame.style.left  = offsetLeft;
			optionContainerDiv.style.width   = offsetWidth;
			optionContainerFrame.style.width = offsetWidth;
			
			if (isNeedQuery){
				_selectQueryInputDiv.style.display = "block";
				_tempDiv.style.height = optionHeight * size + 2;
				optionContainerFrame.style.height = optionHeight * size + 20 + 8;
				_tempDiv.style.width = offsetWidth - 6;
			}else{
				_tempDiv.style.border = "none";
				_tempDiv.style.height = optionHeight * size;
				optionContainerFrame.style.height = optionHeight * size + 6;
				// _tempDiv.style.width = offsetWidth - 4;
			}
			
			for (var optionIndex = 0; optionIndex < p.children.length; optionIndex ++){
				var text = p.children[optionIndex].text;
				var optionDiv = document.createElement("div");
				optionDiv.className = "optionDiv";
				optionDiv.style.height = optionHeight;
				optionDiv.setAttribute("index", optionIndex);
				optionDiv.setAttribute("title", text);
				optionDiv.innerHTML = text;
				_tempDiv.appendChild(optionDiv);
				// 释放内存
				optionDiv = null;
			}
		    
			optionContainerDiv.style.display = "block";
			optionContainerFrame.style.display = "block";
			// 动态设定高度 --- Start
			var tempHeight = parseInt(optionContainerDiv.offsetHeight);
			var tempTop = parseInt(optionContainerDiv.style.top);
			var needHeight = tempHeight + tempTop;
			var bodyHeight = document.getElementById("body-div") || document.body;
			bodyHeight = bodyHeight.scrollHeight;
		    if(document.body.offsetHeight < needHeight){// 如果下方区域不够大，看看上方区域够不够
		    	if (tempTop > tempHeight + p.offsetHeight){
		    		var newTop = tempTop - tempHeight - p.offsetHeight;
		    		optionContainerDiv.style.top = newTop;
		    		optionContainerFrame.style.top = newTop;
		    		newTop = 0;
		    	}else if(tempHeight < bodyHeight){ // 让弹出框向下对齐
		    		optionContainerDiv.style.top = bodyHeight - tempHeight;
		    		optionContainerFrame.style.top = bodyHeight - tempHeight;
		    	}else{ // 缩小弹出框高度
		    		if (isNeedQuery){
		    			_tempDiv.style.height = bodyHeight - 28;
		    		}else{
		    			_tempDiv.style.height = bodyHeight - 6;
		    		}
		    		optionContainerDiv.style.top = 0;
		    		optionContainerDiv.style.height = bodyHeight;
		    		optionContainerFrame.style.top = 0;
		    		optionContainerFrame.style.height = bodyHeight;
		    	}
		    }
		    tempHeight = null;
		    tempTop = null;
		    needHeight = null;
		    bodyHeight = null;
		    // 动态设定高度 --- End
		    
		    
		    // 动态设定宽度 --- Start
		    var tempWidth = parseInt(optionContainerDiv.offsetWidth);
			var tempLeft = parseInt(optionContainerDiv.style.left);
			var needWidth = tempLeft + tempWidth;
			var bodyWidth = document.getElementById("body-div") || document.body;
			bodyWidth = bodyWidth.scrollWidth;
			if (bodyWidth < needWidth){ // 如果当前的宽度小于需要的宽度，需要调整下拉框left位置
				if (tempWidth <= bodyWidth){
					optionContainerDiv.style.left = bodyWidth - tempWidth;
					optionContainerFrame.style.left = bodyWidth - tempWidth;
				}else{
					optionContainerDiv.style.overflowX = "hidden";
					optionContainerDiv.style.left = 0;
					optionContainerDiv.style.width = bodyWidth;
					optionContainerFrame.style.left = 0;
					optionContainerFrame.style.width = bodyWidth;
				}
			}
			tempWidth = null;
			tempLeft = null;
			needWidth = null;
			bodyWidth = null;
		    // 动态设定宽度 --- End
		    
		    
			selectedItem = p.getAttribute("id");
			optionContainerDiv.focus();
			
			var optionDivs = _tempDiv.childNodes;
			for (var tempIndex in optionDivs){
				optionDivs[tempIndex].onfocus = function(){
					p.selectedIndex = this.getAttribute("index");
					// 如果有onchange事件，执行
					if(p.onchange){
						p.onchange();
					}
				}
				
				optionDivs[tempIndex].onmouseover = function(){
					this.style.backgroundColor = "#316AC5";
				}
				
				optionDivs[tempIndex].onmouseout = function(){
					this.style.backgroundColor = "#FFFFFF";
				}
			}
			
			_selectQueryInput.onkeyup = function(){
				try{
					if(event.keyCode == 13 || event.keyCode == 9){
						return false;
					}
					
					var	allCode = _selectQueryTempDiv.children;
					var filter = document.getElementById("_selectQueryInput").value;
					
					var isRegSep = /[\(\)\:\.\$\^\\\*\+\?\{\}\,\=\!\|\[\]\-]/gi;
					filter = filter.replace(isRegSep,function(){
						return "\\" + arguments[0];
						
					});
					var regExp = new RegExp(filter, "i");
					for (var index= 0; index < allCode.length; index++){
						if(allCode[index].tagName.toUpperCase() != "DIV"){
							continue;
						}
						if (allCode[index].innerHTML.search(regExp) < 0){
							allCode[index].style.display = "none";
						}else{
							allCode[index].style.display = "block";
						}
					}
					
				}catch(e){
					// 啥也不做
				}
			}

			// 为下拉框对象、输入框 绑定onblur事件
			optionContainerDiv.onblur = _selectQueryInput.onblur
				= _selectQueryInputDiv.onblur 
				= _tempDiv.onblur = function(){
				var focusElement = document.activeElement;
				if (focusElement.id != "_selectQueryInputDiv" 
					&& focusElement.id != "_selectQueryInput" 
					&& focusElement.id != "_selectQueryTempDiv")
				{
					optionContainerDiv.style.display = "none";
					optionContainerFrame.style.display = "none";
				}				
			}
		}
	}
}
