var m_bFirstClick = true;

function TRSHTMLTr_isInvalidElement(_currElement){	
	var elTemp = _currElement;
	for(var i=0; i<10; i++){
		if(elTemp == null)return false;
		if(elTemp.tagName == "A")return true;		

		if(elTemp.tagName == "INPUT" && elTemp.type != "checkbox" && elTemp.type != "radio")return true;		

		if(elTemp.tagName == "INPUT" && (elTemp.type == "checkbox" || elTemp.type == "radio") && elTemp.bTrigger)return true;		
		

		elTemp = elTemp.parentElement;
	}
	return false;
}
function TRSHTMLTr_onSelectedTR(_elTR){
	var evt = window.event;
	if(!evt || evt == 'undefined'){
		evt = TRSHTMLTr_onSelectedTR.caller;
		while(evt){
			var arg0=evt.arguments[0];
			if(arg0){
				if(arg0 instanceof Event){ // event 
					evt = arg0;
					break;
				}
			}
			evt = evt.caller;
		}
	}
	var elEvent = evt.target||evt.srcElement;
	//不响应某些元素的点击
	if(TRSHTMLTr_isInvalidElement(elEvent))
		return;
	//参数校验
	if(_elTR == null){
		CTRSAction_alert(wcm.LANG.WCM52_ALERT_28 || "调用错误!");
		return;
	}
	//var OldStyle = _elTR.className;
	var bHasRadio = false;
	var arInputs = _elTR.getElementsByTagName("INPUT");
	var nInputCount = arInputs.length || 0;
	for(var n=0; n<nInputCount; n++){
		if(arInputs[n].type == "radio"){
			bHasRadio = true;
			break;
		}
	}
	if(m_bFirstClick ){
		m_bFirstClick = false;
		if(_elTR.className != this.unselectedClassName){
			this.unselectedClassName = _elTR.className;
			//this.selectedClassName = _elTR.className + "_selected";
		}
	}

	//判断Ctrl键是否按下
	if(!(evt.ctrlKey || (elEvent.tagName == "INPUT" && elEvent.type == "checkbox")) || bHasRadio)
	{//取消以前的选择
		for(var i=(this.arElCheckBeforeSelect.length-1); i>=0; i--){
			if(this.arElTRBeforeSelect[i] != _elTR){
				this.arElCheckBeforeSelect[i].bTrigger = true;
				if(this.arElCheckBeforeSelect[i].checked)
					this.arElCheckBeforeSelect[i].click();
				this.arElCheckBeforeSelect[i].bTrigger = false;
			}
			this.arElCheckBeforeSelect.pop();

			if(this.arElTRBeforeSelect[i] != _elTR)
				this.arElTRBeforeSelect[i].className		= this.unselectedClassName;
			this.arElTRBeforeSelect.pop();
		}			
	}
	
	//获取Checkbox		
	var bChecked = false;
	if(elEvent.tagName == "INPUT" && (elEvent.type == "checkbox" || elEvent.type == "radio"))
	{//Checkbox点击		
		bChecked = elEvent.checked;
		//记录选中的元素			
		if(bChecked){
			this.arElCheckBeforeSelect[this.arElCheckBeforeSelect.length]	= elEvent;
			this.arElTRBeforeSelect[this.arElTRBeforeSelect.length]			= _elTR;
		}
		
	}else{//TR点击
		if(arInputs.length == 0){
			CTRSAction_alert(wcm.LANG.WCM52_ALERT_29 || "没有定义INPUT!");
			return;
		}
		
		for(var i=0; i<arInputs.length; i++){
			if(arInputs[i].type == "checkbox" || arInputs[i].type == "radio"){
				arInputs[i].bTrigger = true;
				arInputs[i].click();
				arInputs[i].bTrigger = false;
				
				if(arInputs[i].checked)
					bChecked = true;
				break;
			}
		}
		//记录选中的元素			
		if(bChecked){
			this.arElCheckBeforeSelect[this.arElCheckBeforeSelect.length]	= arInputs[i];
			this.arElTRBeforeSelect[this.arElTRBeforeSelect.length]			= _elTR;
		}

		
	}
	//设置样式
	if(bChecked){
		_elTR.className = this.selectedClassName;
	}else{
		_elTR.className = this.unselectedClassName;
	}
	//this.unselectedClassName = OldStyle;
}

function CTRSHTMLTr(){
	this.unselectedClassName	= "list_tr";
	this.selectedClassName		= "grid_row_active";
	this.arElCheckBeforeSelect	= [];
	this.arElTRBeforeSelect		= [];	
}

CTRSHTMLTr.prototype.onSelectedTR = TRSHTMLTr_onSelectedTR;

var TRSHTMLTr = new CTRSHTMLTr();
