/** Title:			CTRSHTMLElement.js
 *  Description:
 *		Define TRSHTMLElement Object
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-11-24 15:24
 *  Vesion:			1.0
 *  Last EditTime:	2004-11-24/2004-11-24
 *	Update Logs:
 *		CH@2004-11-24 Created File
 *	Note:
 *		
 *	Depends:
 *		
 *	Examples:
 *		//Select Checkbox named "DocumentId"
 *		TRSHTMLElement.selectAllByName("DocumentId");
 *
 *		//Get the Checked Value of the Checkboxs name "DocumentId"
 *		TRSHTMLElement.getElementValueByName("DocumentId");
 *			
 */

function CTRSHTMLElement(){	
	this.getSelectAllStatus		= function(_arElements){
		for(var i=0; i<_arElements.length; i++){
			if(_arElements[i].disabled) continue;
			if(!_arElements[i].checked)return true;
		}
		return false;
	}

	this.selectAllByName		= function(_sElementName, _bChecked, _oCheckValue){
		//1.参数校验
		if(_sElementName == null){
			CTRSAction_alert("Element Name invalid!");
			return;
		}
		//2.获取
		var arElements = document.all(_sElementName);	
		if(arElements == null)return;

		if(!arElements.length){//单个元素
			//wsw @ 2005-7-14 修改点击方式，这种方式不会触发背景色的修改
			//arElements.checked = !arElements.checked;
			arElements.click();
			return;
		}
		
		var bSelectAllStatus = false;
		if(arguments.length == 2){
			bSelectAllStatus = _bChecked;
		}else{
			bSelectAllStatus = this.getSelectAllStatus(arElements);
		}
		//3.遍历
		for(var i=0; i<arElements.length; i++){
			if(_oCheckValue == null || typeof _oCheckValue == "undefined"){
				if(bSelectAllStatus != arElements[i].checked)arElements[i].click();
			}else{
				if(_oCheckValue.constructor == Array){
					for(var j=0; j<_oCheckValue.length; j++){
						if( arElements[i].value == _oCheckValue[j]){
							arElements[i].checked = _bChecked;	
							break;
						}
					}
				}else if( arElements[i].value == _oCheckValue){
					arElements[i].checked = _bChecked;	
				}
				
			}
			//arElements[i].checked = bSelectAllStatus;
		}
	}

	this.selectByNameAndExeClick		= function(_sElementName, _bChecked, _oCheckValue, _callBack){
		if(!this.oRelateElement){
			this.oRelateElement = [];
			this.execTime = 0;
		}
		this.execTime++;
		//1.参数校验
		if(_sElementName == null){
			CTRSAction_alert("Element Name invalid!");
			return;
		}
		//2.获取
		var arElements = document.all(_sElementName);	
		if(arElements == null)return;

		if(!arElements.length){//单个元素
			//wsw @ 2005-7-14 修改点击方式，这种方式不会触发背景色的修改
			//arElements.checked = !arElements.checked;
			arElements.click();
			return;
		}
		
		var bSelectAllStatus = false;
		if(arguments.length == 2){
			bSelectAllStatus = _bChecked;
		}else{
			bSelectAllStatus = this.getSelectAllStatus(arElements);
		}
		//3.遍历
		for(var i=0; i<arElements.length; i++){
			if(_oCheckValue == null || typeof _oCheckValue == "undefined"){
				if(bSelectAllStatus != arElements[i].checked)arElements[i].click();
			}else{
				if(_oCheckValue.constructor == Array){
					for(var j=0; j<_oCheckValue.length; j++){
						if(arElements[i].value == _oCheckValue[j] && !arElements[i].getAttribute("dealed")){
							arElements[i].checked = _bChecked;	
							this.oRelateElement.push(arElements[i]);
							arElements[i].setAttribute("dealed", true);
							arElements[i].fireEvent('onclick');
							break;
						}
					}
				}else if( arElements[i].value == _oCheckValue && !arElements[i].getAttribute("dealed")){
					arElements[i].checked = _bChecked;	
					this.oRelateElement.push(arElements[i]);
					arElements[i].setAttribute("dealed", true);
					arElements[i].fireEvent('onclick');
				}				
			}
			//arElements[i].checked = bSelectAllStatus;
		}
		this.execTime--;
		if(this.execTime == 0){
			for (var i = 0, len=this.oRelateElement.length; i < len; i++){
				this.oRelateElement[i].removeAttribute("dealed");
				this.oRelateElement[i] = null;
			}
			this.oRelateElement = null;
			if(_callBack) _callBack();
		}
	}


	this.getElementValueByName	= function(_sElementName, _sDelim){
		//1.参数校验
		if(_sElementName == null){
			CTRSAction_alert("Element Name invalid!");
			return;
		}		
		//2.获取
		var arElements = document.all(_sElementName);				
		if(arElements == null)return "";

		var sDelim = _sDelim || ",";

		if(!arElements.length){
			if(arElements.checked)
				return arElements.value;
			return "";
		}



		//3.遍历
		var bFirst = true;
		var sValue	= "";
		for(var i=0; i<arElements.length; i++){
			if(!arElements[i].checked)continue;

			if(bFirst){
				bFirst = false;
				sValue = arElements[i].value;
			}
			else
				sValue += sDelim + arElements[i].value;
		}
		return sValue
	}

	this.getValueByName = this.getElementValueByName;

	this.setElementCheckedByValue	= function(_sElementName, _sValue){
		//1.参数校验
		if(_sValue == null || _sValue.length == 0){
			return;
		}		
		//2.获取
		var arElements = document.all(_sElementName);				
		if(arElements == null)return;

		var sValue = "," + _sValue + ",";		
		
		if(!arElements.length){
			arElements.checked = (sValue.indexOf("," + arElements.value + ",")>=0);			
			return;
		}

		//3.遍历
		var bFirst = true;		
		for(var i=0; i<arElements.length; i++){
			arElements[i].checked = (sValue.indexOf("," + arElements[i].value + ",")>=0);			
		}
		return;
	}

	this.setTopElementsChecked = function(_sElementName, _nTopNum){
		//1.参数校验
		if(_nTopNum == null || _nTopNum <= 0){
			return;
		}		
		//2.获取
		var arElements = document.all(_sElementName);				
		if(arElements == null)return;
		
		
		if(!arElements.length){
			if(!arElements.checked)
				arElements.click();
			return;
		}

		//3.遍历
		for(var i=0; i<arElements.length && i<_nTopNum;  i++){
			//arElements[i].checked = true;			
			if(!arElements[i].checked)
				arElements[i].click();
		}
		return;
	}
}

var TRSHTMLElement = new CTRSHTMLElement();