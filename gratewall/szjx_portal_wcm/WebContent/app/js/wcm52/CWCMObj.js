//中文
function isUndefined(a) {
    return typeof a == 'undefined';
} 


function CWCMObj_getProperty(_sPropName){
	return this.hProperty[_sPropName.toUpperCase()];
}

function CWCMObj_setProperty(_sPropName, _sValue){
	//Record Key
	if(isUndefined(this.getProperty(_sPropName)))
		this.arKeys[this.arKeys.length] = _sPropName;

	return this.hProperty[_sPropName.toUpperCase()] = _sValue;
}

function CWCMObj_setBitValue(_sPropName, _nBitIndex, _bValue){
	if(isNaN(_nBitIndex)){
		CTRSAction_alert("BitIndex InValid!PropertyName:["+_sPropName+"] BitIndex:["+_nBitIndex+"]");
		return;
	}
	
	var nValue = 0;
	var sPorpertyValue = this.getProperty(_sPropName);
	if(!isUndefined(sPorpertyValue)){
		nValue = parseInt(sPorpertyValue);		
		if(isNaN(nValue)){
			CTRSAction_alert("Property InValid!Name:["+_sPropName+"] Value:["+sPorpertyValue+"]");
			return;
		}
	}
	
	var oTRSBitsValue = new CTRSBitsValue(nValue);
	oTRSBitsValue.setBit(_nBitIndex, _bValue);

	this.setProperty(_sPropName, oTRSBitsValue.nLowValue);
}

function CWCMObj_setAttribute(_sPropName, _sAttributeName, _sAttributeValue){	
	var sPorpertyValue = this.getProperty(_sPropName);
	if(isUndefined(sPorpertyValue)){
		sPorpertyValue = "";
	}else
		sPorpertyValue += "&";
	sPorpertyValue += _sAttributeName + "=" + _sAttributeValue;

	this.setProperty(_sPropName, sPorpertyValue);
}


function CWCMObj_getKeys(){
	return this.arKeys;
}


function CWCMObj(){	
//Define Properties
	this.hProperty	= {};	
	this.arKeys		= [];

//Define Method
	this.getProperty = CWCMObj_getProperty;
	this.setProperty = CWCMObj_setProperty;
	this.getKeys	 = CWCMObj_getKeys;
	this.setBitValue = CWCMObj_setBitValue;
	this.setBitValue = CWCMObj_setBitValue;
	this.setAttribute= CWCMObj_setAttribute;
}