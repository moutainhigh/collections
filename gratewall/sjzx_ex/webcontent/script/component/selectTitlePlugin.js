/**
 * Author : Barry
 * ģ��Select, ��Option����Title
 * �����б���1������2�� ... ��n���������ID�����һ�������Ǹ��������Ƿ�ӻ����ȡ����,�ò�������Ϊ�գ�Ĭ��Ϊ���ᶯ̬��ȡ����(true) 
 */
function setShowTitleSelectList(){
	var isIE7 = window.navigator.userAgent.toUpperCase().indexOf("MSIE 7") >= 0 ? true : false;
	// IE8 ����IE7�ı�����չʾ��
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
		
		// ����й�������ʱ���ֹ���ִ�λ���
		if (document.getElementById("body-div")){
		    document.getElementById("body-div").attachEvent("onscroll", function(){
                optionContainerDiv.style.display = "none";
                document.getElementById("optionContainerFrame").style.display = "none";
            });
		}
	}	
	
	var selectLength = arguments.length;
	
	// �Ƿ񲻻ᶯ̬�ı����ݣ�Ĭ��Ϊtrue, ����Ƕ�̬�ı����ݵģ���Ҫ�������һ������Ϊfalse;
	var noDataChange = true;
	if (selectLength > 1 
		&& typeof (arguments[selectLength - 1]) == "boolean" ){
		noDataChange = arguments[selectLength - 1];
		selectLength = selectLength - 1;		
	}
	
	for (var i = 0; i < selectLength; i ++){
//		if (typeof(arguments[i]) != "string"){
//			alert("����ĵڡ�" + i + "�������������ַ�����");
//			return false;
//		}
		// �ڸ�IFRAME
		var optionContainerFrame = document.getElementById("optionContainerFrame");
		var selectObj;
		
		if (typeof(arguments[i]) == "string"){
			selectObj = document.getElementById(arguments[i]);
		}else if(typeof(arguments[i]) == "object"){
			selectObj = arguments[i];
		}else{
			alert("����ĵڡ�" + i + "�������������⣡");
			return false;
		}
		
		//�����IE7�Ļ�����ÿ�������������һ����͸��DIV��������DIV��ʱ����Ӧ
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
		
		// �������������¼�
		selectObj.onbeforeactivate = function(){return false};
		selectObj.onmouseover = function(){this.setCapture()};
		selectObj.onmouseout = function(){this.releaseCapture()};
		selectObj.onmousedown = function(){
			var p = this;
			// �����ǰ�Ѿ��򿪣���ر�ѡ��򣬲�����
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
		    
			// ��ʼ�����������
			var cood = getElementPos(p);
			// ��ȡ��¼����
			var size = p.children.length > 10 ? 10 : p.children.length;
			// �Ƿ���Ҫ��ѯ����
			var isNeedQuery = p.children.length > 20 ? true : false;
			// ����DIV���ڸ�IFRAME��λ�úʹ�С��Ϣ
			var offsetHeight = p.offsetHeight;
			var offsetWidth  = p.offsetWidth;
			var offsetLeft   = cood.x;
			var offsetTop    = cood.y;
			if (offsetWidth < 200 && isNeedQuery){
				offsetWidth = 200;
			}
			
			// ����select���õ�����ı���������������ʾ�Ŀ��
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
			// �����ȣ����ǲ�Ҫ����500px
			if (maxLength * 13 > 200){
				offsetWidth = maxLength * 13 > 500 ? 500 : maxLength * 13;
			}
			
			optionContainerDiv.innerHTML = '<div id="_selectQueryInputDiv" name="_selectQueryInputDiv" style="height:20px;width:100%;display:none;font-size:12px;color:#0000ff;overflow:hidden">��������<input id="_selectQueryInput" name="_selectQueryInput" type="text" class="queryInput"/></div><div id="_selectQueryTempDiv" class="selectQueryResultContainer"></div>';
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
				// �ͷ��ڴ�
				optionDiv = null;
			}
		    
			optionContainerDiv.style.display = "block";
			optionContainerFrame.style.display = "block";
			// ��̬�趨�߶� --- Start
			var tempHeight = parseInt(optionContainerDiv.offsetHeight);
			var tempTop = parseInt(optionContainerDiv.style.top);
			var needHeight = tempHeight + tempTop;
			var bodyHeight = document.getElementById("body-div") || document.body;
			bodyHeight = bodyHeight.scrollHeight;
		    if(document.body.offsetHeight < needHeight){// ����·����򲻹��󣬿����Ϸ����򹻲���
		    	if (tempTop > tempHeight + p.offsetHeight){
		    		var newTop = tempTop - tempHeight - p.offsetHeight;
		    		optionContainerDiv.style.top = newTop;
		    		optionContainerFrame.style.top = newTop;
		    		newTop = 0;
		    	}else if(tempHeight < bodyHeight){ // �õ��������¶���
		    		optionContainerDiv.style.top = bodyHeight - tempHeight;
		    		optionContainerFrame.style.top = bodyHeight - tempHeight;
		    	}else{ // ��С������߶�
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
		    // ��̬�趨�߶� --- End
		    
		    
		    // ��̬�趨��� --- Start
		    var tempWidth = parseInt(optionContainerDiv.offsetWidth);
			var tempLeft = parseInt(optionContainerDiv.style.left);
			var needWidth = tempLeft + tempWidth;
			var bodyWidth = document.getElementById("body-div") || document.body;
			bodyWidth = bodyWidth.scrollWidth;
			if (bodyWidth < needWidth){ // �����ǰ�Ŀ��С����Ҫ�Ŀ�ȣ���Ҫ����������leftλ��
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
		    // ��̬�趨��� --- End
		    
		    
			selectedItem = p.getAttribute("id");
			optionContainerDiv.focus();
			
			var optionDivs = _tempDiv.childNodes;
			for (var tempIndex in optionDivs){
				optionDivs[tempIndex].onfocus = function(){
					p.selectedIndex = this.getAttribute("index");
					// �����onchange�¼���ִ��
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
					// ɶҲ����
				}
			}

			// Ϊ�������������� ��onblur�¼�
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
