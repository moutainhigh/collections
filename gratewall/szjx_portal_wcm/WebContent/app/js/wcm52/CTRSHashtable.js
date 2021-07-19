//中文
/** Title:			TRSWindow.js
 *  Description:
 *		Define CTRSHashtable Object
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-11-23 15:27
 *  Vesion:			1.0
 *  Last EditTime:	2004-11-23/2004-11-23
 *	Update Logs:
 *		CH@2004-11-23 Created File
 *	Note:
 *		
 *
 */
function TRSItem(_oValue, _nIndex){
	this.value = _oValue;
	this.index = _nIndex;
}

function CTRSHashtable() {
	this.oObjects	= null;
	this.arKeys		= null;
	this.length		= 0;

	this.init		= function(){
		this.oObjects	= {};
		this.arKeys		= [];
	}

	this.clear		= function(){
		this.oObjects	= null;
		this.arKeys		= null;
	}

	this.contains	= function(_oValue){
		return (typeof(this.oObjects[_oValue]) != "undefined");
	}

	this.containsKey= function(_oKey){
		CTRSAction_alert("Not Realize!");
		return false;
	}

	this.get		= function(_oKey){
		var oTRSItem = this.oObjects[_oKey];
		if(oTRSItem == null 
			|| typeof(oTRSItem) != "object" 
			|| oTRSItem.constructor != TRSItem){
			return null;
		}

		return oTRSItem.value;
	}

	this.keys		= function(){
		var arTemp = [];
		for(var i=0; i<this.arKeys.length; i++){
			if(this.arKeys[i] == null)continue;
			
			arTemp[arTemp.length] = this.arKeys[i];
		}
		return arTemp;
	}

	this.isEmpty	= function(){
		return this.size() == 0;
	}

	this.put		= function(_oKey, _oValue){
		//1. Check Param		
		if(_oKey == null || _oValue == null){
			CTRSAction_alert("Param not Valid["+typeof(_oKey)+"["+typeof(_oValue)+"]");
			return false;
		}

		//2.init
		if(this.oObjects == null)
			this.init();

		//3. Put in 
		var oTRSItem = this.oObjects[_oKey];
		if(oTRSItem == null 
			|| typeof(oTRSItem) != "object" 
			|| oTRSItem.constructor != TRSItem){			
			oTRSItem = new TRSItem(_oValue, this.arKeys.length);
			this.arKeys[this.arKeys.length] = _oKey;
			this.oObjects[_oKey] = oTRSItem;
			this.length++;
		}else{
			oTRSItem.value = _oValue;
		}
		
		
		//4.Return Result
		return true;
	}

	this.putAll		= function(_oTRSHashtable){
		var nSize = _oTRSHashtable.size();
		var arKeys = _oTRSHashtable.keys();
		for(var i=0; i<nSize; i++){
			this.put(arKeys[i], _oTRSHashtable.get(arKeys[i]));			
		}		
	}

	this.remove		= function(_oKey){
		var oTRSItem = this.oObjects[_oKey];
		if(oTRSItem == null 
			|| typeof(oTRSItem) != "object" 
			|| oTRSItem.constructor != TRSItem)
			return null;

		var oValue = oTRSItem.value;
		this.arKeys[oTRSItem.index] = null;
		this.oObjects[_oKey]		= null;
		this.length--;
		return oValue;
	}

	this.size		= function(){
		return this.length;
	}
	
}
