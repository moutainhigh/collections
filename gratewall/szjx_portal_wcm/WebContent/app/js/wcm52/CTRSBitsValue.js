//中文
function CTRSBitsValue_getBit(_nIndex){
	if(_nIndex >= 32){
		return this.nHighValue & Math.pow(2, (_nIndex-32));
	}else{
		return this.nLowValue & Math.pow(2, _nIndex);
	}
}

function CTRSBitsValue_setValue(_nLowValue, _nHighValue){
	this.nLowValue	= _nLowValue	|| 0;
	this.nHighValue = _nHighValue	|| 0;
}

function CTRSBitsValue_setBit(_nIndex, _bValue){
	var nIndex = _nIndex, nValue = this.nLowValue;
	if(_nIndex >= 32){
		nIndex = _nIndex - 32;
		nValue = this.nHighValue;
	}
	
	if(nValue == 0 && !_bValue)return;

	var nTemp;
	if(_bValue){
		nValue = (nValue | Math.pow(2, nIndex));		
	}else{//^ &
		nTemp = ~Math.pow(2, nIndex);
		nValue = (nValue & nTemp);
	}

	if(_nIndex > 31){
		this.nHighValue = nValue;
	}else{
		this.nLowValue = nValue;
	}
}

function CTRSBitsValue(_nLowValue, _nHighValue){
//Define Properties
	this.nLowValue	= _nLowValue || 0;
	this.nHighValue = _nHighValue || 0;

//Define Method
	this.setBit		= CTRSBitsValue_setBit;
	this.setValue	= CTRSBitsValue_setValue;
	this.getBit		= CTRSBitsValue_getBit;
}

var TRSBitsValue = new CTRSBitsValue();